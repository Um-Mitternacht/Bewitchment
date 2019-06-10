package com.bewitchment.api;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.*;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;

@SuppressWarnings("WeakerAccess")
public class BewitchmentAPI {
	private static final IForgeRegistry<OvenRecipe> REGISTRY_OVEN = new RegistryBuilder<OvenRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "oven_recipe")).setType(OvenRecipe.class).create();
	private static final IForgeRegistry<DistilleryRecipe> REGISTRY_DISTILLERY = new RegistryBuilder<DistilleryRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "distillery_recipe")).setType(DistilleryRecipe.class).create();
	private static final IForgeRegistry<SpinningWheelRecipe> REGISTRY_SPINNING_WHEEL = new RegistryBuilder<SpinningWheelRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "spinning_wheel_recipe")).setType(SpinningWheelRecipe.class).create();
	
	private static final IForgeRegistry<FrostfireRecipe> REGISTRY_FROSTFIRE = new RegistryBuilder<FrostfireRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "frostfire_recipe")).setType(FrostfireRecipe.class).create();
	private static final IForgeRegistry<Ritual> REGISTRY_RITUAL = new RegistryBuilder<Ritual>().setName(new ResourceLocation(Bewitchment.MODID, "ritual")).setType(Ritual.class).create();
	
	private static final IForgeRegistry<Fortune> REGISTRY_FORTUNE = new RegistryBuilder<Fortune>().setName(new ResourceLocation(Bewitchment.MODID, "fortune")).setType(Fortune.class).create();
	
	private static final Map<EntityEntry, Collection<ItemStack>> ATHAME_LOOT = new HashMap<>();
	
	private static final Map<Predicate<BlockWorldState>, AltarUpgrade> ALTAR_UPGRADES = new HashMap<>();
	
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
	 * @param recipe the recipe to register
	 */
	public static void registerOvenRecipe(OvenRecipe recipe) {
		REGISTRY_OVEN.register(recipe);
	}
	
	/**
	 * registers a new DistilleryRecipe
	 * @param recipe the recipe to register
	 */
	public static void registerDistilleryRecipe(DistilleryRecipe recipe) {
		REGISTRY_DISTILLERY.register(recipe);
	}
	
	/**
	 * registers a new SpinningWheelRecipe
	 * @param recipe the recipe to register
	 */
	public static void registerSpinningWheelRecipe(SpinningWheelRecipe recipe) {
		REGISTRY_SPINNING_WHEEL.register(recipe);
	}
	
	/**
	 * registers a new Ritual
	 * @param ritual the ritual to register
	 */
	public static void registerRitual(Ritual ritual) {
		REGISTRY_RITUAL.register(ritual);
	}
	
	/**
	 * registers a new FrostFireRecipe
	 * @param recipe the recipe to register
	 */
	public static void registerFrostfireRecipe(FrostfireRecipe recipe) {
		REGISTRY_FROSTFIRE.register(recipe);
	}
	
	/**
	 * registers a new Fortune
	 * @param fortune the fortune to register
	 */
	public static void registerFortune(Fortune fortune) {
		REGISTRY_FORTUNE.register(fortune);
	}
	
	/**
	 * registers new Athame loot
	 * @param clazz the entity class to be associated with the list
	 * @param list the list of ItemStacks to be dropped as loot
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
	
	/**
	 * registers a new altar upgrade
	 * @param predicate the predicate to check
	 * @param upgrade the upgrade to register
	 */
	public static void registerAltarUpgrade(Predicate<BlockWorldState> predicate, AltarUpgrade upgrade) {
		ALTAR_UPGRADES.put(predicate, upgrade);
	}
	
	/**
	 * @param world the world
	 * @param pos the block position to check
	 * @return the upgrade associated with the state, or null
	 */
	public static AltarUpgrade getAltarUpgrade(World world, BlockPos pos) {
		for (Predicate<BlockWorldState> predicate : ALTAR_UPGRADES.keySet()) if (predicate.test(new BlockWorldState(world, pos, true))) return ALTAR_UPGRADES.get(predicate);
		return null;
	}
	
	/**
	 * @param entity the entity to check
	 * @return false always, vampires are not currently in the mod
	 */
	public static boolean isVampire(EntityLivingBase entity) {
		return false;
	}
	
	/**
	 * @param entity the entity to check
	 * @return false always, werewolves are not currently in the mod
	 */
	public static boolean isWerewolf(EntityLivingBase entity) {
		return false;
	}
	
	/**
	 * @param entity the entity to check
	 * @return true if the entity is weak to cold iron, false otherwise
	 */
	public static boolean isWeakToColdIron(EntityLivingBase entity) {
		return entity.getCreatureAttribute() == DEMON || entity.getCreatureAttribute() == SPIRIT || entity instanceof EntityBlaze || entity instanceof EntityEnderman || entity instanceof EntityVex;
	}
	
	/**
	 * @param entity the entity to check
	 * @return true if the entity is weak to silver, false otherwise
	 */
	public static boolean isWeakToSilver(EntityLivingBase entity) {
		return isWerewolf(entity) || isVampire(entity) || entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
	}
}