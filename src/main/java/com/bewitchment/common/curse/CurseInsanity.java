package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CurseInsanity extends Curse {
	public CurseInsanity() {
		super(new ResourceLocation(Bewitchment.MODID, "insanity"), Arrays.asList(Util.get(Blocks.RED_MUSHROOM), Util.get(ModObjects.belladonna), Util.get(ModObjects.elderberries), Util.get(ModObjects.oil_of_vitriol), Util.get(ModObjects.toe_of_frog), Util.get(ModObjects.spanish_moss), Util.get(ModObjects.taglock)), false, false, CurseCondition.EXIST, 0.01);
	}

	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		EntityLiving fake = getRandomFakeMob(target.getRNG(), target);
		target.world.spawnEntity(fake);
		return true;
	}

	private EntityLiving getRandomFakeMob(Random rand, EntityPlayer target) {
		World world = target.world;
		List<Biome.SpawnListEntry> spawns = world.getBiome(target.getPosition()).getSpawnableList(EnumCreatureType.MONSTER);
		EntityLiving temp;
		if (spawns.size() <= 0) {
			temp = new EntityZombie(world);
		} else {
			try {
				temp = spawns.get(rand.nextInt(spawns.size())).entityClass.getConstructor(World.class).newInstance(world);
			} catch (Exception e) {
				temp = new EntityZombie(world);
			}
		}
		int x = (int) target.posX + rand.nextInt(6) - 5;
		int z = (int) target.posZ + rand.nextInt(6) - 5;
		temp.setPosition(x, world.getHeight(x, z), z);
		temp.setEntityInvulnerable(true);
		List<EntityAIBase> toRemove = new ArrayList<>();
		for (EntityAITasks.EntityAITaskEntry task : temp.tasks.taskEntries) {
			toRemove.add(task.action);
		}
		for (EntityAIBase remove : toRemove) {
			temp.tasks.removeTask(remove);
		}
		temp.tasks.addTask(1, new EntityAIFakeAttack(temp, 1.0, false));
		temp.setAttackTarget(target);
		return temp;
	}

	static class EntityAIFakeAttack extends EntityAIBase {
		private final boolean canPenalize = false;
		World world;
		EntityLiving attacker;
		int attackTick;
		double speedTowardsTarget;
		boolean longMemory;
		Path path;
		int survivalTime;
		private int delayCounter;
		private double targetX;
		private double targetY;
		private double targetZ;
		private int failedPathFindingPenalty = 0;

		EntityAIFakeAttack(EntityLiving creature, double speedIn, boolean useLongMemory) {
			this.attacker = creature;
			this.world = creature.world;
			this.speedTowardsTarget = speedIn;
			this.longMemory = useLongMemory;
			this.survivalTime = creature.getRNG().nextInt(20) + 30;
			this.setMutexBits(3);
		}

		public boolean shouldExecute() {
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
			if (entitylivingbase == null) {
				return false;
			} else if (!entitylivingbase.isEntityAlive()) {
				return false;
			} else if (this.canPenalize) {
				if (--this.delayCounter <= 0) {
					this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
					this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
					return this.path != null;
				} else {
					return true;
				}
			} else {
				this.path = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
				if (this.path != null) {
					return true;
				} else {
					return this.getAttackReachSqr(entitylivingbase) >= this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
				}
			}
		}

		public boolean shouldContinueExecuting() {
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
			if (entitylivingbase == null) {
				return false;
			} else if (!entitylivingbase.isEntityAlive()) {
				return false;
			} else if (!this.longMemory) {
				return !this.attacker.getNavigator().noPath();
			} else {
				return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer) entitylivingbase).isSpectator() && !((EntityPlayer) entitylivingbase).isCreative();
			}
		}

		public void startExecuting() {
			this.attacker.getNavigator().setPath(this.path, this.speedTowardsTarget);
			this.delayCounter = 0;
		}

		public void resetTask() {
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
			if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer) entitylivingbase).isSpectator() || ((EntityPlayer) entitylivingbase).isCreative())) {
				this.attacker.setAttackTarget(null);
			}
			this.attacker.getNavigator().clearPath();
		}

		public void updateTask() {
			EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
			this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
			double d0 = this.attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
			--this.delayCounter;
			if ((this.longMemory || this.attacker.getEntitySenses().canSee(entitylivingbase)) && this.delayCounter <= 0 && (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D || entitylivingbase.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D || this.attacker.getRNG().nextFloat() < 0.05F)) {
				this.targetX = entitylivingbase.posX;
				this.targetY = entitylivingbase.getEntityBoundingBox().minY;
				this.targetZ = entitylivingbase.posZ;
				this.delayCounter = 4 + this.attacker.getRNG().nextInt(7);
				if (this.canPenalize) {
					this.delayCounter += this.failedPathFindingPenalty;
					if (this.attacker.getNavigator().getPath() != null) {
						PathPoint finalPathPoint = this.attacker.getNavigator().getPath().getFinalPathPoint();
						if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1.0D) {
							this.failedPathFindingPenalty = 0;
						} else {
							this.failedPathFindingPenalty += 10;
						}
					} else {
						this.failedPathFindingPenalty += 10;
					}
				}
				if (d0 > 1024.0D) {
					this.delayCounter += 10;
				} else if (d0 > 256.0D) {
					this.delayCounter += 5;
				}
				if (!this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, this.speedTowardsTarget)) {
					this.delayCounter += 15;
				}
			}
			this.attackTick = Math.max(this.attackTick - 1, 0);
			this.checkAndPerformAttack(entitylivingbase, d0);
		}

		void checkAndPerformAttack(EntityLivingBase enemy, double distToEnemySqr) {
			double d0 = this.getAttackReachSqr(enemy);
			if (distToEnemySqr <= 5) {
				this.survivalTime--;
				if (this.survivalTime <= 0) {
					((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, false, attacker.posX, attacker.posY, attacker.posZ, 100, 1, 2, 1, 0.05, 0);
					attacker.setDead();
				}
			}
			if (distToEnemySqr <= d0 && this.attackTick <= 0) {
				this.attackTick = 20;
				this.attacker.swingArm(EnumHand.MAIN_HAND);
			}
		}

		double getAttackReachSqr(EntityLivingBase attackTarget) {
			return (this.attacker.width * 2.0F * this.attacker.width * 2.0F + attackTarget.width);
		}
	}
}
