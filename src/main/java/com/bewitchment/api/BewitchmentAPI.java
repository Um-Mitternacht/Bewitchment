package com.bewitchment.api;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.DistilleryRecipe;
import com.bewitchment.api.registry.Fortune;
import com.bewitchment.api.registry.OvenRecipe;
import com.bewitchment.api.registry.SpinningWheelRecipe;
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
	private static final IForgeRegistry<DistilleryRecipe> REGISTRY_DISTILLERY = new RegistryBuilder<DistilleryRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "distillery_recipe")).setType(DistilleryRecipe.class).create();
	private static final IForgeRegistry<SpinningWheelRecipe> REGISTRY_SPINNING_WHEEL = new RegistryBuilder<SpinningWheelRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "spinning_wheel_recipe")).setType(SpinningWheelRecipe.class).create();
	
	private static final IForgeRegistry<Fortune> REGISTRY_FORTUNE = new RegistryBuilder<Fortune>().setName(new ResourceLocation(Bewitchment.MODID, "fortune")).setType(Fortune.class).create();
	
	private static final Map<EntityEntry, Collection<ItemStack>> ATHAME_LOOT = new HashMap<>();
	
	/**
	 * The Demon creature attribute.
	 */
	public static EnumCreatureAttribute DEMON = EnumHelper.addCreatureAttribute("DEMON");
	
	/**
	 * The Spirit creature attribute.
	 */
	public static EnumCreatureAttribute SPIRIT = EnumHelper.addCreatureAttribute("SPIRIT");
	
	/**
	 * registers a new OvenRecipe
	 *
	 * @param recipe the recipe to register
	 */
	public static void registerOvenRecipe(OvenRecipe recipe) {
		REGISTRY_OVEN.register(recipe);
	}
	
	/**
	 * registers a new DistilleryRecipe
	 *
	 * @param recipe the recipe to register
	 */
	public static void registerDistilleryRecipe(DistilleryRecipe recipe) {
		REGISTRY_DISTILLERY.register(recipe);
	}
	
	/**
	 * registers a new SpinningWheelRecipe
	 *
	 * @param recipe the recipe to register
	 */
	public static void registerSpinningWheelRecipe(SpinningWheelRecipe recipe) {
		REGISTRY_SPINNING_WHEEL.register(recipe);
	}
	
	/**
	 * registers a new fortune
	 *
	 * @param fortune the fortune to register
	 */
	public static void registerFortune(Fortune fortune) {
		REGISTRY_FORTUNE.register(fortune);
	}
	
	/**
	 * registers new Athame loot
	 *
	 * @param clazz the entity class to be associated with the list
	 * @param list  the list of ItemStacks to be dropped as loot
	 */
	public static void registerAthameLoot(Class<? extends EntityLivingBase> clazz, Collection<ItemStack> list) {
		ATHAME_LOOT.put(EntityRegistry.getEntry(clazz), list);
	}
	
	/**
	 * @param entity the entity to check
	 * @return the loot associated with the entity, if any
	 */
	public static Collection<ItemStack> getAthameLoot(EntityLivingBase entity) {
		Collection<ItemStack> fin = new HashSet<>();
		EntityEntry entry = EntityRegistry.getEntry(entity.getClass());
		if (ATHAME_LOOT.containsKey(entry)) fin.addAll(ATHAME_LOOT.get(entry));
		return fin;
	}
}