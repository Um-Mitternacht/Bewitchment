/**
 * This class was created by <ZaBi94> on Dec 10th, 2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */

package com.bewitchment.common.content.spell.spells;

import com.bewitchment.common.content.spell.Spell;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class SpellMagnet extends Spell {

	public SpellMagnet(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		if (rtrace.typeOfHit == Type.BLOCK && caster != null) {
			world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(rtrace.hitVec), new BlockPos(rtrace.hitVec.add(1, 1, 1))).grow(2)).forEach(ei -> {
				ei.setNoPickupDelay();
				if (caster instanceof EntityPlayer) {
					ei.onCollideWithPlayer((EntityPlayer) caster);
				} else {
					ei.setPositionAndUpdate(caster.posX, caster.posY, caster.posZ);
				}
			});
		}
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return true;
	}

}
