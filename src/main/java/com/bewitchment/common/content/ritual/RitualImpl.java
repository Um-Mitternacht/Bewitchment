package com.bewitchment.common.content.ritual;

import com.bewitchment.api.ritual.IRitual;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class RitualImpl implements IRitual {

	private ResourceLocation rl;
	private int time, circles, altarStartingPower, tickPower;
	private NonNullList<ItemStack> output;
	private NonNullList<Ingredient> input;

	public RitualImpl(ResourceLocation registryName, @Nonnull NonNullList<Ingredient> input, @Nonnull NonNullList<ItemStack> output, int timeInTicks, int circles, int altarStartingPower, int powerPerTick) {
		this.time = timeInTicks;
		this.input = input;
		this.output = output;
		this.circles = circles;
		this.altarStartingPower = altarStartingPower;
		this.tickPower = powerPerTick;
		setRegistryName(registryName);
	}

	@Override
	public IRitual setRegistryName(ResourceLocation name) {
		rl = name;
		return this;
	}

	public IRitual setRegistryName(String domain, String name) {
		return setRegistryName(new ResourceLocation(domain, name));
	}

	@Override
	public ResourceLocation getRegistryName() {
		return rl;
	}

	@Override
	public Class<IRitual> getRegistryType() {
		return IRitual.class;
	}

	@Override
	public NonNullList<Ingredient> getInput() {
		NonNullList<Ingredient> stacks = NonNullList.create();
		stacks.addAll(input);
		return stacks;
	}

	@Override
	public NonNullList<ItemStack> getOutputRaw() {
		NonNullList<ItemStack> copy = NonNullList.<ItemStack>create();
		for (ItemStack i : output) {
			copy.add(i);
		}
		return copy;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public boolean onLowPower(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, int ticks, BlockPos effectivePosition, int covenSize) {
		return false;
	}

	@Override
	public void onStarted(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
	}

	@Override
	public void onStopped(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
	}

	@Override
	public void onFinish(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, BlockPos effectivePosition, int covenSize) {
	}

	@Override
	public void onUpdate(EntityPlayer player, TileEntity tile, World world, BlockPos circlePos, NBTTagCompound data, int ticks, BlockPos effectivePosition, int covenSize) {
	}

	@Override
	public int getCircles() {
		return circles;
	}

	@Override
	public int getRunningPower() {
		return tickPower;
	}

	@Override
	public int getRequiredStartingPower() {
		return altarStartingPower;
	}

}
