package com.bewitchment.common.entity.living.animals;

import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.entity.spirits.demons.EntityUran;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.google.common.collect.Sets;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Set;

/**
 * Created by Joseph on 10/2/2018.
 */

//ENTITYFAMILIAR
public class EntitySnake extends EntityMultiSkin implements IAnimatedEntity {

	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/snake");
	private static final String[] names = {"David Hisslehoff", "Strangles", "Julius Squeezer", "William Snakespeare", "Medusa", "Sir Hiss", "Nagini", "Naga", "Slithers", "Rumplesnakeskin", "Monty the Python", "Shesha", "Nagaraja", "Stheno", "Euryale", "Vasuki", "Bakunawa", "Kaliya", "Karkotaka", "Manasa", "Mucalinda", "Padmavati", "Paravataksha", "Takshaka", "Ulupi", "Yulong", "Sir Booplesnoot", "Cobra", "Angus Snake", "Anguis", "Python", "Fafnir", "Echidna", "Anaconda", "Madame White Snake", "Meretseger", "Kaa", "Snape", "Solid Snake", "Apophis", "Ouroboros"};
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.RABBIT, Items.CHICKEN);
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntitySnake.class, DataSerializers.VARINT);
	private static final int TIME_BETWEEN_MILK = 3600;
	private static final double maxHPWild = 8;
	private int animationTick;
	private Animation currentAnimation;

	private int timerRef = 0;
	private int milkCooldown = 0;

	public EntitySnake(World worldIn) {
		super(worldIn);
		setSize(1F, .3F);
		this.moveHelper = new EntityMoveHelper(this);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(TINT, 0xFFFFFF);
		this.aiSit = new EntityAISit(this);
	}

	@Override
	public int getSkinTypes() {
		return 6;
	}

	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			Entity entity = source.getTrueSource();

			if (this.aiSit != null) {
				this.setSitting(false);
			}

			if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
				amount = (amount + 1.0F) / 2.0F;
			}

			return super.attackEntityFrom(source, amount);
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockGrass && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}

	public void onStruckByLightning(EntityLightningBolt lightningBolt) {
		if (!this.world.isRemote && !this.isDead) {
			EntityUran entityuran = new EntityUran(this.world);
			entityuran.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			entityuran.setNoAI(this.isAIDisabled());

			if (this.hasCustomName()) {
				entityuran.setCustomNameTag(this.getCustomNameTag());
				entityuran.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
			}

			this.world.spawnEntity(entityuran);
			this.setDead();
		}
	}

	@Override
	protected void initEntityAI() {
		this.aiSit = new EntityAISit(this);
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(3, new EntityAIMate(this, 1d));
		this.tasks.addTask(4, this.aiSit);
		this.targetTasks.addTask(3, new EntityAITargetNonTamed<>(this, EntityPlayer.class, true, p -> p.getDistanceSq(this) < 1));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityRabbit || e instanceof EntityChicken || e instanceof EntityBlindworm || e instanceof EntityLizard || e.getClass().getName().equals("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat")));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.5D, false));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.5D));
		this.tasks.addTask(5, new EntityAIWander(this, 0.5D));
		this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
		this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
		tasks.addTask(4, new EntityAIFollowOwner(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue(), 2, 5));
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.55d);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (milkCooldown > 0) {
			milkCooldown--;
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
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
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 2000, 1, false, false));
			}
		}
		return flag;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(otherAnimal instanceof EntitySnake)) {
			return false;
		} else {
			EntitySnake entitysnake = (EntitySnake) otherAnimal;

			if (!entitysnake.isTamed()) {
				return false;
			} else if (entitysnake.isSitting()) {
				return false;
			} else {
				return this.isInLove() && entitysnake.isInLove();
			}
		}
	}

	@Override
	public boolean canBePushed() {
		return true;
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
							player.setHeldItem(hand, new ItemStack(ModItems.snake_venom));
						} else if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.snake_venom))) {
							player.dropItem(new ItemStack(ModItems.snake_venom), false);
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

			if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem())) {
				if (!player.capabilities.isCreativeMode) {
					itemstack.shrink(1);
				}

				if (!this.isSilent()) {
					this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
				}

				if (!this.world.isRemote) {
					if (this.rand.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
						this.setTamedBy(player);
						this.playTameEffect(true);
						this.world.setEntityState(this, (byte) 7);
					} else {
						this.playTameEffect(false);
						this.world.setEntityState(this, (byte) 6);
					}
				}
				return true;
			}
			if (this.isOwner(player) && !this.world.isRemote && !this.isBreedingItem(itemstack)) {
				this.aiSit.setSitting(!this.isSitting());
				this.isJumping = false;
				this.navigator.clearPath();
				this.setAttackTarget((EntityLivingBase) null);
			}
			return super.processInteract(player, hand);
		}
		return super.processInteract(player, hand);
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(getOwner())) {
			super.collideWithEntity(entityIn);
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.RABBIT;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntitySnake(world);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		milkCooldown = compound.getInteger("milkCooldown");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("milkCooldown", milkCooldown);
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
