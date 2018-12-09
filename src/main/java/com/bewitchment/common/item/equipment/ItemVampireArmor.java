package com.bewitchment.common.item.equipment;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.client.render.entity.model.ModelVampireArmor;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemVampireArmor extends ItemArmor implements IModelRegister {

	public ItemVampireArmor(String id, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndex, equipmentSlotIn);
		this.setMaxStackSize(1);
		setRegistryName(id);
		setTranslationKey(id);
		setCreativeTab(ModCreativeTabs.ITEMS_CREATIVE_TAB);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (itemStack != ItemStack.EMPTY) {
			if (itemStack.getItem() instanceof ItemArmor) {

				ModelVampireArmor.INSTANCE.hatAnchor.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				ModelVampireArmor.INSTANCE.body.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				ModelVampireArmor.INSTANCE.capeCollarBack1.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				ModelVampireArmor.INSTANCE.capeCollarRight1.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				ModelVampireArmor.INSTANCE.capeCollarLeft1.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				ModelVampireArmor.INSTANCE.capeBack1.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				ModelVampireArmor.INSTANCE.armLeft.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				ModelVampireArmor.INSTANCE.armRight.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				ModelVampireArmor.INSTANCE.legLeft.showModel = armorSlot == EntityEquipmentSlot.LEGS;
				ModelVampireArmor.INSTANCE.legRight.showModel = armorSlot == EntityEquipmentSlot.LEGS;

				ModelVampireArmor.INSTANCE.isChild = _default.isChild;
				ModelVampireArmor.INSTANCE.isRiding = _default.isRiding;
				ModelVampireArmor.INSTANCE.isSneak = _default.isSneak;
				ModelVampireArmor.INSTANCE.rightArmPose = _default.rightArmPose;
				ModelVampireArmor.INSTANCE.leftArmPose = _default.leftArmPose;

				return ModelVampireArmor.INSTANCE;
			}
		}

		return null;

	}

}
