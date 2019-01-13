package com.bewitchment.common.entity.spirits.demons;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.entity.living.animals.*;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 10/2/2018.
 */

public class EntityUran extends EntityMultiSkin implements IMob, IAnimatedEntity {


	//Todo: Rewrite code, and implement weaknesses to water. Also implement a special potion effect that upon killing a target, spawns more uranids.
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/snake");
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityUran.class, DataSerializers.VARINT);
	private static final int TIME_BETWEEN_MILK = 6660;
	private int timerRef = 0;
	private int milkCooldown = 0;
	private int animationTick;
	private Animation currentAnimation;

	public EntityUran(World worldIn) {
		super(worldIn);
		setSize(1.6F, 1.6F); //Todo: Figure out how to change the size of this properly
		this.isImmuneToFire = true;
		this.setPathPriority(PathNodeType.WATER, -1.0F);
		this.setPathPriority(PathNodeType.LAVA, 8.0F);
		this.setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
		this.setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.experienceValue = 20;
	}

	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.getAPI().DEMON;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		if (this.aiSit != null) {
			this.aiSit.setSitting(false);
		}
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
		//DEV ONLY CODE -- REMOVE BEFORE COMPILATION
		//TODO
		//---- ^^^^ ----
		if (this.getAttackTarget() == null || this.getAttackTarget().isDead || this.getRevengeTarget() == null || this.getRevengeTarget().isDead) {
			ItemStack itemstack = player.getHeldItem(hand);
			if (itemstack.getItem() == ModItems.glass_jar) {
				if (milkCooldown == 0 && getRNG().nextBoolean()) {
					if (this.getGrowingAge() >= 0 && !player.capabilities.isCreativeMode) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.setHeldItem(hand, new ItemStack(ModItems.uranid_venom));
						} else if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.snake_venom))) {
							player.dropItem(new ItemStack(ModItems.uranid_venom), false);
						}
						milkCooldown = TIME_BETWEEN_MILK;
						return true;
					}
				} else {
					//if milk not ready, or randomly 1/2 of the times
					this.setAttackTarget(player);
					this.setRevengeTarget(player);
				}
			}
		}
		return false;
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
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityRabbit || e instanceof EntityChicken || e instanceof EntityBlindworm || e instanceof EntityLizard || e instanceof EntityCow || e instanceof EntityParrot || e instanceof EntitySheep || e instanceof EntityPig || e instanceof EntityVillager || e instanceof EntityPlayer || e instanceof EntityRaven || e instanceof EntityOwl || e instanceof EntityNewt || e instanceof EntityToad || e instanceof EntitySnake || e instanceof EntityHorse || e instanceof EntityDonkey || e instanceof EntityMule || e instanceof EntityLlama || e instanceof EntityWolf || e instanceof EntityOcelot || e instanceof EntityPolarBear || e.getClass().getName().equals("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat")));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
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
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
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
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 2000, 1, false, false));
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
	public EntityAgeable createChild(EntityAgeable ageable) {
		return null;
	}

	@Override
	public int getSkinTypes() {
		return 9;
	}

	@Override
	public int getAnimationTick() {
		return 0;
	}

	@Override
	public void setAnimationTick(int tick) {

	}

	@Override
	public Animation getAnimation() {
		return null;
	}

	@Override
	public void setAnimation(Animation animation) {

	}

	@Override
	public Animation[] getAnimations() {
		return new Animation[0];
	}
}
