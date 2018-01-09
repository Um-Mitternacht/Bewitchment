package com.bewitchment.common.block.tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.TileEntityGlyph;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCircleGlyph extends BlockMod implements ITileEntityProvider {
	
	protected static final AxisAlignedBB FLAT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0025D, 1.0D);
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyType TYPE = new PropertyType("type", GlyphType.class, Arrays.asList(GlyphType.values()));
	public static final PropertyInteger LETTER = PropertyInteger.create("letter", 0, 5);

	public BlockCircleGlyph(String id) {
		super(id, Material.GRASS);
		this.setDefaultState(this.blockState.getBaseState()
				.withProperty(FACING, EnumFacing.SOUTH)
				.withProperty(TYPE, GlyphType.NORMAL)
				.withProperty(LETTER, 0)
				);
		this.setHardness(5);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if (getStateFromMeta(meta).getValue(TYPE).equals(GlyphType.GOLDEN)) return new TileEntityGlyph();
		return null;
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState floor = worldIn.getBlockState(pos.down());
		return floor.getBlock().canPlaceTorchOnTop(floor, worldIn, pos);
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return FLAT_AABB;
	}
	
	@Override
	public boolean hasTileEntity(IBlockState state) {
		return state.getValue(TYPE).equals(GlyphType.GOLDEN);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		return new ArrayList<ItemStack>(0);
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}
	
	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return false;
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion) {
		return 100;
	}
	
	@Override
	public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos) {
		return PathNodeType.OPEN;
	}

	@Override
	public boolean isCollidable() {
		return true;
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
		return null;
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
		return super.canPlaceBlockOnSide(worldIn, pos, side) && side == EnumFacing.UP;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

    @Override
	public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState blockState, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }
    
	@Override
	public IBlockState getStateFromMeta(int meta) {
		int color = meta & 3;
		int dir = (meta>>2) & 3;
		return this.getDefaultState()
				.withProperty(TYPE, GlyphType.values()[color])
				.withProperty(FACING, EnumFacing.HORIZONTALS[dir]);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		int color = state.getValue(TYPE).ordinal();
		int dir = state.getValue(FACING).getHorizontalIndex();
		return (dir<<2)|color; //Bitwise that's DDCC, where DD is either 00=south, 01=... and CC is 00=normal, 01=golden...
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		int letter = Math.abs(pos.getX()+pos.getZ()*2)%6;
		return state.withProperty(LETTER, letter);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, TYPE, LETTER);
	}
	
	@Override
	public EnumPushReaction getMobilityFlag(IBlockState state) {
		return EnumPushReaction.DESTROY;
	}

    @Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		int pt = stateIn.getValue(TYPE).ordinal(); 
		double d0 = pos.getX() + 0.5D;
		double d1 = pos.getY() + 0.05D;
		double d2 = pos.getZ() + 0.5D;
		if (pt>1) {
			double spreadX = rand.nextGaussian()/3;
			double spreadZ = rand.nextGaussian()/3;
			worldIn.spawnParticle(pt==3?EnumParticleTypes.FLAME:EnumParticleTypes.PORTAL, d0+spreadX, d1, d2+spreadZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}
		if (pt==1) {
			TileEntityGlyph te = (TileEntityGlyph) worldIn.getTileEntity(pos);
			if (te.hasRunningRitual()) {
				double spreadX = rand.nextGaussian()*0.4;
				double spreadZ = rand.nextGaussian()*0.4;
				worldIn.spawnParticle(EnumParticleTypes.END_ROD,d0+spreadX,d1,d2+spreadZ,0,0.02+0.1*rand.nextDouble(),0,0,0,0);
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand.equals(EnumHand.OFF_HAND) || !playerIn.getHeldItem(hand).isEmpty()) return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
		if (state.getBlock().hasTileEntity(state)) {
			TileEntityGlyph te = (TileEntityGlyph) worldIn.getTileEntity(pos);
			if (te == null) {
				Bewitchment.logger.warn("Null TE on ritual");
				return false;
			}
			if (te.hasRunningRitual()) {
				te.stopRitual(playerIn);
			} else {
				te.startRitual(playerIn);
			}
		}
		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (!this.canPlaceBlockAt(worldIn, pos)) worldIn.destroyBlock(pos, false);
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = state.getValue(TYPE).ordinal();
		ItemStack stack = new ItemStack(ModItems.ritual_chalk, 1, meta);
		return stack;
	}
	
	@Override
	public void registerModel() {
	}
	
	//######################################################################################### PROPERTY STUFF
	
	public static class PropertyType extends PropertyEnum<GlyphType> {
		protected PropertyType(String name, Class<GlyphType> valueClass, Collection<GlyphType> allowedValues) {
			super(name, valueClass, allowedValues);
		}
	}
	
	public static enum GlyphType implements IStringSerializable {
		NORMAL, GOLDEN, ENDER, NETHER;
		
		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
		
	}
	

}
