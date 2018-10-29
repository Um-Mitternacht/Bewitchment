package com.bewitchment.common.entity.living.familiar;

import com.bewitchment.api.entity.EntityFamiliar;
import com.bewitchment.common.core.helper.MobHelper;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockGrass;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.Set;

/**
 * Created by Joseph on 10/2/2018.
 */

public class EntitySnake extends EntityFamiliar {

	private static final double maxHPWild = 8;
	private static final ResourceLocation loot = new ResourceLocation(LibMod.MOD_ID, "entities/snake");
	private static final String[] names = {};
	private static final Set<Item> TAME_ITEMS = Sets.newHashSet(Items.RABBIT, Items.CHICKEN);
	private static final DataParameter<Integer> TINT = EntityDataManager.createKey(EntitySnake.class, DataSerializers.VARINT);
	private static final int TIME_BETWEEN_MILK = 3600;

	private int milkCooldown = 0;

	public EntitySnake(World worldIn) {
		super(worldIn);
		setSize(1F, .3F);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(TINT, 0xFFFFFF);
		this.aiSit = new EntityAISit(this);
	}

	@Override
	protected void setFamiliarAttributes(boolean isFamiliar) {
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(isFamiliar ? 20 : maxHPWild);
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
	public boolean getCanSpawnHere() {
		int i = MathHelper.floor(this.posX);
		int j = MathHelper.floor(this.getEntityBoundingBox().minY);
		int k = MathHelper.floor(this.posZ);
		BlockPos blockpos = new BlockPos(i, j, k);
		Block block = this.world.getBlockState(blockpos.down()).getBlock();
		return block instanceof BlockGrass && this.world.getLight(blockpos) > 8 && super.getCanSpawnHere();
	}

	@Override
	public int getTotalVariants() {
		return 5;
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 0.3D, false));
		this.tasks.addTask(5, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest2(this, EntityPlayer.class, 5f, 1f));
		this.tasks.addTask(3, new EntityAIMate(this, 1d));
		this.tasks.addTask(4, this.aiSit);
		this.targetTasks.addTask(4, new EntityAITargetNonTamed<EntityLivingBase>(this, EntityLivingBase.class, false, MobHelper::isSnakeFodder));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
	}

	@Override
	protected ResourceLocation getLootTable() {
		return loot;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8d);
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (milkCooldown > 0) {
			milkCooldown--;
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 6;
	}

	@Override
	public boolean attackEntityAsMob(Entity entity) {
		super.attackEntityAsMob(entity);
		boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue()));
		if (flag) {
			this.applyEnchantments(this, entity);
			if (entity instanceof EntityLivingBase) {
				((EntityLivingBase) entity).addPotionEffect(new PotionEffect(MobEffects.POISON, 2000, 1, false, false));
			}
		}
		return flag;
	}

	@Override
	public boolean canMateWith(EntityAnimal otherAnimal) {
		if (otherAnimal == this) {
			return false;
		} else if (!this.isTamed()) {
			return false;
		} else if (!(otherAnimal instanceof EntitySnake)) {
			return false;
		} else {
			EntitySnake entitysnake = (EntitySnake) otherAnimal;

			if (!entitysnake.isTamed()) {
				return false;
			} else if (entitysnake.isSitting()) {
				return false;
			} else {
				return this.isInLove() && entitysnake.isInLove();
			}
		}
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		if (this.getAttackTarget() == null || this.getAttackTarget().isDead || this.getRevengeTarget() == null || this.getRevengeTarget().isDead) {
			ItemStack itemstack = player.getHeldItem(hand);
			if (itemstack.getItem() == ModItems.glass_jar) {
				if (milkCooldown == 0 && getRNG().nextBoolean()) {
					if (this.getGrowingAge() >= 0 && !player.capabilities.isCreativeMode) {
						itemstack.shrink(1);
						if (itemstack.isEmpty()) {
							player.setHeldItem(hand, new ItemStack(ModItems.snake_venom));
						} else if (!player.inventory.addItemStackToInventory(new ItemStack(ModItems.snake_venom))) {
							player.dropItem(new ItemStack(ModItems.snake_venom), false);
						}
						milkCooldown = TIME_BETWEEN_MILK;
						return true;
					}
				} else {
					//if milk not ready, or randomly 1/2 of the times
					this.setAttackTarget(player);
					this.setRevengeTarget(player);
				}
			}

			if (!this.isTamed() && TAME_ITEMS.contains(itemstack.getItem())) {
				if (!player.capabilities.isCreativeMode) {
					itemstack.shrink(1);
				}

				if (!this.isSilent()) {
					this.world.playSound((EntityPlayer) null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PARROT_EAT, this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
				}

				if (!this.world.isRemote) {
					if (this.rand.nextInt(10) == 0 && !net.minecraftforge.event.ForgeEventFactory.onAnimalTame(this, player)) {
						this.setTamedBy(player);
						this.playTameEffect(true);
						this.world.setEntityState(this, (byte) 7);
					} else {
						this.playTameEffect(false);
						this.world.setEntityState(this, (byte) 6);
					}
				}
				return true;
			}
		}
		return false;
	}

	@Override
	protected void collideWithEntity(Entity entityIn) {
		if (!entityIn.equals(getOwner())) {
			super.collideWithEntity(entityIn);
		}
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
	public String[] getRandomNames() {
		return names;
	}

	@Override
	public boolean isBreedingItem(ItemStack stack) {
		return stack.getItem() == Items.RABBIT;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable ageable) {
		return new EntitySnake(world);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		milkCooldown = compound.getInteger("milkCooldown");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("milkCooldown", milkCooldown);
	}
}
