package com.bewitchment.common.integration.chisel;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.chisel.BlockSilverChiselled.BlockSilverVariant;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class Chisel {
	public static void registerBlockVariants() {
		for (BlockSilverVariant sv: BlockSilverVariant.values()) {
			FMLInterModComms.sendMessage("chisel", "variation:add", "silver_block|"+ModBlocks.silver_block_chisel.getRegistryName().toString()+"|"+sv.ordinal());
		}
	}
}
