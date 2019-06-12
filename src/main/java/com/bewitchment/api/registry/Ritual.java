package com.bewitchment.api.registry;

import com.bewitchment.Util;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.entity.TileEntityGlyph;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.item.tool.ItemAthame;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Ritual extends IForgeRegistryEntry.Impl<Ritual> {
	public static final int[][] small = {{0, 0, 1, 1, 1, 0, 0}, {0, 1, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 1, 0}, {0, 0, 1, 1, 1, 0, 0}};
	public static final int[][] medium = {{0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0}};
	public static final int[][] large = {{0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}, {0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}, {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0}};
	
	public final List<Ingredient> input;
	public final List<ItemStack> output;
	
	public final Predicate<EntityLivingBase> sacrificePredicate;
	public final int[] circles = new int[3];
	
	public final int time, startingPower, runningPower;
	
	public final boolean canBePerformedRemotely;
	
	public Ritual(ResourceLocation name, List<Ingredient> input, Predicate<EntityLivingBase> sacrificePredicate, List<ItemStack> output, boolean canBePerformedRemotely, int time, int startingPower, int runningPower, int small, int medium, int big) {
		setRegistryName(name);
		this.input = input;
		this.sacrificePredicate = sacrificePredicate;
		this.output = output;
		this.canBePerformedRemotely = canBePerformedRemotely;
		this.time = time;
		this.startingPower = startingPower;
		this.runningPower = runningPower;
		if (small < 0) throw new IllegalArgumentException("Cannot have the smaller circle missing");
		if (medium < 0 && big > 0) throw new IllegalArgumentException("Cannot have missing middle circle when a big circle is present");
		if (small == BlockGlyph.GOLDEN || medium == BlockGlyph.GOLDEN || big == BlockGlyph.GOLDEN) throw new IllegalArgumentException("No golden circles allowed");
		circles[0] = small;
		circles[1] = medium;
		circles[2] = big;
	}
	
	public Ritual(ResourceLocation name, List<Ingredient> input, Predicate<EntityLivingBase> sacrificePredicate, List<ItemStack> output, int time, int startingPower, int runningPower, int small, int medium, int big) {
		this(name, input, sacrificePredicate, output, true, time, startingPower, runningPower, small, medium, big);
	}
	
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster) {
		return true;
	}
	
	public void onStarted(World world, BlockPos pos, EntityPlayer caster) {
	}
	
	public void onFinished(World world, BlockPos pos) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileEntityGlyph) {
				TileEntityGlyph glyph = (TileEntityGlyph) tile;
				ItemStack athame = ItemStack.EMPTY;
				for (int i = 0; i < glyph.getInventories()[0].getSlots(); i++) {
					if (glyph.getInventories()[0].getStackInSlot(i).getItem() instanceof ItemAthame) {
						athame = glyph.getInventories()[0].getStackInSlot(i).copy();
						break;
					}
				}
				ModTileEntity.clear(glyph.getInventories()[0]);
				for (ItemStack stack : output) InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack.copy());
				if (!athame.isEmpty()) {
					EntityPlayer player = world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), Byte.MAX_VALUE, false);
					if (player != null) {
						athame.damageItem(50, player);
						InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, athame);
					}
				}
			}
		}
	}
	
	public void onHalted(World world, BlockPos pos) {
		if (!world.isRemote) {
			TileEntity tile = world.getTileEntity(pos);
			if (tile instanceof TileEntityGlyph) {
				TileEntityGlyph glyph = (TileEntityGlyph) tile;
				for (int i = 0; i < glyph.getInventories()[0].getSlots(); i++) InventoryHelper.spawnItemStack(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, glyph.getInventories()[0].getStackInSlot(i).copy());
				ModTileEntity.clear(glyph.getInventories()[0]);
			}
		}
	}
	
	public void onUpdate(World world, BlockPos pos, EntityPlayer caster) {
	}
	
	@SideOnly(Side.CLIENT)
	public void onClientUpdate(World world, BlockPos pos, EntityPlayer caster) {
	}
	
	public final boolean matches(World world, BlockPos pos, List<EntityItem> items, List<EntityLivingBase> livings) {
		for (int x = 0; x < small.length; x++) {
			for (int z = 0; z < small.length; z++) {
				IBlockState state = world.getBlockState(pos.add(x - small.length / 2, 0, z - small.length / 2));
				if (small[x][z] == 1 && (state.getBlock() != ModObjects.glyph || (state.getValue(BlockGlyph.TYPE) != BlockGlyph.GOLDEN && (state.getValue(BlockGlyph.TYPE) != circles[0] && circles[0] != BlockGlyph.ANY)))) return false;
			}
		}
		if (circles[1] != -1) {
			for (int x = 0; x < medium.length; x++) {
				for (int z = 0; z < medium.length; z++) {
					IBlockState state = world.getBlockState(pos.add(x - medium.length / 2, 0, z - medium.length / 2));
					if (medium[x][z] == 1 && (state.getBlock() != ModObjects.glyph || (state.getValue(BlockGlyph.TYPE) != BlockGlyph.GOLDEN && (state.getValue(BlockGlyph.TYPE) != circles[1] && circles[1] != BlockGlyph.ANY)))) return false;
				}
			}
		}
		if (circles[2] != -1) {
			for (int x = 0; x < large.length; x++) {
				for (int z = 0; z < large.length; z++) {
					IBlockState state = world.getBlockState(pos.add(x - large.length / 2, 0, z - large.length / 2));
					if (large[x][z] == 1 && (state.getBlock() != ModObjects.glyph || (state.getValue(BlockGlyph.TYPE) != BlockGlyph.GOLDEN && (state.getValue(BlockGlyph.TYPE) != circles[2] && circles[2] != BlockGlyph.ANY)))) return false;
				}
			}
		}
		List<ItemStack> ground = new ArrayList<>();
		for (EntityItem item : items) ground.add(item.getItem());
		ground = Util.expandList(ground);
		ItemStackHandler handler = new ItemStackHandler(ground.size());
		for (int i = 0; i < handler.getSlots(); i++) handler.insertItem(i, ground.get(i).copy(), false);
		if (Util.areISListsEqual(input, handler)) {
			if (sacrificePredicate != null) {
				for (EntityLivingBase entity : livings) if (sacrificePredicate.test(entity)) return true;
				return false;
			}
			return true;
		}
		return false;
	}
}