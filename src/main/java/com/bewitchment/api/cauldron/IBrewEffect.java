package com.bewitchment.api.cauldron;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public interface IBrewEffect {

	/**
	 * Called when a splash brew hits the ground
	 *
	 * @param world     The world
	 * @param pos       The position on which the splash brew impacted
	 * @param modifiers The list of modifiers carried by the splash brew entity
	 * @param thrower   The (optional) thrower of the potion
	 */
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, @Nullable EntityLivingBase thrower);

	/**
	 * Called when a brew effect gets applied to an entity
	 *
	 * @param entity    The entity this effect is being applied to
	 * @param effect    The PotionEffect that is about to be applied
	 * @param modifiers The list of modifiers carried by the brew entity (splash, drinkable, arrow or lingering)
	 * @param thrower   the (optional) thrower of the potion
	 * @return The potion effect to be applied to the entity. If no changes are required, just return the one passed as a parameter
	 */
	public PotionEffect onApplyToEntity(EntityLivingBase entity, PotionEffect effect, IBrewModifierList modifiers, @Nullable Entity thrower);

	public boolean hasInWorldEffect();

	public int getDefaultDuration();

	public int getArrowDuration();

	public int getLingeringDuration();
}
