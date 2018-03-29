package com.bewitchment.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.crafting.cauldron.CauldronFoodValue;
import com.bewitchment.common.crafting.cauldron.CauldronRegistry;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.util.CauldronFluidTank;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileCauldron extends TileMod implements ITickable {
	
	private static final int MAX_HEAT = 40, BOILING_POINT = 30, CRAFTING_TIME = 100, DEFAULT_COLOR = 0x12193b;
	
	private Mode mode = Mode.IDLE;
	private List<ItemStack> ingredients = new ArrayList<>();
	private AxisAlignedBB collectionZone;
	private CauldronFluidTank tank;
	
	private int currentColorRGB = DEFAULT_COLOR;
	private int heat = 0;
	private int progress = 0;
	private boolean lockInputForCrafting = false;


	public enum Mode {
		
		IDLE(false), FAILING(false), BREW(true), CRAFTING(true), STEW(true);
		
		private boolean failable;
		
		Mode(boolean failable) {
			this.failable = failable;
		}
		
		public boolean canFail() {
			return failable;
		}
	}
	
	public TileCauldron() {
		collectionZone = new AxisAlignedBB(0, 0, 0, 1, 0.65D, 1);
		tank = new CauldronFluidTank(this);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) { // ----------- Server side
			if (world.getTotalWorldTime() % 5 == 0) {
				handleHeatAndBoilingStatus();
				if (isBoiling() && !lockInputForCrafting) {
					ItemStack stack = gatherNextItemFromTop();
					if (!stack.isEmpty()) {
						processNextItem(stack);
					}
				}
			}
			
			if ((mode == Mode.CRAFTING && lockInputForCrafting) || mode == Mode.STEW) {
				if (progress < CRAFTING_TIME) {
					progress++; // TODO Should this require ME?
					markDirty();
				} else {
					if (mode == Mode.CRAFTING) {
						CauldronRegistry.getCraftingResult(tank.getFluid(), ingredients).ifPresent(stack -> {
							EntityItem result = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
							world.spawnEntity(result);
						});
						reset();
					}
					syncToClient();
				}
			}
			
		} else { // ----------- Client side
			float level = tank.getFluidAmount() / (Fluid.BUCKET_VOLUME * 2F);
			level = getPos().getY() + 0.1F + level;
			if (isBoiling()) {
				for (int i = 0; i < 2; i++) {
					double posX = getPos().getX() + 0.2D + world.rand.nextDouble() * 0.6D;
					double posZ = getPos().getZ() + 0.2D + world.rand.nextDouble() * 0.6D;
					Bewitchment.proxy.spawnParticle(ParticleF.CAULDRON_BUBBLE, posX, level, posZ, 0, 0, 0, currentColorRGB);
				}
			}
			
			if (mode == Mode.CRAFTING || (mode == Mode.STEW && progress >= CRAFTING_TIME)) {
				final float x = getPos().getX() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				final float z = getPos().getZ() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				Bewitchment.proxy.spawnParticle(ParticleF.SPARK, x, level, z, 0.0D, 0.1D, 0.0D);
			}
		}
	}
	
	private void reset() {
		tank.setFluid(null);
		tank.setCanDrain(true);
		tank.setCanFill(true);
		lockInputForCrafting = false;
		ingredients.clear();
		mode = Mode.IDLE;
		heat = 0;
		currentColorRGB = DEFAULT_COLOR;
		markDirty();
		syncToClient();
	}
	

	private void processNextItem(ItemStack stack) {
		boolean flag = ingredients.isEmpty();
		switch (mode) {
			case IDLE: {
				mode = getModeForFirstItem(stack);
				tank.setCanDrain(false);
				tank.setCanFill(false);
				processNextItem(stack); // Now actually pass it to the correct mode handler
				break;
			}
			case CRAFTING: {
				ingredients.add(stack);
				checkForCraftingRecipe();
				break;
			}
			case STEW: {
				progress = 0;
				if (CauldronRegistry.getCauldronFoodValue(stack) != null) {
					ingredients.add(stack);
				} else {
					mode = Mode.FAILING;
				}
				if (ingredients.size() > 10) {
					mode = Mode.FAILING;
				}
				break;
			}
			case FAILING: {
				ingredients.add(stack);
				break;
			}
			case BREW: {
				ingredients.add(stack);
				updateBrew();
				break;
			}
			default: {
				break;
			}
		}
		if (flag) {
			syncToClient();
		}
		markDirty();
	}
	
	private void updateBrew() {
		progress = 0;
	}
	
	private ItemStack getSoup() {
		// TEMP CODE, TODO
		int hunger = 0;
		float saturation = 0;
		float multiplier = 1;
		float decay = 0.6f;
		
		long differentItems = ingredients.stream().map(is -> is.toString()).distinct().count();
		
		for (ItemStack i : ingredients) {
			CauldronFoodValue next = CauldronRegistry.getCauldronFoodValue(i);
			if (next == null) {
				Bewitchment.logger.warn(i + " is not a valid food, this shouldn't happen! Report to https://github.com/Um-Mitternacht/Bewitchment/issues");
				mode = Mode.FAILING;
				return ItemStack.EMPTY;
			}
			hunger += (next.hunger * multiplier);
			saturation += (next.saturation * multiplier);
			multiplier *= decay;
		}
		
		float bonus = differentItems / 4f;
		hunger *= bonus;
		saturation *= bonus;
		ItemStack stew = new ItemStack(ModItems.stew);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("hunger", hunger);
		nbt.setFloat("saturation", saturation);
		stew.setTagCompound(nbt);
		return stew;
	}
	
	private void checkForCraftingRecipe() {
		FluidStack fs = tank.getFluid();
		Optional<ItemStack> result = CauldronRegistry.getCraftingResult(fs, new ArrayList<>(ingredients));
		if (result.isPresent()) {
			lockInputForCrafting = true;
		}
	}
	
	private Mode getModeForFirstItem(ItemStack stack) {
		if (CauldronRegistry.getCauldronFoodValue(stack) != null && tank.getInnerFluid() == FluidRegistry.WATER) {
			return Mode.STEW;
		} else if (stack.getItem() == Items.NETHER_WART && tank.getInnerFluid() == FluidRegistry.WATER) {
			return Mode.BREW;
		}
		return Mode.CRAFTING;
	}
	
	private ItemStack gatherNextItemFromTop() {
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, collectionZone.offset(getPos()));
		if (list.isEmpty()) {
			return ItemStack.EMPTY;
		}
		EntityItem selectedEntityItem = list.get(0);
		ItemStack next = selectedEntityItem.getItem().splitStack(1);
		if (selectedEntityItem.getItem().isEmpty()) {
			selectedEntityItem.setDead();
		}
		return next;
	}
	
	private void handleHeatAndBoilingStatus() {
		if (!tank.isEmpty()) {
			if (tank.getInnerFluid().getTemperature() > 1000) { // Hot liquids are hot
				if (heat < MAX_HEAT) {
					heat = MAX_HEAT;
					markDirty();
					syncToClient();
				}
			} else {
				if (isAboveFlame()) {
					if (heat < MAX_HEAT) {
						heat++;
						markDirty();
					}
				} else {
					if (heat > 0) {
						heat--;
						if (mode.canFail() && heat < BOILING_POINT) {
							mode = Mode.FAILING;
						}
						markDirty();
					}
				}
				if (heat >= BOILING_POINT - 1 && heat <= BOILING_POINT + 1) {
					syncToClient();
				}
			}
		}
	}
	
	@Override
	void readAllModDataNBT(NBTTagCompound cmp) {
		// TODO
	}
	
	@Override
	void writeAllModDataNBT(NBTTagCompound cmp) {
		// TODO
	}
	
	public boolean onCauldronRightClick(EntityPlayer playerIn, EnumHand hand, ItemStack heldItem) {
		if (!playerIn.world.isRemote) {
			if (ingredients.size() == 0 && heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
				FluidUtil.interactWithFluidHandler(playerIn, hand, tank);
				return true;
			}
			if (heldItem.getItem() == Items.BOWL && mode == Mode.STEW && progress >= CRAFTING_TIME) {
				if (!playerIn.isCreative()) {
					heldItem.shrink(1);
				}
				giveItemToPlayer(playerIn, getSoup());
				reset();
				return true;
			}
		}
		// TODO
		return true;
	}
	
	public int getColorRGB() {
		if (mode == Mode.FAILING) {
			return 0xb1d626; // Vomit color basically
		}
		return currentColorRGB;
	}
	
	public boolean hasIngredients() {
		return !ingredients.isEmpty();
	}
	
	public Optional<FluidStack> getFluid() {
		return tank.isEmpty() ? Optional.empty() : Optional.ofNullable(tank.getFluid());
	}
	
	private void blendColor(int newColorRGB, float ratio) {
		if (ratio > 1f) {
			ratio = 1f;
		} else if (ratio < 0f) {
			ratio = 0f;
		}
		float iRatio = 1.0f - ratio;
		
		int aA = (currentColorRGB >> 24 & 0xff);
		int aR = ((currentColorRGB & 0xff0000) >> 16);
		int aG = ((currentColorRGB & 0xff00) >> 8);
		int aB = (currentColorRGB & 0xff);
		
		int bA = (newColorRGB >> 24 & 0xff);
		int bR = ((newColorRGB & 0xff0000) >> 16);
		int bG = ((newColorRGB & 0xff00) >> 8);
		int bB = (newColorRGB & 0xff);
		
		int A = ((int) (aA * iRatio) + (int) (bA * ratio));
		int R = ((int) (aR * iRatio) + (int) (bR * ratio));
		int G = ((int) (aG * iRatio) + (int) (bG * ratio));
		int B = ((int) (aB * iRatio) + (int) (bB * ratio));
		
		currentColorRGB = A << 24 | R << 16 | G << 8 | B;
	}
	
	private boolean isAboveFlame() {
		IBlockState below = world.getBlockState(this.pos.down());
		return (below.getMaterial() == Material.FIRE || below.getMaterial() == Material.LAVA);
	}
	
	public boolean isBoiling() {
		return heat >= BOILING_POINT;
	}
	
	public void onLiquidChange() {
		heat = 0;
		markDirty();
		syncToClient();
	}
	
	@Override
	void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setInteger("heat", heat);
		tag.setInteger("progress", progress);
		tag.setInteger("color", currentColorRGB);
		tag.setBoolean("hasItemsInside", ingredients.size() > 0);
		tag.setInteger("mode", mode.ordinal());
	}
	
	@Override
	void readModSyncDataNBT(NBTTagCompound tag) {
		tank.readFromNBT(tag.getCompoundTag("tank"));
		heat = tag.getInteger("heat");
		progress = tag.getInteger("progress");
		currentColorRGB = tag.getInteger("color");
		if (tag.getBoolean("hasItemsInside")) {
			ingredients.clear();
			ingredients.add(ItemStack.EMPTY); // Makes the list not empty
		}
		mode = Mode.values()[tag.getInteger("mode")];
	}
	
	private void giveItemToPlayer(EntityPlayer player, ItemStack toGive) {
		if (!player.inventory.addItemStackToInventory(toGive)) {
			player.dropItem(toGive, false);
		} else if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
		}
	}
}
