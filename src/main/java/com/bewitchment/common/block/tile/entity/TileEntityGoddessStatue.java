package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

/**
 * Created by Joseph on 6/8/2020.
 */
public class TileEntityGoddessStatue extends ModTileEntity {
	
	public TileEntityGoddessStatue() {
	}
	
	@Override
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		ExtendedPlayer cap = player.getCapability(ExtendedPlayer.CAPABILITY, null);
		if (!cap.getCurses().isEmpty())
			cap.getCurses().clear();
		return super.activate(world, pos, player, hand, face);
	}
}
