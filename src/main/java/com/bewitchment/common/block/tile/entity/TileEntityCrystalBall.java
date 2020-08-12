package com.bewitchment.common.block.tile.entity;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.common.block.tile.entity.util.TileEntityAltarStorage;
import com.bewitchment.common.item.ItemTaglock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@SuppressWarnings("ConstantConditions")
public class TileEntityCrystalBall extends TileEntityAltarStorage {
    @Override
    public boolean activate(World world, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing face) {
        if (!world.isRemote) {
            if (player.getHeldItem(hand).getItem() instanceof ItemTaglock) {
                if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, 1000)) {
                    NBTTagCompound tag = player.getHeldItem(hand).getTagCompound();
                    if (tag != null && tag.hasKey("boundId")) {
                        if (Util.findPlayer(UUID.fromString(tag.getString("boundId"))) != null) {
                            sendTarotMsg(player, UUID.fromString(tag.getString("boundId")));
                        } else
                            player.sendStatusMessage(new TextComponentTranslation("tarot.player_offline", tag.getString("boundName")), true);
                    } else player.sendStatusMessage(new TextComponentTranslation("tarot.no_player"), true);
                } else player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
            } else if (MagicPower.attemptDrain(altarPos != null ? world.getTileEntity(altarPos) : null, player, 750)) {
                ExtendedPlayer cap = player.getCapability(ExtendedPlayer.CAPABILITY, null);
                if (cap.getFortune() == null) {
                    List<Fortune> valid = GameRegistry.findRegistry(Fortune.class).getValuesCollection().stream().filter(f -> f.isValid(player)).collect(Collectors.toList());
                    if (!valid.isEmpty()) {
                        cap.setFortune(valid.get(player.getRNG().nextInt(valid.size())));
                        int timeTil = player.getRNG().nextInt(cap.getFortune().getMaxTime() - cap.getFortune().getMinTime()) + cap.getFortune().getMinTime();
                        long milli = timeTil * 20 * 1000;
                        cap.setFortuneExpiration(System.currentTimeMillis() + milli);
                        ExtendedPlayer.syncToClient(player);
                        player.sendStatusMessage(new TextComponentTranslation("fortune." + cap.getFortune().getRegistryName().toString().replace(":", ".")), true); //maybe replace that true with false so that the message can be re-read?
                    } else player.sendStatusMessage(new TextComponentTranslation("fortune.no_fortune"), true);
                } else
                    player.sendStatusMessage(new TextComponentTranslation("fortune.has_fortune", player.getDisplayName(), new TextComponentTranslation("fortune." + cap.getFortune().getRegistryName().toString().replace(":", ".")).getFormattedText()), true);
            } else player.sendStatusMessage(new TextComponentTranslation("altar.no_power"), true);
        }
        return true;
    }

    private void sendTarotMsg(EntityPlayer player, UUID uuid) {
        EntityPlayer tagPlayer = Util.findPlayer(uuid);
        ExtendedPlayer cap = tagPlayer.getCapability(ExtendedPlayer.CAPABILITY, null);
        if (BewitchmentAPI.hasColdIronGear(tagPlayer))
            player.sendStatusMessage(new TextComponentTranslation("tarot.is_shrouded", tagPlayer.getDisplayName()), true);
        else switch (tagPlayer.getRNG().nextInt(11)) {
            case 0:
                // Chance for no reveal
                player.sendStatusMessage(new TextComponentTranslation("tarot.no_tarot", tagPlayer.getDisplayName()), true);
                break;
            case 1:
                // Check if player has pets *WIP*
                if (BewitchmentAPI.hasPets(tagPlayer)) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.has_pets", tagPlayer.getDisplayName()), true);
                } else {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.no_pets", tagPlayer.getDisplayName()), true);
                }
                break;
            case 2:
                // Check for good/back/no fortune on player
                if (cap.getFortune() != null) {
                    if (cap.getFortune().isNegative()) {
                        player.sendStatusMessage(new TextComponentTranslation("tarot.bad_fortune", tagPlayer.getDisplayName()), true);
                    } else
                        player.sendStatusMessage(new TextComponentTranslation("tarot.good_fortune", tagPlayer.getDisplayName()), true);
                } else
                    player.sendStatusMessage(new TextComponentTranslation("tarot.no_fortune", tagPlayer.getDisplayName()), true);
                break;
            //TODO: Make this work
            case 3:
                // Check if player has non-passive effects on
                if (BewitchmentAPI.hasEffects(tagPlayer)) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.has_effect", tagPlayer.getDisplayName()), true);
                } else {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.no_effect", tagPlayer.getDisplayName()), true);
                }
            case 4:
                // Display how many mobs the player has killed.
                player.sendStatusMessage(new TextComponentTranslation("tarot.mobkills", tagPlayer.getDisplayName(), cap.getMobsKilled()), true);
                break;
            case 5:
                if (BewitchmentAPI.defeatedBoss(tagPlayer)) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.has_boss", tagPlayer.getDisplayName()), true);
                } else {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.no_boss", tagPlayer.getDisplayName()), true);
                }
                break;
            case 6:
                if (ExtendedWorld.playerPledgedToDemon(world, player, "leonard")) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.demon_pledged", tagPlayer.getDisplayName()), true);
                } else if (ExtendedWorld.playerPledgedToDemon(world, player, "baphomet")) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.demon_pledged", tagPlayer.getDisplayName()), true);
                } else {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.not_pledged", tagPlayer.getDisplayName()), true);
                }
                break;
            case 7:
                if (!cap.getCurses().isEmpty()) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_cursed", tagPlayer.getDisplayName()), true);
                } else if (cap.getCurses().isEmpty()) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_not_cursed", tagPlayer.getDisplayName()), true);
                }
                break;
            //TODO: Look in bauble inventories too
            case 8:
                if (tagPlayer.inventory.hasItemStack(new ItemStack(Item.getByNameOrId("poppet_" + "")))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_protected", tagPlayer.getDisplayName()), true);
                } else if (tagPlayer.inventory.hasItemStack(new ItemStack(Item.getByNameOrId("odinRing")))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_protected", tagPlayer.getDisplayName()), true);
                } else if (tagPlayer.inventory.hasItemStack(new ItemStack(Items.TOTEM_OF_UNDYING))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_protected", tagPlayer.getDisplayName()), true);
                } else if (tagPlayer.inventory.hasItemStack(new ItemStack(Item.getByNameOrId("totem_" + "")))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_protected", tagPlayer.getDisplayName()), true);
                } else if (!tagPlayer.inventory.hasItemStack(new ItemStack(Item.getByNameOrId("poppet_" + "")))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.not_protected", tagPlayer.getDisplayName()), true);
                } else if (!tagPlayer.inventory.hasItemStack(new ItemStack(Item.getByNameOrId("odinRing")))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.not_protected", tagPlayer.getDisplayName()), true);
                } else if (!tagPlayer.inventory.hasItemStack(new ItemStack(Items.TOTEM_OF_UNDYING))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.not_protected", tagPlayer.getDisplayName()), true);
                } else if (!tagPlayer.inventory.hasItemStack(new ItemStack(Item.getByNameOrId("totem_" + "")))) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.not_protected", tagPlayer.getDisplayName()), true);
                }
                break;
            case 9:
                // Display how many villagers or other players the player in question has killed.
                player.sendStatusMessage(new TextComponentTranslation("tarot.bloodshed", tagPlayer.getDisplayName(), cap.getPeopleKilled()), true);
                break;
            case 10:
                if (BewitchmentAPI.hasGreenWitchGear(tagPlayer)) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_hedgewitch", tagPlayer.getDisplayName()), true);
                } else if (BewitchmentAPI.hasBesmirchedGear(tagPlayer)) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_besmirched", tagPlayer.getDisplayName()), true);
                } else if (BewitchmentAPI.hasAlchemistGear(tagPlayer)) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_alchemist", tagPlayer.getDisplayName()), true);
                } else if (BewitchmentAPI.hasWitchesGear(tagPlayer)) {
                    player.sendStatusMessage(new TextComponentTranslation("tarot.is_witch", tagPlayer.getDisplayName()), true);
                }
                break;

        }
    }
}