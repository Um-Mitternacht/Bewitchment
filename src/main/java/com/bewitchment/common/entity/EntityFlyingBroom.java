package com.bewitchment.common.entity;

import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.core.util.DimensionalPosition;
import com.bewitchment.common.item.ModItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityMountEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Arrays;

@Mod.EventBusSubscriber
public class EntityFlyingBroom extends Entity {

	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityFlyingBroom.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FUEL = EntityDataManager.<Integer>createKey(EntityFlyingBroom.class, DataSerializers.VARINT); //ONLY SYNCHRONIZED WHEN EMPTY OR FULL
	private static final int MAX_FUEL = 100;
	Field isJumping = ReflectionHelper.findField(EntityLivingBase.class, "field_70703_bu", "isJumping");
	private DimensionalPosition orig_position;

	public EntityFlyingBroom(World world) {
		super(world);
		this.setSize(1f, 1);
	}

	public EntityFlyingBroom(World world, double x, double y, double z, int type) {
		this(world);
		this.setPosition(x, y, z);
		this.setType(type);
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
	}

	@SubscribeEvent(receiveCanceled = false, priority = EventPriority.LOWEST)
	public static void onUnmounting(EntityMountEvent evt) {
		if (evt.getEntityBeingMounted() instanceof EntityFlyingBroom && evt.isDismounting()) {
			EntityFlyingBroom broom = (EntityFlyingBroom) evt.getEntityBeingMounted();
			EntityPlayer source = (EntityPlayer) evt.getEntityMounting();
			if (!source.isDead) {
				if (broom.getType() == 3) {
					BlockPos start = broom.getMountPos();
					if (broom.world.provider.getDimension() == broom.getMountDim()) {
						broom.setPositionAndUpdate(start.getX(), start.getY(), start.getZ());
						source.setPositionAndUpdate(start.getX(), start.getY(), start.getZ());
					}
				}
				if (!broom.world.isRemote) { // TODO check for owl
					EntityItem ei = new EntityItem(broom.world, source.posX, source.posY, source.posZ, new ItemStack(ModItems.broom, 1, broom.getType()));
					broom.world.spawnEntity(ei);
					broom.setDead();
					ei.onCollideWithPlayer(source);
				}
			}
		}
	}

	@Override
	public double getMountedYOffset() {
		return 0.4;
	}

	private BlockPos getMountPos() {
		return orig_position == null ? null : orig_position.getPosition();
	}

	private int getMountDim() {
		return orig_position == null ? 0 : orig_position.getDim();
	}

	public void setMountPos(BlockPos pos, int dim) {
		orig_position = new DimensionalPosition(pos, dim);
	}

	@Override
	protected void entityInit() {
		this.getDataManager().register(TYPE, 0);
		this.getDataManager().register(FUEL, 0);
		this.setEntityBoundingBox(new AxisAlignedBB(getPosition()).contract(0, 1, 0));
	}

	@Override
	protected boolean canBeRidden(Entity entityIn) {
		return entityIn instanceof EntityPlayer;
	}

	@Override
	public boolean canBePushed() {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		this.doBlockCollisions();
		int broomType = getType();
		float friction = broomType == 0 ? 0.99f : 0.98f;
		if (onGround) {
			friction = 0.8f;
		}
		this.motionX *= friction;
		this.motionY *= friction;
		this.motionZ *= friction;
		EntityPlayer rider = (EntityPlayer) this.getControllingPassenger();

		if (this.isBeingRidden()) {
			if (rider == null) {
				Bewitchment.logger.warn(this + " is being ridden by a null rider!");
				return;
			}
			if (getDataManager().get(FUEL) < 5) {
				refuel(rider);
			}
			float front = rider.moveForward, strafe = rider.moveStrafing, up = 0;
			try {
				up = isJumping.getBoolean(rider) ? 1 : 0;
			} catch (Exception e) {
				e.printStackTrace();
			}
			Vec3d look = rider.getLookVec();
			if (broomType == 1) {
				handleElderMovement(front, up, strafe, look);
				this.motionY -= 0.005;
			} else if (broomType == 2) {
				handleJuniperMovement(front, up, strafe, look);
			} else if (broomType == 3) {
				handleYewMovement(front, up, strafe, look);
			} else if (broomType == 4) {
				handleCypressMovement(front, up, strafe, look);
			}
			this.setRotationYawHead(rider.rotationYaw);

		} else {
			if (!this.collidedVertically) {
				motionY -= 0.009;
				if (motionY < -0.5) motionY = -0.5;
			}
		}

		if (this.collidedHorizontally) {
			if (this.prevPosX == this.posX) motionX = 0;
			if (this.prevPosZ == this.posZ) motionZ = 0;
		}
		if (this.collidedVertically) {
			if (this.prevPosY == this.posY) motionY = 0;
		}
		if (this.isBeingRidden()) {
			this.setSize(1f, 2f);// If a player is riding, account for the height of the player
		}
		this.move(MoverType.SELF, motionX, motionY, motionZ);
		if (this.isBeingRidden()) {
			this.setSize(1f, 1f);
		}
	}

	private void refuel(EntityPlayer rider) {
		IMagicPowerContainer pmp = rider.getCapability(IMagicPowerContainer.CAPABILITY, null);
		if (pmp.drain(30)) {
			getDataManager().set(FUEL, MAX_FUEL);
			getDataManager().setDirty(FUEL);
		}
	}

	private void handleCypressMovement(float front, float up, float strafe, Vec3d look) {
		handleMundaneMovement(front, look);
	}

