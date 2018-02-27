package com.bewitchment.common.potion;

import com.bewitchment.common.lib.LibMod;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionMod extends Potion {
	
	@SideOnly(Side.CLIENT)
	public static final ResourceLocation EXTRA_EFFECTS_ALT = new ResourceLocation(LibMod.MOD_ID, "textures/gui/potions.png");
	
	public PotionMod(String name, boolean isBadEffectIn, int liquidColorIn) {
		super(isBadEffectIn, liquidColorIn);
		this.setRegistryName(LibMod.MOD_ID, name);
		this.setPotionName("effect." + name + ".name");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getStatusIconIndex() {
		Minecraft.getMinecraft().renderEngine.bindTexture(EXTRA_EFFECTS_ALT);
		return super.getStatusIconIndex();
	}
	
}
