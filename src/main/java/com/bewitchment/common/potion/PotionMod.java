package com.bewitchment.common.potion;

import com.bewitchment.common.lib.LibMod;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionMod extends Potion {

	public static final ResourceLocation EXTRA_EFFECTS_ALT = new ResourceLocation(LibMod.MOD_ID, "textures/gui/potions.png");
	private final boolean instant;

	public PotionMod(String name, boolean isBadEffectIn, int liquidColorIn, boolean isInstant) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(LibMod.MOD_ID, name);
		this.setPotionName("effect." + name);
		this.instant = isInstant;
	}

	@Override
	public boolean isInstant() {
		return instant;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isBeneficial() {
		return !this.isBadEffect();
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(EXTRA_EFFECTS_ALT);
		return super.getStatusIconIndex();
	}

}
