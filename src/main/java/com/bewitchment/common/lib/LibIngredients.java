package com.bewitchment.common.lib;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.magic.ItemFumes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.oredict.OreIngredient;

public class LibIngredients {

	//TODO any time we call Ingredient.from*, the call should be cached here and used multiple times

	public static Ingredient blazePowder = Ingredient.fromItem(Items.BLAZE_POWDER);
	public static Ingredient goldNugget = Ingredient.fromItem(Items.GOLD_NUGGET);
	public static Ingredient slime = Ingredient.fromItem(Items.SLIME_BALL);
	public static Ingredient dirt = Ingredient.fromStacks(new ItemStack(Blocks.DIRT, 1, 0));
	public static Ingredient stickyPiston = Ingredient.fromStacks(new ItemStack(Blocks.STICKY_PISTON, 1, 0));
	public static Ingredient sponge = Ingredient.fromStacks(new ItemStack(Blocks.SPONGE, 1, 0));
	public static Ingredient anyLog = new OreIngredient("logWood");
	public static Ingredient anyLeaf = new OreIngredient("treeLeaves");
	public static Ingredient pentacle = Ingredient.fromItem(ModItems.pentacle);
	public static Ingredient fumeReekOfDeath = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.reek_of_death.ordinal()));
	public static Ingredient anyDye = new OreIngredient("dye");
	public static Ingredient acaciaLog = Ingredient.fromStacks(new ItemStack(Blocks.LOG2, 1, 0));
	public static Ingredient redstone = Ingredient.fromItem(Items.REDSTONE);
	public static Ingredient ghastTear = Ingredient.fromItem(Items.GHAST_TEAR);
	public static Ingredient obsidian = Ingredient.fromItem(Item.getItemFromBlock(Blocks.OBSIDIAN));
	public static Ingredient fire_charge = Ingredient.fromItem(Items.FIRE_CHARGE);
	public static Ingredient goldIngot = new OreIngredient("ingotGold");
	public static Ingredient netherBrickItem = Ingredient.fromItem(Items.NETHERBRICK);
	public static Ingredient sand = new OreIngredient("sand");
	public static Ingredient dimensionalSand = Ingredient.fromItem(ModItems.dimensional_sand);
	public static Ingredient normalRitualChalk = Ingredient.fromStacks(new ItemStack(ModItems.ritual_chalk));
	public static Ingredient fumeFieryBreeze = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.fiery_breeze.ordinal()));
	public static Ingredient fumeHeavenlyWind = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.heavenly_winds.ordinal()));
	public static Ingredient fumePetrichorOdour = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.petrichor_odour.ordinal()));
	public static Ingredient fumeZephyrOfDepths = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.zephyr_of_the_depths.ordinal()));
	public static Ingredient fumeCleansingAura = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.cleansing_aura.ordinal()));
	public static Ingredient fumeCloudyOil = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.cloudy_oil.ordinal()));
	public static Ingredient fumeBottledMagic = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.bottled_magic.ordinal()));
	public static Ingredient fumeBirchSoul = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.birch_soul.ordinal()));
	public static Ingredient fumeDropletOfWisdom = Ingredient.fromStacks(new ItemStack(ModItems.fume, 1, ItemFumes.Type.droplet_of_wisdom.ordinal()));
	public static Ingredient ectoplasm = Ingredient.fromItem(ModItems.ectoplasm);
	public static Ingredient graveyardDust = Ingredient.fromItem(ModItems.graveyard_dust);
	public static Ingredient wormwood = Ingredient.fromItem(ModItems.wormwood);
	public static Ingredient empty_honeycomb = Ingredient.fromItem(ModItems.empty_honeycomb);
	public static Ingredient hoof = Ingredient.fromItem(ModItems.hoof);
	public static Ingredient emptyGoblet = Ingredient.fromStacks(new ItemStack(ModBlocks.goblet, 1, 0));
	public static Ingredient diamondOre = new OreIngredient("oreDiamond");
	public static Ingredient glowstoneBlock = Ingredient.fromItem(Item.getItemFromBlock(Blocks.GLOWSTONE));
	public static Ingredient goldenCarrot = Ingredient.fromItem(Items.GOLDEN_CARROT);
	public static Ingredient athame = Ingredient.fromItem(ModItems.athame);
	public static Ingredient apple = Ingredient.fromItem(Items.APPLE);
	public static Ingredient poisonousPotato = Ingredient.fromItem(Items.POISONOUS_POTATO);
	public static Ingredient witherSkull = Ingredient.fromStacks(new ItemStack(Items.SKULL, 1, 1));
	public static Ingredient soulSand = Ingredient.fromItem(Item.getItemFromBlock(Blocks.SOUL_SAND));
	public static Ingredient woodAsh = Ingredient.fromItem(ModItems.wood_ash);
	public static Ingredient clayBall = Ingredient.fromItem(Items.CLAY_BALL);
	public static Ingredient locationStone = Ingredient.fromItem(ModItems.location_stone);
	public static Ingredient blazeRod = Ingredient.fromItem(Items.BLAZE_ROD);
	public static Ingredient coal = Ingredient.fromItem(Items.COAL);
	public static Ingredient whiteSage = Ingredient.fromItem(ModItems.white_sage);
	public static Ingredient sagebrush = Ingredient.fromItem(ModItems.sagebrush);
	public static Ingredient salt = Ingredient.fromItem(ModItems.salt);
	public static Ingredient paper = Ingredient.fromItem(Items.PAPER);
	public static Ingredient wax = Ingredient.fromItem(ModItems.wax);
	public static Ingredient craftingTable = Ingredient.fromItem(Item.getItemFromBlock(Blocks.CRAFTING_TABLE));
	public static Ingredient anyGlass = new OreIngredient("blockGlass");
	public static Ingredient quartz = new OreIngredient("gemQuartz");
	public static Ingredient logCypress = Ingredient.fromStacks(new ItemStack(ModBlocks.log_cypress, 1, 0));
	public static Ingredient logYew = Ingredient.fromStacks(new ItemStack(ModBlocks.log_yew, 1, 0));
	public static Ingredient logJuniper = Ingredient.fromStacks(new ItemStack(ModBlocks.log_juniper, 1, 0));
	public static Ingredient logElder = Ingredient.fromStacks(new ItemStack(ModBlocks.log_elder, 1, 0));
	public static Ingredient broomMundane = Ingredient.fromStacks(new ItemStack(ModItems.broom, 1, 0));
	public static Ingredient magicSalve = Ingredient.fromItem(ModItems.magic_salve);
	public static Ingredient elytra = Ingredient.fromItem(Items.ELYTRA);
	public static Ingredient anyString = new OreIngredient("string");
	public static Ingredient glowstoneDust = Ingredient.fromItem(Items.GLOWSTONE_DUST);
	public static Ingredient boline = Ingredient.fromItem(ModItems.boline);
	public static Ingredient bloodyRags = Ingredient.fromItem(ModItems.sanguine_fabric);
	public static Ingredient anySapling = new OreIngredient("treeSapling");

}