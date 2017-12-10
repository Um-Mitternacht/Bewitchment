package com.witchcraft.api;

import com.witchcraft.api.crop.Crop;
import com.witchcraft.api.crop.ICrop;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class was created by Arekkuusu on 14/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public final class CropRegistry {

	private static final Map<Crop, Item> seeds = new LinkedHashMap<>();
	private static final Map<Crop, Block> crops = new LinkedHashMap<>();
	private static final Map<Crop, Item> foods = new LinkedHashMap<>();

	private CropRegistry() {
	}

	/**
	 * Register a Crop to the {@link CropRegistry}.
	 * <p>
	 * The Item Seed needs to be different, for ex the Kelp seed,
	 * that needs to be placed on water so it uses a different placement recipeDropLogic.
	 * </p>
	 *
	 * @param crop     The Crop enum
	 * @param cropItem The item this Crop will drop when harvested
	 * @param seedItem The seed that will place the Crop
	 */
	public static <T extends Block & ICrop> void registerCrop(Crop crop, T placed, Item cropItem, Item seedItem) {
		placed.setCrop(cropItem);
		placed.setSeed(seedItem);

		CropRegistry.getSeeds().put(crop, seedItem);
		CropRegistry.getCrops().put(crop, placed);
		CropRegistry.getFoods().put(crop, cropItem);
	}

	public static Map<Crop, Item> getSeeds() {
		return seeds;
	}

	public static Map<Crop, Block> getCrops() {
		return crops;
	}

	public static Map<Crop, Item> getFoods() {
		return foods;
	}
}
