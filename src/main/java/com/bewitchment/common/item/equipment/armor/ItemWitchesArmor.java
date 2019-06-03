package com.bewitchment.common.item.equipment.armor;

import com.bewitchment.Bewitchment;
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
	public ItemWitchesArmor(EntityEquipmentSlot slot) {
		super(ModObjects.ARMOR_WITCHES, 0, slot);
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return Bewitchment.MODID + ":textures/models/armor/witches" + (this == ModObjects.witches_hat && stack.getDisplayName().toLowerCase().contains("faith") ? "_variant" : "") + ".png";
	}
	
	@SideOnly(Side.CLIENT)
	@Nullable
	public ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default) {
		return new ModelWitchesArmor(slot);
	}
}