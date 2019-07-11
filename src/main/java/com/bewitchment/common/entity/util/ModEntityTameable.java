package com.bewitchment.common.entity.util;

import com.google.common.collect.Sets;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.attributes.AttributeModifier;
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
import java.util.UUID;

@SuppressWarnings({"NullableProblems", "EntityConstructor", "ConstantConditions"})
public abstract class ModEntityTameable extends EntityTameable {
	public static final DataParameter<Integer> SKIN = EntityDataManager.createKey(ModEntityTameable.class, DataSerializers.VARINT);
	
	private static final AttributeModifier BOOST_HEALTH = new AttributeModifier(UUID.fromString("78a34748-46be-445a-8de6-d5f2436a6d6b"), "boostHealth", 2, 1);
	
	private final Set<Item> tameItems;
	
	private final ResourceLocation lootTableLocation;
	
	protected ModEntityTameable(World world, ResourceLocation lootTableLocation, Item... tameItems) {
		super(world);
		this.tameItems = Sets.newHashSet(tameItems);
		this.lootTableLocation = lootTableLocation;
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		return lootTableLocation;
	}
	
	@Override
	public EntityAgeable createChild(EntityAgeable other) {
		EntityAgeable entity = (EntityAgeable) EntityRegistry.getEntry(getClass()).newInstance(world);
		entity.getDataManager().set(SKIN, rand.nextBoolean() ? dataManager.get(SKIN) : other.getDataManager().get(SKIN));
		return entity;
	}
	
	@Override
	public boolean canMateWith(EntityAnimal other) {
		if (other == this || !(other.getClass().getName().equals(getClass().getName()))) return false;
		return isTamed() && isInLove() && ((EntityTameable) other).isTamed() && other.isInLove() && !((EntityTameable) other).isSitting();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		Entity entity = source.getTrueSource();
		if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) amount = (amount + 1) / 2f;
		boolean flag = super.attackEntityFrom(source, amount);
		if (flag && aiSit != null) aiSit.setSitting(false);
		return flag;
	}
	
	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
		if (!isTamed() && tameItems.contains(stack.getItem())) {
			if (!player.isCreative()) stack.shrink(1);
			if (!isSilent()) world.playSound(null, posX, posY, posZ, SoundEvents.ENTITY_PARROT_EAT, getSoundCategory(), 1, 1 + (rand.nextFloat() - rand.nextFloat()) * 0.2f);
			if (!world.isRemote) {
				if (rand.nextInt(5) == 0 && !ForgeEventFactory.onAnimalTame(this, player)) {
					setTamedBy(player);
					playTameEffect(true);
					world.setEntityState(this, (byte) 7);
				}
				else {
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
	public void setTamed(boolean tamed) {
		super.setTamed(tamed);
		if (tamed) getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(BOOST_HEALTH);
		else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).removeModifier(BOOST_HEALTH);
		heal(getMaxHealth());
	}
	
	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData data) {
		dataManager.set(SKIN, rand.nextInt(getSkinTypes()));
		return super.onInitialSpawn(difficulty, data);
	}
	
	@Override
	protected void initEntityAI() {
		aiSit = new EntityAISit(this);
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
	
	protected int getSkinTypes() {
		return 1;
	}
	
	@Override
	public abstract boolean isBreedingItem(ItemStack stack);
}