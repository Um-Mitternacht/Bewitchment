package com.bewitchment.common.divination.tarots;

import java.util.function.Function;
import java.util.function.Predicate;

import com.bewitchment.api.divination.ITarot;
import com.bewitchment.common.lib.LibMod;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class QuickTarot implements ITarot {
	
	ResourceLocation rl;
	String name;
	Predicate<EntityPlayer> apply, reverse;
	Function<EntityPlayer, Integer> getNum;
	
	public QuickTarot(String unlocalizedName, Predicate<EntityPlayer> apply, Predicate<EntityPlayer> reverse, Function<EntityPlayer, Integer> getNum) {
		rl = new ResourceLocation(LibMod.MOD_ID, "textures/gui/tarots/" + unlocalizedName + ".png");
		name = unlocalizedName;
		this.reverse = reverse;
		this.getNum = getNum;
		this.apply = apply;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return rl;
	}
	
	@Override
	public boolean isApplicableToPlayer(EntityPlayer player) {
		return apply.test(player);
	}
	
	@Override
	public boolean hasNumber(EntityPlayer player) {
		return getNum.apply(player) >= 0;
	}
	
	@Override
	public boolean isReversed(EntityPlayer player) {
		return reverse.test(player);
	}
	
	@Override
	public String getUnlocalizedName() {
		return name;
	}
	
	@Override
	public int getNumber(EntityPlayer player) {
		return getNum.apply(player);
	}
	
}
