package com.bewitchment.common.village;

import com.bewitchment.registry.ModObjects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;

import java.util.Random;

public class TradeHandler {
	public static class ItemstackForEmerald implements EntityVillager.ITradeList {
		public ItemStack sellingItem;
		public EntityVillager.PriceInfo emeraldPriceInfo, itemPriceInfo;
		
		public ItemstackForEmerald(Item par1Item, EntityVillager.PriceInfo emeraldPriceInfo, EntityVillager.PriceInfo itemPriceInfo) {
			this.sellingItem = new ItemStack(par1Item);
			this.emeraldPriceInfo = emeraldPriceInfo;
			this.itemPriceInfo = itemPriceInfo;
		}
		
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
		
		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomSapling(random, itemPriceInfo.getPrice(random));
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
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
				case 2:
					itemStack = new ItemStack(ModObjects.elder_sapling, amount);
					break;
				default:
					itemStack = null;
					break;
			}
			return itemStack;
		}
	}
	
	public static class RandomPlantsforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo, itemPriceInfo;
		
		public RandomPlantsforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo, EntityVillager.PriceInfo itemPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
			this.itemPriceInfo = itemPriceInfo;
		}
		
		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomPlant(random, itemPriceInfo.getPrice(random));
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}
		
		private ItemStack getRandomPlant(Random random, int amount) {
			ItemStack itemStack;
			switch (random.nextInt(2)) {
				case 0:
					itemStack = new ItemStack(ModObjects.embergrass, amount);
					break;
				case 1:
					itemStack = new ItemStack(ModObjects.torchwood, amount);
					break;
				default:
					itemStack = null;
					break;
			}
			return itemStack;
		}
	}
	
	public static class RandomBaublesforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo;
		
		public RandomBaublesforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
		}
		
		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomBauble(random, 1);
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}
		
		private ItemStack getRandomBauble(Random random, int amount) {
			ItemStack itemStack;
			switch (random.nextInt(3)) {
				case 0:
					itemStack = new ItemStack(ModObjects.girdle_of_the_dryads, amount);
					break;
				case 1:
					itemStack = new ItemStack(ModObjects.nazar, amount);
					break;
				case 2:
					itemStack = new ItemStack(ModObjects.token_of_remedies, amount);
					break;
				default:
					itemStack = null;
					break;
			}
			return itemStack;
		}
	}
	
	public static class RandomWeaponsforEmeralds implements EntityVillager.ITradeList {
		public EntityVillager.PriceInfo emeraldPriceInfo;
		
		public RandomWeaponsforEmeralds(EntityVillager.PriceInfo emeraldPriceInfo) {
			this.emeraldPriceInfo = emeraldPriceInfo;
		}
		
		@Override
		public void addMerchantRecipe(IMerchant iMerchant, MerchantRecipeList merchantRecipeList, Random random) {
			ItemStack itemStack = getRandomWeapon(random);
			ItemStack itemStack1 = new ItemStack(Items.EMERALD, emeraldPriceInfo.getPrice(random));
			merchantRecipeList.add(new MerchantRecipe(itemStack1, itemStack));
		}
		
		private ItemStack getRandomWeapon(Random random) {
			ItemStack itemStack;
			switch (random.nextInt(2)) {
				case 0:
					itemStack = new ItemStack(ModObjects.athame);
					break;
				case 1:
					itemStack = new ItemStack(ModObjects.boline);
					break;
				default:
					itemStack = null;
					break;
			}
			if (random.nextInt(10) >= 5 && itemStack != null) EnchantmentHelper.addRandomEnchantment(random, itemStack, random.nextInt(31), false);
			return itemStack;
		}
	}
}
