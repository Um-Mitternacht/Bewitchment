package com.bewitchment.common.block;

import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.common.block.tile.entity.TileEntityPoppetShelf;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings({"NullableProblems", "deprecation"})
public class BlockPoppetShelf extends ModBlockContainer {
	private static final AxisAlignedBB BOX_S = new AxisAlignedBB(0, 0, 0, 1, 1, 4 / 16d), BOX_E = new AxisAlignedBB(0, 0, 0, 4 / 16d, 1, 1), BOX_N = new AxisAlignedBB(0, 0, 1, 1, 1, 12 / 16d), BOX_W = new AxisAlignedBB(1, 0, 0, 12 / 16d, 1, 1);
	
	public BlockPoppetShelf(String type) {
		super(null, "poppet_shelf_" + type, Blocks.PLANKS, -1);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPoppetShelf();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		switch (state.getValue(BlockHorizontal.FACING).getHorizontalIndex()) {
			case 0:
				return BOX_S;
			case 1:
				return BOX_W;
			case 2:
				return BOX_N;
			case 3:
				return BOX_E;
		}
		return null;
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, BlockHorizontal.FACING);
	}
	
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
		return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw).getOpposite());
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityPoppetShelf) {
			TileEntityPoppetShelf tile = (TileEntityPoppetShelf) world.getTileEntity(pos);
			IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
			for (int slot = 0; slot < handler.getSlots(); slot++) {
				ItemStack stack = handler.getStackInSlot(slot);
				InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
			}
			ExtendedWorld ext = ExtendedWorld.get(world);
			for (NBTTagCompound poppet : ext.storedPoppetShelves) {
				if (poppet.getInteger("dimension") == world.provider.getDimension() && poppet.getLong("position") == pos.toLong()) {
					ext.storedPoppetShelves.remove(poppet);
					ext.markDirty();
					break;
				}
			}
		}
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		int gridPos = getGridPosition(state, hitX, hitY, hitZ);
		if (!world.isRemote && hand == EnumHand.MAIN_HAND && world.getTileEntity(pos) instanceof TileEntityPoppetShelf) {
			TileEntityPoppetShelf tile = (TileEntityPoppetShelf) world.getTileEntity(pos);
			tile.interact(player, gridPos);
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		NBTTagCompound poppet = new NBTTagCompound();
		poppet.setLong("position", pos.toLong());
		poppet.setInteger("dimension", placer.dimension);
		ExtendedWorld ext = ExtendedWorld.get(world);
		ext.storedPoppetShelves.add(poppet);
		ext.markDirty();
		super.onBlockPlacedBy(world, pos, state, placer, stack);
	}
	
	private int getGridPosition(IBlockState state, float hitX, float hitY, float hitZ) {
		final int[][] GRID = {{6, 7, 8}, {3, 4, 5}, {0, 1, 2}};
		float x = 0, y = 0;
		switch (state.getValue(BlockHorizontal.FACING).getHorizontalIndex()) {
			case 0:
				x = hitX;
				y = hitY;
				break;
			case 2:
				x = 1 - hitX;
				y = hitY;
				break;
			case 1:
				x = hitZ;
				y = hitY;
				break;
			case 3:
				x = 1 - hitZ;
				y = hitY;
				break;
		}
		int xPos = (int) Math.floor((double) x * 3);
		int yPos = (int) Math.floor((double) y * 3);
		if (xPos >= 2) xPos = 2;
		if (yPos >= 2) yPos = 2;
		return GRID[yPos][xPos];
	}
}
