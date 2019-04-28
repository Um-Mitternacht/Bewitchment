package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.LargeEntityAIAttackMelee;
import com.bewitchment.common.entity.living.EntityMultiSkinMonster;
import com.bewitchment.common.lib.LibMod;
import com.bewitchment.common.potion.ModPotions;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

/**
 * Created by Joseph on 12/28/2018.
 */
public class EntityHellhoundAlpha extends EntityMultiSkinMonster implements IAnimatedEntity, IMob {

	@SuppressWarnings("deprecation")
	public static final Animation ANIMATION_BITE = Animation.create(20, 10);
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/hellhound_alpha");
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityHellhoundAlpha.class, DataSerializers.VARINT);
	private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenSky(false);
	private int animationTick;
	private Animation currentAnimation;

	public EntityHellhoundAlpha(World world) {
		super(world);
		setSize(1.6F, 1.6F);
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 65;
		this.moveHelper = new EntityMoveHelper(this);
	}

	@Override
	public int getSkinTypes() {
		return 6;
	}

	public void setCustomNameTag(String name) {
		super.setCustomNameTag(name);
		this.bossInfo.setName(this.getDisplayName());
	}

	@Override
	public boolean getCanSpawnHere() {
		return (this.world.provider.doesWaterVaporize() || this.world.provider.isNether()) && this.world.checkNoEntityCollision(this.getEntityBoundingBox()) && this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox());
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.95d);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.5D);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(5, new EntityAIWander(this, 0.5D));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> e instanceof EntityUran || (!e.isImmuneToFire() && e.getCreatureAttribute() != BewitchmentAPI.getAPI().DEMON && e.getCreatureAttribute() != EnumCreatureAttribute.UNDEAD)));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new LargeEntityAIAttackMelee(this, 0.5D, false));
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
					((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2000, 3, false, false));
					entity.setFire(125);
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

	protected void updateAITasks() {
		if (this.ticksExisted % 20 == 0) {
			this.heal(2.5F);
		}

		this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
	}

	public boolean isPotionApplicable(PotionEffect potioneffectIn) {
		if (potioneffectIn.getPotion() == MobEffects.WITHER || potioneffectIn.getPotion() == MobEffects.POISON || potioneffectIn.getPotion() == ModPotions.rotting) {
			net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent event = new net.minecraftforge.event.entity.living.PotionEvent.PotionApplicableEvent(this, potioneffectIn);
			net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
			return event.getResult() == net.minecraftforge.fml.common.eventhandler.Event.Result.ALLOW;
		}
		return super.isPotionApplicable(potioneffectIn);
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		if (this.hasCustomName()) {
			this.bossInfo.setName(this.getDisplayName());
		}
	}

	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	public boolean isNonBoss() {
		return false;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	public float getBrightness() {
		return 0.3F;
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
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
		return new Animation[]{IAnimatedEntity.NO_ANIMATION, EntityHellhoundAlpha.ANIMATION_BITE};
	}
}
