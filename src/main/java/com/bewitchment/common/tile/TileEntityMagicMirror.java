package com.bewitchment.common.tile;

import com.bewitchment.api.state.StateProperties;
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

import java.util.UUID;

public class TileEntityMagicMirror extends ModTileEntity implements ITickable {
	private static final int REFRESH_TIME = 10;
	private static final double SHADE_DISTANCE_1 = 2.0;
	private static final double SHADE_DISTANCE_2 = 3.0;
	private static final double SHADE_DISTANCE_3 = 4.0;

	private int refreshTimer;
	private int shadeType;

	public TileEntityMagicMirror() {
		this.refreshTimer = 0;
	}

	private void activate(boolean active, double distanceSq) {
		if(!active) {
			shadeType = 0;
		} else if(distanceSq <= SHADE_DISTANCE_1 * SHADE_DISTANCE_1) {
			shadeType = 3;
		} else if(distanceSq <= SHADE_DISTANCE_2 * SHADE_DISTANCE_2) {
			shadeType = 2;
		} else {
			shadeType = 1;
		}
		this.world.setBlockState(this.pos, this.world.getBlockState(this.pos)
				.withProperty(StateProperties.MIRROR_VARIANTS, shadeType)
				.withProperty(BlockMagicMirror.BOTTOM_FACING, this.world.getBlockState(this.pos).getValue(BlockMagicMirror.BOTTOM_FACING)));
		this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false); //TODO: is this the right method
		this.syncToClient();
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
		if (ItemStack.areItemsEqual(held, new ItemStack(ModItems.taglock))) {
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

		if (refreshTimer >= REFRESH_TIME) {
			EntityPlayer closestPlayer = this.world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), SHADE_DISTANCE_3, false);
			if(closestPlayer == null) {
				activate(false, -1.0f);
			} else if (closestPlayer.hasCapability(CapabilityTransformationData.CAPABILITY, null)) {
				final ITransformationData capability = closestPlayer.getCapability(CapabilityTransformationData.CAPABILITY, null);
				if (capability.getType() != DefaultTransformations.VAMPIRE) {
					activate(true, closestPlayer.getDistanceSq(this.pos));
				}
			} else {
				activate(true, closestPlayer.getDistanceSq(this.pos));
			}
			refreshTimer = 0;
		}
		refreshTimer++;
	}

	public int getShadeType() {
		return shadeType;
	}

	@Override
	protected void writeAllModDataNBT(NBTTagCompound tag) {
		tag.setInteger("shadeType", shadeType);
	}

	@Override
	protected void readAllModDataNBT(NBTTagCompound tag) {
		shadeType = tag.getInteger("shadeType");
	}

	@Override
	protected void writeModSyncDataNBT(NBTTagCompound tag) {
		tag.setInteger("shadeType", shadeType);
	}

	@Override
	protected void readModSyncDataNBT(NBTTagCompound tag) {
		shadeType = tag.getInteger("shadeType");
	}
}
