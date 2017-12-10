package com.witchcraft.common.entity;

import com.witchcraft.api.brew.BrewEffect;
import com.witchcraft.api.brew.BrewUtils;
import com.witchcraft.api.brew.IBrewEntityImpact;
import com.witchcraft.api.helper.NBTHelper;
import com.witchcraft.common.core.capability.brew.BrewStorageHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

/**
 * This class was created by Arekkuusu on 22/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class EntityBrew extends EntityThrowable {

	private static final DataParameter<ItemStack> ITEM = EntityDataManager.createKey(EntityBrew.class, DataSerializers.ITEM_STACK);
	private BrewDispersion dispersion;

	public EntityBrew(World worldIn) {
		super(worldIn);
	}

	public EntityBrew(World worldIn, double x, double y, double z, ItemStack stack, BrewDispersion dispersion) {
		super(worldIn, x, y, z);
		this.dispersion = dispersion;
		setBrew(stack);
	}

	public EntityBrew(World worldIn, EntityLivingBase living, ItemStack stack, BrewDispersion dispersion) {
		super(worldIn, living);
		this.dispersion = dispersion;
		setBrew(stack);
	}

	@Override
	protected void entityInit() {
		getDataManager().register(ITEM, ItemStack.EMPTY);
	}

	@Override
	protected void onImpact(RayTraceResult result) {
		if (!world.isRemote && !isDead) {
			if (!getBrew().isEmpty() && getBrew().hasTagCompound()) {
				impact(result);

				switch (dispersion) {
					case SPLASH:
						doSplash();
						break;
					case LINGER:
						doLinger();
						break;
					default:
				}
			}

			world.playSound(null, getPosition(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.NEUTRAL, 0.2F, 1F);
			world.playEvent(2007, getPosition(), getColor());
			setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setString("dispersion", dispersion.name());

		ItemStack stack = getBrew();
		if (!stack.isEmpty()) {
			compound.setTag("Brew", stack.writeToNBT(new NBTTagCompound()));
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		dispersion = BrewDispersion.valueOf(compound.getString("dispersion"));

		ItemStack stack = new ItemStack(compound.getCompoundTag("Brew"));
		if (stack.isEmpty()) {
			this.setDead();
		} else {
			this.setBrew(stack);
		}
	}

	public ItemStack getBrew() {
		return getDataManager().get(ITEM);
	}

	public void setBrew(ItemStack stack) {
		getDataManager().set(ITEM, stack);
		getDataManager().setDirty(ITEM);
	}

	private void impact(RayTraceResult result) {
		playSound(SoundEvents.ENTITY_SPLASH_POTION_BREAK, 1F, 1F);
		List<BrewEffect> brewEffects = BrewUtils.getBrewsFromStack(getBrew());

		brewEffects.stream().filter(brewEffect -> brewEffect.getBrew() instanceof IBrewEntityImpact).forEach(brewEffect ->
				((IBrewEntityImpact) brewEffect.getBrew()).impact(result, world, brewEffect.getAmplifier())
		);
	}

	private void doSplash() {
		AxisAlignedBB axisalignedbb = this.getEntityBoundingBox().expand(4.0D, 2.0D, 4.0D);
		List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb);

		if (!list.isEmpty()) {
			Tuple<List<BrewEffect>, List<PotionEffect>> tuple = BrewUtils.deSerialize(NBTHelper.fixNBT(getBrew()));

			for (EntityLivingBase entity : list) {
				double distance = this.getDistanceSq(entity);

				if (distance < 16.0D) {
					for (BrewEffect effect : tuple.getFirst()) {
						BrewStorageHandler.addEntityBrewEffect(entity, effect.copy());
					}

					for (PotionEffect potioneffect : tuple.getSecond()) {
						entity.addPotionEffect(new PotionEffect(potioneffect));
					}
				}
			}
		}
	}

	private void doLinger() {
		EntityBrewLinger linger = new EntityBrewLinger(world, posX, posY, posZ);
		linger.setBrew(getBrew());
		linger.setOwner(getThrower());
		linger.setColor(getColor());
		linger.setRadiusPerTick(-linger.getRadius() / (float) linger.getDuration());

		world.spawnEntity(linger);
	}

	public int getColor() {
		return NBTHelper.getInteger(getBrew(), BrewUtils.BREW_COLOR);
	}

	public enum BrewDispersion {
		SPLASH,
		LINGER,
		GAS
	}
}
