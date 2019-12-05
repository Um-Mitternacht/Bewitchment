package com.bewitchment.core;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;

import java.util.Map;

public class BewitchmentFMLSetupHook implements IFMLCallHook {
	private static final Logger logger = LogManager.getLogger("Bewitchment Forge Core");
	
	@Override
	public void injectData(Map<String, Object> map) {
	
	}
	
	@Override
	public Void call() throws Exception {
		logger.debug("initializing Mixin environment and configurations");
		MixinBootstrap.init();
		Mixins.addConfiguration("mixins.bewitchment.json");
		return null;
	}
}
