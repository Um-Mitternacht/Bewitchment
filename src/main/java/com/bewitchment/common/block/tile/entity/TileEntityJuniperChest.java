package com.bewitchment.common.block.tile.entity;

import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

@SuppressWarnings("NullableProblems")
public class TileEntityJuniperChest extends ModTileEntity implements ITickable {
	private final ItemStackHandler inventory = new ItemStackHandler(36);

	public float lidAngle, prevLidAngle;
	public int using;

	@Override
	public ItemStackHandler[] getInventories() {
		return new ItemStackHandler[]{inventory};
	}

	@Override
	public void update() {
		prevLidAngle = lidAngle;
		if (using > 0 && lidAngle == 0)
			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		if (using == 0 && lidAngle > 0 || using > 0 && lidAngle < 1) {
			if (using > 0) lidAngle += 0.1f;
			else lidAngle -= 0.1f;
			if (lidAngle > 1) lidAngle = 1;
			if (lidAngle < 0) lidAngle = 0;
			if (lidAngle < 0.5f && prevLidAngle >= 0.5f)
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5f, world.rand.nextFloat() * 0.1f + 0.9f);
		}
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if (id == 1) {
			this.using = type;
			return true;
		}
		return super.receiveClientEvent(id, type);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing face) {
		return false;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing face) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, face);
	}
}