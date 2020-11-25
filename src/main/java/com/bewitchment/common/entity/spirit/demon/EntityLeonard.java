package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.common.entity.util.IPledgeable;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModPotions;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class EntityLeonard extends AbstractGreaterDemon implements IPledgeable {

	private int timer = 0;

	public EntityLeonard(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/leonard"));
		isImmuneToFire = true;
		setSize(1.0f, 3.8f);
		inventoryHandsDropChances[0] = 0;
		experienceValue = 165;
	}

	@Override
	protected boolean isValidLightLevel() {
		return false;
	}

	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("timer", timer);
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		timer = compound.getInteger("timer");
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			if (ExtendedWorld.playerPledgedToDemon(player.world, player, this.getPledgeName())) {
				if (player.experienceLevel >= 2) {
					if (timer == 0) {
						if (player.getHeldItem(hand).getItem() == Items.BOWL) {
							player.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModObjects.stew_of_the_grotesque));
							world.playSound(player, player.getPosition(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.PLAYERS, 1, 1);
							timer = 24000;
						}
					}
				}
			}
		}
		return super.processInteract(player, hand);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return ModSounds.BAFOMETYR_HURT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.BAFOMETYR_DEATH;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		this.setEquipmentBasedOnDifficulty(difficulty);
		return super.onInitialSpawn(difficulty, data);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (timer > 0) timer--;
		if (this.getHealth() < this.getMaxHealth() && !(ticksExisted % 200 > 5)) {
			this.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 1, false, true));
			world.playSound(null, getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.HOSTILE, 6, 1);
			this.swingArm(EnumHand.MAIN_HAND);
		}
		if (this.getHealth() / this.getMaxHealth() < 0.4 && !(ticksExisted % 200 > 5)) {
			this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 0, false, true));
			world.playSound(null, getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.HOSTILE, 6, 1);
			this.swingArm(EnumHand.MAIN_HAND);
		}
		if (this.getHealth() / this.getMaxHealth() < 0.25) {
			if (this.getAttackTarget() != null && !this.getAttackTarget().isPotionActive(ModPotions.mortal_coil)) {
				this.getAttackTarget().addPotionEffect(new PotionEffect(ModPotions.mortal_coil, 6000));
			}
		}
		boolean buffed = ticksExisted % 450 > 5;
		if (!buffed) {
			if (!world.isRemote) {
				((WorldServer) world).spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE, posX, posY, posZ, 128, width, height + 1, width, 0.1);
				world.playSound(null, posX, posY, posZ, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.HOSTILE, 5, 1);
			}
			this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 1));
			this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300, 1));
			this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 1));
			this.swingArm(EnumHand.MAIN_HAND);
			return;
		}
		if (getAttackTarget() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) getAttackTarget();
			boolean launchFireball = ticksExisted % 100 > 5;
			if (!launchFireball && getDistance(player) > 4) {
				double d0 = getDistanceSq(player);
				double d1 = player.posX - this.posX;
				double d2 = player.getEntityBoundingBox().minY + (double) (player.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
				double d3 = player.posZ - this.posZ;
				float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
				world.playEvent(null, 1018, new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ), 0);
				EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, this, d1 + this.getRNG().nextGaussian() * (double) f, d2, d3 + this.getRNG().nextGaussian() * (double) f);
				entitysmallfireball.posY = posY + (double) (height / 2.0F) + 0.5D;
				world.spawnEntity(entitysmallfireball);
				this.swingArm(EnumHand.MAIN_HAND);
			}
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			player.addPotionEffect(new PotionEffect(ModPotions.magic_weakness, 60, 0));
		}
		return super.attackEntityAsMob(entityIn);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(13.616);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(6.16);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.9D);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potioneffectIn) {
		return !potioneffectIn.getPotion().isBadEffect();
	}

	@Override
	public void onDeath(DamageSource cause) {
		List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(posX - 16, posY - 16, posZ - 16, posX + 16, posY + 16, posZ + 16));
		for (EntityPlayer player : players) player.removeActivePotionEffect(ModPotions.mortal_coil);
		super.onDeath(cause);
	}

	public void setCustomNameTag(@NotNull String name) {
		super.setCustomNameTag(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> e instanceof EntityVillager || e instanceof AbstractIllager || e instanceof EntityWitch || e instanceof EntityIronGolem));
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
	protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
		this.dropItem(ModObjects.leonards_wand, 1);
		this.dropItem(ModObjects.demon_heart, 1);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	protected void updateAITasks() {
		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModObjects.leonards_wand));
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	public String getPledgeName() {
		return "leonard";
	}
}