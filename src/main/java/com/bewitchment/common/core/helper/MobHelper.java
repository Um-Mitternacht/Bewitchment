package com.bewitchment.common.core.helper;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.bewitchment.common.entity.living.familiar.EntitySnake;
import com.google.common.collect.Sets;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.INpc;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
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
	public static final Set<String> OWL_FOOD = Sets.newHashSet(); //Food for owls
	public static final Set<String> SNAKE_FOOD = Sets.newHashSet(); //Food for sneks
	public static final Set<String> UNDEAD_BODY = Sets.newHashSet(); //Corporeal undead, such as zombies and mummies
	public static final Set<String> HUMANS = Sets.newHashSet(); //People
	public static final Set<String> WEREWOLF_FOOD = Sets.newHashSet(); //Livestock targeted by werewolves

	public static void init() {

		/*
		* Don't overload them for the sake of adding stuff
		* Eg: endermen are not in any of these lists
		* Less is more in this case
		*/

		SPIRITS.add(EntityGhast.class.getName());
		SPIRITS.add(EntityVex.class.getName());
		SPIRITS.add(EntityBlaze.class.getName()); //Zabi, we discussed this in a past VC, and agreed these are spirits of sorts. As a result, they will stay.
		SPIRITS.add(EntityGuardian.class.getName());
		SPIRITS.add("thaumcraft.common.entities.monster.EntityWisp"); // <-- Example of fully qualified name
		SPIRITS.add("astralsorcery.common.entities.EntityFlare");
		SPIRITS.add("its_meow.betteranimalsplus.common.entity.miniboss.hirschgeist.EntityHirschgeist");
		SPIRITS.add("com.jarhax.eerieentities.entities.EntityNetherKnight");
		SPIRITS.add("com.jarhax.eerieentities.entities.EntityCursedArmor");

		//Will be used for actual demons later on. Blazes are just fire elementals
		//DEMONS.add(EntityBlaze.class.getName());

		CANIDS.add(EntityWolf.class.getName());
		CANIDS.add("evilcraft.common.entity.monster.Werewolf");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityFeralWolf");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityFox");
		CANIDS.add("its_meow.betteranimalsplus.common.entity.EntityCoyote");

		//Villagers are already included in the list
		HUMANS.add(EntityWitch.class.getName());
		HUMANS.add(EntityIllusionIllager.class.getName());
		HUMANS.add(EntityEvoker.class.getName());
		HUMANS.add(EntityVindicator.class.getName());
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistCleric");
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistKnight");

		VILLAGERS.add("mca.entity.EntityVillagerMCA");

		OWL_FOOD.add(EntityRabbit.class.getName());
		OWL_FOOD.add(EntityChicken.class.getName());
		OWL_FOOD.add(EntityParrot.class.getName());
		OWL_FOOD.add(EntityBat.class.getName());
		OWL_FOOD.add(EntitySnake.class.getName());
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityHedgehog"); // This is a stupid check, but the codebase of Animania is weird. Really, really weird.
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityHedgehogBase");
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityHedgehogAlbino");
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityFerretGrey");
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityFerretWhite");
		OWL_FOOD.add("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat");

		SNAKE_FOOD.add(EntityRabbit.class.getName());
		SNAKE_FOOD.add(EntityChicken.class.getName());
		SNAKE_FOOD.add(EntityParrot.class.getName());
		SNAKE_FOOD.add(EntityBat.class.getName());
		SNAKE_FOOD.add("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat");

		WEREWOLF_FOOD.add(EntitySheep.class.getName());
		WEREWOLF_FOOD.add(EntityCow.class.getName());
		WEREWOLF_FOOD.add(EntityPig.class.getName());
		WEREWOLF_FOOD.add(EntityHorse.class.getName());
		WEREWOLF_FOOD.add(EntityDonkey.class.getName());
		WEREWOLF_FOOD.add(EntityMule.class.getName());
		WEREWOLF_FOOD.add(EntityLlama.class.getName());
		WEREWOLF_FOOD.add(EntityMooshroom.class.getName());
		WEREWOLF_FOOD.add("com.animania.common.entities.pigs.EntitySowYorkshire"); //Todo: Get every single breed animania adds and support it ಠ_ಠ

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

	public static boolean isOwlFodder(EntityLivingBase entity) {
		return OWL_FOOD.contains(entity.getClass().getName());
	}

	public static boolean isSnakeFodder(EntityLivingBase entity) {
		return SNAKE_FOOD.contains(entity.getClass().getName());
	}

	public static boolean isWerewolfFood(EntityLivingBase entity) {
		return WEREWOLF_FOOD.contains(entity.getClass().getName());
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
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.SPECTRE) {
			return true;
		}
		return SPIRITS.contains(entity.getClass().getName());
	}

	//For usage in silver
	public static boolean isCorporealUndead(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			return true;
		}
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.VAMPIRE) {
			return true;
		}
		return UNDEAD_BODY.contains(entity.getClass().getName());
	}

	//For usage in aconite
	public static boolean isCanid(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformationData.CAPABILITY, null).getType() == DefaultTransformations.WEREWOLF) {
			return true;
		}
		return CANIDS.contains(entity.getClass().getName());
	}
}

