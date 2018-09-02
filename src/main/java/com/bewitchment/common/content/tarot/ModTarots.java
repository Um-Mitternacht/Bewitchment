package com.bewitchment.common.content.tarot;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import com.bewitchment.common.content.infusion.capability.InfusionCapability;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;

public class ModTarots {

	private ModTarots() {
	}

	public static void init() {
		TarotHandler.registerTarot(new QuickTarot("enderman", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.END, null, null));
		TarotHandler.registerTarot(new QuickTarot("diamonds", p -> p.getCapability(CapabilityFortune.CAPABILITY, null).getFortune() != null, p -> p.getCapability(CapabilityFortune.CAPABILITY, null).getFortune().isNegative(), null));
		TarotHandler.registerTarot(new QuickTarot("iron_golem", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.OVERWORLD, null, null));
		TarotHandler.registerTarot(new QuickTarot("wither_skeleton", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.NETHER, null, null));
		TarotHandler.registerTarot(new QuickTarot("star", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.DREAM, null, null));
		TarotHandler.registerTarot(new QuickTarot("nitwit", p -> p.getCapability(IMagicPowerContainer.CAPABILITY, null).getMaxAmount() / 800 > 0, null, p -> p.getCapability(IMagicPowerContainer.CAPABILITY, null).getMaxAmount() / 800));
		TarotHandler.registerTarot(new QuickTarot("moon", p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE || p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getLevel()));
		TarotHandler.registerTarot(new QuickTarot("silver_sword", p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.HUNTER, p -> false, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getLevel()));
		TarotHandler.registerTarot(new QuickTarot("hermit", p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.SPECTRE, null, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getLevel()));
	}

}
