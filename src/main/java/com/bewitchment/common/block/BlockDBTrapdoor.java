package com.bewitchment.common.block;

import com.bewitchment.common.block.tile.entity.TileEntityDBDoor;
import com.bewitchment.common.block.util.ModBlockTrapdoor;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class BlockDBTrapdoor extends ModBlockTrapdoor implements ITileEntityProvider {
	public BlockDBTrapdoor() {
		super("dragons_blood_trapdoor", ModObjects.dragons_blood_planks);
	}

	@Nullable
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityDBDoor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		if (worldIn.getTileEntity(pos) instanceof TileEntityDBDoor) {
			TileEntityDBDoor te = (TileEntityDBDoor) worldIn.getTileEntity(pos);
			if (!te.handler.getStackInSlot(0).isEmpty()) worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0);
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (world.getTileEntity(pos) instanceof TileEntityDBDoor) ((TileEntityDBDoor) world.getTileEntity(pos)).activate(world, pos, player, hand, face);
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}

	@Nullable
	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityDBDoor();
	}
}
