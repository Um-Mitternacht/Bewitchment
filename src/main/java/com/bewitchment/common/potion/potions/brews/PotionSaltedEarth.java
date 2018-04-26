package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.potion.BrewMod;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

public class PotionSaltedEarth extends BrewMod {

	public PotionSaltedEarth() {
		super("salted_earth", true, 0x555D50, true, 0);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);

		BlockPos posI = pos.add(box, box / 2, box);
		BlockPos posF = pos.add(-box, -box / 2, -box);

		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			boolean place = amplifier > 2 || world.rand.nextBoolean();
			if (place && state.getBlock() == Blocks.FARMLAND && world.isAirBlock(spot.up())) {
				world.setBlockState(spot, Blocks.DIRT.getStateFromMeta(1), 3);
			}
		}
	}
}
