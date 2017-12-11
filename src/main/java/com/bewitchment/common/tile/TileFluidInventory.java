package com.bewitchment.common.tile;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.TileFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * This class was created by Arekkuusu on 01/05/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public abstract class TileFluidInventory extends TileFluidHandler {

	public TileFluidInventory() {
		tank = createFluidHandler();
	}

	private CauldronFluid createFluidHandler() {
		return new CauldronFluid(this, Fluid.BUCKET_VOLUME);
	}

	abstract void onLiquidChange();

	void play(SoundEvent events, float volume, float pitch) {
		world.playSound(null, getPos(), events, SoundCategory.BLOCKS, volume, pitch);
	}

	void particleServerSide(EnumParticleTypes particle, double x, double y, double z, double xOffset, double yOffset, double zOffset, int count) {
		if (world instanceof WorldServer) {
			BlockPos pos = getPos();
			((WorldServer) world).spawnParticle(particle, pos.getX() + x, pos.getY() + y, pos.getZ() + z, count, xOffset, yOffset, zOffset, 0D);
		}
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(getPos(), 0, getUpdateTag());
	}

	abstract void writeDataNBT(NBTTagCompound cmp);

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		readDataNBT(tag);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {
		tag = super.writeToNBT(tag);
		writeDataNBT(tag);
		return tag;
	}

	abstract void readDataNBT(NBTTagCompound cmp);

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return oldState.getBlock() != newState.getBlock();
	}

	public Optional<FluidStack> getFluid() {
		FluidStack stack = tank.getFluid();
		return stack != null ? Optional.of(stack) : Optional.empty();
	}

	CauldronFluid tank() {
		return (CauldronFluid) tank;
	}

	@SuppressWarnings("WeakerAccess")
	public class CauldronFluid extends FluidTank {

		private final TileFluidInventory tile;

		public CauldronFluid(TileFluidInventory tile, int capacity) {
			super(capacity);
			this.tile = tile;
		}

		public CauldronFluid(TileFluidInventory tile, @Nullable FluidStack fluidStack, int capacity) {
			super(fluidStack, capacity);
			this.tile = tile;
		}

		public CauldronFluid(TileFluidInventory tile, Fluid fluid, int amount, int capacity) {
			super(fluid, amount, capacity);
			this.tile = tile;
		}

		@Override
		public int fillInternal(FluidStack resource, boolean doFill) {
			int filled = super.fillInternal(resource, doFill);
			if (doFill && filled > 0) {
				world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
			}
			return filled;
		}

		@Nullable
		@Override
		public FluidStack drainInternal(int maxDrain, boolean doDrain) {
			FluidStack drained = super.drainInternal(maxDrain, doDrain);
			if (doDrain && drained != null && drained.amount > 0) {
				world.updateComparatorOutputLevel(pos, world.getBlockState(pos).getBlock());
			}
			return drained;
		}

		@Override
		protected void onContentsChanged() {
			tile.onLiquidChange();
		}

		@Override
		public String toString() {
			return String.format("Cauldron: %s, %d/%d", fluid != null && fluid.getFluid() != null ? fluid.getFluid().getName() : "Empty", getFluidAmount(), getCapacity());
		}

		public boolean hasFluid() {
			FluidStack fluid = getFluid();
			return fluid != null && fluid.amount > 0 && fluid.getFluid() != null;
		}

		public boolean hasFluid(Fluid other) {
			return fluid != null && fluid.getFluid() == other;
		}

		@Nullable
		public Fluid getInnerFluid() {
			return fluid != null ? fluid.getFluid() : null;
		}

		public boolean isEmpty() {
			return getFluid() == null || getFluid().amount <= 0;
		}

		public boolean isFull() {
			return getFluid() != null && getFluid().amount == getCapacity();
		}
	}
}
