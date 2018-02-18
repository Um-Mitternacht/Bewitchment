package com.bewitchment.common.tile;

import com.bewitchment.api.divination.Fortune;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class TileEntityCrystalBall extends TileMod {

	private TileEntityWitchAltar te = null;

	@Override
	protected void readDataNBT(NBTTagCompound tag) {
		// NO-OP
	}

	@Override
	protected void writeDataNBT(NBTTagCompound tag) {
		// NO-OP
	}

	public boolean fortune(EntityPlayer reader) {
		if (consumePower(5000, false)) {
			return readFortune(reader, null);
		}
		reader.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.no_power"), true);
		return false;
	}

	@SuppressWarnings({"deprecation", "null"})
	private boolean readFortune(@Nonnull EntityPlayer endPlayer, @Nullable EntityPlayer externalReader) {

		// This is here to support reading to others in the future
		EntityPlayer messageRecpt = endPlayer;

		if (endPlayer.getDistanceSq(this.getPos()) > 25) {
			messageRecpt.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.too_far"), true);
			return false;
		}

		Fortune fortune = endPlayer.getCapability(CapabilityDivination.CAPABILITY, null).getFortune();

		if (fortune != null) {

			messageRecpt.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.already_told", new TextComponentTranslation(fortune.getUnlocalizedName())), false);
			return false;
		}
		List<Fortune> valid = Fortune.REGISTRY.getValues().parallelStream().filter(f -> f.canBeUsedFor(endPlayer)).collect(Collectors.toList());
		if (valid.size() == 0) {
			messageRecpt.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.no_available_fortunes"), true);
			return false;
		}
		int totalEntries = valid.parallelStream().mapToInt(f -> f.getDrawingWeight()).sum();
		final int draw = endPlayer.getRNG().nextInt(totalEntries);
		int current = 0;
		for (Fortune f : valid) {
			int entries = f.getDrawingWeight();
			if (current < draw && draw < current + entries) {
				fortune = f;
				break;
			}
			current += entries;
		}
		endPlayer.getCapability(CapabilityDivination.CAPABILITY, null).setFortune(fortune);
		endPlayer.sendStatusMessage(new TextComponentTranslation(fortune.getUnlocalizedName()), true);
		return true;
	}

	private boolean consumePower(int power, boolean simulate) {
		if (power == 0) return true;
		if (te == null || te.isInvalid())
			te = TileEntityWitchAltar.getClosest(pos, world);
		if (te == null) return false;
		return te.consumePower(power, simulate);
	}
}
