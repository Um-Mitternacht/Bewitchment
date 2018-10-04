package com.bewitchment.common.integration.common;

import net.minecraft.entity.Entity;
import net.minecraft.entity.INpc;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityParrot;
import net.minecraft.entity.passive.EntityRabbit;

/**
 * Created by Joseph on 8/25/2018. Credit to Alexthe666 for some of the code.
 */

//A set of utilities to let mobs wreak havoc and interact with each other
//Fixme: Overhaul isOwlFodder and similar methods, move it to DietaryUtils, and set up a class whitelist. Current means are messy and possibly straining on resources.
public class DietaryUtils {

	public static boolean isVillager(Entity entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof INpc || className.contains("VillagerMCA") || className.contains("MillVillager") || className.contains("Citizen");
	}

	public static boolean isAnimaniaMob(Entity entity) {
		String className = entity.getClass().getCanonicalName().toLowerCase();
		return className.contains("animania");
	}

	public static boolean isOwlFodder(Entity entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof EntityRabbit || entity instanceof EntityBat || entity instanceof EntityChicken || entity instanceof EntityParrot || className.contains("Rat") || className.contains("Hedgehog") || className.contains("Hamster") || className.contains("Squirrel") || className.contains("Hare") || className.contains("Fox") || className.contains("Weasel") || className.contains("Pigeon") || className.contains("Turkey") || className.contains("Mouse") || className.contains("Bat") || className.contains("Lizard") || className.contains("Frog") || className.contains("Toad") || className.contains("Snake") || className.contains("Beetle") || className.contains("Chinchilla") || className.contains("Cavy") || className.contains("GuineaPig") || className.contains("Crow") || className.contains("Raven") || className.contains("Pheasant") || className.contains("Partridge") || className.contains("Jackdaw") || className.contains("Mongoose") || className.contains("Rooster") || className.contains("Hen") || className.contains("Chick") || className.contains("Shrew") || className.contains("Mole") || className.contains("Vole") || className.contains("Lemming") || className.contains("Jird") || className.contains("Jerboa") || className.contains("Gerbil") || className.contains("Muskrat") || className.contains("Marmot") || className.contains("Deer") || className.contains("Ferret") || className.contains("Chinchilla");
	}

	public static boolean isSnakeFodder(Entity entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof EntityRabbit || entity instanceof EntitySpider || entity instanceof EntityChicken || className.contains("Rat") || className.contains("Mouse") || className.contains("Hamster") || className.contains("Vole") || className.contains("Shrew") || className.contains("Weasel") || className.contains("Mole") || className.contains("Blindworm") || className.contains("Frog") || className.contains("Toad") || className.contains("Newt") || className.contains("Salamander") || className.contains("GuineaPig") || className.contains("Cavy") || className.contains("Chick") || className.contains("Chinchilla");
	}
}
