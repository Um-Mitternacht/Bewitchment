package com.bewitchment.common.block;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Joseph on 6/8/2020.
 */
public class BlockGoddessStatue extends ModBlock {
	public BlockGoddessStatue() {
		super("goddess_statue", Material.ROCK, SoundType.STONE, 10000, 10000, "pickaxe", 10000);
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		super.onBlockClicked(worldIn, pos, playerIn);
		ExtendedPlayer cap = playerIn.getCapability(ExtendedPlayer.CAPABILITY, null);
		if (!cap.getCurses().isEmpty())
			cap.getCurses().clear();
	}
}
