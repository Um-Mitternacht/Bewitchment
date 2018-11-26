package com.bewitchment.common.content.cauldron.behaviours;

import com.bewitchment.api.mp.IMagicPowerConsumer;
import com.bewitchment.common.content.cauldron.CauldronCraftingRecipe;
import com.bewitchment.common.content.cauldron.CauldronRegistry;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import com.bewitchment.common.tile.util.CauldronFluidTank;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import java.awt.*;
import java.util.Random;

public class CauldronBehaviourCrafting implements ICauldronBehaviour {

	private static final String ID = "craft";
	private static final int MAX_CRAFT_TIME = 100, POWER_PER_TICK = 2;

	private int craftTime = 0, color = TileEntityCauldron.DEFAULT_COLOR;
	private boolean validRecipe = false, lowEnergy = false;
	private TileEntityCauldron cauldron;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		cauldron = tile;
	}

	@Override
	public void handleParticles(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			if (lowEnergy) {
				Random r = cauldron.getWorld().rand;
				cauldron.getWorld().spawnParticle(EnumParticleTypes.SPELL_MOB, cauldron.getPos().getX() + 0.4 + 0.2 * r.nextDouble(), cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.4 + 0.2 * r.nextDouble(), 0xFF * (0.5 + 0.3 * cauldron.getWorld().rand.nextDouble() * 0.5), 0, 0);
			} else if (validRecipe) {
				Random r = cauldron.getWorld().rand;
				cauldron.getWorld().spawnParticle(EnumParticleTypes.SPELL_INSTANT, cauldron.getPos().getX() + 0.4 + 0.2 * r.nextDouble(), cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.4 + 0.2 * r.nextDouble(), 0, 0, 0);
			}
		}
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public boolean shouldInputsBeBlocked() {
		return cauldron.getCurrentBehaviour() == this && validRecipe;
	}

	@Override
	public void statusChanged(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			if (!cauldron.getInputs().isEmpty() && cauldron.getFluid().isPresent() && !validRecipe) {
				validRecipe = CauldronRegistry.getCraftingResult(cauldron.getFluid().get(), cauldron.getInputs()).isPresent();
				color = Color.getHSBColor(cauldron.getWorld().rand.nextFloat(), 0.6f + 0.4f * cauldron.getWorld().rand.nextFloat(), cauldron.getWorld().rand.nextFloat()).getRGB();
				cauldron.markDirty();
				cauldron.syncToClient();
			}
		}
	}

	@Override
	public void update(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			boolean wasLowEnergy = lowEnergy;
			if (validRecipe && craftTime < MAX_CRAFT_TIME) {
				if (cauldron.getCapability(IMagicPowerConsumer.CAPABILITY, null).drainAltarFirst(null, cauldron.getPos(), cauldron.getWorld().provider.getDimension(), POWER_PER_TICK)) {
					lowEnergy = false;
				} else {
					lowEnergy = true;
				}

				if (!lowEnergy) {
					craftTime++;
				}
				cauldron.markDirty();
				if (wasLowEnergy != lowEnergy) {
					cauldron.syncToClient();
				}
			}

			if (validRecipe && craftTime >= MAX_CRAFT_TIME) {
				CauldronCraftingRecipe result = CauldronRegistry.getCraftingResult(cauldron.getFluid().get(), cauldron.getInputs()).get();
				cauldron.setTankLock(true);
				CauldronFluidTank tank = (CauldronFluidTank) cauldron.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				tank.drain(result.getRequiredFluidAmount(), true);

				if (result.hasItemOutput()) {
					EntityItem e = new EntityItem(cauldron.getWorld(), cauldron.getPos().getX() + 0.5, cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.5, result.getItemResult());
					e.addTag("cauldron_drop");
					e.motionY = 0.06;
					e.motionX = 0;
					e.motionZ = 0;
					cauldron.getWorld().spawnEntity(e);

				}

				if (result.hasFluidOutput()) {
					tank.setFluid(result.getFluidResult());
				}

				cauldron.setBehaviour(cauldron.getDefaultBehaviours().IDLE);
				lowEnergy = false;
				validRecipe = false;
				craftTime = 0;
				cauldron.clearItemInputs();//MD & StC called here
			}
		}
	}

	@Override
	public int getColor() {
		return color;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		saveToSyncNBT(tag);
		tag.setInteger("craftTime", craftTime);

	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		loadFromSyncNBT(tag);
		craftTime = tag.getInteger("craftTime");
	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		tag.setInteger("color_craft", color);
		tag.setBoolean("hasRecipe", validRecipe);
		tag.setBoolean("lowEnergy", lowEnergy);
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		color = tag.getInteger("color_craft");
		validRecipe = tag.getBoolean("hasRecipe");
		lowEnergy = tag.getBoolean("lowEnergy");
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void onDeactivation() {
		validRecipe = false;
		lowEnergy = false;
		color = TileEntityCauldron.DEFAULT_COLOR;
		craftTime = 0;
		cauldron.markDirty();
	}

}
