package com.bewitchment.common.item.equipment.armor;

import com.bewitchment.client.model.armor.ModelWitchesArmor;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class ItemWitchesArmor extends ItemArmor {
    private final String texture;

    public ItemWitchesArmor(EntityEquipmentSlot slot, String texture) {
        super(ModObjects.ARMOR_WITCHES, 0, slot);
        this.texture = texture;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return texture + (this == ModObjects.witches_hat && stack.getDisplayName().toLowerCase().contains("faith") ? "_variant" : "") + ".png";
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default) {
        ModelBiped model = ModelWitchesArmor.getInstance(slot, stack.getItem() == ModObjects.witches_hat || stack.getItem() == ModObjects.besmirched_hat || stack.getItem() == ModObjects.alchemist_hat || stack.getItem() == ModObjects.green_witch_hat);
        model.setModelAttributes(_default);
        return model;
    }
}