package com.bewitchment.common.core.capability.energy.user;

import com.bewitchment.common.core.capability.ModCapability;
import com.bewitchment.common.core.capability.energy.storage.CapabilityMagicPoints;
import com.bewitchment.common.tile.TileEntityWitchAltar;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

import javax.annotation.Nullable;
import java.util.Optional;

public class CapabilityMagicPointsUser extends ModCapability {
	@CapabilityInject(CapabilityMagicPointsUser.class)
	public static final Capability<CapabilityMagicPointsUser> CAPABILITY = null;

	private BlockPos altarPos;

	public CapabilityMagicPointsUser() {
		altarPos = null;
	}

	@Nullable
	public boolean findClosestAltar(BlockPos pos, World world) {
		Optional<Tuple<TileEntityWitchAltar, Double>> res = world.loadedTileEntityList.parallelStream()
				.filter(te -> te instanceof TileEntityWitchAltar)
				.map(te -> new Tuple<TileEntityWitchAltar, Double>((TileEntityWitchAltar) te, te.getDistanceSq(pos.getX(), pos.getY(), pos.getZ())))
				.filter(tup -> tup.getSecond() <= 256)
				.min((t1, t2) -> t1.getSecond().compareTo(t2.getSecond()));
		if (res.isPresent()) {
			altarPos = res.get().getFirst().getPos();
			return true;
		} else {
			altarPos = null;
			return false;
		}
	}

	public boolean hasValidAltar(World world) {
		return altarPos != null && world.getTileEntity(altarPos) instanceof TileEntityWitchAltar;
	}

	public CapabilityMagicPoints getAltar(World world) {
		return ((TileEntityWitchAltar) world.getTileEntity(altarPos)).getCapability(CapabilityMagicPoints.CAPABILITY, null);
	}

	public boolean shouldShowHud() {
		return false;
	}

	@Override
	public NBTTagCompound serializeNBT() {
		return new NBTTagCompound();
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbtTagCompound) {

	}
}
