package com.bewitchment.common.village;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class VillagerHedgeWitchHandler {
	private static final VillagerRegistry VILLAGER_REGISTRY = VillagerRegistry.instance();
	public static VillagerRegistry.VillagerProfession PROF_HEDGEWITCH;
	
	public static void initHedgeWitchHouse() {
		// Initialize house here
	}
	
	public static void initHedgeWitchTrades() {
		PROF_HEDGEWITCH = new VillagerRegistry.VillagerProfession(Bewitchment.MODID + ":hedge_witch", Bewitchment.MODID + ":textures/entity/villager_greenwitch.png", Bewitchment.MODID + ":textures/entity/villager_zombie_greenwitch.png");
		ForgeRegistries.VILLAGER_PROFESSIONS.register(PROF_HEDGEWITCH);
		
		VillagerRegistry.VillagerCareer career_witch = new VillagerRegistry.VillagerCareer(PROF_HEDGEWITCH, Bewitchment.MODID + ".witch");
		career_witch.addTrade(1, new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.tallow), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(6, 10)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.empty_jar), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.juniper_berries), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.elderberries), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(3, 5)));
		career_witch.addTrade(2, new TradeHandler.RandomSaplingsforEmeralds(new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 3)));
		career_witch.addTrade(3, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.ravens_feather), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(5, 10)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.owlets_wing), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.wood_ash), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(10, 20)), new TradeHandler.RandomWeaponsforEmeralds(new EntityVillager.PriceInfo(3, 7)));
		career_witch.addTrade(4, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.heart), new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(1, 2)), new TradeHandler.RandomPlantsforEmeralds(new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(2, 5)));
		career_witch.addTrade(5, new TradeHandler.RandomBaublesforEmeralds(new EntityVillager.PriceInfo(8, 16)));
	}
}
