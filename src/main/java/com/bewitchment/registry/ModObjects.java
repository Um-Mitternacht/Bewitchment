package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.block.*;
import com.bewitchment.common.block.tile.entity.TileEntityCrystalBall;
import com.bewitchment.common.block.tile.entity.TileEntityDistillery;
import com.bewitchment.common.block.tile.entity.TileEntitySpinningWheel;
import com.bewitchment.common.block.tile.entity.TileEntityWitchesOven;
import com.bewitchment.common.block.util.*;
import com.bewitchment.common.item.ItemSalt;
import com.bewitchment.common.item.equipment.baubles.*;
import com.bewitchment.common.item.food.ItemGarlic;
import com.bewitchment.common.item.food.ItemHeart;
import com.bewitchment.common.item.tool.ItemAthame;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.common.item.util.ModItemAxe;
import com.bewitchment.common.item.util.ModItemDoor;
import com.bewitchment.common.item.util.ModItemPickaxe;
import com.bewitchment.common.world.gen.tree.WorldGenCypressTree;
import com.bewitchment.common.world.gen.tree.WorldGenElderTree;
import com.bewitchment.common.world.gen.tree.WorldGenJuniperTree;
import com.bewitchment.common.world.gen.tree.WorldGenYewTree;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions", "ArraysAsListWithZeroOrOneArgument"})
public class ModObjects {
	public static final List<Object> REGISTRY = new ArrayList<>();
	
