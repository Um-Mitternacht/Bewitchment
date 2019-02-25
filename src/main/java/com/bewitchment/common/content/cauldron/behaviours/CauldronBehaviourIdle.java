package com.bewitchment.common.content.cauldron.behaviours;

import com.bewitchment.client.fx.ParticleF;
import com.bewitchment.common.Bewitchment;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.content.cauldron.CauldronRegistry;
import com.bewitchment.common.core.statics.ModSounds;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import com.bewitchment.common.tile.util.CauldronFluidTank;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.util.Random;

public class CauldronBehaviourIdle implements ICauldronBehaviour {

	private static final int MAX_HEAT = 150, BOIL_THRESHOLD = 100;
	private static final String ID = "idle";

	private TileEntityCauldron cauldron;
	private int heat = 0;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		cauldron = tile;
	}

	@Override
	public void handleParticles(boolean active) {
		BlockPos pos = cauldron.getPos();
		World world = cauldron.getWorld();
		Random rand = world.rand;
		CauldronFluidTank tank = (CauldronFluidTank) cauldron.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		float level = tank.getFluidAmount() / (Fluid.BUCKET_VOLUME * 2F);
		level = pos.getY() + 0.1F + level;
		if (heat >= BOIL_THRESHOLD) {
			Fluid fluid = tank.getInnerFluid();
			if (fluid == FluidRegistry.WATER || fluid == Fluids.MUNDANE_OIL || fluid == Fluids.BW_HONEY) {
				for (int i = 0; i < 2; i++) {
					double posX = pos.getX() + 0.2D + world.rand.nextDouble() * 0.6D;
					double posZ = pos.getZ() + 0.2D + world.rand.nextDouble() * 0.6D;
					Bewitchment.proxy.spawnParticle(ParticleF.CAULDRON_BUBBLE, posX, level, posZ, 0, 0, 0, cauldron.getColorRGB());
				}
				if (rand.nextInt(4) == 0) {
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.BOIL, SoundCategory.BLOCKS, rand.nextFloat() * 0.1F, 0.5F + rand.nextFloat() * 0.8f, true);
				}
			} else if (fluid == FluidRegistry.LAVA) {
				if (rand.nextInt(5) == 0) {
					double posX = pos.getX() + 0.2D + world.rand.nextDouble() * 0.6D;
					double posZ = pos.getZ() + 0.2D + world.rand.nextDouble() * 0.6D;
					world.spawnParticle(EnumParticleTypes.LAVA, posX, level, posZ, 0, 0.1, 0);
				}
				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.6F, 0.9F + rand.nextFloat() * 0.15F, false);
			} else {
				if (rand.nextInt(4) == 0) {
					world.playSound(pos.getX(), pos.getY(), pos.getZ(), ModSounds.BOIL, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 1F + rand.nextFloat() * 0.8f, true);
				}
			}
		}
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public boolean canAccept(EntityItem itemEntity) {
		return ICauldronBehaviour.super.canAccept(itemEntity) && !itemEntity.getTags().contains("cauldron_drop");
	}

	@Override
	public boolean shouldInputsBeBlocked() {
		return heat < BOIL_THRESHOLD || !cauldron.getFluid().isPresent() || cauldron.getFluid().get().amount <= 0;
	}

	@Override
	public void update(boolean isActive) {
		IBlockState below = cauldron.getWorld().getBlockState(cauldron.getPos().down());
		boolean wasBoiling = heat >= MAX_HEAT;
		if (cauldron.getFluid().isPresent()) {
			if (below.getMaterial() == Material.FIRE || below.getMaterial() == Material.LAVA) {
				if (heat < MAX_HEAT) {
					heat++;
				}
			} else {
				if (heat > 0) {
					heat--;
				}
			}
			if (cauldron.getFluid().get().getFluid().getTemperature() > 800) {
				heat = MAX_HEAT;
			}
			cauldron.markDirty();
			boolean isBoilingNow = heat >= MAX_HEAT;
			if (isBoilingNow != wasBoiling) {
				cauldron.syncToClient();
			}
		}
	}

	@Override
	public int getColor() {
		return TileEntityCauldron.DEFAULT_COLOR;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		tag.setInteger("heat", heat);
	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		heat = tag.getInteger("heat");
	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		saveToNBT(tag);
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		loadFromNBT(tag);
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void statusChanged(boolean isActive) {
		if (cauldron.getInputs().size() > 0 && cauldron.getInputs().get(cauldron.getInputs().size() - 1).getItem() == ModItems.wood_ash) {
			cauldron.setBehaviour(cauldron.getDefaultBehaviours().CLEANING);
			cauldron.setTankLock(false);
		} else if (isActive) {
			if (cauldron.getInputs().size() > 0) {
				ItemStack stack = cauldron.getInputs().get(cauldron.getInputs().size() - 1);
				if (cauldron.getFluid().isPresent() && cauldron.getFluid().get().getFluid() == FluidRegistry.LAVA) {
					cauldron.setBehaviour(cauldron.getDefaultBehaviours().LAVA);
				} else if (stack.getItem() == Items.NETHER_WART && cauldron.getFluid().get().getFluid() == FluidRegistry.WATER) {
					cauldron.setBehaviour(cauldron.getDefaultBehaviours().BREWING);
					cauldron.setTankLock(false);
				} else if (CauldronRegistry.getCauldronFoodValue(stack) != null && cauldron.getFluid().get().getFluid() == FluidRegistry.WATER) {
					cauldron.setBehaviour(cauldron.getDefaultBehaviours().STEW);
					cauldron.setTankLock(false);
				} else {
					cauldron.setBehaviour(cauldron.getDefaultBehaviours().CRAFTING);
					cauldron.setTankLock(false);
				}
			}
		}


		if (!cauldron.getFluid().isPresent() || cauldron.getFluid().get().amount <= 0) {
			if (cauldron.getInputs().size() > 0) {
				cauldron.clearItemInputs();
			}
			heat = 0;
			cauldron.setBehaviour(this);
			cauldron.setTankLock(true);
			cauldron.markDirty();
			cauldron.syncToClient();
		}
	}

	@Override
	public void onDeactivation() {

	}


}
