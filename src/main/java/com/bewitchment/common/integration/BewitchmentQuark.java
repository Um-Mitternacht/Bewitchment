package com.bewitchment.common.integration;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Brew;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import vazkii.quark.base.module.ConfigHelper;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.world.feature.*;
import vazkii.quark.world.world.underground.UndergroundBiome;
import vazkii.quark.world.world.underground.UndergroundBiomeGlowshroom;

import static vazkii.quark.misc.feature.ExtraPotions.dangerSight;
import static vazkii.quark.misc.feature.ExtraPotions.enableDangerSight;
import static vazkii.quark.world.feature.Biotite.biotite;
import static vazkii.quark.world.feature.CaveRoots.*;
import static vazkii.quark.world.feature.Crabs.crabShell;
import static vazkii.quark.world.feature.Crabs.resilience;
import static vazkii.quark.world.feature.Frogs.gildedFrogLeg;
import static vazkii.quark.world.feature.UndergroundBiomes.glowceliumEnabled;
import static vazkii.quark.world.feature.UndergroundBiomes.glowshroom;

public class BewitchmentQuark {

    @SubscribeEvent
    public void registerBrew(RegistryEvent.Register<Brew> event) {
        event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "prismarine_haste"), Util.get(Items.PRISMARINE_CRYSTALS), new PotionEffect(MobEffects.HASTE, (20 * 30))));
        if (ModuleLoader.isFeatureEnabled(Frogs.class)) event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "frog_boost"), Util.get(gildedFrogLeg), new PotionEffect(MobEffects.JUMP_BOOST, (20 * 30))));
        if (ModuleLoader.isFeatureEnabled(Biotite.class)) event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "biotite_resistance"), Util.get(biotite), new PotionEffect(MobEffects.RESISTANCE, (20 * 30))));
        if (ModuleLoader.isFeatureEnabled(UndergroundBiomes.class) && enableDangerSight && glowceliumEnabled)event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "danger_sight"), Util.get(glowshroom), new PotionEffect(dangerSight, (20 * 30))));
        if (ModuleLoader.isFeatureEnabled(CaveRoots.class) && enableFlowers){
            event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blueberry"), Util.get(new ItemStack(root_flower, 1, 0)), new PotionEffect(blue_effect, (20 * 30))));
            event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blackening"), Util.get(new ItemStack(root_flower, 1, 1)), new PotionEffect(black_effect, (20 * 30))));
            event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "whitening"), Util.get(new ItemStack(root_flower, 1, 2)), new PotionEffect(white_effect, (20 * 30))));
        }
        if (ModuleLoader.isFeatureEnabled(Crabs.class)) event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "stability"), Util.get(crabShell), new PotionEffect(resilience, (20 * 30))));
    }
}
