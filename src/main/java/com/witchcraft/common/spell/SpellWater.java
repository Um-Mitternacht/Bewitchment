/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */

package com.witchcraft.common.spell;

import com.witchcraft.api.spell.Spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class SpellWater extends Spell {

	public SpellWater(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		if (rtrace.typeOfHit==Type.BLOCK) {
			BlockPos pos = rtrace.getBlockPos().offset(rtrace.sideHit);
			if (world.isAirBlock(pos)) world.setBlockState(pos, Blocks.WATER.getDefaultState(), 3);
		}
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return !world.provider.isNether();
	}

}
