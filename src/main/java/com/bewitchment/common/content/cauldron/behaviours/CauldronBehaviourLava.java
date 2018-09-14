package com.bewitchment.common.content.cauldron.behaviours;

import com.bewitchment.common.tile.tiles.TileEntityCauldron;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CauldronBehaviourLava implements ICauldronBehaviour {

	private static final String ID = "lava";

	private TileEntityCauldron cauldron;
	private World world;
	private BlockPos pos;

	@Override
	public void setCauldron(TileEntityCauldron tile) {
		cauldron = tile;
		world = tile.getWorld();
		pos = tile.getPos();
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
				world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1, true);
			} else if (stack.getItem() == Item.getItemFromBlock(Blocks.TNT)) {
				world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 3, true);
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
				EntityFireworkRocket entityfireworkrocket = new EntityFireworkRocket(world, pos.getX(), pos.getY(), pos.getZ(), stack);
				if (isCharge) {
					NBTTagCompound hackTag = new NBTTagCompound();
					entityfireworkrocket.writeEntityToNBT(hackTag);
					hackTag.setInteger("LifeTime", 2);
					entityfireworkrocket.readEntityFromNBT(hackTag);
				}
				world.spawnEntity(entityfireworkrocket);
			}
		}
	}

	@Override
	public void onDeactivation() {

	}
}
