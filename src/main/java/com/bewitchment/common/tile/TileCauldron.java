package com.bewitchment.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.crafting.cauldron.CauldronFoodValue;
import com.bewitchment.common.crafting.cauldron.CauldronRegistry;
import com.bewitchment.common.tile.util.CauldronFluidTank;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TileCauldron extends TileMod implements ITickable {
	
	private static final int MAX_HEAT = 20, BOILING_POINT = 15;

	private int currentColorRGB = 0x12193b;
	private Mode mode = Mode.IDLE;
	private List<ItemStack> ingredients = new ArrayList<>();
	private int heat = 0;
	private int ticks = 0;
	private AxisAlignedBB collectionZone;
	private CauldronFluidTank tank;

	public enum Mode {
		
		IDLE(false), FAILING(false), BREW(true), CRAFTING(true), COOKING(true);
		
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
		tank.fill(new FluidStack(FluidRegistry.WATER, 1000), true);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if (ticks % 10 == 0) {
				handleHeatAndBoilingStatus();
				if (isBoiling()) {
					ItemStack stack = gatherNextItemFromTop();
					if (!stack.isEmpty()) {
						processNextItem(stack);
					}
				}
			}
			ticks++;
		} else {
			float level = tank.getFluidAmount() / (Fluid.BUCKET_VOLUME * 2F);
			level = getPos().getY() + 0.1F + level;
			for (int i = 0; i < 2; i++) {
				double posX = getPos().getX() + 0.2D + world.rand.nextDouble() * 0.6D;
				double posZ = getPos().getZ() + 0.2D + world.rand.nextDouble() * 0.6D;
				Bewitchment.proxy.spawnParticle(ParticleF.CAULDRON_BUBBLE, posX, level, posZ, 0, 0, 0, currentColorRGB);
			}
			if (ticks % 2 == 0 && hasIngredients()) {
				final float x = getPos().getX() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				final float z = getPos().getZ() + MathHelper.clamp(world.rand.nextFloat(), 0.2F, 0.9F);
				Bewitchment.proxy.spawnParticle(ParticleF.SPARK, x, level, z, 0.0D, 0.1D, 0.0D);
			}
		}
	}
	
	private void processNextItem(ItemStack stack) {
		switch (mode) {
			case IDLE: {
				mode = getModeForFirstItem(stack);
				break;
			}
			case CRAFTING: {
				ingredients.add(stack);
				checkForCraftingRecipe();
				break;
			}
			case COOKING: {
				updateSoupStats(stack);
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
		markDirty();
	}
	
	private void updateBrew() {
		// TODO Auto-generated method stub
	}
	
	private void updateSoupStats(ItemStack stack) {
		// TEMP CODE, TODO
		
		int hunger = 0;
		float saturation = 0;
		float multiplier = 1;
		float decay = 0.6f;
		
		for (ItemStack i : ingredients) {
			CauldronFoodValue next = CauldronRegistry.getValue(i);
			if (next == null) {
				Bewitchment.logger.warn("Not a valid food, this shouldn't happen!");
				return;
			}
			hunger += (next.hunger * multiplier);
			saturation += (next.saturation * multiplier);
			multiplier *= decay;
		}
		
		System.out.format("Soup stats:\nUses: %d\nHunger: %d\nSaturation: %d", ingredients.size(), hunger, saturation);
		
	}
	
	private void checkForCraftingRecipe() {
		// TODO Auto-generated method stub
	}
	
	private Mode getModeForFirstItem(ItemStack stack) {
		if (stack.getItem() instanceof ItemFood) {
			return Mode.COOKING;
		} else if (stack.getItem() == Items.NETHER_WART) {
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
		if (tank.isEmpty()) {
			heat = 0;
			mode = Mode.IDLE;
			ingredients.clear();
			markDirty();
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
		}
	}
	
	@Override
	void readDataNBT(NBTTagCompound cmp) {
		// TODO
	}
	
	@Override
	void writeDataNBT(NBTTagCompound cmp) {
		// TODO
	}
	
	public boolean useCauldron(EntityPlayer playerIn, EnumHand hand, ItemStack heldItem) {
		return false; // TODO
	}
	
	public int getColorRGB() {
		if (mode == Mode.FAILING) {
			return 0x4d5a0b; // Vomit color basically
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
		// TODO check if recipe should be voided
		markDirty();
	}
}
