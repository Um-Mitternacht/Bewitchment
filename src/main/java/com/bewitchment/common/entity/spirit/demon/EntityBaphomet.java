package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.capability.extendedworld.ExtendedWorld;
import com.bewitchment.api.registry.Contract;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.common.entity.util.IPledgeable;
import com.bewitchment.registry.ModEntities;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModSounds;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class EntityBaphomet extends AbstractGreaterDemon implements IPledgeable {
	private int mobSpawnTicks = 0;
	private int pullCooldown = 0;

	public EntityBaphomet(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/baphomet"));
		isImmuneToFire = true;
		setSize(1.0f, 3.6f);
		inventoryHandsDropChances[0] = 0;
		experienceValue = 185;
	}

	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	protected boolean isValidLightLevel() {
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		super.writeEntityToNBT(tag);
		tag.setInteger("mobSpawnTicks", mobSpawnTicks);
		tag.setInteger("pullCooldown", pullCooldown);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.mobSpawnTicks = compound.getInteger("mobSpawnTicks");
		this.pullCooldown = compound.getInteger("pullCooldown");
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		this.setEquipmentBasedOnDifficulty(difficulty);
		return super.onInitialSpawn(difficulty, data);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	public void setCustomNameTag(String name) {
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
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
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
		this.dropItem(ModObjects.caduceus, 1);
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
		this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(ModObjects.caduceus));
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (hand == EnumHand.MAIN_HAND) {
			if (ExtendedWorld.playerPledgedToDemon(player.world, player, this.getPledgeName())) {
				if (player.experienceLevel >= 6) {
					ExtendedPlayer ep = player.getCapability(ExtendedPlayer.CAPABILITY, null);
					List<Curse> contracts = GameRegistry.findRegistry(Curse.class).getValuesCollection().stream().filter(Curse::isPositive).filter(c -> c instanceof Contract).collect(Collectors.toList());
					if (!ep.getCurses().stream().anyMatch(contracts::contains)) {
						player.addExperienceLevel(-6);
						if (!world.isRemote) {
							Contract contract = (Contract) contracts.get(player.getRNG().nextInt(contracts.size()));
							if (ep != null) ep.addCurse(contract, 7);
							player.sendStatusMessage(new TextComponentTranslation("baphomet.getcontract", I18n.format(contract.getRegistryName().toString().replace(":", "."))), true);
						}
						world.playSound(player, player.getPosition(), SoundEvents.ENTITY_ILLAGER_CAST_SPELL, SoundCategory.PLAYERS, 5, 1);
						return true;
					} else player.sendStatusMessage(new TextComponentTranslation("baphomet.alreadyhascontract"), true);
				} else player.sendStatusMessage(new TextComponentTranslation("baphomet.lowlevel"), true);
			} else player.sendStatusMessage(new TextComponentTranslation("baphomet.notpledged"), true);
		}
		return super.processInteract(player, hand);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (getAttackTarget() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) getAttackTarget();
			boolean buffed = ticksExisted % 600 > 5;
			if (!buffed) {
				if (!world.isRemote) {
					((WorldServer) world).spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, 100, width, height + 1, width, 0.1);
					world.playSound(null, posX, posY, posZ, SoundEvents.ITEM_FIRECHARGE_USE, SoundCategory.HOSTILE, 5, 1);
				}
				this.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 300, 1));
				this.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 300, 1));
				this.addPotionEffect(new PotionEffect(MobEffects.SPEED, 300, 1));
				return;
			}
			boolean middleHealth = getHealth() / getMaxHealth() < 0.5;
			if (middleHealth && mobSpawnTicks <= 0) {
				int amount = rand.nextInt(3) + 1;
				for (int i = 0; i < amount; i++) {
					EntityFeuerwurm temp = (EntityFeuerwurm) ModEntities.feuerwurm.newInstance(world);
					temp.setAttackTarget(player);
					temp.getDataManager().set(SKIN, rand.nextInt(temp.getSkinTypes()));
					temp.setPosition(posX + rand.nextGaussian() * 0.8, posY + 0.5, posZ + rand.nextGaussian() * 0.8);
					temp.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 2));
					world.spawnEntity(temp);
					if (!world.isRemote)
						((WorldServer) world).spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, 16, temp.width, temp.height + 1, temp.width, 0.1);
				}
				mobSpawnTicks = 180;
				this.swingArm(EnumHand.MAIN_HAND);
			} else if (mobSpawnTicks > 0) {
				mobSpawnTicks--;
			}
			if (getDistance(player) > 10 && pullCooldown <= 0) {
				player.motionX += (posX - getAttackTarget().posX) / 10;
				player.motionZ += (posZ - getAttackTarget().posZ) / 10;
				if (!world.isRemote) ((EntityPlayerMP) player).connection.sendPacket(new SPacketEntityVelocity(player));
				pullCooldown = 40;
			} else if (pullCooldown > 0) {
				pullCooldown--;
			}
			boolean lowHealth = getHealth() / getMaxHealth() < 0.3;
			if (lowHealth && mobSpawnTicks <= 0) {
				int amount = rand.nextInt(3) + 1;
				for (int i = 0; i < amount; i++) {
					EntityBafometyr temp = (EntityBafometyr) ModEntities.bafometyr.newInstance(world);
					temp.setAttackTarget(player);
					temp.getDataManager().set(SKIN, rand.nextInt(temp.getSkinTypes()));
					temp.setPosition(posX + rand.nextGaussian() * 0.8, posY + 0.5, posZ + rand.nextGaussian() * 0.8);
					temp.addPotionEffect(new PotionEffect(MobEffects.SPEED, 600, 2));
					world.spawnEntity(temp);
					if (!world.isRemote)
						((WorldServer) world).spawnParticle(EnumParticleTypes.FLAME, posX, posY, posZ, 16, temp.width, temp.height + 1, temp.width, 0.1);
				}
				mobSpawnTicks = 180;
				this.swingArm(EnumHand.MAIN_HAND);
			}
			boolean launchFireball = ticksExisted % 80 > 5;
			if (!launchFireball && getDistance(player) > 2) {
				double d0 = getDistanceSq(player);
				double d1 = player.posX - this.posX;
				double d2 = player.getEntityBoundingBox().minY + (double) (player.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
				double d3 = player.posZ - this.posZ;
				float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
				world.playEvent(null, 1018, new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ), 0);
				EntitySmallFireball entitysmallfireball = new EntitySmallFireball(world, this, d1 + this.getRNG().nextGaussian() * (double) f, d2, d3 + this.getRNG().nextGaussian() * (double) f);
				entitysmallfireball.posY = posY + (double) (height / 2.0F) + 0.5D;
				world.spawnEntity(entitysmallfireball);
			}
		}
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
	public boolean attackEntityAsMob(Entity entityIn) {
		if (entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 0));
			this.heal(2);
		}
		return super.attackEntityAsMob(entityIn);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(666);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.70);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(13.616);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(6.66);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.9D);
	}

	@Override
	public String getPledgeName() {
		return "baphomet";
	}
}
