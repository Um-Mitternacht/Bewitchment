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
		this.cauldron = tile;
	}

	@Override
	public void handleParticles(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			if (this.lowEnergy) {
				Random r = this.cauldron.getWorld().rand;
				this.cauldron.getWorld().spawnParticle(EnumParticleTypes.SPELL_MOB, this.cauldron.getPos().getX() + 0.4 + (0.2 * r.nextDouble()), this.cauldron.getPos().getY() + 0.5, this.cauldron.getPos().getZ() + 0.4 + (0.2 * r.nextDouble()), 0xFF * (0.5 + (0.3 * this.cauldron.getWorld().rand.nextDouble() * 0.5)), 0, 0);
			} else if (this.validRecipe) {
				Random r = this.cauldron.getWorld().rand;
				this.cauldron.getWorld().spawnParticle(EnumParticleTypes.SPELL_INSTANT, this.cauldron.getPos().getX() + 0.4 + (0.2 * r.nextDouble()), this.cauldron.getPos().getY() + 0.5, this.cauldron.getPos().getZ() + 0.4 + (0.2 * r.nextDouble()), 0, 0, 0);
			}
		}
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public boolean shouldInputsBeBlocked() {
		return (this.cauldron.getCurrentBehaviour() == this) && this.validRecipe;
	}

	@Override
	public void statusChanged(boolean isActiveBehaviour) {
		if (isActiveBehaviour && !this.cauldron.getInputs().isEmpty() && this.cauldron.getFluid().isPresent() && !this.validRecipe) {
			this.validRecipe = CauldronRegistry.getCraftingResult(this.cauldron.getFluid().get(), this.cauldron.getInputs()).isPresent();
			this.color = Color.getHSBColor(this.cauldron.getWorld().rand.nextFloat(), 0.6f + (0.4f * this.cauldron.getWorld().rand.nextFloat()), this.cauldron.getWorld().rand.nextFloat()).getRGB();
			this.cauldron.markDirty();
			this.cauldron.syncToClient();
		}
	}

	@Override
	public void update(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			boolean wasLowEnergy = this.lowEnergy;
			if (this.validRecipe && (this.craftTime < MAX_CRAFT_TIME)) {
				if (this.cauldron.getCapability(IMagicPowerConsumer.CAPABILITY, null).drainAltarFirst(null, this.cauldron.getPos(), this.cauldron.getWorld().provider.getDimension(), POWER_PER_TICK)) {
					this.lowEnergy = false;
				} else {
					this.lowEnergy = true;
				}

				if (!this.lowEnergy) {
					this.craftTime++;
				}
				this.cauldron.markDirty();
				if (wasLowEnergy != this.lowEnergy) {
					this.cauldron.syncToClient();
				}
			}

			if (this.validRecipe && (this.craftTime >= MAX_CRAFT_TIME)) {
				CauldronCraftingRecipe result = CauldronRegistry.getCraftingResult(this.cauldron.getFluid().get(), this.cauldron.getInputs()).get();
				this.cauldron.setTankLock(true);
				CauldronFluidTank tank = (CauldronFluidTank) this.cauldron.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
				tank.drain(result.getRequiredFluidAmount(), true);

				if (result.hasItemOutput()) {
					EntityItem e = new EntityItem(this.cauldron.getWorld(), this.cauldron.getPos().getX() + 0.5, this.cauldron.getPos().getY() + 0.5, this.cauldron.getPos().getZ() + 0.5, result.getItemResult(this.cauldron.getInputs()));
					e.addTag("cauldron_drop");
					e.motionY = 0.06;
					e.motionX = 0;
					e.motionZ = 0;
					this.cauldron.getWorld().spawnEntity(e);

				}

				if (result.hasFluidOutput()) {
					tank.setFluid(result.getFluidResult());
				}

				this.cauldron.setBehaviour(this.cauldron.getDefaultBehaviours().IDLE);
				this.lowEnergy = false;
				this.validRecipe = false;
				this.craftTime = 0;
				this.cauldron.clearItemInputs();// MD & StC called here
			}
		}
	}

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		this.saveToSyncNBT(tag);
		tag.setInteger("craftTime", this.craftTime);

	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		this.loadFromSyncNBT(tag);
		this.craftTime = tag.getInteger("craftTime");
	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		tag.setInteger("color_craft", this.color);
		tag.setBoolean("hasRecipe", this.validRecipe);
		tag.setBoolean("lowEnergy", this.lowEnergy);
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		this.color = tag.getInteger("color_craft");
		this.validRecipe = tag.getBoolean("hasRecipe");
		this.lowEnergy = tag.getBoolean("lowEnergy");
	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void onDeactivation() {
		this.validRecipe = false;
		this.lowEnergy = false;
		this.color = TileEntityCauldron.DEFAULT_COLOR;
		this.craftTime = 0;
		this.cauldron.markDirty();
	}

}