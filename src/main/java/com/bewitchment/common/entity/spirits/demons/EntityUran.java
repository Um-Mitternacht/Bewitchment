package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkinMonster;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 10/2/2018.
 */

public class EntityUran extends EntityMultiSkinMonster implements IMob, IAnimatedEntity {


	//Todo: Rewrite code, and implement weaknesses to water. Also implement a special potion effect that upon killing a target, spawns more uranids.
	@SuppressWarnings("deprecation")
	public static final Animation ANIMATION_BITE = Animation.create(20, 10);
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/snake");
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityUran.class, DataSerializers.VARINT);
	private static final int TIME_BETWEEN_MILK = 6660;
	private int timerRef = 0;
	private int milkCooldown = 0;
	private int animationTick;
	private Animation currentAnimation;

	private int milkTimer;

	public EntityUran(World world) {
		super(world);
		setSize(1.6F, 1.6F); //Todo: Figure out how to change the size of this properly
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 20;
		this.moveHelper = new EntityMoveHelper(this);
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (!world.isRemote && source == DamageSource.DROWN && this.hurtTime == 1) {
			for (int i = 0; i < 20; i++)
				((WorldServer) world).spawnParticle(EnumParticleTypes.SMOKE_NORMAL, false, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 1, (this.rand.nextDouble() - 0.5D) * 2, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2, 0);
			this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1, 1);
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean getCanSpawnHere() {
		return (this.world.provider.doesWaterVaporize() || this.world.provider.isNether()) && this.world.checkNoEntityCollision(this.getEntityBoundingBox()) && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
	}

	public void onUpdate() {
		super.onUpdate();

		if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
			this.setDead();
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!world.isRemote && (getAttackTarget() == null || getAttackTarget().isDead || getRevengeTarget() == null || getRevengeTarget().isDead)) {
			ItemStack stack = player.getHeldItem(hand);
			if (stack.getItem() == ModItems.glass_jar) {
				if (milkTimer == 0 && getRNG().nextBoolean()) {
					world.playSound(null, getPosition(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.NEUTRAL, 1, 1);
					Util.giveAndConsumeItem(player, hand, new ItemStack(ModItems.uranid_venom));
					milkTimer = 6660;
					return true;
				} else {
					setAttackTarget(player);
					setRevengeTarget(player);
				}
			}
		}
		return super.processInteract(player, hand);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(5, new EntityAIWander(this, 0.5D));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, 10, false, false, p -> p.getDistanceSq(this) < 2));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> e instanceof EntityAnimal || (!e.isImmuneToFire() && e.getCreatureAttribute() != BewitchmentAPI.getAPI().DEMON && e.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD)));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.5D, false));
	}

	public boolean isPotionApplicable(PotionEffect potioneffectIn) {
		if (potioneffectIn.getPotion() == MobEffects.POISON) {
			net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW;
		}
		return super.isPotionApplicable(potioneffectIn);
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}

	//Todo: Stalking behaviors
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.65d);
	}

	protected void updateAITasks() {
		if (this.isWet()) {
			this.attackEntityFrom(DamageSource.DROWN, 2.5F);
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 10;
	}

	public float getBrightness() {
		return 0.3F;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase && this.getAnimation() != ANIMATION_BITE) {
				{
					this.setAnimation(ANIMATION_BITE);
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 100, 0, false, false));
				}
			}
			return flag;
		}
		return false;
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		AnimationHandler.INSTANCE.updateAnimations(this);
	}

	public void resetTimer() {
		timerRef = 0;
	}

	public void addTimer(int n) {
		timerRef += n;
	}

	public int getTimer() {
		return timerRef;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public int getSkinTypes() {
		return 9;
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
		return new Animation[]{IAnimatedEntity.NO_ANIMATION, EntityUran.ANIMATION_BITE};
	}
}
