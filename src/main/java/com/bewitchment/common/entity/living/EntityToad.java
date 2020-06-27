package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityTameable;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

@SuppressWarnings({"ConstantConditions"})
public class EntityToad extends ModEntityTameable {

	private static final DataParameter<Integer> ANIMATION_TIME = EntityDataManager.createKey(EntityToad.class, DataSerializers.VARINT);
	private static final DataParameter<Float> ANIMATION_HEIGHT = EntityDataManager.createKey(EntityToad.class, DataSerializers.FLOAT);

	public EntityToad(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/toad"), Items.SPIDER_EYE);
		setSize(1, 0.3f);
		experienceValue = 5;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.TOAD_IDLE;
	}

	@Override
	protected void despawnEntity() {
		if (canDespawn()) {
			super.despawnEntity();
		}
	}

	@Override
	public EntityAgeable createChild(EntityAgeable other) {
		return new EntityToad(this.world);
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (ticksExisted % 20 == 0 && isWet()) heal(1);
	}

	@Override
	protected boolean canDespawn() {
		return !(isTamed() || hasCustomName());
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.FERMENTED_SPIDER_EYE;
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, aiSit);
		tasks.addTask(2, new EntityAIMate(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
		tasks.addTask(2, new EntityAIAttackMelee(this, getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(), false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAIFollowParent(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIFollowOwner(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(), 10, 2));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(0, new EntityAIOwnerHurtByTarget(this));
		targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
		targetTasks.addTask(2, new EntityAITargetNonTamed<>(this, EntityPlayer.class, true, p -> p.getDistanceSq(this) < 1));
		targetTasks.addTask(3, new EntityAITargetNonTamed<>(this, EntityLivingBase.class, false, e -> e instanceof EntityEndermite || e instanceof EntitySilverfish));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.5);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(ANIMATION_TIME, 0);
		this.dataManager.register(ANIMATION_HEIGHT, 0f);
	}

	@Override
	protected int getSkinTypes() {
		return 4;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect effect) {
		return effect.getPotion() != MobEffects.SLOWNESS && super.isPotionApplicable(effect);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.TOAD_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.TOAD_DEATH;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		if (entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue())) {
			applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase)
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1, false, false));
		}
		return super.attackEntityAsMob(entity);
	}

	public float postIncAnimation() {
		this.dataManager.set(ANIMATION_TIME, this.dataManager.get(ANIMATION_TIME) + 1);
		return (float) this.dataManager.get(ANIMATION_TIME);
	}

	public float getAnimationTime() {
		return (float) this.dataManager.get(ANIMATION_TIME);
	}

	public void resetAnimationTime() {
		this.dataManager.set(ANIMATION_TIME, 0);
	}

	public float getAnimationHeight() {
		return this.dataManager.get(ANIMATION_HEIGHT);
	}

	public float setAnimationHeight(float in) {
		this.dataManager.set(ANIMATION_HEIGHT, in);
		return in;
	}

	public void resetAnimationHeight() {
		this.dataManager.set(ANIMATION_HEIGHT, 0.0F);
	}
}