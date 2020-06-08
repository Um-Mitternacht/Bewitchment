package com.bewitchment.common.block;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.common.block.tile.entity.TileEntityGoddessStatue;
import com.bewitchment.common.block.util.ModBlock;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 6/8/2020.
 */
public class BlockGoddessStatue extends ModBlock implements ITileEntityProvider {
	public BlockGoddessStatue() {
		super("goddess_statue", Material.ROCK, SoundType.STONE, 10000, 10000, "pickaxe", 10000);
	}
	
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityGoddessStatue();
	}
}
