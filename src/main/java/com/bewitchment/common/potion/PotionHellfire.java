package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.potion.util.ModPotion;
import com.bewitchment.registry.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLiving;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class PotionHellfire extends ModPotion {
    private static final ResourceLocation icon = new ResourceLocation(Bewitchment.MODID, "textures/gui/effect/hellfire.png");

    public PotionHellfire() {
        super("hellfire", true, 0);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void playerFireDamage(LivingHurtEvent event) {
        if (!event.getEntity().world.isRemote && event.getEntityLiving().isPotionActive(this) && (event.getSource().isFireDamage() || (event.getSource().getTrueSource() instanceof EntityLiving && ((EntityLiving) event.getSource().getTrueSource()).getCreatureAttribute() == BewitchmentAPI.DEMON))) {
            int amp = event.getEntityLiving().getActivePotionEffect(ModPotions.hellfire).getAmplifier();
            float mod = 1.5f + amp * amp * 0.5f;
            event.setAmount(event.getAmount() * mod);
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
