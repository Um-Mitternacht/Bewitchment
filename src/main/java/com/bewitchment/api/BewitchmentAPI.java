package com.bewitchment.api;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.AltarUpgrade;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "SameReturnValue"})
public class BewitchmentAPI {
	/**
	 * a Map of loot for mobs to drop when killed with the Athame
	 */
	public static final Map<Predicate<EntityLivingBase>, Collection<ItemStack>> ATHAME_LOOT = new HashMap<>();
	
	/**
	 * a Map of AltarUpgrades for the Witches' Atlar to search for
	 */
	public static final Map<Predicate<BlockWorldState>, AltarUpgrade> ALTAR_UPGRADES = new HashMap<>();
	
	/**
	 * A list of pets to be chosen for the meet pet fortune
	 */
	public static final List<EntityEntry> VALID_PETS = new ArrayList<>();
	
	/**
	 * The Demon creature attribute.
	 */
	public static EnumCreatureAttribute DEMON = EnumHelper.addCreatureAttribute("DEMON");
	
	/**
	 * The Spirit creature attribute.
	 */
	public static EnumCreatureAttribute SPIRIT = EnumHelper.addCreatureAttribute("SPIRIT");
	
	/**
	 * @param entity the entity to check
	 * @return true if the entity is a witch, false otherwise
	 */
	public static boolean isWitch(EntityLivingBase entity) {
		return !isWitchHunter(entity) && (entity instanceof EntityWitch || (entity instanceof EntityPlayer && Bewitchment.proxy.doesPlayerHaveAdvancement((EntityPlayer) entity, new ResourceLocation(Bewitchment.MODID, "crafted_altar"))));
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
	 * @return false always, spectres are not currently in the mod
	 */
	public static boolean isSpectre(EntityLivingBase entity) {
		return false;
	}
	
	/**
	 * @param entity the entity to check
	 * @return false always, witch hunters are not currently in the mod
	 */
	public static boolean isWitchHunter(EntityLivingBase entity) {
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
		return isWerewolf(entity) || entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD;
	}
}