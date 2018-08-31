package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class PotionFrostbite extends BrewMod {

	@ObjectHolder("extraalchemy:effect.freezing")
	public static final Potion freezing = null;

	public PotionFrostbite() {
		super("freezing", true, 0xB0E0E6, true, 0);
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		BlockPos pos = entity.getPosition();
		int box = 1 + amplifier;
		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);
		BlockPos.getAllInBox(posI, posF).forEach(pos1 -> {
			if (entity.world.getBlockState(pos1).getBlock() == Blocks.AIR) {
				entity.world.setBlockState(pos1, Blocks.PACKED_ICE.getDefaultState());
			}
		});
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			if (spot.distanceSq(pos) < 2 + box * box / 2) {
				IBlockState state = world.getBlockState(spot);
				if (world.rand.nextInt(4) <= modifiers.getLevel(DefaultModifiers.POWER).orElse(0) / 2) {
					Block block = state.getBlock();
					if (block == Blocks.WATER && world.isAirBlock(spot.up())) {
						world.setBlockState(spot, Blocks.ICE.getDefaultState(), 3);
					} else if (block == Blocks.SNOW_LAYER) {
						int level = state.getValue(BlockSnow.LAYERS);
						if (level == 8) {
							world.setBlockState(spot, Blocks.SNOW.getDefaultState(), 3);
						} else {
							world.setBlockState(spot, Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, level + 1), 3);
						}
					} else if (block == Blocks.SNOW) {
						world.setBlockState(spot, Blocks.PACKED_ICE.getDefaultState(), 3);
					} else if (block == Blocks.FROSTED_ICE) {
						world.setBlockState(spot, Blocks.ICE.getDefaultState(), 3);
					} else if (block == Blocks.LAVA) {
						world.setBlockState(spot, Blocks.OBSIDIAN.getDefaultState(), 3);
					} else if (block == Blocks.AIR && world.getBlockState(spot.down()).getBlock() == Blocks.GRASS) {
						world.setBlockState(spot, Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, 1), 3);
					}
				}
			}
		}
	}

	public Potion getPotion() {
		if (freezing != null) {
			Bewitchment.logger.info("Extra Alchemy spotted! The freezing potion entity effect will be \"borrowed\" from it");
			return freezing;
		}
		Bewitchment.logger.info("No extra alchemy found, freezing potion will default to its potion object!");
		return this;
	}

}
