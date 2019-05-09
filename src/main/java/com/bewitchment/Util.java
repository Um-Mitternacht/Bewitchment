package com.bewitchment;

import com.bewitchment.common.block.BlockSaltBarrier;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

@SuppressWarnings("deprecation")
public class Util {
	public static <T extends Block> T registerBlock(T block, String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int level, String... oreDictionaryNames) {
		ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, name);
		block.setRegistryName(loc);
		block.setTranslationKey(loc.toString().replace(":", "."));
		block.setCreativeTab(Bewitchment.proxy.tab);
		ObfuscationReflectionHelper.setPrivateValue(Block.class, block, sound, "blockSoundType", "field_149762_H");
		block.setHardness(hardness);
		block.setResistance(resistance);
		block.setHarvestLevel(tool, level);
		if (mat == Material.CARPET) Blocks.FIRE.setFireInfo(block, 60, 20);
		if (mat == Material.CLOTH || mat == Material.LEAVES) Blocks.FIRE.setFireInfo(block, 30, 60);
		if (mat == Material.PLANTS) Blocks.FIRE.setFireInfo(block, 60, 100);
		if (mat == Material.TNT || mat == Material.VINE) Blocks.FIRE.setFireInfo(block, 15, 100);
		if (mat == Material.WOOD) Blocks.FIRE.setFireInfo(block, 5, 20);
		if (mat == Material.ICE) block.setDefaultSlipperiness(0.98f);
		ForgeRegistries.BLOCKS.register(block);
		if (/*!(block instanceof BlockWitchesLight) && !(block instanceof BlockGlyph) && */ !(block instanceof BlockSaltBarrier) && !(block instanceof BlockCrops) && !(block instanceof BlockDoor) && !(block instanceof BlockSlab) && !(block instanceof IFluidBlock)) {
			Item item = new ItemBlock(block).setRegistryName(loc).setTranslationKey(block.getTranslationKey());
			ForgeRegistries.ITEMS.register(item);
			Bewitchment.proxy.registerTexture(item, block instanceof BlockSapling ? "inventory" : "normal");
		}
		for (String ore : oreDictionaryNames) OreDictionary.registerOre(ore, block);
		return block;
	}

	public static <T extends Block> T registerBlock(T block, String name, T base, String... oreDictionaryNames) {
		return registerBlock(block, name, base.getDefaultState().getMaterial(), base.getSoundType(), base.getBlockHardness(null, null, null), base.getExplosionResistance(null) * 5, base.getHarvestTool(base.getDefaultState()), base.getHarvestLevel(base.getDefaultState()), oreDictionaryNames);
	}

	public static <T extends Item> T registerItem(T item, String name, String... oreDictionaryNames) {
		ResourceLocation loc = new ResourceLocation(Bewitchment.MODID, name);
		item.setRegistryName(loc);
		item.setTranslationKey(loc.toString().replace(":", "."));
		item.setCreativeTab(Bewitchment.proxy.tab);
		ForgeRegistries.ITEMS.register(item);
		Bewitchment.proxy.registerTexture(item, "normal");
		for (String ore : oreDictionaryNames) OreDictionary.registerOre(ore, item);
		return item;
	}

	public static Item registerItem(String name, String... oreDictionaryNames) {
		return registerItem(new Item(), name, oreDictionaryNames);
	}
}