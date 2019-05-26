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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

@SuppressWarnings({"NullableProblems", "deprecation", "ConstantConditions"})
public class BlockWitchesAltar extends ModBlockContainer {
	// type 0 = unformed, type 1 = formed, type 2 = tile
	private static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 2);
	
	private final BlockPattern altarPattern = FactoryBlockPattern.start().aisle("?AAA?", "ABBBA", "ABBBA", "?AAA?").where('?', s -> s != null && BlockStateMatcher.ANY.apply(s.getBlockState())).where('A', s -> s != null && !(s.getBlockState().getBlock() instanceof BlockWitchesAltar)).where('B', s -> s != null && s.getBlockState().getBlock() == this).build();
	
	public BlockWitchesAltar(String name, Block base) {
		super(null, name, base, -1);
		setDefaultState(getBlockState().getBaseState().withProperty(TYPE, 0));
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return meta == 2 ? new TileEntityWitchesAltar() : null;
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
		if (!world.isRemote && !player.isSneaking()) {
			ItemStack stack = player.getHeldItem(hand);
			if (altarPattern.match(world, pos) != null) {
				if (face == EnumFacing.UP && stack.getItem() == Item.getItemFromBlock(Blocks.CARPET) && changeAltar(world, pos, false)) return true;
				else if (stack.isEmpty()) {
					MagicPower cap = world.getTileEntity(getNearestAltar(world, pos)).getCapability(MagicPower.CAPABILITY, null);
					if (cap != null) {
						player.sendStatusMessage(new TextComponentTranslation("altar.powerinfo"), true);
					}
				}
			}
		}
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if (state.getValue(TYPE) == 2) refreshNearby(world, pos);
		for (EnumFacing face : EnumFacing.HORIZONTALS) {
			IBlockState state0 = world.getBlockState(pos.offset(face));
			if (state0.getBlock() instanceof BlockWitchesAltar && state0.getValue(TYPE) > 0) {
				world.setBlockState(pos.offset(face), getDefaultState());
				breakBlock(world, pos.offset(face), state0);
			}
		}
		super.breakBlock(world, pos, state);
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
		return new BlockStateContainer(this, TYPE);
	}
	
	public static BlockPos getNearestAltar(World world, BlockPos pos) {
		if (pos != null) {
			int radius = 8;
			for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
				IBlockState state = world.getBlockState(pos0);
				if (state.getBlock() instanceof BlockWitchesAltar) {
					for (EnumFacing face : EnumFacing.HORIZONTALS) {
						BlockPos offset = pos0.offset(face);
						if (world.getBlockState(offset).getBlock() instanceof BlockWitchesAltar) {
							BlockPos offset0 = offset.offset(face);
							while (world.getBlockState(offset0).getBlock() instanceof BlockWitchesAltar) {
								if (world.getBlockState(offset0).getValue(TYPE) == 2) return offset0;
								offset0 = offset0.offset(face);
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private void refreshNearby(World world, BlockPos pos) {
		int radius = 8;
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius)))
			if (world.getBlockState(pos0).getBlock() instanceof ModBlockContainer) ((ModBlockContainer) world.getBlockState(pos0).getBlock()).refreshAltarPos(world, pos0);
	}
	
	private boolean changeAltar(World world, BlockPos pos, boolean hasCenter) {
		for (EnumFacing face : EnumFacing.HORIZONTALS) {
			BlockPos offset = pos.offset(face);
			if (world.getBlockState(offset).getBlock() instanceof BlockWitchesAltar && world.getBlockState(offset).getValue(TYPE) == 0) {
				world.setBlockState(offset, getDefaultState().withProperty(TYPE, hasCenter ? 1 : 2));
				return changeAltar(world, offset, true);
			}
		}
		if (!hasCenter) refreshNearby(world, pos);
		return true;
	}
}