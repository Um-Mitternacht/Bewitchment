package com.bewitchment.common.potion.potions.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.item.baubles.ItemGirdleOfTheWooded;
import com.bewitchment.common.potion.BrewMod;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PotionFertilize extends BrewMod {

	public PotionFertilize() {
		super("fertilize", false, 0xede5e3, true, 0);
	}

	@Override
	public void affectEntity(Entity source, Entity indirectSource, EntityLivingBase entity, int amplifier, double health) {
		if (entity instanceof EntityPlayer) {
			for (int i = 0; i < 1 + amplifier; i++) {
				if (entity.getRNG().nextBoolean()) {
					ItemGirdleOfTheWooded.buildBark((EntityPlayer) entity);
				}
			}
		}
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int box = 1 + modifiers.getLevel(DefaultModifiers.RADIUS).orElse(0);
		BlockPos posI = pos.add(box, box, box);
		BlockPos posF = pos.add(-box, -box, -box);
		int amplifier = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);
		Iterable<BlockPos> spots = BlockPos.getAllInBox(posI, posF);
		for (BlockPos spot : spots) {
			IBlockState state = world.getBlockState(spot);
			if (world.rand.nextBoolean() && state.getBlock() instanceof IGrowable) {
				IGrowable crop = (IGrowable) state.getBlock();
				for (int i = 0; i < amplifier + 1; i++)
					if (crop.canGrow(world, spot, state, false))
						crop.grow(world, world.rand, spot, state);
			}
		}
	}
}
