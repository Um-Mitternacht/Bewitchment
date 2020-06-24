package com.bewitchment.common.entity.util;

import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import javax.annotation.Nullable;
import java.util.Set;

@SuppressWarnings({"NullableProblems", "EntityConstructor", "ConstantConditions", "WeakerAccess"})
public abstract class ModEntityTameable extends EntityTameable {
	public static final DataParameter<Integer> SKIN = EntityDataManager.createKey(ModEntityTameable.class, DataSerializers.VARINT);

	private final Set<Item> tameItems;

	private final ResourceLocation lootTableLocation;

	protected ModEntityTameable(World world, ResourceLocation lootTableLocation, Item... tameItems) {
		super(world);
		this.tameItems = Sets.newHashSet(tameItems);
		this.lootTableLocation = lootTableLocation;
	}

	@Override
	public EntityAgeable createChild(EntityAgeable other) {
		EntityAgeable entity = (EntityAgeable) EntityRegistry.getEntry(getClass()).newInstance(world);
		entity.getDataManager().set(SKIN, rand.nextBoolean() ? dataManager.get(SKIN) : other.getDataManager().get(SKIN));
		return entity;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		Entity entity = source.getTrueSource();
		if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow))
			amount = (amount + 1) / 2f;
		boolean flag = super.attackEntityFrom(source, amount);
		if (flag && aiSit != null) aiSit.setSitting(false);
		return flag;
	}

	@Override
	public abstract boolean isBreedingItem(ItemStack stack);

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!isTamed() && tameItems.contains(stack.getItem())) {
			if (!player.isCreative()) stack.shrink(1);
			if (!isSilent())
				world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_PARROT_EAT, getSoundCategory(), 1, 1 + (rand.nextFloat() - rand.nextFloat()) * 0.2f);
			if (!world.isRemote) {
				if (rand.nextInt(5) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
					setTamedBy(player);
					playTameEffect(true);
					world.setEntityState(this, (byte) 7);
					heal(getMaxHealth());
				} else {
					playTameEffect(false);
					world.setEntityState(this, (byte) 6);
				}
			}
			return true;
		}
		if (!world.isRemote && isOwner(player) && isTamed() && !isBreedingItem(stack)) {
			aiSit.setSitting(!isSitting());
			isJumping = false;
			navigator.clearPath();
			setAttackTarget(null);
		}
		return super.processInteract(player, hand);
	}

	@Override
	public boolean canMateWith(EntityAnimal other) {
		if (other == this || !(other.getClass().getName().equals(getClass().getName()))) return false;
		return isTamed() && isInLove() && ((EntityTameable) other).isTamed() && other.isInLove() && !((EntityTameable) other).isSitting();
	}

	@Override
	protected void initEntityAI() {
		aiSit = new EntityAISit(this);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		boostHealth(isTamed());
	}

	@Override
	protected ResourceLocation getLootTable() {
		return lootTableLocation;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		dataManager.set(SKIN, rand.nextInt(getSkinTypes()));
		return super.onInitialSpawn(difficulty, data);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		dataManager.register(SKIN, 0);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("skin", dataManager.get(SKIN));
		dataManager.setDirty(SKIN);
		super.writeEntityToNBT(tag);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tag) {
		dataManager.set(SKIN, tag.getInteger("skin"));
		super.readEntityFromNBT(tag);
	}

	@Override
	public void setTamed(boolean tamed) {
		super.setTamed(tamed);
		boostHealth(tamed);
	}

	protected int getSkinTypes() {
		return 1;
	}

	protected void boostHealth(boolean tamed) {
		if (tamed) getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8);
	}
}