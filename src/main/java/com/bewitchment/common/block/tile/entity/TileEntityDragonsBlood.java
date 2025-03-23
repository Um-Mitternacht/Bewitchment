package com.bewitchment.common.block.tile.entity;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import com.bewitchment.common.item.ItemSigil;
import com.bewitchment.common.item.ItemTaglock;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class TileEntityDragonsBlood extends ModTileEntity implements ITickable {
    public ItemSigil sigil;
    public int cooldown = 0;
    public boolean whiteList;
    public Set<String> playerUUIDSet = new HashSet<>();

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
        TileEntityDragonsBlood te = this;
        if (world.getBlockState(pos).getBlock() == ModObjects.dragons_blood_door.door)
            te = world.getTileEntity(pos.down()) instanceof TileEntityDragonsBlood ? (TileEntityDragonsBlood) world.getTileEntity(pos.down()) : this;
        if (te.sigil == null) {
            if (player.getHeldItem(hand).getItem() instanceof ItemSigil) {
                te.sigil = (ItemSigil) player.inventory.decrStackSize(player.inventory.currentItem, 1).getItem();
                te.whiteList = te.sigil.positive;
                te.playerUUIDSet.add(player.getUniqueID().toString());
                markDirty();
                return true;
            }
        } else if (player.getHeldItem(hand).getItem() instanceof ItemTaglock && player.getHeldItem(hand).hasTagCompound()) {
            modifyList(te, player.getHeldItem(hand).getTagCompound().getString("boundId"));
            markDirty();
            return true;
        } else if (te.cooldown <= 0 && (isPlayerOnList(te, player) == te.whiteList)) {
            te.sigil.applyEffects(player);
            te.cooldown = te.sigil.cooldown;
            markDirty();
            return true;
        }
        return super.activate(world, pos, player, hand, face);
    }

    private void writeUpdateTag(NBTTagCompound tag) {
        tag.setString("sigil", sigil == null ? "" : sigil.getRegistryName().toString());
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
        cooldown = tag.getInteger("cooldown");
        whiteList = tag.getBoolean("whitelist");
        NBTTagList playerList = tag.getTagList("playerList", Constants.NBT.TAG_STRING);
        for (int i = 0; i < playerList.tagCount(); i++) {
            playerUUIDSet.add(playerList.getStringTagAt(i));
        }
    }

    private boolean isPlayerOnList(TileEntityDragonsBlood te, EntityPlayer player) {
        return te.playerUUIDSet.contains(player.getUniqueID().toString());
    }

    private void modifyList(TileEntityDragonsBlood te, String uuid) {
        if (te.playerUUIDSet.contains(uuid)) te.playerUUIDSet.remove(uuid);
        else te.playerUUIDSet.add(uuid);
    }

    @Override
    public void update() {
        if (cooldown > 0) cooldown--;
    }
}
