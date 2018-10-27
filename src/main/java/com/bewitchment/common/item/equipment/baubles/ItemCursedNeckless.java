package com.bewitchment.common.item.equipment.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.IBaublesItemHandler;
import com.bewitchment.common.content.enchantments.BaublesEnchantment;
import com.bewitchment.common.core.ModCreativeTabs;
import com.bewitchment.common.item.ItemMod;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentBindingCurse;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCursedNeckless extends ItemMod implements IBauble {

    private int enchantability = 0;
    private BaubleType type = BaubleType.AMULET;

    public ItemCursedNeckless(String id) {
        super(id);
        this.setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
        this.setMaxStackSize(1);
    }

    @Override
    public boolean isDamageable() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        if (!stack.isItemEnchanted()) {
            tooltip.add(TextFormatting.AQUA + I18n.format("witch.tooltip.cursedNeckless_description.name"));
        }
    }


    @Override
    public int getItemEnchantability(ItemStack stack) {
        return enchantability;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return !EnchantmentHelper.hasBindingCurse(itemstack);
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return type;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment == Enchantments.BINDING_CURSE;
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
        player.addPotionEffect(new PotionEffect(Potion.getPotionById(25), 100, 150));

    }
}