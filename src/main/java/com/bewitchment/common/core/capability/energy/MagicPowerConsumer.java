package com.bewitchment.common.core.capability.energy;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.Bewitchment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;

public class MagicPowerConsumer implements IMagicPowerConsumer {

	BlockPos cachedPos = null;
	int cachedDim = 0;

	public static void init() {
		CapabilityManager.INSTANCE.register(IMagicPowerConsumer.class, new MagicPowerConsumerStorage(), MagicPowerConsumer::new);
	}

	@Override
	public boolean drainAltarFirst(@Nullable EntityPlayer caster, @Nonnull BlockPos pos, int dimension, int amount) {
		if (amount == 0) {
			return true;
		}
		if (cachedPos == null || !isValidAltar(DimensionManager.getWorld(cachedDim), cachedPos)) {
			findNewAltar(DimensionManager.getWorld(dimension), pos, 16);
		}
		if (cachedPos != null) {
			World tileWorld = DimensionManager.getWorld(cachedDim);
			IMagicPowerContainer source = tileWorld.getTileEntity(cachedPos).getCapability(IMagicPowerContainer.CAPABILITY, null);
			if (source.drain(amount)) {
				return true;
			}
		}
		if (caster != null) {
			return caster.getCapability(IMagicPowerContainer.CAPABILITY, null).drain(amount);
		}
		return false;
	}

	private void findNewAltar(World world, BlockPos position, int radius) {
		cachedPos = world.tickableTileEntities.parallelStream()//
				.filter(t -> !t.isInvalid())//
				.filter(t -> t.hasCapability(IMagicPowerContainer.CAPABILITY, null))//
				.filter(t -> t.getDistanceSq(position.getX(), position.getY(), position.getZ()) < radius * radius)//
				.sorted(Comparator.<TileEntity>comparingDouble(t -> t.getDistanceSq(position.getX(), position.getY(), position.getZ())))//
				.map(t -> t.getPos())//
				.findFirst().orElse(null);
		if (cachedPos != null) {
			cachedDim = world.provider.getDimension();
		} else {
			cachedDim = 0;
		}

	}

	private boolean isValidAltar(World world, BlockPos position) {
		if (world == null || position == null) {
			Bewitchment.logger.warn("Checked if null is a valid altar dimension/position. I won't crash, but that shouldn't happen");
			new NullPointerException().printStackTrace();
			return false;
		}
		TileEntity te = world.getTileEntity(cachedPos);
		return (te != null && !te.isInvalid() && te.hasCapability(IMagicPowerContainer.CAPABILITY, null));
	}

	@Override
	public NBTTagCompound writeToNbt() {
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("altarDim", cachedDim);
		if (cachedPos != null) {
			tag.setInteger("altarX", cachedPos.getX());
			tag.setInteger("altarY", cachedPos.getY());
			tag.setInteger("altarZ", cachedPos.getZ());
		}
		return tag;
	}

	@Override
	public void readFromNbt(NBTTagCompound tag) {
		cachedDim = tag.getInteger("altarDim");
		if (tag.hasKey("altarX")) {
			cachedPos = new BlockPos(tag.getInteger("altarX"), tag.getInteger("altarY"), tag.getInteger("altarZ"));
		}
	}

}
