package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityTameable;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings({"WeakerAccess", "NullableProblems"})
public class EntityRaven extends ModEntityTameable {
	public EntityRaven(World world) {
		this(world, new ResourceLocation(Bewitchment.MODID, "entities/raven"), Items.GOLD_NUGGET, ModObjects.silver_nugget);
	}
	
	protected EntityRaven(World world, ResourceLocation lootTableLocation, Item... tameItems) {
		super(world, lootTableLocation, tameItems);
		setSize(0.4f, 0.4f);
		moveHelper = new EntityFlyHelper(this);
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable other) {
		EntityAgeable entity = new EntityRaven(world);
		entity.getDataManager().set(SKIN, world.rand.nextBoolean() ? getDataManager().get(SKIN) : other.getDataManager().get(SKIN));
		return entity;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getTrueSource() != null && !(source.getTrueSource() instanceof EntityPlayer) && !(source.getTrueSource() instanceof EntityArrow)) amount = (amount + 1) / 2f;
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() instanceof ItemSeeds;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!onGround && motionY <= 0) motionY *= 0.6;
	}
	
	@Override
	public boolean canMateWith(EntityAnimal other) {
		if (other == this || !(other instanceof EntityRaven)) return false;
		return isTamed() && isInLove() && ((EntityTameable) other).isTamed() && other.isInLove() && !((EntityTameable) other).isSitting();
	}
	
	@Override
	protected void updateFallState(double y, boolean grounded, IBlockState state, BlockPos pos) {
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {
	}
	
	@Override
	public boolean attackEntityAsMob(Entity entity) {
		return entity.attackEntityFrom(DamageSource.causeMobDamage(this), (float) getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIMate(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() / 2));
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(3, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, aiSit);
		tasks.addTask(3, new EntityAIFollowParent(this, getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue()));
		tasks.addTask(3, new EntityAIWanderAvoidWaterFlying(this, getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue()));
		tasks.addTask(4, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue()));
		tasks.addTask(5, new EntityAIFollowOwnerFlying(this, getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getAttributeValue(), 2, 5));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(0, new EntityAIOwnerHurtByTarget(this));
		targetTasks.addTask(1, new EntityAIOwnerHurtTarget(this));
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.5);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
		getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1);
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
		return ModSounds.RAVEN_CRY;
	}
	
	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}
}