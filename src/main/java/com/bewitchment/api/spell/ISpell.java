package com.bewitchment.api.spell;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public interface ISpell extends IForgeRegistryEntry<ISpell> {

	public String getName();

	public int getColor();

	public int getCost();

	public EnumSpellType getType();

	public void performEffect(RayTraceResult rtrace, EntityLivingBase caster, World world);

	public boolean canBeUsed(World world, BlockPos pos, EntityLivingBase caster);

	public static enum EnumSpellType {
		INSTANT, PROJECTILE_BLOCK, PROJECTILE_ENTITY, PROJECTILE_ALL
	}
}
