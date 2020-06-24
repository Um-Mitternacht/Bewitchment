package com.bewitchment.common.village;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

public class VillagerTradeHandler {

	public static final VillagerTradeHandler INSTANCE = new VillagerTradeHandler();

	public VillagerRegistry.VillagerProfession demon;
	public VillagerRegistry.VillagerProfession alchemist;
	public VillagerRegistry.VillagerProfession hedgewitch;

	public void init() {
		hedgewitch = new VillagerRegistry.VillagerProfession(Bewitchment.MODID + ":hedge_witch", Bewitchment.MODID + ":textures/entity/villager_greenwitch.png", Bewitchment.MODID + ":textures/entity/villager_zombie_greenwitch.png");
		{
			ForgeRegistries.VILLAGER_PROFESSIONS.register(hedgewitch);
			VillagerRegistry.VillagerCareer career_witch = new VillagerRegistry.VillagerCareer(hedgewitch, Bewitchment.MODID + ".witch");
			career_witch.addTrade(1, new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.tallow), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(6, 10)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.empty_jar), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.juniper_berries), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.elderberries), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.RandomSeedsforEmeralds(new EntityVillager.PriceInfo(1, 2)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.oak_apple_gall), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(3, 5)));
			career_witch.addTrade(2, new TradeHandler.RandomSaplingsforEmeralds(new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 3)), new TradeHandler.RandomSeedsforEmeralds(new EntityVillager.PriceInfo(2, 4)));
			career_witch.addTrade(3, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.ravens_feather), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(5, 10)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.owlets_wing), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.wood_ash), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(10, 20)), new TradeHandler.RandomWeaponsforEmeralds(new EntityVillager.PriceInfo(3, 7)), new TradeHandler.RandomSeedsforEmeralds(new EntityVillager.PriceInfo(3, 5)));
			career_witch.addTrade(4, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.heart), new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(1, 2)), new TradeHandler.RandomPlantsforEmeralds(new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(2, 5)), new TradeHandler.RandomSeedsforEmeralds(new EntityVillager.PriceInfo(5, 8)));
			career_witch.addTrade(5, new TradeHandler.RandomBaublesforEmeralds(new EntityVillager.PriceInfo(8, 16)), new TradeHandler.RandomSeedsforEmeralds(new EntityVillager.PriceInfo(7, 11)), (new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.thyrsus), new EntityVillager.PriceInfo(3, 12), new EntityVillager.PriceInfo(1, 1))));
		}
		alchemist = new VillagerRegistry.VillagerProfession(Bewitchment.MODID + ":alchemist", Bewitchment.MODID + ":textures/entity/villager_alchemist.png", Bewitchment.MODID + ":textures/entity/villager_alchemist_zombie.png");
		{
			ForgeRegistries.VILLAGER_PROFESSIONS.register(alchemist);
			VillagerRegistry.VillagerCareer career_alchemist = new VillagerRegistry.VillagerCareer(alchemist, Bewitchment.MODID + ".alchemist");
			career_alchemist.addTrade(1, new TradeHandler.ItemstackForEmerald(new ItemStack(Items.GLASS_BOTTLE), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(5, 9)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.silver_ingot), new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(2, 5)), new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.salt), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(10, 16)), new TradeHandler.EmeraldforItemstack(new ItemStack(Items.GOLD_INGOT), new EntityVillager.PriceInfo(1, 2), new EntityVillager.PriceInfo(10, 16)));
			career_alchemist.addTrade(2, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.stone_ichor), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(1, 3)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.fiery_unguent), new EntityVillager.PriceInfo(4, 6), new EntityVillager.PriceInfo(1, 3)));
			career_alchemist.addTrade(3, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.dragons_blood_resin), new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(3, 5)),
					//new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.moonstone), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)),
					new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.bottled_frostfire), new EntityVillager.PriceInfo(3, 5), new EntityVillager.PriceInfo(3, 5)), new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.amethyst), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)), new TradeHandler.EmeraldforItemstack(new ItemStack(ModObjects.garnet), new EntityVillager.PriceInfo(1, 3), new EntityVillager.PriceInfo(2, 5)));
			career_alchemist.addTrade(4, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.demonic_elixir), new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(1, 2)), new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.cleansing_balm), new EntityVillager.PriceInfo(3, 6), new EntityVillager.PriceInfo(1, 3)));
			career_alchemist.addTrade(5, new TradeHandler.ItemstackForEmerald(new ItemStack(ModObjects.dragons_blood_sapling), new EntityVillager.PriceInfo(2, 4), new EntityVillager.PriceInfo(1, 1)));
		}
		demon = new VillagerRegistry.VillagerProfession("bewitchment:demon", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
			VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(demon, "noble");
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.nethersteel[0]), new EntityVillager.PriceInfo(10, 20)));
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.scorned_bricks[0]), new EntityVillager.PriceInfo(15, 25)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.diabolical_vein), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.opal), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.garnet), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.amethyst), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(3, new TradeHandler.ItemsForDemon(new ItemStack(Items.EMERALD), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(3, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.triskelion), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(3, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.silver_ingot), new EntityVillager.PriceInfo(10, 13)));
			career.addTrade(4, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.box_of_sealed_evil), new EntityVillager.PriceInfo(1, 2)));
			//career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.diabolical_vein), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(4, new TradeHandler.RandomIdolForDemon());
			career.addTrade(5, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.fortunes_favor), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(5, new TradeHandler.RandomContractForDemon());
			career.addTrade(5, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.gluttons_sash), new EntityVillager.PriceInfo(40, 1)));
			//career.addTrade(5, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.sigil_of_the_steed), new EntityVillager.PriceInfo(1, 3)));
		}
		{
			VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(demon, "despot");
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.tongue_of_dog), new EntityVillager.PriceInfo(5, 7)));
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.nethersteel[0]), new EntityVillager.PriceInfo(10, 16)));
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(Items.ARROW), new EntityVillager.PriceInfo(10, 16)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(Items.SKULL, 1, 0), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.heart), new EntityVillager.PriceInfo(1, 2)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(Items.GHAST_TEAR), new EntityVillager.PriceInfo(3, 6)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.hellhound_horn), new EntityVillager.PriceInfo(2, 4)));
			// hellhound head here...
			career.addTrade(3, new TradeHandler.ItemsForDemon(new ItemStack(Items.SKULL, 1, 1), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(3, new TradeHandler.EnchantedItemForDemon(Items.IRON_SWORD, new EntityVillager.PriceInfo(1, 1), 15));
			ItemStack flameBow = new ItemStack(Items.BOW);
			flameBow.addEnchantment(Enchantments.FLAME, 1);
			career.addTrade(3, new TradeHandler.ItemsForDemon(flameBow, new EntityVillager.PriceInfo(1, 1)));
			ItemStack powerBow = new ItemStack(Items.BOW);
			powerBow.addEnchantment(Enchantments.POWER, 2);
			career.addTrade(3, new TradeHandler.ItemsForDemon(powerBow, new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(4, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.demon_heart), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(4, new TradeHandler.EnchantedItemForDemon(Items.DIAMOND_SWORD, new EntityVillager.PriceInfo(1, 1), 30));
			career.addTrade(4, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.bottled_hellfire), new EntityVillager.PriceInfo(1, 3)));
			career.addTrade(5, new TradeHandler.RandomContractForDemon());
			career.addTrade(5, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.vampiric_amulet), new EntityVillager.PriceInfo(40, 1)));
			//career.addTrade(5, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.tongue_of_dog), new EntityVillager.PriceInfo(5, 7)));
		}
		{
			VillagerRegistry.VillagerCareer career = new VillagerRegistry.VillagerCareer(demon, "apocryphal_teacher");
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(Items.BOOK), new EntityVillager.PriceInfo(10, 20)));
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.ravens_feather), new EntityVillager.PriceInfo(3, 6)));
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.iron_gall_ink), new EntityVillager.PriceInfo(5, 10)));
			career.addTrade(1, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.snake_venom), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.dragons_blood_resin), new EntityVillager.PriceInfo(4, 12)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.poppet), new EntityVillager.PriceInfo(3, 5)));
			career.addTrade(2, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.sanguine_cloth), new EntityVillager.PriceInfo(2, 4)));
			career.addTrade(2, new TradeHandler.EnchantedItemForDemon(Items.BOOK, new EntityVillager.PriceInfo(1, 1), 10));
			career.addTrade(3, new TradeHandler.EnchantedItemForDemon(Items.BOOK, new EntityVillager.PriceInfo(1, 1), 20));
			career.addTrade(3, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.stew_of_the_grotesque), new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(3, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.oil_of_vitriol), new EntityVillager.PriceInfo(2, 3)));
			// career.addTrade(4, new TradeHandler.DemonItemForItems(new ItemStack(ModObjects.hellworld_brew), new EntityVillager.PriceInfo(1, 3)));
			ItemStack looting = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(Enchantments.LOOTING, 3));
			career.addTrade(4, new TradeHandler.ItemsForDemon(looting, new EntityVillager.PriceInfo(1, 1)));
			ItemStack flame = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(Enchantments.FLAME, 1));
			career.addTrade(4, new TradeHandler.ItemsForDemon(flame, new EntityVillager.PriceInfo(1, 1)));
			ItemStack fire_aspect = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(Enchantments.FIRE_ASPECT, 2));
			career.addTrade(4, new TradeHandler.ItemsForDemon(fire_aspect, new EntityVillager.PriceInfo(1, 1)));
			career.addTrade(5, new TradeHandler.ItemsForDemon(new ItemStack(ModObjects.skeleton_key), new EntityVillager.PriceInfo(40, 1)));
			career.addTrade(5, new TradeHandler.RandomIdolForDemon(), new TradeHandler.RandomContractForDemon());
		}
	}
}
