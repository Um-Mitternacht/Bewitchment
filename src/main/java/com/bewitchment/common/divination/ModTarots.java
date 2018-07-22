package com.bewitchment.common.divination;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.infusion.IInfusionCapability;
import com.bewitchment.api.mp.IMagicPowerStorage;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.divination.tarots.QuickTarot;

public class ModTarots {

	private ModTarots() {
	}

	public static void init() {
		TarotHandler.registerTarot(new QuickTarot("enderman", p -> p.getCapability(IInfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.END, null, null));
		TarotHandler.registerTarot(new QuickTarot("diamonds", p -> p.getCapability(CapabilityDivination.CAPABILITY, null).getFortune() != null, p -> p.getCapability(CapabilityDivination.CAPABILITY, null).getFortune().isNegative(), null));
		TarotHandler.registerTarot(new QuickTarot("iron_golem", p -> p.getCapability(IInfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.OVERWORLD, null, null));
		TarotHandler.registerTarot(new QuickTarot("wither_skeleton", p -> p.getCapability(IInfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.NETHER, null, null));
		TarotHandler.registerTarot(new QuickTarot("star", p -> p.getCapability(IInfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.DREAM, null, null));
		TarotHandler.registerTarot(new QuickTarot("nitwit", p -> p.getCapability(IMagicPowerStorage.CAPABILITY, null).getMaxAmount() / 800 > 0, null, p -> p.getCapability(IMagicPowerStorage.CAPABILITY, null).getMaxAmount() / 800));
		TarotHandler.registerTarot(new QuickTarot("moon", p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE || p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getLevel()));
		TarotHandler.registerTarot(new QuickTarot("silver_sword", p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.HUNTER, p -> false, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getLevel()));
		TarotHandler.registerTarot(new QuickTarot("hermit", p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.SPECTRE, null, p -> p.getCapability(CapabilityTransformationData.CAPABILITY, null).getLevel()));
	}

}
