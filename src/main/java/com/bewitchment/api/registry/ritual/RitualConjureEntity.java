package com.bewitchment.api.registry.ritual;

import com.bewitchment.api.registry.Ritual;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public abstract class RitualConjureEntity<T extends EntityLiving> extends Ritual {

	protected RitualConjureEntity(ResourceLocation name, List<Ingredient> input, Predicate<EntityLivingBase> sacrificePredicate, List<ItemStack> output, boolean canBePerformedRemotely, int time, int startingPower, int runningPower, int small, int medium, int big) {
		super(name, input, sacrificePredicate, output, canBePerformedRemotely, time, startingPower, runningPower, small, medium, big);
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		conjureEntity(world, effectivePos, createEntity(world));
	}

	protected void conjureEntity(@NotNull World world, @NotNull BlockPos pos, @NotNull T entity) {
		entity.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), world.rand.nextInt(360), 0);
		entity.onInitialSpawn(world.getDifficultyForLocation(pos), null);

		for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50)))
			CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);

		world.spawnEntity(entity);
	}

	protected abstract T createEntity(World world);
}
