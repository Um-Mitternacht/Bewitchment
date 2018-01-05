package com.bewitchment.common.core.command;

import java.util.HashMap;
import java.util.Map;

/**
 * This class was created by Arekkuusu on 19/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModCommands {

	//Todo: Convert all of these into spells.

	static final Map<String, IIncantation> commands = new HashMap<>();

	private ModCommands() {
	}

	public static void init() {
		addIncantation("medico", new IncantationHeal());
		addIncantation("lux", new IncantationCandlelight());
		addIncantation("tenebrae", new IncantationSnuff());
		addIncantation("aqua", new IncantationFisheye());
	}

	private static void addIncantation(String name, IIncantation incantation) {
		commands.put(name, incantation);
	}
}
