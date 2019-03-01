package com.bewitchment.common.content.cauldron.behaviours;

import com.bewitchment.common.content.cauldron.CauldronFoodValue;
import com.bewitchment.common.content.cauldron.CauldronRegistry;
import com.bewitchment.common.core.helper.Log;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;

import java.util.Random;

public class CauldronBehaviourStew implements ICauldronBehaviour {
	private static final String ID = "stew";
	private static final int COOK_TIME = 20 * 20;

	private TileEntityCauldron cauldron;
	private int progress = 0;
	private int clientSideItemNumber = 0;
	private ItemStack currentlyCooking = ItemStack.EMPTY;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		this.cauldron = tile;
	}

	@Override
	public void handleParticles(boolean isActiveBehaviour) {
		if (isActiveBehaviour && (this.progress < COOK_TIME)) {
			Random r = this.cauldron.getWorld().rand;
			this.cauldron.getWorld().spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.cauldron.getPos().getX() + 0.3 + (0.4 * r.nextDouble()), this.cauldron.getPos().getY() + 0.5, this.cauldron.getPos().getZ() + 0.3 + (0.4 * r.nextDouble()), 0, 0, 0);
		}
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public void statusChanged(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			this.clientSideItemNumber = this.cauldron.getInputs().size();
			if (this.clientSideItemNumber != 0) {
				if (this.cauldron.getInputs().size() > 10) {
					this.cauldron.setBehaviour(this.cauldron.getDefaultBehaviours().FAILING);
				} else {
					ItemStack last = this.cauldron.getInputs().get(this.clientSideItemNumber - 1);
					if (CauldronRegistry.getCauldronFoodValue(last) == null) {
						this.cauldron.setBehaviour(this.cauldron.getDefaultBehaviours().FAILING);
					} else {
						this.progress = 0;
						this.currentlyCooking = this.getSoup();
						this.clientSideItemNumber++;
						this.cauldron.setColor(this.getColor());
					}
				}
			}
			this.cauldron.markDirty();
			this.cauldron.syncToClient();
		}
	}

	@Override
	public void playerInteract(EntityPlayer player, EnumHand hand) {
		if (player.getHeldItem(hand).getItem().equals(Items.BOWL) && ((this.progress >= COOK_TIME) || player.isCreative())) {
			if (!player.isCreative()) {
				player.getHeldItem(hand).splitStack(1);
			}
			ItemStack soup = this.currentlyCooking;
			this.currentlyCooking = ItemStack.EMPTY;
			this.progress = 0;
			this.cauldron.clearTanks();
			this.cauldron.clearItemInputs();
			this.cauldron.setBehaviour(this.cauldron.getDefaultBehaviours().IDLE);
			TileEntityCauldron.giveItemToPlayer(player, soup);
			this.clientSideItemNumber = 0;
			this.cauldron.markDirty();
			this.cauldron.syncToClient();
		}
	}

	@Override
	public void update(boolean isActiveBehaviour) {
		if (isActiveBehaviour && !this.currentlyCooking.isEmpty() && (this.progress < COOK_TIME)) {
			++this.progress;
			if (progress >= COOK_TIME) {
				this.cauldron.markDirty();
				this.cauldron.syncToClient();
			}
		}
	}

	@Override
	public int getColor() {
		int baseColor = 0xa76e00;
		double darkeningFactor = 1d - 0.04 * clientSideItemNumber;
		int r = (baseColor >> 16) & 0xFF;
		int g = (baseColor >> 8) & 0xFF;
		int b = baseColor & 0xFF;
		r *= darkeningFactor;
		g *= darkeningFactor;
		b *= darkeningFactor;
		return (r << 16) | (g << 8) | b;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {
		tag.setInteger("progress", progress);
		tag.setTag("currentlyCooking", currentlyCooking.writeToNBT(new NBTTagCompound()));
	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {
		progress = tag.getInteger("progress");
		currentlyCooking = new ItemStack(tag.getCompoundTag("currentlyCooking"));
	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		tag.setInteger("progress", progress);
		tag.setInteger("soupNum", this.clientSideItemNumber);
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		this.clientSideItemNumber = tag.getInteger("soupNum");
		progress = tag.getInteger("progress");
	}

	@Override
	public String getID() {
		return ID;
	}

	private ItemStack getSoup() {
		int hunger = 0;
		float saturation = 0;
		float multiplier = 1;
		float decay = 0.6f;

		long differentItems = this.cauldron.getInputs().stream().map(is -> is.toString()).distinct().count();

		for (ItemStack i : this.cauldron.getInputs()) {
			CauldronFoodValue next = CauldronRegistry.getCauldronFoodValue(i);
			if (next == null) {
				Log.w(i + " is not a valid food, this shouldn't happen! Report to https://github.com/Um-Mitternacht/Covens/issues");
				this.cauldron.setBehaviour(this.cauldron.getDefaultBehaviours().FAILING);
				return ItemStack.EMPTY;
			}
			hunger += (next.hunger * multiplier);
			saturation += (next.saturation * multiplier);
			multiplier *= decay;
		}

		float bonus = differentItems / 4f;
		hunger *= bonus;
		saturation *= bonus;
		ItemStack stew = new ItemStack(ModItems.stew);
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("hunger", hunger);
		nbt.setFloat("saturation", saturation);
		stew.setTagCompound(nbt);
		return stew;
	}

	@Override
	public void onDeactivation() {
		this.progress = 0;
		this.currentlyCooking = ItemStack.EMPTY;
		this.cauldron.markDirty();
		this.cauldron.syncToClient();
	}

}
