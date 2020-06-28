package com.bewitchment.common.village;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.spirit.demon.EntityDemon;
import com.bewitchment.common.item.util.ModItemBauble;
import com.bewitchment.registry.ModObjects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@SuppressWarnings({"NullableProblems", "WeakerAccess"})
public class TradeHandler {
	public static class ItemstackForEmerald implements EntityVillager.ITradeList {
		public ItemStack sellingItem;
		public EntityVillager.PriceInfo emeraldPriceInfo, itemPriceInfo;

		public ItemstackForEmerald(ItemStack stack, EntityVillager.PriceInfo emeraldPriceInfo, EntityVillager.PriceInfo itemPriceInfo) {
			this.sellingItem = stack;
			this.emeraldPriceInfo = emeraldPriceInfo;
			this.itemPriceInfo = itemPriceInfo;
		}

		@Override
		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			int i1 = 1;
			int i2 = 1;
			if (this.emeraldPriceInfo != null) i1 = this.emeraldPriceInfo.getPrice(random);
			if (this.itemPriceInfo != null) i2 = this.itemPriceInfo.getPrice(random);

			ItemStack itemstack = new ItemStack(Items.EMERALD, i1);
			ItemStack itemstack1 = new ItemStack(sellingItem.getItem(), i2, sellingItem.getMetadata());

