package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModSounds;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAITargetNonTamed;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.passive.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

@SuppressWarnings("WeakerAccess")
public class EntityOwl extends EntityRaven {
	public EntityOwl(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/owl"), Items.RABBIT, Items.CHICKEN);
		setSize(0.4f, 0.9f);
		moveHelper = new EntityFlyHelper(this);
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable other) {
		EntityAgeable entity = new EntityOwl(world);
		entity.getDataManager().set(SKIN, world.rand.nextBoolean() ? getDataManager().get(SKIN) : other.getDataManager().get(SKIN));
		return entity;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.OWL_HOOT;
	}
	
	@Override
	public boolean canMateWith(EntityAnimal other) {
		if (other == this || !(other instanceof EntityOwl)) return false;
		return isTamed() && isInLove() && ((EntityTameable) other).isTamed() && other.isInLove() && !((EntityTameable) other).isSitting();
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.RABBIT;
	}
	
	@Override
	protected float getSoundVolume() {
		return 0.5f;
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.5);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(10);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.1);
		getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.8);
	}
	
	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		tasks.addTask(1, new EntityAIFleeSun(this, 1));
		targetTasks.addTask(2, new EntityAITargetNonTamed<>(this, EntityLivingBase.class, false, e -> e instanceof EntityBat || e instanceof EntityChicken || e instanceof EntityLizard || e instanceof EntityParrot || e instanceof EntityRabbit));
	}
}