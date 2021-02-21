package com.bewitchment.api.registry.entity;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.capability.magicpower.MagicPower;
import com.bewitchment.api.message.SyncBroom;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.lang.reflect.Field;

@SuppressWarnings({"NullableProblems", "deprecation"})
public abstract class EntityBroom extends Entity {
	private static final Field jumping = ReflectionHelper.findField(EntityLivingBase.class, "isJumping", "field_70703_bu");

	public ItemStack item;
	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private double lerpYaw;
	private double lerpPitch;
	private boolean canFly = true;

	public EntityBroom(World world) {
		super(world);
		setSize(0.7f, 0.7f);
		setNoGravity(true);
	}

	@Override
	protected void entityInit() {
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		super.onUpdate();
		this.tickLerp();
		Entity rider = getControllingPassenger();
		if (rider instanceof EntityPlayer) {
			rotationYaw = rider.rotationYaw;
			boolean jump = false;
			try {
				jump = jumping.getBoolean(rider);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (world.getTotalWorldTime() % 20 == 0 && (!onGround || jump))
				canFly = MagicPower.attemptDrain(null, (EntityPlayer) rider, getMagicCost());
			if (canFly) {
				if (jump && motionY < 1) motionY += (0.1f + getThrust());
			}
			motionX += rider.motionX * getSpeed();
			motionZ += rider.motionZ * getSpeed();
		}
		float friction = 0.98f;
		if (onGround) friction = 0.4f;
		motionX *= friction;
		motionZ *= friction;
		if (collidedHorizontally) {
			motionX = 0;
			motionZ = 0;
		}
		motionY -= 0.1f;
		motionY *= 0.75f;
		motionX = MathHelper.clamp(motionX, -getMaxSpeed(), getMaxSpeed());
		motionZ = MathHelper.clamp(motionZ, -getMaxSpeed(), getMaxSpeed());
		move(MoverType.SELF, motionX, motionY, motionZ);
		this.markVelocityChanged();
		for (EntityPlayer player : world.playerEntities) {
			if (!world.isRemote && player instanceof EntityPlayerMP && player != getControllingPassenger())
				Bewitchment.network.sendTo(new SyncBroom(this), (EntityPlayerMP) player);
		}
	}

	@Override
	protected void addPassenger(Entity passenger) {
		super.addPassenger(passenger);
		if (this.canPassengerSteer() && this.lerpSteps > 0) {
			this.lerpSteps = 0;
			this.posX = this.lerpX;
			this.posY = this.lerpY;
			this.posZ = this.lerpZ;
			this.rotationYaw = (float) this.lerpYaw;
			this.rotationPitch = (float) this.lerpPitch;
		}
	}

	private void tickLerp() {
		if (this.lerpSteps > 0 && !this.canPassengerSteer()) {
			double newX = this.posX + ((this.lerpX - this.posX) / (double) this.lerpSteps);
			double newY = this.posY + ((this.lerpY - this.posY) / (double) this.lerpSteps);
			double newZ = this.posZ + ((this.lerpZ - this.posZ) / (double) this.lerpSteps);
			double deltaYaw = MathHelper.wrapDegrees(this.lerpYaw - (double) this.rotationYaw);
			this.rotationYaw = (float) ((double) this.rotationYaw + (deltaYaw / (double) this.lerpSteps));
			this.rotationPitch = (float) ((double) this.rotationPitch + ((this.lerpPitch - (double) this.rotationPitch) / (double) this.lerpSteps));
			this.lerpSteps--;
			this.setPosition(newX, newY, newZ);
			this.setRotation(this.rotationYaw, this.rotationPitch);
		}
	}

	@Override
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYaw = yaw;
		this.lerpPitch = pitch;
		this.lerpSteps = 10;
	}

	@Override
	protected void updateFallState(double y, boolean onGround, IBlockState state, BlockPos pos) {
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (source.getImmediateSource() != null && getControllingPassenger() == null) {
			setDead();
			if (!world.isRemote) InventoryHelper.spawnItemStack(world, posX, posY, posZ, item.copy());
			return true;
		}
		return super.attackEntityFrom(source, amount);
	}

	@Override
	public boolean canBeCollidedWith() {
		return !isDead;
	}

	@Override
	public boolean canBePushed() {
		return canBeCollidedWith();
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		item = tag.hasKey("item") ? new ItemStack(tag.getCompoundTag("item")) : ItemStack.EMPTY;
		canFly = tag.getBoolean("canFly");
		motionX = tag.getDouble("motionX");
		motionY = tag.getDouble("motionY");
		motionZ = tag.getDouble("motionZ");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		if (item != null) tag.setTag("item", item.serializeNBT());
		tag.setBoolean("canFly", canFly);
		tag.setDouble("motionX", motionX);
		tag.setDouble("motionY", motionY);
		tag.setDouble("motionZ", motionZ);
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (!world.isRemote && !isBeingRidden()) {
			player.startRiding(this);
			return true;
		}
		return super.processInitialInteract(player, hand);
	}

	@Override
	public double getMountedYOffset() {
		return 0.4;
	}

	@Override
	public Entity getControllingPassenger() {
		return getPassengers().isEmpty() ? null : getPassengers().get(0);
	}

	@Override
	public boolean canPassengerSteer() {
		return true;
	}

	private Vec3d getRiderPosition() {
		if (!this.getPassengers().isEmpty()) {
			float distance = 0F;

			double dx = Math.cos((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;
			double dz = Math.sin((this.rotationYaw + 90) * Math.PI / 180.0D) * distance;

			return new Vec3d(this.posX + dx, this.posY + this.getMountedYOffset() + this.getPassengers().get(0).getYOffset(), this.posZ + dz);
		} else {
			return new Vec3d(this.posX, this.posY, this.posZ);
		}
	}

	@Override
	public void updatePassenger(Entity passenger) {
		if (!this.getPassengers().isEmpty()) {
			Vec3d riderPos = this.getRiderPosition();

			this.getPassengers().get(0).setPosition(riderPos.x, riderPos.y, riderPos.z);
		}
	}

	@Override
	public boolean canRiderInteract() {
		return true;
	}

	protected abstract float getSpeed();

	protected abstract float getMaxSpeed();

	protected abstract float getThrust();

	protected abstract int getMagicCost();
}