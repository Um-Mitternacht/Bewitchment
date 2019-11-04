package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Curse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ContractBerserker extends Curse {
	public ContractBerserker() {
		super(new ResourceLocation(Bewitchment.MODID, "beserker"), null, true, true, CurseCondition.DAMAGE);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		LivingHurtEvent event0 = (LivingHurtEvent) event;
		event0.setAmount(event0.getAmount() / (target.getHealth() / target.getMaxHealth()));
		return true;
	}
}
