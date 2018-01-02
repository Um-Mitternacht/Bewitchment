package com.bewitchment.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 08/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public abstract class TileMod extends TileEntity {

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		readDataNBT(par1nbtTagCompound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound par1nbtTagCompound) {
		final NBTTagCompound ret = super.writeToNBT(par1nbtTagCompound);
		writeDataNBT(ret);
		return ret;
	}

	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		final NBTTagCompound tag = getUpdateTag();
		writeDataNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	/*
	 * (non-Javadoc)
	 * By making this final we would prevent stripping useless nbt data from a (frequently sent)
	 * network packet, which is of course bad. For example, in the thread spinner tile entity,
	 * the NBT tag carries way more information than what the clients need to know to function
	 * properly. I'm removing the final modifier to allow a lighter network load. I'll probably
	 * rework the base class in the future to have two methods that only read and write sync-data
	 * 
	 * - zabi
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
		readDataNBT(packet.getNbtCompound());
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	abstract void readDataNBT(NBTTagCompound cmp);

	abstract void writeDataNBT(NBTTagCompound cmp);
}