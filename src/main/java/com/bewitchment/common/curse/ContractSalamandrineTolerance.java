package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Curse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ContractSalamandrineTolerance extends Curse {
	public ContractSalamandrineTolerance() {
		super(new ResourceLocation(Bewitchment.MODID, "salamandrine_tolerance"), null, true, true, CurseCondition.HURT);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		LivingHurtEvent event0 = (LivingHurtEvent) event;
		if (event0.getSource() == DamageSource.IN_FIRE || event0.getSource() == DamageSource.ON_FIRE) {
			event0.setAmount(event0.getAmount() / 2);
			return true;
		}
		return false;
	}
}
