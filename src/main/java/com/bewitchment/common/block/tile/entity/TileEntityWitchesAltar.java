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
	
	private Map<IBlockState, Integer> map = new HashMap<>();
	private int counter;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magicPower.deserializeNBT(tag.getCompoundTag("magicPower"));
		color = tag.getInteger("color");
		if (tag.hasKey("gain")) gain = tag.getInteger("gain");
		multiplier = tag.getDouble("multiplier");
		counter = tag.getInteger("counter");
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("magicPower", magicPower.serializeNBT());
		tag.setInteger("color", color);
		tag.setInteger("gain", gain);
		tag.setDouble("multiplier", multiplier);
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
		if (!world.isRemote) {
			if (magicPower.amount > magicPower.maxAmount) magicPower.amount = magicPower.maxAmount;
			if (world.getTotalWorldTime() % 20 == 0) magicPower.fill(gain * 16);
			scan(Bewitchment.proxy.config.altarScansPerTick);
		}
	}
	
	@Override
	public void onLoad() {
		world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 10, 0);
		counter = 0;
		magicPower.maxAmount = 0;
		map.clear();
		scan(4096);
	}
	
	private void scan(int times) {
		for (int i = 0; i < times; i++) {
			counter = ++counter % 4096;
			int x = counter & 15;
			int y = (counter >> 4) & 15;
			int z = (counter >> 8) & 15;
			BlockPos check = new BlockPos(pos.getX() + x - 8, pos.getY() + y - 8, pos.getZ() + z - 8);
			IBlockState state = world.getBlockState(check);
			if (state.getBlock() instanceof BlockLog) state = state.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
			else if (state.getBlock() instanceof BlockLeaves) state = state.withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, false);
			else if (!(state.getBlock() instanceof BlockFlower)) state = state.getBlock().getDefaultState();
			int val = BewitchmentAPI.getNatureValue(state);
			if (val != 0) {
				int current = map.getOrDefault(state, 0);
				if (current < 20) map.put(state, current + val);
			}
			if (counter == 4095) {
				magicPower.maxAmount = 0;
				for (int value : map.values()) magicPower.maxAmount += value;
				magicPower.maxAmount += map.keySet().size() * 40;
				magicPower.maxAmount *= multiplier;
				map.clear();
			}
		}
	}
}