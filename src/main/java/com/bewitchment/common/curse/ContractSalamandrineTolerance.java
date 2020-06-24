package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Contract;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;

public class ContractSalamandrineTolerance extends Contract {
	public ContractSalamandrineTolerance() {
		super(new ResourceLocation(Bewitchment.MODID, "salamandrine_tolerance"), true, true, CurseCondition.HURT, null, Arrays.asList(Items.BLAZE_ROD, Items.GHAST_TEAR));
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		LivingHurtEvent event0 = (LivingHurtEvent) event;
		if (event0.getSource() == DamageSource.IN_FIRE || event0.getSource() == DamageSource.ON_FIRE || event0.getSource() == DamageSource.LAVA) {
			event0.setAmount(event0.getAmount() / 3);
			return true;
		}
		return false;
	}
}
