package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.block.BlockWitchesAltar;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "NullableProblems"})
public class TileEntityWitchesAltar extends ModTileEntity implements ITickable {
	public MagicPower magicPower = MagicPower.CAPABILITY.getDefaultInstance();
	
	public int color, gain = 1;
	private double multiplier = 1;
	private int maxPower;
	
	private Map<IBlockState, Integer> statesFound = new HashMap<>();
	private int[] cachedValues = new int[Short.MAX_VALUE];
	private int counter;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magicPower.deserializeNBT(tag.getCompoundTag("magicPower"));
		color = tag.getInteger("color");
		if (tag.hasKey("gain")) gain = tag.getInteger("gain");
		multiplier = tag.getDouble("multiplier");
		maxPower = tag.getInteger("maxPower");
		if (tag.hasKey("cachedValues")) cachedValues = tag.getIntArray("cachedValues");
		counter = tag.getInteger("counter");
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("magicPower", magicPower.serializeNBT());
		tag.setInteger("color", color);
		tag.setInteger("gain", gain);
		tag.setDouble("multiplier", multiplier);
		tag.setInteger("maxPower", maxPower);
		if (cachedValues != null) tag.setIntArray("cachedValues", cachedValues);
		tag.setInteger("counter", counter);
		return super.writeToNBT(tag);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY || super.hasCapability(capability, face);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == MagicPower.CAPABILITY ? MagicPower.CAPABILITY.cast(magicPower) : super.getCapability(capability, face);
	}
	
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return super.shouldRefresh(world, pos, oldState, newState) || !newState.getValue(BlockWitchesAltar.TYPE).equals(oldState.getValue(BlockWitchesAltar.TYPE));
	}
	
	@Override
	public void update() {
		if (!world.isRemote && cachedValues != null) {
			if (magicPower.amount > magicPower.maxAmount) magicPower.amount = magicPower.maxAmount;
			if (world.getTotalWorldTime() % 20 == 0) magicPower.fill(gain * 16);
			scan(Bewitchment.proxy.config.altarScansPerTick);
		}
	}
	
	@Override
	public void onLoad() {
		world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 10, 0);
		counter = 0;
		scan(Short.MAX_VALUE);
	}
	
	private void scan(int times) {
		for (int i = 0; i < times; i++) {
			counter = ++counter % Short.MAX_VALUE;
			int x = counter & 31;
			int y = (counter >> 5) & 31;
			int z = (counter >> 10) & 31;
			BlockPos check = new BlockPos(pos.getX() + x - 16, pos.getY() + y - 16, pos.getZ() + z - 16);
			IBlockState state = world.getBlockState(check);
			if (state.getBlock() instanceof BlockLog) state = state.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
			else if (state.getBlock() instanceof BlockLeaves) state = state.withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, false);
			else if (!(state.getBlock() instanceof BlockFlower)) state = state.getBlock().getDefaultState();
			int found = statesFound.getOrDefault(state, 0);
			if (found < 1 + statesFound.size() / 2) {
				int val = BewitchmentAPI.getNatureValue(state);
				if (val != 0 && val != cachedValues[counter]) {
					maxPower -= cachedValues[counter];
					cachedValues[counter] = val;
					maxPower += val;
					statesFound.put(state, ++found);
				}
			}
			if (counter == Short.MAX_VALUE - 1) {
				magicPower.maxAmount = (int) (maxPower * multiplier);
				maxPower = 0;
				statesFound.clear();
			}
		}
	}
}