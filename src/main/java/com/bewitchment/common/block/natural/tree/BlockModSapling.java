/**
 * This class was created by <ZaBi94> on Dec 11th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.block.natural.tree;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

public class BlockModSapling extends BlockBush implements IGrowable, IModelRegister {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 3);
	public static final PropertySapling TYPE = new PropertySapling("type", EnumSaplingType.class, Arrays.asList(EnumSaplingType.values()));
	protected static final AxisAlignedBB SAPLING_AABB = new AxisAlignedBB(0.09999999403953552D, 0.0D, 0.09999999403953552D, 0.8999999761581421D, 0.800000011920929D, 0.8999999761581421D);

	public BlockModSapling(String id) {
		setTranslationKey(id);
		setRegistryName(LibMod.MOD_ID, id);
		setCreativeTab(ModCreativeTabs.PLANTS_CREATIVE_TAB);
		this.setTickRandomly(true);
		this.setSoundType(SoundType.PLANT);
		this.setDefaultState(blockState.getBaseState().withProperty(STAGE, 0).withProperty(TYPE, EnumSaplingType.ELDER));
	}

	private static void registerModel(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(new ResourceLocation(item.getRegistryName().toString() + "_" + EnumSaplingType.values()[meta].getName()), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	private static void generateElderTree(World world, BlockPos pos, Random r) {
		IBlockState leaves = ModBlocks.leaves_elder.getDefaultState();
		int h = generateTrunk(3, 5, ModBlocks.log_elder.getDefaultState(), world, pos, r);
		for (int dx = -2; dx < 3; dx++) {
			for (int dz = -2; dz < 3; dz++) {
				for (int dy = -2; dy < 1; dy++) {
					BlockPos current = pos.up(h).add(dx, dy, dz);
					if (isAirBlock(world, current) && ((Math.abs(dz) != 2 || Math.abs(dx) != 2) || r.nextDouble() < 0.2)) {
						if ((dy < 0 || (dx < 2 && dz < 2 && dx > -2 && dz > -2))) {
							world.setBlockState(current, leaves, 3);
						}
					}
				}
			}
		}
	}

	private static void generateJuniperTree(World world, BlockPos pos, Random r) {
		int h = generateTrunk(2, 4, ModBlocks.log_juniper.getDefaultState(), world, pos, r);
		EnumFacing branchOffset = EnumFacing.HORIZONTALS[r.nextInt(4)];
		BlockPos branching = pos.up(h).offset(branchOffset);
		IBlockState log = ModBlocks.log_juniper.getDefaultState().withProperty(BlockModLog.LOG_AXIS, EnumAxis.NONE);
		ArrayList<BlockPos> logs = new ArrayList<BlockPos>();
		if (isAirBlock(world, branching)) {
			world.setBlockState(branching, log, 3);
			logs.add(branching);
		}
		BlockPos other = branching.offset(branchOffset.getOpposite(), 2);
		if (isAirBlock(world, other)) {
			world.setBlockState(other, log, 3);
			logs.add(other);
		}
		for (int i = 0; i < h / 2; i++) {
			BlockPos current = branching.up().offset(branchOffset, i + 1);
			if (isAirBlock(world, current)) {
				logs.add(current);
				world.setBlockState(current, log, 3);
			}
		}

		IBlockState leaves = ModBlocks.leaves_juniper.getDefaultState();
		for (BlockPos p : logs) {
			for (EnumFacing f : EnumFacing.VALUES) {
				BlockPos lpos1 = p.offset(f);
				if (isAirBlock(world, lpos1))
					world.setBlockState(lpos1, leaves, 3);
				for (EnumFacing f2 : EnumFacing.VALUES)
					if (f2 != EnumFacing.DOWN) {
						BlockPos lpos = p.offset(f).offset(f2);
						if (isAirBlock(world, lpos) && r.nextDouble() < 0.8D)
							world.setBlockState(lpos, leaves, 3);
					}
			}
		}

	}

	private static void generateYewTree(World world, BlockPos pos, Random r) {
		int h1 = generateTrunk(4, 6, ModBlocks.log_yew.getDefaultState(), world, pos, r);
		int h2 = generateTrunk(4, 6, ModBlocks.log_yew.getDefaultState(), world, pos.east(), r);
		int h3 = generateTrunk(4, 6, ModBlocks.log_yew.getDefaultState(), world, pos.east().north(), r);
		int h4 = generateTrunk(4, 6, ModBlocks.log_yew.getDefaultState(), world, pos.north(), r);
		int hmin = Math.min(Math.min(h1, h2), Math.min(h3, h4));
		int hmax = Math.max(Math.max(h1, h2), Math.max(h3, h4));

		IBlockState leaves = ModBlocks.leaves_yew.getDefaultState();

		for (int dx = -2; dx < 4; dx++)
			for (int dz = -3; dz < 3; dz++)
				for (int dy = -2; dy < hmax - hmin + 2; dy++) {
					BlockPos current = pos.up(hmin).add(dx, dy, dz);
					if (isAirBlock(world, current) /* && ((Math.abs(dz)!=2 || Math.abs(dx)!=2) || r.nextDouble()<0.2) */) {
						if (dx == -2 || dx == 3 || dz == -3 || dz == 2) {
							if (r.nextDouble() < 0.1 || dy >= hmax - hmin)
								continue;
						}
						if (dx == -1 || dx == 2 || dz == -2 || dz == 1) {
							if (dy == hmax - hmin + 1)
								continue;
						}
						if (dx == -2 && dz == -3)
							continue;
						if (dx == -2 && dz == 2)
							continue;
						if (dx == 3 && dz == -3)
							continue;
						if (dx == 3 && dz == 2)
							continue;
						world.setBlockState(current, leaves, 3);
					}
				}
	}

	public static boolean canSaplingGrow(EnumSaplingType type, World world, BlockPos pos) {
		if (world.isRemote)
			return false;
		if (!isAirBlock(world, pos.up()))
			return false;
		if (type == EnumSaplingType.ELDER) {
			for (int dx = -1; dx < 2; dx++) {
				for (int dz = -1; dz < 2; dz++) {
					for (int dy = 0; dy < 1; dy++) {
						BlockPos current = pos.up(2).add(dx, dy, dz);
						if (!isAirBlock(world, current)) {
							return false;
						}
					}
				}
			}
		} else if (type == EnumSaplingType.JUNIPER) {
			for (int dx = -2; dx < 3; dx++) {
				for (int dz = -2; dz < 3; dz++) {
					for (int dy = 0; dy < 2; dy++) {
						BlockPos current = pos.up(2).add(dx, dy, dz);
						if (!isAirBlock(world, current)) {
							return false;
						}
					}
				}
			}
		} else if (type == EnumSaplingType.YEW) {
			if (world.getBlockState(pos.north()).getBlock() != ModBlocks.sapling || world.getBlockState(pos.north()).getValue(BlockModSapling.TYPE) != EnumSaplingType.YEW)
				return false;
			if (world.getBlockState(pos.east()).getBlock() != ModBlocks.sapling || world.getBlockState(pos.east()).getValue(BlockModSapling.TYPE) != EnumSaplingType.YEW)
				return false;
			if (world.getBlockState(pos.north().east()).getBlock() != ModBlocks.sapling || world.getBlockState(pos.north().east()).getValue(BlockModSapling.TYPE) != EnumSaplingType.YEW)
				return false;
			for (int dx = -2; dx < 3; dx++) {
				for (int dz = -2; dz < 3; dz++) {
					for (int dy = 0; dy < 3; dy++) {
						BlockPos current = pos.up(2).add(dx, dy, dz);
						if (!isAirBlock(world, current)) {
							return false;
						}
					}
				}
			}
		} else if (type == EnumSaplingType.CYPRESS) {
			for (int dx = -1; dx < 2; dx++) {
				for (int dz = -1; dz < 2; dz++) {
					for (int dy = 0; dy < 8; dy++) {
						BlockPos current = pos.up(2).add(dx, dy, dz);
						if (!isAirBlock(world, current)) {
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private static int generateTrunk(int minHeight, int maxHeight, IBlockState log, World world, BlockPos pos, Random r) {
		int h = minHeight + r.nextInt(maxHeight - minHeight + 1);
		for (int i = 0; i < h; i++) {
			if (isAirBlock(world, pos.up(i)) || i == 0) {
				world.setBlockState(pos.up(i), log, 3);
			}
		}
		return h;
	}

	private static boolean isAirBlock(World world, BlockPos current) {
		return world.getBlockState(current).getBlock().canBeReplacedByLeaves(world.getBlockState(current), world, current);
	}

	private static void generateCypressTree(World world, BlockPos pos, Random r) { // Todo: Make this like a cypress. This is just test gen for now, while I try and figure out tree gen
		IBlockState leaves = ModBlocks.leaves_cypress.getDefaultState();
		int h = generateTrunk(5, 13, ModBlocks.log_cypress.getDefaultState(), world, pos, r); // Run the bark all the way up the tree
		for (int dy = -h + 2; dy < 2; dy++) {
			boolean cross = dy <= -1;
			boolean core = dy > -1;
			boolean full = dy >= -h + 3 && dy <= -h / 2;
			for (int dx = -1; dx <= 1; dx++) {
				for (int dz = -1; dz <= 1; dz++) {
					BlockPos current = pos.up(h).add(dx, dy, dz);
					if (isAirBlock(world, current)) {
						if ((core && dz == 0 && dx == 0) || full || (cross && (dz == 0 || dx == 0))) {
							world.setBlockState(current, leaves, 3);
						}
					}
				}
			}
		}
	}

	@Override
	public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return SAPLING_AABB;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		items.add(new ItemStack(ModBlocks.sapling, 1, 0));
		items.add(new ItemStack(ModBlocks.sapling, 1, 1));
		items.add(new ItemStack(ModBlocks.sapling, 1, 2));
		items.add(new ItemStack(ModBlocks.sapling, 1, 3));
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
		return true;
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TYPE).ordinal();
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
		return worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World world, Random rand, BlockPos pos, IBlockState state) {
		if (canSaplingGrow(state.getValue(TYPE), world, pos)) {
			if (state.getValue(STAGE) < 3)
				world.setBlockState(pos, state.cycleProperty(STAGE), 3);
			else {
				generateTree(world, rand, pos, state);
			}
		} else {
			if (state.getValue(TYPE) == EnumSaplingType.YEW) {
				if (world.getBlockState(pos.south()).getBlock() == ModBlocks.sapling && world.getBlockState(pos.south()).getValue(TYPE) == EnumSaplingType.YEW) {
					((BlockModSapling) world.getBlockState(pos.south()).getBlock()).grow(world, rand, pos.south(), world.getBlockState(pos.south()));
					return;
				}
				if (world.getBlockState(pos.west()).getBlock() == ModBlocks.sapling && world.getBlockState(pos.west()).getValue(TYPE) == EnumSaplingType.YEW) {
					((BlockModSapling) world.getBlockState(pos.west()).getBlock()).grow(world, rand, pos.west(), world.getBlockState(pos.west()));
					return;
				}
			}
		}
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TYPE).ordinal() | (state.getValue(STAGE) << 2);
	}

	@SuppressWarnings("deprecation")
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(TYPE, EnumSaplingType.values()[meta & 3]).withProperty(STAGE, meta >> 2);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TYPE, STAGE);
	}

	@Override
	public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
		if (random.nextDouble() < 0.1)
			grow(worldIn, random, pos, state);
	}

	private void generateTree(World world, Random rand, BlockPos pos, IBlockState state) {
		if (!world.isRemote) {
			switch (state.getValue(TYPE)) {
				case ELDER:
					generateElderTree(world, pos, rand);
					break;
				case JUNIPER:
					generateJuniperTree(world, pos, rand);
					break;
				case YEW:
					generateYewTree(world, pos, rand);
					break;
				case CYPRESS:
					generateCypressTree(world, pos, rand);
					break;
				default:
					break;

			}
		}
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		int t = stack.getMetadata();
		if (t >= EnumSaplingType.values().length)
			t = 0;
		worldIn.setBlockState(pos, state.withProperty(TYPE, EnumSaplingType.values()[t]), 3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		registerModel(this, 0);
		registerModel(this, 1);
		registerModel(this, 2);
		registerModel(this, 3);
	}

	public static enum EnumSaplingType implements IStringSerializable {

		ELDER, JUNIPER, YEW, CYPRESS;

		@Override
		public String getName() {
			return this.name().toLowerCase();
		}
	}

	public static class PropertySapling extends PropertyEnum<EnumSaplingType> {
		protected PropertySapling(String name, Class<EnumSaplingType> valueClass, Collection<EnumSaplingType> allowedValues) {
			super(name, valueClass, allowedValues);
		}
	}
}
