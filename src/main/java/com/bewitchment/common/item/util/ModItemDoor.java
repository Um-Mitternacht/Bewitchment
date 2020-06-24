package com.bewitchment.common.item.util;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.TileEntityDragonsBlood;
import com.bewitchment.common.item.tool.ItemJuniperKey;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

@SuppressWarnings("NullableProblems")
public class ModItemDoor extends ItemDoor {
	public final ModBlockDoor door;

	public ModItemDoor(String name, Block base, String... oreDictionaryNames) {
		this(name, new ModBlockDoor("block_" + name, base), oreDictionaryNames);
	}

	private ModItemDoor(String name, ModBlockDoor door, String... oreDictionaryNames) {
		super(door);
		Util.registerItem(this, name, oreDictionaryNames);
		this.door = door;
		this.door.drop = new ItemStack(this);
		ForgeRegistries.BLOCKS.register(door);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
		EnumActionResult flag = super.onItemUse(player, world, pos, hand, face, hitX, hitY, hitZ);
		if (flag != EnumActionResult.FAIL && this == ModObjects.juniper_door) {
			BlockPos pos0 = pos;
			while (world.getBlockState(pos0).getBlock() != ModObjects.juniper_door.door) pos0 = pos0.up();
			Util.giveItem(player, ((ItemJuniperKey) ModObjects.juniper_key).setTags(world, pos0, new ItemStack(ModObjects.juniper_key)));
		}
		return flag;
	}

	@SuppressWarnings({"NullableProblems", "ConstantConditions", "deprecation"})
	public static class ModBlockDoor extends BlockDoor implements ITileEntityProvider {
		private ItemStack drop;

		private ModBlockDoor(String name, Block base) {
			super(base.getDefaultState().getMaterial());
			Util.registerBlock(this, name, base);
			setCreativeTab(null);
			if (base == ModObjects.juniper_planks) setResistance(Integer.MAX_VALUE);
		}

		@Override
		public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing face, float hitX, float hitY, float hitZ) {
			if (this == ModObjects.juniper_door.door) {
				if (!ItemJuniperKey.checkAccess(world, pos, player, true)) {
					return true;
				}
			}
			if (this == ModObjects.dragons_blood_door.door) {
				((TileEntityDragonsBlood) world.getTileEntity(pos)).activate(world, pos, player, hand, face);
			}
			return super.onBlockActivated(world, pos, state, player, hand, face, hitX, hitY, hitZ);
		}

		@Override
		public void toggleDoor(World world, BlockPos pos, boolean open) {
			if (this != ModObjects.juniper_door.door) super.toggleDoor(world, pos, open);
		}

		@Override
		public void neighborChanged(IBlockState state, World world, BlockPos to, Block block, BlockPos from) {
			if (state.getValue(HALF) == BlockDoor.EnumDoorHalf.UPPER) {
				BlockPos pos = to.down();
				IBlockState state0 = world.getBlockState(pos);
				if (state0.getBlock() != this) world.setBlockToAir(to);
				else if (block != this) state0.neighborChanged(world, pos, block, from);
			} else {
				boolean flag0 = false;
				BlockPos pos0 = to.up();
				IBlockState state0 = world.getBlockState(pos0);
				if (state0.getBlock() != this) {
					world.setBlockToAir(to);
					flag0 = true;
				}
				if (!world.getBlockState(to.down()).isSideSolid(world, to.down(), EnumFacing.UP)) {
					world.setBlockToAir(to);
					flag0 = true;
					if (state0.getBlock() == this) world.setBlockToAir(pos0);
				}
				if (flag0) {
					if (!world.isRemote) this.dropBlockAsItem(world, to, state, 0);
				} else if (this != ModObjects.juniper_door.door) {
					boolean flag1 = world.isBlockPowered(to) || world.isBlockPowered(pos0);
					if (block != this && (flag1 || block.getDefaultState().canProvidePower()) && flag1 != state0.getValue(POWERED)) {
						world.setBlockState(pos0, state0.withProperty(POWERED, flag1), 2);
						if (flag1 != state.getValue(OPEN)) {
							world.setBlockState(to, state.withProperty(OPEN, flag1), 2);
							world.markBlockRangeForRenderUpdate(to, to);
							world.playEvent(null, flag1 ? 1006 : 1012, to, 0);
						}
					}
				}
			}
		}

		@Override
		public Item getItemDropped(IBlockState state, Random rand, int fortune) {
			return state.getValue(HALF) == EnumDoorHalf.UPPER ? Items.AIR : drop.getItem();
		}

		@Override
		public EnumPushReaction getPushReaction(IBlockState state) {
			return state.getBlock() == ModObjects.juniper_door.door ? EnumPushReaction.BLOCK : super.getPushReaction(state);
		}

		@Override
		public ItemStack getItem(World world, BlockPos pos, IBlockState state) {
			return drop;
		}

		@Override
		@SideOnly(Side.CLIENT)
		public BlockRenderLayer getRenderLayer() {
			return Util.isTransparent(getDefaultState()) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.CUTOUT;
		}

		@Nullable
		@Override
		public TileEntity createNewTileEntity(World world, int i) {
			return new TileEntityDragonsBlood();
		}

		@Override
		@SideOnly(Side.CLIENT)
		public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
			if (worldIn.getTileEntity(pos) instanceof TileEntityDragonsBlood) {
				TileEntityDragonsBlood te = (TileEntityDragonsBlood) worldIn.getTileEntity(pos);
				if (te.sigil != null)
					worldIn.spawnParticle(EnumParticleTypes.SPELL_MOB, pos.getX(), pos.getY(), pos.getZ(), 1, 0, 0);
			}
		}

		@Override
		public float getPlayerRelativeBlockHardness(IBlockState state, EntityPlayer player, World world, BlockPos pos) {
			float val = super.getPlayerRelativeBlockHardness(state, player, world, pos);
			if (this == ModObjects.juniper_door.door) {
				if (ItemJuniperKey.checkAccess(world, pos, player, false)) return val;
				return -1;
			}
			return val;
		}

		@Nullable
		@Override
		public TileEntity createTileEntity(World world, IBlockState state) {
			if (state.getBlock() == ModObjects.dragons_blood_door.door) return new TileEntityDragonsBlood();
			return super.createTileEntity(world, state);
		}

		@Override
		public boolean canEntityDestroy(IBlockState state, IBlockAccess world, BlockPos pos, Entity entity) {
			if (this == ModObjects.juniper_door.door && entity instanceof EntityZombie) {
				return false;
			} else return super.canEntityDestroy(state, world, pos, entity);
		}
	}
}