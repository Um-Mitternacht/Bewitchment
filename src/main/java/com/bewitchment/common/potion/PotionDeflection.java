package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.IProjectile;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"unused", "ConstantConditions", "deprecation"})
public class PotionDeflection extends ModPotion {
	private static final ResourceLocation icon = new ResourceLocation(Bewitchment.MODID, "textures/gui/effect/deflect.png");

	public PotionDeflection() {
		super("deflection", false, 0xc000ff);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onAttacked(LivingAttackEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getSource().getImmediateSource() instanceof IProjectile) {
			int amplifier = event.getEntityLiving().getActivePotionEffect(this).getAmplifier();
			event.getSource().getImmediateSource().motionX *= 4 * amplifier;
			event.getSource().getImmediateSource().motionY *= 4 * amplifier;
			event.getSource().getImmediateSource().motionZ *= 4 * amplifier;
			event.setCanceled(true);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
		mc.getTextureManager().bindTexture(icon);
		Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
		mc.getTextureManager().bindTexture(icon);
		Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
	}
}