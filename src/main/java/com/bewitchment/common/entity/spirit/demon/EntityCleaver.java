package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 2/10/2020.
 */
public class EntityCleaver extends ModEntityMob {
	public int attackTimer = 0;

	public EntityCleaver(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/cleaver"));
		setSize(1.0f, 2.5f);
		isImmuneToFire = true;
		setPathPriority(PathNodeType.LAVA, 8);
		setPathPriority(PathNodeType.DANGER_FIRE, 0);
		setPathPriority(PathNodeType.DAMAGE_FIRE, 0);
		experienceValue = 25;
	}

	@Override
	protected int getSkinTypes() {
		return 2;
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		setEquipmentBasedOnDifficulty(difficulty);
		setEnchantmentBasedOnDifficulty(difficulty);
		return super.onInitialSpawn(difficulty, data);
	}

	@Override
	protected boolean canDespawn() {
		return !hasCustomName();
	}

	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.75, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> (e instanceof EntityPlayer || e instanceof EntityFeuerwurm || e instanceof EntityHellhound || e instanceof EntityBafometyr || (!e.isImmuneToFire() && e.getCreatureAttribute() != BewitchmentAPI.DEMON && e.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD)) && !BewitchmentAPI.hasBesmirchedGear(e)));
	}

	public void onEntityUpdate() {
		if (attackTimer > 0) attackTimer--;
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
	public void handleStatusUpdate(byte id) {
		if (id == 4) attackTimer = 20;
		else super.handleStatusUpdate(id);
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.CLEAVER_IDLE;
	}

	@Override
	protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
		if (itemEntity.getItem().getItem() instanceof ItemSword || itemEntity.getItem().getItem() instanceof ItemArmor || itemEntity.getItem().getItem() instanceof ItemShield) {
			super.updateEquipmentIfNeeded(itemEntity);
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModObjects.cleaver_sword));
		this.setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(Items.GOLDEN_HELMET));
		this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
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
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.CLEAVER_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.CLEAVER_DEATH;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getHealth() / this.getMaxHealth() < 0.35 && !(ticksExisted % 200 > 5)) {
			this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 2000, 1));
			world.playSound(null, getPosition(), ModSounds.CLEAVER_IDLE, SoundCategory.HOSTILE, 1, 1);
			attackTimer = 80;
			world.setEntityState(this, (byte) 4);
		}
	}

	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));

		if (flag) {
			if (getAttackTarget() != null) {
				this.applyEnchantments(this, entityIn);
				int i = this.rand.nextInt(100);
				if (entityIn instanceof EntityLivingBase) {
					attackTimer = 20;
					world.setEntityState(this, (byte) 4);
					if (i < 3) {
						((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 500, 3, false, false));
						entityIn.motionZ -= 1.5;
						if (entityIn instanceof EntityPlayer)
							((EntityPlayerMP) entityIn).connection.sendPacket(new SPacketEntityVelocity(entityIn));
					}
				}
			}
		}
		return flag;
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.50);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.50);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
	}
}
