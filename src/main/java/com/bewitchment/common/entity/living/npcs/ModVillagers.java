package com.bewitchment.common.entity.living.npcs;

import com.bewitchment.common.lib.LibMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

/**
 * Created by Joseph on 2/9/2019. Credit to Lfja, Pokefenn, and AlexThe666 for bits of the code.
 */
@Mod.EventBusSubscriber(modid = LibMod.MOD_ID)
public class ModVillagers {

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
	public VillagerRegistry.VillagerProfession hunter;
	public VillagerRegistry.VillagerProfession necromancer;
	public VillagerRegistry.VillagerProfession alchemist;

}