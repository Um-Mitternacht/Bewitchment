package com.bewitchment.common.core.helper;

import java.util.Set;

import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.transformation.DefaultTransformations;
import com.bewitchment.common.content.transformation.capability.CapabilityTransformationData;
import com.google.common.collect.Sets;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.INpc;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityEvoker;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityVindicator;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteractSpecific;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by Joseph on 8/25/2018. Credit to Alexthe666 for some of the code.
 */

public class MobHelper {
	
	public static final Set<String> VILLAGERS = Sets.newHashSet();
	public static final Set<String> SPIRITS = Sets.newHashSet();
	public static final Set<String> CANIDS = Sets.newHashSet();
	public static final Set<String> DEMONS = Sets.newHashSet();
	public static final Set<String> OWL_FOOD = Sets.newHashSet();
	public static final Set<String> SNAKE_FOOD = Sets.newHashSet();
	public static final Set<String> UNDEAD_BODY = Sets.newHashSet();
	public static final Set<String> HUMANS = Sets.newHashSet();
	
	public static void init() {
		
		//Don't overload them for the sake of adding stuff
		//Eg: endermen are not in any of these lists
		//Less is more in this case
		
		SPIRITS.add(EntityGhast.class.getName());
		SPIRITS.add(EntityVex.class.getName());
		SPIRITS.add("thaumcraft.common.entities.monster.EntityWisp"); // <-- Example of fully qualified name
		
		DEMONS.add(EntityBlaze.class.getName());
		
		CANIDS.add(EntityWolf.class.getName());
		
		//Villagers are already included in the list
		HUMANS.add(EntityWitch.class.getName());
		HUMANS.add(EntityIllusionIllager.class.getName());
		HUMANS.add(EntityEvoker.class.getName());
		HUMANS.add(EntityVindicator.class.getName());
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistCleric");
		HUMANS.add("thaumcraft.common.entities.monster.cult.EntityCultistKnight");
		
		OWL_FOOD.add(EntityRabbit.class.getName());
		OWL_FOOD.add(EntityChicken.class.getName());
		OWL_FOOD.add(EntityParrot.class.getName());
		OWL_FOOD.add(EntityBat.class.getName());
		
		SNAKE_FOOD.add(EntityRabbit.class.getName());
		SNAKE_FOOD.add(EntityChicken.class.getName());
		SNAKE_FOOD.add(EntityParrot.class.getName());
		SNAKE_FOOD.add(EntityBat.class.getName());
		
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
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformationData.CAPABILITY, null).getType()==DefaultTransformations.SPECTRE) {
			return true;
		}
		return SPIRITS.contains(entity.getClass().getName());
	}

	//For usage in silver
	public static boolean isCorporealUndead(EntityLivingBase entity) {
		if (entity.getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
			return true;
		}
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformationData.CAPABILITY, null).getType()==DefaultTransformations.VAMPIRE) {
			return true;
		}
		return UNDEAD_BODY.contains(entity.getClass().getName());
	}

	//For usage in aconite
	public static boolean isCanid(EntityLivingBase entity) {
		if (entity instanceof EntityPlayer && entity.getCapability(CapabilityTransformationData.CAPABILITY, null).getType()==DefaultTransformations.WEREWOLF) {
			return true;
		}
		return CANIDS.contains(entity.getClass().getName());
	}
}

