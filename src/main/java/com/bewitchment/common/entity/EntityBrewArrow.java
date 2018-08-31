package com.bewitchment.common.entity;

import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.cauldron.BrewData.ApplicationType;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityBrewArrow extends EntityArrow {

	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityBrewArrow.class, DataSerializers.VARINT);
	private static final DataParameter<ItemStack> ARROW = EntityDataManager.<ItemStack>createKey(EntityBrewArrow.class, DataSerializers.ITEM_STACK);

	public EntityBrewArrow(World worldIn) {
		super(worldIn);
	}

	public EntityBrewArrow(World worldIn, EntityLivingBase shooter) {
		super(worldIn, shooter);
	}

	public EntityBrewArrow(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(COLOR, Integer.valueOf(-1));
		this.dataManager.register(ARROW, ItemStack.EMPTY);
	}

	@Override
	protected ItemStack getArrowStack() {
		return this.getDataManager().get(ARROW);
	}

	public void setArrowStack(ItemStack arrow) {
		dataManager.set(COLOR, BrewData.fromStack(arrow).getColor());
		dataManager.set(ARROW, arrow);
		dataManager.setDirty(COLOR);
		dataManager.setDirty(ARROW);
	}

	public int getColor() {
		return this.dataManager.get(COLOR).intValue();
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (this.world.isRemote) {
			if (this.inGround) {
				if (this.timeInGround % 5 == 0) {
					this.spawnPotionParticles(1);
				}
			} else {
				this.spawnPotionParticles(2);
			}
		}
	}

	private void spawnPotionParticles(int particleCount) {
		int i = this.getColor();
		if (i != -1 && particleCount > 0) {
			double d0 = (i >> 16 & 255) / 255.0D;
			double d1 = (i >> 8 & 255) / 255.0D;
			double d2 = (i >> 0 & 255) / 255.0D;

			for (int j = 0; j < particleCount; ++j) {
				this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2);
			}
		}
	}

	@Override
	protected void arrowHit(EntityLivingBase living) {
		BrewData.fromStack(dataManager.get(ARROW)).applyToEntity(living, this, this.shootingEntity, ApplicationType.ARROW);
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setTag("arrow", dataManager.get(ARROW).writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setArrowStack(new ItemStack(compound.getCompoundTag("arrow")));
	}

}
