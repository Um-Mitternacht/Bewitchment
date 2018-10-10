package com.bewitchment.common.entity.living.familiar;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.entity.EntityFamiliar;
import com.bewitchment.common.core.handler.ModSounds;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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

public class EntityOwl extends EntityFamiliar {

	private static final double maxHPWild = 8;
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/owl");
	private static final String[] names = {"Owlmighty", "Owliver", "Owl Capone", "Owleister Crowley", "Owlie", "Owlivia", "Owlive", "Hedwig", "Archimedes", "Owlexander", "Robin Hoot", "Owlex", "Athena", "Strix", "Minerva", "Ascalaphus", "Lechuza", "Stolas", "Andras", "Kikiyaon", "Chickcharney", "Hootling"};
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityOwl.class, DataSerializers.VARINT);

	public EntityOwl(World worldIn) {
		super(worldIn);
		this.setSize(0.4f, 0.9f);
		this.moveHelper = new EntityFlyHelper(this);
	}

	public static boolean isOwlFodder(Entity entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof EntityRabbit || entity instanceof EntityBat || entity instanceof EntityChicken || entity instanceof EntityParrot || className.contains("Rat") || className.contains("Hedgehog") || className.contains("Hamster") || className.contains("Squirrel") || className.contains("Hare") || className.contains("Fox") || className.contains("Weasel") || className.contains("Pigeon") || className.contains("Turkey") || className.contains("Mouse") || className.contains("Bat") || className.contains("Lizard") || className.contains("Frog") || className.contains("Toad") || className.contains("Snake") || className.contains("Beetle") || className.contains("Chinchilla") || className.contains("Cavy") || className.contains("GuineaPig") || className.contains("Crow") || className.contains("Raven") || className.contains("Pheasant") || className.contains("Partridge") || className.contains("Jackdaw") || className.contains("Mongoose") || className.contains("Rooster") || className.contains("Hen") || className.contains("Chick") || className.contains("Shrew") || className.contains("Mole") || className.contains("Vole") || className.contains("Lemming") || className.contains("Jird") || className.contains("Jerboa") || className.contains("Gerbil") || className.contains("Muskrat") || className.contains("Marmot") || className.contains("Deer") || className.contains("Ferret") || className.contains("Chinchilla");
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(maxHPWild);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.6);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
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
		//Fixme: Overhaul isOwlFodder, move it to DietaryUtils, and set up a class whitelist. Current means are messy and possibly straining on resources.
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, EntityOwl::isOwlFodder));
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
		if (!player.world.isRemote) {
			if (!isFamiliar() && !isChild()) {
				setTamedBy(player);
				BewitchmentAPI.getAPI().bindFamiliarToPlayer(player, this);
			} else if (player.getHeldItem(hand).isEmpty()) { // TODO temp code
				if (player.isSneaking()) {
					setFamiliar(false);
					setTamed(false);
					setOwnerId(null);
				} else {
					this.aiSit.setSitting(!isSitting());
					this.setSitting(!isSitting());
				}
			} else {
				super.processInteract(player, hand);
			}
		}
		return true;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntityOwl(world);
	}

	@Override
	public int getTotalVariants() {
		return 4;
	}

	@Override
	public String[] getRandomNames() {
		return names;
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
		return 4;
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
	public boolean isCreatureType(EnumCreatureType type, boolean forSpawnCount) {
		if (forSpawnCount && isFamiliar()) {
			return false;
		}
		return super.isCreatureType(type, forSpawnCount);
	}

	@Override
	public boolean isNoDespawnRequired() {
		return super.isNoDespawnRequired() || isFamiliar();
	}

	@Override
	protected void setFamiliarAttributes(boolean isFamiliar) {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(isFamiliar ? 20 : maxHPWild);
	}

}
