package com.bewitchment.common.divination.fortunes;

import com.bewitchment.api.divination.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;

/**
 * Created by Joseph on 2/12/2018.
 */

//Todo: Make this functional. Sleep is recovering, but I'd rather have some time to fully recover. Also, I'm feeling kind of sick.
public class FortuneTreasure extends Fortune {

	public FortuneTreasure(int weight, String name, String modid) {
		super(weight, name, modid);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@Override
	public boolean canBeUsedFor(@Nonnull EntityPlayer player) {
		return false;
	}

	@Override
	public boolean canShouldBeAppliedNow(@Nonnull EntityPlayer player) {
		return false;
	}

	@Override
	public boolean apply(@Nonnull EntityPlayer player) {
		return false;
	}

	@Override
	public boolean isNegative() {
		return false;
	}
}
