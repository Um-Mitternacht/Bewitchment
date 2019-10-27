package com.bewitchment.common.village;

import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class DemonTradeHandler {

	public static final DemonTradeHandler INSTANCE = new DemonTradeHandler();

	public VillagerRegistry.VillagerProfession demon;

	public void init() {
		demon = new VillagerRegistry.VillagerProfession("bewitchment:demon", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
			VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(demon, "noble");
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.nethersteel[0]), new EntityVillager.PriceInfo(10, 20)));
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.scorned_bricks[0]), new EntityVillager.PriceInfo(15, 25)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.diabolical_vein), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.opal), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.garnet), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.amethyst), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(3, new TradeHandler.DemonItemForItems(new ItemStack(Items.EMERALD), new EntityVillager.PriceInfo(1, 3)));
			//career.addTrade(3, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.triskelion_amulet), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(3, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.silver_ingot), new EntityVillager.PriceInfo(10, 13)));
			//career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.box_of_sealed_evil), new EntityVillager.PriceInfo(1, 2)));
			//career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.demonic_contract), new EntityVillager.PriceInfo(1, 1)));
			//career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.diabolical_vein), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(5, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.fortunes_favor), new EntityVillager.PriceInfo(1, 1)));
			//career.addTrade(5, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.diabolical_vein), new EntityVillager.PriceInfo(1, 3)));
			//career.addTrade(5, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.sigil_of_the_steed), new EntityVillager.PriceInfo(1, 3)));
		}
		{
			VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(demon, "despot");
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.tongue_of_dog), new EntityVillager.PriceInfo(5, 7)));
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.nethersteel[0]), new EntityVillager.PriceInfo(10, 16)));
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(Items.ARROW), new EntityVillager.PriceInfo(10, 16)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(Items.SKULL, 1, 0), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.heart), new EntityVillager.PriceInfo(1, 2)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(Items.GHAST_TEAR), new EntityVillager.PriceInfo(3, 6)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.hellhound_horn), new EntityVillager.PriceInfo(2, 4)));
			// hellhound head here...
			career.addTrade(3, new TradeHandler.DemonItemForItems(new ItemStack(Items.SKULL, 1, 1), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(3, new TradeHandler.EnchantedItemForDemon(Items.IRON_SWORD, new EntityVillager.PriceInfo(1, 1), 15));
			career.addTrade(3, new TradeHandler.EnchantedItemForDemon(Items.BOW, new EntityVillager.PriceInfo(1, 1), 15));
			//career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.tongue_of_dog), new EntityVillager.PriceInfo(5, 7)));
			career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.demon_heart), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(4, new TradeHandler.EnchantedItemForDemon(Items.DIAMOND_SWORD, new EntityVillager.PriceInfo(1, 1), 30));
			career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.bottled_hellfire), new EntityVillager.PriceInfo(1, 3)));
			//career.addTrade(5, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.tongue_of_dog), new EntityVillager.PriceInfo(5, 7)));
			//career.addTrade(5, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.tongue_of_dog), new EntityVillager.PriceInfo(5, 7)));
		}
		{
			VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(demon, "apocryphal_teacher");
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(Items.BOOK), new EntityVillager.PriceInfo(10, 20)));
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.ravens_feather), new EntityVillager.PriceInfo(3, 6)));
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.iron_gall_ink), new EntityVillager.PriceInfo(5, 10)));
			career.addTrade(1, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.snake_venom), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.dragons_blood_resin), new EntityVillager.PriceInfo(4, 12)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.poppet), new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(2, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.sanguine_cloth), new EntityVillager.PriceInfo(2, 4)));
			career.addTrade(2, new TradeHandler.EnchantedItemForDemon(Items.BOOK, new EntityVillager.PriceInfo(1, 1), 10));
			career.addTrade(3, new TradeHandler.EnchantedItemForDemon(Items.BOOK, new EntityVillager.PriceInfo(1, 1), 20));
			career.addTrade(3, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.stew_of_the_grotesque), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(3, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.oil_of_vitriol), new EntityVillager.PriceInfo(2, 3)));
			// career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.bottled_hellfire), new EntityVillager.PriceInfo(1, 3)));
			ItemStack looting = new ItemStack(Items.BOOK);
			looting.addEnchantment(Enchantments.LOOTING, 3);
			career.addTrade(4, new TradeHandler.DemonItemForItems(looting, new EntityVillager.PriceInfo(1, 1)));
			ItemStack flame = new ItemStack(Items.BOOK);
			looting.addEnchantment(Enchantments.FLAME, 1);
			career.addTrade(4, new TradeHandler.DemonItemForItems(flame, new EntityVillager.PriceInfo(1, 1)));
			ItemStack fire_aspect = new ItemStack(Items.BOOK);
			looting.addEnchantment(Enchantments.FIRE_ASPECT, 2);
			career.addTrade(4, new TradeHandler.DemonItemForItems(fire_aspect, new EntityVillager.PriceInfo(2, 3)));
		}
	}
}
