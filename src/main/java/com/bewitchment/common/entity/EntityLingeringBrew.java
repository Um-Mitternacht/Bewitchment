package com.bewitchment.common.entity;

import com.bewitchment.common.content.cauldron.BrewData;
import com.bewitchment.common.content.cauldron.BrewData.ApplicationType;
import com.google.common.collect.Maps;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

public class EntityLingeringBrew extends Entity {
	private static final DataParameter<Float> RADIUS = EntityDataManager.<Float>createKey(EntityLingeringBrew.class, DataSerializers.FLOAT);
	private static final DataParameter<Integer> COLOR = EntityDataManager.<Integer>createKey(EntityLingeringBrew.class, DataSerializers.VARINT);
	private static final DataParameter<ItemStack> BREW = EntityDataManager.<ItemStack>createKey(EntityLingeringBrew.class, DataSerializers.ITEM_STACK);
	private final Map<Entity, Integer> reapplicationDelayMap;
	private int duration;
	private int waitTime;
	private int reapplicationDelay;
	private int durationOnUse;
	private float radiusOnUse;
	private float radiusPerTick;
	private EntityLivingBase owner;
	private UUID ownerUniqueId;

	public EntityLingeringBrew(World worldIn) {
		super(worldIn);
		this.reapplicationDelayMap = Maps.<Entity, Integer>newHashMap();
		this.duration = 600;
		this.waitTime = 20;
		this.reapplicationDelay = 20;
		this.noClip = true;
		this.isImmuneToFire = true;
		this.setRadius(3.0F);
	}

	public EntityLingeringBrew(World worldIn, double x, double y, double z) {
		this(worldIn);
		this.setPosition(x, y, z);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(COLOR, Integer.valueOf(0));
		this.getDataManager().register(RADIUS, Float.valueOf(0.5F));
		this.getDataManager().register(BREW, ItemStack.EMPTY);
	}

	public EntityLingeringBrew setRadius(float radiusIn) {
		double d0 = this.posX;
		double d1 = this.posY;
		double d2 = this.posZ;
		this.setSize(radiusIn * 2.0F, 0.5F);
		this.setPosition(d0, d1, d2);

		if (!this.world.isRemote) {
			this.getDataManager().set(RADIUS, Float.valueOf(radiusIn));
		}
		return this;
	}

	public float getRadius() {
		return this.getDataManager().get(RADIUS).floatValue();
	}

	public EntityLingeringBrew setBrew(ItemStack stack) {
		this.dataManager.set(BREW, stack);
		this.dataManager.setDirty(BREW);
		this.dataManager.set(COLOR, BrewData.fromStack(stack).getColor());
		this.dataManager.setDirty(COLOR);
		return this;
	}

	public int getColor() {
		return this.getDataManager().get(COLOR).intValue();
	}

	public int getDuration() {
		return this.duration;
	}

