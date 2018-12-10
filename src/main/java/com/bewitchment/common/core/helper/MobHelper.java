package com.bewitchment.common.core.helper;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.transformation.CapabilityTransformation;
import com.bewitchment.common.entity.spirits.demons.EntityUran;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.INpc;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;

/**
 * Created by Joseph on 8/25/2018. Credit to Alexthe666 for some of the code.
 */

public class MobHelper {

	public static final Set<String> VILLAGERS = Sets.newHashSet(); //Hmm hmmm mmph
	public static final Set<String> SPIRITS = Sets.newHashSet(); //Spirits and discarnate undead
	public static final Set<String> CANIDS = Sets.newHashSet(); //The entire canid family of carnivorans
	public static final Set<String> DEMONS = Sets.newHashSet(); //Infernal beings
	public static final Set<String> UNDEAD_BODY = Sets.newHashSet(); //Corporeal undead, such as zombies and mummies
	public static final Set<String> HUMANS = Sets.newHashSet(); //People

	public static void init() {

		/*
		* Don't overload them for the sake of adding stuff
		* Eg: endermen are not in any of these lists
		* Less is more in this case
		*/

		//This class is to be used in extending certain brews/weapons/mechanics only. Things like mobs eating each other go in their own class.

		SPIRITS.add(EntityGhast.class.getName());
		SPIRITS.add(EntityVex.class.getName());
		SPIRITS.add("thaumcraft.common.entities.monster.EntityWisp"); // <-- Example of fully qualified name
		SPIRITS.add("astralsorcery.common.entities.EntityFlare");
		SPIRITS.add("its_meow.betteranimalsplus.common.entity.miniboss.hirschgeist.EntityHirschgeist");
		SPIRITS.add("com.jarhax.eerieentities.entities.EntityCursedArmor");
		SPIRITS.add("familiarfauna.entities.EntityPixie");
		SPIRITS.add("com.github.alexthe666.iceandfire.entity.EntityPixie");
		SPIRITS.add("teamroots.roots.entity.EntityFairy");

		//Will be used for actual demons later on.
		DEMONS.add(EntityUran.class.getName());

		CANIDS.add(EntityWolf.class.getName());
		CANIDS.add("evilcraft.common.entity.monster.Werewolf");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityFeralWolf");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityFox");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityCoyote");
		CANIDS.add("elucent.mysticalworld.entity.EntityFox");

		//Villagers are already included in the list
		HUMANS.add(EntityWitch.class.getName());
		HUMANS.add(EntityIllusionIllager.class.getName());
		HUMANS.add(EntityEvoker.class.getName());
		HUMANS.add(EntityVindicator.class.getName());
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistCleric");
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistKnight");

		VILLAGERS.add("mca.entity.EntityVillagerMCA");

		if (System.getProperty("stickdebug", "").equals("true")) {
			MinecraftForge.EVENT_BUS.register(new Object() {
				@SubscribeEvent
				public void onStickInteraction(EntityInteractSpecific evt) {
					if (evt.getItemStack().getItem() == Items.STICK) {
						Log.i(evt.getTarget().getClass().getName());
					}
				}
			});
		}

	}

	public static boolean isVillager(EntityLivingBase entity) {
		if (entity instanceof INpc) {
			return true;
		}
		return VILLAGERS.contains(entity.getClass().getName());
	}

	public static boolean isHumanoid(EntityLivingBase entity) {
		if (isVillager(entity) || entity instanceof EntityPlayer) {
			return true;
		}
		return HUMANS.contains(entity.getClass().getName());
	}

	public static boolean isDemon(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == BewitchmentAPI.getAPI().DEMON) {
			return true;
		}
		return DEMONS.contains(entity.getClass().getName());
	}

	//For usage in cold iron
	public static boolean isSpirit(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == BewitchmentAPI.getAPI().SPIRIT) {
			return true;
		}
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.SPECTRE) {
			return true;
		}
		return SPIRITS.contains(entity.getClass().getName());
	}

	//For usage in silver
	public static boolean isCorporealUndead(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			return true;
		}
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE) {
			return true;
		}
		return UNDEAD_BODY.contains(entity.getClass().getName());
	}

	//For usage in aconite
	public static boolean isCanid(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformation.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF) {
			return true;
		}
		return CANIDS.contains(entity.getClass().getName());
	}
}

