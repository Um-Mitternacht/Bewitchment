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

	}

	public void setRandomProfession(EntityVillager entity, Random rand) {
		entity.setProfession(professions.get(rand.nextInt(professions.size())));
	}

	private void register(VillagerRegistry.VillagerProfession prof, int id) {
		professions.put(id, prof);
	}
}
