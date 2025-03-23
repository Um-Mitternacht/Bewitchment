package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({"ConstantConditions", "unused"})
public class PotionMortalCoil extends ModPotion {
    private static final ResourceLocation icon = new ResourceLocation(Bewitchment.MODID, "textures/gui/effect/mortal_coil.png");

    public PotionMortalCoil() {
        super("mortal_coil", true, 0x4B3621);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        if (!event.getEntityLiving().world.isRemote && event.getEntityLiving().isPotionActive(this) && event.getEntityLiving().getActivePotionEffect(this).getDuration() == 1) {
            event.getEntityLiving().attackEntityFrom(DamageSource.MAGIC, Integer.MAX_VALUE);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    @Override
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha) {
        mc.getTextureManager().bindTexture(icon);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}