			recipeList.add(new MerchantRecipe(itemstack, itemstack1));
		}
	}

	public static class EmeraldforItemstack implements EntityVillager.ITradeList {
		public ItemStack buyingItem;
		public EntityVillager.PriceInfo emeraldPriceInfo, itemPriceInfo;

		public EmeraldforItemstack(ItemStack item, EntityVillager.PriceInfo emeraldPriceInfo, EntityVillager.PriceInfo itemPriceInfo) {
			this.buyingItem = item;
			this.emeraldPriceInfo = emeraldPriceInfo;
			this.itemPriceInfo = itemPriceInfo;
		}

		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = new ItemStack(buyingItem.getItem(), itemPriceInfo.getPrice(random), buyingItem.getMetadata());
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack, itemStack1));
		}
	}

	public static class RandomSaplingsforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo, itemPriceInfo;

		public RandomSaplingsforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo, EntityVillager.PriceInfo itemPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
			this.itemPriceInfo = itemPriceInfo;
		}

		private ItemStack getRandomSapling(Random random, int amount) {
			ItemStack itemStack;
			switch (random.nextInt(4)) {
				case 0:
					itemStack = new ItemStack(ModObjects.cypress_sapling, amount);
					break;
				case 1:
					itemStack = new ItemStack(ModObjects.juniper_sapling, amount);
					break;
				default:
					itemStack = new ItemStack(ModObjects.elder_sapling, amount);
			}
			return itemStack;
		}

		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomSapling(random, itemPriceInfo.getPrice(random));
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}
	}

	public static class RandomPlantsforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo, itemPriceInfo;

		public RandomPlantsforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo, EntityVillager.PriceInfo itemPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
			this.itemPriceInfo = itemPriceInfo;
		}

		private ItemStack getRandomPlant(Random random, int amount) {
			ItemStack itemStack;
			switch (random.nextInt(3)) {
				case 0:
					itemStack = new ItemStack(ModObjects.embergrass, amount);
					break;
				case 1:
					itemStack = new ItemStack(ModObjects.torchwood, amount);
					break;
				default:
					itemStack = new ItemStack(ModObjects.spanish_moss, amount);
			}
			return itemStack;
		}

		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomPlant(random, itemPriceInfo.getPrice(random));
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}


	}

	public static class RandomBaublesforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo;

		public RandomBaublesforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
		}

		private ItemStack getRandomBauble(Random random) {
			List<Item> baubles = ForgeRegistries.ITEMS.getValuesCollection().stream().filter(i -> i instanceof ModItemBauble).collect(Collectors.toList());
			if (!baubles.isEmpty()) return new ItemStack(baubles.get(random.nextInt(baubles.size())));
			return new ItemStack(ModObjects.girdle_of_the_dryads);
		}

		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomBauble(random);
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}


	}

	public static class RandomSeedsforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo, itemPriceInfo;

		public RandomSeedsforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo, EntityVillager.PriceInfo itemPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
			this.itemPriceInfo = itemPriceInfo;
		}

		private ItemStack getRandomSeed(Random random, int amount) {
			ItemStack itemStack;
			switch (random.nextInt(4)) {
				case 0:
					itemStack = new ItemStack(Items.WHEAT_SEEDS, amount);
					break;
				case 1:
					itemStack = new ItemStack(Items.MELON_SEEDS, amount);
					break;
				case 2:
					itemStack = new ItemStack(Items.PUMPKIN_SEEDS, amount);
					break;
				default:
					itemStack = new ItemStack(Items.BEETROOT_SEEDS, amount);
			}
			return itemStack;
		}

		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomSeed(random, itemPriceInfo.getPrice(random));
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}


	}

	public static class RandomWeaponsforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo;

		public RandomWeaponsforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
		}

		private ItemStack getRandomWeapon(Random random) {
			ItemStack itemStack;
			if (random.nextInt(2) == 0) {
				itemStack = new ItemStack(ModObjects.athame);
			} else {
				itemStack = new ItemStack(ModObjects.boline);
			}
			if (random.nextInt(10) >= 5)
				EnchantmentHelper.addRandomEnchantment(random, itemStack, random.nextInt(31), false);
			return itemStack;
		}

		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomWeapon(random);
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}
	}

	public static class ItemsForDemon implements EntityVillager.ITradeList {
		private static final EntityVillager.PriceInfo goldPriceInfo = new EntityVillager.PriceInfo(15, 30);
		private static final EntityVillager.PriceInfo diamondPriceInfo = new EntityVillager.PriceInfo(5, 7);
		private static final EntityVillager.PriceInfo blazePriceInfo = new EntityVillager.PriceInfo(4, 15);
		private static final EntityVillager.PriceInfo magmaPriceInfo = new EntityVillager.PriceInfo(8, 18);

		public ItemStack sellingItem;
		public EntityVillager.PriceInfo sellingPrice;

		public ItemsForDemon(ItemStack sellingItem, EntityVillager.PriceInfo sellingPrice) {
			this.sellingItem = sellingItem;
			this.sellingPrice = sellingPrice;
		}

		public static ItemStack getRandomDemonPrice(Random random, boolean reduced) {
			switch (random.nextInt(4)) {
				case 0:
					return new ItemStack(Items.BLAZE_ROD, (int) (blazePriceInfo.getPrice(random) * (reduced ? 0.5 : 1)));
				case 1:
					return new ItemStack(Items.DIAMOND, (int) (diamondPriceInfo.getPrice(random) * (reduced ? 0.5 : 1)));
				case 2:
					return new ItemStack(Items.GOLD_INGOT, (int) (goldPriceInfo.getPrice(random) * (reduced ? 0.5 : 1)));
				default:
					return new ItemStack(Items.MAGMA_CREAM, (int) (magmaPriceInfo.getPrice(random) * (reduced ? 0.5 : 1)));
			}
		}

		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = new ItemStack(sellingItem.getItem(), sellingPrice.getPrice(random), sellingItem.getMetadata());
			if (sellingItem.getItem() instanceof ItemBow || sellingItem.getItem() instanceof ItemEnchantedBook)
				itemStack = sellingItem;
			boolean reduced = false;
			if (iMerchant.getCustomer() != null && BewitchmentAPI.hasBesmirchedGear(iMerchant.getCustomer())) {
				reduced = true;
			} else if (iMerchant instanceof EntityDemon && ((EntityDemon) iMerchant).lastBuyer != null) {
				reduced = BewitchmentAPI.hasBesmirchedGear(((EntityDemon) iMerchant).lastBuyer);
			}
			ItemStack itemStack1 = getRandomDemonPrice(random, reduced);
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}
	}

	public static class EnchantedItemForDemon implements EntityVillager.ITradeList {
		public ItemStack enchantedItemStack;
		public EntityVillager.PriceInfo priceInfo;
		public int enchantmentLevel;

		public EnchantedItemForDemon(Item item, EntityVillager.PriceInfo priceSelling, int enchantmentLevel) {
			this.enchantedItemStack = new ItemStack(item);
			this.priceInfo = priceSelling;
			this.enchantmentLevel = enchantmentLevel;
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			ItemStack itemstack = ItemsForDemon.getRandomDemonPrice(random, false);
			ItemStack itemstack1 = EnchantmentHelper.addRandomEnchantment(random, new ItemStack(this.enchantedItemStack.getItem(), 1, this.enchantedItemStack.getMetadata()), enchantmentLevel, false);
			recipeList.add(new MerchantRecipe(itemstack, itemstack1));
		}
	}

	public static class RandomIdolForDemon implements EntityVillager.ITradeList {

		public RandomIdolForDemon() {
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			ItemStack itemstack = ItemsForDemon.getRandomDemonPrice(random, false);
			final String[] demons = {"leonard", "baphomet", "lilith", "herne", "moloch"};
			final String[] variants = {"stone", "gold", "nether_brick", "scorned_brick"};
			String demon = demons[random.nextInt(demons.length)];
			String variant = variants[random.nextInt(variants.length)];
			Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(Bewitchment.MODID, variant + "_" + demon + "_statue"));
			ItemStack itemstack1 = new ItemStack(ModObjects.stone_leonard_statue);
			if (item != null) itemstack1 = new ItemStack(item);
			recipeList.add(new MerchantRecipe(itemstack, itemstack1));
		}
	}

	public static class RandomContractForDemon implements EntityVillager.ITradeList {

		public RandomContractForDemon() {
		}

		public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
			ItemStack itemstack = ItemsForDemon.getRandomDemonPrice(random, false);
			ItemStack itemstack1 = Util.getRandomContract(random);
			recipeList.add(new MerchantRecipe(itemstack, ItemStack.EMPTY, itemstack1, 0, 1));
		}
	}
}
