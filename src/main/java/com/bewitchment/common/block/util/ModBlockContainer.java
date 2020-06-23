package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import com.bewitchment.common.block.BlockWitchesAltar;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesCauldron;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

@SuppressWarnings({"deprecation", "NullableProblems", "ConstantConditions"})
public abstract class ModBlockContainer extends BlockContainer {
	private final Object modInstance;
	private final int guiID;
	
	protected ModBlockContainer(Object modInstance, String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int guiID) {
		super(mat);
		Util.registerBlock(this, name, mat, sound, hardness, resistance, tool, 0);
		this.modInstance = modInstance;
		this.guiID = guiID;
	}
	
	protected ModBlockContainer(Object modInstance, String name, Block base, int guiID) {
		super(base.getDefaultState().getMaterial());
		Util.registerBlock(this, name, base);
		this.modInstance = modInstance;
		this.guiID = guiID;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops") && hasTileEntity(state) && world.getTileEntity(pos) instanceof ModTileEntity && !(world.getTileEntity(pos) instanceof TileEntityWitchesCauldron)) {
			ModTileEntity tile = (ModTileEntity) world.getTileEntity(pos);
			for (IItemHandler inventory : tile.getInventories())
				for (int i = 0; i < inventory.getSlots(); i++)
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), inventory.getStackInSlot(i));
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
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		return (!Util.isTransparent(state) || world.getBlockState(pos.offset(face)).getBlock() != this) && super.shouldSideBeRendered(state, world, pos, face);
	}
	
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.UNDEFINED;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (!player.isSneaking()) {
			if (guiID > -1) {
				player.openGui(modInstance, guiID, world, pos.getX(), pos.getY(), pos.getZ());
				return true;
			}
			if (world.getTileEntity(pos) instanceof ModTileEntity) return ((ModTileEntity) world.getTileEntity(pos)).activate(world, pos, player, hand, face);
		}
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		refreshAltarPos(world, pos);
	}
	
	@Override
	public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.getMaterial() == Material.WOOD;
	}
	
	@Override
	public boolean isWood(IBlockAccess world, BlockPos pos) {
		return world.getBlockState(pos).getMaterial() == Material.WOOD;
	}
	
	public void refreshAltarPos(IBlockAccess world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityAltarStorage) ((TileEntityAltarStorage) tile).altarPos = BlockWitchesAltar.getNearestAltarPos(world, pos);
	}
}