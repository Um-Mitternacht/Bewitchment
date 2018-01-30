package com.bewitchment.common.core.command;

import static com.bewitchment.api.BewitchmentAPI.COLOR;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.capability.energy.EnergyHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 4/20/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
class IncantationCandlelight implements IIncantation {

	@Override
	public void cast(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		World world = sender.getEntityWorld();
		BlockPos source = sender.getPosition();
		for (BlockPos pos : BlockPos.getAllInBox(source.add(7, 3, 7), source.add(-5, -5, -5))) {
			IBlockState state = world.getBlockState(pos);
			boolean flag = false;
			if (state.getBlock() == ModBlocks.candle_medium) {
				world.setBlockState(pos, ModBlocks.candle_medium_lit.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 3);
				flag = true;
			}
			if (state.getBlock() == ModBlocks.candle_small) {
				world.setBlockState(pos, ModBlocks.candle_small_lit.getDefaultState().withProperty(COLOR, state.getValue(COLOR)), 3);
				flag = true;
			}
			if (flag) {
				for (int i = 0; i < 5; i++) {
					double x = pos.getX() + world.rand.nextFloat();
					double y = pos.getY() + world.rand.nextFloat();
					double z = pos.getZ() + world.rand.nextFloat();
					world.spawnParticle(EnumParticleTypes.FLAME, x, y, z, 0, 0, 0);
				}
			}
		}
		EnergyHandler.addEnergy((EntityPlayer) sender, 800);
	}
}
