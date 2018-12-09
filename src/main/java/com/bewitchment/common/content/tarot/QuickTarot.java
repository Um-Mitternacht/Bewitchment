package com.bewitchment.common.content.tarot;

import com.bewitchment.api.divination.ITarot;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Function;
import java.util.function.Predicate;

public class QuickTarot implements ITarot {

	ResourceLocation rl;
	ResourceLocation regName = null;
	String name, unlocalizedName;
	Predicate<EntityPlayer> apply, reverse;
	Function<EntityPlayer, Integer> getNum;

	public QuickTarot(String name, Predicate<EntityPlayer> apply, Predicate<EntityPlayer> reverse, Function<EntityPlayer, Integer> getNum) {
		validateAll(name, apply);
		this.name = name;
		this.unlocalizedName = "tarot." + name + ".name";
		rl = new ResourceLocation(LibMod.MOD_ID, "textures/gui/tarots/" + name + ".png");
		this.reverse = reverse;
		if (reverse == null) {
			this.reverse = p -> p.getRNG().nextBoolean();
		}
		this.getNum = getNum;
		if (getNum == null) {
			this.getNum = p -> -1;
		}
		this.apply = apply;
		this.setRegistryName(new ResourceLocation(LibMod.MOD_ID, name));
	}

	private static final void validateAll(Object... objects) {
		for (Object o : objects)
			if (o == null)
				throw new RuntimeException("Arguments cannot be null");
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
	public String getTranslationKey() {
		return unlocalizedName;
	}

	@Override
	public int getNumber(EntityPlayer player) {
		return getNum.apply(player);
	}

	@Override
	public ITarot setRegistryName(ResourceLocation nameIn) {
		regName = nameIn;
		return this;
	}

	@Override
	public ResourceLocation getRegistryName() {
		return regName;
	}

	@Override
	public Class<ITarot> getRegistryType() {
		return ITarot.class;
	}

}
