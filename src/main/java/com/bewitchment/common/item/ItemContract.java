package com.bewitchment.common.item;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Contract;
import com.bewitchment.api.registry.Curse;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ItemContract extends Item {
    public ItemContract() {
        super();
        Util.registerItem(this, "contract", Collections.singletonList(s -> s.hasTagCompound() && s.getTagCompound().hasKey("boundName")));
        setMaxStackSize(1);
    }

    public boolean complete(ItemStack stack) {
        if (stack.getItem() instanceof ItemContract && stack.hasTagCompound()) {
            NBTTagCompound tag = stack.getTagCompound();
            if (tag.hasKey("mobsComplete") && tag.hasKey("mobsTotal") && tag.getInteger("mobsTotal") - tag.getInteger("mobsComplete") == 0) {
                return true;
            }
            if (tag.hasKey("items")) {
                NBTTagList list = tag.getTagList("items", Constants.NBT.TAG_COMPOUND);
                boolean flag = true;
                for (int i = 0; i < list.tagCount(); i++) {
                    NBTTagCompound compound = list.getCompoundTagAt(i);
                    if (compound.getInteger("amountTotal") - compound.getInteger("amountComplete") != 0) flag = false;
                }
                return flag;
            }
        }
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.hasTagCompound() && heldItem.getTagCompound().hasKey("contract")) {
            Contract temp = (Contract) GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(heldItem.getTagCompound().getString("contract")));
            if (!heldItem.getTagCompound().hasKey("boundId") && !heldItem.getTagCompound().hasKey("boundName")) {
                heldItem.getTagCompound().setString("boundId", player.getPersistentID().toString());
                heldItem.getTagCompound().setString("boundName", player.getName());
                player.setHeldItem(hand, heldItem);
                if (world.isRemote)
                    world.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 10.0F, 1.0F);
                return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
            } else if (heldItem.getTagCompound().hasKey("boundId") && Util.findPlayer(heldItem.getTagCompound().getString("boundId")) == player && complete(heldItem) && player.hasCapability(ExtendedPlayer.CAPABILITY, null)) {
                if (temp != null) {
                    ItemStack offhand = player.getHeldItem(hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                    if (temp.isPositive()) {
                        player.getCapability(ExtendedPlayer.CAPABILITY, null).addCurse(temp, 7);
                        if (!world.isRemote) {
                            for (int i = 0; i < 128; i++)
                                Bewitchment.network.sendTo(new SpawnParticle(EnumParticleTypes.FLAME, player.posX + world.rand.nextGaussian() / 4, player.posY + 1.6, player.posZ + world.rand.nextGaussian() / 4), (EntityPlayerMP) player);
                            player.getHeldItem(hand).shrink(1);
                        }
                        return new ActionResult<>(EnumActionResult.PASS, ItemStack.EMPTY);
                    } else if (offhand.getItem() instanceof ItemTaglock && offhand.hasTagCompound() && offhand.getTagCompound().hasKey("boundId")) {
                        EntityPlayer target = Util.findPlayer(offhand.getTagCompound().getString("boundId"));
                        if (target != null && target.hasCapability(ExtendedPlayer.CAPABILITY, null)) {
                            target.getCapability(ExtendedPlayer.CAPABILITY, null).addCurse(temp, 1);
                            player.getHeldItem(hand).shrink(1);
                            return new ActionResult<>(EnumActionResult.PASS, ItemStack.EMPTY);
                        }
                    }
                }
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound.hasKey("contract")) {
                tooltip.add(TextFormatting.DARK_RED + I18n.format(compound.getString("contract").replace(":", ".")));
            }
            if (compound.hasKey("boundName")) {
                String boundName = compound.getString("boundName");
                tooltip.add(I18n.format("tooltip.bewitchment.contract_bound", boundName));
            }
            if (compound.hasKey("items")) {
                NBTTagList list = compound.getTagList("items", Constants.NBT.TAG_COMPOUND);
                for (int i = 0; i < list.tagCount(); i++) {
                    NBTTagCompound tag = list.getCompoundTagAt(i);
                    tooltip.add(I18n.format("tooltip.bewitchment.contract_items", tag.getInteger("amountComplete"), tag.getInteger("amountTotal"), I18n.format(ForgeRegistries.ITEMS.getValue(new ResourceLocation(tag.getString("item"))).getTranslationKey() + ".name")));
                }
            }
            if (compound.hasKey("mobsTotal") && compound.hasKey("mobsComplete") && compound.hasKey("contract")) {
                Contract temp = (Contract) GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(stack.getTagCompound().getString("contract")));
                tooltip.add(I18n.format("tooltip." + temp.getRegistryName().toString().replace(":", ".") + "_entities", compound.getInteger("mobsComplete"), compound.getInteger("mobsTotal")));
            }
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return complete(stack);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
