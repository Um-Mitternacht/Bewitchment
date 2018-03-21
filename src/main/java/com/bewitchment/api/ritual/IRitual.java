package com.bewitchment.api.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IRitual extends IForgeRegistryEntry<IRitual> {

	/**
	 * The input items that trigger the ritual. Add an ingredient twice for it to be counted twice
	 *
	 * @return the list of ItemStacks to be consumed
	 */
	public @Nonnull
	NonNullList<Ingredient> getInput();

	/**
	 * @return the raw output without modifications. This is mainly used for showing the recipe in JEI
	 */
	public @Nonnull
	NonNullList<ItemStack> getOutputRaw();

	/**
	 * Override this to change the output given by {@link IRitual#getOutputRaw()} based on what was used,
	 * like damaging some of the input stacks
	 *
	 * @param input the items used to trigger the ritual
	 * @param tag   the acessory tag
	 * @return a list with all the items to spit out when the ritual finishes
	 */
	default @Nonnull
	NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound tag) {
		return getOutputRaw();
	}

	/**
	 * @return the time, in ticks, of how long this ritual should last. Negative values make it go indefinitely
	 */
	public int getTime();

	/**
	 * This method is called every tick if the altar has not enough power to keep it running.
	 * This method is called in place of {@link #onUpdate(EntityPlayer, TileEntity, World, BlockPos, NBTTagCompound, int)}
	 *
	 * @param player The player that activated the ritual, or null
	 * @param tile   the TileEntityGlyph performing the ritual
	 * @param world  the world the ritual is being performed into
	 * @param pos    the position of the tile
	 * @param data   the accessory tag
	 * @param ticks  how many ticks passed since activation
	 * @return true if the ritual should be stopped after this, false otherwise
	 */
	public boolean onLowPower(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data, int ticks);

	/**
	 * This method gets called when the ritual is triggered by a player
	 *
	 * @param player The player that activated the ritual, or null
	 * @param tile   the TileEntityGlyph performing the ritual
	 * @param world  the world the ritual is being performed into
	 * @param pos    the position of the tile
	 * @param data   the accessory tag
	 */
	public void onStarted(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data);

	/**
	 * This method gets called when the ritual is stopped before completion by a player
	 * This method is never called if {@link #onFinish(EntityPlayer, TileEntity, World, BlockPos, NBTTagCompound)} is called
	 *
	 * @param player The player that activated the ritual, or null
	 * @param tile   the TileEntityGlyph performing the ritual
	 * @param world  the world the ritual is being performed into
	 * @param pos    the position of the tile
	 * @param data   the accessory tag
	 */
	public void onStopped(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data);

	/**
	 * This method gets called when the ritual time expires, before stopping automatically
	 * This method is never called if {@link #onStopped(EntityPlayer, TileEntity, World, BlockPos, NBTTagCompound)} is called
	 *
	 * @param player The player that activated the ritual, or null
	 * @param tile   the TileEntityGlyph performing the ritual
	 * @param world  the world the ritual is being performed into
	 * @param pos    the position of the tile
	 * @param data   the accessory tag
	 */
	public void onFinish(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data);

	/**
	 * This method gets called every tick since the ritual was activated, if it has enough power to run.
	 * If it doesn't, {@link #onLowPower(EntityPlayer, TileEntity, World, BlockPos, NBTTagCompound, int)} gets called instead
	 *
	 * @param player The player that activated the ritual, or null
	 * @param tile   the TileEntityGlyph performing the ritual
	 * @param world  the world the ritual is being performed into
	 * @param pos    the position of the tile
	 * @param data   the accessory tag
	 * @param ticks  how many ticks passed since activation
	 */
	public void onUpdate(@Nullable EntityPlayer player, TileEntity tile, World world, BlockPos pos, NBTTagCompound data, int ticks);

	/**
	 * This method is used to check other pre-conditions, different from the input items (dimension, activating player, nearby blocks, lunar phase...)
	 *
	 * @param player The player that activated the ritual, or null
	 * @param world  the world the ritual is being performed into
	 * @param pos    the position of the tile
	 * @param recipe the list of items used to trigger this ritual
	 * @return
	 */
	default boolean isValid(EntityPlayer player, World world, BlockPos pos, List<ItemStack> recipe) {
		return true;
	}

	/**
	 * Ideally you should use BewitchmentAPI#getCirclesIntegerForRitual(...) to get human readable code.
	 * You should either cache this value or, even better, return a hard coded value for even better performance.
	 * The bit encoding is an 8 bit number BBMMSSNN where the first 6 bits are, 2 by 2, the metadata of the circle
	 * type from bigger to smaller, and the last 2 the number of total circles.
	 *
	 * @return the integer bit representation of what circles this ritual requires.
	 */
	public int getCircles();

	/**
	 * The power that should be drained each tick for this to work.
	 * If the power is not available, {@link #onLowPower(EntityPlayer, TileEntity, World, BlockPos, NBTTagCompound, int)}
	 * gets called instead of {@link #onUpdate(EntityPlayer, TileEntity, World, BlockPos, NBTTagCompound, int)}
	 *
	 * @return the value in AP/ME/MP
	 */
	public int getRunningPower();

	/**
	 * The power that should be drained at the beginning of the ritual for it to trigger at all.
	 * If there is not enough power the ritual is not started at all and the stacks on the ground are not consumed
	 *
	 * @return the value in AP/ME/MP
	 */
	public int getRequiredStartingPower();

}
