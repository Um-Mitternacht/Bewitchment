package com.bewitchment.common.block;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Joseph on 6/8/2020.
 */
public class BlockGoddessStatue extends ModBlock {
	private boolean cursed;
	
	public BlockGoddessStatue() {
		super("goddess_statue", Material.ROCK, SoundType.STONE, 10000, 10000, "pickaxe", 10000);
	}
	
	//Remind me never to do this awfulness again. This works but good god. This looks like I huffed a bunch of cocaine and proceeded to slam my face on the keyboard.
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ExtendedPlayer ep = playerIn.getCapability(ExtendedPlayer.CAPABILITY, null);
		playerIn.world.playSound(null, playerIn.getPosition(), SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 1, 1);
		
		List<Curse> curses = ep.getCurses();
		if (cursed) return true;
		for (Curse curse : curses) {
			if (playerIn.getRNG().nextDouble() < (1.0)) ep.removeCurse(curse);
		}
		
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
}
