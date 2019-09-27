package com.bewitchment.api.registry.item;

import com.bewitchment.common.block.tile.entity.TileEntityIdol;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("deprecation")
public class ItemIdol extends Item {
	private final Block block;
	
	public ItemIdol(String blockName, Block base) {
		super();
		Block block = new BlockIdol("block_" + blockName, base, this);
		this.block = block;
		ForgeRegistries.BLOCKS.register(block);
	}
	
	@Override
	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
		ItemStack stack = player.getHeldItem(hand);
		if (player.canPlayerEdit(pos0, face, stack) && world.mayPlace(world.getBlockState(pos0).getBlock(), pos0, false, face, player) && block.canPlaceBlockAt(world, pos0)) {
			world.setBlockState(pos0, block.getStateForPlacement(world, pos, face, hitX, hitY, hitZ, 0, player, hand));
			world.setBlockToAir(pos0.up());
			TileEntity tile = world.getTileEntity(pos0);
			if (tile instanceof TileEntityIdol) {
				((TileEntityIdol) tile).getInventories()[0].insertItem(0, stack.copy().splitStack(1), false);
				((TileEntityIdol) tile).syncToClient();
			}
			stack.shrink(1);
			world.playSound(null, pos, block.getSoundType().getPlaceSound(), SoundCategory.BLOCKS, 1, 1);
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.FAIL;
	}
	
	@SuppressWarnings({"NullableProblems", "deprecation"})
	public static class BlockIdol extends ModBlockContainer {
		private static final AxisAlignedBB BOX = FULL_BLOCK_AABB.expand(0, 1, 0);
		
		private final Item item;
		
		private BlockIdol(String name, Block base, Item item) {
			super(null, name, base, -1);
			setLightOpacity(0);
			this.item = item;
		}
		
		@Nullable
		@Override
		public TileEntity createNewTileEntity(World world, int meta) {
			return new TileEntityIdol();
		}
		
		@Override
		public boolean isFullBlock(IBlockState state) {
			return false;
		}
		
		@Override
		public boolean isFullCube(IBlockState state) {
			return false;
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
			return BOX;
		}
		
		@Override
		public Item getItemDropped(IBlockState state, Random rand, int fortune) {
			return Items.AIR;
		}
		
		@Override
		public boolean canPlaceBlockAt(World world, BlockPos pos) {
			return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
		}
		
		@Override
		public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
			return new ItemStack(item);
		}
		
		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, BlockHorizontal.FACING);
		}
		
		@Override
		public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
			return false;
		}
		
		@Override
		public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing face, float hitX, float hitY, float hitZ, int meta, EntityLivingBase living, EnumHand hand) {
			return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(living.rotationYaw).getOpposite());
		}
	}
}