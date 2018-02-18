package com.bewitchment.common.entity;

import com.bewitchment.api.brew.BrewEffect;
import com.bewitchment.api.brew.BrewUtils;
import com.bewitchment.common.core.capability.brew.BrewStorageHandler;
import com.google.common.collect.Maps;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * This class was created by Arekkuusu on 08/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("WeakerAccess")
public class EntityBrewLinger extends Entity {

	private static final DataParameter<Boolean> IGNORE_RADIUS = EntityDataManager.createKey(EntityBrewLinger.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityBrewLinger.class, DataSerializers.VARINT);
	private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(EntityBrewLinger.class, DataSerializers.FLOAT);
	private final Map<Entity, Integer> reapplicationDelayMap = Maps.newHashMap();
	private Tuple<List<BrewEffect>, List<PotionEffect>> tuple;
	private ItemStack stack = ItemStack.EMPTY;
	private EntityLivingBase owner;
	private int reapplicationDelay;
	private UUID ownerUniqueId;
	private float radiusPerTick;
	private int durationOnUse;
	private float radiusOnUse;
	private int duration;
	private int waitTime;

	public EntityBrewLinger(World world, double x, double y, double z) {
		this(world);
		setPosition(x, y, z);
	}

	public EntityBrewLinger(World worldIn) {
		super(worldIn);
		duration = 600;
		waitTime = 10;
		reapplicationDelay = 20;
		noClip = true;
		isImmuneToFire = true;
		setRadius(3.0F);
		setRadiusOnUse(-0.5F);
	}

	public void setRadiusOnUse(float radiusOnUseIn) {
		radiusOnUse = radiusOnUseIn;
	}

