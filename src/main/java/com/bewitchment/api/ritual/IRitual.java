package com.bewitchment.api.ritual;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.bewitchment.common.tile.TileEntityGlyph;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface IRitual extends IForgeRegistryEntry<IRitual> {
	
	public @Nonnull NonNullList<Ingredient> getInput();
	
	public @Nonnull NonNullList<ItemStack> getOutputRaw();
	
	default @Nonnull NonNullList<ItemStack> getOutput(NonNullList<ItemStack> input, NBTTagCompound tag) {
		return getOutputRaw();
	}
	
	public int getTime();
	
	public void onLowPower(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, int ticks);
	
	public void onStarted(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data);
	
	public void onStopped(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data);
	
	public void onFinish(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data);
	
	public void onUpdate(@Nullable EntityPlayer player, TileEntityGlyph tile, World world, BlockPos pos, NBTTagCompound data, int ticks);
	
	default boolean isValid(EntityPlayer player, World world, BlockPos pos, List<ItemStack> recipe) {
		return true;
	}
	
	public int getCircles();
	
	public int getRunningPower();
	
	public int getRequiredStartingPower();
	
}
