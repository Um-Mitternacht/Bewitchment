package com.bewitchment.common.crafting;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.item.ModMaterials;
import com.bewitchment.common.item.magic.ItemFumes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class VanillaCrafting {

	private VanillaCrafting() {
	}

	public static void blocks() {

		GameRegistry.addSmelting(ModBlocks.silver_ore, new ItemStack(ModItems.silver_ingot, 1), 0.35F);
		GameRegistry.addSmelting(Blocks.SAPLING, new ItemStack(ModItems.wood_ash, 4), 0.15F);
		GameRegistry.addSmelting(ModItems.silver_scales, new ItemStack(ModItems.silver_nugget, 1), 0.20F);
		GameRegistry.addSmelting(Items.MELON, new ItemStack(ModItems.grilled_watermelon, 1), 0.45F);
		GameRegistry.addSmelting((new ItemStack(ModItems.fume, 1, ItemFumes.Type.unfired_jar.ordinal())), new ItemStack(ModItems.fume, 1, ItemFumes.Type.empty_jar.ordinal()), 0.45F);

		ModMaterials.TOOL_RITUAL.setRepairItem(new ItemStack(ModItems.silver_ingot));
		ModMaterials.ARMOR_SILVER.setRepairItem(new ItemStack(ModItems.silver_ingot));
		ModMaterials.TOOL_SILVER.setRepairItem(new ItemStack(ModItems.silver_ingot));
	}
}
