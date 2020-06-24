package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"unused", "ConstantConditions", "deprecation"})
public class PotionSinking extends ModPotion {
	private static final ResourceLocation icon = new ResourceLocation(Bewitchment.MODID, "textures/gui/effect/sinking.png");

	public PotionSinking() {
		super("sinking", true, 0x4000c0);
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onJump(LivingEvent.LivingJumpEvent event) {
		if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getEntityLiving().world.rand.nextInt(3) <= event.getEntityLiving().getActivePotionEffect(this).getAmplifier()) {
			event.getEntityLiving().motionY = 0;
			if (event.getEntityLiving() instanceof EntityPlayer)
				((EntityPlayerMP) event.getEntityLiving()).connection.sendPacket(new SPacketEntityVelocity(event.getEntityLiving()));
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