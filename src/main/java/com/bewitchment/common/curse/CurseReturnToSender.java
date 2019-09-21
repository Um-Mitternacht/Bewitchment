package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Arrays;

public class CurseReturnToSender extends Curse {

	public CurseReturnToSender() {
		super(new ResourceLocation(Bewitchment.MODID, "return_to_sender"), Arrays.asList(Util.get(ModObjects.white_sage), Util.get(ModObjects.salt), Util.get("nuggetIron"), Util.get(ModObjects.taglock)), false);
	}

	@Override
	public boolean apply(@Nullable EntityPlayer player) {
		ExtendedPlayer ep;
		if(player.hasCapability(ExtendedPlayer.CAPABILITY, null)) {
			ep = player.getCapability(ExtendedPlayer.CAPABILITY, null);
			NBTTagString tag = new NBTTagString(this.getRegistryName().toString());
			ep.curses.appendTag(tag);
			return true;
		}
		return false;
	}
}
