package com.bewitchment.common.block.head;

import com.bewitchment.common.lib.LibMod;

import net.minecraft.util.ResourceLocation;

/**
 * @author its_meow
 *
 * Mar 7, 2019
 */
public class HeadTextures {
	
	
	// These are needed on the server therefore I must copy them from the renderers so as to not load nonexistent classes
	
	// Package visibility
	static final ResourceLocation[] HELL_HOUND = new ResourceLocation[6];

	static {
		HELL_HOUND[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_1.png");
		HELL_HOUND[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_2.png");
		HELL_HOUND[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_3.png");
		HELL_HOUND[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_4.png");
		HELL_HOUND[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_5.png");
		HELL_HOUND[5] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/hellhound_6.png");
	}
	
	static final ResourceLocation[] ALPHA_HELL_HOUND = new ResourceLocation[6];

	static {
		ALPHA_HELL_HOUND[0] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_1.png");
		ALPHA_HELL_HOUND[1] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_2.png");
		ALPHA_HELL_HOUND[2] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_3.png");
		ALPHA_HELL_HOUND[3] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_4.png");
		ALPHA_HELL_HOUND[4] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_5.png");
		ALPHA_HELL_HOUND[5] = new ResourceLocation(LibMod.MOD_ID, "textures/entity/mobs/spirits/bosses/hellhoundalpha_6.png");
	}
	
}
