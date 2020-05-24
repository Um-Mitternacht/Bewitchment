package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityTameable;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.registry.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("ConstantConditions")
public class EntityOwl extends ModEntityTameable {
	public int timeUntilNextShed;
	protected int shearTimer;
	
	public EntityOwl(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/owl"), Items.RABBIT, Items.CHICKEN);
		setSize(0.4f, 0.9f);
		this.timeUntilNextShed = this.rand.nextInt(6000) + 6000;
		moveHelper = new EntityFlyHelper(this);
		experienceValue = 5;
	}
	
	@Override
	protected PathNavigate createNavigator(World world) {
		PathNavigateFlying path = new PathNavigateFlying(this, world);
		path.setCanEnterDoors(true);
		path.setCanFloat(true);
		path.setCanOpenDoors(false);
		return path;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.OWL_HOOT;
	}
	
	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.RABBIT;
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (!world.isRemote && player.getHeldItem(hand).getItem() instanceof ItemBoline && shearTimer == 0) {
			InventoryHelper.spawnItemStack(world, posX, posY, posZ, new ItemStack(Items.FEATHER, 1 + world.rand.nextInt(3)));
			shearTimer = 12000;
			return true;
		}
		else return super.processInteract(player, hand);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, aiSit);
		tasks.addTask(2, new EntityAIMate(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() / 2));
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAIFollowParent(this, getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue()));
		tasks.addTask(3, new EntityAIWanderAvoidWaterFlying(this, getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue()));
		tasks.addTask(4, new EntityAIFollowOwnerFlying(this, getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue(), 10, 2));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(0, new EntityAIOwnerHurtByTarget(this));
		targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
		tasks.addTask(1, new EntityAIFleeSun(this, 1));
		targetTasks.addTask(2, new EntityAITargetNonTamed<>(this, EntityLivingBase.class, false, e -> e instanceof EntityBat || e instanceof EntityChicken || e instanceof EntityLizard || e instanceof EntityParrot || e instanceof EntityRabbit || e.getClass().getName().endsWith("Rat")));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
		getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.8);
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("shearTimer", shearTimer);
		tag.setInteger("shedTime", this.timeUntilNextShed);
		super.writeEntityToNBT(tag);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		shearTimer = tag.getInteger("shearTimer");
		if (tag.hasKey("shedTime")) {
			this.timeUntilNextShed = tag.getInteger("shedTime");
		}
		super.readEntityFromNBT(tag);
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (this.getHealth() < this.getMaxHealth() && !(ticksExisted % 200 > 5)) {
			this.heal(2);
		}
		if (!onGround && motionY <= 0) motionY *= 0.6;
		if (shearTimer > 0) shearTimer--;
		
		if (!this.world.isRemote && !this.isChild() && --this.timeUntilNextShed <= 0) {
			this.dropItem(Items.FEATHER, 1);
			this.timeUntilNextShed = this.rand.nextInt(6000) + 6000;
		}
	}
	
	@Override
	public boolean getCanSpawnHere() {
		return !world.isDaytime() && super.getCanSpawnHere();
	}
	
	@Override
	protected void updateFallState(double y, boolean grounded, IBlockState state, BlockPos pos) {
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {
	}
	
	@Override
	protected float getSoundVolume() {
		return 0.5f;
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
	}
}
