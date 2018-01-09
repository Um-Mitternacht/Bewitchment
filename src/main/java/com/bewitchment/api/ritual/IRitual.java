package com.bewitchment.api.ritual;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 06/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface IRitual<T extends TileEntity> {

	boolean canPerform(T tile, World world, BlockPos pos);

	void onUpdate(RitualHolder<T> ritual, T tile, World world, BlockPos pos);

	void onFinish(T tile, World world, BlockPos pos);

	int getCost();
}
