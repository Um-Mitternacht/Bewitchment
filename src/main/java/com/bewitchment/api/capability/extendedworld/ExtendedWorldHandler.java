package com.bewitchment.api.capability.extendedworld;

import com.bewitchment.Bewitchment;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class ExtendedWorldHandler {
	private static final ResourceLocation LOC = new ResourceLocation(Bewitchment.MODID, "extended_world");
	
	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<World> event) {
		event.addCapability(LOC, new ExtendedWorld());
	}
}