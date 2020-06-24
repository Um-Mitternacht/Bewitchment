package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;

public class CurseReturnToSender extends Curse {

	public CurseReturnToSender() {
		super(new ResourceLocation(Bewitchment.MODID, "return_to_sender"), Arrays.asList(Util.get(ModObjects.white_sage), Util.get(ModObjects.salt), Util.get("nuggetSilver"), Util.get("nuggetIron"), Util.get(ModObjects.taglock)), false, true, CurseCondition.REACTION);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		return true;
	}
}
