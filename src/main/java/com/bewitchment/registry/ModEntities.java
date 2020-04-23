package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.common.entity.living.*;
import com.bewitchment.common.entity.misc.*;
import com.bewitchment.common.entity.spirit.demon.*;
import com.bewitchment.common.entity.spirit.ghost.EntityBlackDog;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
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
	public static final EntityEntry dragons_blood_broom = createEntityEntry(EntityDragonsBloodBroom.class, "dragons_blood_broom");
	
	public static final EntityEntry lizard = createEntityEntry(EntityLizard.class, "lizard", 0x568203, 0x0070bb, EnumCreatureType.CREATURE, ModConfig.mobSpawns.lizard.lizardWeight, ModConfig.mobSpawns.lizard.lizardMin, ModConfig.mobSpawns.lizard.lizardMax, Arrays.asList(ModConfig.mobSpawns.lizard.lizardBiomes));
	public static final EntityEntry owl = createEntityEntry(EntityOwl.class, "owl", 0xaf813f, 0x6e5127, EnumCreatureType.CREATURE, ModConfig.mobSpawns.owl.owlWeight, ModConfig.mobSpawns.owl.owlMin, ModConfig.mobSpawns.owl.owlMax, Arrays.asList(ModConfig.mobSpawns.owl.owlBiomes));
	public static final EntityEntry raven = createEntityEntry(EntityRaven.class, "raven", 0x222222, 0x280638, EnumCreatureType.CREATURE, ModConfig.mobSpawns.raven.ravenWeight, ModConfig.mobSpawns.raven.ravenMin, ModConfig.mobSpawns.raven.ravenMax, Arrays.asList(ModConfig.mobSpawns.raven.ravenBiomes));
	public static final EntityEntry snake = createEntityEntry(EntitySnake.class, "snake", 0xfF9779, 0x696969, EnumCreatureType.CREATURE, ModConfig.mobSpawns.snake.snakeWeight, ModConfig.mobSpawns.snake.snakeMin, ModConfig.mobSpawns.snake.snakeMax, Arrays.asList(ModConfig.mobSpawns.snake.snakeBiomes));
	public static final EntityEntry toad = createEntityEntry(EntityToad.class, "toad", 0xa9ba9d, 0xc3b091, EnumCreatureType.CREATURE, ModConfig.mobSpawns.toad.toadWeight, ModConfig.mobSpawns.toad.toadMin, ModConfig.mobSpawns.toad.toadMax, Arrays.asList(ModConfig.mobSpawns.toad.toadBiomes));
	
	public static final EntityEntry black_dog = createEntityEntry(EntityBlackDog.class, "black_dog", 0x000000, 0x000000, EnumCreatureType.MONSTER, ModConfig.mobSpawns.blackDog.blackDogWeight, ModConfig.mobSpawns.blackDog.blackDogMin, ModConfig.mobSpawns.blackDog.blackDogMax, Arrays.asList(ModConfig.mobSpawns.blackDog.blackDogBiomes));
	public static final EntityEntry ghost = createEntityEntry(EntityGhost.class, "ghost", 0x000000, 0x8F9779, EnumCreatureType.MONSTER, ModConfig.mobSpawns.ghost.ghostWeight, ModConfig.mobSpawns.ghost.ghostMin, ModConfig.mobSpawns.ghost.ghostMax, Arrays.asList(ModConfig.mobSpawns.ghost.ghostBiomes));
	
	public static final EntityEntry hellhound = createEntityEntry(EntityHellhound.class, "hellhound", 0x555555, 0xed2939, EnumCreatureType.MONSTER, ModConfig.mobSpawns.hellhound.hellhoundWeight, ModConfig.mobSpawns.hellhound.hellhoundMin, ModConfig.mobSpawns.hellhound.hellhoundMax, Arrays.asList(ModConfig.mobSpawns.hellhound.hellhoundBiomes));
	public static final EntityEntry feuerwurm = createEntityEntry(EntityFeuerwurm.class, "feuerwurm", 0x555555, 0xff9966, EnumCreatureType.MONSTER, ModConfig.mobSpawns.feuerwurm.feuerwurmWeight, ModConfig.mobSpawns.feuerwurm.feuerwurmMin, ModConfig.mobSpawns.feuerwurm.feuerwurmMax, Arrays.asList(ModConfig.mobSpawns.feuerwurm.feuerwurmBiomes));
	
	public static final EntityEntry werewolf = createEntityEntry(EntityWerewolf.class, "werewolf", 0x9F8170, 0x954535, EnumCreatureType.MONSTER, ModConfig.mobSpawns.werewolf.werewolfWeight, ModConfig.mobSpawns.werewolf.werewolfMin, ModConfig.mobSpawns.werewolf.werewolfMax, Arrays.asList(ModConfig.mobSpawns.werewolf.werewolfBiomes));
	
	public static final EntityEntry cleaver = createEntityEntry(EntityCleaver.class, "cleaver", 0x5C4033, 0x483C32, EnumCreatureType.MONSTER, ModConfig.mobSpawns.cleaver.cleaverWeight, ModConfig.mobSpawns.cleaver.cleaverMin, ModConfig.mobSpawns.cleaver.cleaverMax, Arrays.asList(ModConfig.mobSpawns.cleaver.cleaverBiomes));
	
	public static final EntityEntry demon = createEntityEntry(EntityDemon.class, "demon", 0x555555, 0xed2939);
	public static final EntityEntry demoness = createEntityEntry(EntityDemoness.class, "demoness", 0x555555, 0xed2939);
	public static final EntityEntry imp = createEntityEntry(EntityImp.class, "imp", 0x555555, 0xed2939);
	
	public static final EntityEntry bafometyr = createEntityEntry(EntityBafometyr.class, "bafometyr", 0x000000, 0x000000);
	
	public static final EntityEntry shadow_person = createEntityEntry(EntityShadowPerson.class, "shadow_person", 0x000000, 0x000000);
	
	public static final EntityEntry cambion = createEntityEntry(EntityCambion.class, "cambion", 0xC41E3A, 0x483C32);
	
	public static final EntityEntry druden = createEntityEntry(EntityDruden.class, "druden", 0x228B22, 0x8A3324, EnumCreatureType.MONSTER, ModConfig.mobSpawns.druden.drudenWeight, ModConfig.mobSpawns.druden.drudenMin, ModConfig.mobSpawns.druden.drudenMax, Arrays.asList(ModConfig.mobSpawns.druden.drudenBiomes));
	public static final EntityEntry baphomet = createEntityEntry(EntityBaphomet.class, "baphomet", 0x472f22, 0x690303);
	public static final EntityEntry leonard = createEntityEntry(EntityLeonard.class, "leonard", 0x472f22, 0xe8c91a);
	
	public static final EntityEntry silver_arrow = createEntityEntry(EntitySilverArrow.class, "silver_arrow");
	
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
