package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractIllager;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 3/29/2020.
 */
public class EntityWerewolf extends ModEntityMob {
	private static final DataParameter<Integer> WEREWOLF_TYPE = EntityDataManager.<Integer>createKey(EntityWerewolf.class, DataSerializers.VARINT);
	
	public EntityWerewolf(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/werewolf"));
		setSize(1.200f, 3.2f);
		experienceValue = 35;
	}
	
	@Override
	protected boolean isValidLightLevel() {
		return true;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(WEREWOLF_TYPE, Integer.valueOf(0));
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("WerewolfType", this.getWerewolfType());
	}
	
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setWerewolfType(compound.getInteger("WerewolfType"));
	}
	
	@Nullable
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		livingdata = super.onInitialSpawn(difficulty, livingdata);
		int i = this.getRandomWerewolfType();
		boolean flag = false;
		
		if (livingdata instanceof EntityWerewolf.WerewolfTypeData) {
			i = ((EntityWerewolf.WerewolfTypeData) livingdata).typeData;
			flag = true;
		}
		else {
			livingdata = new EntityWerewolf.WerewolfTypeData(i);
		}
		
		this.setWerewolfType(i);
		
		return livingdata;
	}
	
	@Override
	protected void initEntityAI() {
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.5, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, false, false, e -> e instanceof EntityVillager || e instanceof AbstractIllager || e instanceof EntityWitch || e instanceof EntityIronGolem || e instanceof EntitySheep || e instanceof EntityCow || e instanceof EntityChicken || e instanceof EntityLlama || e instanceof EntityPig || e instanceof EntityRabbit || e instanceof AbstractHorse));
	}
	
	@Override
	protected boolean canDespawn() {
		return !hasCustomName();
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!world.isRemote && world.isDaytime() && canDespawn()) setDead();
	}
	
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		
		if (flag) {
			this.applyEnchantments(this, entityIn);
		}
		
		return flag;
	}
	
	@Override
	public boolean getCanSpawnHere() {
		if (!world.isDaytime()) if (world.getCurrentMoonPhaseFactor() == 1.0) return super.getCanSpawnHere();
		return false;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(1);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(55);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
	}
	
	private int getRandomWerewolfType() {
		boolean flag = rand.nextBoolean();
		Biome biome = world.getBiome(getPosition());
		if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SNOWY)) return flag ? 1 : 5;
		else if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.CONIFEROUS)) return flag ? 3 : 2;
		else return flag ? 4 : 0;
	}
	
	public int getWerewolfType() {
		return ((Integer) this.dataManager.get(WEREWOLF_TYPE)).intValue();
	}
	
	public void setWerewolfType(int werewolfTypeId) {
		this.dataManager.set(WEREWOLF_TYPE, Integer.valueOf(werewolfTypeId));
	}
	
	public static class WerewolfTypeData implements IEntityLivingData {
		public int typeData;
		
		public WerewolfTypeData(int type) {
			this.typeData = type;
		}
	}
}
