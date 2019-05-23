package com.bewitchment.common.item.equipment.baubles;

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
	
	@SideOnly(Side.CLIENT)
	@Nullable
	public ModelBiped getArmorModel(EntityLivingBase living, ItemStack stack, EntityEquipmentSlot slot, ModelBiped _default) {
		if (!stack.isEmpty()) {
			ModelWitchesArmor model = new ModelWitchesArmor();
			model.hat1.showModel = slot == EntityEquipmentSlot.HEAD && stack.getItem() == ModObjects.witches_hat;
			model.hood01.showModel = slot == EntityEquipmentSlot.HEAD && stack.getItem() == ModObjects.witches_cowl;
			model.bipedBody.showModel = slot == EntityEquipmentSlot.CHEST;
			model.armLeft.showModel = slot == EntityEquipmentSlot.CHEST;
			model.armRight.showModel = slot == EntityEquipmentSlot.CHEST;
			model.legLeft.showModel = slot == EntityEquipmentSlot.LEGS;
			model.legRight.showModel = slot == EntityEquipmentSlot.LEGS;
			
			model.isChild = _default.isChild;
			model.isRiding = _default.isRiding;
			model.isSneak = _default.isSneak;
			model.leftArmPose = _default.leftArmPose;
			model.rightArmPose = _default.rightArmPose;
			return model;
		}
		return null;
	}
	
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return Bewitchment.MODID + ":textures/models/armor/witches" + (this == ModObjects.witches_hat && stack.getDisplayName().toLowerCase().contains("faith") ? "_variant" : "") + ".png";
		//		return this == ModObjects.witches_hat && stack.getDisplayName().toLowerCase().contains("faith") ? Bewitchment.MODID +  ":/textures/armor/witches.png" : null;
	}
}
