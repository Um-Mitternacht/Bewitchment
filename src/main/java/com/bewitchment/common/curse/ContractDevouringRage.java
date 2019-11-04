package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Curse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.Event;


public class ContractDevouringRage extends Curse {
	public ContractDevouringRage() {
		super(new ResourceLocation(Bewitchment.MODID, "devouring_rage"), null, true, true, CurseCondition.KILL);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		LivingDeathEvent event0 = (LivingDeathEvent) event;
		if (event0.getEntityLiving() instanceof EntityAnimal) {
			target.heal(3f);
			target.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 20, 9));
			return true;
		}
		return false;
	}
}
