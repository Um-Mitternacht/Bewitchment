package com.bewitchment.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

/**
 * This class was created by Arekkuusu on 08/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public abstract class ModTileEntity extends TileEntity {

	protected static final Random rng = new Random();

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return false;
	}

	public void onBlockBroken(World worldIn, BlockPos pos, IBlockState state) {
	}

	public void onBlockHarvested(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack) {
	}

	protected abstract void readAllModDataNBT(NBTTagCompound tag);

	protected abstract void writeAllModDataNBT(NBTTagCompound tag);

	protected abstract void writeModSyncDataNBT(NBTTagCompound tag);

	protected abstract void readModSyncDataNBT(NBTTagCompound tag);

	public void syncToClient() {
		if (world != null) {
			IBlockState state = world.getBlockState(pos);
			world.notifyBlockUpdate(pos, state, state, 3);
		}
	}

	/**
	 * For internal use only.
	 *
	 * @param tag The nbt data to be read.
	 */
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readAllModDataNBT(tag);
	}

	/**
	 * For internal use only.
	 *
	 * @param tag A nbt tag provided by forge to save tile data in.
	 * @return A nbt tag with all the tile data saved on it.
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		final NBTTagCompound ret = super.writeToNBT(tag);
		writeAllModDataNBT(ret);
		return ret;
	}

	/**
	 * For internal use only.
	 *
	 * @return
	 */
	@Override
	public final SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}

	/**
	 * For internal use only.
	 *
	 * @return
	 */
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeModSyncDataNBT(tag);
		return tag;
	}

	/**
	 * For internal use only.
	 *
	 * @param net
	 * @param packet
	 */
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		handleUpdateTag(packet.getNbtCompound());
	}

	/**
	 * For internal use only.
	 *
	 * @param tag
	 */
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		readModSyncDataNBT(tag);
	}

	/**
	 * For internal use only.
	 *
	 * @param world
	 * @param pos
	 * @param oldState
	 * @param newState
	 * @return
	 */
	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

}