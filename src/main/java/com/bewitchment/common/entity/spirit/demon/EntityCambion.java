package com.bewitchment.common.entity.spirit.demon;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.common.entity.living.EntityWerewolf;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
import com.bewitchment.common.entity.util.ModEntityMob;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

/**
 * Created by Joseph on 3/30/2020.
 */

public class EntityCambion extends ModEntityMob {
	private static final DataParameter<Integer> CAMBION_TYPE = EntityDataManager.createKey(EntityCambion.class, DataSerializers.VARINT);
	public int attackTimer = 0;

	public EntityCambion(World world) {
		super(world, new ResourceLocation(Bewitchment.MODID, "entities/cambion"));
		setSize(0.8f, 2.0f);
		experienceValue = 25;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return BewitchmentAPI.DEMON;
	}

	@Override
	protected void initEntityAI() {
		super.initEntityAI();
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.35, false));
		tasks.addTask(2, new EntityAIWatchClosest2(this, EntityPlayer.class, 5, 1));
		tasks.addTask(3, new EntityAILookIdle(this));
		tasks.addTask(3, new EntityAIOpenDoor(this, true));
		tasks.addTask(3, new EntityAIWander(this, getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * (2 / 3d)));
		tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLivingBase.class, 10, true, false, e -> e instanceof EntityHellhound || e instanceof EntityFeuerwurm || e instanceof EntityGhost || e instanceof EntityBlackDog || e instanceof EntityZombie || e instanceof EntitySkeleton || e instanceof EntitySpider || e instanceof EntityWerewolf));
	}

	@Override
	public void handleStatusUpdate(byte id) {
		if (id == 4) attackTimer = 10;
		else super.handleStatusUpdate(id);
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
	}

	@Override
	protected void updateEquipmentIfNeeded(EntityItem itemEntity) {
		if (itemEntity.getItem().getItem() instanceof ItemSword || itemEntity.getItem().getItem() instanceof ItemArmor || itemEntity.getItem().getItem() instanceof ItemShield) {
			super.updateEquipmentIfNeeded(itemEntity);
		}
	}

	@Override
	protected boolean canEquipItem(ItemStack stack) {
		return super.canEquipItem(stack);
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		super.setEquipmentBasedOnDifficulty(difficulty);
	}

	@Override
	protected boolean isValidLightLevel() {
		return true;
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(CAMBION_TYPE, Integer.valueOf(0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("CambionType", this.getCambionType());
	}

	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.setCambionType(compound.getInteger("CambionType"));
	}

	@Nullable
	protected ResourceLocation getLootTable() {
		return LootTableList.ENTITIES_WITCH;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		data = super.onInitialSpawn(difficulty, data);
		int i = this.getRandomCambionType();
		int a = rand.nextInt(6);
		int b = rand.nextInt(8);
		int c = rand.nextInt(4);
		int d = rand.nextInt(8);

		if (data instanceof EntityCambion.CambionTypeData) {
			i = ((EntityCambion.CambionTypeData) data).typeData;
		} else {
			data = new EntityCambion.CambionTypeData(i);
		}

		this.setCambionType(i);

		if (a == 3) {
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.CHAINMAIL_CHESTPLATE));
		} else if (a < 1) {
			this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.GOLDEN_CHESTPLATE));
		} else if (a > 4) this.setItemStackToSlot(EntityEquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));

		if (b < 2) {
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
		} else if (d < 2) {
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_AXE));
		} else if (d > 4) {
			this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
		} else if (b > 4) this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));

		if (c < 2) {
			this.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, new ItemStack(Items.SHIELD));
		} else if (c > 2)

			setEquipmentBasedOnDifficulty(difficulty);
		setCanPickUpLoot(this.rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());
		setEnchantmentBasedOnDifficulty(difficulty);
		return super.onInitialSpawn(difficulty, data);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (!world.isRemote & isChild()) setDead();
	}

	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
	}

	protected SoundEvent getDeathSound() {
		return SoundEvents.EVOCATION_ILLAGER_DEATH;
	}

	//Todo: Redo everything.
	public boolean attackEntityAsMob(Entity entityIn) {
		boolean flag = super.attackEntityAsMob(entityIn);

		if (flag) {
			this.applyEnchantments(this, entityIn);
			attackTimer = 10;
			int i = this.rand.nextInt(100);
			int j = this.rand.nextInt(100);
			world.setEntityState(this, (byte) 4);
			if (entityIn instanceof EntityLivingBase) {
				if (i < 5) {
					entityIn.setFire(25);
				} else if (j < 35) {
					((EntityLivingBase) entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 500, 0, false, true));
				}
			}
		}

		return flag;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4);
		getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1);
		getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(1);
		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
	}

	private int getRandomCambionType() {
		int flag = rand.nextInt(4);
		return flag;
	}

	public int getCambionType() {
		return this.dataManager.get(CAMBION_TYPE).intValue();
	}

	public void setCambionType(int cambionTypeId) {
		this.dataManager.set(CAMBION_TYPE, Integer.valueOf(cambionTypeId));
	}

	public static class CambionTypeData implements IEntityLivingData {
		public int typeData;

		public CambionTypeData(int type) {
			this.typeData = type;
		}
	}
}
