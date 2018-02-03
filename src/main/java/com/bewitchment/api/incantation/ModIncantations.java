package com.bewitchment.api.incantation;

import com.bewitchment.common.incantation.IncantationCandlelight;
import com.bewitchment.common.incantation.IncantationFisheye;
import com.bewitchment.common.incantation.IncantationHeal;
import com.bewitchment.common.incantation.IncantationSnuff;

import java.util.HashMap;
import java.util.Map;

/**
 * This class was created by Arekkuusu on 19/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class ModIncantations {

	//Todo: Convert all of these into spells.

	private static final Map<String, IIncantation> commands = new HashMap<>();

	private ModIncantations() {
	}

	public static void init() {
		addIncantation("medico", new IncantationHeal());
		addIncantation("lux", new IncantationCandlelight());
		addIncantation("tenebrae", new IncantationSnuff());
		addIncantation("aqua", new IncantationFisheye());
	}

	private static void addIncantation(String name, IIncantation incantation) {
		getCommands().put(name, incantation);
	}

	public static Map<String, IIncantation> getCommands() {
		return commands;
	}
}
