package com.bewitchment.common.potion;

import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.UUID;

/**
 * Created by Joseph on 4/2/2020.
 */

//Todo: Everything.
public class PotionCorrosion extends ModPotion {
	
	public static final UUID MODIFIER_UUID = UUID.fromString("e8506ffe-e2b4-4f19-8669-becb8e3eb666");
	public static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Armor reduction", 1, 1)).setSaved(true);
	
	protected PotionCorrosion() {
		super("corrosion", true, 0x0BDA51);
	}
	
	@SubscribeEvent
	public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
		
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this)) {
			event.getEntityLiving().getEntityAttribute(SharedMonsterAttributes.ARMOR).getModifier(UUID.fromString("e8506ffe-e2b4-4f19-8669-becb8e3eb666"));
		}
	}
}
