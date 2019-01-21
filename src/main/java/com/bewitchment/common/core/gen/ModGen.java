package com.bewitchment.common.core.gen;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.BlockGemOre;
import com.bewitchment.common.core.statics.ModConfig;
import net.minecraft.init.Blocks;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static com.bewitchment.common.core.gen.WorldGenOre.OreGenBuilder.DEFAULT_STATE;

/**
 * This class was created by Arekkuusu on 21/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModGen {

	private ModGen() {
	}

	public static void init() {
		//World generation
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.silver_ore, ModConfig.WORLD_GEN.silver.silver_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.silver.silver_min_vein, ModConfig.WORLD_GEN.silver.silver_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.silver.silver_min_height, ModConfig.WORLD_GEN.silver.silver_max_height)
				.build(DEFAULT_STATE), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.blood_stone.bloodStone_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.blood_stone.bloodStone_min_vein, ModConfig.WORLD_GEN.blood_stone.bloodStone_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.blood_stone.bloodStone_min_height, ModConfig.WORLD_GEN.blood_stone.bloodStone_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.BLOODSTONE.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.tourmaline.tourmaline_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.tourmaline.tourmaline_min_vein, ModConfig.WORLD_GEN.tourmaline.tourmaline_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.tourmaline.tourmaline_min_height, ModConfig.WORLD_GEN.tourmaline.tourmaline_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.TOURMALINE.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.malachite.malachite_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.malachite.malachite_min_vein, ModConfig.WORLD_GEN.malachite.malachite_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.malachite.malachite_min_height, ModConfig.WORLD_GEN.malachite.malachite_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.MALACHITE.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.tigers_eye.tigersEye_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.tigers_eye.tigersEye_min_vein, ModConfig.WORLD_GEN.tigers_eye.tigersEye_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.tigers_eye.tigersEye_min_height, ModConfig.WORLD_GEN.tigers_eye.tigersEye_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.TIGERS_EYE.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.nuummite.nuummite_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.nuummite.nuummite_min_vein, ModConfig.WORLD_GEN.nuummite.nuummite_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.nuummite.nuummite_min_height, ModConfig.WORLD_GEN.nuummite.nuummite_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.NUUMMITE.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.garnet.garnet_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.garnet.garnet_min_vein, ModConfig.WORLD_GEN.garnet.garnet_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.garnet.garnet_min_height, ModConfig.WORLD_GEN.garnet.garnet_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.GARNET.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.jasper.jasper_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.jasper.jasper_min_vein, ModConfig.WORLD_GEN.jasper.jasper_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.jasper.jasper_min_height, ModConfig.WORLD_GEN.jasper.jasper_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.JASPER.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.salt_ore, ModConfig.WORLD_GEN.salt.salt_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.salt.salt_min_vein, ModConfig.WORLD_GEN.salt.salt_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.salt.salt_min_height, ModConfig.WORLD_GEN.salt.salt_max_height)
				.build(DEFAULT_STATE), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.amethyst.amethyst_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.amethyst.amethyst_min_vein, ModConfig.WORLD_GEN.amethyst.amethyst_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.amethyst.amethyst_min_height, ModConfig.WORLD_GEN.amethyst.amethyst_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.AMETHYST.ordinal())), 0);
		GameRegistry.registerWorldGenerator(WorldGenOre.OreGenBuilder.forOre(ModBlocks.gem_ore, ModConfig.WORLD_GEN.alexandrite.alexandrite_gen_chance)
				.generateOn(Blocks.STONE)
				.setVeinSize(ModConfig.WORLD_GEN.alexandrite.alexandrite_min_vein, ModConfig.WORLD_GEN.alexandrite.alexandrite_max_vein)
				.setHeightRange(ModConfig.WORLD_GEN.alexandrite.alexandrite_min_height, ModConfig.WORLD_GEN.alexandrite.alexandrite_max_height)
				.build(block -> BlockGemOre.getStateById(BlockGemOre.Gem.ALEXANDRITE.ordinal())), 0);
		GameRegistry.registerWorldGenerator(new WorldGenBlob(ModBlocks.coquina), ModConfig.WORLD_GEN.coquina.coquina_gen_chance);
		GameRegistry.registerWorldGenerator(new WorldGenBeehive(), 0);
	}
}
