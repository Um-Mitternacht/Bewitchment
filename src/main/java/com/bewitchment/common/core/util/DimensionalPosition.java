package com.bewitchment.common.core.util;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class DimensionalPosition {

	private int dim, x, y, z;

	public DimensionalPosition(int xIn, int yIn, int zIn, int dimension) {
		x = xIn;
		y = yIn;
		z = zIn;
		dim = dimension;
	}

	public DimensionalPosition(NBTTagCompound tag) {
		this(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z"), tag.getInteger("d"));
	}

	public DimensionalPosition(BlockPos pos, int dimension) {
		this(pos.getX(), pos.getY(), pos.getZ(), dimension);
	}

	public DimensionalPosition(Entity entity) {
		this(entity.getPosition(), entity.world.provider.getDimension());
	}

	public DimensionalPosition(TileEntity entity) {
		this(entity.getPos(), entity.getWorld().provider.getDimension());
	}

	public BlockPos getPosition() {
		return new BlockPos(getX(), getY(), getZ());
	}

	public int getX() {
		return x;
	}

	public int getDim() {
		return dim;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public double getDistanceSqFrom(DimensionalPosition pos) {
		if (pos.getDim() != this.getDim()) {
			return Double.POSITIVE_INFINITY;
		}
		int diffx = this.getX() - pos.getX();
		int diffy = this.getY() - pos.getY();
		int diffz = this.getZ() - pos.getZ();

		return diffx * diffx + diffy * diffy + diffz * diffz;
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("x", x);
		tag.setInteger("y", y);
		tag.setInteger("z", z);
		tag.setInteger("d", dim);
		return tag;
	}
}
