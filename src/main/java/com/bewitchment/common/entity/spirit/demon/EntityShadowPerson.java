package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * Created by Joseph on 10/21/2019.
 */
public class EntityShadowPerson extends ModEntityMob {

	public EntityShadowPerson(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/shadow_person"));
		setSize(1, 2.25f);
		isImmuneToFire = true;
		limitedLifeSpan = true;
		lifeTimeTicks = 600 + 20 * rand.nextInt(31);
	}
	
	public void fall(float distance, float damageMultiplier) {
	}
	
	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.75);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.66);
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(0, new EntityAIBreakDoor(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWanderAvoidWater(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> e instanceof EntityVillager || e instanceof AbstractIllager || e instanceof EntityWitch || e instanceof EntityIronGolem));
	}
	
	@Override
	protected int getSkinTypes() {
		return 4;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
}
