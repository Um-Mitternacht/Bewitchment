package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.registry.ModPotions;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemAxe;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDruden extends ModEntityMob {
	
	public int attackTimer = 0;
	
	public EntityDruden(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/druden"));
		setSize(1.425f, 3.7f);
		isImmuneToFire = false;
		experienceValue = 45;
	}
	
	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotion() != MobEffects.POISON && effect.getPotion() != MobEffects.WITHER && super.isPotionApplicable(effect);
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}
	
	@Override
	protected void despawnEntity() {
		if (!hasCustomName()) {
			super.despawnEntity();
		}
	}
	
	@Override
	protected boolean canDespawn() {
		return !hasCustomName();
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (attackTimer > 0) attackTimer--;
		if (this.getHealth() < this.getMaxHealth() && !(ticksExisted % 200 > 5)) {
			this.heal(2);
			this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 80, 2));
			world.playSound(null, getPosition(), SoundEvents.BLOCK_GRASS_PLACE, SoundCategory.HOSTILE, 6, 1);
		}
		if (isBurning()) {
			attackEntityFrom(DamageSource.ON_FIRE, 6.66f);
			if (hurtTime == 1) {
				for (int i = 0; i < 20; i++)
					world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + (rand.nextDouble() - 0.5) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5) * width, 0, 0, 0);
				world.playSound(null, getPosition(), SoundEvents.ENTITY_GHAST_HURT, SoundCategory.HOSTILE, 1.3F, 1);
			}
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return super.attackEntityFrom(source, amount * (source.getImmediateSource() instanceof EntityLivingBase && ((EntityLivingBase) source.getImmediateSource()).getHeldItemMainhand().getItem() instanceof ItemAxe ? 3 : 1));
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		boolean flag = super.attackEntityAsMob(entity);
		if (flag) {
			attackTimer = 10;
			world.setEntityState(this, (byte) 4);
			world.playSound(null, getPosition(), SoundEvents.ENTITY_GHAST_SCREAM, SoundCategory.HOSTILE, 1.3F, 1);
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(ModPotions.fear, 500, 0, false, false));
			}
		}
		return flag;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.14);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.00);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8);
	}
	
	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(SoundEvents.ENTITY_STRAY_STEP, 0.0F, 0.0F);
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 10, false, false, p -> (p.getDistanceSq(this) < 2) && !BewitchmentAPI.hasBesmirchedGear(p)));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> (e instanceof EntityVillager || e instanceof EntityEnderman || e instanceof EntityGolem || (!e.isImmuneToFire() && e.getCreatureAttribute() != BewitchmentAPI.DEMON && e.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD)) && !BewitchmentAPI.hasBesmirchedGear(e)));
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
		}
		else {
			this.setAir(300);
		}
	}
	
	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 4) attackTimer = 10;
		else super.handleStatusUpdate(id);
	}
	
	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
}
