package com.bewitchment.common.world.gen;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.common.world.gen.tree.WorldGenCypressTree;
import com.bewitchment.common.world.gen.tree.WorldGenElderTree;
import com.bewitchment.common.world.gen.tree.WorldGenJuniperTree;
import com.bewitchment.common.world.gen.tree.WorldGenYewTree;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;
import java.util.function.Predicate;

public class ModWorldGen implements IWorldGenerator {
	private final WorldGenerator cypressTree = new WorldGenCypressTree(true);
	private final WorldGenerator elderTree = new WorldGenElderTree(true);
	private final WorldGenerator juniperTree = new WorldGenJuniperTree(true);
	private final WorldGenerator yewTree = new WorldGenYewTree(true);
	
	private final WorldGenerator silverOre = new WorldGenMinable(ModObjects.silver_ore.getDefaultState(), ModConfig.oreGen.silverSize);
	private final WorldGenerator saltOre = new WorldGenMinable(ModObjects.salt_ore.getDefaultState(), ModConfig.oreGen.saltSize);
	private final WorldGenerator amethystOre = new WorldGenMinable(ModObjects.amethyst_ore.getDefaultState(), ModConfig.oreGen.amethystSize);
	private final WorldGenerator garnetOre = new WorldGenMinable(ModObjects.garnet_ore.getDefaultState(), ModConfig.oreGen.garnetSize);
	private final WorldGenerator moonstoneOre = new WorldGenMinable(ModObjects.moonstone_ore.getDefaultState(), ModConfig.oreGen.moonstoneSize);
	
	public ModWorldGen() {
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.aconitum_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.belladonna_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.hellebore_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.mandrake_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.wormwood_seeds), 3);
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "chests/books"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "chests/materials"));
	}
	
	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider) {
		if (world.provider instanceof WorldProviderSurface) {
			generateCoquina(world, rand, chunkX, chunkZ);
			generateOre(world, rand, silverOre, chunkX, chunkZ, ModConfig.oreGen.silverChance, ModConfig.oreGen.silverMin, ModConfig.oreGen.silverMax);
			generateOre(world, rand, saltOre, chunkX, chunkZ, ModConfig.oreGen.saltChance, ModConfig.oreGen.saltMin, ModConfig.oreGen.saltMax);
			generateOre(world, rand, amethystOre, chunkX, chunkZ, ModConfig.oreGen.amethystChance,ModConfig.oreGen.amethystMin, ModConfig.oreGen.amethystMax);
			generateOre(world, rand, garnetOre, chunkX, chunkZ, ModConfig.oreGen.garnetChance, ModConfig.oreGen.garnetMin, ModConfig.oreGen.garnetMax);
			generateOre(world, rand, moonstoneOre, chunkX, chunkZ, ModConfig.oreGen.moonstoneChance, ModConfig.oreGen.moonstoneMin, ModConfig.oreGen.moonstoneMax);
			generateTree(world, rand, cypressTree, ModObjects.cypress_sapling, chunkX, chunkZ, ModConfig.treeGen.cypressChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && (BiomeDictionary.hasType(b, BiomeDictionary.Type.COLD) || BiomeDictionary.hasType(b, BiomeDictionary.Type.SPOOKY)));
			generateTree(world, rand, elderTree, ModObjects.elder_sapling, chunkX, chunkZ, ModConfig.treeGen.elderChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && !BiomeDictionary.hasType(b, BiomeDictionary.Type.COLD));
			generateTree(world, rand, juniperTree, ModObjects.juniper_sapling, chunkX, chunkZ, ModConfig.treeGen.juniperChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.SAVANNA) || BiomeDictionary.hasType(b, BiomeDictionary.Type.MAGICAL));
			generateTree(world, rand, yewTree, ModObjects.yew_sapling, chunkX, chunkZ, ModConfig.treeGen.yewChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && BiomeDictionary.hasType(b, BiomeDictionary.Type.DENSE));
		}
	}
	
	private void generateCoquina(World world, Random rand, int chunkX, int chunkZ) {
		if (rand.nextInt(6) == 0) {
			BlockPos pos = world.getHeight(new BlockPos(chunkX * 16 + rand.nextInt(16), 0, chunkZ * 16 + rand.nextInt(16)));
			if (BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH).contains(world.getBiome(pos)) || BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN).contains(world.getBiome(pos))) {
				while (world.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
					for (int i = 0; i < 3; i++) {
						int x = rand.nextInt(2);
						int y = rand.nextInt(2);
						int z = rand.nextInt(2);
						for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-x, -y, -z), pos.add(x, y, z)))
							if (blockpos.distanceSq(pos) <= Math.pow((x + y + z) * .333f + 0.5f, 2)) world.setBlockState(blockpos, ModObjects.coquina[0].getDefaultState(), 2);
						pos = pos.add(rand.nextInt(2) - 1, -rand.nextInt(2), rand.nextInt(2) - 1);
					}
					pos = pos.down();
				}
			}
		}
	}
	
	private void generateOre(World world, Random rand, WorldGenerator gen, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight) {
		for (int i = 0; i < chance; i++)
			gen.generate(world, rand, new BlockPos(chunkX * 16, rand.nextInt(maxHeight - minHeight) + minHeight, chunkZ * 16));
	}
	
	private void generateTree(World world, Random rand, WorldGenerator gen, Block block, int chunkX, int chunkZ, int chance, Predicate<Biome> predicate) {
		if (chance != 0 && rand.nextInt(chance) == 0) {
			int x = chunkX * 16 + 8;
			int z = chunkZ * 16 + 8;
			BlockPos pos = new BlockPos(x, world.getHeight(x, z), z);
			Biome biome = world.getBiome(pos);
			if (predicate.test(biome) && block.canPlaceBlockAt(world, pos)) gen.generate(world, rand, pos);
		}
	}
}