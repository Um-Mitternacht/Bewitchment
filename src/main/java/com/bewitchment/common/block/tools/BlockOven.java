package com.bewitchment.common.block.tools;

import static net.minecraft.block.BlockHorizontal.FACING;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.TileOven;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Joseph on 3/4/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BlockOven extends BlockMod implements ITileEntityProvider {

	//Todo: Add functionality.


	public BlockOven() {
		super(LibBlockName.OVEN, Material.IRON);
		setDefaultState(defaultState().withProperty(FACING, EnumFacing.NORTH));
		setSound(SoundType.METAL);
		setResistance(3F);
		setHardness(3F);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		final EnumFacing facing = EnumFacing.getHorizontal(meta);
		return getDefaultState().withProperty(FACING, facing);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		final EnumFacing facing = state.getValue(FACING);
		return facing.getHorizontalIndex();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			final TileEntity tile1 = worldIn.getTileEntity(pos);
			if (tile1 == null || !(tile1 instanceof TileOven)) return false;

			ItemStack heldItem = playerIn.getHeldItem(hand);
			if (!heldItem.isEmpty() && heldItem.getItem() == Items.NAME_TAG) {
				((TileOven) tile1).setCustomName(heldItem.getDisplayName());
			} else {
				playerIn.openGui(Bewitchment.instance, LibGui.OVEN, worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		final TileEntity tile1 = worldIn.getTileEntity(pos);
		if (tile1 != null && tile1 instanceof TileOven) {
			((TileOven) tile1).dropItems();
		}
		worldIn.removeTileEntity(pos);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		final EnumFacing enumfacing = EnumFacing.fromAngle(placer.rotationYaw);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileOven();
	}
}
