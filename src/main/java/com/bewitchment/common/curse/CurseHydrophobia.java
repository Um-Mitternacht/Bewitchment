package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;

public class CurseHydrophobia extends Curse {
    public CurseHydrophobia() {
        super(new ResourceLocation(Bewitchment.MODID, "hydrophobia"), Arrays.asList(Util.get(ModObjects.oil_of_vitriol), Util.get("coquina"), Util.get("coquina"), Util.get(Blocks.STONE), Util.get(new ItemStack(Items.DYE, 1, 0)), Util.get(Items.PRISMARINE_SHARD), Util.get(ModObjects.taglock)), true, false, CurseCondition.EXIST);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAquaticDamage(LivingDamageEvent event) {
        if (!event.getEntity().getEntityWorld().isRemote && event.getEntityLiving() instanceof EntityPlayer && event.getEntityLiving().isWet()) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (player.getCapability(ExtendedPlayer.CAPABILITY, null).hasCurse(this)) {
                event.setAmount(event.getAmount() * 1.5f);
            }
        }
    }

    @Override
    public boolean doCurse(Event event, EntityPlayer target) {
        if (target.isWet()) {
            target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 1));
        }
        return false;
    }
}
