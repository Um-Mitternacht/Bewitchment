package com.bewitchment.common.entity;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.cauldron.BrewData.ApplicationType;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import com.google.common.base.Predicate;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

public class EntityBrew extends EntityThrowable {

	public static final Predicate<EntityLivingBase> WATER_SENSITIVE = (e -> (e instanceof EntityEnderman || e instanceof EntityBlaze));
	private static final DataParameter<ItemStack> ITEM = EntityDataManager.<ItemStack>createKey(EntityBrew.class, DataSerializers.ITEM_STACK);
	private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityBrew.class, DataSerializers.VARINT);

	public EntityBrew(World worldIn) {
		super(worldIn);
	}

	public EntityBrew(World worldIn, EntityLivingBase throwerIn, ItemStack potionDamageIn) {
		super(worldIn, throwerIn);
		this.setItem(potionDamageIn);
	}

	public EntityBrew(World worldIn, double x, double y, double z, ItemStack potionDamageIn) {
		super(worldIn, x, y, z);
		if (!potionDamageIn.isEmpty()) {
			this.setItem(potionDamageIn);
		}
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(ITEM, ItemStack.EMPTY);
		this.getDataManager().register(COLOR, TileEntityCauldron.DEFAULT_COLOR);
	}

	public ItemStack getBrew() {
		return this.getDataManager().get(ITEM);
	}

	public void setItem(ItemStack stack) {
		this.getDataManager().set(ITEM, stack);
		this.getDataManager().setDirty(ITEM);
		this.getDataManager().set(COLOR, BrewData.fromStack(stack).getColor());
		this.getDataManager().setDirty(COLOR);
	}

	@Override
	protected float getGravityVelocity() {
		return 0.05F;
	}

	public int getColor() {
		return dataManager.get(COLOR);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote) {
			ItemStack itemstack = this.getBrew();
			BrewData data = BrewData.fromStack(itemstack);
			data.applyInWorld(world, result.hitVec.x, result.hitVec.y, result.hitVec.z, result.sideHit, this.getThrower());
			if (this.isLingering()) {
				EntityLingeringBrew entBrew = new EntityLingeringBrew(world, result.hitVec.x, result.hitVec.y, result.hitVec.z);
				entBrew.setBrew(itemstack);
				data.setupLingeringCloud(entBrew);
				entBrew.setOwner(getThrower());
				entBrew.setRadiusOnUse(0);
				world.spawnEntity(entBrew);
			} else {
				applySplash(result, data);
			}

			int i = data.getEffects().stream().filter(be -> be.getPotion() != null).map(be -> be.getPotion()).anyMatch(p -> p.isInstant()) ? 2007 : 2002;
			this.world.playEvent(i, new BlockPos(this), getColor());
			this.setDead();
		}
	}

	private void applySplash(RayTraceResult raytrace, BrewData data) {
		int size = (int) data.getEffects().stream().mapToDouble(be -> be.getModifierList().getLevel(DefaultModifiers.RADIUS).orElse(0)).average().orElse(0);
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().grow(size + 4.0D, size / 2 + 2.0D, size + 4.0D);
		List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);
		for (EntityLivingBase entitylivingbase : list) {
			if (entitylivingbase.canBeHitWithPotion()) {
				if (this.getDistanceSq(entitylivingbase) < 16.0D + size) {
					data.applyToEntity(entitylivingbase, this, getThrower(), ApplicationType.GENERAL);
				}
			}
		}
	}

	private boolean isLingering() {
		return this.getBrew().getItem() == ModItems.brew_phial_linger;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		ItemStack itemstack = new ItemStack(compound.getCompoundTag("Potion"));

		if (itemstack.isEmpty()) {
			this.setDead();
		} else {
			this.setItem(itemstack);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		ItemStack itemstack = this.getBrew();

		if (!itemstack.isEmpty()) {
			compound.setTag("Potion", itemstack.writeToNBT(new NBTTagCompound()));
		}
	}
}
