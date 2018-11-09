package com.bewitchment.common.content.transformation.werewolf;

import com.bewitchment.api.event.HotbarActionCollectionEvent;
import com.bewitchment.api.event.HotbarActionTriggeredEvent;
import com.bewitchment.api.event.TransformationModifiedEvent;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.actionbar.ModAbilities;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.core.helper.AttributeModifierModeHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

import java.util.UUID;

@Mod.EventBusSubscriber
public class WerewolfAbilityHandler {

	private static final UUID werewolf_strength = UUID.fromString("289b7d2e-d464-48db-83a4-dd3c15e5c48e");
	private static final UUID werewolf_life = UUID.fromString("ffc356b5-e6e9-40b1-b9d2-a0b7a5f9ff4e");

	@SubscribeEvent
	public static void updateAttributes(TransformationModifiedEvent evt) {
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

	@SubscribeEvent
	public static void attachAbilities(HotbarActionCollectionEvent evt) {
		CapabilityTransformation data = evt.player.getCapability(CapabilityTransformation.CAPABILITY, null);
		if (data.getType() == DefaultTransformations.WEREWOLF) {
			evt.getList().add(ModAbilities.WOLF_SHIFT);
			if (data.getLevel() >= 2) {
				evt.getList().add(ModAbilities.NIGHT_VISION_WEREWOLF);
			}

			if (data.getLevel() >= 5) {
				evt.getList().add(ModAbilities.HOWL);
			}
		}
	}

	@SubscribeEvent
	public static void abilityHandler(PlayerTickEvent evt) {
		if (evt.phase == Phase.START && !evt.player.world.isRemote && evt.player.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF) {
			PotionEffect nv = evt.player.getActivePotionEffect(MobEffects.NIGHT_VISION);
			if ((nv == null || nv.getDuration() <= 220) && evt.player.getCapability(CapabilityWerewolfStatus.CAPABILITY, null).nightVision) {
				evt.player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 300, 0, true, false));
			}
		}
	}

	@SubscribeEvent
	public static void onHotbarAbilityToggled(HotbarActionTriggeredEvent evt) {
		CapabilityTransformation data = evt.player.getCapability(CapabilityTransformation.CAPABILITY, null);
		if (data.getType() != DefaultTransformations.WEREWOLF) {
			return;
		}
		if (evt.action == ModAbilities.NIGHT_VISION_WEREWOLF) {
			CapabilityWerewolfStatus ww = evt.player.getCapability(CapabilityWerewolfStatus.CAPABILITY, null);
			ww.setNightVision(!ww.nightVision);
		}
		if (evt.action == ModAbilities.WOLF_SHIFT) {
			CapabilityWerewolfStatus cap = evt.player.getCapability(CapabilityWerewolfStatus.CAPABILITY, null);
			cap.changeForm(evt.player.isSneaking());
		}
		if (evt.action == ModAbilities.HOWL) {
			evt.player.sendStatusMessage(new TextComponentString("Howl ability not available yet"), true);
		}
	}

}
