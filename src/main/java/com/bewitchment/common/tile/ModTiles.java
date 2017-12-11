package com.bewitchment.common.tile;

import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * This class was created by Arekkuusu on 09/03/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModTiles {

	private static final String CAULDRON = "cauldron";
	private static final String CANDLE = "candle";
	private static final String APIARY = "apiary";
	private static final String OVEN = "oven";

	private ModTiles() {
	}

	public static void registerAll() {

		GameRegistry.registerTileEntity(TileCauldron.class, CAULDRON);
		GameRegistry.registerTileEntity(TileCandle.class, CANDLE);
		GameRegistry.registerTileEntity(TileApiary.class, APIARY);
		GameRegistry.registerTileEntity(TileOven.class, OVEN);
	}
}
