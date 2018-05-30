package com.bewitchment.client.handler;

import com.bewitchment.client.core.IModelRegister;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings({"WeakerAccess", "ConstantConditions"})
@SideOnly(Side.CLIENT)
public final class ModelHandler {

	private ModelHandler() {
	}

	/**
	 * Register all Item and Block models from the registry.
	 */
	public static void registerModels() {
		for (Block block : Block.REGISTRY) {
			if (block instanceof IModelRegister)
				((IModelRegister) block).registerModel();
		}

		for (Item item : Item.REGISTRY) {
			if (item instanceof IModelRegister)
				((IModelRegister) item).registerModel();
		}
	}

	public static void registerForgeModel(Block block, int meta, String variant) {
		Item item = Item.getItemFromBlock(block);
		if (item == Items.AIR) throw new UnsupportedOperationException("This block has no Item!");
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), variant);
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	public static void registerForgeModel(Item item, int meta, String variant) {
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), variant);
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	public static void registerModel(Block block, int meta) {
		Item item = Item.getItemFromBlock(block);
		if (item != Items.AIR) {
			registerModel(item, meta);
		}
	}

	public static void registerModel(Item item, int meta) {
		ModelResourceLocation modelResourceLocation = new ModelResourceLocation(item.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, modelResourceLocation);
	}

	public static <T extends Enum<T> & IStringSerializable> void registerModel(Block block, Class<T> clazz) {
		Item item = Item.getItemFromBlock(block);
		if (item == Items.AIR) throw new UnsupportedOperationException("This block has no Item!");
		registerModel(item, clazz);
	}

	public static <T extends Enum<T> & IStringSerializable> void registerModel(Item item, Class<T> clazz) {
		for (T t : clazz.getEnumConstants()) {
			ResourceLocation location = new ResourceLocation(item.getRegistryName() + "_" + t.getName());
			ModelResourceLocation modelResourceLocation = new ModelResourceLocation(location, "inventory");
			ModelLoader.setCustomModelResourceLocation(item, t.ordinal(), modelResourceLocation);
		}
	}
}
