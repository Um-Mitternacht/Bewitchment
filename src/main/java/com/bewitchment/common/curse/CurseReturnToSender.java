package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Arrays;

public class CurseReturnToSender extends Curse {
	
	public CurseReturnToSender() {
		super(new ResourceLocation(Bewitchment.MODID, "return_to_sender"), Arrays.asList(Util.get(ModObjects.white_sage), Util.get(ModObjects.salt), Util.get("nuggetSilver"), Util.get("nuggetIron"), Util.get(ModObjects.taglock)), false, CurseCondition.REACTION);
	}
	
	@Override
	public boolean doCurse(@Nullable EntityPlayer target) {
		System.out.println("Lorem ipsum docet"); //this is a test
		System.out.println(target.getCapability(ExtendedPlayer.CAPABILITY, null).curses);
		return true;
	}
}
