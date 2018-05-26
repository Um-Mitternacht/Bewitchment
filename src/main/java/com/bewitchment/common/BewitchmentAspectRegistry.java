package com.bewitchment.common;

import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.item.ModItems;
import net.minecraft.item.ItemStack;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * Created by Joseph on 5/6/2018.
 */
public class BewitchmentAspectRegistry {

	public static void register() {
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.ectoplasm), new AspectList().add(Aspect.SOUL, 5).add(Aspect.DEATH, 5));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.tarots), new AspectList().add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.heart), new AspectList().add(Aspect.DEATH, 7).add(Aspect.MAN, 7));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.wood_ash), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.moonbell), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.aconitum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.asphodel), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.belladonna), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2).add(Aspect.MAGIC, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.chrysanthemum), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.garlic), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.AVERSION, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.ginger), new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 2).add(Aspect.SENSES, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.hellebore), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.FIRE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.kenaf), new AspectList().add(Aspect.PLANT, 2).add(Aspect.CRAFT, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.kelp), new AspectList().add(Aspect.PLANT, 2).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.lavender), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.mint), new AspectList().add(Aspect.PLANT, 2).add(Aspect.COLD, 2).add(Aspect.SENSES, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.infested_wheat), new AspectList().add(Aspect.PLANT, 2).add(Aspect.UNDEAD, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.oak_apple_gall), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2).add(Aspect.ENTROPY, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.mandrake_root), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.EARTH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.sagebrush), new AspectList().add(Aspect.PLANT, 2).add(Aspect.VOID, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.thistle), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AVERSION, 2).add(Aspect.PROTECT, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.silphium), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DESIRE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.tulsi), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.white_sage), new AspectList().add(Aspect.PLANT, 2).add(Aspect.AURA, 2).add(Aspect.SOUL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.witchweed), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DEATH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.wormwood), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SOUL, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.eye_of_old), new AspectList().add(Aspect.SENSES, 4).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.eye_of_ancient), new AspectList().add(Aspect.SENSES, 4).add(Aspect.WATER, 2).add(Aspect.ELDRITCH, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.chromatic_quill), new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 2).add(Aspect.AIR, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.tongue_of_dog), new AspectList().add(Aspect.SENSES, 4).add(Aspect.BEAST, 4).add(Aspect.DEATH, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.needle_bone), new AspectList().add(Aspect.DEATH, 2).add(Aspect.CRAFT, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.mortar_and_pestle), new AspectList().add(Aspect.MOTION, 8).add(Aspect.CRAFT, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.bee), new AspectList().add(Aspect.AVERSION, 2).add(Aspect.BEAST, 2).add(Aspect.ALCHEMY, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.athame), new AspectList().add(Aspect.AVERSION, 10).add(Aspect.MAGIC, 10).add(Aspect.METAL, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.boline), new AspectList().add(Aspect.PLANT, 10).add(Aspect.MAGIC, 10).add(Aspect.METAL, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.mortar_and_pestle_stone), new AspectList().add(Aspect.MOTION, 8).add(Aspect.EARTH, 8).add(Aspect.CRAFT, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.salt), new AspectList().add(Aspect.EARTH, 4).add(Aspect.WATER, 4).add(Aspect.PROTECT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.honey), new AspectList().add(Aspect.DESIRE, 6).add(Aspect.LIFE, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.girdle_of_the_wooded), new AspectList().add(Aspect.PLANT, 20).add(Aspect.LIFE, 20).add(Aspect.PROTECT, 20).add(Aspect.MAGIC, 20));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.nazar), new AspectList().add(Aspect.DESIRE, 8).add(Aspect.METAL, 8).add(Aspect.CRYSTAL, 8).add(Aspect.PROTECT, 8).add(Aspect.MAGIC, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.horseshoe), new AspectList().add(Aspect.METAL, 8).add(Aspect.BEAST, 8).add(Aspect.PROTECT, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.wax), new AspectList().add(Aspect.CRAFT, 8).add(Aspect.ALCHEMY, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.taglock), new AspectList().add(Aspect.SOUL, 8).add(Aspect.LIFE, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.cold_iron_ingot), new AspectList().add(Aspect.AVERSION, 4).add(Aspect.COLD, 4).add(Aspect.METAL, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.empty_honeycomb), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.VOID, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.honeycomb), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.LIFE, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 0), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.PROTECT, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 1), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.ELDRITCH, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 2), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MAGIC, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 3), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.SENSES, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 4), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AURA, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 5), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.LIFE, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 6), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.AVERSION, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 7), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.EARTH, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 8), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.ALCHEMY, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.gem, 1, 9), new AspectList().add(Aspect.DESIRE, 4).add(Aspect.CRYSTAL, 4).add(Aspect.MIND, 4));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.torchwood), new AspectList().add(Aspect.PLANT, 8).add(Aspect.FIRE, 8).add(Aspect.MAGIC, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.moonbell), new AspectList().add(Aspect.PLANT, 6).add(Aspect.DARKNESS, 6).add(Aspect.MAGIC, 6));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.raging_grass), new AspectList().add(Aspect.PLANT, 8).add(Aspect.AVERSION, 8).add(Aspect.MAGIC, 8));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.crystal_ball), new AspectList().add(Aspect.CRYSTAL, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.tarot_table), new AspectList().add(Aspect.EARTH, 25).add(Aspect.PLANT, 25).add(Aspect.MAGIC, 25).add(Aspect.DESIRE, 25));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.scorned_bricks), new AspectList().add(Aspect.FIRE, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.embittered_bricks), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10).add(Aspect.DARKNESS, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.fake_ice), new AspectList().add(Aspect.COLD, 10).add(Aspect.MAGIC, 10));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.goblet), new AspectList().add(Aspect.METAL, 15).add(Aspect.MAGIC, 15).add(Aspect.VOID, 15));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModBlocks.thread_spinner), new AspectList().add(Aspect.PLANT, 30).add(Aspect.MAGIC, 15).add(Aspect.CRAFT, 25));
	}

}
