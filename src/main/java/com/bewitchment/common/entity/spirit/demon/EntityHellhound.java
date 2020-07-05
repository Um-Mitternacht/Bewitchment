package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class EntityHellhound extends ModEntityMob {
	public EntityHellhound(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/hellhound"));
		setSize(1.0f, 1.25f);
		isImmuneToFire = true;
		setPathPriority(PathNodeType.WATER, -1);
		setPathPriority(PathNodeType.LAVA, 8);
		setPathPriority(PathNodeType.DANGER_FIRE, 0);
		setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
		experienceValue = 20;
	}

	@Override
	protected int getSkinTypes() {
		return 4;
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotion() != MobEffects.POISON && effect.getPotion() != MobEffects.WITHER && super.isPotionApplicable(effect);
	}

	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (isWet()) {
			attackEntityFrom(DamageSource.MAGIC, 2.5f);
			if (hurtTime == 1) {
				for (int i = 0; i < 20; i++)
					world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0, 0, 0);
				world.playSound(null, getPosition(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.HOSTILE, 1, 1);
			} else if (isInLava()) {
				if (ticksExisted % 20 == 0 && isInLava()) heal(2);
			}
		}
	}

	@Nullable
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_WOLF_HURT;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_WOLF_DEATH;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);
		if (flag) {
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1, false, false));
				entity.setFire(3);
			}
		}
		return flag;
	}

	@Override
	public boolean getCanSpawnHere() {
		return (world.provider.doesWaterVaporize() || world.provider.isNether()) && !world.containsAnyLiquid(getEntityBoundingBox()) && super.getCanSpawnHere();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.25);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(1.00);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.95);
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> (e instanceof EntityPlayer || e instanceof EntityFeuerwurm || e instanceof EntitySheep || e instanceof EntityCow || e instanceof EntityChicken || e instanceof EntityLlama || e instanceof EntityPig || e instanceof EntityRabbit || e instanceof AbstractHorse || (!e.isImmuneToFire() && e.getCreatureAttribute() != BewitchmentAPI.DEMON && e.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD)) && !BewitchmentAPI.hasBesmirchedGear(e)));
	}

	public void onEntityUpdate() {
		int i = this.getAir();
		super.onEntityUpdate();

		if (this.isEntityAlive() && !this.isInWater()) {
			--i;
			this.setAir(i);

			if (this.getAir() == -20) {
				this.setAir(300);
			}
		} else {
			this.setAir(300);
		}
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_WOLF_GROWL;
	}

	@Override
	protected boolean canDespawn() {
		return !hasCustomName();
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}
}
