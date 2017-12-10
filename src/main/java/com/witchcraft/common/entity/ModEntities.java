package com.witchcraft.common.entity;

import com.witchcraft.common.Witchcraft;
import com.witchcraft.common.item.ModItems;
import com.witchcraft.common.lib.LibMod;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public final class ModEntities {

	private ModEntities() {
	}

	public static void init() {
		int id = 0;

		EntityRegistry.registerModEntity(getResource("brew_throwable"), EntityBrew.class, "brew_throwable", id++, Witchcraft.instance, 64, 1, true);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.brew_phial_splash, new IBehaviorDispenseItem() {
			@Override
			public ItemStack dispense(IBlockSource source, final ItemStack stack) {
				return (new BehaviorProjectileDispense() {

					@Override
					protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
						return new EntityBrew(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy(), EntityBrew.BrewDispersion.SPLASH);
					}

					@Override
					protected float getProjectileInaccuracy() {
						return super.getProjectileInaccuracy() * 0.5F;
					}

					@Override
					protected float getProjectileVelocity() {
						return super.getProjectileVelocity() * 1.25F;
					}
				}).dispense(source, stack);
			}
		});
		EntityRegistry.registerModEntity(getResource("brew_linger"), EntityBrewLinger.class, "brew_linger", id, Witchcraft.instance, 64, 1, false);
		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(ModItems.brew_phial_linger, new IBehaviorDispenseItem() {
			@Override
			public ItemStack dispense(IBlockSource source, final ItemStack stack) {
				return (new BehaviorProjectileDispense() {

					@Override
					protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
						return new EntityBrew(worldIn, position.getX(), position.getY(), position.getZ(), stack.copy(), EntityBrew.BrewDispersion.LINGER);
					}

					@Override
					protected float getProjectileInaccuracy() {
						return super.getProjectileInaccuracy() * 0.5F;
					}

					@Override
					protected float getProjectileVelocity() {
						return super.getProjectileVelocity() * 1.25F;
					}
				}).dispense(source, stack);
			}
		});
	}

	private static ResourceLocation getResource(String name) {
		return new ResourceLocation(LibMod.MOD_ID, name);
	}
}