	@Override
	protected void entityInit() {
		getDataManager().register(IGNORE_RADIUS, Boolean.FALSE);
		getDataManager().register(COLOR, 0);
		getDataManager().register(RADIUS, 0.5F);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (!isDead) {
			float radius = getRadius();

			if (world.isRemote) {
				if (shouldIgnoreRadius()) {
					if (rand.nextBoolean()) {
						for (int i = 0; i < 2; ++i) {
							float f1 = rand.nextFloat() * ((float) Math.PI * 2F);
							float f2 = MathHelper.sqrt(rand.nextFloat()) * 0.2F;
							float f3 = MathHelper.cos(f1) * f2;
							float f4 = MathHelper.sin(f1) * f2;

							int color = rand.nextBoolean() ? 16777215 : getColor();
							int r = color >> 16 & 255;
							int g = color >> 8 & 255;
							int b = color & 255;
							world.spawnAlwaysVisibleParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), posX + f3, posY, posZ + f4, r / 255.0F, g / 255.0F, b / 255.0F);
						}
					}
				} else {
					float rad = (float) Math.PI * radius * radius;

					for (int k1 = 0; k1 < rad; ++k1) {
						float f6 = rand.nextFloat() * ((float) Math.PI * 2F);
						float f7 = MathHelper.sqrt(rand.nextFloat()) * radius;
						float f8 = MathHelper.cos(f6) * f7;
						float f9 = MathHelper.sin(f6) * f7;

						int color = getColor();
						int r = color >> 16 & 255;
						int g = color >> 8 & 255;
						int b = color & 255;
						world.spawnAlwaysVisibleParticle(EnumParticleTypes.SPELL_MOB.getParticleID(), posX + f8, posY, posZ + f9, r / 255.0F, g / 255.0F, b / 255.0F);
					}
				}
			} else if (tuple != null) {
				if (ticksExisted >= waitTime + duration) {
					setDead();
					return;
				}

				boolean wait = ticksExisted < waitTime;

				if (shouldIgnoreRadius() != wait) {
					setIgnoreRadius(wait);
				}

				if (wait) {
					return;
				}

				if (radiusPerTick != 0.0F) {
					radius += radiusPerTick;

					if (radius < 0.5F) {
						setDead();
						return;
					}

					setRadius(radius);
				}

				if (ticksExisted % 5 == 0) {
					Iterator<Map.Entry<Entity, Integer>> iterator = reapplicationDelayMap.entrySet().iterator();

					while (iterator.hasNext()) {
						Map.Entry<Entity, Integer> entry = iterator.next();

						if (ticksExisted >= entry.getValue()) {
							iterator.remove();
						}
					}

					List<BrewEffect> brewEffects = tuple.getFirst();
					List<PotionEffect> potionEffects = tuple.getSecond();

					if (brewEffects.isEmpty() && potionEffects.isEmpty()) {
						reapplicationDelayMap.clear();
					} else {
						List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox());

						if (!list.isEmpty()) {
							for (EntityLivingBase entity : list) {
								if (!reapplicationDelayMap.containsKey(entity)) {
									double x = entity.posX - posX;
									double z = entity.posZ - posZ;
									double xz = x * x + z * z;

									if (xz <= radius * radius) {
										reapplicationDelayMap.put(entity, ticksExisted + reapplicationDelay);

										for (BrewEffect effect : tuple.getFirst()) {
											BrewStorageHandler.addEntityBrewEffect(entity, effect.copy());
										}

										for (PotionEffect potioneffect : tuple.getSecond()) {
											entity.addPotionEffect(new PotionEffect(potioneffect));
										}

										if (radiusOnUse != 0.0F) {
											radius += radiusOnUse;

											if (radius < 0.5F) {
												setDead();
												return;
											}

											setRadius(radius);
										}

										if (durationOnUse != 0) {
											duration += durationOnUse;

											if (duration <= 0) {
												setDead();
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
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		ticksExisted = compound.getInteger("Age");
		duration = compound.getInteger("Duration");
		waitTime = compound.getInteger("WaitTime");
		reapplicationDelay = compound.getInteger("ReapplicationDelay");
		durationOnUse = compound.getInteger("DurationOnUse");
		radiusOnUse = compound.getFloat("RadiusOnUse");
		radiusPerTick = compound.getFloat("RadiusPerTick");
		setRadius(compound.getFloat("Radius"));
		setColor(compound.getInteger("Color"));
		ownerUniqueId = compound.getUniqueId("OwnerUUID");

		ItemStack stack = new ItemStack(compound.getCompoundTag("Brew"));
		if (stack.isEmpty()) {
			this.setDead();
		} else {
			this.setBrew(stack);
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Age", ticksExisted);
		compound.setInteger("Duration", duration);
		compound.setInteger("WaitTime", waitTime);
		compound.setInteger("ReapplicationDelay", reapplicationDelay);
		compound.setInteger("DurationOnUse", durationOnUse);
		compound.setFloat("RadiusOnUse", radiusOnUse);
		compound.setFloat("RadiusPerTick", radiusPerTick);
		compound.setFloat("Radius", getRadius());
		compound.setInteger("Color", getColor());

		ItemStack stack = getBrew();
		if (!stack.isEmpty()) {
			compound.setTag("Brew", stack.writeToNBT(new NBTTagCompound()));
		}

		if (ownerUniqueId != null) {
			compound.setUniqueId("OwnerUUID", ownerUniqueId);
		}
	}

	@Override
	public EnumPushReaction getPushReaction() {
		return EnumPushReaction.IGNORE;
	}

	public ItemStack getBrew() {
		return stack;
	}

	public void setBrew(ItemStack stack) {
		if (!stack.isEmpty() && BrewUtils.hasBrewData(stack)) {
			tuple = BrewUtils.deSerialize(stack.getTagCompound());
		} else {
			tuple = null;
		}

		this.stack = stack;
	}

	public float getRadius() {
		return getDataManager().get(RADIUS);
	}

	public void setRadius(float radiusIn) {
		double d0 = posX;
		double d1 = posY;
		double d2 = posZ;
		setSize(radiusIn * 2.0F, 0.5F);
		setPosition(d0, d1, d2);

		if (!world.isRemote) {
			getDataManager().set(RADIUS, radiusIn);
		}
	}

	public boolean shouldIgnoreRadius() {
		return getDataManager().get(IGNORE_RADIUS);
	}

	public int getColor() {
		return getDataManager().get(COLOR);
	}

	protected void setIgnoreRadius(boolean ignoreRadius) {
		getDataManager().set(IGNORE_RADIUS, ignoreRadius);
	}

	public void setColor(int colorIn) {
		getDataManager().set(COLOR, colorIn);
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int durationIn) {
		duration = durationIn;
	}

	public void setRadiusPerTick(float radiusPerTickIn) {
		radiusPerTick = radiusPerTickIn;
	}

	public void setWaitTime(int waitTimeIn) {
		waitTime = waitTimeIn;
	}

	@Nullable
	public EntityLivingBase getOwner() {
		if (owner == null && ownerUniqueId != null && world instanceof WorldServer) {
			Entity entity = ((WorldServer) world).getEntityFromUuid(ownerUniqueId);

			if (entity instanceof EntityLivingBase) {
				owner = (EntityLivingBase) entity;
			}
		}

		return owner;
	}

	public void setOwner(@Nullable EntityLivingBase ownerIn) {
		owner = ownerIn;
		ownerUniqueId = ownerIn == null ? null : ownerIn.getUniqueID();
	}
}
