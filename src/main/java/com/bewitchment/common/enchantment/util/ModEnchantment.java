package com.bewitchment.common.enchantment.util;

import com.bewitchment.Bewitchment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ModEnchantment extends Enchantment {
    private final int maxLevel;

    protected ModEnchantment(String name, Rarity rarity, int maxLevel, EnumEnchantmentType type, EntityEquipmentSlot... slots) {
        super(rarity, type, slots);
        setRegistryName(new ResourceLocation(Bewitchment.MODID, name));
        setName(getRegistryName().toString().replace(":", "."));
        this.maxLevel = maxLevel;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    protected int getMaxLevelOnEntity(EntityLivingBase living) {
        int max = 0;
        for (ItemStack stack : getEntityEquipment(living))
            if (EnchantmentHelper.getEnchantmentLevel(this, stack) > max)
                max = EnchantmentHelper.getEnchantmentLevel(this, stack);
        return max;
    }

    protected int getTotalLevelOnEntity(EntityLivingBase living) {
        int total = 0;
        for (ItemStack stack : getEntityEquipment(living)) total += EnchantmentHelper.getEnchantmentLevel(this, stack);
        return total;
    }
}