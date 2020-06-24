package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.handler.GuiHandler;
import com.bewitchment.common.item.ItemSigil;
import com.bewitchment.common.item.ItemTaglock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("NullableProblems")
public class TileEntityDBChest extends ModTileEntity implements ITickable {
	private final ItemStackHandler inventory = new ItemStackHandler(36);
	public ItemSigil sigil;
	public int cooldown = 0;
	public boolean whiteList;
	public Set<String> playerUUIDSet = new HashSet<>();

	public float lidAngle, prevLidAngle;
	public int using;

	@Override
	public void update() {
		prevLidAngle = lidAngle;
		if (using > 0 && lidAngle == 0)
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		if (using == 0 && lidAngle > 0 || using > 0 && lidAngle < 1) {
			if (using > 0) lidAngle += 0.1f;
			else lidAngle -= 0.1f;
			if (lidAngle > 1) lidAngle = 1;
			if (lidAngle < 0) lidAngle = 0;
			if (lidAngle < 0.5f && prevLidAngle >= 0.5f)
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		}
		if (cooldown > 0) cooldown--;
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.using = type;
			return true;
		}
		return super.receiveClientEvent(id, type);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, face);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, face);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		this.readUpdateTag(tag);
		super.readFromNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		this.writeUpdateTag(tag);
		return super.writeToNBT(tag);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeUpdateTag(tag);
		return new SPacketUpdateTileEntity(pos, getBlockMetadata(), tag);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tag = super.getUpdateTag();
		writeUpdateTag(tag);
		return tag;
	}

	@Override
	public void onDataPacket(NetworkManager manager, SPacketUpdateTileEntity packet) {
		NBTTagCompound tag = packet.getNbtCompound();
		readUpdateTag(tag);
	}

	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory};
	}

	@Override
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (sigil == null) {
			if (player.getHeldItem(hand).getItem() instanceof ItemSigil) {
				sigil = (ItemSigil) player.inventory.decrStackSize(player.inventory.currentItem, 1).getItem();
				whiteList = sigil.positive;
				playerUUIDSet.add(player.getUniqueID().toString());
				markDirty();
				return true;
			}
		} else if (player.getHeldItem(hand).getItem() instanceof ItemTaglock && player.getHeldItem(hand).hasTagCompound()) {
			modifyList(this, player.getHeldItem(hand).getTagCompound().getString("boundId"));
			markDirty();
			return true;
		} else if (cooldown <= 0 && (isPlayerOnList(this, player) == whiteList)) {
			sigil.applyEffects(player);
			cooldown = sigil.cooldown;
			markDirty();
		}
		player.openGui(Bewitchment.instance, GuiHandler.ModGui.DRAGONS_BLOOD_CHEST.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	private void writeUpdateTag(NBTTagCompound tag) {
		tag.setString("sigil", sigil == null ? "" : sigil.getRegistryName().toString());
		tag.setTag("inventory", inventory.serializeNBT());
		tag.setInteger("cooldown", cooldown);
		tag.setBoolean("whitelist", whiteList);
		NBTTagList playerList = new NBTTagList();
		for (String s : playerUUIDSet) {
			playerList.appendTag(new NBTTagString(s));
		}
		tag.setTag("playerList", playerList);
	}

	private void readUpdateTag(NBTTagCompound tag) {
		sigil = tag.getString("sigil").isEmpty() ? null : (ItemSigil) ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("sigil")));
		inventory.deserializeNBT(tag.getCompoundTag("inventory"));
		cooldown = tag.getInteger("cooldown");
		whiteList = tag.getBoolean("whitelist");
		NBTTagList playerList = tag.getTagList("playerList", Constants.NBT.TAG_STRING);
		for (int i = 0; i < playerList.tagCount(); i++) {
			playerUUIDSet.add(playerList.getStringTagAt(i));
		}
	}

	private boolean isPlayerOnList(TileEntityDBChest te, EntityPlayer player) {
		return te.playerUUIDSet.contains(player.getUniqueID().toString());
	}

	private void modifyList(TileEntityDBChest te, String uuid) {
		if (te.playerUUIDSet.contains(uuid)) te.playerUUIDSet.remove(uuid);
		else te.playerUUIDSet.add(uuid);
	}
}