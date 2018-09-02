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
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;

public class SpellDisarming extends Spell {

	public SpellDisarming(int cost, int color, EnumSpellType type, String name, String mod_id) {
		super(cost, color, type, name, mod_id);
	}

	@Override
	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world) {
		if (rtrace.typeOfHit == Type.ENTITY && rtrace.entityHit instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) rtrace.entityHit;
			EnumHand hand = null;
			if (!entity.getHeldItemMainhand().isEmpty()) hand = EnumHand.MAIN_HAND;
			else if (!entity.getHeldItemOffhand().isEmpty()) hand = EnumHand.OFF_HAND;
			if (hand != null) {
				ItemStack stack = entity.getHeldItem(hand).copy();
				entity.setHeldItem(hand, ItemStack.EMPTY);
				if (!(entity instanceof EntityPlayer) && stack.isItemStackDamageable() && stack.getItemDamage() == 0) {
					stack.setItemDamage((int) (stack.getMaxDamage() * (0.5D + 0.5D * Math.random())));
				}
				EntityItem ei = new EntityItem(world, entity.posX, entity.posY, entity.posZ, stack);
				ei.setPickupDelay(200);
				ei.setNoDespawn();
				if (!world.isRemote) world.spawnEntity(ei);
			}
		}
	}

	@Override
	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster) {
		return true;
	}

}
