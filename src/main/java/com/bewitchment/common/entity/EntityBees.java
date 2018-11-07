package com.bewitchment.common.entity;

import com.bewitchment.common.block.ModBlocks;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class EntityBees extends Entity {

	private static final int RADIUS = 7;

	@SideOnly(Side.CLIENT)
	private ArrayList<Particle> beesParticles = Lists.newArrayList();

	private int ttl;

	public EntityBees(World worldIn) {
		super(worldIn);
		this.setSize(5, 5);
		this.setNoGravity(true);
	}

	public EntityBees(World worldIn, int lifeSpan, BlockPos pos) {
		this(worldIn);
		this.ttl = lifeSpan;
		this.setPosition(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d);
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		if (this.ticksExisted > this.ttl) {
			this.lookForNewHive();
			this.setDead();
		}
		if (this.ticksExisted % 60 == 0) {
			this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox()).stream()
					.peek(elb -> ttl -= 10)
					.forEach(elb -> elb.attackEntityFrom(DamageSource.GENERIC, 1f));
		}
	}

	@SideOnly(Side.CLIENT)
	public List<Particle> getParticles() {
		return beesParticles;
	}

	private void lookForNewHive() {
		MutableBlockPos pos = new MutableBlockPos();
		for (int i = -RADIUS; i <= RADIUS; i++) {
			for (int j = -RADIUS; j <= RADIUS; j++) {
				for (int k = -RADIUS; k <= RADIUS; k++) {
					pos.setPos(posX + i, posY + j, posZ + k);
					IBlockState bs = world.getBlockState(pos);
					if (bs.getBlock().isLeaves(bs, world, pos) && world.getBlockState(pos.down()).getBlock().isReplaceable(world, pos.down())) {
						if (world.setBlockState(pos.down(), ModBlocks.beehive.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.getHorizontal(rand.nextInt(4))), 3)) {
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public boolean canBeAttackedWithItem() {
		return false;
	}

	@Override
	public boolean canBePushed() {
		return false;
	}

	@Override
	public boolean canBeCollidedWith() {
		return false;
	}

	@Override
	public boolean canRenderOnFire() {
		return false;
	}

	@Override
	protected boolean canTriggerWalking() {
		return false;
	}

	@Override
	protected boolean canFitPassenger(Entity passenger) {
		return false;
	}

	@Override
	public boolean hitByEntity(Entity entityIn) {
		return true;
	}

	@Override
	protected void entityInit() {
		this.noClip = true;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound tag) {
		this.ttl = tag.getInteger("ttl");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound tag) {
		tag.setInteger("ttl", this.ttl);
	}

}
