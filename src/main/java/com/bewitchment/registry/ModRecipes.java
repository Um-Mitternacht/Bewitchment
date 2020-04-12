package com.bewitchment.registry;

import c4.consecration.common.init.ConsecrationItems;
import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.*;
import com.bewitchment.common.block.BlockCandelabra;
import com.bewitchment.common.block.BlockCandle;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.BlockStatue;
import com.bewitchment.common.block.tile.entity.TileEntityStatue;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.spirit.demon.*;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
import com.bewitchment.common.integration.dynamictrees.DynamicTreesCompat;
import com.bewitchment.common.item.tool.ItemGrimoireMagia;
import com.bewitchment.common.ritual.*;
import com.ferreusveritas.dynamictrees.ModTrees;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamilyVanilla;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.RecipeTippedArrow;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.misc.BlockNitor;
import vazkii.botania.common.block.BlockGaiaHead;
import vazkii.botania.common.item.ModItems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ModRecipes {
	public static List<OvenRecipe> ovenRecipes = new ArrayList<>();
	public static List<DistilleryRecipe> distilleryRecipes = new ArrayList<>();
	public static List<SpinningWheelRecipe> spinningWheelRecipes = new ArrayList<>();
	public static List<Ritual> ritualRecipes = new ArrayList<>();
	public static List<CauldronRecipe> cauldronRecipes = new ArrayList<>();
	
	public static void init() {
		furnaceInit();
		
		altarInit();
		petsInit();
		craftingRecipesInit();
		
		ModObjects.TOOL_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.TOOL_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
		ModObjects.ARMOR_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.ARMOR_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
		ModObjects.ARMOR_WITCHES.setRepairItem(new ItemStack(ModObjects.witches_stitching));
	}
	
	private static void craftingRecipesInit() {
		ForgeRegistries.RECIPES.register(new RecipeTippedArrow() {
			@Override
			public ItemStack getCraftingResult(InventoryCrafting inv) {
				ItemStack stack = super.getCraftingResult(inv);
				if (PotionUtils.getPotionFromItem(stack) == PotionTypes.EMPTY) stack.getTagCompound().setInteger("CustomPotionColor", PotionUtils.getPotionColorFromEffectList(PotionUtils.getFullEffectsFromItem(stack)));
				return stack;
			}
		}.setRegistryName("minecraft", "tippedarrow"));
		
		ForgeRegistries.RECIPES.register(new RecipeWand().setRegistryName(Bewitchment.MODID, "leonards_wand"));
	}
	
	private static void petsInit() {
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityOcelot.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityWolf.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityHorse.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityDonkey.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityMule.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityLlama.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityParrot.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityOwl.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityRaven.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntitySnake.class));
		BewitchmentAPI.VALID_PETS.add(EntityRegistry.getEntry(EntityToad.class));
	}
	
	public static void postInit() {
		athamePostInit();
		furnacePostInit();
	}
	
	private static void altarInit() {
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityFlowerPot && ((TileEntityFlowerPot) s.getTileEntity()).getFlowerItemStack().isEmpty(), new AltarUpgrade(AltarUpgrade.Type.CUP, 0, 1.05));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityFlowerPot && !((TileEntityFlowerPot) s.getTileEntity()).getFlowerItemStack().isEmpty(), new AltarUpgrade(AltarUpgrade.Type.CUP, 1, 1.1));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() == ModObjects.goblet, new AltarUpgrade(AltarUpgrade.Type.CUP, 0, 1.075));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() == ModObjects.filled_goblet, new AltarUpgrade(AltarUpgrade.Type.CUP, 1, 1.2));
		Util.registerAltarUpgradeItem(Items.GLASS_BOTTLE, new AltarUpgrade(AltarUpgrade.Type.CUP, 0, 1.05));
		Util.registerAltarUpgradeItem(ModObjects.flying_ointment, new AltarUpgrade(AltarUpgrade.Type.CUP, 1, 1.17));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) s.getTileEntity()).getSkullType() == 5, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 6, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) s.getTileEntity()).getSkullType() == 1, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) s.getTileEntity()).getSkullType() != 5 && ((TileEntitySkull) s.getTileEntity()).getSkullType() != 1, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityStatue, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeItem(ModObjects.pentacle, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 3, 0));
		List<Block> statues = ForgeRegistries.BLOCKS.getValuesCollection().stream().filter(i -> i instanceof BlockStatue).collect(Collectors.toList());
		for (Block statue : statues) {
			Util.registerAltarUpgradeItem(Item.getItemFromBlock(statue), new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		}
		Util.registerAltarUpgradeOreDict("fossil", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemDiamond", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemEmerald", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemPsi", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemPrismarine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemLifeCrystal", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemQuartz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemLapis", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("dustRedstone", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemBloodstone", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemNuummite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemGarnet", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemTourmaline", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemJasper", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemTigersEye", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemMalachite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemAmethyst", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemAlexandrite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemAquamarine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemRuby", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemSapphire", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemPeridot", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAmber", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemApatite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemTopaz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemJet", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemTanzanite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemPearl", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemOpal", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemZanite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCrimsonMiddleGem", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAquaMiddleGem", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemGreenMiddleGem", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemZircon", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAzurite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemEudialyte", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemRime", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAgate", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemJade", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemOnyx", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemEnderBiotite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemBurnium", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemEndimium", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemHephaestite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemScarlite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAether", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemSerpentine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemPetoskeyStone", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemValonite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemRhodochrosite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemBoronNitride", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemFluorite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemVilliaumite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCarobbiite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemMoon", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemRedstone", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemVinteum", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemQuartzite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemGlass", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemQuartzBlock", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAlmandine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemBlueTopaz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCinnabar", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemGreenSapphire", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemRutile", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemLazurite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemSodalite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCertusQuartz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemOlivine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemLignite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemGarnetRed", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemGarnetYellow", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemMonazite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemDominicanAmber", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemScarabBlue", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemScarab", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemMoldavite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeOreDict("gemVioletSapphire", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCatsEye", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAmmolite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemSpinel", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemMoonstone", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemSunstone", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemPyrope", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemRoseQuartz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemKunzite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemKyanite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChrysoprase", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemBlackDiamond", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemTektite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemMorganite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemLepidolite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCoral", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCarnelian", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemGoldenBeryl", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemHeliodor", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemIndicolite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemIolite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemTurquoise", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("materialCoraliumPearl", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemShadow", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemBlackTourmaline", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCrystalFlux", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemLava", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemEnderEssence", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemPhoenixite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemBoronArsenide", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemQuartzBlack", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemMoon", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChaos", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedCinnabar", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedAlmandine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedAmethyst", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedApatite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedBlueTopaz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedCertusQuartz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedCoal", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedCoke", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedDiamond", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedEmerald", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedGarnetRed", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedGarnetYellow", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedGlass", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedGreenSapphire", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedJasper", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedLapis", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedLazurite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedLignite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedMonazite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedNetherQuartz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedOlivine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedOpal", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedQuartzite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedRuby", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedRutile", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedSapphire", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedSodalite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedTanzanite", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedTopaz", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemChippedVinteum", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAmetrine", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemAbalone", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCowry", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("gemCowrie", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeOreDict("dustSalt", new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
		Util.registerAltarUpgradeItem(ModObjects.demon_heart, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 4, 0));
		Util.registerAltarUpgradeItem(ModObjects.grimoire_magia, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		//Addendum 2000-2: While making repairs to SRA units in Sector 3382 on
		//??/??/????.2, Technician [DATA EXPUNGED] reported the discovery of
		//human remains in an advanced state of decay. Analysis of clothing
		//fragments discovered with the remains indicates the remains are 450 -
		//700 yrs old. Valid Foundation security credentials for Dr. Alto Clef
		//were discovered nearby, although a genetic match could not be
		//established. The following note was recovered from a hermetically
		//sealed plastic document sleeve.
		
		//Why did we have to build this thing?
		//When did we do it?
		//How long have we been doing it?
		//Do we even know?!
		
		Util.registerAltarUpgradeItem(ModObjects.athame, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.325));
		Util.registerAltarUpgradeItem(ModObjects.silver_sword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.3));
		Util.registerAltarUpgradeItem(ModObjects.cold_iron_sword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.35));
		Util.registerAltarUpgradeItem(Items.WOODEN_SWORD, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.075));
		Util.registerAltarUpgradeItem(Items.STONE_SWORD, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.15));
		Util.registerAltarUpgradeItem(Items.IRON_SWORD, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.225));
		Util.registerAltarUpgradeItem(Items.GOLDEN_SWORD, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.3));
		Util.registerAltarUpgradeItem(Items.DIAMOND_SWORD, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.325));
		
		//Botania stuff
		if (Loader.isModLoaded("botania")) {
			Util.registerAltarUpgradeItem(ModItems.twigWand, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.1));
			Util.registerAltarUpgradeItem(ModItems.manasteelSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.23));
			Util.registerAltarUpgradeItem(ModItems.starSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.375));
			Util.registerAltarUpgradeItem(ModItems.thunderSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.375));
			Util.registerAltarUpgradeItem(ModItems.enderDagger, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.23));
			Util.registerAltarUpgradeItem(ModItems.elementiumSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.3));
			Util.registerAltarUpgradeItem(ModItems.terraSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.35));
			Util.registerAltarUpgradeItem(ModItems.cobbleRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.2));
			Util.registerAltarUpgradeItem(ModItems.dirtRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.2));
			Util.registerAltarUpgradeItem(ModItems.diviningRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.25));
			Util.registerAltarUpgradeItem(ModItems.exchangeRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.35));
			Util.registerAltarUpgradeItem(ModItems.fireRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.3));
			Util.registerAltarUpgradeItem(ModItems.gravityRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.3));
			Util.registerAltarUpgradeItem(ModItems.missileRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.4));
			Util.registerAltarUpgradeItem(ModItems.rainbowRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.4));
			Util.registerAltarUpgradeItem(ModItems.skyDirtRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.225));
			Util.registerAltarUpgradeItem(ModItems.smeltRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.225));
			Util.registerAltarUpgradeItem(ModItems.terraformRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.3));
			Util.registerAltarUpgradeItem(ModItems.tornadoRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.25));
			Util.registerAltarUpgradeItem(ModItems.waterRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.2));
			BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockGaiaHead, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 5, 0));
		}
		
		//Thaumcraft stuff
		if (Loader.isModLoaded("thaumcraft")) {
			Util.registerAltarUpgradeItem(ItemsTC.thaumiumSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.25));
			Util.registerAltarUpgradeItem(ItemsTC.voidSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.3));
			Util.registerAltarUpgradeItem(ItemsTC.elementalSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.3));
			Util.registerAltarUpgradeItem(ItemsTC.crimsonBlade, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.325));
			Util.registerAltarUpgradeItem(ItemsTC.primalCrusher, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.35));
			BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof thaumcraft.common.blocks.basic.BlockCandle, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.125));
			BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockNitor, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.225));
			Util.registerAltarUpgradeItem(ItemsTC.pechWand, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.35));
		}
		
		//Consecration Stuff
		if (Loader.isModLoaded("consecration")) {
			Util.registerAltarUpgradeItem(ConsecrationItems.blessedDust, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 0));
			Util.registerAltarUpgradeItem(ConsecrationItems.holyWater, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.12));
			Util.registerAltarUpgradeItem(ConsecrationItems.fireStick, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.14));
		}
		
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockTorch, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.05));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockRedstoneTorch, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.1));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockCandle, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.125));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockCandelabra, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.225));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockEndRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.325));
		Util.registerAltarUpgradeItem(ModObjects.leonards_wand, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.56));
		Util.registerAltarUpgradeItem(ModObjects.caduceus, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.78));
		Util.registerAltarUpgradeItem(Items.BLAZE_ROD, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.2));
	}
	
	private static void athamePostInit() {
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityPlayer, Sets.newHashSet(new ItemStack(ModObjects.heart), new ItemStack(Items.SKULL, 1, 3)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityVillager || e instanceof EntityWitch || e instanceof AbstractIllager, Sets.newHashSet(new ItemStack(ModObjects.heart)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityZombieVillager, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityWither, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 6)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityElderGuardian, Sets.newHashSet(new ItemStack(ModObjects.eye_of_old, 3)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityZombie && !(e instanceof EntityPigZombie), Sets.newHashSet(new ItemStack(Items.SKULL, 1, 2), new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityPigZombie, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 3), new ItemStack(ModObjects.hoof, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof AbstractSkeleton && !(e instanceof EntityWitherSkeleton), Sets.newHashSet(new ItemStack(Items.SKULL), new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityWitherSkeleton, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 1), new ItemStack(ModObjects.spectral_dust, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityCreeper, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 4)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityBlaze, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 2), new ItemStack(Items.BLAZE_ROD, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityGhast, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 8), new ItemStack(Items.GHAST_TEAR, 6)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntitySilverfish, Sets.newHashSet(new ItemStack(ModObjects.silver_nugget, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityEndermite, Sets.newHashSet(new ItemStack(ModObjects.dimensional_sand, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityEnderman, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 2), new ItemStack(ModObjects.dimensional_sand, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityVex, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 4), new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityGuardian, Sets.newHashSet(new ItemStack(ModObjects.eye_of_old)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityPig, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityCow, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntitySheep, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityWolf, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityRabbit, Sets.newHashSet(new ItemStack(Items.RABBIT_FOOT)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof AbstractHorse, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof AbstractHorse && e.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityLizard, Sets.newHashSet(new ItemStack(ModObjects.lizard_leg, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityOwl, Sets.newHashSet(new ItemStack(ModObjects.owlets_wing, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityRaven, Sets.newHashSet(new ItemStack(ModObjects.ravens_feather, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityToad, Sets.newHashSet(new ItemStack(ModObjects.toe_of_frog, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntitySnake, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityBlackDog, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.ectoplasm, 4), new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityHellhound, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.hellhound_horn, 2), new ItemStack(Items.BLAZE_POWDER, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityFeuerwurm, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3), new ItemStack(Items.BLAZE_POWDER, 2), new ItemStack(ModObjects.hellhound_horn, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityImp, Sets.newHashSet(new ItemStack(ModObjects.heart), new ItemStack(ModObjects.hoof, 2), new ItemStack(Items.BLAZE_POWDER, 4), new ItemStack(ModObjects.hellhound_horn, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityDemon, Sets.newHashSet(new ItemStack(ModObjects.demon_heart), new ItemStack(ModObjects.hoof, 2), new ItemStack(Items.BLAZE_POWDER, 16), new ItemStack(ModObjects.hellhound_horn, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityShadowPerson, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 4)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityCleaver, Sets.newHashSet(new ItemStack(ModObjects.heart), new ItemStack(ModObjects.hoof, 2), new ItemStack(Items.BEEF, 2), new ItemStack(Items.BLAZE_POWDER, 4), new ItemStack(ModObjects.hellhound_horn, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityGhost, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 4), new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityDruden, Sets.newHashSet(new ItemStack(ModObjects.cypress_sapling), new ItemStack(ModObjects.heart), new ItemStack(ModObjects.oak_apple_gall, 4), new ItemStack(ModObjects.hoof, 2), new ItemStack(ModObjects.hellhound_horn, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityBaphomet, Sets.newHashSet(new ItemStack(ModObjects.demon_heart, 4), new ItemStack(ModObjects.hoof, 2), new ItemStack(Items.BLAZE_POWDER, 16), new ItemStack(ModObjects.adders_fork, 4), new ItemStack(ModObjects.hellhound_horn, 3)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityLeonard, Sets.newHashSet(new ItemStack(ModObjects.demon_heart, 4), new ItemStack(ModObjects.hellebore, 6), new ItemStack(Items.BLAZE_POWDER, 32), new ItemStack(ModObjects.belladonna, 4), new ItemStack(ModObjects.hellhound_horn, 3)));
	}
	
	private static void furnaceInit() {
		GameRegistry.addSmelting(ModObjects.amethyst_ore, new ItemStack(ModObjects.amethyst), 0.85f);
		GameRegistry.addSmelting(ModObjects.garnet_ore, new ItemStack(ModObjects.garnet), 0.85f);
		GameRegistry.addSmelting(ModObjects.opal_ore, new ItemStack(ModObjects.opal), 0.85f);
		GameRegistry.addSmelting(ModObjects.silver_ore, new ItemStack(ModObjects.silver_ingot), 0.65f);
		GameRegistry.addSmelting(ModObjects.salt_ore, new ItemStack(ModObjects.salt), 0.35f);
		
		GameRegistry.addSmelting(ModObjects.embittered_bricks, new ItemStack(ModObjects.cracked_embittered_bricks), 0.1f);
		GameRegistry.addSmelting(ModObjects.scorned_bricks[0], new ItemStack(ModObjects.cracked_scorned_bricks), 0.1f);
		
		GameRegistry.addSmelting(ModObjects.cypress_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.elder_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.juniper_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		
		GameRegistry.addSmelting(ModObjects.unfired_jar, new ItemStack(ModObjects.empty_jar), 0.15f);
		
		GameRegistry.addSmelting(ModObjects.golden_thread, new ItemStack(Items.GOLD_NUGGET), 0.15f);
	}
	
	private static void furnacePostInit() {
		for (Block block : ForgeRegistries.BLOCKS) {
			if (block instanceof BlockSapling && FurnaceRecipes.instance().getSmeltingResult(new ItemStack(block)).isEmpty()) {
				GameRegistry.addSmelting(block, new ItemStack(ModObjects.wood_ash, 1), 0.15f);
			}
		}
		if (Loader.isModLoaded("dynamictrees")) {
			DynamicTreesCompat.addFurnaceRecipes();
		}
	}
	
	protected static void addCauldronRecipes() {
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "focal_chalk"), Arrays.asList(Util.get(ModObjects.ritual_chalk), Util.get(ModObjects.liquid_witchcraft), Util.get("nuggetGold")), Arrays.asList(new ItemStack(ModObjects.focal_chalk), new ItemStack(ModObjects.empty_jar))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "fiery_chalk"), Arrays.asList(Util.get(ModObjects.ritual_chalk), Util.get(Items.BLAZE_POWDER), Util.get("netherrack")), Collections.singletonList(new ItemStack(ModObjects.fiery_chalk))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "phasing_chalk"), Arrays.asList(Util.get(ModObjects.ritual_chalk), Util.get(ModObjects.dimensional_sand), Util.get("dustGlowstone")), Collections.singletonList(new ItemStack(ModObjects.phasing_chalk))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "filled_goblet"), Arrays.asList(Util.get(ModObjects.goblet), Util.get(ModObjects.cloudy_oil), Util.get(Items.GHAST_TEAR), Util.get("dustRedstone"), Util.get("dustRedstone"), Util.get("dustRedstone")), Arrays.asList(new ItemStack(ModObjects.filled_goblet), new ItemStack(ModObjects.empty_jar))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "embergrass"), Arrays.asList(Util.get(new ItemStack(Blocks.TALLGRASS, 1, 1)), Util.get(ModObjects.liquid_witchcraft), Util.get(Items.BLAZE_POWDER), Util.get(new ItemStack(Blocks.YELLOW_FLOWER, 1, Short.MAX_VALUE), new ItemStack(Blocks.RED_FLOWER, 1, Short.MAX_VALUE))), Arrays.asList(new ItemStack(ModObjects.embergrass), new ItemStack(ModObjects.empty_jar))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "torchwood"), Arrays.asList(Util.get(new ItemStack(Blocks.TALLGRASS, 1, 1)), Util.get(ModObjects.liquid_witchcraft), Util.get("logWood"), Util.get("glowstone")), Arrays.asList(new ItemStack(ModObjects.torchwood), new ItemStack(ModObjects.empty_jar))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "tallow"), Arrays.asList(Util.get(Items.ROTTEN_FLESH), Util.get(Items.ROTTEN_FLESH)), Collections.singletonList(new ItemStack(ModObjects.tallow))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "slimeball"), Collections.singletonList(Util.get(ModObjects.hoof)), Collections.singletonList(new ItemStack(Items.SLIME_BALL))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "iron_gall_ink"), Arrays.asList(Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get("nuggetIron")), Collections.singletonList(new ItemStack(ModObjects.iron_gall_ink, 3))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "catechu_brown"), Collections.singletonList(Util.get("logWood")), Collections.singletonList(new ItemStack(ModObjects.catechu_brown, 4))));
		
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "poison_potato"), Arrays.asList(Util.get(Items.FERMENTED_SPIDER_EYE), Util.get(Items.POTATO)), Collections.singletonList(new ItemStack(Items.POISONOUS_POTATO))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "banner_pattern_removal"), Collections.singletonList(Util.get(new ItemStack(Items.BANNER, 1, Short.MAX_VALUE))), Collections.singletonList(new ItemStack(Items.BANNER, 1, EnumDyeColor.WHITE.getDyeDamage()))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "wash_sigil"), Collections.singletonList(Util.get(new ItemStack(ModObjects.dragons_blood_broom))), Collections.singletonList(new ItemStack(ModObjects.dragons_blood_broom))));
		
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "blue_ink_cap"), Arrays.asList(Util.get(Blocks.BROWN_MUSHROOM), Util.get("dyeBlue"), Util.get(ModObjects.iron_gall_ink), Util.get(ModObjects.liquid_witchcraft)), Collections.singletonList(new ItemStack(ModObjects.blue_ink_cap))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "frostflower"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, OreDictionary.WILDCARD_VALUE)), Util.get(Items.SNOWBALL), Util.get(ModObjects.perpetual_ice), Util.get(ModObjects.liquid_witchcraft)), Collections.singletonList(new ItemStack(ModObjects.frostflower))));
		
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_allium"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 2)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_allium))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_azure_bluet"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 3)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_azure_bluet))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_blue_orchid"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 1)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_blue_orchid))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_dandelion"), Arrays.asList(Util.get(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_dandelion))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_oxeye_daisy"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 8)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_oxeye_daisy))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_tulip_orange"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 5)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_tulip_orange))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_tulip_pink"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 7)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_tulip_pink))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_tulip_red"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 4)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_tulip_red))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_tulip_white"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 6)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_tulip_white))));
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "flower_siphoning_poppy"), Arrays.asList(Util.get(new ItemStack(Blocks.RED_FLOWER, 1, 0)), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.bone_needle), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.flower_siphoning_poppy))));
		
		// There, the witches danced, and ate the flesh of an unborn lamb, tainted with reptiles and amphibians, to sully it's sinless nature.
		cauldronRecipes.add(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "stew_of_the_grotesque"), Arrays.asList(Util.get(ModObjects.demonic_elixir), Util.get(ModObjects.heart), Util.get(Items.MUTTON), Util.get(Items.SLIME_BALL), Util.get(ModObjects.lizard_leg), Util.get(ModObjects.toe_of_frog), Util.get(ModObjects.adders_fork), Util.get(ModObjects.belladonna), Util.get(ModObjects.hellebore)), Arrays.asList(new ItemStack(ModObjects.stew_of_the_grotesque), new ItemStack(ModObjects.empty_jar))));
	}
	
	protected static void addOvenRecipes() {
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit"), new ItemStack(Blocks.SAPLING, 1), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.oak_spirit), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit_alt"), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.oak_spirit), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "spruce_heart"), new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.spruce_heart), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "birch_soul"), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.birch_soul), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil"), new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.cloudy_oil), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "acacia_resin"), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.acacia_resin), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ebb_of_death"), new ItemStack(ModObjects.cypress_sapling), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.ebb_of_death), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "droplet_of_wisdom"), new ItemStack(ModObjects.elder_sapling), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.droplet_of_wisdom), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "liquid_witchcraft"), new ItemStack(ModObjects.mandrake_root), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.liquid_witchcraft), 0.75f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "essence_of_vitality"), new ItemStack(ModObjects.juniper_sapling), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.essence_of_vitality), 0.75f));
		
		if (Loader.isModLoaded("dynamictrees")) {
			for (TreeFamilyVanilla family : ModTrees.baseFamilies) {
				Species species = family.getCommonSpecies();
				String name = species.getSaplingName().toString().toLowerCase();
				name = name.substring(name.indexOf(":") + 1);
				ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, name), species.getSeedStack(1), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(name.contains("oak") ? ModObjects.oak_spirit : name.contains("spruce") ? ModObjects.spruce_heart : name.contains("birch") ? ModObjects.birch_soul : name.contains("acacia") ? ModObjects.acacia_resin : ModObjects.cloudy_oil), 0.75f));
			}
			ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cypress_seed"), DynamicTreesCompat.cypressTree.getCommonSpecies().getSeedStack(1), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.ebb_of_death), 0.75f));
			ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "elder_seed"), new ItemStack(ModObjects.elderberries), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.droplet_of_wisdom), 0.75f));
			ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "juniper_seed"), new ItemStack(ModObjects.juniper_berries), new ItemStack(ModObjects.wood_ash, 1), new ItemStack(ModObjects.essence_of_vitality), 0.75f));
		}
		
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil_alt0"), new ItemStack(Blocks.CACTUS), new ItemStack(Items.DYE, 1, 2), new ItemStack(ModObjects.cloudy_oil), 0.55f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ectoplasm"), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), new ItemStack(ModObjects.ectoplasm, 3), 0.65f, false));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ectoplasm_alt"), new ItemStack(Items.BONE), new ItemStack(Items.DYE, 1, 15), new ItemStack(ModObjects.ectoplasm), 0.65f, false));
		
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand"), new ItemStack(Items.ENDER_EYE), new ItemStack(Items.BLAZE_POWDER, 1, 0), new ItemStack(ModObjects.dimensional_sand, 2), 0.8f, false));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand_alt0"), new ItemStack(Items.SHULKER_SHELL), new ItemStack(Items.CHORUS_FRUIT_POPPED, 1, 0), new ItemStack(ModObjects.dimensional_sand, 4), 1, false));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand_alt1"), new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(ModObjects.dimensional_sand, 2), 0.75f, false));
		
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "witches_can_cook"), new ItemStack(Items.BEEF, 1), new ItemStack(Items.COOKED_BEEF), new ItemStack(ModObjects.tallow), 0.35f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "witches_can_cook_2"), new ItemStack(Items.PORKCHOP, 1), new ItemStack(Items.COOKED_PORKCHOP), new ItemStack(ModObjects.tallow), 0.35f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "witches_can_cook_3"), new ItemStack(Items.MUTTON, 1), new ItemStack(Items.COOKED_MUTTON), new ItemStack(ModObjects.tallow), 0.35f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "witches_can_cook_6"), new ItemStack(Items.CHICKEN, 1), new ItemStack(Items.COOKED_CHICKEN), new ItemStack(ModObjects.tallow), 0.35f));
		
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "witches_can_cook_4"), new ItemStack(Items.FISH, 1), new ItemStack(Items.COOKED_FISH), new ItemStack(ModObjects.cloudy_oil), 0.35f));
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "witches_can_cook_5"), new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1), new ItemStack(ModObjects.cloudy_oil), 0.35f));
		
		ovenRecipes.add(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "witches_can_cook_7"), new ItemStack(Items.RABBIT, 1), new ItemStack(Items.COOKED_RABBIT), new ItemStack(ModObjects.tallow), 0.35f));
	}
	
	protected static void addDistilleryRecipes() {
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "bottled_frostfire"), Arrays.asList(Util.get(Items.GLASS_BOTTLE), Util.get(ModObjects.perpetual_ice), Util.get(ModObjects.cleansing_balm), Util.get(ModObjects.fiery_unguent)), Arrays.asList(new ItemStack(ModObjects.bottled_frostfire), new ItemStack(ModObjects.empty_jar, 2))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "cleansing_balm"), Arrays.asList(Util.get(ModObjects.acacia_resin), Util.get("cropWhiteSage"), Util.get("salt"), Util.get("cropGarlic")), Arrays.asList(new ItemStack(ModObjects.cleansing_balm), new ItemStack(ModObjects.wood_ash))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "demonic_elixir"), Arrays.asList(Util.get(ModObjects.demon_heart), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.empty_jar), Util.get(ModObjects.empty_jar)), Collections.singletonList(new ItemStack(ModObjects.demonic_elixir, 3))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "fiery_unguent"), Arrays.asList(Util.get(Items.BLAZE_POWDER), Util.get(ModObjects.cloudy_oil), Util.get("wax")), Collections.singletonList(new ItemStack(ModObjects.fiery_unguent))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "blood_from_hearts"), Arrays.asList(Util.get(ModObjects.heart), Util.get(Items.GLASS_BOTTLE), Util.get(Items.GLASS_BOTTLE), Util.get(Items.GLASS_BOTTLE)), Collections.singletonList(new ItemStack(ModObjects.bottle_of_blood, 3))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "bottled_hellfire"), Arrays.asList(Util.get(Items.GLASS_BOTTLE), Util.get("wax"), Util.get("wax"), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.dragons_blood_resin)), Arrays.asList(new ItemStack(ModObjects.bottled_hellfire), new ItemStack(ModObjects.empty_jar, 1))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "swirl_of_depths"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get(Items.FISH), Util.get("coquina"), Util.get(new ItemStack(Items.POTIONITEM, 1, 0))), Arrays.asList(new ItemStack(ModObjects.swirl_of_depths), new ItemStack(Items.GLASS_BOTTLE, 1))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "oil_of_vitriol"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get("nuggetIron"), Util.get("gunpowder"), Util.get(ModObjects.dragons_blood_resin)), Collections.singletonList(new ItemStack(ModObjects.oil_of_vitriol))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "otherworld_tears"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.dimensional_sand), Util.get("enderpearl"), Util.get("dustGlowstone")), Collections.singletonList(new ItemStack(ModObjects.otherworldly_tears))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "stone_ichor"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get("stone"), Util.get("nuggetIron"), Util.get("gravel")), Collections.singletonList(new ItemStack(ModObjects.stone_ichor))));
		distilleryRecipes.add(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "heaven_extract"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.birch_soul), Util.get("feather"), Util.get(ModObjects.owlets_wing)), Arrays.asList(new ItemStack(ModObjects.heaven_extract), new ItemStack(ModObjects.empty_jar))));
	}
	
	protected static void addSpinningWheelRecipes() {
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "cobweb"), Arrays.asList(Util.get("string"), Util.get("string"), Util.get("string")), Collections.singletonList(new ItemStack(Blocks.WEB))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "witches_stitching"), Arrays.asList(Util.get("string"), Util.get("string"), Util.get(ModObjects.liquid_witchcraft), Util.get(ModObjects.liquid_witchcraft)), Arrays.asList(new ItemStack(ModObjects.witches_stitching, 2), new ItemStack(ModObjects.empty_jar, 2))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "diabolical_vein"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.golden_thread), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.heart)), Arrays.asList(new ItemStack(ModObjects.diabolical_vein, 2), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "pure_filament"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.golden_thread), Util.get(ModObjects.acacia_resin), Util.get("cropWhiteSage")), Arrays.asList(new ItemStack(ModObjects.pure_filament, 2), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "sanguine_cloth"), Arrays.asList(Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 15)), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 15)), Util.get(ModObjects.bottle_of_blood), Util.get(ModObjects.diabolical_vein)), Arrays.asList(new ItemStack(ModObjects.sanguine_cloth, 2), new ItemStack(Items.GLASS_BOTTLE))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "spirit_string"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.golden_thread), Util.get(ModObjects.ectoplasm), Util.get(ModObjects.spanish_moss)), Collections.singletonList(new ItemStack(ModObjects.spirit_string, 2))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "golden_thread"), Arrays.asList(Util.get("nuggetGold"), Util.get("cropWheat"), Util.get("cropWheat"), Util.get(ModObjects.liquid_witchcraft)), Arrays.asList(new ItemStack(ModObjects.golden_thread, 2), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "alchemist_hat"), Arrays.asList(Util.get(ModObjects.witches_hat), Util.get(ModObjects.heaven_extract), Util.get(ModObjects.witches_stitching), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 8))), Arrays.asList(new ItemStack(ModObjects.alchemist_hat), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "alchemist_hood"), Arrays.asList(Util.get(ModObjects.witches_cowl), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.witches_stitching), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 8))), Arrays.asList(new ItemStack(ModObjects.alchemist_cowl), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "alchemist_robes"), Arrays.asList(Util.get(ModObjects.witches_robes), Util.get(ModObjects.swirl_of_depths), Util.get(ModObjects.witches_stitching), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 8))), Arrays.asList(new ItemStack(ModObjects.alchemist_robes), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "alchemist_pants"), Arrays.asList(Util.get(ModObjects.witches_pants), Util.get(ModObjects.stone_ichor), Util.get(ModObjects.witches_stitching), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 8))), Arrays.asList(new ItemStack(ModObjects.alchemist_pants), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "besmirched_hat"), Arrays.asList(Util.get(ModObjects.witches_hat), Util.get(ModObjects.diabolical_vein), Util.get(ModObjects.golden_thread), Util.get(ModObjects.sanguine_cloth)), Collections.singletonList(new ItemStack(ModObjects.besmirched_hat))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "besmirched_hood"), Arrays.asList(Util.get(ModObjects.witches_cowl), Util.get(ModObjects.diabolical_vein), Util.get(ModObjects.sanguine_cloth), Util.get(ModObjects.sanguine_cloth)), Collections.singletonList(new ItemStack(ModObjects.besmirched_cowl))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "besmirched_robes"), Arrays.asList(Util.get(ModObjects.witches_robes), Util.get(ModObjects.diabolical_vein), Util.get(ModObjects.golden_thread), Util.get(ModObjects.sanguine_cloth)), Collections.singletonList(new ItemStack(ModObjects.besmirched_robes))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "besmirched_pants"), Arrays.asList(Util.get(ModObjects.witches_pants), Util.get(ModObjects.diabolical_vein), Util.get(ModObjects.golden_thread), Util.get(ModObjects.sanguine_cloth)), Collections.singletonList(new ItemStack(ModObjects.besmirched_pants))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "green_witch_hat"), Arrays.asList(Util.get(ModObjects.witches_hat), Util.get(ModObjects.pure_filament), Util.get("treeSapling"), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 11))), Collections.singletonList(new ItemStack(ModObjects.green_witch_hat))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "green_witch_hood"), Arrays.asList(Util.get(ModObjects.witches_cowl), Util.get(ModObjects.pure_filament), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 11)), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 11))), Collections.singletonList(new ItemStack(ModObjects.green_witch_cowl))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "green_witch_robes"), Arrays.asList(Util.get(ModObjects.witches_robes), Util.get(ModObjects.pure_filament), Util.get("treeSapling"), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 11))), Collections.singletonList(new ItemStack(ModObjects.green_witch_robes))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "green_witch_pants"), Arrays.asList(Util.get(ModObjects.witches_pants), Util.get(ModObjects.pure_filament), Util.get("treeSapling"), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 11))), Collections.singletonList(new ItemStack(ModObjects.green_witch_pants))));
		
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "alchemist_fleece"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get("elementalFume"), Util.get(new ItemStack(Blocks.WOOL, 1, 8)), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 8))), Arrays.asList(new ItemStack(ModObjects.alchemists_fleece, 3), new ItemStack(ModObjects.empty_jar))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "green_witch_fleece"), Arrays.asList(Util.get("treeLeaves"), Util.get(Items.LEATHER), Util.get(new ItemStack(Blocks.WOOL, 1, 11)), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 11))), Collections.singletonList(new ItemStack(ModObjects.greenwitch_fleece, 3))));
		spinningWheelRecipes.add(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "besmirched_fleece"), Arrays.asList(Util.get(ModObjects.sanguine_cloth), Util.get(ModObjects.golden_thread), Util.get(new ItemStack(Blocks.WOOL, 1, 15)), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 15))), Collections.singletonList(new ItemStack(ModObjects.besmirched_fleece, 3))));
	}
	
	protected static void addRitualRecipe() {
		ritualRecipes.add(new RitualSolarGlory());
		ritualRecipes.add(new RitualHighMoon());
		ritualRecipes.add(new RitualSandsOfTime());
		ritualRecipes.add(new RitualDeluge());
		ritualRecipes.add(new RitualTeleport());
		ritualRecipes.add(new RitualPerception());
		ritualRecipes.add(new RitualHungryFlames());
		ritualRecipes.add(new RitualFrenziedGrowth());
		ritualRecipes.add(new RitualCallOfTheWild());
		ritualRecipes.add(new RitualLesserHellMouth());
		ritualRecipes.add(new RitualHellmouth());
		ritualRecipes.add(new RitualGreaterHellmouth());
		ritualRecipes.add(new RitualConjureWitch());
		ritualRecipes.add(new RitualConjureWither());
		ritualRecipes.add(new RitualConjureDemon());
		ritualRecipes.add(new RitualConjureImp());
		ritualRecipes.add(new RitualConjureBaphomet());
		ritualRecipes.add(new RitualConjureLeonard());
		ritualRecipes.add(new RitualSpiritualRift());
		ritualRecipes.add(new RitualBiomeShift());
		ritualRecipes.add(new RitualCurseCleansing(true));
		ritualRecipes.add(new RitualCurseCleansing(false));
		ritualRecipes.add(new RitualDrawing(new ResourceLocation(Bewitchment.MODID, "draw_small"), Collections.singletonList(Util.get(Items.CLAY_BALL)), 150, BlockGlyph.ANY, -1, -1, Ritual.small));
		ritualRecipes.add(new RitualDrawing(new ResourceLocation(Bewitchment.MODID, "draw_medium"), Arrays.asList(Util.get(Items.CLAY_BALL), Util.get(ModObjects.wood_ash)), 300, BlockGlyph.ANY, BlockGlyph.ANY, -1, Ritual.medium));
		ritualRecipes.add(new RitualDrawing(new ResourceLocation(Bewitchment.MODID, "draw_large"), Arrays.asList(Util.get(Items.CLAY_BALL), Util.get(Items.CLAY_BALL), Util.get(ModObjects.wood_ash), Util.get(ModObjects.wood_ash)), 450, BlockGlyph.ANY, BlockGlyph.ANY, BlockGlyph.ANY, Ritual.large));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "crystal_ball"), Arrays.asList(Util.get("blockGlass"), Util.get("gemQuartz"), Util.get("gemQuartz"), Util.get("gemQuartz"), Util.get(ModObjects.droplet_of_wisdom)), null, Collections.singletonList(new ItemStack(ModObjects.crystal_ball)), 5, 500, 50, BlockGlyph.NORMAL, BlockGlyph.ENDER, -1));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "tarot_table"), Arrays.asList(Util.get(Blocks.STONEBRICK), Util.get(ModObjects.liquid_witchcraft), Util.get(ModObjects.juniper_planks), Util.get(new ItemStack(Blocks.WOOL, 1, Short.MAX_VALUE))), null, Collections.singletonList(new ItemStack(ModObjects.tarot_table)), 7, 750, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "tarot_cards"), Arrays.asList(Util.get("dye"), Util.get("dye"), Util.get("dye"), Util.get("paper"), Util.get("materialWax", "materialBeeswax", "wax", "tallow", "materialPressedWax", "itemBeeswax", "clumpWax", "beeswax", "itemWax")), null, Collections.singletonList(new ItemStack(ModObjects.tarot_cards)), 4, 500, 25, BlockGlyph.NORMAL, -1, -1));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "grimoire_magia"), Arrays.asList(Util.get("leather"), Util.get("leather"), Util.get("paper"), Util.get("paper"), Util.get("paper"), Util.get(ModObjects.liquid_witchcraft), Util.get(ModObjects.opal)), null, Collections.singletonList(ItemGrimoireMagia.create(0)), 5, 150, 20, BlockGlyph.NORMAL, -1, -1));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "purifying_earth"), Arrays.asList(Util.get("dirt"), Util.get("dirt"), Util.get("dirt"), Util.get("dirt"), Util.get("cropWhiteSage"), Util.get("cropWhiteSage"), Util.get("salt"), Util.get("salt")), null, Collections.singletonList(new ItemStack(ModObjects.purifying_earth, 16)), 2, 200, 20, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "cypress_broom"), Arrays.asList(Util.get(ModObjects.broom), Util.get(ModObjects.cypress_wood), Util.get(ModObjects.cypress_leaves), Util.get(ModObjects.flying_ointment), Util.get(ModObjects.ebb_of_death)), null, Collections.singletonList(new ItemStack(ModObjects.cypress_broom)), 10, 1250, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.ENDER));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "elder_broom"), Arrays.asList(Util.get(ModObjects.broom), Util.get(ModObjects.elder_wood), Util.get(ModObjects.elder_leaves), Util.get(ModObjects.flying_ointment), Util.get(ModObjects.droplet_of_wisdom)), null, Collections.singletonList(new ItemStack(ModObjects.elder_broom)), 10, 1250, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.ENDER));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "juniper_broom"), Arrays.asList(Util.get(ModObjects.broom), Util.get(ModObjects.juniper_wood), Util.get(ModObjects.juniper_leaves), Util.get(ModObjects.flying_ointment), Util.get(ModObjects.liquid_witchcraft)), null, Collections.singletonList(new ItemStack(ModObjects.juniper_broom)), 10, 1250, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.ENDER));
		ritualRecipes.add(new Ritual(new ResourceLocation(Bewitchment.MODID, "dragons_blood_broom"), Arrays.asList(Util.get(ModObjects.broom), Util.get(ModObjects.dragons_blood_wood), Util.get(ModObjects.dragons_blood_leaves), Util.get(ModObjects.flying_ointment), Util.get(ModObjects.liquid_witchcraft), Util.get(ModObjects.dragons_blood_resin)), null, Collections.singletonList(new ItemStack(ModObjects.dragons_blood_broom)), 10, 1250, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.ENDER));
		if (ModConfig.memes.wednesday) ritualRecipes.add(new RitualWednesday());
	}
}
