package com.bewitchment.common.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bewitchment.common.tile.util.CauldronFluidTank;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
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
		collectionZone = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 0.65D, pos.getZ() + 1);
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
		// TODO Auto-generated method stub
	}
	
	private void checkForCraftingRecipe() {
		// TODO Auto-generated method stub
	}
	
	private Mode getModeForFirstItem(ItemStack stack) {
		// TODO Auto-generated method stub
		return Mode.FAILING;
	}
	
	private ItemStack gatherNextItemFromTop() {
		List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, collectionZone);
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
