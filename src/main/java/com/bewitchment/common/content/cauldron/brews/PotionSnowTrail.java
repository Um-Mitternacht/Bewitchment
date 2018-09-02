package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class PotionSnowTrail extends BrewMod {

	public PotionSnowTrail() {
		super("snow_trail", false, 0xccfff9, false, 3 * 60 * 20);
		this.setIconIndex(4, 1);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {

		int box = 1 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);

		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = world.rand.nextInt(7) <= amplifier;
			if (place && Blocks.SNOW_LAYER.canPlaceBlockAt(world, spot) && state.getBlock().isReplaceable(world, spot)) {
				if (state.getBlock() == Blocks.SNOW_LAYER) {
					int stage = state.getValue(BlockSnow.LAYERS);
					if (stage < 8) {
						world.setBlockState(spot, Blocks.SNOW_LAYER.getDefaultState().withProperty(BlockSnow.LAYERS, stage + 1), 3);
					} else {
						world.setBlockState(spot, Blocks.SNOW.getDefaultState(), 3);
					}
				} else {
					world.setBlockState(spot, Blocks.SNOW_LAYER.getDefaultState(), 3);
				}
			}
		}
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return (duration % Math.max(1, 10 - 2 * amplifier)) == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		for (int l = 0; l < 4; ++l) {
			int i = MathHelper.floor(entity.posX + (l % 2 * 2 - 1) * 0.25F);
			int j = MathHelper.floor(entity.posY);
			int k = MathHelper.floor(entity.posZ + (l / 2 % 2 * 2 - 1) * 0.25F);
			BlockPos blockpos = new BlockPos(i, j, k);
			World world = entity.world;
			if (world.isAirBlock(blockpos) && Blocks.SNOW_LAYER.canPlaceBlockAt(world, blockpos)) {
				world.setBlockState(blockpos, Blocks.SNOW_LAYER.getDefaultState());
			}
		}
	}

}
