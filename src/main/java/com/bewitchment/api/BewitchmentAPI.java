package com.bewitchment.api;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.AltarUpgrade;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.EntityEntry;

import java.util.*;
import java.util.function.Predicate;

@SuppressWarnings({"unused", "SameReturnValue", "ConstantConditions"})
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
	 * @param entity the EntityPlayer to check
	 * @return false always, has pet check WIP
	 */
	public static boolean hasPets(EntityPlayer entity) {
		return (entity.getCapability(ExtendedPlayer.CAPABILITY, null)).pets > 0;
	}

	/**
	 * @param entity the entity to check
	 * @return if player defeated at least one boss
	 */
	public static boolean defeatedBoss(EntityPlayer entity) {
		return !(entity.getCapability(ExtendedPlayer.CAPABILITY, null)).uniqueDefeatedBosses.isEmpty();
	}


	/**
	 * @param entity the entity to check
	 * @return false always, infusion is not currently in the mod
	 */
	public static boolean isInfused(EntityPlayer entity) {
		return false;
	}

	/**
	 * @param entity the entity to check
	 * @return true when there's active effect not provided by beacon, vice versa
	 */
	public static boolean hasEffects(EntityPlayer entity) {
		for(PotionEffect potion: entity.getActivePotionEffects()) {
			if(!potion.getIsAmbient()) return true;
		}
		return false;
	}

	/**
	 * @param entity the entity to check
	 * @return false always, poppets are not currently in the mod
	 */
	public static boolean hasPoppets(EntityPlayer entity) {
		return false;
	}
	
	/**
	 * @param entity the entity to check
	 * @return false always, witch hunters are not currently in the mod
	 */
	public static boolean isWitchHunter(EntityLivingBase entity) {
		return false;
	}
	
	public static float getSilverWeakness(EntityLivingBase entity) {
		float fin = 1;
		if (isVampire(entity) || isWerewolf(entity) || entity.isEntityUndead()) {
			fin = 1.5f;
			if (entity instanceof EntityPlayer) fin *= 1.5f;
		}
		return fin;
	}
	
	public static float getColdIronWeakness(EntityLivingBase entity) {
		float fin = 1;
		if (entity.getCreatureAttribute() == DEMON || entity.getCreatureAttribute() == SPIRIT || entity instanceof EntityBlaze || entity instanceof EntityVex) {
			fin = 1.5f;
			if (entity instanceof EntityPlayer) fin *= 1.5f;
		}
		return fin;
	}
}