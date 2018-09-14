package com.bewitchment.common.content.cauldron.behaviours;

import com.bewitchment.common.tile.tiles.TileEntityCauldron;

public class DefaultBehaviours {

	public ICauldronBehaviour IDLE, FAILING, BREWING, STEW, CRAFTING, CLEANING, LAVA;

	public void init(TileEntityCauldron tile) {
		IDLE = new CauldronBehaviourIdle();
		LAVA = new CauldronBehaviourLava();
		STEW = new CauldronBehaviourStew();
		FAILING = new CauldronBehaviourFailing();
		CLEANING = new CauldronBehaviourClean();
		BREWING = new CauldronBehaviourBrewing();
		CRAFTING = new CauldronBehaviourCrafting();

		IDLE.setCauldron(tile);
		LAVA.setCauldron(tile);
		STEW.setCauldron(tile);
		FAILING.setCauldron(tile);
		CLEANING.setCauldron(tile);
		BREWING.setCauldron(tile);
		CRAFTING.setCauldron(tile);

		tile.addBehaviour(IDLE);
		tile.addBehaviour(LAVA);
		tile.addBehaviour(STEW);
		tile.addBehaviour(FAILING);
		tile.addBehaviour(CLEANING);
		tile.addBehaviour(BREWING);
		tile.addBehaviour(CRAFTING);

		tile.setBehaviour(IDLE);
	}
}
