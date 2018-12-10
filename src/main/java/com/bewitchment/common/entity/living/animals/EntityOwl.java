package com.bewitchment.common.entity.living.animals;

import com.bewitchment.common.core.statics.ModSounds;
import com.bewitchment.common.entity.living.EntityMultiSkin;
import com.bewitchment.common.lib.LibMod;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Set;

public class EntityOwl extends EntityMultiSkin {

	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/owl");
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.RABBIT, Items.CHICKEN);
	//	private static final String[] names = {"Owlmighty", "Owliver", "Owl Capone", "Owleister Crowley", "Owlie", "Owlivia", "Owlive", "Hedwig", "Archimedes", "Owlexander", "Robin Hoot", "Owlex", "Athena", "Strix", "Minerva", "Ascalaphus", "Lechuza", "Stolas", "Andras", "Kikiyaon", "Chickcharney", "Hootling"};
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityOwl.class, DataSerializers.VARINT);

	public EntityOwl(World worldIn) {
		super(worldIn);
		this.setSize(0.4f, 0.9f);
		this.moveHelper = new EntityFlyHelper(this);
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.6);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(6.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6d);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(TINT, 0xFFFFFF);
		this.aiSit = new EntityAISit(this);
	}

	@Override
	protected void setupTamedAI() {
		this.tasks.addTask(5, new EntityAIFollowOwnerFlying(this, 2, 10, 2));
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.5D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(2, new EntityAIFleeSun(this, 1d));
		this.tasks.addTask(4, new EntityAIWanderAvoidWaterFlying(this, 0.8));
		this.tasks.addTask(3, new EntityAIMate(this, 0.8d));
		this.tasks.addTask(4, this.aiSit);
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, e -> e instanceof EntityRabbit || e instanceof EntityBat || e instanceof EntityChicken || e instanceof EntityParrot || e instanceof EntityBlindworm || e instanceof EntityLizard));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
	}

	@Override
	protected PathNavigate createNavigator(World worldIn) {
		PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
		pathnavigateflying.setCanOpenDoors(false);
		pathnavigateflying.setCanFloat(true);
		pathnavigateflying.setCanEnterDoors(true);
		return pathnavigateflying;
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
						this.playTameEffect(true);
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
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityOwl(world);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.OWL_HOOT;
	}

	@Override
	protected float getSoundVolume() {
		return 0.5f;
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.RABBIT;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(otherAnimal instanceof EntityOwl)) {
			return false;
		} else {
			EntityOwl entityowl = (EntityOwl) otherAnimal;

			if (!entityowl.isTamed()) {
				return false;
			} else if (entityowl.isSitting()) {
				return false;
			} else {
				return this.isInLove() && entityowl.isInLove();
			}
		}
	}

	@Override
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockLeaves || block == Blocks.GRASS || block instanceof BlockLog || block == Blocks.AIR && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}

	@Override
	public boolean attackEntityAsMob(Entity entityIn) {
		return entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), 2.0F);
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(getOwner())) {
			super.collideWithEntity(entityIn);
		}
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
	public int getSkinTypes() {
		return 4;
	}

}
