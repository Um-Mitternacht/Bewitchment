package com.bewitchment.common.village;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
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
		PROF_ALCHEMIST = new VillagerRegistry.VillagerProfession(Bewitchment.MODID + ":alchemist", Bewitchment.MODID + ":textures/entity/villager_alchemist.png", Bewitchment.MODID + ":textures/entity/villager_alchemist_zombie.png");
		ForgeRegistries.VILLAGER_PROFESSIONS.register(PROF_ALCHEMIST);
		
		VillagerRegistry.VillagerCareer career_alchemist = new VillagerRegistry.VillagerCareer(PROF_ALCHEMIST, Bewitchment.MODID + ".alchemist");
		career_alchemist.addTrade(1,
				new TradeHandler.ItemstackForEmerald(new ItemStack(Items.GLASS_BOTTLE), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(5, 9)),
				new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.silver_ingot), new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(2, 5)),
				new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.salt), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(10, 16)),
				new TradeHandler.EmeraldforItemstack(new ItemStack(Items.GOLD_INGOT), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(10, 16)));
		career_alchemist.addTrade(2,
				new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.stone_ichor), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(1, 3)),
				new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.fiery_unguent), new EntityVillager.PriceInfo(4, 6), new EntityVillager.PriceInfo(1, 3)));
		career_alchemist.addTrade(3, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.dragons_blood_resin), new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(3, 5)),
				//new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.moonstone), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)),
				new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.bottled_frostfire), new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.amethyst), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)), new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.garnet), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)));
		career_alchemist.addTrade(4, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.demonic_elixir), new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(1, 2)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.cleansing_balm), new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(1, 3)));
		career_alchemist.addTrade(5, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.dragons_blood_sapling), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(1, 1)));
	}
}
