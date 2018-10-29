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
		SPIRITS.add("thaumcraft.common.entities.monster.EntityWisp"); // <-- Example of fully qualified name
		SPIRITS.add("astralsorcery.common.entities.EntityFlare");
		SPIRITS.add("its_meow.betteranimalsplus.common.entity.miniboss.hirschgeist.EntityHirschgeist");
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
		OWL_FOOD.add("familiarfauna.entities.EntityTurkey");
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityHedgehog"); // This is a stupid check, but the codebase of Animania is weird. Really, really weird.
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityHedgehogBase");
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityHedgehogAlbino");
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityFerretGrey");
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityFerretWhite");
		OWL_FOOD.add("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat"); //Todo: Get all peacock, rabbit, and other small animal breeds from Animania
		OWL_FOOD.add("com.animania.common.entities.rodents.EntityHamster");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityChickPlymouthRock");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityChickWyandotte");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityChickOrpington");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityChickLeghorn");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityChickRhodeIslandRed");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityRoosterPlymouthRock");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityRoosterWyandotte");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityRoosterOrpington");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityRoosterLeghorn");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityRoosterRhodeIslandRed");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityHenPlymouthRock");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityHenWyandotte");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityHenOrpington");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityHenLeghorn");
		OWL_FOOD.add("com.animania.common.entities.chickens.EntityHenRhodeIslandRed");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckChinchilla");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeChinchilla");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitChinchilla");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckRex");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeRex");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitRex");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckNewZealand");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeNewZealand");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitNewZealand");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckJack");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeJack");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitJack");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckLop");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeLop");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitLop");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckHavana");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeHavana");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitHavana");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckDutch");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeDutch");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitDutch");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckCottontail");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeCottontail");
		OWL_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitCottontail");
		OWL_FOOD.add("com.animania.common.entities.amphibians.EntityFrogs");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockWhite");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockBlue");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockTaupe");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockPurple");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockPeach");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockCharcoal");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockOpal");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlWhite");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlBlue");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlTaupe");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlPurple");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlPeach");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlCharcoal");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlOpal");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeachicklWhite");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickBlue");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickTaupe");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickPurple");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickPeach");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickCharcoal");
		OWL_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickOpal");

		SNAKE_FOOD.add(EntityRabbit.class.getName());
		SNAKE_FOOD.add(EntityChicken.class.getName());
		SNAKE_FOOD.add(EntityParrot.class.getName());
		SNAKE_FOOD.add(EntityBat.class.getName());
		SNAKE_FOOD.add("familiarfauna.entities.EntityTurkey");
		SNAKE_FOOD.add("seraphaestus.historicizedmedicine.Mob.Rat.EntityRat"); //Todo: Get all peacock, rabbit, and other small animal breeds from Animania
		SNAKE_FOOD.add("com.animania.common.entities.rodents.EntityHamster");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityChickPlymouthRock");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityChickWyandotte");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityChickOrpington");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityChickLeghorn");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityChickRhodeIslandRed");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityRoosterPlymouthRock");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityRoosterWyandotte");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityRoosterOrpington");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityRoosterLeghorn");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityRoosterRhodeIslandRed");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityHenPlymouthRock");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityHenWyandotte");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityHenOrpington");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityHenLeghorn");
		SNAKE_FOOD.add("com.animania.common.entities.chickens.EntityHenRhodeIslandRed");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckChinchilla");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeChinchilla");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitChinchilla");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckRex");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeRex");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitRex");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckNewZealand");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeNewZealand");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitNewZealand");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckJack");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeJack");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitJack");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckLop");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeLop");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitLop");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckHavana");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeHavana");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitHavana");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckDutch");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeDutch");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitDutch");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitBuckCottontail");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitDoeCottontail");
		SNAKE_FOOD.add("com.animania.common.entities.rodents.rabbits.EntityRabbitKitCottontail");
		SNAKE_FOOD.add("com.animania.common.entities.amphibians.EntityFrogs");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockWhite");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockBlue");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockTaupe");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockPurple");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockPeach");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockCharcoal");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeacockOpal");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlWhite");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlBlue");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlTaupe");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlPurple");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlPeach");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlCharcoal");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeafowlOpal");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeachicklWhite");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickBlue");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickTaupe");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickPurple");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickPeach");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickCharcoal");
		SNAKE_FOOD.add("com.animania.common.entities.peacocks.EntityPeachickOpal");


		WEREWOLF_FOOD.add(EntitySheep.class.getName());
		WEREWOLF_FOOD.add(EntityCow.class.getName());
		WEREWOLF_FOOD.add(EntityPig.class.getName());
		WEREWOLF_FOOD.add(EntityHorse.class.getName());
		WEREWOLF_FOOD.add(EntityDonkey.class.getName());
		WEREWOLF_FOOD.add(EntityMule.class.getName());
		WEREWOLF_FOOD.add(EntityLlama.class.getName());
		WEREWOLF_FOOD.add(EntityMooshroom.class.getName());
		WEREWOLF_FOOD.add("com.animania.common.entities.pigs.EntitySowYorkshire"); //Todo: Get every single breed animania adds for its animals and support it ಠ_ಠ

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

