package com.bewitchment.common.tile;

import com.bewitchment.api.divination.IFortune;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import com.bewitchment.common.core.capability.energy.user.CapabilityMagicPointsUser;
import com.bewitchment.common.divination.Fortune;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class TileEntityCrystalBall extends ModTileEntity {
	private static final String USER_TAG = "magicPointsUser";

	private CapabilityMagicPointsUser magicPointsUser;

	public TileEntityCrystalBall() {
		this.magicPointsUser = new CapabilityMagicPointsUser();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (hand == EnumHand.OFF_HAND) return false;
		if (worldIn.isRemote) return true;
		return fortune(playerIn);
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {

	}

	public boolean fortune(EntityPlayer reader) {
		if (consumePower(5000)) {
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

		IFortune fortune = endPlayer.getCapability(CapabilityDivination.CAPABILITY, null).getFortune();

		if (fortune != null) {

			messageRecpt.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.already_told", new TextComponentTranslation(fortune.getUnlocalizedName())), false);
			return false;
		}
		List<IFortune> valid = Fortune.REGISTRY.getValuesCollection().parallelStream().filter(f -> f.canBeUsedFor(endPlayer)).collect(Collectors.toList());
		if (valid.size() == 0) {
			messageRecpt.sendStatusMessage(new TextComponentTranslation("crystal_ball.error.no_available_fortunes"), true);
			return false;
		}
		int totalEntries = valid.parallelStream().mapToInt(f -> f.getDrawingWeight()).sum();
		final int draw = endPlayer.getRNG().nextInt(totalEntries);
		int current = 0;
		for (IFortune f : valid) {
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

	private boolean consumePower(int power) {
		if (power == 0) return true;
		if (magicPointsUser.hasValidAltar(world) || magicPointsUser.findClosestAltar(this.pos, this.world)) {
			return magicPointsUser.getAltar(world).subtract(power);
		} else {
			return false;
		}
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setTag(USER_TAG, magicPointsUser.serializeNBT());
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		magicPointsUser.deserializeNBT((NBTTagCompound) tag.getTag(USER_TAG));
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		// NO-OP
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		// NO-OP
	}
}
