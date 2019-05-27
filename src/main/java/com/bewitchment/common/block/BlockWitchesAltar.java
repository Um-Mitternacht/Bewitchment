package com.bewitchment.common.block;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesAltar;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.BlockStateMatcher;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"NullableProblems", "deprecation", "ConstantConditions", "WeakerAccess"})
public class BlockWitchesAltar extends ModBlockContainer {
	// type 0 = unformed, type 1 = formed, type 2 = tile
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 2), COLOR = PropertyInteger.create("color", 0, 15);
	
	private final BlockPattern altarPattern = FactoryBlockPattern.start().aisle("?AAA?", "ABBBA", "ABBBA", "?AAA?").where('?', s -> s != null && BlockStateMatcher.ANY.apply(s.getBlockState())).where('A', s -> s != null && !(s.getBlockState().getBlock() instanceof BlockWitchesAltar)).where('B', s -> s != null && s.getBlockState().getBlock() == this).build();
	
	public BlockWitchesAltar(String name, Block base) {
		super(null, name, base, -1);
		setDefaultState(getBlockState().getBaseState().withProperty(TYPE, 0).withProperty(COLOR, 0));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return meta == 2 ? new TileEntityWitchesAltar() : null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}
	
	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		for (EnumFacing face : EnumFacing.HORIZONTALS) {
			IBlockState state = world.getBlockState(pos.offset(face));
			if (state.getBlock() instanceof BlockWitchesAltar && state.getValue(TYPE) != 0) return false;
		}
		return true;
	}
	
	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (!world.isRemote && !player.isSneaking() && hand == EnumHand.MAIN_HAND) {
			ItemStack stack = player.getHeldItem(hand);
			if (altarPattern.match(world, pos) != null) {
				if (face == EnumFacing.UP && stack.getItem() == Item.getItemFromBlock(Blocks.CARPET)) {
					refreshAltar(world, pos, stack.getMetadata());
					if (!player.isCreative()) stack.shrink(1);
					return true;
				}
				else if (stack.isEmpty()) {
					TileEntityWitchesAltar tile = getNearestAltar(world, pos);
					if (tile != null) {
						MagicPower cap = tile.getCapability(MagicPower.CAPABILITY, null);
						// put gain here
						player.sendStatusMessage(new TextComponentTranslation("altar.powerinfo", cap.amount, cap.maxAmount, 1), true);
					}
				}
			}
		}
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		destroyAltar(world, pos);
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntityWitchesAltar tile = getNearestAltar(world, pos);
		return tile == null ? state : state.withProperty(COLOR, tile.color);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TYPE, meta);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE);
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, COLOR);
	}
	
	public static TileEntityWitchesAltar getNearestAltar(IBlockAccess world, BlockPos pos) {
		BlockPos altarPos = getNearestAltarPos(world, pos);
		if (altarPos != null) {
			TileEntity tile = world.getTileEntity(altarPos);
			if (tile instanceof TileEntityWitchesAltar) return (TileEntityWitchesAltar) tile;
		}
		return null;
	}
	
	public static BlockPos findAltarPos(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof BlockWitchesAltar) {
			int type = world.getBlockState(pos).getValue(TYPE);
			if (type > 0) {
				if (type == 2) return pos;
				for (EnumFacing face : EnumFacing.HORIZONTALS) {
					BlockPos offset = pos.offset(face);
					while (world.getBlockState(offset).getBlock() instanceof BlockWitchesAltar) {
						if (world.getBlockState(offset).getValue(TYPE) == 2) return offset;
						offset = offset.offset(face);
					}
				}
			}
		}
		return null;
	}
	
	public static BlockPos getNearestAltarPos(IBlockAccess world, BlockPos pos) {
		if (pos != null) {
			if (world.getBlockState(pos).getBlock() instanceof BlockWitchesAltar && world.getBlockState(pos).getValue(TYPE) == 2) return pos;
			BlockPos checkNear = findAltarPos(world, pos);
			if (checkNear != null) return checkNear;
			int radius = 8;
			for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
				BlockPos altar = findAltarPos(world, pos0);
				if (altar != null) return altar;
			}
		}
		return null;
	}
	
	public static void createAltar(World world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof BlockWitchesAltar) {
			if (world.getBlockState(pos).getValue(TYPE) == 0) {
				world.setBlockState(pos, world.getBlockState(pos).withProperty(TYPE, 2), 2);
				refreshNearby(world, pos);
			}
			for (EnumFacing face : EnumFacing.HORIZONTALS) {
				BlockPos offset = pos.offset(face);
				if (world.getBlockState(offset).getBlock() instanceof BlockWitchesAltar && world.getBlockState(offset).getValue(TYPE) == 0) {
					world.setBlockState(offset, world.getBlockState(offset).withProperty(TYPE, 1), 2);
					createAltar(world, offset);
				}
			}
		}
	}
	
	public static void destroyAltar(World world, BlockPos pos) {
		for (EnumFacing face : EnumFacing.HORIZONTALS) {
			BlockPos offset = pos.offset(face);
			if (world.getBlockState(offset).getBlock() instanceof BlockWitchesAltar) {
				int type = world.getBlockState(offset).getValue(TYPE);
				if (world.getBlockState(offset).getBlock() instanceof BlockWitchesAltar && type > 0) {
					if (type == 2) refreshNearby(world, offset);
					world.setBlockState(offset, world.getBlockState(offset).withProperty(TYPE, 0), 2);
					world.removeTileEntity(offset);
					destroyAltar(world, offset);
				}
			}
		}
	}
	
	public static void refreshAltar(World world, BlockPos pos, int color) {
		TileEntityWitchesAltar tile = getNearestAltar(world, pos);
		int amount = 0, maxAmount = 0;
		if (tile != null) {
			MagicPower cap = tile.getCapability(MagicPower.CAPABILITY, null);
			amount = cap.amount;
			maxAmount = cap.maxAmount;
		}
		destroyAltar(world, pos);
		createAltar(world, pos);
		tile = getNearestAltar(world, pos);
		if (tile != null) {
			MagicPower cap = tile.getCapability(MagicPower.CAPABILITY, null);
			cap.amount = amount;
			cap.maxAmount = maxAmount;
			tile.color = color;
			tile.markDirty();
		}
	}
	
	private static void refreshNearby(IBlockAccess world, BlockPos pos) {
		int radius = 8;
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			Block block = world.getBlockState(pos0).getBlock();
			if (block instanceof ModBlockContainer) ((ModBlockContainer) block).refreshAltarPos(world, pos0);
		}
	}
}