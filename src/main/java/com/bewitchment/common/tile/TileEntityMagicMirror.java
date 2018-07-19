package com.bewitchment.common.tile;

import java.util.UUID;

import com.bewitchment.api.helper.ItemStackHelper;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.tools.BlockMagicMirror;
import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.core.helper.NBTHelper;
import com.bewitchment.common.core.net.NetworkHandler;
import com.bewitchment.common.core.net.messages.PlayerMimicDataChanged;
import com.bewitchment.common.item.ModItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMagicMirror extends ModTileEntity implements ITickable {
	private static final double SHADOW_RANGE = 4.0;
	private static final int REFRESH_TIME = 120;
	private int refreshTimer;

	public TileEntityMagicMirror() {
		this.refreshTimer = 0;
	}

	public void activate(boolean active) {
		this.world.setBlockState(this.pos, this.world.getBlockState(this.pos)
				.withProperty(BlockMagicMirror.ACTIVE, active)
				.withProperty(BlockMagicMirror.BOTTOM_FACING, this.world.getBlockState(this.pos).getValue(BlockMagicMirror.BOTTOM_FACING)));
		this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false); //TODO: is this the right method
		this.markDirty();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		}
		if (worldIn.isRemote) {
			return true;
		}

		ItemStack held = playerIn.getHeldItem(hand);
		if (ItemStackHelper.haveSameItem(held, ModItems.taglock, 0)) {
			final UUID playerID = NBTHelper.getUniqueID(held, Bewitchment.TAGLOCK_ENTITY);
			final String playerName = NBTHelper.getString(held, Bewitchment.TAGLOCK_ENTITY_NAME);
			final IMimicData capability = playerIn.getCapability(CapabilityMimicData.CAPABILITY, null);
			capability.setMimickedPlayerID(playerID);
			capability.setMimickedPlayerName(playerName);
			if (playerIn.getUniqueID().equals(playerID)) {
				capability.setMimicking(false, playerIn);
			} else {
				capability.setMimicking(true, playerIn);
			}
			NetworkHandler.HANDLER.sendToAll(new PlayerMimicDataChanged(playerIn));
		}
		return true;
	}

	@Override
	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {

	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		if (refreshTimer % REFRESH_TIME == 0) {
			if (this.world.isAnyPlayerWithinRangeAt(this.pos.getX(), this.pos.getY(), this.pos.getZ(), SHADOW_RANGE)) {
				EntityPlayer player = this.world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), SHADOW_RANGE, false);
				if (player.hasCapability(CapabilityTransformationData.CAPABILITY, null)) {
					final ITransformationData capability = player.getCapability(CapabilityTransformationData.CAPABILITY, null);
					if (capability.getType() != DefaultTransformations.VAMPIRE) {
						activate(true);
					}
				} else {
					activate(true);
				}
			} else {
				activate(false);
			}
			refreshTimer = 0;
		}
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound cmp) {

	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound cmp) {

	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {

	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {

	}
}
