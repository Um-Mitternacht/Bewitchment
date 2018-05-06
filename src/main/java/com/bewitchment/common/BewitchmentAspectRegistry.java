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
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.tarots), new AspectList().add(Aspect.MAGIC, 7).add(Aspect.CRAFT, 7));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.heart), new AspectList().add(Aspect.DEATH, 7).add(Aspect.MAN, 7));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.wood_ash), new AspectList().add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 3));
		ThaumcraftApi.registerObjectTag(new ItemStack(ModItems.moonbell), new AspectList().add(Aspect.PLANT, 2).add(Aspect.DARKNESS, 2));
	}

}
