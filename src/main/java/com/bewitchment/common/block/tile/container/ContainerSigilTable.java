package com.bewitchment.common.block.tile.container;

import com.bewitchment.api.registry.SigilRecipe;
import com.bewitchment.common.block.tile.container.util.ModContainer;
import com.bewitchment.common.block.tile.entity.TileEntitySigilTable;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ContainerSigilTable extends ModContainer {
	private final TileEntitySigilTable tile;
	private final InventoryCrafting matrix;

	public ContainerSigilTable(InventoryPlayer inventory, TileEntitySigilTable tile) {
		this.matrix = new InventoryCrafting(this, 5, 5);
		this.tile = tile;
		for (int y = 0; y < 5; y++) {
			for (int x = 0; x < 5; x++) {
				addSlotToContainer(new Slot(matrix, x + y *5, 25 + x * 18, 9 + y * 18));
			}
		}
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 9; j++)
				addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 110 + i * 18));
		for (int i = 0; i < 9; i++)
			addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 167));
	}

	@Override
	public void onCraftMatrixChanged(IInventory inventoryIn) {
		final SigilRecipe recipe = GameRegistry.findRegistry(SigilRecipe.class).getValuesCollection().stream().filter(p -> p.matches(matrix)).findAny().orElse(null);
		//if(recipe != null) result.setInventorySlotContents(1, recipe.output);
	}
}
