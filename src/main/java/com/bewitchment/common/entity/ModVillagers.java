package com.bewitchment.common.entity;

import net.minecraftforge.fml.common.registry.VillagerRegistry;

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

	//Villager professions
	public VillagerRegistry.VillagerProfession hedge_witch;
	public VillagerRegistry.VillagerProfession rockhound;
	public VillagerRegistry.VillagerProfession herbalist;
	public VillagerRegistry.VillagerProfession alchemist;
	public VillagerRegistry.VillagerProfession hunter;
}
