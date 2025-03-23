package com.bewitchment.common.potion;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.potion.util.ModPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class PotionFear extends ModPotion {
    private static final ResourceLocation icon = new ResourceLocation(Bewitchment.MODID, "textures/gui/effect/fear.png");

    public PotionFear() {
        super("fear", true, 0x10c440);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public boolean isInstant() {
        return false;
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

    @Override
    public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase living, int amplifier, double health) {
        super.affectEntity(source, indirectSource, living, amplifier, health);
    }

    @SubscribeEvent
    public void movePlayer(TickEvent.PlayerTickEvent event) {
        if (event.player.isPotionActive(this) && event.phase == TickEvent.Phase.END) {
            event.player.motionX += event.player.getRNG().nextGaussian() / 4d;
            event.player.motionZ += event.player.getRNG().nextGaussian() / 4d;
            if (!event.player.world.isRemote)
                ((EntityPlayerMP) event.player).connection.sendPacket(new SPacketEntityVelocity(event.player));
        }
    }
}
