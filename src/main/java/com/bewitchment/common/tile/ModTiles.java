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
	private static final String WITCH_ALTAR = "witch_altar";
	private static final String THREAD_SPINNER = "thread_spinner";
	private static final String BARREL = "barrel";
	private static final String RITUAL_GLYPH = "glyph";

	private ModTiles() {
	}

	public static void registerAll() {

		GameRegistry.registerTileEntity(TileCauldron.class, CAULDRON);
		GameRegistry.registerTileEntity(TileCandle.class, CANDLE);
		GameRegistry.registerTileEntity(TileApiary.class, APIARY);
		GameRegistry.registerTileEntity(TileOven.class, OVEN);
		GameRegistry.registerTileEntity(TileEntityWitchAltar.class, WITCH_ALTAR);
		GameRegistry.registerTileEntity(TileEntityThreadSpinner.class, THREAD_SPINNER);
		GameRegistry.registerTileEntity(TileEntityBarrel.class, BARREL);
		GameRegistry.registerTileEntity(TileEntityGlyph.class, RITUAL_GLYPH);
	}
}
