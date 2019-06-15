package com.bewitchment.common.block;

import com.bewitchment.common.block.tile.entity.TileEntityGlyph;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SuppressWarnings({"NullableProblems", "deprecation", "ConstantConditions"})
public class BlockGlyph extends ModBlockContainer {
	/**
	 * 0 = golden, 1 = normal, 2 = nether, 3 = ender, 4 = any
	 */
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 4);
	
	public static final int GOLDEN = 0, NORMAL = 1, NETHER = 2, ENDER = 3, ANY = 4;
	
	private static final PropertyInteger LETTER = PropertyInteger.create("letter", 0, 5);
	
	private static final AxisAlignedBB BOX = new AxisAlignedBB(0, 0, 0, 1, 0.0025, 1);
	
	public BlockGlyph() {
		super(null, "glyph", Material.CIRCUITS, SoundType.STONE, 5, 100, "", 0, -1);
		setCreativeTab(null);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityGlyph();
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return state.getValue(TYPE) == GOLDEN;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (state.getValue(TYPE) == GOLDEN) {
			TileEntityGlyph tile = ((TileEntityGlyph) world.getTileEntity(pos));
			ItemStack stack = player.getHeldItem(hand);
			if (!world.isRemote && stack.isEmpty()) {
				if (tile.ritual != null) tile.stopRitual(false);
				else tile.startRitual(player);
			}
			else if (stack.getItem() == ModObjects.waystone && stack.hasTagCompound() && stack.getTagCompound().hasKey("location")) {
				if (tile.ritual != null && tile.ritual.canBePerformedRemotely) {
					tile.effectivePos = BlockPos.fromLong(stack.getTagCompound().getLong("location"));
					tile.effectiveDim = stack.getTagCompound().getInteger("dimension");
					stack.damageItem(1, player);
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return BOX;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		return null;
	}
	
	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}
	
	@Override
	public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos) {
		return PathNodeType.OPEN;
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult result, World world, BlockPos pos, EntityPlayer player) {
		int type = state.getValue(TYPE);
		return new ItemStack(type == NORMAL ? ModObjects.ritual_chalk : type == GOLDEN ? ModObjects.focal_chalk : type == NETHER ? ModObjects.fiery_chalk : ModObjects.phasing_chalk);
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlockFaceShape(world, pos, EnumFacing.UP) == BlockFaceShape.SOLID;
	}
	
	@Override
	public boolean isReplaceable(IBlockAccess world, BlockPos pos) {
		return (world.getBlockState(pos).getBlock() != this || world.getBlockState(pos).getValue(TYPE) != GOLDEN);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
		int type = state.getValue(TYPE);
		double x = pos.getX() + 0.5, y = pos.getY() + 0.05, z = pos.getZ() + 0.5;
		if (type == NETHER) world.spawnParticle(EnumParticleTypes.FLAME, x + rand.nextGaussian() / 3, y, z + rand.nextGaussian() / 3, 0, 0, 0);
		if (type == ENDER) {
			world.spawnParticle(EnumParticleTypes.PORTAL, x + rand.nextGaussian() / 3, y, z + rand.nextGaussian() / 3, 0, 0, 0);
			world.spawnParticle(EnumParticleTypes.END_ROD, x + rand.nextGaussian() * 0.4, y, z + rand.nextGaussian() * 0.4, 0, 0.02 + 0.1 * rand.nextDouble(), 0);
		}
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		return state.withProperty(LETTER, Math.abs(pos.getX() + pos.getZ() * 2) % 6);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, meta & 3).withProperty(BlockHorizontal.FACING, EnumFacing.HORIZONTALS[(meta >> 2) & 3]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE) | (state.getValue(BlockHorizontal.FACING).getHorizontalIndex() << 2);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, BlockHorizontal.FACING, LETTER);
	}
}