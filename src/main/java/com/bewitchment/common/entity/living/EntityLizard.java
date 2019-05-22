package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityAnimal;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

@SuppressWarnings({"NullableProblems", "WeakerAccess"})
public class EntityLizard extends ModEntityAnimal {
	public EntityLizard(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/lizard"));
		setSize(1, 0.3f);
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable other) {
		EntityAgeable entity = new EntityLizard(world);
		entity.getDataManager().set(SKIN, world.rand.nextBoolean() ? getDataManager().get(SKIN) : other.getDataManager().get(SKIN));
		return entity;
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
	
	@Override
	public boolean canMateWith(EntityAnimal other) {
		if (other == this || !(other instanceof EntityLizard)) return false;
		return isInLove() && other.isInLove();
	}
	
	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.SPIDER_EYE;
	}
	
	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(6);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6);
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAIPanic(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIMate(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() / 2));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(2, new EntityAIFollowParent(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 2 / 3));
	}
}