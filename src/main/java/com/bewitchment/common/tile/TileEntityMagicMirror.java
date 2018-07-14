package com.bewitchment.common.tile;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.block.tools.BlockMagicMirror;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;


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
		this.world.notifyNeighborsOfStateChange(this.pos, this.getBlockType(), false);
		this.markDirty();
	}

	@Override
	public void update() {
		if (!world.isRemote) {
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
	}

	@Override
	void readAllModDataNBT(NBTTagCompound cmp) {

	}

	@Override
	void writeAllModDataNBT(NBTTagCompound cmp) {

	}

	@Override
	void writeModSyncDataNBT(NBTTagCompound tag) {

	}

	@Override
	void readModSyncDataNBT(NBTTagCompound tag) {

	}
}
