package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.world.BiomeChangingUtils;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

/**
 * Original code by Zabi94, modified by Sunconure11 afterwards, with bits stuck on from Ael.
 */
public class RitualSowingSalt extends Ritual {
	public RitualSowingSalt() {
		super(new ResourceLocation(Bewitchment.MODID, "sowing_salt"), Arrays.asList(Util.get("salt"), Util.get("salt"), Util.get(ModObjects.demon_heart), Util.get(ModObjects.opal), Util.get(ModObjects.pentacle), Util.get(new ItemStack(ModObjects.ebb_of_death)), Util.get(new ItemStack(ModObjects.dimensional_sand)), Util.get(new ItemStack(ModObjects.lizard_leg)), Util.get(new ItemStack(ModObjects.ravens_feather)), Util.get(new ItemStack(ModObjects.ectoplasm))), null, null, true, 150, 1600, 75, BlockGlyph.ENDER, BlockGlyph.NETHER, BlockGlyph.ENDER);
	}

	public Biome getSaltedBiome() {
		return Biomes.DESERT;
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		int radius = 32;
		int minY = 60;
		int maxY = 256;
		for (int i = 0; i < inventory.getSlots(); i++) {
			for (double x = -radius; x < radius; x++) {
				for (double y = -minY; y < maxY; y++) {
					for (double z = -radius; z < radius; z++) {
						if (Math.sqrt((x * x) + (z * z)) < radius) {
							BlockPos pos = effectivePos.add(x, y, z);
							Block block = world.getBlockState(pos).getBlock();
							BiomeChangingUtils.setBiome(world, world.getChunk(pos), pos, getSaltedBiome());
							if (block instanceof BlockDirt) {
								world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), 3);
							} else if (block instanceof BlockGrass) {
								world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), 3);
							} else if (block instanceof BlockGrassPath) {
								world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), 3);
							} else if (block instanceof BlockMycelium) {
								world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), 3);
							} else if (block instanceof BlockFarmland) {
								world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT), 3);
							}
							for (i = 0; i < inventory.getSlots(); i++) {
								inventory.extractItem(i, 1, false);
							}
							if (!world.isRemote) {
								world.getWorldInfo().setRaining(false);
								world.getWorldInfo().setThundering(false);
							}
						}
					}
				}
			}
		}
	}
}