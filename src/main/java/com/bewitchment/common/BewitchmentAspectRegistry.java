package com.bewitchment.common;

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
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.ginger), new AspectList().add(Aspect.PLANT, 2).add(Aspect.FIRE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.hellebore), new AspectList().add(Aspect.PLANT, 2).add(Aspect.MAGIC, 2).add(Aspect.FIRE, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.kenaf), new AspectList().add(Aspect.PLANT, 2).add(Aspect.CRAFT, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.kelp), new AspectList().add(Aspect.PLANT, 2).add(Aspect.WATER, 2));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.lavender), new AspectList().add(Aspect.PLANT, 2).add(Aspect.SENSES, 2));
	}

}
