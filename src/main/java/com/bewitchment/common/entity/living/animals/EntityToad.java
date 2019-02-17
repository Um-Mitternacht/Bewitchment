package com.bewitchment.common.entity.living.animals;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.familiars.EntityFamiliar;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class EntityToad extends EntityFamiliar /*implements IAnimatedEntity*/ {

	//public static final Animation ANIMATION_LEAP = Animation.create(20, 15);
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/toad");
	private static final String[] names = {"Iron Henry", "Jimmy", "Kermit", "Frog-n-stein", "Prince Charming", "Heqet", "Hapi", "Aphrodite", "Physignathus", "Jiraiya", "Dat Boi", "Llamhigyn Y Dwr", "Michigan", "Wednesday", "Trevor", "Odin", "Woden", "Mercury", "Miércoles"};
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE, ModItems.silver_scales, ModItems.envenomed_fang);
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityToad.class, DataSerializers.VARINT);
	private static final double maxHPWild = 8;
	//private int animationTick;
	//private Animation currentAnimation;
	private static final DataParameter<Integer> ANIMATION_TIME = EntityDataManager.<Integer>createKey(EntityToad.class, DataSerializers.VARINT);
	private static final DataParameter<Float> ANIMATION_HEIGHT = EntityDataManager.<Float>createKey(EntityToad.class, DataSerializers.FLOAT);

	public EntityToad(World worldIn) {
		super(worldIn);
		setSize(1F, 0.3F);
		this.moveHelper = new EntityMoveHelper(this);
	}

	@Override
	public String[] getRandomNames() {
		return names;
	}

	@Override
	protected void setFamiliarAttributes(boolean isFamiliar) {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(isFamiliar ? 20 : maxHPWild);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TINT, 0xFFFFFF);
		this.dataManager.register(ANIMATION_TIME, Integer.valueOf(0));
		this.dataManager.register(ANIMATION_HEIGHT, Float.valueOf(0.0F));
		this.aiSit = new EntityAISit(this);
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		}
		if (this.aiSit != null) {
			this.aiSit.setSitting(false);
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean canBePushed() {
		return true;
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

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(0, new EntityAIPanic(this, 0.4D));
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(3, new EntityAIMate(this, 0.4d));
		this.tasks.addTask(4, this.aiSit);
		this.targetTasks.addTask(3, new EntityAITargetNonTamed<>(this, EntityPlayer.class, true, p -> p.getDistanceSq(this) < 1));
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityEndermite || e instanceof EntitySilverfish));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(4, new EntityAIFollowParent(this, 0.4D));
		this.tasks.addTask(5, new EntityAIWander(this, 0.4D));
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.70d);
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2000, 1, false, false));
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
		} else if (!(otherAnimal instanceof EntityToad)) {
			return false;
		} else {
			EntityToad entitytoad = (EntityToad) otherAnimal;

			if (!entitytoad.isTamed()) {
				return false;
			} else if (entitytoad.isSitting()) {
				return false;
			} else {
				return this.isInLove() && entitytoad.isInLove();
			}
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		{
			ItemStack itemstack = player.getHeldItem(hand);

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
						setFamiliar(true);
						this.playTameEffect(true);
						BewitchmentAPI.getAPI().bindFamiliarToPlayer(player, this);
						this.world.setEntityState(this, (byte) 7);
					} else {
						this.playTameEffect(false);
						this.world.setEntityState(this, (byte) 6);
					}
				}
				return true;
			}
			return true;
		}
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(getOwner())) {
			super.collideWithEntity(entityIn);
		}
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == ModItems.envenomed_fang;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityToad(world);
	}

	@Override
	public int getSkinTypes() {
		return 4;
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
		return (float) this.dataManager.get(ANIMATION_HEIGHT);
	}

	public float setAnimationHeight(float in) {
		this.dataManager.set(ANIMATION_HEIGHT, in);
		return in;
	}

	public void resetAnimationHeight() {
		this.dataManager.set(ANIMATION_HEIGHT, 0.0F);
	}

	// LLibrary stuff \/
	/*
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
		return new Animation[]{IAnimatedEntity.NO_ANIMATION, EntityToad.ANIMATION_LEAP};
	}*/
}
