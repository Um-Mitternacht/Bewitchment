package com.bewitchment.common.core.event;

import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.core.helper.AttributeModifierModeHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

public class WerewolfAbilityHandler {

	private static final UUID werewolf_strength = UUID.fromString("289b7d2e-d464-48db-83a4-dd3c15e5c48e");
	private static final UUID werewolf_life = UUID.fromString("ffc356b5-e6e9-40b1-b9d2-a0b7a5f9ff4e");

	@SubscribeEvent
	public void updateAttributes(TransformationModifiedEvent evt) {
		IAttributeInstance dmg = evt.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		IAttributeInstance life = evt.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
		float healthPC = evt.getEntityPlayer().getHealth() / evt.getEntityPlayer().getMaxHealth();
		dmg.removeModifier(werewolf_strength);
		life.removeModifier(werewolf_life);
		if (evt.type == DefaultTransformations.WEREWOLF) {
			dmg.applyModifier(new AttributeModifier(werewolf_strength, "ww-strength", 0.5d * evt.level, AttributeModifierModeHelper.ADD));
			life.applyModifier(new AttributeModifier(werewolf_life, "ww-life", 2 * evt.level, AttributeModifierModeHelper.ADD));
			evt.getEntityPlayer().setHealth(evt.getEntityPlayer().getMaxHealth() * healthPC);
		}
	}

}
