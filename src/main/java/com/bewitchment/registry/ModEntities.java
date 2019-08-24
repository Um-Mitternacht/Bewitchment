package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.misc.EntityCypressBroom;
import com.bewitchment.common.entity.misc.EntityElderBroom;
import com.bewitchment.common.entity.misc.EntityJuniperBroom;
import com.bewitchment.common.entity.misc.EntityYewBroom;
import com.bewitchment.common.entity.spirit.demon.*;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings({"unused", "SameParameterValue", "WeakerAccess"})
public class ModEntities {
	public static int entity_id = 0;
	
	public static final EntityEntry cypress_broom = createEntityEntry(EntityCypressBroom.class, "cypress_broom");
	public static final EntityEntry elder_broom = createEntityEntry(EntityElderBroom.class, "elder_broom");
	public static final EntityEntry juniper_broom = createEntityEntry(EntityJuniperBroom.class, "juniper_broom");
	public static final EntityEntry yew_broom = createEntityEntry(EntityYewBroom.class, "yew_broom");
	
	public static final EntityEntry lizard = createEntityEntry(EntityLizard.class, "lizard", 0x568203, 0x0070bb, EnumCreatureType.CREATURE, Bewitchment.config.lizardWeight, Bewitchment.config.lizardMin, Bewitchment.config.lizardMax, Bewitchment.config.lizardBiomes);
	public static final EntityEntry newt = createEntityEntry(EntityNewt.class, "newt", 0x000000, 0xffd300, EnumCreatureType.CREATURE, Bewitchment.config.newtWeight, Bewitchment.config.newtMin, Bewitchment.config.newtMax, Bewitchment.config.newtBiomes);
	public static final EntityEntry owl = createEntityEntry(EntityOwl.class, "owl", 0xaf813f, 0x6e5127, EnumCreatureType.CREATURE, Bewitchment.config.owlWeight, Bewitchment.config.owlMin, Bewitchment.config.owlMax, Bewitchment.config.owlBiomes);
	public static final EntityEntry raven = createEntityEntry(EntityRaven.class, "raven", 0x222222, 0x280638, EnumCreatureType.CREATURE, Bewitchment.config.ravenWeight, Bewitchment.config.ravenMin, Bewitchment.config.ravenMax, Bewitchment.config.ravenBiomes);
	public static final EntityEntry snake = createEntityEntry(EntitySnake.class, "snake", 0xfF9779, 0x696969, EnumCreatureType.CREATURE, Bewitchment.config.snakeWeight, Bewitchment.config.snakeMin, Bewitchment.config.snakeMax, Bewitchment.config.snakeBiomes);
	public static final EntityEntry toad = createEntityEntry(EntityToad.class, "toad", 0xa9ba9d, 0xc3b091, EnumCreatureType.CREATURE, Bewitchment.config.toadWeight, Bewitchment.config.toadMin, Bewitchment.config.toadMax, Bewitchment.config.toadBiomes);
	
	public static final EntityEntry black_dog = createEntityEntry(EntityBlackDog.class, "black_dog", 0x000000, 0x000000, EnumCreatureType.MONSTER, Bewitchment.config.blackDogWeight, Bewitchment.config.blackDogMin, Bewitchment.config.blackDogMax, Bewitchment.config.blackDogBiomes);
	
	public static final EntityEntry hellhound = createEntityEntry(EntityHellhound.class, "hellhound", 0x555555, 0xed2939, EnumCreatureType.MONSTER, Bewitchment.config.hellhoundWeight, Bewitchment.config.hellhoundMin, Bewitchment.config.hellhoundMax, Bewitchment.config.hellhoundBiomes);
	public static final EntityEntry feuerwurm = createEntityEntry(EntityFeuerwurm.class, "feuerwurm", 0x555555, 0xff9966, EnumCreatureType.MONSTER, Bewitchment.config.feuerwurmWeight, Bewitchment.config.feuerwurmMin, Bewitchment.config.feuerwurmMax, Bewitchment.config.feuerwurmBiomes);
	
	public static final EntityEntry demon = createEntityEntry(EntityDemon.class, "demon", 0x555555, 0xed2939);
	public static final EntityEntry demoness = createEntityEntry(EntityDemoness.class, "demoness", 0x555555, 0xed2939);
	public static final EntityEntry imp = createEntityEntry(EntityImp.class, "imp", 0x555555, 0xed2939);
	
	private static EntityEntry createEntityEntry(Class<? extends Entity> clazz, String name) {
		return EntityEntryBuilder.create().entity(clazz).id(new ResourceLocation(Bewitchment.MODID, name), entity_id++).name(Bewitchment.MODID + "." + name).tracker(128, 1, true).build();
	}
	
	private static EntityEntry createEntityEntry(Class<? extends Entity> clazz, String name, int solidColor, int spotColor) {
		return EntityEntryBuilder.create().entity(clazz).id(new ResourceLocation(Bewitchment.MODID, name), entity_id++).name(Bewitchment.MODID + "." + name).tracker(64, 1, true).egg(solidColor, spotColor).build();
	}
	
	private static EntityEntry createEntityEntry(Class<? extends Entity> clazz, String name, int solidColor, int spotColor, EnumCreatureType type, int weight, int min, int max, List<String> types) {
		Set<Biome> biomes = new HashSet<>();
		for (String typeName : types) biomes.addAll(BiomeDictionary.getBiomes(Type.getType(typeName)));
		return EntityEntryBuilder.create().entity(clazz).id(new ResourceLocation(Bewitchment.MODID, name), entity_id++).name(Bewitchment.MODID + "." + name).tracker(64, 1, true).egg(solidColor, spotColor).spawn(type, weight, min, max, biomes).build();
	}
}