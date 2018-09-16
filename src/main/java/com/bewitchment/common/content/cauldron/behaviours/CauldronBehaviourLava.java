package com.bewitchment.common.content.cauldron.behaviours;

import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class CauldronBehaviourLava implements ICauldronBehaviour {

	private static final String ID = "lava";

	private TileEntityCauldron cauldron;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		cauldron = tile;
	}

	@Override
	public void handleParticles(boolean active) {

	}

	@Override
	public boolean canAccept(ItemStack stack) {
		return true;
	}

	@Override
	public void update(boolean active) {
		//NO-OP
	}

	@Override
	public int getColor() {
		return 0xff8d00;
	}

	@Override
	public void saveToNBT(NBTTagCompound tag) {

	}

	@Override
	public void loadFromNBT(NBTTagCompound tag) {

	}

	@Override
	public void saveToSyncNBT(NBTTagCompound tag) {

	}

	@Override
	public void loadFromSyncNBT(NBTTagCompound tag) {

	}

	@Override
	public String getID() {
		return ID;
	}

	@Override
	public void statusChanged(boolean active) {
		if (active && cauldron.getInputs().size() > 0) {
			ItemStack stack = cauldron.getInputs().get(cauldron.getInputs().size() - 1);
			if (stack.getItem() == Items.GUNPOWDER || stack.getItem() == Items.FIRE_CHARGE) {
				cauldron.getWorld().createExplosion(null, cauldron.getPos().getX() + 0.5, cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.5, 1, true);
			} else if (stack.getItem() == Item.getItemFromBlock(Blocks.TNT)) {
				cauldron.getWorld().createExplosion(null, cauldron.getPos().getX() + 0.5, cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.5, 3, true);
			} else if (stack.getItem() == Items.FIREWORKS || stack.getItem() == Items.FIREWORK_CHARGE) {
				boolean isCharge = false;
				if (stack.getItem() == Items.FIREWORK_CHARGE) {
					isCharge = true;
					NBTTagCompound fireworks = new NBTTagCompound();
					fireworks.setByte("Flight", (byte) 3);
					NBTTagList explosionList = new NBTTagList();
					if (stack.getTagCompound() != null) {
						explosionList.appendTag(stack.getTagCompound().getCompoundTag("Explosion"));
					}
					fireworks.setTag("Explosions", explosionList);
					stack = new ItemStack(Items.FIREWORKS);
					NBTTagCompound fireworksBaseTag = new NBTTagCompound();
					fireworksBaseTag.setTag("Fireworks", fireworks);
					stack.setTagCompound(fireworksBaseTag);

				}
				EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(cauldron.getWorld(), cauldron.getPos().getX() + 0.5, cauldron.getPos().getY() + 0.5, cauldron.getPos().getZ() + 0.5, stack);
				if (isCharge) {
					NBTTagCompound hackTag = new NBTTagCompound();
					entityfireworkrocket.writeEntityToNBT(hackTag);
					hackTag.setInteger("LifeTime", 0);
					entityfireworkrocket.readEntityFromNBT(hackTag);
				}
				cauldron.getWorld().spawnEntity(entityfireworkrocket);
			}
		}
	}

	@Override
	public void onDeactivation() {

	}
}
