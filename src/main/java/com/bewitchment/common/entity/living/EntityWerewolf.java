package com.bewitchment.common.entity.living;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 3/29/2020.
 */
public class EntityWerewolf extends ModEntityMob {
	private static final DataParameter<Integer> WEREWOLF_TYPE = EntityDataManager.<Integer>createKey(EntityWerewolf.class, DataSerializers.VARINT);
	
	protected EntityWerewolf(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/werewolf"));
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
	
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		
		if (flag) {
			this.applyEnchantments(this, entityIn);
		}
		
		return flag;
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
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!world.isRemote && world.isDaytime() && !world.isRaining() && canDespawn()) setDead();
	}
	
	@Override
	public boolean getCanSpawnHere() {
		if (!world.isDaytime()) if (world.getCurrentMoonPhaseFactor() == 1.0) return super.getCanSpawnHere();
		return false;
	}
	
	@Override
	protected boolean canDespawn() {
		return !hasCustomName();
	}
	
	//WIP
	private int getRandomWerewolfType() {
		Biome biome = this.world.getBiome(new BlockPos(this));
		int i = this.rand.nextInt(100);
		if (biome.isSnowyBiome()) {
			return 1;
		}
		
		return i < 50 ? 0 : (i < 90 ? 5 : 2);
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
	
	//@Override
	//public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
	//	BlockPos pos = getPosition();
	//	World world = getEntityWorld();
	//
	//	if (this.world.getBiomeForCoordsBody(pos, world.getBiome(BiomeDictionary.Type.SAVANNA))) {
	//		return (IEntityLivingData) AFRICAN;
	//	}
	//	else if (this.world.getBiomeForCoordsBody(pos, world.getBiome(BiomeDictionary.Type.JUNGLE))) {
	//		return (IEntityLivingData) ASIAN;
	//	}
	//	return data;
	//}
}
