package com.bewitchment.common.tile;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

/**
 * This class was created by Arekkuusu on 08/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("deprecation")
public class TileCandle extends TileMod implements ITickable { // TODO no reason for them to be tickable tiles, we can achieve the same results with blockstates

	private EnumDyeColor color;
	private boolean lit;
	private int type;
	private int ticks;

	public TileCandle() {
	}

	public TileCandle(int type, EnumDyeColor color) {
		this.type = type;
		this.color = color;
	}

	public void litCandle() {
		world.playSound(null, getPos(), SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1F, 1F);
		this.lit = true;
		world.profiler.startSection("checkLight");
		world.checkLight(pos);
		world.profiler.endSection();
	}

	public void unLitCandle() {
		if (isLit()) {
			world.playSound(null, getPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1F, 1F);
			this.lit = false;
			world.profiler.startSection("checkLight");
			world.checkLight(pos);
			world.profiler.endSection();
		}
	}

	public boolean isLit() {
		return lit;
	}

	@Override
	public void update() {
		if (lit && ticks % 10 == 0) {
			final BlockPos pos = getPos();
			world.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.7 + type * 0.25, pos.getZ() + 0.5, 0, 0, 0);
		}
		ticks++;
	}

	public EnumDyeColor getColor() {
		return color;
	}

	public void setColor(EnumDyeColor color) {
		this.color = color;
	}

	@Override
	void readDataNBT(NBTTagCompound cmp) {
		lit = cmp.getBoolean("lit");
		color = EnumDyeColor.byMetadata(cmp.getInteger("color"));
	}

	@Override
	void writeDataNBT(NBTTagCompound cmp) {
		cmp.setBoolean("lit", lit);
		cmp.setInteger("color", color.getMetadata());
	}
}
