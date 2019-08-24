package com.bewitchment.common.handler;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class VillagerAlchemistHandler {
    private static final VillagerRegistry VILLAGER_REGISTRY = VillagerRegistry.instance();
    public static VillagerRegistry.VillagerProfession PROF_ALCHEMIST;

    public static void initAlchemistHouse() {
        // Initialize house here
    }

    public static void initAlchemistTrades() {
        PROF_ALCHEMIST = new VillagerRegistry.VillagerProfession(
                Bewitchment.MODID+":alchemist",
                Bewitchment.MODID+":textures/entity/villager_alchemist.png",
                Bewitchment.MODID+":textures/entity/villager_alchemist_zombie.png");
        ForgeRegistries.VILLAGER_PROFESSIONS.register(PROF_ALCHEMIST);

        VillagerRegistry.VillagerCareer career_alchemist = new VillagerRegistry.VillagerCareer(PROF_ALCHEMIST, Bewitchment.MODID+".alchemist");
        career_alchemist.addTrade(1,
                new EntityVillager.ListItemForEmeralds(new ItemStack(ModObjects.cypress_sapling), new EntityVillager.PriceInfo(2, 5)));
    }
}
