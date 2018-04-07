package com.bewitchment.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.cauldron.BrewBuilder;
import com.bewitchment.common.cauldron.BrewData;
import com.bewitchment.common.core.helper.ColorHelper;
import com.bewitchment.common.crafting.CauldronCraftingRecipe;
import com.bewitchment.common.crafting.cauldron.CauldronFoodValue;
import com.bewitchment.common.crafting.cauldron.CauldronRegistry;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.util.CauldronFluidTank;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntityCauldron extends ModTileEntity implements ITickable {
	
	public static final int MAX_HEAT = 40, BOILING_POINT = 25, DEFAULT_COLOR = 0x42499b;
	
	private Mode mode = Mode.IDLE;
	private NonNullList<ItemStack> ingredients = NonNullList.create();
	private AxisAlignedBB collectionZone;
	private CauldronFluidTank tank;
	
	private int currentColorRGB = DEFAULT_COLOR;
	private int heat = 0;
	private int progress = 0;
	private boolean lockInputForCrafting = false;
	
	public static enum Mode {
		IDLE(0), FAILING(0), BREW(200), CRAFTING(100), STEW(1000), LAVA(0), CLEANING(30);
		
		private int time;
		
		private Mode(int time) {
			this.time = time;
		}
		
		public int getTime() {
			return time;
		}
	}
	
	public TileEntityCauldron() {
		collectionZone = new AxisAlignedBB(0, 0, 0, 1, 0.65D, 1);
		tank = new CauldronFluidTank(this);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) { 
			if (world.getTotalWorldTime() % 5 == 0) {
				handleHeatAndBoilingStatus();
				handleItemCollisions();
			}
			handleCraftingProgress();
		}
	}
	
	private void handleCraftingProgress() {
		if ((getMode() == Mode.CRAFTING && lockInputForCrafting) || (getMode() != Mode.CRAFTING && getMode().getTime() > 0)) {
			if (progress < getMode().getTime()) {
				progress++; // TODO Should this require ME?
				markDirty();
			} else {
				if (getMode() == Mode.CRAFTING) {
					Optional<CauldronCraftingRecipe> result = CauldronRegistry.getCraftingResult(tank.getFluid(), ingredients);
					if (result.isPresent()) {
						CauldronCraftingRecipe recipe = result.get();
						if (tank.getFluidAmount() >= recipe.getRequiredAmount()) {
							tank.setCanDrain(true);
							tank.drain(recipe.getRequiredAmount(), true);
							spawnCraftingResultAndUnlock(recipe);// TODO rename to ...AndReset instead of ...AndUnlock
							syncToClient();
						}
					}
				} else if (getMode() == Mode.CLEANING) {
					tank.setCanDrain(true);
					tank.drain(250, true);
					tank.setCanDrain(false);
					progress = 0;
					if (tank.isEmpty()) {
						reset();
					} else {
						markDirty();
						syncToClient();
					}
				}
				
			}
		}
	}
	
	private void spawnCraftingResultAndUnlock(CauldronCraftingRecipe stack) {
		EntityItem result = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack.getResult());
		world.spawnEntity(result);
		tank.setCanDrain(true);
		tank.setCanFill(true);
		ingredients.clear();
		mode = Mode.IDLE;
		lockInputForCrafting = false;
		progress = 0;
		markDirty();
	}
	
	private void handleItemCollisions() {
		if (isBoiling() && !lockInputForCrafting) {
			ItemStack stack = gatherNextItemFromTop();
			if (!stack.isEmpty()) {
				processNextItem(stack);
			}
		}
	}
	
	private void reset() {
		tank.setFluid(null);
		tank.setCanDrain(true);
		tank.setCanFill(true);
		lockInputForCrafting = false;
		ingredients.clear();
		setMode(Mode.IDLE);
		heat = 0;
		currentColorRGB = DEFAULT_COLOR;
		markDirty();
		syncToClient();
	}
	

	private void processNextItem(ItemStack stack) {
		boolean flag = ingredients.isEmpty();
		progress = 0;
		if (stack.getItem() == ModItems.wood_ash) {
			setMode(Mode.CLEANING);
			lockInputForCrafting = true;
			tank.setFluid(new FluidStack(FluidRegistry.WATER, tank.getFluidAmount()));
			tank.setCanDrain(false);
			tank.setCanFill(false);
			currentColorRGB = 0xFF00FF;
			syncToClient();
			markDirty();
			return;
		}
		
		switch (getMode()) {
			case IDLE: {
				setMode(getModeForFirstItem(stack));
				tank.setCanDrain(false);
				tank.setCanFill(false);
				if (mode != Mode.BREW) {
					processNextItem(stack); // Now this actually passes it to the correct mode handler
				}
				break;
			}
			case CRAFTING: {
				if (tank.getInnerFluid() != FluidRegistry.LAVA) {
					ingredients.add(stack);
				} else if (tank.getInnerFluid() == FluidRegistry.WATER) {
					blendColor(0xFF000000 | (0x10000 * this.world.rand.nextInt(0xFF)) | (0x100 * this.world.rand.nextInt(0xFF)) | (this.world.rand.nextInt(0xFF)), 0.15f);
				}
				checkForCraftingRecipe();
				break;
			}
			case STEW: {
				if (CauldronRegistry.getCauldronFoodValue(stack) != null) {
					ingredients.add(stack);
					blendColor(0x855000, 0.2f);
				} else {
					setMode(Mode.FAILING);
				}
				if (ingredients.size() > 10) {
					setMode(Mode.FAILING);
				}
				syncToClient();
				break;
			}
			case FAILING: {
				ingredients.add(stack);
				break;
			}
			case BREW: {
				ingredients.add(stack);
				progress = 0;
				updateBrewColor();
				break;
			}
			case LAVA: {
				tank.setCanDrain(true);
				tank.setCanFill(true);
				handleExplosivesInLava(stack);
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
	
	private void handleExplosivesInLava(ItemStack stack) {
		if (stack.getItem() == Items.GUNPOWDER || stack.getItem() == Items.FIRE_CHARGE) {
			world.createExplosion(null, pos.getX() + 0.5, pos.getX() + 0.5, pos.getX() + 0.5, 1, true); // FIXME
		} else if (stack.getItem() == Item.getItemFromBlock(Blocks.TNT)) {
			world.createExplosion(null, pos.getX() + 0.5, pos.getX() + 0.5, pos.getX() + 0.5, 3, true); // FIXME I have no idea why it's not working tbh...
		} else if (stack.getItem() == Items.FIREWORKS || stack.getItem() == Items.FIREWORK_CHARGE) {
			world.createExplosion(null, pos.getX() + 0.5, pos.getX() + 0.5, pos.getX() + 0.5, 3, true); // FIXME + TODO Make firework go off
		}
	}
	
	private void updateBrewColor() {
		Optional<BrewData> data = new BrewBuilder(ingredients).build();
		if (data.isPresent()) {
			currentColorRGB = PotionUtils.getPotionColorFromEffectList(data.get().getEffects().stream()//
					.map(ibe -> new PotionEffect(ibe.getPotion()))//
					.collect(Collectors.toList()));
		} else {
			this.mode = Mode.FAILING;
		}
		markDirty();
		syncToClient();
	}
	
	private ItemStack getSoup() {
		int hunger = 0;
		float saturation = 0;
		float multiplier = 1;
		float decay = 0.6f;
		
		long differentItems = ingredients.stream().map(is -> is.toString()).distinct().count();
		
		for (ItemStack i : ingredients) {
			CauldronFoodValue next = CauldronRegistry.getCauldronFoodValue(i);
			if (next == null) {
				Bewitchment.logger.warn(i + " is not a valid food, this shouldn't happen! Report to https://github.com/Um-Mitternacht/Bewitchment/issues");
				setMode(Mode.FAILING);
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
		Optional<CauldronCraftingRecipe> result = CauldronRegistry.getCraftingResult(fs, new ArrayList<>(ingredients));
		if (result.isPresent()) {
			lockInputForCrafting = true;
		}
	}
	
	private Mode getModeForFirstItem(ItemStack stack) {
		if (tank.getInnerFluid() == FluidRegistry.LAVA) {
			return Mode.LAVA;
		}
		if (CauldronRegistry.getCauldronFoodValue(stack) != null && tank.getInnerFluid() == FluidRegistry.WATER) {
			currentColorRGB = 0xede301;
			return Mode.STEW;
		} else if (stack.getItem() == Items.NETHER_WART && tank.getInnerFluid() == FluidRegistry.WATER) {
			currentColorRGB = 0xa366a3;
			return Mode.BREW;
		}
		currentColorRGB = 0x5cb85c;
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
						if (ingredients.size() > 0 && heat < BOILING_POINT) {
							setMode(Mode.FAILING);
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
	void readAllModDataNBT(NBTTagCompound tag) {
		mode = Mode.values()[tag.getInteger("mode")];
		progress = tag.getInteger("progress");
		heat = tag.getInteger("heat");
		currentColorRGB = tag.getInteger("color");
		lockInputForCrafting = tag.getBoolean("lock");
		tank.readFromNBT(tag.getCompoundTag("tank"));
		ingredients.clear();
		ItemStackHelper.loadAllItems(tag.getCompoundTag("ingredients"), ingredients);
	}
	
	@Override
	void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setInteger("mode", mode.ordinal());
		tag.setInteger("progress", progress);
		tag.setInteger("heat", heat);
		tag.setInteger("color", currentColorRGB);
		tag.setBoolean("lock", lockInputForCrafting);
		tag.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		tag.setTag("ingredients", ItemStackHelper.saveAllItems(new NBTTagCompound(), ingredients));
	}
	
	public boolean onCauldronRightClick(EntityPlayer playerIn, EnumHand hand, ItemStack heldItem) {
		if (!playerIn.world.isRemote) {
			if (ingredients.size() == 0 && heldItem.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
				FluidUtil.interactWithFluidHandler(playerIn, hand, tank);
				if (playerIn.isCreative() && tank.isFull()) {
					heat = MAX_HEAT;
					markDirty();
					syncToClient();
				}
			} else if (heldItem.getItem() == Items.BOWL && getMode() == Mode.STEW && (progress >= getMode().getTime() || playerIn.isCreative())) {
				if (!playerIn.isCreative()) {
					heldItem.shrink(1);
				}
				lockInputForCrafting = true;
				ItemStack soup = getSoup();
				tank.setCanDrain(true);
				tank.drain(500, true);
				tank.setCanDrain(false);
				giveItemToPlayer(playerIn, soup);
				if (tank.isEmpty()) {
					reset();
				} else {
					syncToClient();
					markDirty();
				}
			} else if (heldItem.getItem() == Items.GLASS_BOTTLE && Mode.BREW == getMode() && progress >= getMode().getTime()) {
				if (progress >= getMode().getTime() || playerIn.isCreative()) {
					lockInputForCrafting = true;
					createAndGiveBrew(playerIn, heldItem);
				}
			}
		}
		return true;
	}
	
	private void createAndGiveBrew(EntityPlayer playerIn, ItemStack stack) {
		Optional<BrewData> data = new BrewBuilder(ingredients).build();
		if (data.isPresent()) {
			ItemStack brew = new ItemStack(ModItems.brew_phial_drink);
			if (!playerIn.isCreative()) {
				stack.shrink(1);
			}
			data.get().saveToStack(brew);
			giveItemToPlayer(playerIn, brew);
			tank.setCanDrain(true);
			tank.drain(300 + world.rand.nextInt(400), true);// TODO make it not completely random but dependent on the player brewing skill
			tank.setCanDrain(false);
			if (tank.isEmpty()) {
				reset();
			} else {
				markDirty();
				syncToClient();
			}
		}
	}
	
	public int getColorRGB() {
		if (getMode() == Mode.FAILING) {
			return 0xb1d626; // Vomit color basically
		}
		if (tank.getInnerFluid() == Fluids.BW_HONEY) {
			return 0x8b720a;
		}
		if (tank.getInnerFluid() == Fluids.MUNDANE_OIL) {
			return 0x508030;
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
		currentColorRGB = ColorHelper.blendColor(currentColorRGB, newColorRGB, ratio);
		syncToClient();
	}
	
	private boolean isAboveFlame() {
		IBlockState below = world.getBlockState(this.pos.down());
		return (below.getMaterial() == Material.FIRE || below.getMaterial() == Material.LAVA);
	}
	
	public boolean isBoiling() {
		return heat >= BOILING_POINT;
	}
	
	public void onLiquidChange() {
		if (tank.getFluidAmount() == 0) {
			reset();
		}
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
		tag.setInteger("mode", getMode().ordinal());
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
		setMode(Mode.values()[tag.getInteger("mode")]);
	}
	
	private void giveItemToPlayer(EntityPlayer player, ItemStack toGive) {
		if (!player.inventory.addItemStackToInventory(toGive)) {
			player.dropItem(toGive, false);
		} else if (player instanceof EntityPlayerMP) {
			((EntityPlayerMP) player).sendContainerToPlayer(player.inventoryContainer);
		}
	}
	
	public CauldronFluidTank getTank() {
		return tank;
	}
	
	public Mode getMode() {
		return mode;
	}
	
	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public int getProgress() {
		return progress;
	}
}
