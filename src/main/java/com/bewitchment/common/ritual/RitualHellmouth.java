package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.entity.spirit.demon.EntityCambion;
import com.bewitchment.common.entity.spirit.demon.EntityFeuerwurm;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;

public class RitualHellmouth extends Ritual {
	public RitualHellmouth() {
		super(new ResourceLocation(Bewitchment.MODID, "hellmouth"), Arrays.asList(Util.get(ModObjects.athame), Util.get("netherrack"), Util.get(Blocks.MAGMA), Util.get("cropHellebore"), Util.get(ModObjects.snake_venom), Util.get(new ItemStack(Items.COAL, 1, 1))), null, Collections.singletonList(new ItemStack(ModObjects.athame, 1, 0)), 15, 1250, 45, BlockGlyph.NETHER, BlockGlyph.NETHER, -1);
	}

	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.no_rain";
	}

	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return !world.isRaining();
	}

	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) {
			for (int i = 0; i < world.rand.nextInt(5) + 2; i++) {
				EntityLiving entity;
				int rand = world.rand.nextInt(5);
				if (rand == 0) entity = new EntityFeuerwurm(world);
				else if (rand == 1) entity = new EntityFeuerwurm(world);
				else if (rand == 2) entity = new EntityBlaze(world);
				else if (rand == 3) entity = new EntityCambion(world);
				else entity = new EntityBlaze(world);
				entity.onInitialSpawn(world.getDifficultyForLocation(effectivePos), null);
				boolean valid = false;
				for (int j = 0; j < 16; j++) {
					if (entity.attemptTeleport(effectivePos.getX() + world.rand.nextInt(12) - 6, effectivePos.getY(), effectivePos.getZ() + world.rand.nextInt(12) - 6)) {
						entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextInt(360), 0);
						valid = true;
						break;
					}
				}
				if (valid) {
					for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50)))
						CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
					world.spawnEntity(entity);
				}
			}
		}
	}

	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		for (int i = 0; i < 10; i++) {
			double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.FLAME, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
	}
}