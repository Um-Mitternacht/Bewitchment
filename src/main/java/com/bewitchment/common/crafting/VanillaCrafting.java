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
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 0), new ItemStack(ModItems.gem, 4, 0), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 1), new ItemStack(ModItems.gem, 4, 1), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 2), new ItemStack(ModItems.gem, 4, 2), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 3), new ItemStack(ModItems.gem, 4, 3), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 4), new ItemStack(ModItems.gem, 4, 4), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 5), new ItemStack(ModItems.gem, 4, 5), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 6), new ItemStack(ModItems.gem, 4, 6), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 7), new ItemStack(ModItems.gem, 4, 7), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 8), new ItemStack(ModItems.gem, 4, 8), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModBlocks.gem_ore), 1, 9), new ItemStack(ModItems.gem, 4, 9), 0.35F);
		GameRegistry.addSmelting(new ItemStack((ModItems.golden_thread), 1, 0), new ItemStack(Items.GOLD_NUGGET, 1, 0), 1.0F);

		ModMaterials.TOOL_RITUAL.setRepairItem(new ItemStack(ModItems.silver_ingot));
		ModMaterials.ARMOR_SILVER.setRepairItem(new ItemStack(ModItems.silver_ingot));
		ModMaterials.TOOL_SILVER.setRepairItem(new ItemStack(ModItems.silver_ingot));
		ModMaterials.TOOL_COLD_IRON.setRepairItem(new ItemStack(ModItems.cold_iron_ingot));
		ModMaterials.ARMOR_COLD_IRON.setRepairItem(new ItemStack(ModItems.cold_iron_ingot));
		ModMaterials.ARMOR_BEWITCHED_LEATHER.setRepairItem(new ItemStack(ModItems.witches_stitching));
		ModMaterials.ARMOR_VAMPIRE.setRepairItem(new ItemStack(ModItems.sanguine_fabric));
	}
}
