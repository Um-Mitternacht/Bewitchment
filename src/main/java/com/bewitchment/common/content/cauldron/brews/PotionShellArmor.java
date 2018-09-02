package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.content.cauldron.BrewMod;
import com.bewitchment.common.core.helper.AttributeModifierModeHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class PotionShellArmor extends BrewMod {

	private static final UUID modifierUUID = UUID.fromString("54a14a6a-c4be-4113-8998-4073b97344af");

	public PotionShellArmor() {
		super("shell_armor", false, 0xCCFF00, false, 20 * 90);
		MinecraftForge.EVENT_BUS.register(this);
		this.setIconIndex(2, 1);
	}

	@Override
	public void applyAttributesModifiersToEntity(EntityLivingBase entity, AbstractAttributeMap attributeMapIn, int amplifier) {
		super.applyAttributesModifiersToEntity(entity, attributeMapIn, amplifier);
		IAttributeInstance armor_attr = entity.getEntityAttribute(SharedMonsterAttributes.ARMOR);
		armor_attr.removeModifier(modifierUUID);
		armor_attr.applyModifier(new AttributeModifier(modifierUUID, "PotionShellArmor", amplifier + 1, AttributeModifierModeHelper.ADD));
	}

	@Override
	public void removeAttributesModifiersFromEntity(EntityLivingBase entityLivingBaseIn, AbstractAttributeMap attributeMapIn, int amplifier) {
		super.removeAttributesModifiersFromEntity(entityLivingBaseIn, attributeMapIn, amplifier);
		entityLivingBaseIn.getEntityAttribute(SharedMonsterAttributes.ARMOR).removeModifier(modifierUUID);
	}

	@SubscribeEvent
	public void onHurt(LivingHurtEvent evt) {
		PotionEffect pe = evt.getEntityLiving().getActivePotionEffect(this);
		if (pe != null && evt.getSource().getTrueSource() != null && !evt.getSource().damageType.equals("thorns")) {
			evt.getSource().getTrueSource().attackEntityFrom(DamageSource.causeThornsDamage(evt.getEntityLiving()), (pe.getAmplifier() / 8f) * evt.getAmount());
		}
	}

}
