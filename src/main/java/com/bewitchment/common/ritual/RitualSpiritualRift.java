package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.message.SpawnParticle;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.entity.spirit.demon.EntityCambion;
import com.bewitchment.common.entity.spirit.demon.EntityDruden;
import com.bewitchment.common.entity.spirit.demon.EntityShadowPerson;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
import com.bewitchment.registry.ModObjects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;

public class RitualSpiritualRift extends Ritual {
	public RitualSpiritualRift() {
		super(new ResourceLocation(Bewitchment.MODID, "spiritual_rift"), Arrays.asList(Util.get("cropWormwood"), Util.get("cropWormwood"), Util.get(ModObjects.ectoplasm), Util.get(ModObjects.ectoplasm), Util.get(ModObjects.spectral_dust), Util.get("enderpearl")), null, null, 15, 1250, 66, BlockGlyph.ENDER, BlockGlyph.ENDER, -1);
	}
	
	@Override
	public String getPreconditionMessage() {
		return "ritual.precondition.no_day";
	}
	
	@Override
	public boolean isValid(World world, BlockPos pos, EntityPlayer caster, ItemStackHandler inventory) {
		return !world.isDaytime();
	}
	
	@Override
	public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		super.onFinished(world, altarPos, effectivePos, caster, inventory);
		if (!world.isRemote) {
			for (int i = 0; i < world.rand.nextInt(12) + 2; i++) {
				EntityLiving entity;
				int rand = world.rand.nextInt(12);
				if (rand == 0) entity = new EntityVex(world);
				else if (rand == 1) entity = new EntityBlackDog(world);
				else if (rand == 2) entity = new EntityGhost(world);
				else if (rand == 3) entity = new EntityDruden(world);
				else if (rand == 4) entity = new EntitySkeleton(world);
				else if (rand == 5) entity = new EntityHusk(world);
				else if (rand == 6) entity = new EntityStray(world);
				else if (rand == 7) entity = new EntityShadowPerson(world);
				else if (rand == 8) entity = new EntityEnderman(world);
				else if (rand == 9) entity = new EntityEndermite(world);
				else if (rand == 10) entity = new EntityCambion(world);
				else entity = new EntityZombie(world);
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
					for (EntityPlayerMP player : world.getEntitiesWithinAABB(EntityPlayerMP.class, entity.getEntityBoundingBox().grow(50))) CriteriaTriggers.SUMMONED_ENTITY.trigger(player, entity);
					world.spawnEntity(entity);
				}
			}
		}
	}
	
	@Override
	public void onUpdate(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
		for (int i = 0; i < 5; i++) {
			double cx = effectivePos.getX() + 0.5, cy = effectivePos.getY() + 0.5, cz = effectivePos.getZ() + 0.5;
			double sx = cx + world.rand.nextGaussian() * 0.5, sy = cy + world.rand.nextGaussian() * 0.5, sz = cz + world.rand.nextGaussian() * 0.5;
			Bewitchment.network.sendToDimension(new SpawnParticle(EnumParticleTypes.CRIT_MAGIC, sx, sy, sz, 0.6 * (sx - cx), 0.6 * (sy - cy), 0.6 * (sz - cz)), world.provider.getDimension());
		}
	}
}