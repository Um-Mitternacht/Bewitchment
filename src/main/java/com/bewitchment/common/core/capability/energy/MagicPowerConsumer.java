package com.bewitchment.common.core.capability.energy;

import java.util.Comparator;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.api.mp.IMagicPowerContainer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

public class MagicPowerConsumer implements IMagicPowerConsumer {
	
	// TODO add storage and register the capability
	
	BlockPos pos = null;
	int dimID = 0;
	
	@Override
	public boolean drain(EntityPlayer caster, World world, BlockPos position, int radius, int amount) {
		if (world.isRemote) {
			return false;
		}
		if (pos == null) {
			findNewAltar(world, position, radius);
		}
		if (pos != null) {
			World w = DimensionManager.getWorld(dimID);
			if (isValidAltar(w, pos)) {
				if (w.getTileEntity(pos).getCapability(IMagicPowerContainer.CAPABILITY, null).drain(amount)) {
					return true;
				}
			}
		}
		return caster.getCapability(IMagicPowerContainer.CAPABILITY, null).drain(amount);
	}
	
	private void findNewAltar(World world, BlockPos position, int radius) {
		pos = world.tickableTileEntities.parallelStream()//
				.filter(t -> !t.isInvalid())//
				.filter(t -> t.hasCapability(IMagicPowerContainer.CAPABILITY, null))//
				.sorted(Comparator.<TileEntity>comparingDouble(t -> t.getDistanceSq(position.getX(), position.getY(), position.getZ())))//
				.filter(t -> t.getDistanceSq(position.getX(), position.getY(), position.getZ()) < radius * radius)//
				.map(t -> t.getPos())//
			.findFirst().orElse(null);
		if (pos!=null) {
			dimID = world.provider.getDimension();
		} else {
			dimID = 0;
		}
		
	}
	
	private boolean isValidAltar(World world, BlockPos position) {
		TileEntity te = world.getTileEntity(pos);
		return (te != null && !te.isInvalid() && te.hasCapability(IMagicPowerContainer.CAPABILITY, null));
	}
	
	@Override
	public NBTTagCompound writeToNbt() {
		return null;
	}
	
	@Override
	public void readFromNbt(NBTTagCompound tag) {
		
	}
	
}
