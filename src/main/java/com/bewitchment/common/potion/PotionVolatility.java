package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"unused", "ConstantConditions", "deprecation"})
public class PotionVolatility extends ModPotion {
	private static final ResourceLocation icon = new ResourceLocation(Bewitchment.MODID, "textures/gui/effect/volatility.png");

	public PotionVolatility() {
		super("volatility", true, 0xff6000);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onDamage(LivingDamageEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && (event.getSource().isMagicDamage() || !event.getSource().isProjectile())) {
			event.getEntityLiving().world.playSound(null, event.getEntityLiving().getPosition(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 1, 1);
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.EXPLOSION_HUGE, event.getEntityLiving().getPosition()), event.getEntityLiving().dimension);
			int amplifier = event.getEntityLiving().getActivePotionEffect(this).getAmplifier() + 1;
			for (EntityLivingBase living : event.getEntityLiving().world.getEntitiesWithinAABB(EntityLivingBase.class, event.getEntityLiving().getEntityBoundingBox().grow(3))) {
				living.removePotionEffect(this);
				living.attackEntityFrom(DamageSource.causeExplosionDamage(event.getEntityLiving()), 8 * amplifier);
			}
			event.getEntityLiving().removePotionEffect(this);
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