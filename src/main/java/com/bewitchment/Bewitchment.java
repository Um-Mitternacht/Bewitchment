package com.bewitchment;

import com.bewitchment.common.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@SuppressWarnings({"WeakerAccess", "unused"})
@Mod(modid = Bewitchment.MODID, name = Bewitchment.NAME, version = Bewitchment.VERSION)
public class Bewitchment {
	public static final String MODID = "bewitchment", NAME = "Bewitchment", VERSION = "0.20";

	@SidedProxy(serverSide = "com.bewitchment.common.CommonProxy", clientSide = "com.bewitchment.client.ClientProxy")
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		proxy.preInit(event);
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}
}