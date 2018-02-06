package com.bewitchment.common.lib;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public final class LibMod {

	//ID for MOD
	public static final String MOD_ID = "bewitchment";

	//Name of MOD
	public static final String MOD_NAME = "Bewitchment";

	//Version of MOD
	public static final String MOD_VER = "@VERSION@";

	//Dependency
	public static final String DEPENDENCIES = "required-after:forge@[14.23.2.2611,];required-after:JEI@[4.8.5.138,];required-after:Waila@[1.8.23-B38_1.12,];required-after:baubles@[1.5.2,];required-after:craftstudioapi@[0.3.0,]";

	//Client proxy location
	public static final String PROXY_CLIENT = "com.bewitchment.client.core.ClientProxy";

	//Server proxy location
	public static final String PROXY_COMMON = "com.bewitchment.common.core.proxy.ServerProxy";

	private LibMod() {
	}
}
