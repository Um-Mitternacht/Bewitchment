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
	private static final int COOK_TIME = 20 * 60;

	private TileEntityCauldron cauldron;
	private int progress = 0;
	private int clientSideItemNumber = 0;
	private ItemStack currentlyCooking = ItemStack.EMPTY;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		cauldron = tile;
	}

	@Override
	public void handleParticles(boolean isActiveBehaviour) {
		if (isActiveBehaviour && progress >= COOK_TIME) {
			Random r = cauldron.getWorld().rand;
			cauldron.getWorld().spawnParticle(EnumParticleTypes.SPELL_INSTANT, cauldron.getPos().getX() + 0.4 + 0.2 * r.nextDouble(), cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.4 + 0.2 * r.nextDouble(), 0, 0, 0);
		}
	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public void statusChanged(boolean isActiveBehaviour) {
		if (isActiveBehaviour) {
			clientSideItemNumber = cauldron.getInputs().size();
			if (clientSideItemNumber != 0) {
				if (cauldron.getInputs().size() > 10) {
					cauldron.setBehaviour(cauldron.getDefaultBehaviours().FAILING);
				} else {
					ItemStack last = cauldron.getInputs().get(clientSideItemNumber - 1);
					if (CauldronRegistry.getCauldronFoodValue(last) == null) {
						cauldron.setBehaviour(cauldron.getDefaultBehaviours().FAILING);
					} else {
						progress = 0;
						currentlyCooking = getSoup();
						clientSideItemNumber++;
						cauldron.setColor(this.getColor());
					}
				}
			}
			cauldron.markDirty();
			cauldron.syncToClient();
		}
	}

	@Override
	public void playerInteract(EntityPlayer player, EnumHand hand) {
		if (player.getHeldItem(hand).getItem().equals(Items.BOWL) && (progress >= COOK_TIME || player.isCreative())) {
			player.getHeldItem(hand).splitStack(1);
			ItemStack soup = currentlyCooking;
			currentlyCooking = ItemStack.EMPTY;
			progress = 0;
			cauldron.clearTanks();
			cauldron.clearItemInputs();
			cauldron.setBehaviour(cauldron.getDefaultBehaviours().IDLE);
			TileEntityCauldron.giveItemToPlayer(player, soup);
			clientSideItemNumber = 0;
			cauldron.markDirty();
			cauldron.syncToClient();
		}
	}

	@Override
	public void update(boolean isActiveBehaviour) {
		if (isActiveBehaviour && !currentlyCooking.isEmpty() && progress < COOK_TIME) {
			++progress;
		}
	}

	@Override
	public int getColor() {
		return (int) (0xa76e00 * (1 - (clientSideItemNumber / 14d)));
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {

	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {

	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {
		tag.setInteger("soupNum", clientSideItemNumber);
	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {
		clientSideItemNumber = tag.getInteger("soupNum");
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

		long differentItems = cauldron.getInputs().stream().map(is -> is.toString()).distinct().count();

		for (ItemStack i : cauldron.getInputs()) {
			CauldronFoodValue next = CauldronRegistry.getCauldronFoodValue(i);
			if (next == null) {
				Log.w(i + " is not a valid food, this shouldn't happen! Report to https://github.com/Um-Mitternacht/Bewitchment/issues");
				cauldron.setBehaviour(cauldron.getDefaultBehaviours().FAILING);
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
		progress = 0;
		currentlyCooking = ItemStack.EMPTY;
		cauldron.markDirty();
		cauldron.syncToClient();
	}

}
