package com.bewitchment.common.entity.living.familiar;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.entity.EntityFamiliar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityOwl extends EntityFamiliar {

	private static final String[] names = {"Owlmighty", "Owliver", "Owl Capone", "Owleister Crowley", "Owlie", "Owlivia", "Owlive", "Hedwig", "Archimedes", "Owlexander", "Robin Hoot", "Owlex", "Athena", "Strix", "Minerva", "Ascalaphus", "Lechuza", "Stolas", "Andras", "Kikiyaon", "Chickcharney", "Hootling"};
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntityOwl.class, DataSerializers.VARINT);

	public EntityOwl(World worldIn) {
		super(worldIn);
		this.setSize(0.4f, 0.9f);
		this.moveHelper = new EntityFlyHelper(this);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(2);
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
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(2, new EntityAIFleeSun(this, 2d));
		this.tasks.addTask(4, new EntityAIWanderAvoidWaterFlying(this, 0.8));
		this.tasks.addTask(4, this.aiSit);
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[0]));
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
			if (!isFamiliar()) {
				setTamedBy(player);
				BewitchmentAPI.getAPI().bindFamiliarToPlayer(player, this);
			} else {
				if (player.isSneaking()) {
					setFamiliar(false);
					setTamed(false);
					setOwnerId(null);
				} else {
					this.aiSit.setSitting(!isSitting());
					this.setSitting(!isSitting());
				}
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
		return 1;
	}

	@Override
	public String[] getRandomNames() {
		return names;
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
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

}
