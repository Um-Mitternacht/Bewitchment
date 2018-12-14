package com.bewitchment.common.integration.thaumcraft;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.natural.BlockGemOre.Gem;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import thaumcraft.common.entities.monster.*;
import thaumcraft.common.entities.monster.boss.*;
import thaumcraft.common.entities.monster.cult.EntityCultist;
import thaumcraft.common.entities.monster.cult.EntityCultistPortalLesser;
import thaumcraft.common.entities.monster.tainted.*;

/**
 * Created by Joseph on 5/6/2018.
 * <b>DON'T USE THIS OUTSIDE OF ThaumcraftCompatBridge!</b>
 */
@Deprecated
public class ThaumcraftCompat {
	
	/*
	 * DO NOT USE THIS CLASS DIRECTLY.
	 * USE METHODS IN ThaumcraftCompatBridge
	 * 
	 * DO NOT CHANGE VISIBILITY. DEFAULT VISIBILITY ALLOWS THE BRIDGE
	 * TO BE THE ONLY CLASS TO ACCESS THESE METHODS
	 */

	// This spot will register our new aspects
	static final Aspect SUN = getOrCreateAspect("sol", 0XFFD300, new Aspect[]{Aspect.FIRE, Aspect.LIGHT}, new ResourceLocation(LibMod.MOD_ID, "textures/thaumcraft/sol.png"), 1);
	static final Aspect MOON = getOrCreateAspect("luna", 0X808080, new Aspect[]{Aspect.EARTH, Aspect.DARKNESS}, new ResourceLocation(LibMod.MOD_ID, "textures/thaumcraft/luna.png"), 1);
	static final Aspect STAR = getOrCreateAspect("stellae", 0X73C2FB, new Aspect[]{SUN, Aspect.VOID}, new ResourceLocation(LibMod.MOD_ID, "textures/thaumcraft/stellae.png"), 1);
	static final Aspect DEMON = getOrCreateAspect("diabolus", 0X960018, new Aspect[]{Aspect.SOUL, Aspect.AVERSION}, new ResourceLocation(LibMod.MOD_ID, "textures/thaumcraft/diabolus.png"), 1);
	private static final ThaumcraftCompat INSTANCE = new ThaumcraftCompat();
	private static boolean registered = false;

	static Aspect getOrCreateAspect(String tag, int color, Aspect[] components, ResourceLocation image, int blend) {
		Aspect a = Aspect.getAspect(tag);
		if (a != null) return a;
		return new Aspect(tag, color, components, image, blend);
	}

	@Deprecated
	static boolean isEldritchMob(Entity target) {
		return target instanceof EntityEldritchGuardian || target instanceof EntityEldritchCrab || target instanceof EntityMindSpider || target instanceof EntityEldritchGolem || target instanceof EntityEldritchWarden;
	}

	@Deprecated
	static boolean isTCSpiritMob(Entity target) {
		return target instanceof EntityEldritchGuardian || target instanceof EntityEldritchCrab || target instanceof EntityFireBat || target instanceof EntityWisp || target instanceof EntityPech || target instanceof EntityMindSpider || target instanceof EntityEldritchGolem || target instanceof EntityEldritchWarden;
	}

	@Deprecated
	static boolean isCrimsonCultMob(Entity target) {
		return target instanceof EntityCultistLeader || target instanceof EntityCultist || target instanceof EntityCultistPortalGreater || target instanceof EntityCultistPortalLesser;
	}

	@Deprecated
	static boolean isThaumcraftMob(Entity target) {
		return target instanceof EntityEldritchGuardian || target instanceof EntityEldritchCrab || target instanceof EntityFireBat || target instanceof EntityWisp || target instanceof EntityPech || target instanceof EntityMindSpider || target instanceof EntityEldritchGolem || target instanceof EntityEldritchWarden || target instanceof EntityCultistLeader || target instanceof EntityCultist || target instanceof EntityCultistPortalGreater || target instanceof EntityCultistPortalLesser || target instanceof EntityTaintacleGiant || target instanceof EntityTaintacle || target instanceof EntityTaintCrawler || target instanceof EntityTaintSeedPrime || target instanceof EntityTaintSeed || target instanceof EntityTaintSwarm || target instanceof EntityBrainyZombie || target instanceof EntityThaumicSlime;
	}

