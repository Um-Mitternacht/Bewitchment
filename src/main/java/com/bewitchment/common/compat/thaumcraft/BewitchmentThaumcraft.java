package com.bewitchment.common.compat.thaumcraft;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

/**
 * Created by Joseph on 5/20/2019.
 */
@SuppressWarnings("deprecation")
public class BewitchmentThaumcraft {
	public static final Aspect SUN = getOrCreateAspect("sol", 0xffd300, new Aspect[]{Aspect.FIRE, Aspect.LIGHT}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/sol.png"));
	public static final Aspect MOON = getOrCreateAspect("luna", 0x808080, new Aspect[]{Aspect.EARTH, Aspect.DARKNESS}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/luna.png"));
	public static final Aspect STAR = getOrCreateAspect("stellae", 0x73c2fb, new Aspect[]{SUN, Aspect.VOID}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/stellae.png"));
	public static final Aspect DEMON = getOrCreateAspect("diabolus", 0x960018, new Aspect[]{Aspect.SOUL, Aspect.AVERSION}, new ResourceLocation(Bewitchment.MODID, "textures/thaumcraft/diabolus.png"));

	private static Aspect getOrCreateAspect(String tag, int color, Aspect[] components, ResourceLocation image) {
		Aspect a = Aspect.getAspect(tag);
		if (a != null) return a;
		return new Aspect(tag, color, components, image, 1);
	}

	@SubscribeEvent
	public void aspectRegistrationEvent(AspectRegistryEvent event) {
		// Items
		event.register.registerObjectTag(new ItemStack(ModObjects.ectoplasm), new AspectList().add(Aspect.SOUL, 5).add(Aspect.DEATH, 5));
		//event.register.registerObjectTag(new ItemStack(ModObjects.tarot_deck), new AspectList().add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.heart), new AspectList().add(Aspect.DEATH, 7).add(Aspect.MAN, 7));
		event.register.registerObjectTag(new ItemStack(ModObjects.wood_ash), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.aconitum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.belladonna), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2).add(Aspect.MAGIC, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.garlic), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.AVERSION, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.hellebore), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.FIRE, 2).add(DEMON, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.oak_apple_gall), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.ENTROPY, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.mandrake_root), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.EARTH, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.white_sage), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2).add(Aspect.SOUL, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.wormwood), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SOUL, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.eye_of_old), new AspectList().add(Aspect.SENSES, 4).add(Aspect.WATER, 2).add(Aspect.ELDRITCH, 2).add(STAR, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.tongue_of_dog), new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 4).add(Aspect.DEATH, 4));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.needle_bone), new AspectList().add(Aspect.DEATH, 2).add(Aspect.CRAFT, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.salt), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		//event.register.registerObjectTag(new ItemStack(ModObjects.girdle_of_the_dryad), new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIFE, 20).add(Aspect.PROTECT, 20).add(Aspect.MAGIC, 20));
		//event.register.registerObjectTag(new ItemStack(ModObjects.nazar), new AspectList().add(Aspect.DESIRE, 8).add(Aspect.METAL, 8).add(Aspect.CRYSTAL, 8).add(Aspect.PROTECT, 8).add(Aspect.MAGIC, 8));
		//event.register.registerObjectTag(new ItemStack(ModObjects.horseshoe), new AspectList().add(Aspect.METAL, 8).add(Aspect.BEAST, 8).add(Aspect.PROTECT, 8));
		//event.register.registerObjectTag(new ItemStack(ModObjects.tallow), new AspectList().add(Aspect.CRAFT, 8).add(Aspect.ALCHEMY, 8).add(SUN, 4));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.taglock), new AspectList().add(Aspect.SOUL, 8).add(Aspect.LIFE, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.cold_iron_ingot), new AspectList().add(Aspect.AVERSION, 15).add(Aspect.COLD, 15).add(Aspect.METAL, 15));
		event.register.registerObjectTag(new ItemStack(ModObjects.amethyst), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.ALCHEMY, 4).add(Aspect.LIFE, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.garnet), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4).add(STAR, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.moonstone), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MAGIC, 4).add(MOON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.spectral_dust), new AspectList().add(Aspect.VOID, 4).add(Aspect.SOUL, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.hoof), new AspectList().add(Aspect.BEAST, 4).add(Aspect.MOTION, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.dimensional_sand), new AspectList().add(Aspect.ELDRITCH, 4).add(Aspect.DARKNESS, 4).add(STAR, 4));
		//event.register.registerObjectTag(new ItemStack(ModObjects.envenomed_fang), new AspectList().add(Aspect.BEAST, 4).add(Aspect.ALCHEMY, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.wool_of_bat), new AspectList().add(Aspect.BEAST, 4).add(Aspect.AIR, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.diabolical_vein), new AspectList().add(Aspect.CRAFT, 4).add(DEMON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.golden_thread), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.DESIRE, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.witches_stitching), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.MAGIC, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.pure_filament), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.AURA, 4));
		//event.register.registerObjectTag(new ItemStack(ModObjects.soul_string), new AspectList().add(Aspect.CRAFT, 4).add(Aspect.SOUL, 4));
		//event.register.registerObjectTag(new ItemStack(ModObjects.chalk_normal), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4));
		//event.register.registerObjectTag(new ItemStack(ModObjects.chalk_golden), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(SUN, 4));
		//event.register.registerObjectTag(new ItemStack(ModObjects.chalk_nether), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(DEMON, 4));
		//event.register.registerObjectTag(new ItemStack(ModObjects.chalk_ender), new AspectList().add(Aspect.MAGIC, 4).add(Aspect.EARTH, 4).add(Aspect.MIND, 4).add(Aspect.ELDRITCH, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.hellebore_seeds), new AspectList().add(Aspect.PLANT, 1).add(DEMON, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.garlic_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SENSES, 1).add(Aspect.AVERSION, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.mandrake_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.MAGIC, 1).add(Aspect.EARTH, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.white_sage_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.AURA, 1).add(Aspect.SOUL, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.aconitum_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.wormwood_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.SOUL, 1));
		event.register.registerObjectTag(new ItemStack(ModObjects.belladonna_seeds), new AspectList().add(Aspect.PLANT, 1).add(Aspect.DEATH, 1).add(Aspect.MAGIC, 1));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.spanish_moss), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AIR, 2).add(Aspect.MAGIC, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.owlets_wing), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2).add(MOON, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.ravens_feather), new AspectList().add(Aspect.BEAST, 3).add(Aspect.AIR, 2).add(Aspect.DARKNESS, 2));
		//event.register.registerObjectTag(new ItemStack(ModObjects.graveyard_dust), new AspectList().add(Aspect.DEATH, 3).add(Aspect.SOUL, 2).add(MOON, 2));
		event.register.registerObjectTag(new ItemStack(ModObjects.adders_fork), new AspectList().add(Aspect.DEATH, 4).add(DEMON, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.snake_venom), new AspectList().add(Aspect.DEATH, 9).add(Aspect.CRYSTAL, 9));
		event.register.registerObjectTag(new ItemStack(ModObjects.eye_of_newt), new AspectList().add(Aspect.WATER, 4).add(Aspect.SENSES, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.toe_of_frog), new AspectList().add(Aspect.WATER, 4).add(Aspect.ALCHEMY, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.lizard_leg), new AspectList().add(Aspect.MOTION, 4).add(Aspect.EARTH, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.yew_aril), new AspectList().add(Aspect.PLANT, 3).add(Aspect.LIFE, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.juniper_berries), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MAGIC, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.elderberries), new AspectList().add(Aspect.PLANT, 3).add(Aspect.MIND, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.hellhound_horn), new AspectList().add(DEMON, 6).add(Aspect.AVERSION, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.demon_heart), new AspectList().add(DEMON, 6).add(Aspect.AVERSION, 6).add(Aspect.FIRE, 6));
		event.register.registerObjectTag(new ItemStack(ModObjects.liquid_wroth), new AspectList().add(DEMON, 6).add(Aspect.ALCHEMY, 6).add(Aspect.CRYSTAL, 9));
		//Todo: Dynamic aspects based on brew contents

		//Todo: Make spells choose their aspects based on NBT Data.
		//Spells
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.spell_page, 1, 0), new AspectList().add(Aspect.MAGIC, 6).add(Aspect.TOOL, 6).add(STAR, 6));

		// Blocks
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.torchwood), new AspectList().add(Aspect.PLANT, 8).add(Aspect.FIRE, 8).add(Aspect.MAGIC, 8));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.ember_grass), new AspectList().add(Aspect.PLANT, 8).add(Aspect.FIRE, 8).add(Aspect.AVERSION, 8));
		event.register.registerObjectTag(new ItemStack(ModObjects.crystal_ball), new AspectList().add(Aspect.CRYSTAL, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		//event.register.registerObjectTag(new ItemStack(ModObjects.tarot_table), new AspectList().add(Aspect.EARTH, 25).add(Aspect.PLANT, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		//event.register.registerObjectTag(new ItemStack(ModObjects.scorned_bricks), new AspectList().add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10).add(DEMON, 10));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.embittered_bricks), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10));
		//event.register.registerObjectTag(new ItemStack(ModObjects.perpetual_ice), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10));
		//event.register.registerObjectTag(new ItemStack(ModObjects.goblet), new AspectList().add(Aspect.METAL, 15).add(Aspect.MAGIC, 15).add(Aspect.VOID, 15));
		event.register.registerObjectTag(new ItemStack(ModObjects.spinning_wheel), new AspectList().add(Aspect.PLANT, 30).add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 25));
		event.register.registerObjectTag(new ItemStack(ModObjects.oven), new AspectList().add(Aspect.FIRE, 25).add(Aspect.METAL, 30).add(Aspect.CRAFT, 30));
		//event.register.registerObjectTag(new ItemStack(ModObjects.nethersteel), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.METAL, 5).add(DEMON, 10));
		event.register.registerObjectTag(new ItemStack(ModObjects.salt_ore), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.coquina), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		//for (Block block : ModObjects.block_of_cold_iron_chiseled)
		//	event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.AVERSION, 101).add(Aspect.COLD, 101).add(Aspect.METAL, 101));
		//for (Block block : ModObjects.block_of_silver_chiseled)
		//	event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.METAL, 67).add(Aspect.DESIRE, 33).add(MOON, 20));
		//for (Block block : ModObjects.coquina_chiseled)
		//	event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		//for (Block block : ModObjects.nethersteel_chiseled)
		//	event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.FIRE, 5).add(Aspect.MAGIC, 5).add(Aspect.METAL, 5).add(DEMON, 10));
		//for (Block block : ModObjects.scorned_bricks_chiseled)
		//	event.register.registerObjectTag(new ItemStack(block), new AspectList().add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10).add(DEMON, 10));
		//event.register.registerObjectTag(new ItemStack(ModObjects.graveyard_dirt), new AspectList().add(Aspect.EARTH, 5).add(Aspect.UNDEAD, 5));
		//event.register.registerObjectTag(new ItemStack(ModObjects.purifying_earth), new AspectList().add(Aspect.EARTH, 5).add(SUN, 5));


		//Gem ores
		event.register.registerObjectTag(new ItemStack(ModObjects.amethyst_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.EARTH, 4).add(Aspect.ALCHEMY, 4).add(Aspect.LIFE, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.garnet_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.EARTH, 4).add(Aspect.PROTECT, 4).add(STAR, 4));
		event.register.registerObjectTag(new ItemStack(ModObjects.moonstone_ore), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.EARTH, 4).add(Aspect.MAGIC, 4).add(MOON, 4));

		//Baubles

		//Silver
		event.register.registerObjectTag(new ItemStack(ModObjects.silver_ingot), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5).add(MOON, 3));
		event.register.registerObjectTag(new ItemStack(ModObjects.silver_ore), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 5).add(Aspect.EARTH, 5).add(MOON, 3));

		//Saplings
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.sapling, 1, 0), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MIND, 3));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.sapling, 1, 1), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.MAGIC, 3));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.sapling, 1, 2), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 3));
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.sapling, 1, 3), new AspectList().add(Aspect.PLANT, 15).add(Aspect.LIFE, 5).add(Aspect.DEATH, 3));


		//Brooms
		//for (int i = 0; i < 5; i++) {
		//	event.register.registerObjectTag(new ItemStack(ModObjects.broom, 1, i), new AspectList().add(Aspect.PLANT, 35).add(Aspect.MAGIC, 35).add(Aspect.FLIGHT, 30).add(MOON, 25).add(STAR, 25));
		//}

		//Technical
		event.register.registerObjectTag(new ItemStack(ModObjects.salt_barrier), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.EARTH, 5));

		//Vampire Gear
		//		evt.register.registerObjectTag(new ItemStack(ModObjects.sanguine_fabric), new AspectList().add(Aspect.CRAFT, 10).add(Aspect.UNDEAD, 10).add(MOON, 10).add(DEMON, 10));

		//Add some of our aspects to existing items in vanilla
		//Use this sparingly. Please run over any future additions to this part of the file with Sunconure11.
		//If new aspects must be added to an item from vanilla, try and preserve as much of the original aspects as possible.
		//The same applies if you try and add aspects to items from other mods.
		event.register.registerObjectTag(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new AspectList().add(Aspect.PLANT, 5).add(Aspect.SENSES, 5).add(SUN, 3).add(Aspect.AIR, 1).add(Aspect.LIFE, 1));
		event.register.registerObjectTag(new ItemStack(Items.GOLD_INGOT), new AspectList().add(Aspect.METAL, 10).add(Aspect.DESIRE, 10).add(SUN, 5));

		//Entities
		ThaumcraftApi.registerEntityTag("owl", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10).add(MOON, 8));
		ThaumcraftApi.registerEntityTag("snake", new AspectList().add(Aspect.BEAST, 10).add(Aspect.AVERSION, 10).add(DEMON, 8));
		ThaumcraftApi.registerEntityTag("raven", new AspectList().add(Aspect.BEAST, 10).add(Aspect.FLIGHT, 10).add(Aspect.DARKNESS, 8));
		ThaumcraftApi.registerEntityTag("toad", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10).add(Aspect.ALCHEMY, 8));

		ThaumcraftApi.registerEntityTag("lizard", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(SUN, 8));
		ThaumcraftApi.registerEntityTag("newt", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10).add(Aspect.MOTION, 8));
		ThaumcraftApi.registerEntityTag("blindworm", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.SENSES, 8));

		ThaumcraftApi.registerEntityTag("uran", new AspectList().add(Aspect.BEAST, 25).add(DEMON, 25).add(Aspect.DARKNESS, 16));
		ThaumcraftApi.registerEntityTag("hellhound", new AspectList().add(Aspect.BEAST, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("demon", new AspectList().add(Aspect.SOUL, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("demoness", new AspectList().add(Aspect.SOUL, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("imp", new AspectList().add(Aspect.SOUL, 25).add(DEMON, 25).add(Aspect.FIRE, 16));
		ThaumcraftApi.registerEntityTag("black_dog", new AspectList().add(Aspect.SOUL, 25).add(Aspect.BEAST, 25).add(Aspect.AVERSION, 16));
	}
}