	public EntityLingeringBrew setDuration(int durationIn) {
		this.duration = durationIn;
		return this;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		float f = this.getRadius();

		if (this.world.isRemote) {
			float f5 = (float) Math.PI * f * f;
			for (int k1 = 0; k1 < f5; ++k1) {
				float f6 = this.rand.nextFloat() * ((float) Math.PI * 2F);
				float f7 = MathHelper.sqrt(this.rand.nextFloat()) * f;
				float f8 = MathHelper.cos(f6) * f7;
				float f9 = MathHelper.sin(f6) * f7;
				int l1 = this.getColor();
				int i2 = l1 >> 16 & 255;
				int j2 = l1 >> 8 & 255;
				int j1 = l1 & 255;
				this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + f8, this.posY, this.posZ + f9, i2 / 255.0F, j2 / 255.0F, j1 / 255.0F);
			}
		} else {
			if (this.ticksExisted >= this.waitTime + this.duration) {
				this.setDead();
				return;
			}

			if (this.ticksExisted < this.waitTime) {
				return;
			}

			if (this.radiusPerTick != 0.0F) {
				f += this.radiusPerTick;

				if (f < 0.5F) {
					this.setDead();
					return;
				}

				this.setRadius(f);
			}
			BrewData data = BrewData.fromStack(dataManager.get(BREW));
			if (this.ticksExisted % 5 == 0) {
				Iterator<Entry<Entity, Integer>> iterator = this.reapplicationDelayMap.entrySet().iterator();

				while (iterator.hasNext()) {
					Entry<Entity, Integer> entry = iterator.next();

					if (this.ticksExisted >= entry.getValue().intValue()) {
						iterator.remove();
					}
				}

				List<EntityLivingBase> list = this.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox());

				if (!list.isEmpty()) {
					for (EntityLivingBase entitylivingbase : list) {
						if (!this.reapplicationDelayMap.containsKey(entitylivingbase) && entitylivingbase.canBeHitWithPotion()) {
							double d0 = entitylivingbase.posX - this.posX;
							double d1 = entitylivingbase.posZ - this.posZ;
							double d2 = d0 * d0 + d1 * d1;

							if (d2 <= f * f) {
								this.reapplicationDelayMap.put(entitylivingbase, Integer.valueOf(this.ticksExisted + this.reapplicationDelay));

								data.applyToEntity(entitylivingbase, this, this.getOwner(), ApplicationType.LINGERING);

								if (this.radiusOnUse != 0.0F) {
									f += this.radiusOnUse;

									if (f < 0.5F) {
										this.setDead();
										return;
									}

									this.setRadius(f);
								}

								if (this.durationOnUse != 0) {
									this.duration += this.durationOnUse;

									if (this.duration <= 0) {
										this.setDead();
										return;
									}
								}
							}
						}
					}
				}
			}
		}
	}

	public EntityLingeringBrew setRadiusOnUse(float radiusOnUseIn) {
		this.radiusOnUse = radiusOnUseIn;
		return this;
	}

	public EntityLingeringBrew setRadiusPerTick(float radiusPerTickIn) {
		this.radiusPerTick = radiusPerTickIn;
		return this;
	}

	public EntityLingeringBrew setWaitTime(int waitTimeIn) {
		this.waitTime = waitTimeIn;
		return this;
	}

	public EntityLingeringBrew setOwner(@Nullable EntityLivingBase ownerIn) {
		this.owner = ownerIn;
		this.ownerUniqueId = ownerIn == null ? null : ownerIn.getUniqueID();
		return this;
	}

	@Nullable
	public EntityLivingBase getOwner() {
		if (this.owner == null && this.ownerUniqueId != null && this.world instanceof WorldServer) {
			Entity entity = ((WorldServer) this.world).getEntityFromUuid(this.ownerUniqueId);
			if (entity instanceof EntityLivingBase) {
				this.owner = (EntityLivingBase) entity;
			}
		}
		return this.owner;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		this.ticksExisted = compound.getInteger("Age");
		this.duration = compound.getInteger("Duration");
		this.waitTime = compound.getInteger("WaitTime");
		this.reapplicationDelay = compound.getInteger("ReapplicationDelay");
		this.durationOnUse = compound.getInteger("DurationOnUse");
		this.radiusOnUse = compound.getFloat("RadiusOnUse");
		this.radiusPerTick = compound.getFloat("RadiusPerTick");
		this.setRadius(compound.getFloat("Radius"));
		this.ownerUniqueId = compound.getUniqueId("OwnerUUID");
		setBrew(new ItemStack(compound.getCompoundTag("brew")));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Age", this.ticksExisted);
		compound.setInteger("Duration", this.duration);
		compound.setInteger("WaitTime", this.waitTime);
		compound.setInteger("ReapplicationDelay", this.reapplicationDelay);
		compound.setInteger("DurationOnUse", this.durationOnUse);
		compound.setFloat("RadiusOnUse", this.radiusOnUse);
		compound.setFloat("RadiusPerTick", this.radiusPerTick);
		compound.setFloat("Radius", this.getRadius());
		compound.setTag("brew", dataManager.get(BREW).writeToNBT(new NBTTagCompound()));
		if (this.ownerUniqueId != null) {
			compound.setUniqueId("OwnerUUID", this.ownerUniqueId);
		}

	}

	@Override
	public void notifyDataManagerChange(DataParameter<?> key) {
		if (RADIUS.equals(key)) {
			this.setRadius(this.getRadius());
		}

		super.notifyDataManagerChange(key);
	}

	@Override
	public EnumPushReaction getPushReaction() {
		return EnumPushReaction.IGNORE;
	}
}
