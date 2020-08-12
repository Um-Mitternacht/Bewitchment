package com.bewitchment.common.block;

import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.block.tile.entity.TileEntityPlacedItem;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesAltar;
import com.bewitchment.common.block.util.ModBlockContainer;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.block.state.pattern.FactoryBlockPattern;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
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
	public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 2), COLOR = PropertyInteger.create("color", 0, 16);

	private final BlockPattern altarPattern = FactoryBlockPattern.start().aisle("AAAAA", "ABBBA", "ABBBA", "AAAAA").where('A', s -> s != null && !(s.getBlockState().getBlock() instanceof BlockWitchesAltar)).where('B', s -> s != null && s.getBlockState().getBlock() == this).build();

	public BlockWitchesAltar(String name, Block base) {
		super(null, name, base, -1);
		setDefaultState(getBlockState().getBaseState().withProperty(TYPE, 0).withProperty(COLOR, 0));
	}

	public static TileEntityWitchesAltar getAltar(IBlockAccess world, BlockPos pos) {
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-1, 0, -1), pos.add(1, 0, 1))) {
			TileEntity tile = world.getTileEntity(pos0);
			if (tile instanceof TileEntityWitchesAltar) return (TileEntityWitchesAltar) tile;
		}
		return null;
	}

	public static BlockPos getNearestAltarPos(IBlockAccess world, BlockPos pos) {
		if (world.getBlockState(pos).getBlock() instanceof BlockWitchesAltar) {
			TileEntityWitchesAltar altar = getAltar(world, pos);
			if (altar != null) return altar.getPos();
		}
		int radius = 24;
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			if (world.getBlockState(pos0).getBlock() instanceof BlockWitchesAltar) {
				TileEntityWitchesAltar altar = getAltar(world, pos0);
				if (altar != null) return altar.getPos();
			}
		}
		return null;
	}

	private static void refreshNearby(IBlockAccess world, BlockPos pos) {
		int radius = 24;
		for (BlockPos pos0 : BlockPos.getAllInBoxMutable(pos.add(-radius, -radius, -radius), pos.add(radius, radius, radius))) {
			Block block = world.getBlockState(pos0).getBlock();
			if (block instanceof ModBlockContainer) ((ModBlockContainer) block).refreshAltarPos(world, pos0);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return meta == 2 ? new TileEntityWitchesAltar() : null;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		for (EnumFacing face : EnumFacing.HORIZONTALS) {
			BlockPos offset = pos.offset(face);
			if (world.getBlockState(offset).getBlock() instanceof BlockWitchesAltar) {
				int type = world.getBlockState(offset).getValue(TYPE);
				if (type > 0) {
					world.setBlockState(offset, getDefaultState().withProperty(TYPE, 0));
					if (type == 2) refreshNearby(world, pos);
					breakBlock(world, offset, world.getBlockState(offset));
				}
			}
		}
		super.breakBlock(world, pos, state);
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
	public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
		return BlockFaceShape.SOLID;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		if (altarPattern.match(world, pos) != null) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.isEmpty()) {
				TileEntityWitchesAltar tile = getAltar(world, pos);
				if (tile != null) {
					MagicPower cap = tile.getCapability(MagicPower.CAPABILITY, null);
					if (!world.isRemote)
						player.sendStatusMessage(new TextComponentTranslation("altar.power_info", cap.getAmount(), cap.getMaxAmount(), tile.gain), true);
				}
			} else {
				if (face == EnumFacing.UP) {
					if (stack.getItem() == Item.getItemFromBlock(Blocks.CARPET)) {
						TileEntityWitchesAltar tile = getAltar(world, pos);
						ItemStack carpet = ItemStack.EMPTY;
						if (tile != null) carpet = new ItemStack(Blocks.CARPET, 1, tile.color - 1);
						if (createAltar(world, pos, stack.getMetadata() + 1)) {
							if (!player.isCreative()) stack.shrink(1);
							if (!world.isRemote && !carpet.isEmpty())
								InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY() + 0.75, pos.getZ(), carpet);
							return true;
						}
					} else {
						if (stack.getItem() instanceof ItemBlock || stack.getItem() instanceof ItemSkull) return false;
						else if (world.getBlockState(pos.up()).getBlock().isReplaceable(world, pos.up())) {
							world.setBlockState(pos.up(), ModObjects.placed_item.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.fromAngle(player.rotationYaw)));
							if (world.getTileEntity(pos.up()) instanceof TileEntityPlacedItem)
								((TileEntityPlacedItem) world.getTileEntity(pos.up())).getInventories()[0].insertItem(0, stack.splitStack(1), false);
							forceScan(world, pos);
							return true;
						}
					}
				}
			}
			return false;
		}
		return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
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
	public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
		TileEntityWitchesAltar tile = getAltar(world, pos);
		return tile == null ? state : state.withProperty(COLOR, tile.color);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
		super.neighborChanged(state, world, to, block, from);
		forceScan(world, to);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		for (EnumFacing face : EnumFacing.VALUES) {
			BlockPos pos0 = pos.offset(face);
			IBlockState state = world.getBlockState(pos0);
			if (state.getBlock() instanceof BlockWitchesAltar && state.getValue(TYPE) > 0) return false;
			if (face.getAxis() != EnumFacing.Axis.Y) {
				state = world.getBlockState(pos0.offset(face.rotateY()));
				if (state.getBlock() instanceof BlockWitchesAltar && state.getValue(TYPE) > 0) return false;
			}
		}
		return super.canPlaceBlockAt(world, pos);
	}

	@Override
	public EnumPushReaction getPushReaction(IBlockState state) {
		return EnumPushReaction.BLOCK;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, COLOR);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

	public boolean createAltar(World world, BlockPos pos, int color) {
		BlockPattern.PatternHelper helper = altarPattern.match(world, pos);
		if (helper != null) {
			for (int i = 1; i < helper.getWidth() - 1; i++) {
				for (int j = 1; j < helper.getHeight() - 1; j++) {
					BlockPos pos0 = helper.translateOffset(i, j, 0).getPos();
					if (world.getBlockState(pos0).getBlock() instanceof BlockWitchesAltar) {
						world.setBlockState(pos0, world.getBlockState(pos0).withProperty(TYPE, i == 2 && j == 2 ? 2 : 1), 2);
						TileEntity tile = world.getTileEntity(pos0);
						if (tile instanceof TileEntityWitchesAltar) {
							TileEntityWitchesAltar altar = (TileEntityWitchesAltar) tile;
							altar.color = color;
							altar.syncToClient();
							refreshNearby(world, pos0);
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	private void forceScan(World world, BlockPos pos) {
		TileEntityWitchesAltar tile = getAltar(world, pos);
		if (tile != null) tile.forceScan();
	}
}