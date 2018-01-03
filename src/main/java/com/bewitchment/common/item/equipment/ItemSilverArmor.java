package com.bewitchment.common.item.equipment;

import com.bewitchment.api.helper.IModelRegister;
import com.bewitchment.client.handler.ModelHandler;
import com.bewitchment.common.core.BewitchmentCreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by BerciTheBeast on 11.4.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class ItemSilverArmor extends ItemArmor implements IModelRegister {

	public ItemSilverArmor(String id, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndex, equipmentSlotIn);
		this.setMaxStackSize(1);
		setRegistryName(id);
		setUnlocalizedName(id);
		setCreativeTab(BewitchmentCreativeTabs.ITEMS_CREATIVE_TAB);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onEntityDamage(LivingHurtEvent event) {
		DamageSource source = event.getSource();

		Entity attacker = source.getTrueSource();
		if ((attacker instanceof EntityLivingBase) && ((EntityLivingBase) attacker).getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			event.setAmount(event.getAmount() * 0.95F);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModel() {
		ModelHandler.registerModel(this, 0);
	}

}
