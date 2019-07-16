package com.bewitchment.registry;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.AltarUpgrade;
import com.bewitchment.common.block.BlockCandle;
import com.bewitchment.common.block.BlockLantern;
import com.bewitchment.common.block.tile.entity.TileEntityIdol;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.spirit.demon.EntityDemon;
import com.bewitchment.common.entity.spirit.demon.EntityHellhound;
import com.bewitchment.common.entity.spirit.demon.EntitySerpent;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.ferreusveritas.dynamictrees.items.Seed;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.RecipeTippedArrow;
import net.minecraft.potion.PotionUtils;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.blocks.misc.BlockNitor;
import vazkii.botania.common.item.ModItems;

@SuppressWarnings({"ConstantConditions", "NullableProblems"})
public class ModRecipes {
	public static void init() {
		furnaceInit();
		altarInit();
		
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
		
		ModObjects.TOOL_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.TOOL_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
		ModObjects.ARMOR_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.ARMOR_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
		
		ForgeRegistries.RECIPES.register(new RecipeTippedArrow() {
			@Override
			public ItemStack getCraftingResult(InventoryCrafting inv) {
				ItemStack stack = super.getCraftingResult(inv);
				if (PotionUtils.getPotionFromItem(stack) == PotionTypes.EMPTY) stack.getTagCompound().setInteger("CustomPotionColor", PotionUtils.getPotionColorFromEffectList(PotionUtils.getFullEffectsFromItem(stack)));
				return stack;
			}
		}.setRegistryName("minecraft", "tippedarrow"));
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
		Util.registerAltarUpgradeItem(ModObjects.pentacle, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 3, 0));
		Util.registerAltarUpgradeItem(ModObjects.stone_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityIdol && ((TileEntityIdol) s.getTileEntity()).getInventories()[0].getStackInSlot(0).getItem() == ModObjects.stone_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeItem(ModObjects.terracotta_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityIdol && ((TileEntityIdol) s.getTileEntity()).getInventories()[0].getStackInSlot(0).getItem() == ModObjects.terracotta_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeItem(ModObjects.gold_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityIdol && ((TileEntityIdol) s.getTileEntity()).getInventories()[0].getStackInSlot(0).getItem() == ModObjects.gold_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeItem(ModObjects.nether_brick_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityIdol && ((TileEntityIdol) s.getTileEntity()).getInventories()[0].getStackInSlot(0).getItem() == ModObjects.nether_brick_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeItem(ModObjects.nethersteel_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityIdol && ((TileEntityIdol) s.getTileEntity()).getInventories()[0].getStackInSlot(0).getItem() == ModObjects.nethersteel_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		Util.registerAltarUpgradeItem(ModObjects.scorned_brick_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getTileEntity() instanceof TileEntityIdol && ((TileEntityIdol) s.getTileEntity()).getInventories()[0].getStackInSlot(0).getItem() == ModObjects.scorned_brick_leonard_idol, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 2, 0));
		
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
		}
		
		//Thaumcraft stuff
		if (Loader.isModLoaded("thaumcraft")) {
			Util.registerAltarUpgradeItem(ItemsTC.thaumiumSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.25));
			Util.registerAltarUpgradeItem(ItemsTC.voidSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.3));
			Util.registerAltarUpgradeItem(ItemsTC.elementalSword, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.3));
			Util.registerAltarUpgradeItem(ItemsTC.crimsonBlade, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.325));
			Util.registerAltarUpgradeItem(ItemsTC.primalCrusher, new AltarUpgrade(AltarUpgrade.Type.SWORD, 0, 1.35));
			BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof thaumcraft.common.blocks.basic.BlockCandle, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.1));
			BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockNitor, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.225));
			Util.registerAltarUpgradeItem(ItemsTC.pechWand, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.35));
		}
		
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockTorch, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.05));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockRedstoneTorch, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.1));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockCandle, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.125));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockLantern, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.25));
		BewitchmentAPI.ALTAR_UPGRADES.put(s -> s.getBlockState().getBlock() instanceof BlockEndRod, new AltarUpgrade(AltarUpgrade.Type.WAND, 0, 1.325));
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
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityBlaze, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityGhast, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 8)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntitySilverfish, Sets.newHashSet(new ItemStack(ModObjects.silver_nugget, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityEndermite, Sets.newHashSet(new ItemStack(ModObjects.dimensional_sand, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityEnderman, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityVex, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 4)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityGuardian, Sets.newHashSet(new ItemStack(ModObjects.eye_of_old)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityPig, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityCow, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntitySheep, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityWolf, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityRabbit, Sets.newHashSet(new ItemStack(Items.RABBIT_FOOT)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof AbstractHorse, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof AbstractHorse && e.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 2)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityLizard, Sets.newHashSet(new ItemStack(ModObjects.lizard_leg, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityNewt, Sets.newHashSet(new ItemStack(ModObjects.eye_of_newt, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityOwl, Sets.newHashSet(new ItemStack(ModObjects.owlets_wing, 2)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityRaven && !(e instanceof EntityOwl), Sets.newHashSet(new ItemStack(ModObjects.ravens_feather, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityToad, Sets.newHashSet(new ItemStack(ModObjects.toe_of_frog, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntitySnake, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityBlackDog, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.ectoplasm, 4), new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityHellhound, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.hellhound_horn, 2), new ItemStack(Items.BLAZE_POWDER, 4)));
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntitySerpent, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3)));
		
		BewitchmentAPI.ATHAME_LOOT.put(e -> e instanceof EntityDemon, Sets.newHashSet(new ItemStack(ModObjects.demon_heart)));
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
		GameRegistry.addSmelting(ModObjects.yew_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		
		GameRegistry.addSmelting(ModObjects.unfired_jar, new ItemStack(ModObjects.empty_jar), 0.15f);
	}
	
	private static void furnacePostInit() {
		for (Block block : ForgeRegistries.BLOCKS) {
			if (block instanceof BlockSapling && FurnaceRecipes.instance().getSmeltingResult(new ItemStack(block)).isEmpty()) GameRegistry.addSmelting(block, new ItemStack(ModObjects.wood_ash, 4), 0.15f);
		}
		if (Loader.isModLoaded("dynamictrees")) for (Item item : ForgeRegistries.ITEMS)
			if (item instanceof Seed && !item.getRegistryName().toString().toLowerCase().contains("cactus") && FurnaceRecipes.instance().getSmeltingResult(new ItemStack(item)).isEmpty())
				GameRegistry.addSmelting(item, new ItemStack(ModObjects.wood_ash, 4), 0.15f);
	}
}
