package com.bewitchment.common.entity;

import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Random;

/**
 * Created by Joseph on 2/9/2019. Credit to Lfja, Pokefenn, and AlexThe666 for bits of the code.
 *
 */
@Mod.EventBusSubscriber(modid = LibMod.MOD_ID)
public class ModVillagers {

	public static final ModVillagers INSTANCE = new ModVillagers();
	public static final VillagerRegistry.VillagerProfession profAlchemist = new VillagerRegistry.VillagerProfession(LibMod.MOD_ID + "alchemist",
			LibMod.MOD_ID + "textures/entity/mobs/npcs/villagers/villager_alchemist.png",
			"minecraft:textures/entity/zombie_villager/zombie_villager.png");
	//Demonic professions
	public VillagerRegistry.VillagerProfession warlord;
	public VillagerRegistry.VillagerProfession slaver;
	public VillagerRegistry.VillagerProfession noble;
	public VillagerRegistry.VillagerProfession brute;
	public VillagerRegistry.VillagerProfession apocryphal_teacher;
	public VillagerRegistry.VillagerProfession despot;
	//Villager professions
	public VillagerRegistry.VillagerProfession hedge_witch;
	public VillagerRegistry.VillagerProfession rockhound;
	public VillagerRegistry.VillagerProfession herbalist;
	public VillagerRegistry.VillagerProfession hunter;
	public VillagerRegistry.VillagerProfession necromancer;

	@SubscribeEvent
	public static void init(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
		event.getRegistry().register(profAlchemist);

		new VillagerRegistry.VillagerCareer(profAlchemist, "alchemist")
				.addTrade(1, new EmeraldsForItemsWithMeta(new ItemStack(ModItems.tulsi, 16, 0), new EntityVillager.PriceInfo(3, 12)),
						new EntityVillager.ListItemForEmeralds(ModItems.juniper_berries, new EntityVillager.PriceInfo(1, 6)),
						makeItemAndEmeraldToItemWithMeta(new ItemStack(ModItems.glass_jar, 1, 0), new EntityVillager.PriceInfo(4, 16), new ItemStack(ModItems.magic_salve, 1, 0), new EntityVillager.PriceInfo(1, 1)));
	}

	static EntityVillager.ITradeList makeItemAndEmeraldToItemWithMeta(ItemStack buying, EntityVillager.PriceInfo buyingPriceInfo, ItemStack selling, EntityVillager.PriceInfo sellingPriceInfo) {
		EntityVillager.ItemAndEmeraldToItem trade = new EntityVillager.ItemAndEmeraldToItem(buying.getItem(), buyingPriceInfo, selling.getItem(), sellingPriceInfo);
		trade.buyingItemStack = buying;
		trade.sellingItemstack = selling;
		return trade;
	}

	static class EmeraldsForItemsWithMeta implements EntityVillager.ITradeList {
		ItemStack stack;
		EntityVillager.PriceInfo price;

		EmeraldsForItemsWithMeta(ItemStack stack, EntityVillager.PriceInfo price) {
			this.stack = stack;
			this.price = price;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			int p = 1;
			if (price != null)
				p = price.getPrice(random);

			ItemStack buying, selling;
			if (p > 0) {
				buying = new ItemStack(stack.getItem(), p, stack.getMetadata());
				selling = new ItemStack(Items.EMERALD);
			} else {
				buying = new ItemStack(stack.getItem(), 1, stack.getMetadata());
				selling = new ItemStack(Items.EMERALD, -p);
			}

			recipeList.add(new MerchantRecipe(buying, selling));
		}
	}
}