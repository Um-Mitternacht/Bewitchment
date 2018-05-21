package com.bewitchment.common.core.event;

import com.bewitchment.common.core.capability.cauldronTeleports.CauldronTeleportProvider;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class CapabilityCauldronTPAttach {

	public static final ResourceLocation cap = new ResourceLocation(LibMod.MOD_ID, "world_cauldron_tp");

	@SubscribeEvent
	public static void onAttach(AttachCapabilitiesEvent<World> evt) {
		evt.addCapability(cap, new CauldronTeleportProvider());
	}

}
