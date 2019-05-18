package com.bewitchment.api;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.OvenRecipe;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BewitchmentAPI {
	private static final IForgeRegistry<OvenRecipe> REGISTRY_OVEN = new RegistryBuilder<OvenRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "oven_recipe")).setType(OvenRecipe.class).create();

	private static final Map<EntityEntry, Collection<ItemStack>> ATHAME_LOOT = new HashMap<>();

	/**
	 * The Demon creature attribute.
	 */
	public static EnumCreatureAttribute DEMON = EnumHelper.addCreatureAttribute("DEMON");

	/**
	 * The Spirit creature attribute.
	 */
	public static EnumCreatureAttribute SPIRIT = EnumHelper.addCreatureAttribute("SPIRIT");

	public static OvenRecipe registerOvenRecipe(OvenRecipe recipe) {
		REGISTRY_OVEN.register(recipe);
		return recipe;
	}

	public static void registerAthameLoot(Class<? extends EntityLivingBase> clazz, Collection<ItemStack> list) {
		ATHAME_LOOT.put(EntityRegistry.getEntry(clazz), list);
	}

	public static Collection<ItemStack> getAthameLoot(EntityLivingBase entity) {
		Collection<ItemStack> fin = new HashSet<>();
		EntityEntry entry = EntityRegistry.getEntry(entity.getClass());
		if (ATHAME_LOOT.containsKey(entry)) fin.addAll(ATHAME_LOOT.get(entry));
		return fin;
	}
}