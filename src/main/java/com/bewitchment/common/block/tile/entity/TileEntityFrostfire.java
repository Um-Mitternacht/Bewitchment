package com.bewitchment.common.block.tile.entity;

import com.bewitchment.api.registry.FrostfireRecipe;
import com.bewitchment.common.block.tile.entity.util.ModTileEntity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityFrostfire extends ModTileEntity implements ITickable {
	@Override
	public void update() {
		if (!world.isRemote && world.getTotalWorldTime() % 20 == 0) {
			for (EntityItem item : world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos))) {
				FrostfireRecipe recipe = GameRegistry.findRegistry(FrostfireRecipe.class).getValuesCollection().stream().filter(s -> s.matches(item.getItem())).findFirst().orElse(null);
				if (recipe != null) {
					world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.25f, 0);
					item.getItem().shrink(1);
					InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), recipe.getOutput().copy());
				}
			}
		}
	}
}