package com.bewitchment.common.core.proxy;

import com.bewitchment.client.fx.ParticleF;

import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface ISidedProxy {

	void preInit(FMLPreInitializationEvent event);

	void init(FMLInitializationEvent event);

	void displayRecordText(ITextComponent text);

	void spawnParticle(ParticleF particleF, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, int... args);
	
	boolean isFancyGraphicsEnabled();
}
