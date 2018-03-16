/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.spell;

import com.bewitchment.common.core.capability.transformation.CapabilityTransformationData;
import com.bewitchment.common.core.capability.transformation.ITransformationData;
import com.bewitchment.common.transformation.ModTransformations;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SpellCallThunderstorm extends Spell {

	public SpellCallThunderstorm(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		world.getWorldInfo().setCleanWeatherTime(0);
		world.getWorldInfo().setRainTime(2000);
		world.getWorldInfo().setThunderTime(3000);
		world.getWorldInfo().setRaining(true);
		world.getWorldInfo().setThundering(true);
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		if (caster instanceof EntityPlayer) {
			ITransformationData data = ((EntityPlayer) caster).getCapability(CapabilityTransformationData.CAPABILITY, null);
			return data.getType() == ModTransformations.VAMPIRE && data.getLevel() >= 9;
		}
		return false;
	}

}
