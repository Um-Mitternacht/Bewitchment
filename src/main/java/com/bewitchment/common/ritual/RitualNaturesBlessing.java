package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

/**
 * Original code by Zabi94, modified by Sunconure11 afterwards, with bits stuck on from Ael.
 */
public class RitualNaturesBlessing extends Ritual {
	public RitualNaturesBlessing() {
		super(new ResourceLocation(Bewitchment.MODID, "natures_blessing"), Arrays.asList(Util.get(ModObjects.white_sage), Util.get(ModObjects.white_sage), Util.get("gemLapis"), Util.get(ModObjects.essence_of_vitality), Util.get(ModObjects.pentacle), Util.get(new ItemStack(ModObjects.cleansing_balm)), Util.get(new ItemStack(ModObjects.dimensional_sand)), Util.get(new ItemStack(ModObjects.spruce_heart)), Util.get(new ItemStack(ModObjects.oak_spirit)), Util.get(new ItemStack(ModObjects.garlic))), null, null, true, 150, 1600, 75, BlockGlyph.ENDER, BlockGlyph.NORMAL, BlockGlyph.ENDER);
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		for (int i = 0; i < 15; i++) {
			double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.WATER_BUBBLE, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		int radius = 32;
		int minY = 64;
		int maxY = 256;
		for (double x = -radius; x < radius; x++) {
			for (double y = -minY; y < maxY; y++) {
				for (double z = -radius; z < radius; z++) {
					if (Math.sqrt((x * x) + (z * z)) < radius) {
						BlockPos pos = effectivePos.add(x, y, z);
						Block block = world.getBlockState(pos).getBlock();
						//Todo: Check only if a block is an instance of coarse dirt.
						if (block instanceof BlockDirt) {
							world.setBlockState(pos, Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), 3);
						}
						if (!world.isRemote) {
							world.getWorldInfo().setRaining(true);
							world.setRainStrength(1.0f);
							world.getWorldInfo().setThundering(true);
							world.setThunderStrength(1.0f);
						}
					}
				}
			}
		}
	}
}


