/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.content.spell.spells;

import com.bewitchment.common.content.spell.Spell;
import com.bewitchment.common.potion.ModPotions;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class SpellMadness extends Spell {

	public SpellMadness(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		if (rtrace.typeOfHit == Type.ENTITY && rtrace.entityHit instanceof EntityLivingBase) {
			((EntityLivingBase) rtrace.entityHit).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 120, 1, false, false));
			((EntityLivingBase) rtrace.entityHit).addPotionEffect(new PotionEffect(MobEffects.HASTE, 120, 1, false, false));
			((EntityLivingBase) rtrace.entityHit).addPotionEffect(new PotionEffect(MobEffects.UNLUCK, 120, 1, false, false));
			((EntityLivingBase) rtrace.entityHit).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 120, 1, false, false));
			((EntityLivingBase) rtrace.entityHit).addPotionEffect(new PotionEffect(ModPotions.revealing, 120, 1, false, false));
			((EntityLivingBase) rtrace.entityHit).addPotionEffect(new PotionEffect(ModPotions.magickal_boon, 120, 1, false, false));
		}
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return true;
	}

}
