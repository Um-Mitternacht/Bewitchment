package com.bewitchment.common.world.gen;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.world.gen.tree.WorldGenCypressTree;
import com.bewitchment.common.world.gen.tree.WorldGenElderTree;
import com.bewitchment.common.world.gen.tree.WorldGenJuniperTree;
import com.bewitchment.common.world.gen.tree.WorldGenYewTree;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;
import java.util.function.Predicate;

public class ModWorldGen implements IWorldGenerator {
	private final WorldGenerator silverOre = new WorldGenMinable(ModObjects.silver_ore.getDefaultState(), Bewitchment.proxy.config.silverSize);
	private final WorldGenerator saltOre = new WorldGenMinable(ModObjects.salt_ore.getDefaultState(), Bewitchment.proxy.config.saltSize);
	private final WorldGenerator amethystOre = new WorldGenMinable(ModObjects.amethyst_ore.getDefaultState(), Bewitchment.proxy.config.amethystSize);
	private final WorldGenerator garnetOre = new WorldGenMinable(ModObjects.garnet_ore.getDefaultState(), Bewitchment.proxy.config.garnetSize);
	private final WorldGenerator moonstoneOre = new WorldGenMinable(ModObjects.moonstone_ore.getDefaultState(), Bewitchment.proxy.config.moonstoneSize);

	public ModWorldGen() {
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.aconitum_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.belladonna_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.hellebore_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.mandrake_seeds), 3);
		MinecraftForge.addGrassSeed(new ItemStack(ModObjects.wormwood_seeds), 3);
	}

	@Override
	public void generate(Random rand, int chunkX, int chunkZ, World world, IChunkGenerator generator, IChunkProvider provider) {
		if (world.provider instanceof WorldProviderSurface) {
			generateTree(world, rand, new WorldGenCypressTree(true), ModObjects.cypress_sapling, chunkX, chunkZ, Bewitchment.proxy.config.cypressChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && (BiomeDictionary.hasType(b, BiomeDictionary.Type.COLD) || BiomeDictionary.hasType(b, BiomeDictionary.Type.SPOOKY)));
			generateTree(world, rand, new WorldGenElderTree(true), ModObjects.elder_sapling, chunkX, chunkZ, Bewitchment.proxy.config.elderChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && !BiomeDictionary.hasType(b, BiomeDictionary.Type.COLD));
			generateTree(world, rand, new WorldGenJuniperTree(true), ModObjects.juniper_sapling, chunkX, chunkZ, Bewitchment.proxy.config.juniperChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.SAVANNA) || BiomeDictionary.hasType(b, BiomeDictionary.Type.MAGICAL));
			generateTree(world, rand, new WorldGenYewTree(true), ModObjects.yew_sapling, chunkX, chunkZ, Bewitchment.proxy.config.yewChance, b -> BiomeDictionary.hasType(b, BiomeDictionary.Type.FOREST) && BiomeDictionary.hasType(b, BiomeDictionary.Type.DENSE));
			generateCoquina(world, rand, chunkX, chunkZ);
			generateOre(world, rand, silverOre, chunkX, chunkZ, Bewitchment.proxy.config.silverChance, Bewitchment.proxy.config.silverMin, Bewitchment.proxy.config.silverMax);
			generateOre(world, rand, saltOre, chunkX, chunkZ, Bewitchment.proxy.config.saltChance, Bewitchment.proxy.config.saltMin, Bewitchment.proxy.config.saltMax);
			generateOre(world, rand, amethystOre, chunkX, chunkZ, Bewitchment.proxy.config.amethystChance, Bewitchment.proxy.config.amethystMin, Bewitchment.proxy.config.amethystMax);
			generateOre(world, rand, garnetOre, chunkX, chunkZ, Bewitchment.proxy.config.garnetChance, Bewitchment.proxy.config.garnetMin, Bewitchment.proxy.config.garnetMax);
			generateOre(world, rand, moonstoneOre, chunkX, chunkZ, Bewitchment.proxy.config.moonstoneChance, Bewitchment.proxy.config.moonstoneMin, Bewitchment.proxy.config.moonstoneMax);
		}
	}

	private void generateTree(World world, Random rand, WorldGenerator gen, Block block, int chunkX, int chunkZ, int chance, Predicate<Biome> predicate) {
		if (chance != 0 && rand.nextInt(chance) == 0) {
			int x = chunkX * 16 + rand.nextInt(16);
			int z = chunkZ * 16 + rand.nextInt(16);
			BlockPos pos = new BlockPos(x, world.getHeight(x, z), z);
			Biome biome = world.getBiome(pos);
			if (predicate.test(biome) && block.canPlaceBlockAt(world, pos)) gen.generate(world, rand, pos);
		}
	}

	private void generateCoquina(World world, Random rand, int chunkX, int chunkZ) {
		BlockPos pos = world.getHeight(new BlockPos(chunkX * 16 + rand.nextInt(16), 0, chunkZ * 16 + rand.nextInt(16)));
		if (BiomeDictionary.getBiomes(BiomeDictionary.Type.BEACH).contains(world.getBiome(pos)) || BiomeDictionary.getBiomes(BiomeDictionary.Type.OCEAN).contains(world.getBiome(pos))) {
			while (world.getBlockState(pos.down()).getBlock() == Blocks.SAND) {
				for (int i = 0; i < 3; i++) {
					int x = rand.nextInt(2);
					int y = rand.nextInt(2);
					int z = rand.nextInt(2);
					for (BlockPos blockpos : BlockPos.getAllInBox(pos.add(-x, -y, -z), pos.add(x, y, z)))
						if (blockpos.distanceSq(pos) <= Math.pow((x + y + z) * .333f + 0.5f, 2))
							world.setBlockState(blockpos, ModObjects.coquina.getDefaultState(), 2);
					pos = pos.add(rand.nextInt(2) - 1, -rand.nextInt(2), rand.nextInt(2) - 1);
				}
				pos = pos.down();
			}
		}
	}

	private void generateOre(World world, Random rand, WorldGenerator gen, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight) {
		for (int i = 0; i < chance; i++)
			gen.generate(world, rand, new BlockPos(chunkX * 16 + rand.nextInt(16), rand.nextInt(maxHeight - minHeight) + minHeight, chunkZ * 16 + rand.nextInt(16)));
	}
}