	private void handleYewMovement(float front, float up, float strafe, Vec3d look) {
		if (getDataManager().get(FUEL) > 1) {
			getDataManager().set(FUEL, getDataManager().get(FUEL) - 2);
			if (getDataManager().get(FUEL) < 5) {
				getDataManager().setDirty(FUEL);
			}
			handleMundaneMovement(front, look);
		}
	}

	private void handleJuniperMovement(float front, float up, float strafe, Vec3d look) {
		if (getDataManager().get(FUEL) > 0) {
			getDataManager().set(FUEL, getDataManager().get(FUEL) - 1);
			if (getDataManager().get(FUEL) < 5) {
				getDataManager().setDirty(FUEL);
			}
			if (front >= 0) {
				Vec3d horAxis = look.crossProduct(new Vec3d(0, 1, 0)).normalize().scale(-strafe / 10);
				motionX += front * (horAxis.x + look.x) / 80;
				motionZ += front * (horAxis.z + look.z) / 80;
				motionY += (up / 80 + front * (horAxis.y + look.y) / 80);

				if (motionX * motionX + motionY * motionY + motionZ * motionZ > 1) {
					Vec3d limit = new Vec3d(motionX, motionY, motionZ).normalize().scale(2);
					motionX = limit.x;
					motionY = limit.y;
					motionZ = limit.z;
				}
			} else {
				motionX /= 1.1;
				motionY /= 1.1;
				motionZ /= 1.1;
			}
		} else {
			motionY -= 0.002;
		}
	}

	private void handleElderMovement(float front, float up, float strafe, Vec3d look) {
		if (getDataManager().get(FUEL) > 0) {
			getDataManager().set(FUEL, getDataManager().get(FUEL) - 1);
			if (getDataManager().get(FUEL) < 5) {
				getDataManager().setDirty(FUEL);
			}
			Vec3d horAxis = look.crossProduct(new Vec3d(0, 1, 0)).normalize().scale(-strafe / 10);
			motionX += front * (horAxis.x + look.x) / 20;
			motionZ += front * (horAxis.z + look.z) / 20;
			motionY += (up / 60) - 0.005;

			if (motionX * motionX + motionY * motionY + motionZ * motionZ > 8) {
				Vec3d limit = new Vec3d(motionX, motionY, motionZ).normalize().scale(2);
				motionX = limit.x;
				motionY = limit.y;
				motionZ = limit.z;
			}
		}
	}

	@Override
	public void setRotationYawHead(float rotation) {
		this.prevRotationYaw = this.rotationYaw;
		this.rotationYaw = rotation;
	}

	private void handleMundaneMovement(float front, Vec3d look) {
		if (front >= 0) {
			motionX += (0.1) * look.x / 8;
			motionY += (0.1) * look.y / 8;
			motionZ += (0.1) * look.z / 8;
		}

		if (motionX * motionX + motionZ * motionZ > 1) {
			Vec3d limit = new Vec3d(motionX, 0, motionZ).normalize();
			motionX = limit.x;
			motionZ = limit.z;
		}
	}

	private void setType(int type) {
		this.getDataManager().set(TYPE, type);
		this.getDataManager().setDirty(TYPE);
	}

	public int getType() {
		return this.getDataManager().get(TYPE);
	}

	@Nullable
	@Override
	public Entity getControllingPassenger() {
		if (this.getPassengers().size() == 0) return null;
		return this.getPassengers().get(0);
	}

	@Override
	public boolean canPassengerSteer() {
		return true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.setLocationAndAngles(tag.getDouble("x"), tag.getDouble("y"), tag.getDouble("z"), tag.getFloat("yaw"), tag.getFloat("pitch"));
		this.setType(tag.getInteger("type"));
		if (tag.hasKey("orig")) {
			this.orig_position = new DimensionalPosition(tag.getCompoundTag("orig"));
		}
		getDataManager().set(FUEL, tag.getInteger("fuel"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setDouble("x", this.posX);
		compound.setDouble("y", this.posY);
		compound.setDouble("z", this.posZ);
		compound.setFloat("yaw", this.rotationYaw);
		compound.setFloat("pitch", this.rotationPitch);
		compound.setInteger("type", getType());
		if (this.orig_position != null) {
			compound.setTag("orig", this.orig_position.writeToNBT());
		}
		compound.setInteger("fuel", getDataManager().get(FUEL));
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (isEntityInvulnerable(source)) {
			return false;
		}
		if (getControllingPassenger() != null) {
			return false;
		}
		if (!world.isRemote && source.getTrueSource() instanceof EntityPlayer) {
			EntityItem ei = new EntityItem(world, source.getTrueSource().posX, source.getTrueSource().posY, source.getTrueSource().posZ, new ItemStack(ModItems.broom, 1, getType()));
			world.spawnEntity(ei);
			this.setDead();
			ei.onCollideWithPlayer((EntityPlayer) source.getTrueSource());
			return true;
		}
		return false;
	}

	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		if (!player.isRiding() && !player.isSneaking()) {
			player.rotationYaw = this.rotationYaw;
			player.rotationPitch = this.rotationPitch;
			player.startRiding(this);
			if (getType() == 3) {
				this.setMountPos(player.getPosition(), player.world.provider.getDimension());
			}
			return EnumActionResult.SUCCESS;
		}
		return EnumActionResult.PASS;
	}

	@Override
	public Iterable<ItemStack> getArmorInventoryList() {
		return Arrays.asList(ItemStack.EMPTY);
	}

	@Override
	public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
	}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		//No fall
	}
}
