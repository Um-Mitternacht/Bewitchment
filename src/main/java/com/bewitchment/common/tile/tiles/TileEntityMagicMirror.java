package com.bewitchment.common.tile.tiles;

import com.bewitchment.api.state.StateProperties;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.client.core.event.custom.MimicEvent;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.capability.mimic.CapabilityMimicData;
import com.bewitchment.common.core.capability.mimic.IMimicData;
import com.bewitchment.common.core.helper.NBTHelper;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.ModTileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.util.UUID;

public class TileEntityMagicMirror extends ModTileEntity implements ITickable {
	private static final int REFRESH_TIME = 10;
	private static final double SHADE_DISTANCE_1 = 2.0;
	private static final double SHADE_DISTANCE_2 = 3.0;
	private static final double SHADE_DISTANCE_3 = 4.0;

	private int refreshTimer;
	private int shadeType;

	public TileEntityMagicMirror() {
		this.refreshTimer = REFRESH_TIME;
	}

	private void activate(boolean active, double distanceSq) {
		if (!active) {
			shadeType = 0;
		} else if (distanceSq <= SHADE_DISTANCE_1 * SHADE_DISTANCE_1) {
			shadeType = 3;
		} else if (distanceSq <= SHADE_DISTANCE_2 * SHADE_DISTANCE_2) {
			shadeType = 2;
		} else {
			shadeType = 1;
		}
		this.world.setBlockState(this.pos, this.world.getBlockState(this.pos).withProperty(StateProperties.MIRROR_VARIANTS, shadeType), 3);
		this.syncToClient();
		this.markDirty();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (playerIn.isSneaking()) {
			return false;
		}
		//The "!worldIn.isRemote" is intentional. This code should only run on the client.
		if (!worldIn.isRemote) {
			return true;
		}

		ItemStack held = playerIn.getHeldItem(hand);
		if (ItemStack.areItemsEqual(held, new ItemStack(ModItems.taglock))) {
			UUID victimID = NBTHelper.getUniqueID(held, Bewitchment.TAGLOCK_ENTITY);
			String victimName = NBTHelper.getString(held, Bewitchment.TAGLOCK_ENTITY_NAME);
			final IMimicData capability = playerIn.getCapability(CapabilityMimicData.CAPABILITY, null);
			capability.setMimickedPlayerID(victimID);
			capability.setMimickedPlayerName(victimName);
			if (playerIn.getUniqueID().equals(victimID)) {
				capability.setMimicking(false, playerIn);
				MinecraftForge.EVENT_BUS.post(new MimicEvent(playerIn, playerIn.getUniqueID(), playerIn.getName(), true));
			} else {
				capability.setMimicking(true, playerIn);
				MinecraftForge.EVENT_BUS.post(new MimicEvent(playerIn, victimID, victimName, false));
			}
		}
		return true;
	}

	@Override
	public void update() {
		if (world.isRemote) {
			return;
		}

		if (refreshTimer >= REFRESH_TIME) {
			EntityPlayer closestPlayer = this.world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), SHADE_DISTANCE_3, false);
			if (closestPlayer == null) {
				activate(false, -1.0f);
			} else if (closestPlayer.hasCapability(CapabilityTransformation.CAPABILITY, null)) {
				final CapabilityTransformation capability = closestPlayer.getCapability(CapabilityTransformation.CAPABILITY, null);
				if (capability.getType() != DefaultTransformations.VAMPIRE) {
					activate(true, closestPlayer.getDistanceSq(this.pos));
				} else {
					activate(false, -1.0f);
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
