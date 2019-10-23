package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Util;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TileEntitySiphoningFlower extends ModTileEntity {
	public String boundId = "", boundName = "", ownerId;
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		boundId = tag.getString("boundId");
		boundName = tag.getString("boundName");
		ownerId = tag.getString("ownerId");
		super.readFromNBT(tag);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag.setString("boundId", boundId);
		tag.setString("boundName", boundName);
		tag.setString("ownerId", ownerId);
		return super.writeToNBT(tag);
	}
	
	@Override
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (player.isSneaking() && player.getHeldItem(EnumHand.MAIN_HAND).isEmpty() && isBound() && isOwner(player)) {
			boundName = "";
			boundId = "";
			if (!world.isRemote) ((WorldServer) world).spawnParticle(EnumParticleTypes.REDSTONE, pos.getX(), pos.getY(), pos.getZ(), 20, 0, 0.5, 0, 0d);
			markDirty();
			return true;
		}
		if (!player.isSneaking() && isBound() && (player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModObjects.boline) && player.getHeldItem(EnumHand.OFF_HAND).getItem() == ModObjects.taglock && !player.getHeldItem(EnumHand.OFF_HAND).hasTagCompound()) {
			ItemStack temp = new ItemStack(ModObjects.taglock);
			temp.setTagCompound(new NBTTagCompound());
			temp.getTagCompound().setString("boundId", boundId);
			temp.getTagCompound().setString("boundName", boundName);
			Util.replaceAndConsumeItem(player, EnumHand.OFF_HAND, temp);
			player.getHeldItem(EnumHand.MAIN_HAND).damageItem(1, player);
			boundName = "";
			boundId = "";
			markDirty();
			return true;
		}
		return super.activate(world, pos, player, hand, face);
	}
	
	public boolean isBound() {
		return !boundId.equals("");
	}
	
	public boolean isOwner(EntityPlayer player) {
		return Util.findPlayer(ownerId) == player;
	}
}
