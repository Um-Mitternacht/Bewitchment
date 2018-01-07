package com.bewitchment.common.block.tools;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibGui;
import com.bewitchment.common.tile.TileEntityBarrel;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

import static net.minecraft.block.BlockHorizontal.FACING;

public class BlockBarrel extends BlockMod implements ITileEntityProvider {

	public static final PropertyWood WOOD_TYPE = new PropertyWood("wood", WoodType.class, Arrays.asList(WoodType.values()));


	private static final AxisAlignedBB bounding_box_NS = new AxisAlignedBB(0.1875D, 0.0D, 0.03125D, 0.8125D, 0.625D, 0.96875D);
	private static final AxisAlignedBB bounding_box_WE = new AxisAlignedBB(0.03125D, 0.0D, 0.1875D, 0.96875D, 0.625D, 0.8125D);

	public BlockBarrel(String id) {
		super(id, Material.WOOD);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.SOUTH).withProperty(WOOD_TYPE, WoodType.OAK));
		this.setHarvestLevel("axe", 0);
		this.setHardness(2.0f);
		this.setLightOpacity(0);
	}

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

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.HORIZONTALS[meta]);
	}

	@Override
	public boolean hasTileEntity() {
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBarrel();
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}

	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}

	@Override
	public boolean isBlockNormalCube(IBlockState state) {
		return false;
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(WOOD_TYPE, WoodType.values()[meta]);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, WOOD_TYPE);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te == null) return state;
		return state.withProperty(WOOD_TYPE, ((TileEntityBarrel) worldIn.getTileEntity(pos)).getType());
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) return true;
		TileEntityBarrel barrel = (TileEntityBarrel) world.getTileEntity(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
		ItemStack stack = player.getHeldItem(hand);
		if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
			IFluidHandlerItem itemHandler = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
			IFluidHandler barrelHandler = barrel.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			FluidStack fluidInItem = itemHandler.drain(Fluid.BUCKET_VOLUME, false);
			FluidStack fluidInBarrel = barrelHandler.drain(Fluid.BUCKET_VOLUME, false);
			if ((fluidInBarrel != null && fluidInBarrel.amount > 0) && (fluidInItem == null || fluidInItem.amount == 0 || (fluidInItem.isFluidEqual(fluidInBarrel) && fluidInItem.amount < Fluid.BUCKET_VOLUME))) {
				itemHandler.fill(barrelHandler.drain(Fluid.BUCKET_VOLUME, true), true);
				player.setHeldItem(hand, itemHandler.getContainer());
			} else if (fluidInItem != null && fluidInItem.amount > 0 && fluidInItem.getFluid() != null && (fluidInBarrel == null || fluidInBarrel.amount == 0 || (fluidInBarrel.amount < Fluid.BUCKET_VOLUME && fluidInBarrel.isFluidEqual(fluidInItem)))) {
				FluidStack fsin = itemHandler.drain(Fluid.BUCKET_VOLUME, true);
				if (fsin != null && fsin.amount > 0 && fsin.getFluid() != null) {
					barrelHandler.fill(fsin, true);
					player.setHeldItem(hand, itemHandler.getContainer());
					player.inventory.markDirty();
				}
			}
			return true;
		}

		player.openGui(Bewitchment.instance, LibGui.BARREL.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(this, 1, state.getActualState(world, pos).getValue(WOOD_TYPE).ordinal());
	}

	@Override
	public void registerModel() {
		for (WoodType wt : WoodType.values()) {
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

	//###########################################################################################################

	public static enum WoodType implements IStringSerializable {
		OAK, SPRUCE, BIRCH, JUNGLE, ACACIA, BIG_OAK, ELDER, JUNIPER, YEW;

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}

	}

	public static class PropertyWood extends PropertyEnum<WoodType> {
		protected PropertyWood(String name, Class<WoodType> valueClass, Collection<WoodType> allowedValues) {
			super(name, valueClass, allowedValues);
		}
	}


}
