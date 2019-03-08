package com.bewitchment.common.item.block;

import com.bewitchment.common.block.head.BlockAnimalSkull;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.tile.tiles.TileEntityHead;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author its_meow
 *
 * Mar 7, 2019
 */
public class ItemBlockSkull extends ItemBlock {

	public final boolean allowFloor;
	public final int typeNum;

	public ItemBlockSkull(Block block, boolean allowFloor, int typeNum) {
		super(block);
		if(block.getRegistryName() != null) {
			this.setRegistryName(block.getRegistryName());
		}
		this.setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB);
		this.allowFloor = allowFloor;
		this.typeNum = typeNum;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
		Block block = world.getBlockState(pos).getBlock();

		if (block == Blocks.SNOW_LAYER && block.isReplaceable(world, pos))
			side = EnumFacing.UP;
		else if (!block.isReplaceable(world, pos))
			pos = pos.offset(side);

		return canBlockBePlaced(world, block, pos, false, side, null, stack);
	}

	public boolean canBlockBePlaced(World world, Block blockToPlace, BlockPos pos, boolean useBounds, EnumFacing side, Entity entity, ItemStack stack) {
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		AxisAlignedBB bb = useBounds ? null : state.getBoundingBox(world, pos);
		if (bb != null && !world.checkNoEntityCollision(bb, entity))
			return false;
		return block.isReplaceable(world, pos) && blockToPlace.canPlaceBlockAt(world, pos);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand,
			EnumFacing side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if (side == EnumFacing.DOWN || (side == EnumFacing.UP && !this.allowFloor)) {
			return EnumActionResult.FAIL;
		} else {
			BlockPos clickedPos = pos.offset(side);
			IBlockState clickedState = world.getBlockState(clickedPos);
			if(!clickedState.getBlock().isReplaceable(world, clickedPos)) {
				return EnumActionResult.FAIL;
			}
			if (!world.isRemote) {
				world.setBlockState(clickedPos, this.block.getDefaultState().withProperty(BlockAnimalSkull.FACING, side), 3);

				TileEntity tile = world.getTileEntity(clickedPos);
				populateTile(stack, side, player, tile);
			}
			if(!player.capabilities.isCreativeMode) {
				stack.shrink(1);
			}
			return EnumActionResult.SUCCESS;
		}
}

	protected void populateTile(ItemStack stack, EnumFacing side, EntityPlayer player, TileEntity tile) {
		if(tile instanceof TileEntityHead) {
			TileEntityHead tileSkull = (TileEntityHead) tile;
			float rotation = 0;
			if(side == EnumFacing.UP || side == EnumFacing.DOWN) {
				rotation = EnumFacing.fromAngle(player.rotationYawHead).getHorizontalAngle();
			} else {
				rotation = (int) side.getHorizontalAngle();
			}
			tileSkull.setRotation(rotation);
			tileSkull.setType(typeNum);
		}
	}

}
