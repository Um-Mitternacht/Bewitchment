package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.block.BlockWitchesAltar;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

@SuppressWarnings({"ConstantConditions", "WeakerAccess", "NullableProblems"})
public class TileEntityWitchesAltar extends ModTileEntity implements ITickable {
	public MagicPower magicPower = MagicPower.CAPABILITY.getDefaultInstance();
	
	public int color, gain = 1;
	private byte[] cachedValues = new byte[Short.MAX_VALUE];
	private int counter;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		magicPower.deserializeNBT(tag.getCompoundTag("magicPower"));
		color = tag.getInteger("color");
		if (tag.hasKey("gain")) gain = tag.getInteger("gain");
		if (tag.hasKey("cachedValues")) cachedValues = tag.getByteArray("cachedValues");
		counter = tag.getInteger("counter");
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setTag("magicPower", magicPower.serializeNBT());
		tag.setInteger("color", color);
		tag.setInteger("gain", gain);
		if (cachedValues != null) tag.setByteArray("cachedValues", cachedValues);
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
			if (world.getTotalWorldTime() % 20 == 0) magicPower.fill(gain);
			for (int i = 0; i < Bewitchment.proxy.config.altarScansPerTick; i++) {
				counter = ++counter % Short.MAX_VALUE;
				int x = counter & 31;
				int y = (counter >> 5) & 31;
				int z = (counter >> 10) & 31;
				BlockPos check = new BlockPos(pos.getX() + x - 16, pos.getY() + y - 16, pos.getZ() + z - 16);
				byte val = BewitchmentAPI.getNatureValue(world.getBlockState(check).getBlock());
				if (val != 0 && val != cachedValues[counter]) {
					magicPower.maxAmount -= cachedValues[counter];
					cachedValues[counter] = val;
					magicPower.maxAmount += val;
				}
			}
		}
	}
	
	@Override
	public void onLoad() {
		world.scheduleBlockUpdate(pos, world.getBlockState(pos).getBlock(), 10, 0);
	}
}