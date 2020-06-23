package com.bewitchment.common.block.tile.entity;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.item.ItemSigil;
import com.bewitchment.common.item.ItemTaglock;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TileEntitySigil extends ModTileEntity implements ITickable {
	public ItemSigil sigil;
	public int cooldown = 0;
	public int timer = 24000 * 7;
	public boolean whiteList;
	public Set<String> whiteListUUIDSet = new HashSet<>();
	
	public void setupTileEntity(ItemSigil sigil) {
		this.sigil = sigil;
		this.cooldown = sigil.cooldown;
		this.whiteList = sigil.positive;
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
	public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
		if (player.getHeldItem(hand).getItem() instanceof ItemTaglock && player.getHeldItem(hand).hasTagCompound()) {
			modifyList(player.getHeldItem(hand).getTagCompound().getString("boundId"));
			return true;
		}
		return super.activate(world, pos, player, hand, face);
	}
	
	private void writeUpdateTag(NBTTagCompound tag) {
		tag.setString("sigil", sigil == null ? "" : sigil.getRegistryName().toString());
		tag.setInteger("cooldown", cooldown);
		tag.setBoolean("whitelist", whiteList);
		NBTTagList playerList = new NBTTagList();
		for (String s : whiteListUUIDSet) {
			playerList.appendTag(new NBTTagString(s));
		}
		tag.setTag("playerList", playerList);
		tag.setInteger("timer", timer);
	}
	
	private void readUpdateTag(NBTTagCompound tag) {
		sigil = tag.getString("sigil").isEmpty() ? null : (ItemSigil) ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("sigil")));
		cooldown = tag.getInteger("cooldown");
		whiteList = tag.getBoolean("whitelist");
		NBTTagList playerList = tag.getTagList("playerList", Constants.NBT.TAG_STRING);
		for (int i = 0; i < playerList.tagCount(); i++) {
			whiteListUUIDSet.add(playerList.getStringTagAt(i));
		}
		timer = tag.getInteger("timer");
	}
	
	private boolean isEntityOnList(EntityLivingBase player) {
		return whiteListUUIDSet.contains(player.getUniqueID().toString());
	}
	
	private void modifyList(String uuid) {
		if (whiteListUUIDSet.contains(uuid)) whiteListUUIDSet.remove(uuid);
		else whiteListUUIDSet.add(uuid);
	}
	
	@Override
	public void update() {
		if (timer > 0) {
			timer--;
			if (cooldown > 0) cooldown--;
			else if (sigil != null) {
				List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos.getX() - 6, pos.getY() - 3, pos.getZ() - 6, pos.getX() + 6, pos.getY() + 3, pos.getZ() + 6)).stream().filter(p -> isEntityOnList(p) == whiteList).collect(Collectors.toList());
				for (EntityLivingBase living : entities) {
					sigil.applyEffects(living);
				}
				cooldown = sigil.cooldown;
			}
		}
		else {
			if (!world.isRemote) ((WorldServer) world).spawnParticle(EnumParticleTypes.REDSTONE, pos.getX(), pos.getY(), pos.getZ(), 100, world.rand.nextGaussian() / 3, 0, world.rand.nextGaussian() / 3, 0);
			world.setBlockToAir(pos);
		}
	}
}
