package com.bewitchment.common.entity;

import com.google.common.collect.Maps;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Map;
import java.util.Random;

/**
 * Created by Joseph on 2/9/2019.
 */
public class ModVillagers {

	public static final ModVillagers INSTANCE = new ModVillagers();

	//Demonic professions
	public VillagerRegistry.VillagerProfession warlord;
	public VillagerRegistry.VillagerProfession slaver;
	public VillagerRegistry.VillagerProfession noble;
	public VillagerRegistry.VillagerProfession brute;
	public VillagerRegistry.VillagerProfession apocryphal_teacher;
	public VillagerRegistry.VillagerProfession despot;

	//Villager professions
	public VillagerRegistry.VillagerProfession hedge_witch;
	public VillagerRegistry.VillagerProfession rockhound;
	public VillagerRegistry.VillagerProfession herbalist;
	public VillagerRegistry.VillagerProfession alchemist;
	public VillagerRegistry.VillagerProfession hunter;
	public VillagerRegistry.VillagerProfession necromancer;

	public Map<Integer, VillagerRegistry.VillagerProfession> professions = Maps.newHashMap();

	public void init() {

		//Demon professions
		warlord = new VillagerRegistry.VillagerProfession("bewitchment:warlord", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		slaver = new VillagerRegistry.VillagerProfession("bewitchment:slaver", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		noble = new VillagerRegistry.VillagerProfession("bewitchment:noble", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		brute = new VillagerRegistry.VillagerProfession("bewitchment:brute", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		apocryphal_teacher = new VillagerRegistry.VillagerProfession("bewitchment:apocryphal_teacher", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		despot = new VillagerRegistry.VillagerProfession("bewitchment:despot", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		//Human professions
		hedge_witch = new VillagerRegistry.VillagerProfession("bewitchment:warlord", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		rockhound = new VillagerRegistry.VillagerProfession("bewitchment:slaver", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		herbalist = new VillagerRegistry.VillagerProfession("bewitchment:noble", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		alchemist = new VillagerRegistry.VillagerProfession("bewitchment:brute", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		hunter = new VillagerRegistry.VillagerProfession("bewitchment:apocryphal_teacher", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
		necromancer = new VillagerRegistry.VillagerProfession("bewitchment:despot", "minecraft:textures/entity/zombie_villager/zombie_farmer.png", "minecraft:textures/entity/zombie_villager/zombie_farmer.png");
		{
		}
	}

	public void setRandomProfession(EntityVillager entity, Random rand) {
		entity.setProfession(professions.get(rand.nextInt(professions.size())));
	}

	private void register(VillagerRegistry.VillagerProfession prof, int id) {
		professions.put(id, prof);
	}
}
