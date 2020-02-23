package com.bewitchment.client.misc;

import com.bewitchment.Bewitchment;
import com.bewitchment.client.model.block.ModelBaphometIdol;
import com.bewitchment.client.model.block.ModelHerneIdol;
import com.bewitchment.client.model.block.ModelLeonardIdol;
import com.bewitchment.client.model.block.ModelLilithIdol;
import com.bewitchment.registry.ModObjects;
import net.minecraft.client.model.ModelBase;
import net.minecraft.util.ResourceLocation;

/**
 * Created by Joseph on 2/22/2020.
 */
public class Idols {
	
	// Register lenny model
	ModelBase lenny = new ModelLeonardIdol();
	registerIdol(ModObjects.stone_leonard_idol, lenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/stone.png"));
	registerIdol(ModObjects.terracotta_leonard_idol, lenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/clay.png"));
	registerIdol(ModObjects.gold_leonard_idol, lenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/gold.png"));
	registerIdol(ModObjects.nether_brick_leonard_idol, lenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/nether_brick.png"));
	registerIdol(ModObjects.nethersteel_leonard_idol, lenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/nethersteel.png"));
	registerIdol(ModObjects.scorned_brick_leonard_idol, lenny, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/leonard/scorned_brick.png"));
	
	// Register lilith model
	ModelBase lilith = new ModelLilithIdol();
	registerIdol(ModObjects.stone_lilith_idol, lilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/stone.png"));
	registerIdol(ModObjects.terracotta_lilith_idol, lilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/clay.png"));
	registerIdol(ModObjects.gold_lilith_idol, lilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/gold.png"));
	registerIdol(ModObjects.nether_brick_lilith_idol, lilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/nether_brick.png"));
	registerIdol(ModObjects.nethersteel_lilith_idol, lilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/nethersteel.png"));
	registerIdol(ModObjects.scorned_brick_lilith_idol, lilith, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/lilith/scorned_brick.png"));
	
	// Register baphomet model
	ModelBase baphomet = new ModelBaphometIdol();
	registerIdol(ModObjects.stone_baphomet_idol, baphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/stone.png"));
	registerIdol(ModObjects.terracotta_baphomet_idol, baphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/clay.png"));
	registerIdol(ModObjects.gold_baphomet_idol, baphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/gold.png"));
	registerIdol(ModObjects.nether_brick_baphomet_idol, baphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/nether_brick.png"));
	registerIdol(ModObjects.nethersteel_baphomet_idol, baphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/nethersteel.png"));
	registerIdol(ModObjects.scorned_brick_baphomet_idol, baphomet, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/baphomet/scorned_brick.png"));
	
	// Register herne model
	ModelBase herne = new ModelHerneIdol();
	registerIdol(ModObjects.stone_herne_idol, herne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/stone.png"));
	registerIdol(ModObjects.terracotta_herne_idol, herne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/clay.png"));
	registerIdol(ModObjects.gold_herne_idol, herne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/gold.png"));
	registerIdol(ModObjects.nether_brick_herne_idol, herne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/nether_brick.png"));
	registerIdol(ModObjects.nethersteel_herne_idol, herne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/nethersteel.png"));
	registerIdol(ModObjects.scorned_brick_herne_idol, herne, new ResourceLocation(Bewitchment.MODID, "textures/blocks/idol/herne/scorned_brick.png"));
}