	@Deprecated
	static boolean isTaintedMob(Entity target) {
		return target instanceof EntityTaintacleGiant || target instanceof EntityTaintacle || target instanceof EntityTaintCrawler || target instanceof EntityTaintSeedPrime || target instanceof EntityTaintSeed || target instanceof EntityTaintSwarm || target instanceof EntityThaumicSlime;
	}

	@Deprecated
	static void register() {
		if (!registered) {
			registered = true;
			MinecraftForge.EVENT_BUS.register(INSTANCE);
		} else {
			throw new RuntimeException("You can only call ThaumcraftCompat.register() once");
		}
	}

	@SuppressWarnings("deprecation")
	@SubscribeEvent
	public void aspectRegistrationEvent(AspectRegistryEvent evt) {

		// Items
		evt.register.registerObjectTag(new ItemStack(ModItems.ectoplasm), new AspectList().add(Aspect.SOUL, 5).add(Aspect.DEATH, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.tarots), new AspectList().add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.heart), new AspectList().add(Aspect.DEATH, 7).add(Aspect.MAN, 7));
		evt.register.registerObjectTag(new ItemStack(ModItems.wood_ash), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.moonbell), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.aconitum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.asphodel), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.belladonna), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2).add(Aspect.MAGIC, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.chrysanthemum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(SUN, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.garlic), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.AVERSION, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.ginger), new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 2).add(Aspect.SENSES, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.hellebore), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.FIRE, 2).add(DEMON, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.kenaf), new AspectList().add(Aspect.PLANT, 2).add(Aspect.CRAFT, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.kelp), new AspectList().add(Aspect.PLANT, 2).add(Aspect.WATER, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.lavender), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.mint), new AspectList().add(Aspect.PLANT, 2).add(Aspect.COLD, 2).add(Aspect.SENSES, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.infested_wheat), new AspectList().add(Aspect.PLANT, 2).add(Aspect.UNDEAD, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.oak_apple_gall), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.ENTROPY, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.mandrake_root), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.EARTH, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.sagebrush), new AspectList().add(Aspect.PLANT, 2).add(Aspect.VOID, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.thistle), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AVERSION, 2).add(Aspect.PROTECT, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.silphium), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DESIRE, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.tulsi), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.white_sage), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2).add(Aspect.SOUL, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.witchweed), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.wormwood), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SOUL, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.eye_of_old), new AspectList().add(Aspect.SENSES, 4).add(Aspect.WATER, 2).add(Aspect.ELDRITCH, 2).add(STAR, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.eye_of_ancient), new AspectList().add(Aspect.SENSES, 4).add(Aspect.WATER, 2).add(Aspect.ELDRITCH, 2).add(STAR, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.chromatic_quill), new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 2).add(Aspect.AIR, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.tongue_of_dog), new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 4).add(Aspect.DEATH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.needle_bone), new AspectList().add(Aspect.DEATH, 2).add(Aspect.CRAFT, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.mortar_and_pestle), new AspectList().add(Aspect.MOTION, 8).add(Aspect.CRAFT, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.mortar_and_pestle_stone), new AspectList().add(Aspect.MOTION, 8).add(Aspect.EARTH, 8).add(Aspect.CRAFT, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.salt), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.honey), new AspectList().add(Aspect.DESIRE, 6).add(Aspect.LIFE, 6).add(SUN, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.girdle_of_the_wooded), new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIFE, 20).add(Aspect.PROTECT, 20).add(Aspect.MAGIC, 20));
		evt.register.registerObjectTag(new ItemStack(ModItems.nazar), new AspectList().add(Aspect.DESIRE, 8).add(Aspect.METAL, 8).add(Aspect.CRYSTAL, 8).add(Aspect.PROTECT, 8).add(Aspect.MAGIC, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.horseshoe), new AspectList().add(Aspect.METAL, 8).add(Aspect.BEAST, 8).add(Aspect.PROTECT, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.wax), new AspectList().add(Aspect.CRAFT, 8).add(Aspect.ALCHEMY, 8).add(SUN, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.taglock), new AspectList().add(Aspect.SOUL, 8).add(Aspect.LIFE, 8));
		evt.register.registerObjectTag(new ItemStack(ModItems.cold_iron_ingot), new AspectList().add(Aspect.AVERSION, 15).add(Aspect.COLD, 15).add(Aspect.METAL, 15));
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_honeycomb), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.VOID, 4).add(SUN, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.honeycomb), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.LIFE, 4).add(SUN, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.GARNET.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4).add(STAR, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.NUUMMITE.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MAGIC, 4).add(Aspect.COLD, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.TIGERS_EYE.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.SENSES, 4).add(Aspect.LIGHT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.TOURMALINE.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AURA, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.BLOODSTONE.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.LIFE, 4).add(SUN, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.JASPER.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AVERSION, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.MALACHITE.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.EARTH, 4).add(Aspect.EXCHANGE, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.AMETHYST.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.ALCHEMY, 4).add(MOON, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.gem, 1, Gem.ALEXANDRITE.ordinal()), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MIND, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.glass_jar), new AspectList().add(Aspect.VOID, 6).add(Aspect.CRYSTAL, 6));
		evt.register.registerObjectTag(new ItemStack(ModItems.spectral_dust), new AspectList().add(Aspect.VOID, 4).add(Aspect.SOUL, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.hoof), new AspectList().add(Aspect.BEAST, 4).add(Aspect.MOTION, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.equine_tail), new AspectList().add(Aspect.BEAST, 4).add(Aspect.AIR, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.silver_scales), new AspectList().add(Aspect.BEAST, 4).add(Aspect.METAL, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.carnivorous_tooth), new AspectList().add(Aspect.BEAST, 4).add(Aspect.AVERSION, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.dimensional_sand), new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.DARKNESS, 4).add(STAR, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.envenomed_fang), new AspectList().add(Aspect.BEAST, 4).add(Aspect.ALCHEMY, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.wool_of_bat), new AspectList().add(Aspect.BEAST, 4).add(Aspect.AIR, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.diabolic_vein), new AspectList().add(Aspect.CRAFT, 4).add(DEMON, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.golden_thread), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.DESIRE, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.witches_stitching), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.MAGIC, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.pure_filament), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.AURA, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.soul_string), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.SOUL, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.regal_silk), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.SENSES, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk, 1, 1), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(SUN, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk, 1, 2), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(Aspect.ELDRITCH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.ritual_chalk, 1, 3), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(DEMON, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_hellebore), new AspectList().add(Aspect.PLANT, 1).add(DEMON, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_silphium), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DESIRE, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_garlic), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.AVERSION, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_lavender), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_mandrake), new AspectList().add(Aspect.PLANT, 1).add(Aspect.MAGIC, 1).add(Aspect.EARTH, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_tulsi), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AURA, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_sagebrush), new AspectList().add(Aspect.PLANT, 1).add(Aspect.VOID, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_white_sage), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AURA, 1).add(Aspect.SOUL, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_ginger), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.FIRE, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_aconitum), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_wormwood), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SOUL, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_asphodel), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DARKNESS, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_chrysanthemum), new AspectList().add(Aspect.PLANT, 1).add(SUN, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_mint), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.COLD, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_belladonna), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1).add(Aspect.MAGIC, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_thistle), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AVERSION, 1).add(Aspect.PROTECT, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_kenaf), new AspectList().add(Aspect.PLANT, 1).add(Aspect.CRAFT, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.seed_kelp), new AspectList().add(Aspect.PLANT, 1).add(Aspect.WATER, 1));
		evt.register.registerObjectTag(new ItemStack(ModItems.spanish_moss), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AIR, 2).add(Aspect.MAGIC, 2));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.spanish_moss), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AIR, 2).add(Aspect.MAGIC, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.owlets_wing), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2).add(MOON, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.ravens_feather), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2).add(Aspect.DARKNESS, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.graveyard_dust), new AspectList().add(Aspect.DEATH, 3).add(Aspect.SOUL, 2).add(MOON, 2));
		evt.register.registerObjectTag(new ItemStack(ModItems.adders_fork), new AspectList().add(Aspect.DEATH, 4).add(DEMON, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.filet_of_fenny_snake), new AspectList().add(Aspect.DEATH, 4).add(Aspect.LIFE, 4).add(DEMON, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.snake_venom), new AspectList().add(Aspect.DEATH, 9).add(Aspect.CRYSTAL, 9));
		evt.register.registerObjectTag(new ItemStack(ModItems.eye_of_newt), new AspectList().add(Aspect.WATER, 4).add(Aspect.SENSES, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.toe_of_frog), new AspectList().add(Aspect.WATER, 4).add(Aspect.ALCHEMY, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.blindworms_sting), new AspectList().add(Aspect.BEAST, 4).add(Aspect.EARTH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.lizard_leg), new AspectList().add(Aspect.MOTION, 4).add(Aspect.EARTH, 4));
		evt.register.registerObjectTag(new ItemStack(ModItems.grilled_watermelon), new AspectList().add(Aspect.LIFE, 3).add(Aspect.DESIRE, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.yew_aril), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MIND, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.juniper_berries), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MAGIC, 3));
		//Todo: Dynamic aspects based on brew contents
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_brew_drink), new AspectList().add(Aspect.ALCHEMY, 6).add(Aspect.CRYSTAL, 6).add(Aspect.VOID, 6));
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_brew_linger), new AspectList().add(Aspect.ALCHEMY, 6).add(Aspect.CRYSTAL, 6).add(Aspect.AIR, 6).add(Aspect.VOID, 6));
		evt.register.registerObjectTag(new ItemStack(ModItems.empty_brew_splash), new AspectList().add(Aspect.ALCHEMY, 6).add(Aspect.CRYSTAL, 6).add(Aspect.EARTH, 6).add(Aspect.VOID, 6));

		//Fumes
		evt.register.registerObjectTag(new ItemStack(ModItems.fume), new AspectList().add(Aspect.EARTH, 11).add(Aspect.WATER, 11));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 1), new AspectList().add(Aspect.EARTH, 11).add(Aspect.WATER, 11).add(Aspect.FIRE, 11));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 2), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 3), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.SOUL, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 4), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 5), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 6), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.DARKNESS, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 7), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.VOID, 5).add(Aspect.AURA, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 8), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.DARKNESS, 5).add(DEMON, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 9), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.SOUL, 5).add(Aspect.EXCHANGE, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 10), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.BEAST, 5).add(Aspect.EXCHANGE, 3).add(Aspect.LIFE, 3));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 11), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.FIRE, 5).add(DEMON, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 12), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.ELDRITCH, 5).add(STAR, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 13), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.FIRE, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 14), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.AIR, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 15), new AspectList().add(Aspect.EARTH, 10).add(Aspect.WATER, 5).add(Aspect.SENSES, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 16), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 10).add(Aspect.AIR, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 17), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.DEATH, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 18), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.LIFE, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 19), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.MIND, 5));
		evt.register.registerObjectTag(new ItemStack(ModItems.fume, 1, 20), new AspectList().add(Aspect.EARTH, 5).add(Aspect.WATER, 5).add(Aspect.PLANT, 5).add(Aspect.MAGIC, 5));

		//Todo: Make spells choose their aspects based on NBT Data.
		//Spells
		evt.register.registerObjectTag(new ItemStack(ModItems.spell_page, 1, 0), new AspectList().add(Aspect.MAGIC, 6).add(Aspect.TOOL, 6).add(STAR, 6));

		// Blocks
		evt.register.registerObjectTag(new ItemStack(ModBlocks.torchwood), new AspectList().add(Aspect.PLANT, 8).add(Aspect.FIRE, 8).add(Aspect.MAGIC, 8));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.ember_grass), new AspectList().add(Aspect.PLANT, 8).add(Aspect.FIRE, 8).add(Aspect.AVERSION, 8));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.moonbell), new AspectList().add(Aspect.PLANT, 6).add(Aspect.DARKNESS, 6).add(Aspect.MAGIC, 6).add(MOON, 6));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.crystal_ball), new AspectList().add(Aspect.CRYSTAL, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.tarot_table), new AspectList().add(Aspect.EARTH, 25).add(Aspect.PLANT, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.scorned_bricks), new AspectList().add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10).add(DEMON, 10));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.embittered_bricks), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.fake_ice), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.goblet), new AspectList().add(Aspect.METAL, 15).add(Aspect.MAGIC, 15).add(Aspect.VOID, 15));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.thread_spinner), new AspectList().add(Aspect.PLANT, 30).add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 25));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.witchfire), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.oven), new AspectList().add(Aspect.FIRE, 25).add(Aspect.METAL, 30).add(Aspect.CRAFT, 30));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.nethersteel), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.METAL, 5).add(DEMON, 10));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.beehive), new AspectList().add(Aspect.BEAST, 15).add(Aspect.DESIRE, 15).add(Aspect.PLANT, 13).add(SUN, 10));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.salt_ore), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.coquina), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));

		//Ores
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 0), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4).add(Aspect.EARTH, 4).add(STAR, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 1), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.COLD, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 2), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.SENSES, 4).add(Aspect.EARTH, 4).add(Aspect.LIGHT, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 3), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AURA, 4).add(Aspect.EARTH, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 4), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.LIFE, 4).add(SUN, 4).add(Aspect.EARTH, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 5), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AVERSION, 4).add(Aspect.EARTH, 4).add(Aspect.PROTECT, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 6), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.EARTH, 6).add(Aspect.EXCHANGE, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 7), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.ALCHEMY, 4).add(MOON, 4).add(Aspect.EARTH, 4));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.gem_ore, 1, 8), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MIND, 4).add(Aspect.EARTH, 4).add(Aspect.PROTECT, 4));

		//Baubles
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_ruby_orb), new AspectList().add(Aspect.METAL, 25).add(Aspect.DESIRE, 25).add(Aspect.CRYSTAL, 25));
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_aquamarine_crown), new AspectList().add(Aspect.METAL, 15).add(Aspect.WATER, 25).add(STAR, 15).add(Aspect.LIGHT, 10));
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_adamantine_star_ring), new AspectList().add(Aspect.CRYSTAL, 25).add(Aspect.DESIRE, 15).add(STAR, 15));
		evt.register.registerObjectTag(new ItemStack(ModItems.talisman_watching_eye), new AspectList().add(Aspect.METAL, 25).add(Aspect.ELDRITCH, 15).add(Aspect.SENSES, 15));

		//Silver
		evt.register.registerObjectTag(new ItemStack(ModItems.silver_ingot), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5).add(MOON, 3));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.silver_ore), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5).add(Aspect.EARTH, 5).add(MOON, 3));

		//Saplings
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 0), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MIND, 3));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 1), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MAGIC, 3));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 2), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 3));
		evt.register.registerObjectTag(new ItemStack(ModBlocks.sapling, 1, 3), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.DEATH, 3));

		//Technical
		evt.register.registerObjectTag(new ItemStack(ModBlocks.salt_barrier), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.EARTH, 5));

		//Vampire Gear
		evt.register.registerObjectTag(new ItemStack(ModItems.sanguine_fabric), new AspectList().add(Aspect.CRAFT, 10).add(Aspect.UNDEAD, 10).add(MOON, 10).add(DEMON, 10));

		//Add some of our aspects to existing items in vanilla
		//Use this sparingly. Please run over any future additions to this part of the file with Sunconure11.
		//If new aspects must be added to an item from vanilla, try and preserve as much of the original aspects as possible.
		//The same applies if you try and add aspects to items from other mods.
		evt.register.registerObjectTag(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new AspectList().add(Aspect.PLANT, 5).add(Aspect.SENSES, 5).add(SUN, 3).add(Aspect.AIR, 1).add(Aspect.LIFE, 1));

		//Entities
		ThaumcraftApi.registerEntityTag("entity_owl", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10).add(MOON, 8));
		ThaumcraftApi.registerEntityTag("entity_snake", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AVERSION, 10).add(DEMON, 8));
		ThaumcraftApi.registerEntityTag("entity_raven", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10).add(Aspect.DARKNESS, 8));
		ThaumcraftApi.registerEntityTag("entity_toad", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10).add(Aspect.ALCHEMY, 8));

		ThaumcraftApi.registerEntityTag("entity_lizard", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(SUN, 8));
		ThaumcraftApi.registerEntityTag("entity_newt", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10).add(Aspect.MOTION, 8));
		ThaumcraftApi.registerEntityTag("entity_blindworm", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.SENSES, 8));

	}
}
