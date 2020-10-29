package com.bewitchment.common.entity.misc;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySilverArrow extends EntityArrow {
	public static final DataParameter<Boolean> COLD_IRON = EntityDataManager.createKey(EntitySilverArrow.class, DataSerializers.BOOLEAN);

	public EntitySilverArrow(World world) {
		super(world);
	}

	public EntitySilverArrow(World worldIn, EntityLivingBase shooter, boolean coldIron) {
		super(worldIn, shooter);
		setColdIron(coldIron);
	}

	@Override
	protected void entityInit() {
		dataManager.register(COLD_IRON, false);
		super.entityInit();
	}

	@Override
	protected void arrowHit(EntityLivingBase living) {
		float weakness = isColdIron() ? BewitchmentAPI.COLD_IRON_WEAKNESS.get(living) : BewitchmentAPI.SILVER_WEAKNESS.get(living);
		if (weakness > 1.0F) living.attackEntityFrom(DamageSource.MAGIC, weakness);
		super.arrowHit(living);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("isColdIron", isColdIron());
		super.writeEntityToNBT(compound);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		setColdIron(compound.getBoolean("isColdIron"));
		super.readEntityFromNBT(compound);
	}

	@Override
	protected ItemStack getArrowStack() {
		return new ItemStack(isColdIron() ? ModObjects.cold_iron_arrow : ModObjects.silver_arrow);
	}

	public boolean isColdIron() {
		return dataManager.get(COLD_IRON);
	}

	public void setColdIron(boolean coldIron) {
		this.dataManager.set(COLD_IRON, coldIron);
	}
}