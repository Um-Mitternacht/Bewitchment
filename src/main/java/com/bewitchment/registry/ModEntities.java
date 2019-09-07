package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
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

import java.util.Arrays;
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
	
	public static final EntityEntry lizard = createEntityEntry(EntityLizard.class, "lizard", 0x568203, 0x0070bb, EnumCreatureType.CREATURE, 20, 1, 4, Arrays.asList(ModConfig.mobSpawns.lizardBiomes));
	public static final EntityEntry newt = createEntityEntry(EntityNewt.class, "newt", 0x000000, 0xffd300, EnumCreatureType.CREATURE, 20, 1, 4, Arrays.asList(ModConfig.mobSpawns.newtBiomes));
	public static final EntityEntry owl = createEntityEntry(EntityOwl.class, "owl", 0xaf813f, 0x6e5127, EnumCreatureType.CREATURE, 20, 1, 4, Arrays.asList(ModConfig.mobSpawns.owlBiomes));
	public static final EntityEntry raven = createEntityEntry(EntityRaven.class, "raven", 0x222222, 0x280638, EnumCreatureType.CREATURE, 20, 1, 4, Arrays.asList(ModConfig.mobSpawns.ravenBiomes));
	public static final EntityEntry snake = createEntityEntry(EntitySnake.class, "snake", 0xfF9779, 0x696969, EnumCreatureType.CREATURE, 20, 1, 4, Arrays.asList(ModConfig.mobSpawns.snakeBiomes));
	public static final EntityEntry toad = createEntityEntry(EntityToad.class, "toad", 0xa9ba9d, 0xc3b091, EnumCreatureType.CREATURE, 20, 1, 4, Arrays.asList(ModConfig.mobSpawns.toadBiomes));
	
	public static final EntityEntry black_dog = createEntityEntry(EntityBlackDog.class, "black_dog", 0x000000, 0x000000, EnumCreatureType.MONSTER, 6, 1, 4, Arrays.asList(ModConfig.mobSpawns.blackDogBiomes));
	
	public static final EntityEntry hellhound = createEntityEntry(EntityHellhound.class, "hellhound", 0x555555, 0xed2939, EnumCreatureType.MONSTER, 6, 1, 4, Arrays.asList(ModConfig.mobSpawns.hellhoundBiomes));
	public static final EntityEntry serpent = createEntityEntry(EntitySerpent.class, "serpent", 0x555555, 0xff9966, EnumCreatureType.MONSTER, 6, 1, 4, Arrays.asList(ModConfig.mobSpawns.serpentBiomes));
	
	public static final EntityEntry demon = createEntityEntry(EntityDemon.class, "demon", 0x555555, 0xed2939);
	public static final EntityEntry demoness = createEntityEntry(EntityDemoness.class, "demoness", 0x555555, 0xed2939);
	public static final EntityEntry imp = createEntityEntry(EntityImp.class, "imp", 0x555555, 0xed2939);
	
	private static EntityEntry createEntityEntry(Class<? extends Entity> clazz, String name) {
		return EntityEntryBuilder.create().entity(clazz).id(new ResourceLocation(Bewitchment.MODID, name), entity_id++).name(Bewitchment.MODID + "." + name).tracker(64, 1, true).build();
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