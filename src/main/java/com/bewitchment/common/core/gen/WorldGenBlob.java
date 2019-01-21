package com.bewitchment.common.core.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenBlob implements IWorldGenerator
{
	private final Block block;
	
	public WorldGenBlob(Block block)
	{
		this.block = block;
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		BlockPos position = world.getHeight(new BlockPos(chunkX*16+random.nextInt(16), 0, chunkZ*16+random.nextInt(16)));
		if (world.getBiome(position) != Biomes.BEACH) return;
		while (true)
        {
            label:
            {
                if (position.getY() > 3)
                {
                    if (world.isAirBlock(position.down())) break label;
                    if (world.getBlockState(position.down()).getBlock() != Blocks.SAND) break label;
                }
                if (position.getY() <= 3) return;
                for (int i = 0; i < 3; i++)
                {
                    int x = random.nextInt(2);
                    int y = random.nextInt(2);
                    int z = random.nextInt(2);
                    float f = (float)(x + y + z) * 0.333f + 0.5f;
                    for (BlockPos blockpos : BlockPos.getAllInBox(position.add(-x, -y, -z), position.add(x, y, z)))
                    {
                        if (blockpos.distanceSq(position) <= f * f) world.setBlockState(blockpos, this.block.getDefaultState(), 4);
                    }
                    position = position.add(random.nextInt(2 + 0 * 2)-1, 0 - random.nextInt(2), random.nextInt(2)-1);
                }
                return;
            }
            position = position.down();
        }
	}
}
