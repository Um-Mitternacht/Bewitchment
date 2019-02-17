package com.bewitchment.common.entity.spirits.ghosts;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 12/29/2018.
 */
public class EntityBlackDog extends EntityMultiSkin implements IAnimatedEntity, IMob {

	@SuppressWarnings("deprecation")
	public static final Animation ANIMATION_BITE = Animation.create(20, 10);
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/black_dog");
	private int animationTick;
	private Animation currentAnimation;

	public EntityBlackDog(World worldIn) {
		super(worldIn);
		setSize(1.8F, 1.8F);
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 35;
		this.moveHelper = new EntityMoveHelper(this);
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	protected void playStepSound(BlockPos pos, Block blockIn) {
		this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.00F, 0.00F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.95d);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.25D);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAIBreakDoor(this));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
		this.targetTasks.addTask(3, new EntityAITargetNonTamed<>(this, EntityPlayer.class, true, p -> p.getDistanceSq(this) < 1));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityPlayer || e instanceof EntityVillager || e instanceof EntityEvoker || e instanceof EntityVindicator || e instanceof EntityIllusionIllager || e instanceof EntitySpellcasterIllager || e instanceof EntityWitch || e instanceof EntityIronGolem));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
		this.applyEntityAI();
	}

	protected void applyEntityAI() {
		this.tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityVillager>(this, EntityVillager.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<EntityIronGolem>(this, EntityIronGolem.class, true));
	}

	@Override
	protected PathNavigate createNavigator(World worldIn) {
		PathNavigateGround pathnavigateground = new PathNavigateGround(this, worldIn);
		pathnavigateground.setBreakDoors(true);
		return pathnavigateground;
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
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			if (entity instanceof EntityLivingBase && this.getAnimation() != ANIMATION_BITE) {
				{
					this.setAnimation(ANIMATION_BITE);
					return entity.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
				}
			}
			return flag;
		}
		return false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource damageSource, float par2) {
		if (!super.attackEntityFrom(damageSource, par2)) {
			return false;
		} else {
			if (damageSource.getTrueSource() != null && !this.equals(damageSource.getTrueSource())) {
				addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 20 * 5, 1, false, false));
				addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 20 * 5, 1, false, false));
			}
		}
		return true;
	}

	public void fall(float distance, float damageMultiplier) {
	}

	public void onLivingUpdate() {
		if (this.isDaytime() && !this.world.isRemote) {
			this.setDead();
		}
		super.onLivingUpdate();
		AnimationHandler.INSTANCE.updateAnimations(this);
	}

	@Override
	public boolean getCanSpawnHere() {
		return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && !this.isDaytime() && super.getCanSpawnHere();
	}

	public boolean isDaytime() {
		long time = this.world.getWorldTime() % 24000L; // Time can go over values of 24000, so divide and take the remainder
		return !(time >= 13000L && time <= 23000L);
	}

	public void onUpdate() {
		super.onUpdate();

		if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
			this.setDead();
		}
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public int getSkinTypes() {
		return 5;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().SPIRIT;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 10;
	}

	@Nullable
	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	public int getAnimationTick() {
		return animationTick;
	}

	@Override
	public void setAnimationTick(int tick) {
		animationTick = tick;
	}

	@Override
	public Animation getAnimation() {
		return currentAnimation;
	}

	@Override
	public void setAnimation(Animation animation) {
		currentAnimation = animation;
	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[]{IAnimatedEntity.NO_ANIMATION, EntityBlackDog.ANIMATION_BITE};
	}
}
