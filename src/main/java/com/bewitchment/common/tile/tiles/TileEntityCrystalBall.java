package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.divination.IFortune;
import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.content.crystalBall.Fortune;
import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class TileEntityCrystalBall extends ModTileEntity {

	private IMagicPowerConsumer altarTracker = IMagicPowerConsumer.CAPABILITY.getDefaultInstance();

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand == EnumHand.OFF_HAND) {
			return false;
		}
		if (worldIn.isRemote) {
			return true;
		}
		return this.fortune(playerIn, null); //TODO add external reading
	}

	public boolean fortune(@Nonnull EntityPlayer endPlayer, @Nullable EntityPlayer externalReader) {
		EntityPlayer actualReader = externalReader == null ? endPlayer : externalReader;
		if (endPlayer == null) {
			throw new IllegalArgumentException("Cannot read a null player fortune");
		}
		if (endPlayer.getDistanceSq(this.getPos()) > 25) {
			actualReader.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.too_far"), true);
			return false;
		}
		IFortune fortune = endPlayer.getCapability(CapabilityFortune.CAPABILITY, null).getFortune();
		if (fortune != null) {
			actualReader.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.already_told", new TextComponentTranslation(fortune.getTranslationKey())), false);
			return false;
		}
		List<IFortune> valid = Fortune.REGISTRY.getValuesCollection().parallelStream().filter(f -> f.canBeUsedFor(endPlayer)).collect(Collectors.toList());
		if (valid.size() == 0) {
			actualReader.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.no_available_fortunes"), true);
			return false;
		}

		if (this.getCapability(IMagicPowerConsumer.CAPABILITY, null).drainAltarFirst(actualReader, this.getPos(), this.world.provider.getDimension(), 3000)) {
			return this.readFortune(endPlayer, actualReader, valid);
		}
		actualReader.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.no_power"), true);
		return false;
	}

	private boolean readFortune(@Nonnull EntityPlayer endPlayer, @Nonnull EntityPlayer externalReader, List<IFortune> valid) {
		int totalEntries = valid.parallelStream().mapToInt(f -> f.getDrawingWeight()).sum();
		final int draw = endPlayer.getRNG().nextInt(totalEntries);
		int current = 0;
		IFortune fortune = null;
		for (IFortune f : valid) {
			int entries = f.getDrawingWeight();
			if ((current <= draw) && (draw < (current + entries))) {
				fortune = f;
				break;
			}
			current += entries;
		}
		endPlayer.getCapability(CapabilityFortune.CAPABILITY, null).setFortune(fortune);
		endPlayer.sendStatusMessage(new TextComponentTranslation(fortune.getTranslationKey()), true);
		if (!endPlayer.equals(externalReader)) {
			externalReader.sendStatusMessage(new TextComponentTranslation("crystal_ball.read.other", new TextComponentTranslation(fortune.getTranslationKey()), endPlayer.getDisplayNameString()), true);
		}
		return true;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return true;
		}
		return super.hasCapability(capability, facing);
	}

	@Nullable
	@Override
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == IMagicPowerConsumer.CAPABILITY) {
			return IMagicPowerConsumer.CAPABILITY.cast(this.altarTracker);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		this.altarTracker.readFromNbt(tag.getCompoundTag("altar"));
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setTag("altar", this.altarTracker.writeToNbt());
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		// NO-OP
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		// NO-OP
	}
}