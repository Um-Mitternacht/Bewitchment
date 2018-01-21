package com.bewitchment.common.divination;

import com.bewitchment.api.capability.EnumInfusionType;
import com.bewitchment.api.divination.TarotHandler;
import com.bewitchment.common.core.capability.divination.CapabilityDivination;
import com.bewitchment.common.core.capability.energy.DummyIEnergy;
import com.bewitchment.common.core.capability.energy.EnergyHandler;
import com.bewitchment.common.divination.tarots.QuickTarot;

public class ModTarots {

	private ModTarots() {
	}

	public static void init() {
		TarotHandler.registerTarot(new QuickTarot("enderman", p -> EnergyHandler.getEnergy(p).orElseGet(() -> DummyIEnergy.INSTANCE).getType() == EnumInfusionType.END, p -> false, p -> -1));
		TarotHandler.registerTarot(new QuickTarot("diamonds", p -> p.getCapability(CapabilityDivination.CAPABILITY, null).getFortune() != null, p -> p.getCapability(CapabilityDivination.CAPABILITY, null).getFortune().isNegative(), p -> -1));
		TarotHandler.registerTarot(new QuickTarot("iron_golem", p -> EnergyHandler.getEnergy(p).orElseGet(() -> DummyIEnergy.INSTANCE).getType() == EnumInfusionType.OVERWORLD, p -> false, p -> -1));
		TarotHandler.registerTarot(new QuickTarot("wither_skeleton", p -> EnergyHandler.getEnergy(p).orElseGet(() -> DummyIEnergy.INSTANCE).getType() == EnumInfusionType.NETHER, p -> false, p -> -1));
		TarotHandler.registerTarot(new QuickTarot("star", p -> EnergyHandler.getEnergy(p).orElseGet(() -> DummyIEnergy.INSTANCE).getType() == EnumInfusionType.DREAM, p -> false, p -> -1));

		TarotHandler.registerTarot(new QuickTarot("filler1", p -> true, p -> false, p -> 1));
		TarotHandler.registerTarot(new QuickTarot("filler2", p -> true, p -> true, p -> 2));
		TarotHandler.registerTarot(new QuickTarot("filler3", p -> true, p -> false, p -> 3));
		TarotHandler.registerTarot(new QuickTarot("filler4", p -> true, p -> true, p -> 4));
		TarotHandler.registerTarot(new QuickTarot("filler5", p -> true, p -> false, p -> 5));
		TarotHandler.registerTarot(new QuickTarot("filler6", p -> true, p -> true, p -> 6));
	}

}
