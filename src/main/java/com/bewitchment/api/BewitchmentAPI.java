package com.bewitchment.api;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class BewitchmentAPI {
	private static final Map<EntityEntry, Collection<ItemStack>> ATHAME_LOOT = new HashMap<>();

	/**
	 * The Demon creature attribute.
	 */
	public static EnumCreatureAttribute DEMON = EnumHelper.addCreatureAttribute("DEMON");

	/**
	 * The Spirit creature attribute.
	 */
	public static EnumCreatureAttribute SPIRIT = EnumHelper.addCreatureAttribute("SPIRIT");

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