	public static final ItemArmor.ArmorMaterial ARMOR_SILVER = EnumHelper.addArmorMaterial("silver", Bewitchment.MODID + ":silver", 11, new int[]{2, 4, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0);
	public static final ItemArmor.ArmorMaterial ARMOR_COLD_IRON = EnumHelper.addArmorMaterial("cold_iron", Bewitchment.MODID + ":cold_iron", 18, new int[]{2, 6, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
	public static final ItemArmor.ArmorMaterial ARMOR_WITCHES = EnumHelper.addArmorMaterial("witches", Bewitchment.MODID + ":witches", 8, new int[]{1, 2, 3, 1}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
	
	public static final Item.ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial("silver", 1, 131, 12, 1.5f, 22);
	public static final Item.ToolMaterial TOOL_COLD_IRON = EnumHelper.addToolMaterial("cold_iron", 2, 425, 7, 2.5f, 14);
	
	//No Item
	public static final ModBlockCrops crop_aconitum = new ModBlockCrops("crop_aconitum");
	public static final ModBlockCrops crop_belladonna = new ModBlockCrops("crop_belladonna");
	public static final ModBlockCrops crop_garlic = new ModBlockCrops("crop_garlic");
	public static final ModBlockCrops crop_hellebore = new ModBlockCrops("crop_hellebore");
	public static final ModBlockCrops crop_mandrake = new ModBlockCrops("crop_mandrake");
	public static final ModBlockCrops crop_white_sage = new ModBlockCrops("crop_white_sage");
	public static final ModBlockCrops crop_wormwood = new ModBlockCrops("crop_wormwood");
	public static final Block salt_barrier = new BlockSaltBarrier();
	//Tiles
	public static final Block witches_oven = registerTileEntity(new BlockWitchesOven(), TileEntityWitchesOven.class);
	public static final Block distillery = registerTileEntity(new BlockDistillery(), TileEntityDistillery.class);
	public static final Block spinning_wheel = registerTileEntity(new BlockSpinningWheel(), TileEntitySpinningWheel.class);
	public static final Block crystal_ball = registerTileEntity(new BlockCrystalBall(), TileEntityCrystalBall.class);
	//Material Blocks
	public static final Block block_of_amethyst = new ModBlock("block_of_amethyst", Material.GLASS, SoundType.GLASS, 5, 30, "pickaxe", 2, "blockAmethyst");
	public static final Block block_of_garnet = new ModBlock("block_of_garnet", Material.GLASS, SoundType.GLASS, 5, 30, "pickaxe", 2, "blockGarnet");
	public static final Block block_of_moonstone = new ModBlock("block_of_moonstone", Material.GLASS, SoundType.GLASS, 5, 30, "pickaxe", 2, "blockMoonstone");
	public static final Block block_of_silver = new ModBlock("block_of_silver", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, "blockSilver");
	public static final Block block_of_cold_iron = new ModBlock("block_of_cold_iron", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, "blockColdIron");
	public static final Block block_of_salt = new ModBlock("block_of_salt", Material.ROCK, SoundType.STONE, 5, 30, "pickaxe", 0, "blockSalt");
	public static final Block amethyst_ore = new ModBlock("amethyst_ore", Material.ROCK, SoundType.STONE, 3, 15, "pickaxe", 2, "oreAmethyst");
	public static final Block garnet_ore = new ModBlock("garnet_ore", Material.ROCK, SoundType.STONE, 3, 15, "pickaxe", 2, "oreGarnet");
	public static final Block moonstone_ore = new ModBlock("moonstone_ore", Material.ROCK, SoundType.STONE, 3, 15, "pickaxe", 2, "oreMoonstone");
	public static final Block silver_ore = new ModBlock("silver_ore", Material.ROCK, SoundType.STONE, 3, 15, "pickaxe", 2, "oreSilver");
	public static final Block salt_ore = new ModBlock("salt_ore", Material.ROCK, SoundType.STONE, 3, 15, "pickaxe", 0, "oreSalt");
	
	public static final Block coquina = new ModBlock("coquina", Material.ROCK, SoundType.STONE, 5, 30, "pickaxe", 0, "coquina");
	public static final Block coquina_bricks = new ModBlock("coquina_bricks", Material.ROCK, SoundType.STONE, 5, 30, "pickaxe", 0);
	public static final Block chiseled_coquina = new ModBlock("chiseled_coquina", Material.ROCK, SoundType.STONE, 5, 30, "pickaxe", 0);
	public static final Block nethersteel = new ModBlock("nethersteel", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2);
	public static final Block perpetual_ice = new ModBlock("perpetual_ice", Material.ICE, SoundType.GLASS, 0.5f, 2.5f, "", 0);
	public static final Block perpetual_ice_stairs = new ModBlockStairs("perpetual_ice_stairs", perpetual_ice);
	public static final Block perpetual_ice_slab = new ModBlockSlab("perpetual_ice_slab", perpetual_ice);
	public static final Block perpetual_ice_fence = new ModBlockFence("perpetual_ice_fence", perpetual_ice);
	public static final Block embittered_bricks = new ModBlock("embittered_bricks", Material.PACKED_ICE, SoundType.STONE, 1.5f, 30, "pickaxe", 0);
	public static final Block cracked_embittered_bricks = new ModBlock("cracked_embittered_bricks", Material.PACKED_ICE, SoundType.STONE, 1.5f, 30, "pickaxe", 0);
	public static final Block chiseled_embittered_bricks = new ModBlock("chiseled_embittered_bricks", Material.PACKED_ICE, SoundType.STONE, 1.5f, 30, "pickaxe", 0);
	public static final Block embittered_brick_stairs = new ModBlockStairs("embittered_brick_stairs", embittered_bricks);
	public static final Block embittered_bricks_slab = new ModBlockSlab("embittered_bricks_slab", embittered_bricks);
	public static final Block embittered_brick_fence = new ModBlockFence("embittered_brick_fence", embittered_bricks);
	
	public static final Block scorned_bricks = new ModBlock("scorned_bricks", Material.ROCK, SoundType.STONE, 25.2f, 1001, "pickaxe", 2);
	public static final Block cracked_scorned_bricks = new ModBlock("cracked_scorned_bricks", Material.ROCK, SoundType.STONE, 25.2f, 1001, "pickaxe", 2);
	public static final Block chiseled_scorned_bricks = new ModBlock("chiseled_scorned_bricks", Material.ROCK, SoundType.STONE, 25.2f, 1001, "pickaxe", 2);
	public static final Block scorned_brick_stairs = new ModBlockStairs("scorned_brick_stairs", scorned_bricks);
	public static final Block scorned_bricks_slab = new ModBlockSlab("scorned_bricks_slab", scorned_bricks);
	public static final Block scorned_brick_fence = new ModBlockFence("scorned_brick_fence", scorned_bricks);
	//Trees
	public static final Block cypress_sapling = new ModBlockSapling("cypress_sapling", new WorldGenCypressTree(false), "treeSapling");
	public static final Block elder_sapling = new ModBlockSapling("elder_sapling", new WorldGenElderTree(false), "treeSapling");
	public static final Block juniper_sapling = new ModBlockSapling("juniper_sapling", new WorldGenJuniperTree(false), "treeSapling");
	public static final Block yew_sapling = new ModBlockSapling("yew_sapling", new WorldGenYewTree(false), "treeSapling");
	public static final Block cypress_wood = new ModBlockPillar("cypress_wood", Material.WOOD, SoundType.WOOD, 2, 10, "axe", 0, "logWood");
	public static final Block elder_wood = new ModBlockPillar("elder_wood", Material.WOOD, SoundType.WOOD, 2, 10, "axe", 0, "logWood");
	public static final Block juniper_wood = new ModBlockPillar("juniper_wood", Material.WOOD, SoundType.WOOD, 2, 10, "axe", 0, "logWood");
	public static final Block yew_wood = new ModBlockPillar("yew_wood", Material.WOOD, SoundType.WOOD, 2, 10, "axe", 0, "logWood");
	public static final Block cypress_planks = new ModBlock("cypress_planks", Material.WOOD, SoundType.WOOD, 2, 15, "axe", 0, "plankWood");
	public static final Block elder_planks = new ModBlock("elder_planks", Material.WOOD, SoundType.WOOD, 2, 15, "axe", 0, "plankWood");
	public static final Block juniper_planks = new ModBlock("juniper_planks", Material.WOOD, SoundType.WOOD, 2, 15, "axe", 0, "plankWood");
	public static final Block yew_planks = new ModBlock("yew_planks", Material.WOOD, SoundType.WOOD, 2, 15, "axe", 0, "plankWood");
	public static final Block cypress_leaves = new ModBlockLeaves("cypress_leaves", "treeLeaves");
	public static final Block elder_leaves = new ModBlockLeaves("elder_leaves", "treeLeaves");
	public static final Block juniper_leaves = new ModBlockLeaves("juniper_leaves", "treeLeaves");
	public static final Block yew_leaves = new ModBlockLeaves("yew_leaves", "treeLeaves");
	//Decor
	public static final ModItemDoor cypress_door = new ModItemDoor("cypress_door", cypress_planks);
	public static final ModItemDoor elder_door = new ModItemDoor("elder_door", elder_planks);
	public static final ModItemDoor juniper_door = new ModItemDoor("juniper_door", juniper_planks);
	public static final ModItemDoor yew_door = new ModItemDoor("yew_door", yew_planks);
	public static final Block cypress_wood_stairs = new ModBlockStairs("cypress_wood_stairs", cypress_planks, "stairWood");
	public static final Block elder_wood_stairs = new ModBlockStairs("elder_wood_stairs", elder_planks, "stairWood");
	public static final Block juniper_wood_stairs = new ModBlockStairs("juniper_wood_stairs", juniper_planks, "stairWood");
	public static final Block yew_wood_stairs = new ModBlockStairs("yew_wood_stairs", yew_planks, "stairWood");
	public static final Block cypress_wood_slab = new ModBlockSlab("cypress_wood_slab", cypress_planks, "slabWood");
	public static final Block elder_wood_slab = new ModBlockSlab("elder_wood_slab", elder_planks, "slabWood");
	public static final Block juniper_wood_slab = new ModBlockSlab("juniper_wood_slab", juniper_planks, "slabWood");
	public static final Block yew_wood_slab = new ModBlockSlab("yew_wood_slab", yew_planks, "slabWood");
	public static final Block cypress_trapdoor = new ModBlockTrapdoor("cypress_trapdoor", cypress_planks);
	public static final Block elder_trapdoor = new ModBlockTrapdoor("elder_trapdoor", elder_planks);
	public static final Block juniper_trapdoor = new ModBlockTrapdoor("juniper_trapdoor", juniper_planks);
	public static final Block yew_trapdoor = new ModBlockTrapdoor("yew_trapdoor", yew_planks);
	public static final Block cypress_fence_gate = new ModBlockFenceGate("cypress_fence_gate", cypress_planks, "fenceGateWood");
	public static final Block elder_fence_gate = new ModBlockFenceGate("elder_fence_gate", elder_planks, "fenceGateWood");
	public static final Block juniper_fence_gate = new ModBlockFenceGate("juniper_fence_gate", juniper_planks, "fenceGateWood");
	public static final Block yew_fence_gate = new ModBlockFenceGate("yew_fence_gate", yew_planks, "fenceGateWood");
	public static final Block cypress_fence = new ModBlockFence("cypress_fence", cypress_planks, "fenceWood");
	public static final Block elder_fence = new ModBlockFence("elder_fence", elder_planks, "fenceWood");
	public static final Block juniper_fence = new ModBlockFence("juniper_fence", juniper_planks, "fenceWood");
	public static final Block yew_fence = new ModBlockFence("yew_fence", yew_planks, "fenceWood");
	public static final Block cypress_pressure_plate = new ModBlockPressurePlate("cypress_pressure_plate", cypress_planks);
	public static final Block elder_pressure_plate = new ModBlockPressurePlate("elder_pressure_plate", elder_planks);
	public static final Block juniper_pressure_plate = new ModBlockPressurePlate("juniper_pressure_plate", juniper_planks);
	public static final Block yew_pressure_plate = new ModBlockPressurePlate("yew_pressure_plate", yew_planks);
	public static final Block cypress_button = new ModBlockButton("cypress_button", cypress_planks);
	public static final Block elder_button = new ModBlockButton("elder_button", elder_planks);
	public static final Block juniper_button = new ModBlockButton("juniper_button", juniper_planks);
	public static final Block yew_button = new ModBlockButton("yew_button", yew_planks);
	//Armor
	public static final Item silver_helmet = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.HEAD), "silver_helmet");
	public static final Item silver_chestplate = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.CHEST), "silver_chestplate");
	public static final Item silver_leggings = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.LEGS), "silver_leggings");
	public static final Item silver_boots = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.FEET), "silver_boots");
	public static final Item cold_iron_helmet = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.HEAD), "cold_iron_helmet");
	public static final Item cold_iron_chestplate = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.CHEST), "cold_iron_chestplate");
	public static final Item cold_iron_leggings = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.LEGS), "cold_iron_leggings");
	public static final Item cold_iron_boots = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.FEET), "cold_iron_boots");
	public static final Item witches_cowl = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD), "witches_cowl");
	public static final Item witches_hat = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD), "witches_hat", Arrays.asList(s -> s.getDisplayName().toLowerCase().contains("faith")));
	public static final Item witches_robes = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.CHEST), "witches_robes");
	public static final Item witches_pants = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.LEGS), "witches_pants");
	//Tools
	public static final Item silver_sword = Util.registerItem(new ItemSword(TOOL_SILVER), "silver_sword");
	public static final Item silver_pickaxe = Util.registerItem(new ModItemPickaxe(TOOL_SILVER), "silver_pickaxe");
	public static final Item silver_axe = Util.registerItem(new ModItemAxe(TOOL_SILVER), "silver_axe");
	public static final Item silver_shovel = Util.registerItem(new ItemSpade(TOOL_SILVER), "silver_shovel");
	public static final Item silver_hoe = Util.registerItem(new ItemHoe(TOOL_SILVER), "silver_hoe");
	public static final Item cold_iron_sword = Util.registerItem(new ItemSword(TOOL_COLD_IRON), "cold_iron_sword", Arrays.asList(s -> s.getDisplayName().equalsIgnoreCase("Hudson Bat") || s.getDisplayName().equalsIgnoreCase("Masashi Bat") || s.getDisplayName().equalsIgnoreCase("Emmanuel Bat") || s.getDisplayName().equalsIgnoreCase("Michael Bat") || s.getDisplayName().equalsIgnoreCase("Yoshihiro Bat") || s.getDisplayName().equalsIgnoreCase("Lewis Bat") || s.getDisplayName().equalsIgnoreCase("Katushiro Bat") || s.getDisplayName().equalsIgnoreCase("Ashley Bat")));
	public static final Item cold_iron_pickaxe = Util.registerItem(new ModItemPickaxe(TOOL_COLD_IRON), "cold_iron_pickaxe");
	public static final Item cold_iron_axe = Util.registerItem(new ModItemAxe(TOOL_COLD_IRON), "cold_iron_axe");
	public static final Item cold_iron_shovel = Util.registerItem(new ItemSpade(TOOL_COLD_IRON), "cold_iron_shovel");
	public static final Item cold_iron_hoe = Util.registerItem(new ItemHoe(TOOL_COLD_IRON), "cold_iron_hoe");
	public static final Item athame = new ItemAthame();
	public static final Item boline = new ItemBoline();
	//Baubles
	public static final Item hellish_bauble = new ItemHellishBauble();
	public static final Item horseshoe = new ItemHorseshoe();
	public static final Item nazar = new ItemNazar();
	public static final Item token_of_remedies = new ItemTokenOfRemedies();
	public static final Item grimoire_magia = Util.registerItem(new Item(), "grimoire_magia", Arrays.asList(s -> s.getDisplayName().equalsIgnoreCase("The Grimoire of Alice") || s.getDisplayName().equalsIgnoreCase("Grimoire of Alice")));
	//Materials
	public static final Item amethyst = Util.registerItem("amethyst", "gemAmethyst", "gemAll");
	public static final Item garnet = Util.registerItem("garnet", "gemGarnet", "gemAll");
	public static final Item moonstone = Util.registerItem("moonstone", "gemMoonstone", "gemAll");
	public static final Item silver_ingot = Util.registerItem("silver_ingot", "ingotSilver");
	public static final Item cold_iron_ingot = Util.registerItem("cold_iron_ingot", "ingotColdIron");
	public static final Item silver_nugget = Util.registerItem("silver_nugget", "nuggetSilver");
	public static final Item cold_iron_nugget = Util.registerItem("cold_iron_nugget", "nuggetColdIron");
	public static final Item salt = new ItemSalt();
	//Oven
	public static final Item unfired_jar = Util.registerItem("unfired_jar");
	public static final Item empty_jar = Util.registerItem("empty_jar");
	public static final Item oak_spirit = Util.registerItem("oak_spirit");
	public static final Item spruce_heart = Util.registerItem("spruce_heart");
	public static final Item birch_soul = Util.registerItem("birch_soul");
	public static final Item cloudy_oil = Util.registerItem("cloudy_oil");
	public static final Item acacia_resin = Util.registerItem("acacia_resin");
	public static final Item ebb_of_death = Util.registerItem("ebb_of_death");
	public static final Item droplet_of_wisdom = Util.registerItem("droplet_of_wisdom");
	public static final Item liquid_witchcraft = Util.registerItem("liquid_witchcraft");
	public static final Item essence_of_vitality = Util.registerItem("essence_of_vitality");
	//Distillery
	public static final Item cleansing_balm = Util.registerItem("cleansing_balm");
	public static final Item demonic_elixir = Util.registerItem("demonic_elixir");
	public static final Item everchanging_dew = Util.registerItem("everchanging_dew");
	public static final Item fiery_unguent = Util.registerItem("fiery_unguent");
	public static final Item heaven_extract = Util.registerItem("heaven_extract");
	public static final Item stone_ichor = Util.registerItem("stone_ichor");
	public static final Item undying_salve = Util.registerItem("undying_salve");
	//Loom
	public static final Item diabolical_vein = Util.registerItem("diabolical_vein");
	public static final Item golden_thread = Util.registerItem("golden_thread");
	public static final Item pure_filament = Util.registerItem("pure_filament");
	public static final Item witches_stitching = Util.registerItem("witches_stitching");
	//Plants
	public static final Item aconitum = Util.registerItem("aconitum", "cropAconitum");
	public static final Item belladonna = Util.registerItem("belladonna", "cropBelladonna");
	public static final Item garlic = new ItemGarlic();
	public static final Item hellebore = Util.registerItem("hellebore", "cropHellebore");
	public static final Item mandrake_root = Util.registerItem("mandrake_root", "cropMandrake");
	public static final Item white_sage = Util.registerItem("white_sage", "cropWhiteSage");
	public static final Item wormwood = Util.registerItem("wormwood", "cropWormwood");
	//Seeds
	public static final Item aconitum_seeds = Util.registerItem(new ItemSeeds(crop_aconitum, Blocks.FARMLAND), "aconitum_seeds");
	public static final Item belladonna_seeds = Util.registerItem(new ItemSeeds(crop_belladonna, Blocks.FARMLAND), "belladonna_seeds");
	public static final Item garlic_seeds = Util.registerItem(new ItemSeeds(crop_garlic, Blocks.FARMLAND), "garlic_seeds");
	public static final Item hellebore_seeds = Util.registerItem(new ItemSeeds(crop_hellebore, Blocks.FARMLAND), "hellebore_seeds");
	public static final Item mandrake_seeds = Util.registerItem(new ItemSeeds(crop_mandrake, Blocks.FARMLAND), "mandrake_seeds");
	public static final Item white_sage_seeds = Util.registerItem(new ItemSeeds(crop_white_sage, Blocks.FARMLAND), "white_sage_seeds");
	public static final Item wormwood_seeds = Util.registerItem(new ItemSeeds(crop_wormwood, Blocks.FARMLAND), "wormwood_seeds");
	//Food
	public static final Item elderberries = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 100), 0.1f), "elderberries");
	public static final Item juniper_berries = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 100), 0.1f), "juniper_berries");
	public static final Item yew_aril = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 100), 0.1f), "yew_aril");
	//Drops
	public static final Item lizard_leg = Util.registerItem("lizard_leg");
	public static final Item eye_of_newt = Util.registerItem("eye_of_newt");
	public static final Item owlets_wing = Util.registerItem("owlets_wing");
	public static final Item ravens_feather = Util.registerItem("ravens_feather");
	public static final Item adders_fork = Util.registerItem("adders_fork");
	public static final Item toe_of_frog = Util.registerItem("toe_of_frog");
	public static final Item hellhound_horn = Util.registerItem("hellhound_horn");
	public static final Item heart = new ItemHeart();
	public static final Item demon_heart = Util.registerItem("demon_heart");
	public static final Item snake_venom = Util.registerItem("snake_venom");
	public static final Item liquid_wroth = Util.registerItem("liquid_wroth");
	//Vanilla Drops
	public static final Item hoof = Util.registerItem("hoof");
	public static final Item eye_of_old = Util.registerItem(new Item(), "eye_of_old", Arrays.asList(s -> s.getDisplayName().equalsIgnoreCase("Haru") || s.getDisplayName().equalsIgnoreCase("Haruspex") || s.getDisplayName().equalsIgnoreCase("H4rv5p3x"), s -> s.getDisplayName().equalsIgnoreCase("Izuxe") || s.getDisplayName().equalsIgnoreCase("Izu") || s.getDisplayName().equalsIgnoreCase("Izuxe43ui520815")));
	public static final Item tongue_of_dog = Util.registerItem("tongue_of_dog");
	public static final Item wool_of_bat = Util.registerItem("wool_of_bat");
	//Misc
	public static final Item catechu_brown = Util.registerItem("catechu_brown", "dyeBrown");
	public static final Item dimensional_sand = Util.registerItem("dimensional_sand");
	public static final Item ectoplasm = Util.registerItem("ectoplasm");
	public static final Item iron_gall_ink = Util.registerItem("iron_gall_ink", "dyeBlack");
	public static final Item oak_apple_gall = Util.registerItem("oak_apple_gall");
	public static final Item spectral_dust = Util.registerItem("spectral_dust");
	public static final Item wood_ash = Util.registerItem("wood_ash");
	
	public static void preInit() {
		Bewitchment.proxy.ignoreProperty(cypress_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(elder_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(juniper_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(yew_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(cypress_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(elder_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(juniper_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(yew_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(cypress_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(elder_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(juniper_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(yew_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(cypress_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(elder_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(juniper_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(yew_fence_gate, BlockFenceGate.POWERED);
		
		crop_aconitum.setItems(aconitum_seeds, aconitum);
		crop_belladonna.setItems(belladonna_seeds, belladonna);
		crop_garlic.setItems(garlic_seeds, garlic);
		crop_hellebore.setItems(hellebore_seeds, hellebore);
		crop_mandrake.setItems(mandrake_seeds, mandrake_root);
		crop_white_sage.setItems(white_sage_seeds, white_sage);
		crop_wormwood.setItems(wormwood_seeds, wormwood);
	}
	
	private static Block registerTileEntity(Block block, Class<? extends TileEntity> tile) {
		GameRegistry.registerTileEntity(tile, block.getRegistryName());
		return block;
	}
}