package com.bewitchment.common.block.tools;

import com.bewitchment.api.state.StateProperties;
import com.bewitchment.api.state.enums.EnumWoodType;
import com.bewitchment.common.block.BlockModTileEntity;
import com.bewitchment.common.tile.TileEntityBarrel;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

import static net.minecraft.block.BlockHorizontal.FACING;

public class BlockBarrel extends BlockModTileEntity {
	private static final AxisAlignedBB bounding_box_NS = new AxisAlignedBB(0.1875D, 0.0D, 0.03125D, 0.8125D, 0.625D, 0.96875D);
	private static final AxisAlignedBB bounding_box_WE = new AxisAlignedBB(0.03125D, 0.0D, 0.1875D, 0.96875D, 0.625D, 0.8125D);

	public BlockBarrel(String id) {
		super(id, Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(StateProperties.WOOD_TYPE, EnumWoodType.OAK));
		this.setHarvestLevel("axe", 0);
		this.setHardness(2.0f);
		this.setLightOpacity(0);
	}

	@SuppressWarnings("deprecation")
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		EnumFacing f = state.getValue(FACING);
		if (f == EnumFacing.EAST || f == EnumFacing.WEST) return bounding_box_WE;
		return bounding_box_NS;
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.HORIZONTALS[meta]);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBarrel();
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(StateProperties.WOOD_TYPE, EnumWoodType.values()[meta]);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, StateProperties.WOOD_TYPE);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null) return state;
		return state.withProperty(StateProperties.WOOD_TYPE, ((TileEntityBarrel) worldIn.getTileEntity(pos)).getType());
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		((TileEntityBarrel) worldIn.getTileEntity(pos)).setType(stack.getMetadata());
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return false;
	}

	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}


	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, state.getActualState(world, pos).getValue(StateProperties.WOOD_TYPE).ordinal());
	}

	@Override
	public void registerModel() {
		for (EnumWoodType wt : EnumWoodType.values()) {
			ModelResourceLocation modelResourceLocation = new ModelResourceLocation(this.getRegistryName() + "_" + wt.getName(), "inventory");
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), wt.ordinal(), modelResourceLocation);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		if (((TileEntityBarrel) world.getTileEntity(pos)).hasRecipe()) {
			EnumFacing f = state.getValue(FACING);
			double dz = f == EnumFacing.NORTH ? 1 : f == EnumFacing.SOUTH ? 0 : 0.5;
			double dx = f == EnumFacing.EAST ? 0 : f == EnumFacing.WEST ? 1 : 0.5;
			world.spawnParticle(EnumParticleTypes.SPELL, pos.getX() + dx, pos.getY() + 0.3, pos.getZ() + dz, 0, 0.1, 0);
		}
	}
}
