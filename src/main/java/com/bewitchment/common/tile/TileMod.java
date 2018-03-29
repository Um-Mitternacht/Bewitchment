package com.bewitchment.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This class was created by Arekkuusu on 08/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public abstract class TileMod extends TileEntity {

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readAllModDataNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		final NBTTagCompound ret = super.writeToNBT(tag);
		writeAllModDataNBT(ret);
		return ret;
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeModSyncDataNBT(tag);
		return tag;
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		handleUpdateTag(packet.getNbtCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readModSyncDataNBT(tag);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	abstract void readAllModDataNBT(NBTTagCompound cmp);

	abstract void writeAllModDataNBT(NBTTagCompound cmp);
	
	abstract void writeModSyncDataNBT(NBTTagCompound tag);
	
	abstract void readModSyncDataNBT(NBTTagCompound tag);
	
	public void syncToClient() {
		IBlockState state = world.getBlockState(pos);
		world.notifyBlockUpdate(pos, state, state, 3);
	}
}