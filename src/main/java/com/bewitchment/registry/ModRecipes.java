package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.registry.*;
import com.bewitchment.common.block.BlockCandle;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.spirit.demon.EntityDemon;
import com.bewitchment.common.entity.spirit.demon.EntityDemoness;
import com.bewitchment.common.entity.spirit.demon.EntityHellhound;
import com.bewitchment.common.entity.spirit.demon.EntitySerpent;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.fortune.*;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;
import java.util.Collections;

@SuppressWarnings("ConstantConditions")
public class ModRecipes {
	public static void init() {
		furnaceInit();
		ovenInit();
		distilleryInit();
		spinningWheelInit();
		frostfireInit();
		fortuneInit();
		altarInit();
		
		ModObjects.TOOL_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.TOOL_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
		ModObjects.ARMOR_COLD_IRON.setRepairItem(new ItemStack(ModObjects.cold_iron_ingot));
		ModObjects.ARMOR_SILVER.setRepairItem(new ItemStack(ModObjects.silver_ingot));
	}
	
	public static void postInit() {
		athamePostInit();
		furnacePostInit();
		ovenPostInit();
	}
	
	private static void altarInit() {
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() == Blocks.SKULL && s.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) s.getTileEntity()).getSkullType() == 5, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 6, 1));
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() == Blocks.SKULL && s.getTileEntity() instanceof TileEntitySkull && ((TileEntitySkull) s.getTileEntity()).getSkullType() == 1, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 3, 1));
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() == Blocks.SKULL, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, 1, 1));
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() == ModObjects.goblet, new AltarUpgrade(AltarUpgrade.Type.CUP, 0, 1.125));
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() == ModObjects.filled_goblet, new AltarUpgrade(AltarUpgrade.Type.CUP, 0, 1.5));
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() instanceof BlockTorch, new AltarUpgrade(AltarUpgrade.Type.WAND, 1, 1));
		BewitchmentAPI.registerAltarUpgrade(s -> s.getBlockState().getBlock() instanceof BlockCandle, new AltarUpgrade(AltarUpgrade.Type.WAND, 2, 1));
		Util.registerAltarUpgradeItem(ModObjects.pentacle, new AltarUpgrade(AltarUpgrade.Type.PENTACLE, -2, 1.6));
	}
	
	private static void athamePostInit() {
		BewitchmentAPI.registerAthameLoot(EntityPlayer.class, Sets.newHashSet(new ItemStack(ModObjects.heart), new ItemStack(Items.SKULL, 1, 3)));
		BewitchmentAPI.registerAthameLoot(EntityVillager.class, Sets.newHashSet(new ItemStack(ModObjects.heart)));
		BewitchmentAPI.registerAthameLoot(EntityZombieVillager.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.registerAthameLoot(EntityWither.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 6)));
		BewitchmentAPI.registerAthameLoot(EntityElderGuardian.class, Sets.newHashSet(new ItemStack(ModObjects.eye_of_old, 4)));
		
		BewitchmentAPI.registerAthameLoot(EntityZombie.class, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 2), new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntityPigZombie.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 3), new ItemStack(ModObjects.hoof, 2)));
		
		BewitchmentAPI.registerAthameLoot(EntityHusk.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntitySkeleton.class, Sets.newHashSet(new ItemStack(Items.SKULL), new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntityWitherSkeleton.class, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 1), new ItemStack(ModObjects.spectral_dust, 2)));
		BewitchmentAPI.registerAthameLoot(EntityStray.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.registerAthameLoot(EntityCreeper.class, Sets.newHashSet(new ItemStack(Items.SKULL, 1, 4)));
		
		BewitchmentAPI.registerAthameLoot(EntityBlaze.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm)));
		BewitchmentAPI.registerAthameLoot(EntityGhast.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 2)));
		
		BewitchmentAPI.registerAthameLoot(EntitySilverfish.class, Sets.newHashSet(new ItemStack(ModObjects.silver_nugget, 2)));
		BewitchmentAPI.registerAthameLoot(EntityEndermite.class, Sets.newHashSet(new ItemStack(ModObjects.dimensional_sand, 2)));
		
		BewitchmentAPI.registerAthameLoot(EntityEnderman.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm)));
		BewitchmentAPI.registerAthameLoot(EntityVex.class, Sets.newHashSet(new ItemStack(ModObjects.ectoplasm, 4)));
		
		BewitchmentAPI.registerAthameLoot(EntityGuardian.class, Sets.newHashSet(new ItemStack(ModObjects.eye_of_old)));
		
		BewitchmentAPI.registerAthameLoot(EntityPig.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityCow.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySheep.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityWolf.class, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog)));
		BewitchmentAPI.registerAthameLoot(EntityRabbit.class, Sets.newHashSet(new ItemStack(Items.RABBIT_FOOT)));
		BewitchmentAPI.registerAthameLoot(EntityHorse.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityDonkey.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityMule.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySkeletonHorse.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust, 2)));
		BewitchmentAPI.registerAthameLoot(EntityLlama.class, Sets.newHashSet(new ItemStack(ModObjects.hoof, 4)));
		BewitchmentAPI.registerAthameLoot(EntityZombieHorse.class, Sets.newHashSet(new ItemStack(ModObjects.spectral_dust)));
		BewitchmentAPI.registerAthameLoot(EntityBat.class, Sets.newHashSet(new ItemStack(ModObjects.wool_of_bat, 3)));
		
		BewitchmentAPI.registerAthameLoot(EntityLizard.class, Sets.newHashSet(new ItemStack(ModObjects.lizard_leg, 4)));
		BewitchmentAPI.registerAthameLoot(EntityNewt.class, Sets.newHashSet(new ItemStack(ModObjects.eye_of_newt, 2)));
		BewitchmentAPI.registerAthameLoot(EntityOwl.class, Sets.newHashSet(new ItemStack(ModObjects.owlets_wing, 2)));
		BewitchmentAPI.registerAthameLoot(EntityRaven.class, Sets.newHashSet(new ItemStack(ModObjects.ravens_feather, 4)));
		BewitchmentAPI.registerAthameLoot(EntityToad.class, Sets.newHashSet(new ItemStack(ModObjects.toe_of_frog, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySnake.class, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3)));
		
		BewitchmentAPI.registerAthameLoot(EntityBlackDog.class, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.ectoplasm, 4), new ItemStack(ModObjects.spectral_dust)));
		
		BewitchmentAPI.registerAthameLoot(EntityHellhound.class, Sets.newHashSet(new ItemStack(ModObjects.tongue_of_dog), new ItemStack(ModObjects.hellhound_horn, 2), new ItemStack(Items.BLAZE_POWDER, 4)));
		BewitchmentAPI.registerAthameLoot(EntitySerpent.class, Sets.newHashSet(new ItemStack(ModObjects.adders_fork, 3)));
		
		BewitchmentAPI.registerAthameLoot(EntityDemon.class, Sets.newHashSet(new ItemStack(ModObjects.demon_heart)));
		BewitchmentAPI.registerAthameLoot(EntityDemoness.class, Sets.newHashSet(new ItemStack(ModObjects.demon_heart)));
	}
	
	private static void furnaceInit() {
		GameRegistry.addSmelting(ModObjects.amethyst_ore, new ItemStack(ModObjects.amethyst), 0.85f);
		GameRegistry.addSmelting(ModObjects.garnet_ore, new ItemStack(ModObjects.garnet), 0.85f);
		GameRegistry.addSmelting(ModObjects.moonstone_ore, new ItemStack(ModObjects.moonstone), 0.85f);
		GameRegistry.addSmelting(ModObjects.silver_ore, new ItemStack(ModObjects.silver_ingot), 0.65f);
		GameRegistry.addSmelting(ModObjects.salt_ore, new ItemStack(ModObjects.salt), 0.35f);
		
		GameRegistry.addSmelting(ModObjects.embittered_bricks, new ItemStack(ModObjects.cracked_embittered_bricks), 0.1f);
		GameRegistry.addSmelting(ModObjects.scorned_bricks[0], new ItemStack(ModObjects.cracked_scorned_bricks), 0.1f);
		
		GameRegistry.addSmelting(ModObjects.cypress_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.elder_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.juniper_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		GameRegistry.addSmelting(ModObjects.yew_wood, new ItemStack(Items.COAL, 1, 1), 0.15f);
		
		GameRegistry.addSmelting(ModObjects.unfired_jar, new ItemStack(ModObjects.empty_jar), 0.15f);
		GameRegistry.addSmelting(ModObjects.golden_thread, new ItemStack(Items.GOLD_NUGGET, 2, 0), 0.65f);
	}
	
	private static void furnacePostInit() {
		for (Block block : ForgeRegistries.BLOCKS) {
			if (block instanceof BlockSapling && FurnaceRecipes.instance().getSmeltingResult(new ItemStack(block)).isEmpty()) GameRegistry.addSmelting(block, new ItemStack(ModObjects.wood_ash, 4), 0.15f);
		}
	}
	
	private static void ovenInit() {
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit"), new ItemStack(Blocks.SAPLING, 1), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.oak_spirit), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit_alt"), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.oak_spirit), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "spruce_heart"), new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.spruce_heart), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "birch_soul"), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.birch_soul), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil"), new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.cloudy_oil), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "acacia_resin"), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.acacia_resin), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ebb_of_death"), new ItemStack(ModObjects.cypress_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.ebb_of_death), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "droplet_of_wisdom"), new ItemStack(ModObjects.elder_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.droplet_of_wisdom), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "liquid_witchcraft"), new ItemStack(ModObjects.juniper_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.liquid_witchcraft), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "essence_of_vitality"), new ItemStack(ModObjects.yew_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.essence_of_vitality), 0.75f));
		
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "bread"), new ItemStack(Items.WHEAT), new ItemStack(Items.BREAD), new ItemStack(ModObjects.cloudy_oil), 0.55f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cactus_green"), new ItemStack(Blocks.CACTUS), new ItemStack(Items.DYE, 1, 2), new ItemStack(ModObjects.cloudy_oil), 0.55f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "popped_chorus_fruit"), new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(ModObjects.dimensional_sand, 2), 0.75f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil_alt"), new ItemStack(ModObjects.mandrake_root), new ItemStack(ModObjects.wood_ash), new ItemStack(ModObjects.cloudy_oil), 0.85f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ectoplasm"), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), new ItemStack(ModObjects.ectoplasm, 3), 0.65f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ectoplasm_alt"), new ItemStack(Items.BONE), new ItemStack(Items.DYE, 1, 15), new ItemStack(ModObjects.ectoplasm), 0.65f));
		
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand_alt0"), new ItemStack(Items.ENDER_EYE), new ItemStack(Items.ENDER_PEARL, 1, 0), new ItemStack(ModObjects.dimensional_sand, 4, 0), 0.35f));
		BewitchmentAPI.registerOvenRecipe(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand_alt1"), new ItemStack(Items.SHULKER_SHELL), new ItemStack(Items.CHORUS_FRUIT_POPPED, 1, 0), new ItemStack(ModObjects.dimensional_sand, 2, 0), 0.45f));
	}
	
	private static void ovenPostInit() {
		for (ItemStack stack : FurnaceRecipes.instance().getSmeltingList().keySet()) {
			if (GameRegistry.findRegistry(OvenRecipe.class).getValuesCollection().stream().noneMatch(r -> Util.areStacksEqual(r.input, stack))) {
				ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, stack.getItem().getRegistryName().getPath() + stack.getMetadata());
				int index = 0;
				while (GameRegistry.findRegistry(OvenRecipe.class).containsKey(loc)) loc = new ResourceLocation(loc.getNamespace(), loc.getPath() + index++);
				BewitchmentAPI.registerOvenRecipe(new OvenRecipe(loc, stack, FurnaceRecipes.instance().getSmeltingResult(stack), stack.getItem() instanceof ItemFood ? new ItemStack(ModObjects.cloudy_oil) : ItemStack.EMPTY, 0.75f));
			}
		}
	}
	
	private static void distilleryInit() {
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "bottled_frostfire"), Arrays.asList(Util.get(Items.GLASS_BOTTLE), Util.get(ModObjects.perpetual_ice), Util.get(ModObjects.cleansing_balm), Util.get(Items.FIRE_CHARGE)), Collections.singletonList(new ItemStack(ModObjects.bottled_frostfire))));
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "cleansing_balm"), Arrays.asList(Util.get(ModObjects.acacia_resin), Util.fromOres("cropWhiteSage"), Util.fromOres("salt"), Util.fromOres("cropGarlic")), Arrays.asList(new ItemStack(ModObjects.cleansing_balm), new ItemStack(ModObjects.wood_ash))));
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "demonic_elixir"), Arrays.asList(Util.get(Items.BLAZE_POWDER), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.demon_heart), Util.get(ModObjects.spectral_dust)), Arrays.asList(new ItemStack(ModObjects.demonic_elixir, 2), new ItemStack(ModObjects.diabolical_vein))));
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "everchanging_dew"), Arrays.asList(Util.fromOres("dye"), Util.get(ModObjects.essence_of_vitality), Util.fromOres("paper")), Arrays.asList(new ItemStack(ModObjects.everchanging_dew), new ItemStack(Items.SLIME_BALL, 3))));
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "fiery_unguent"), Arrays.asList(Util.get(Items.BLAZE_POWDER), Util.get(ModObjects.cloudy_oil), Util.fromOres("obsidian"), Ingredient.fromStacks(new ItemStack(ModObjects.wood_ash))), Collections.singletonList(new ItemStack(ModObjects.fiery_unguent))));
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "heaven_extract"), Arrays.asList(Util.get(ModObjects.birch_soul), Util.fromOres("dustGlowstone"), Util.fromOres("gemGarnet"), Util.fromOres("gemQuartz")), Collections.singletonList(new ItemStack(ModObjects.heaven_extract))));
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "stone_ichor"), Arrays.asList(Util.fromOres("coquina"), Util.get(ModObjects.liquid_witchcraft), Util.fromOres("obsidian"), Util.fromOres("stone")), Arrays.asList(new ItemStack(ModObjects.stone_ichor), new ItemStack(ModObjects.salt, 3))));
		BewitchmentAPI.registerDistilleryRecipe(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "undying_salve"), Arrays.asList(Util.get(ModObjects.ectoplasm), Util.get(ModObjects.ebb_of_death), Util.get(ModObjects.essence_of_vitality)), Arrays.asList(new ItemStack(ModObjects.ectoplasm, 2), new ItemStack(ModObjects.undying_salve, 2))));
	}
	
	private static void spinningWheelInit() {
		BewitchmentAPI.registerSpinningWheelRecipe(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "spider_web"), Arrays.asList(Util.fromOres("string"), Util.fromOres("string"), Util.fromOres("string")), new ItemStack(Blocks.WEB)));
		BewitchmentAPI.registerSpinningWheelRecipe(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "diabolical_vein"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.demon_heart), Util.get(ModObjects.ebb_of_death), Util.get(ModObjects.fiery_unguent)), new ItemStack(ModObjects.diabolical_vein, 4)));
		BewitchmentAPI.registerSpinningWheelRecipe(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "golden_thread"), Arrays.asList(Util.fromOres("cropWheat"), Util.fromOres("cropWheat"), Util.get(Blocks.HAY_BLOCK), Util.get(ModObjects.everchanging_dew)), new ItemStack(ModObjects.golden_thread, 3)));
		BewitchmentAPI.registerSpinningWheelRecipe(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "pure_filament"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.witches_stitching), Util.get(ModObjects.cleansing_balm), Util.get(ModObjects.cleansing_balm)), new ItemStack(ModObjects.pure_filament, 4)));
		BewitchmentAPI.registerSpinningWheelRecipe(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "witches_stitching"), Arrays.asList(Util.fromOres("string"), Util.fromOres("string"), Util.get(ModObjects.oak_spirit), Util.get(ModObjects.oak_spirit)), new ItemStack(ModObjects.witches_stitching, 4)));
	}
	
	private static void frostfireInit() {
		BewitchmentAPI.registerFrostfireRecipe(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_ingot"), Util.fromOres("oreIron"), new ItemStack(ModObjects.cold_iron_ingot)));
		if (!Arrays.asList(Util.fromOres("clusterIron").getMatchingStacks()).isEmpty())
			BewitchmentAPI.registerFrostfireRecipe(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_cluster"), Util.fromOres("clusterIron"), new ItemStack(ModObjects.cold_iron_nugget, 18)));
		if (!Arrays.asList(Util.fromOres("dustIron", "gritIron").getMatchingStacks()).isEmpty())
			BewitchmentAPI.registerFrostfireRecipe(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_ingot_alt"), Util.fromOres("dustIron", "gritIron"), new ItemStack(ModObjects.cold_iron_ingot)));
		if (!Arrays.asList(Util.fromOres("dustTinyIron").getMatchingStacks()).isEmpty())
			BewitchmentAPI.registerFrostfireRecipe(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_nugget"), Util.fromOres("dustTinyIron"), new ItemStack(ModObjects.cold_iron_nugget)));
	}
	
	private static void fortuneInit() {
		BewitchmentAPI.registerFortune(new FortuneBadLuck());
		BewitchmentAPI.registerFortune(new FortuneGoodLuck());
		BewitchmentAPI.registerFortune(new FortuneIllness());
		BewitchmentAPI.registerFortune(new FortuneVitality());
		BewitchmentAPI.registerFortune(new FortuneMeetPet());
		BewitchmentAPI.registerFortune(new FortuneMeetMerchant());
		BewitchmentAPI.registerFortune(new FortuneMeetDemon());
		BewitchmentAPI.registerFortune(new FortuneMeetSerpent());
		BewitchmentAPI.registerFortune(new FortuneMeetBlaze());
		BewitchmentAPI.registerFortune(new FortuneMeetDireWolf());
		BewitchmentAPI.registerFortune(new FortuneMeetSilverfish());
		BewitchmentAPI.registerFortune(new FortuneMeetWitch());
		BewitchmentAPI.registerFortune(new FortuneMeetZombie());
		BewitchmentAPI.registerFortune(new FortuneDeath());
		BewitchmentAPI.registerFortune(new FortuneDropItem());
		BewitchmentAPI.registerFortune(new FortuneTreasure());
	}
}