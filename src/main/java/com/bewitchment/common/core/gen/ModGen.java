package com.bewitchment.common.core.gen;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.BlockGemOre;
import com.bewitchment.common.block.natural.BlockGemOre.Gem;
import com.bewitchment.common.core.statics.ModConfig;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class was created by Arekkuusu on 21/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModGen {

	public static void init() {
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.silver_ore.getDefaultState(),
				ModConfig.WORLD_GEN.silver.silver_min_vein, ModConfig.WORLD_GEN.silver.silver_max_vein,
				ModConfig.WORLD_GEN.silver.silver_min_height, ModConfig.WORLD_GEN.silver.silver_max_height, ModConfig.WORLD_GEN.silver.silver_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.BLOODSTONE),
				ModConfig.WORLD_GEN.blood_stone.bloodStone_min_vein, ModConfig.WORLD_GEN.blood_stone.bloodStone_max_vein,
				ModConfig.WORLD_GEN.blood_stone.bloodStone_min_height, ModConfig.WORLD_GEN.blood_stone.bloodStone_max_height, ModConfig.WORLD_GEN.blood_stone.bloodStone_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.TOURMALINE),
				ModConfig.WORLD_GEN.tourmaline.tourmaline_min_vein, ModConfig.WORLD_GEN.tourmaline.tourmaline_max_vein,
				ModConfig.WORLD_GEN.tourmaline.tourmaline_min_height, ModConfig.WORLD_GEN.tourmaline.tourmaline_max_height, ModConfig.WORLD_GEN.tourmaline.tourmaline_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.MALACHITE),
				ModConfig.WORLD_GEN.malachite.malachite_min_vein, ModConfig.WORLD_GEN.malachite.malachite_max_vein,
				ModConfig.WORLD_GEN.malachite.malachite_min_height, ModConfig.WORLD_GEN.malachite.malachite_max_height, ModConfig.WORLD_GEN.malachite.malachite_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.TIGERS_EYE),
				ModConfig.WORLD_GEN.tigers_eye.tigersEye_min_vein, ModConfig.WORLD_GEN.tigers_eye.tigersEye_max_vein,
				ModConfig.WORLD_GEN.tigers_eye.tigersEye_min_height, ModConfig.WORLD_GEN.tigers_eye.tigersEye_max_height, ModConfig.WORLD_GEN.tigers_eye.tigersEye_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.NUUMMITE),
				ModConfig.WORLD_GEN.nuummite.nuummite_min_vein, ModConfig.WORLD_GEN.nuummite.nuummite_max_vein,
				ModConfig.WORLD_GEN.nuummite.nuummite_min_height, ModConfig.WORLD_GEN.nuummite.nuummite_max_height, ModConfig.WORLD_GEN.nuummite.nuummite_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.GARNET),
				ModConfig.WORLD_GEN.garnet.garnet_min_vein, ModConfig.WORLD_GEN.garnet.garnet_max_vein,
				ModConfig.WORLD_GEN.garnet.garnet_min_height, ModConfig.WORLD_GEN.garnet.garnet_max_height, ModConfig.WORLD_GEN.garnet.garnet_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.JASPER),
				ModConfig.WORLD_GEN.jasper.jasper_min_vein, ModConfig.WORLD_GEN.jasper.jasper_max_vein,
				ModConfig.WORLD_GEN.jasper.jasper_min_height, ModConfig.WORLD_GEN.jasper.jasper_max_height, ModConfig.WORLD_GEN.jasper.jasper_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.BW_AMETHYST),
				ModConfig.WORLD_GEN.bw_amethyst.amethyst_min_vein, ModConfig.WORLD_GEN.bw_amethyst.amethyst_max_vein,
				ModConfig.WORLD_GEN.bw_amethyst.amethyst_min_height, ModConfig.WORLD_GEN.bw_amethyst.amethyst_max_height, ModConfig.WORLD_GEN.bw_amethyst.amethyst_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.gem_ore.getDefaultState().withProperty(BlockGemOre.GEM, Gem.ALEXANDRITE),
				ModConfig.WORLD_GEN.alexandrite.alexandrite_min_vein, ModConfig.WORLD_GEN.alexandrite.alexandrite_max_vein,
				ModConfig.WORLD_GEN.alexandrite.alexandrite_min_height, ModConfig.WORLD_GEN.alexandrite.alexandrite_max_height, ModConfig.WORLD_GEN.alexandrite.alexandrite_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenOre(ModBlocks.salt_ore.getDefaultState(),
				ModConfig.WORLD_GEN.salt.salt_min_vein, ModConfig.WORLD_GEN.salt.salt_max_vein,
				ModConfig.WORLD_GEN.salt.salt_min_height, ModConfig.WORLD_GEN.salt.salt_max_height, ModConfig.WORLD_GEN.salt.salt_gen_chance), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCoquina(), ModConfig.WORLD_GEN.coquina.coquina_gen_chance);
		GameRegistry.registerWorldGenerator(new WorldGenBeehive(), 0);
	}
}
