package com.bewitchment.common.content.tarot;

import com.bewitchment.api.infusion.DefaultInfusions;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import com.bewitchment.common.content.infusion.capability.InfusionCapability;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import net.minecraft.entity.player.EntityPlayer;

import java.util.function.Predicate;

public class ModTarots {

	private ModTarots() {
	}

	public static void init() {
		Predicate<EntityPlayer> random = p -> p.getRNG().nextBoolean();
		TarotHandler.registerTarot(new QuickTarot("enderman", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.END, null, null));
		TarotHandler.registerTarot(new QuickTarot("diamonds", p -> p.getCapability(CapabilityFortune.CAPABILITY, null).getFortune() != null, p -> p.getCapability(CapabilityFortune.CAPABILITY, null).getFortune().isNegative(), null));
		TarotHandler.registerTarot(new QuickTarot("iron_golem", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.OVERWORLD, null, null));
		TarotHandler.registerTarot(new QuickTarot("wither_skeleton", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.NETHER, null, null));
		TarotHandler.registerTarot(new QuickTarot("star", p -> p.getCapability(InfusionCapability.CAPABILITY, null).getType() == DefaultInfusions.DREAM, null, null));
		TarotHandler.registerTarot(new QuickTarot("nitwit", p -> p.getCapability(IMagicPowerContainer.CAPABILITY, null).getMaxAmount() / 800 > 0, null, p -> p.getCapability(IMagicPowerContainer.CAPABILITY, null).getMaxAmount() / 800));
		TarotHandler.registerTarot(new QuickTarot("moon", p -> p.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE || p.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF, p -> p.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF, p -> p.getCapability(CapabilityTransformation.CAPABILITY, null).getLevel()));
		TarotHandler.registerTarot(new QuickTarot("silver_sword", p -> p.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.HUNTER, p -> false, p -> p.getCapability(CapabilityTransformation.CAPABILITY, null).getLevel()));
		TarotHandler.registerTarot(new QuickTarot("hermit", p -> p.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.SPECTRE, null, p -> p.getCapability(CapabilityTransformation.CAPABILITY, null).getLevel()));

		TarotHandler.registerTarot(new QuickTarot("ender_dragon", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("evoker", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("guardian", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("illusioner", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("stronghold", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("sun", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("mounts", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("world", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("witch", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("wither", random, null, null));
		TarotHandler.registerTarot(new QuickTarot("zombie", random, null, null));
	}

}
