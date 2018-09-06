package com.bewitchment.common.tile.util;

import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

import javax.annotation.Nullable;

public class CauldronFluidTank extends FluidTank {

	private final TileEntityCauldron tileCauldron;

	public CauldronFluidTank(TileEntityCauldron tile) {
		super(Fluid.BUCKET_VOLUME);
		this.tileCauldron = tile;
	}

	@Override
	public int fillInternal(FluidStack resource, boolean doFill) {
		int filled = super.fillInternal(resource, doFill);
		if (doFill && filled > 0) {
			// world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
		}
		return filled;
	}

	@Nullable
	@Override
	public FluidStack drainInternal(int maxDrain, boolean doDrain) {
		FluidStack drained = super.drainInternal(maxDrain, doDrain);
		if (doDrain && drained != null && drained.amount > 0) {
			// world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
		}
		return drained;
	}

	@Override
	protected void onContentsChanged() {
		tileCauldron.onLiquidChange();
	}

	@Override
	public String toString() {
		return String.format("Cauldron: %s, %d/%d", fluid != null && fluid.getFluid() != null ? fluid.getFluid().getName() : "Empty", getFluidAmount(), getCapacity());
	}

	public boolean hasFluid() {
		FluidStack fluid = getFluid();
		return fluid != null && fluid.amount > 0 && fluid.getFluid() != null;
	}

	public boolean hasFluid(Fluid other) {
		return fluid != null && fluid.getFluid() == other;
	}

	@Nullable
	public Fluid getInnerFluid() {
		return fluid != null ? fluid.getFluid() : null;
	}

	public boolean isEmpty() {
		return getFluid() == null || getFluid().amount <= 0;
	}

	public boolean isFull() {
		return getFluid() != null && getFluid().amount == getCapacity();
	}
}
