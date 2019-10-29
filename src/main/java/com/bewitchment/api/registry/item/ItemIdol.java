package com.bewitchment.api.registry.item;

import com.bewitchment.common.block.tile.entity.TileEntityIdol;
import com.bewitchment.common.block.util.ModBlock;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
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
	private final BlockIdol block;
	private final int height;
	
	public ItemIdol(String blockName, Block base) {
		super();
		int height = 2;
		if (blockName.contains("statue")) {
			if (blockName.contains("herne") || blockName.contains("lilith")) height = 4;
			if (blockName.contains("leonard")) height = 3;
		}
		this.height = height;
		BlockIdol block = new BlockIdol("block_" + blockName, base, this, height);
		this.block = block;
		ForgeRegistries.BLOCKS.register(block);
	}
	
	@Override
	@Nonnull
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
		ItemStack stack = player.getHeldItem(hand);
		if (canPlace(player, world, pos, face, hand)) {
			world.setBlockState(pos0, block.getStateForPlacement(world, pos, face, hitX, hitY, hitZ, 0, player, hand));
			for (int i = 0; i < block.height - 1; i++) {
				world.setBlockState(pos0.up().up(i), ModObjects.filler.getStateFromMeta(i));
			}
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

	private boolean canPlace(EntityPlayer player, World world, BlockPos pos, EnumFacing face, EnumHand hand) {
		BlockPos pos0 = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(face);
		ItemStack stack = player.getHeldItem(hand);
		for (int i = 0; i < this.height - 1; i++) {
			if (!(player.canPlayerEdit(pos0.up(i), face, stack) && world.mayPlace(world.getBlockState(pos0.up(i)).getBlock(), pos0, false, face, player) && block.canPlaceBlockAt(world, pos0.up(i)))) {
				return false;
			}
		}
		return true;
	}
	
	@SuppressWarnings({"NullableProblems", "deprecation"})
	public static class BlockIdol extends ModBlockContainer {
		private final Item item;

		public int height;
		
		private BlockIdol(String name, Block base, Item item, int height) {
			super(null, name, base, -1);
			setLightOpacity(0);
			this.item = item;
			this.height = height;
		}
		
		@Nullable
		@Override
		public TileEntity createNewTileEntity(World world, int meta) {
			return new TileEntityIdol();
		}

		@Override
		public IBlockState getStateFromMeta(int meta) {
			return getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[meta]);
		}
		
		@Override
		public void breakBlock(World world, BlockPos pos, IBlockState state) {
			for (int i = 0; i < this.height; i++) {
				world.setBlockToAir(pos.up(i));
			}
			super.breakBlock(world, pos, state);
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
		public int getMetaFromState(IBlockState state) {
			return state.getValue(BlockHorizontal.FACING).getHorizontalIndex();
		}
		
		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
			return FULL_BLOCK_AABB.expand(0, height - 1, 0);
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

	public static class BlockFiller extends ModBlock {
		public static final PropertyInteger HEIGHT = PropertyInteger.create("height", 0, 5);

		public BlockFiller() {
			super("filler", Blocks.STONE);
		}

		@Override
		public boolean isOpaqueCube(IBlockState state) {
			return false;
		}

		private int getHeightAbove(BlockPos pos) {
			int height = 0;
			BlockPos pos0 = pos;
			while (Minecraft.getMinecraft().world.getBlockState(pos0.up()).getBlock() instanceof BlockFiller) {
				height++;
				pos0 = pos0.up();
			}
			return height;
		}		@Override
		public EnumBlockRenderType getRenderType(IBlockState state) {
			return EnumBlockRenderType.INVISIBLE;
		}

		private BlockPos getIdol(World world, BlockPos pos) {
			for (int i = 0; i < 5; i++) {
				if (world.getBlockState(pos.down(i)).getBlock() instanceof BlockIdol) return pos.down(i);
			}
			return null;
		}

		@Override
		public int quantityDropped(Random random) {
			return 0;
		}



		@Override
		public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
			return false;
		}


		@Override
		public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
			super.getSubBlocks(itemIn, items);
		}

		@Override
		public IBlockState getStateFromMeta(int meta) {
			return getDefaultState().withProperty(HEIGHT, meta);
		}

		@Override
		public int getMetaFromState(IBlockState state) {
			return state.getValue(HEIGHT);
		}

		@Override
		protected BlockStateContainer createBlockState() {
			return new BlockStateContainer(this, HEIGHT);
		}


		@Override
		public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
			int height = getHeightAbove(pos);
			return new AxisAlignedBB(0, -state.getValue(HEIGHT) - 1, 0, 1, height + 1, 1);
		}

		@Override
		public void breakBlock(World world, BlockPos pos, IBlockState state) {
			BlockPos idol = getIdol(world, pos);
			if (idol != null) {
				world.getBlockState(idol).getBlock().breakBlock(world, idol, world.getBlockState(idol));
			}
			else super.breakBlock(world, pos, state);
		}
	}
}