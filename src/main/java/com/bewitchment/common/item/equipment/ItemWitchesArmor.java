package com.bewitchment.common.item.equipment;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.client.render.entity.model.ModelWitchesArmor;
import com.bewitchment.common.core.ModCreativeTabs;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemWitchesArmor extends ItemArmor implements IModelRegister {
	
	private final ModelWitchesArmor model = new ModelWitchesArmor();

	public ItemWitchesArmor(String id, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndex, equipmentSlotIn);
		this.setMaxStackSize(1);
		setRegistryName(id);
		setUnlocalizedName(id);
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
				
				model.hat1.showModel = armorSlot == EntityEquipmentSlot.HEAD;
				model.body.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				model.armLeft.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				model.armRight.showModel = armorSlot == EntityEquipmentSlot.CHEST;
				model.legLeft.showModel = armorSlot == EntityEquipmentSlot.LEGS;
				model.legRight.showModel = armorSlot == EntityEquipmentSlot.LEGS;

				model.isChild = _default.isChild;
				model.isRiding = _default.isRiding;
				model.isSneak = _default.isSneak;
				model.rightArmPose = _default.rightArmPose;
				model.leftArmPose = _default.leftArmPose;

				return model;
			}
		}

		return null;

	}

}
