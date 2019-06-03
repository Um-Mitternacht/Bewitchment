package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.common.block.BlockWitchesAltar;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.ItemStackHandler;

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
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		boolean flag = super.shouldRefresh(world, pos, oldState, newState) || !newState.getValue(BlockWitchesAltar.TYPE).equals(oldState.getValue(BlockWitchesAltar.TYPE));
		if (!world.isRemote && flag) InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(Blocks.CARPET, 1, color - 1));
		return flag;
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
	public ItemStackHandler[] getInventories() {
		ItemStackHandler fin = new ItemStackHandler();
		fin.insertItem(0, new ItemStack(Blocks.CARPET, 1, color - 1), false);
		return new ItemStackHandler[]{fin};
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
		map.clear();
		scan(Short.MAX_VALUE);
	}
	
	private void scan(int times) {
		for (int i = 0; i < times; i++) {
			counter = ++counter % Short.MAX_VALUE;
			int x = counter & 31;
			int y = (counter >> 5) & 31;
			int z = (counter >> 10) & 31;
			BlockPos check = new BlockPos(pos.getX() + x - 8, pos.getY() + y - 8, pos.getZ() + z - 8);
			IBlockState state = world.getBlockState(check);
			if (state.getBlock() instanceof BlockLog) state = state.withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y);
			else if (state.getBlock() instanceof BlockRotatedPillar) state = state.getBlock().getDefaultState().withProperty(BlockRotatedPillar.AXIS, EnumFacing.Axis.Y);
			else if (state.getBlock() instanceof BlockLeaves) state = state.withProperty(BlockLeaves.CHECK_DECAY, false).withProperty(BlockLeaves.DECAYABLE, false);
			else if (!(state.getBlock() instanceof BlockFlower)) state = state.getBlock().getDefaultState();
			if (isNatural(state)) {
				map.put(state, map.getOrDefault(state, 0) + 1);
				map.put(state, Math.max(map.keySet().size() * 2, map.get(state)));
			}
			if (counter == Short.MAX_VALUE - 1) {
				int maxPower = 0;
				for (int val : map.values()) maxPower += val;
				magicPower.maxAmount = (int) (maxPower * multiplier);
				map.clear();
			}
		}
	}
	
	private boolean isNatural(IBlockState state) {
		return (!(state.getBlock() instanceof BlockGrass)) && (state.getBlock() instanceof IGrowable || state.getBlock() instanceof IPlantable || state.getBlock() instanceof BlockMelon || state.getBlock() instanceof BlockPumpkin || state.getBlock() instanceof BlockLeaves || (state.getBlock() instanceof BlockRotatedPillar && state.getMaterial() == Material.WOOD));
	}
}