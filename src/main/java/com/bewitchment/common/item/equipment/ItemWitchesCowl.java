package com.bewitchment.common.item.equipment;

import com.bewitchment.client.core.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.client.render.entity.model.ModelWitchsHood;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 2/17/2019.
 */
public class ItemWitchesCowl extends ItemArmor implements IModelRegister {
	public ItemWitchesCowl(String id, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndex, equipmentSlotIn);
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
	public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, EntityEquipmentSlot slot, ModelBiped biped) {
		if (stack != ItemStack.EMPTY) {
			if (stack.getItem() instanceof ItemArmor) {
				ModelWitchsHood armourModel = new ModelWitchsHood(slot);

				armourModel.isSneak = biped.isSneak;
				armourModel.isRiding = biped.isRiding;
				armourModel.isChild = biped.isChild;
				armourModel.rightArmPose = biped.rightArmPose;
				armourModel.leftArmPose = biped.leftArmPose;

				return armourModel;
			}
		}

		return null;
	}
}
