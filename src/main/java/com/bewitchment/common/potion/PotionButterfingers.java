package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 4/7/2020.
 */
public class PotionButterfingers extends ModPotion {
    private static final ResourceLocation icon = new ResourceLocation(Bewitchment.MODID, "textures/gui/effect/butterfingers.png");

    public PotionButterfingers() {
        super("butterfingers", true, 0xF8DE7E);
    }

    @Override
    public boolean isInstant() {
        return true;
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

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
        ItemStack mainhand = living.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack offhand = living.getHeldItem(EnumHand.OFF_HAND);
        living.entityDropItem(mainhand.splitStack(mainhand.getCount()), 1);
        living.entityDropItem(offhand.splitStack(offhand.getCount()), 1);
    }
}
