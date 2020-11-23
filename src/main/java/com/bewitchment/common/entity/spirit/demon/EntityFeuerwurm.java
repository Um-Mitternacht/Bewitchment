package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class EntityFeuerwurm extends ModEntityMob {

	public EntityFeuerwurm(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/feuerwurm"));
		setSize(1.45f, 0.85f);
		isImmuneToFire = true;
		setPathPriority(PathNodeType.WATER, -1);
		setPathPriority(PathNodeType.LAVA, 8);
		setPathPriority(PathNodeType.DANGER_FIRE, 0);
		setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
		experienceValue = 20;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotion() != MobEffects.POISON && super.isPotionApplicable(effect);
	}

	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	public boolean shouldRenderInPass(int pass) {
		if (getDataManager().get(ModEntityMob.SPECTRAL)) {
			return pass == 1;
		}
		return pass == 0;
	}

	@Override
	public int getSkinTypes() {
		return 9;
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		super.readEntityFromNBT(tag);
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

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);
		if (flag) {
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 0, false, false));
			}
		}
		return flag;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_BLAZE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_ENDERDRAGON_GROWL;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_BLAZE_DEATH;
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL || (world.provider.doesWaterVaporize() || world.provider.isNether()) && !world.containsAnyLiquid(getEntityBoundingBox()) && super.getCanSpawnHere();
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.50);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0.25);
	}

	@Override
	protected boolean canDropLoot() {
		return !getDataManager().get(SPECTRAL);
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 10, false, false, p -> (p.getDistanceSq(this) < 2) && (!BewitchmentAPI.hasBesmirchedGear(p))));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> (e instanceof EntityAnimal || e instanceof EntityHellhound || (!e.isImmuneToFire() && e.getCreatureAttribute() != BewitchmentAPI.DEMON && e.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD)) && !BewitchmentAPI.hasBesmirchedGear(e)));
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

	@Override
	protected boolean canDespawn() {
		return !hasCustomName();
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}
}
