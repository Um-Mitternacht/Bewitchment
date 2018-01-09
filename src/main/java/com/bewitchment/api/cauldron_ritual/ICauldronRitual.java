package com.bewitchment.api.cauldron_ritual;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 06/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface ICauldronRitual<T extends TileEntity> {

	boolean canPerform(T tile, World world, BlockPos pos);

	void onUpdate(CauldronRitualHolder<T> ritual, T tile, World world, BlockPos pos);

	void onFinish(T tile, World world, BlockPos pos);

	int getCost();
}
