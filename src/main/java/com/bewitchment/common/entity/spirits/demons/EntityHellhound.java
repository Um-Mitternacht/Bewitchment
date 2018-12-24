package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.entity.living.animals.*;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 12/10/2018.
 */
public class EntityHellhound extends EntityMultiSkin {

	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/hellhound");
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityUran.class, DataSerializers.VARINT);

	public EntityHellhound(World worldIn) {
		super(worldIn);
		setSize(1.6F, 1.6F);
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 20;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	protected void updateAITasks() {
		if (this.isWet()) {
			this.attackEntityFrom(DamageSource.DROWN, 1.0F);
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		return (this.world.provider.doesWaterVaporize() || this.world.provider.isNether()) && this.world.checkNoEntityCollision(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.85d);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(3, new EntityAIMate(this, 1d));
		this.tasks.addTask(7, new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
		this.targetTasks.addTask(3, new EntityAITargetNonTamed<>(this, EntityPlayer.class, true, p -> p.getDistanceSq(this) < 1));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityRabbit || e instanceof EntityChicken || e instanceof EntityBlindworm || e instanceof EntityLizard || e instanceof EntityCow || e instanceof EntityParrot || e instanceof EntitySheep || e instanceof EntityPig || e instanceof EntityVillager || e instanceof EntityPlayer || e instanceof EntityRaven || e instanceof EntityOwl || e instanceof EntityNewt || e instanceof EntityToad || e instanceof EntitySnake || e instanceof EntityHorse || e instanceof EntityDonkey || e instanceof EntityMule || e instanceof EntityLlama || e instanceof EntityWolf || e instanceof EntityOcelot || e instanceof EntityPolarBear || e instanceof EntityUran || e.getClass().getName().equals("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat")));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2000, 1, false, false));
				entity.setFire(25);
			}
		}
		return flag;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(getOwner())) {
			super.collideWithEntity(entityIn);
		}
	}

	public boolean isPotionApplicable(PotionEffect potioneffectIn) {
		if (potioneffectIn.getPotion() == MobEffects.WITHER || potioneffectIn.getPotion() == MobEffects.POISON || potioneffectIn.getPotion() == ModPotions.rotting) {
			net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW;
		}
		return super.isPotionApplicable(potioneffectIn);
	}


	@Override
	public int getMaxSpawnedInChunk() {
		return 10;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	public float getBrightness() {
		return 0.3F;
	}

	@Override
	public int getSkinTypes() {
		return 6;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityHellhound(world);
	}
}
