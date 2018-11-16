/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.content.spell.spells;

import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.spell.Spell;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
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
			CapabilityTransformation data = ((EntityPlayer) caster).getCapability(CapabilityTransformation.CAPABILITY, null);
			return data.getType() == DefaultTransformations.VAMPIRE && data.getLevel() >= 9;
		}
		return false;
	}

}
