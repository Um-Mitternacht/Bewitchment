package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.potion.util.ModPotion;
import com.bewitchment.registry.ModPotions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PotionHellfire extends ModPotion {

    public PotionHellfire() {
        super("hellfire", true, 0);
    }

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc) {
        mc.getTextureManager().bindTexture(new ResourceLocation(Bewitchment.MODID, "textures/gui/potionhellfire.png"));
        Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
    }

    @SubscribeEvent
    public void playerFireDamage (LivingHurtEvent event) {
        if (event.getSource().isFireDamage() && event.getEntityLiving().getActivePotionEffect(ModPotions.hellfire) != null) {
            int amp = event.getEntityLiving().getActivePotionEffect(ModPotions.hellfire).getAmplifier();
            float mod = 1.5f + amp * amp * 0.5f;
            event.setAmount(event.getAmount() * mod);
        }
    }
}
