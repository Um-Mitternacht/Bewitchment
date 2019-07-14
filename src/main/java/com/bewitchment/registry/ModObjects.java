package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.item.ItemBroom;
import com.bewitchment.common.block.*;
import com.bewitchment.common.block.crop.BlockCropsSpreading;
import com.bewitchment.common.block.plants.BlockEmbergrass;
import com.bewitchment.common.block.plants.BlockTorchwood;
import com.bewitchment.common.block.tile.entity.*;
import com.bewitchment.common.block.util.*;
import com.bewitchment.common.integration.chisel.ModBlockChisel;
import com.bewitchment.common.item.*;
import com.bewitchment.common.item.equipment.armor.ItemWitchesArmor;
import com.bewitchment.common.item.equipment.baubles.*;
import com.bewitchment.common.item.food.ItemGarlic;
import com.bewitchment.common.item.food.ItemHeart;
import com.bewitchment.common.item.tool.ItemAthame;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.common.item.tool.ItemGrimoireMagia;
import com.bewitchment.common.item.tool.ItemJuniperKey;
import com.bewitchment.common.item.util.ModItemAxe;
import com.bewitchment.common.item.util.ModItemBauble;
import com.bewitchment.common.item.util.ModItemDoor;
import com.bewitchment.common.item.util.ModItemPickaxe;
import com.bewitchment.common.world.gen.tree.WorldGenCypressTree;
import com.bewitchment.common.world.gen.tree.WorldGenElderTree;
import com.bewitchment.common.world.gen.tree.WorldGenJuniperTree;
import com.bewitchment.common.world.gen.tree.WorldGenYewTree;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.chisel.api.carving.CarvingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions", "SameParameterValue"})
public class ModObjects {
	public static final ItemArmor.ArmorMaterial ARMOR_SILVER = EnumHelper.addArmorMaterial("silver", Bewitchment.MODID + ":silver", 11, new int[]{2, 4, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0);
	public static final ItemArmor.ArmorMaterial ARMOR_COLD_IRON = EnumHelper.addArmorMaterial("cold_iron", Bewitchment.MODID + ":cold_iron", 18, new int[]{2, 6, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
	public static final ItemArmor.ArmorMaterial ARMOR_WITCHES = EnumHelper.addArmorMaterial("witches", Bewitchment.MODID + ":witches", 8, new int[]{1, 2, 3, 1}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);
	
	public static final Item.ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial("silver", 1, 131, 12, 1.5f, 22);
	public static final Item.ToolMaterial TOOL_COLD_IRON = EnumHelper.addToolMaterial("cold_iron", 2, 425, 7, 2.5f, 14);
	
	//No Item
	public static final ModBlockCrops crop_aconitum = new ModBlockCrops("crop_aconitum");
	public static final ModBlockCrops crop_belladonna = new BlockCropsSpreading("crop_belladonna");
	public static final ModBlockCrops crop_garlic = new ModBlockCrops("crop_garlic");
	public static final ModBlockCrops crop_hellebore = new ModBlockCrops("crop_hellebore");
	public static final ModBlockCrops crop_mandrake = new ModBlockCrops("crop_mandrake");
	public static final ModBlockCrops crop_white_sage = new ModBlockCrops("crop_white_sage");
	public static final ModBlockCrops crop_wormwood = new ModBlockCrops("crop_wormwood");
	public static final Block salt_barrier = new BlockSaltBarrier();
	public static final Block glyph = registerTileEntity(new BlockGlyph(), TileEntityGlyph.class);
	public static final Block placed_item = registerTileEntity(new BlockPlacedItem(), TileEntityPlacedItem.class);
	public static final Block witches_light = new BlockWitchesLight();
	//Material Blocks
	public static final Block blessed_stone = new ModBlock("blessed_stone", Blocks.BEDROCK);
	
	public static final Block block_of_amethyst = new ModBlock("block_of_amethyst", Blocks.DIAMOND_BLOCK, "blockAmethyst");
	public static final Block block_of_garnet = new ModBlock("block_of_garnet", Blocks.DIAMOND_BLOCK, "blockGarnet");
	public static final Block block_of_opal = new ModBlock("block_of_opal", Blocks.DIAMOND_BLOCK, "blockOpal");
	public static final Block[] block_of_silver = createChiselBlocks("block_of_silver", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, Collections.singletonList("blockSilver"), "symbol", "bevel", "sun", "moon", "cup", "pentacle", "sword", "wand", "pentagram");
	public static final Block[] block_of_cold_iron = createChiselBlocks("block_of_cold_iron", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, Collections.singletonList("blockColdIron"), "symbol", "bevel", "sun", "moon", "cup", "pentacle", "sword", "wand", "pentagram");
	public static final Block block_of_salt = new ModBlock("block_of_salt", Material.ROCK, SoundType.STONE, 5, 30, "pickaxe", 0, "blockSalt");
	public static final Block amethyst_ore = new ModBlock("amethyst_ore", Blocks.IRON_ORE, "oreAmethyst");
	public static final Block garnet_ore = new ModBlock("garnet_ore", Blocks.IRON_ORE, "oreGarnet");
	public static final Block opal_ore = new ModBlock("opal_ore", Blocks.IRON_ORE, "oreOpal");
	public static final Block silver_ore = new ModBlock("silver_ore", Blocks.IRON_ORE, "oreSilver");
	public static final Block salt_ore = new ModBlock("salt_ore", Blocks.COAL_ORE, "oreSalt");
	
	public static final Block purifying_earth = new BlockPurifyingEarth();
	public static final Block[] coquina = createChiselBlocks("coquina", Material.ROCK, SoundType.STONE, 5, 30, "pickaxe", 0, Collections.singletonList("coquina"), "smooth", "shell");
	public static final Block coquina_bricks = new ModBlock("coquina_bricks", coquina[0]);
	public static final Block chiseled_coquina = new ModBlock("chiseled_coquina", coquina[0]);
	public static final Block[] nethersteel = createChiselBlocks("nethersteel", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, Collections.singletonList("blockNethersteel"), "symbol", "bevel", "polished", "sentient", "pentacle", "pentagram", "skull", "eye", "watching_eye", "hellish", "watching_hellish");
	public static final Block perpetual_ice = new ModBlock("perpetual_ice", Blocks.ICE);
	public static final Block perpetual_ice_stairs = new ModBlockStairs("perpetual_ice_stairs", perpetual_ice);
	public static final Block perpetual_ice_slab = new ModBlockSlab("perpetual_ice_slab", perpetual_ice);
	public static final Block perpetual_ice_fence = new ModBlockFence("perpetual_ice_fence", perpetual_ice);
	public static final Block embittered_bricks = new ModBlock("embittered_bricks", Material.PACKED_ICE, SoundType.STONE, 1.5f, 30, "pickaxe", 0);
	public static final Block cracked_embittered_bricks = new ModBlock("cracked_embittered_bricks", embittered_bricks);
	public static final Block chiseled_embittered_bricks = new ModBlock("chiseled_embittered_bricks", embittered_bricks);
	public static final Block embittered_brick_stairs = new ModBlockStairs("embittered_brick_stairs", embittered_bricks);
	public static final ModBlockSlab embittered_bricks_slab = new ModBlockSlab("embittered_bricks_slab", embittered_bricks);
	public static final Block embittered_brick_fence = new ModBlockFence("embittered_brick_fence", embittered_bricks);
	public static final Block[] scorned_bricks = createChiselBlocks("scorned_bricks", Material.ROCK, SoundType.STONE, 25.2f, 1001, "pickaxe", 2, Collections.emptyList(), "symbol", "bevel", "hellish", "raw", "raw_cracked", "small", "soft", "small_tiles", "medium_tiles", "triple", "braid", "layers", "road", "ornate", "panel", "prism", "slanted");
	public static final Block cracked_scorned_bricks = new ModBlock("cracked_scorned_bricks", scorned_bricks[0]);
	public static final Block chiseled_scorned_bricks = new ModBlock("chiseled_scorned_bricks", scorned_bricks[0]);
	public static final Block scorned_brick_stairs = new ModBlockStairs("scorned_brick_stairs", scorned_bricks[0]);
	public static final ModBlockSlab scorned_bricks_slab = new ModBlockSlab("scorned_bricks_slab", scorned_bricks[0]);
	public static final Block scorned_brick_fence = new ModBlockFence("scorned_brick_fence", scorned_bricks[0]);
	//Util Blocks
	public static final Block goblet = new BlockGoblet(false);
	public static final Block filled_goblet = new BlockGoblet(true);
	//Candles
	public static final Block white_candle = new BlockCandle("white_candle");
	public static final Block orange_candle = new BlockCandle("orange_candle");
	public static final Block magenta_candle = new BlockCandle("magenta_candle");
	public static final Block light_blue_candle = new BlockCandle("light_blue_candle");
	public static final Block yellow_candle = new BlockCandle("yellow_candle");
	public static final Block lime_candle = new BlockCandle("lime_candle");
	public static final Block pink_candle = new BlockCandle("pink_candle");
	public static final Block gray_candle = new BlockCandle("gray_candle");
	public static final Block light_gray_candle = new BlockCandle("light_gray_candle");
	public static final Block cyan_candle = new BlockCandle("cyan_candle");
	public static final Block purple_candle = new BlockCandle("purple_candle");
	public static final Block blue_candle = new BlockCandle("blue_candle");
	public static final Block brown_candle = new BlockCandle("brown_candle");
	public static final Block green_candle = new BlockCandle("green_candle");
	public static final Block red_candle = new BlockCandle("red_candle");
	public static final Block black_candle = new BlockCandle("black_candle");
	//Lanterns
	public static final Block white_lantern = new BlockLantern("white_lantern");
	public static final Block orange_lantern = new BlockLantern("orange_lantern");
	public static final Block magenta_lantern = new BlockLantern("magenta_lantern");
	public static final Block light_blue_lantern = new BlockLantern("light_blue_lantern");
	public static final Block yellow_lantern = new BlockLantern("yellow_lantern");
	public static final Block lime_lantern = new BlockLantern("lime_lantern");
	public static final Block pink_lantern = new BlockLantern("pink_lantern");
	public static final Block gray_lantern = new BlockLantern("gray_lantern");
	public static final Block light_gray_lantern = new BlockLantern("light_gray_lantern");
	public static final Block cyan_lantern = new BlockLantern("cyan_lantern");
	public static final Block purple_lantern = new BlockLantern("purple_lantern");
	public static final Block blue_lantern = new BlockLantern("blue_lantern");
	public static final Block brown_lantern = new BlockLantern("brown_lantern");
	public static final Block green_lantern = new BlockLantern("green_lantern");
	public static final Block red_lantern = new BlockLantern("red_lantern");
	public static final Block black_lantern = new BlockLantern("black_lantern");
	//Plants
	public static final Block embergrass = new BlockEmbergrass();
	public static final Block torchwood = new BlockTorchwood();
	//Trees
	public static final Block cypress_sapling = new ModBlockSapling("cypress_sapling", new WorldGenCypressTree(false), "treeSapling");
	public static final Block elder_sapling = new ModBlockSapling("elder_sapling", new WorldGenElderTree(false), "treeSapling");
	public static final Block juniper_sapling = new ModBlockSapling("juniper_sapling", new WorldGenJuniperTree(false), "treeSapling");
	public static final Block yew_sapling = new ModBlockSapling("yew_sapling", new WorldGenYewTree(false), "treeSapling");
	public static final Block cypress_wood = new ModBlockPillar("cypress_wood", Blocks.LOG, "logWood");
	public static final Block elder_wood = new ModBlockPillar("elder_wood", Blocks.LOG, "logWood");
	public static final Block juniper_wood = new ModBlockPillar("juniper_wood", Blocks.LOG, "logWood");
	public static final Block yew_wood = new ModBlockPillar("yew_wood", Blocks.LOG, "logWood");
	public static final Block cypress_planks = new ModBlock("cypress_planks", Blocks.PLANKS, "plankWood");
	public static final Block elder_planks = new ModBlock("elder_planks", Blocks.PLANKS, "plankWood");
	public static final Block juniper_planks = new ModBlock("juniper_planks", Blocks.PLANKS, "plankWood");
	public static final Block yew_planks = new ModBlock("yew_planks", Blocks.PLANKS, "plankWood");
	public static final Block cypress_leaves = new ModBlockLeaves("cypress_leaves", "treeLeaves");
	public static final Block elder_leaves = new ModBlockLeaves("elder_leaves", "treeLeaves");
	public static final Block juniper_leaves = new ModBlockLeaves("juniper_leaves", "treeLeaves");
	public static final Block yew_leaves = new ModBlockLeaves("yew_leaves", "treeLeaves");
	//Decor
	public static final ModItemDoor cypress_door = new ModItemDoor("cypress_door", cypress_planks);
	public static final ModItemDoor elder_door = new ModItemDoor("elder_door", elder_planks);
	public static final ModItemDoor juniper_door = new ModItemDoor("juniper_door", juniper_planks);
	public static final ModItemDoor yew_door = new ModItemDoor("yew_door", yew_planks);
	public static final Block juniper_chest = registerTileEntity(new BlockJuniperChest(), TileEntityJuniperChest.class);
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
	//Tiles
	public static final Block stone_witches_altar = registerTileEntity(new BlockWitchesAltar("stone_witches_altar", Blocks.STONE), TileEntityWitchesAltar.class);
	public static final Block nether_brick_witches_altar = new BlockWitchesAltar("nether_brick_witches_altar", Blocks.NETHER_BRICK);
	public static final Block obsidian_witches_altar = new BlockWitchesAltar("obsidian_witches_altar", Blocks.OBSIDIAN);
	public static final Block coquina_witches_altar = new BlockWitchesAltar("coquina_witches_altar", coquina[0]);
	public static final Block embittered_brick_witches_altar = new BlockWitchesAltar("embittered_brick_witches_altar", embittered_bricks);
	public static final Block scorned_brick_witches_altar = new BlockWitchesAltar("scorned_brick_witches_altar", scorned_bricks[0]);
	
	public static final Block witches_cauldron = registerTileEntity(new BlockWitchesCauldron(), TileEntityWitchesCauldron.class);
	public static final Block witches_oven = registerTileEntity(new BlockWitchesOven(), TileEntityWitchesOven.class);
	public static final Block distillery = registerTileEntity(new BlockDistillery(), TileEntityDistillery.class);
	public static final Block spinning_wheel = registerTileEntity(new BlockSpinningWheel(), TileEntitySpinningWheel.class);
	public static final Block crystal_ball = registerTileEntity(new BlockCrystalBall(), TileEntityCrystalBall.class);
	public static final Block tarot_table = registerTileEntity(new BlockTarotTable(), TileEntityTarotTable.class);
	public static final Item tarot_cards = new ItemTarotCards();
	public static final Block frostfire = registerTileEntity(new BlockFrostfire(), TileEntityFrostfire.class);
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
	public static final Item witches_hat = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD), "witches_hat", Collections.singletonList(s -> s.getDisplayName().toLowerCase().contains("faith")));
	public static final Item witches_robes = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.CHEST), "witches_robes");
	public static final Item witches_pants = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.LEGS), "witches_pants");
	//Tools
	public static final Item silver_sword = Util.registerItem(new ItemSword(TOOL_SILVER), "silver_sword");
	public static final Item silver_pickaxe = Util.registerItem(new ModItemPickaxe(TOOL_SILVER), "silver_pickaxe");
	public static final Item silver_axe = Util.registerItem(new ModItemAxe(TOOL_SILVER), "silver_axe");
	public static final Item silver_shovel = Util.registerItem(new ItemSpade(TOOL_SILVER), "silver_shovel");
	public static final Item silver_hoe = Util.registerItem(new ItemHoe(TOOL_SILVER), "silver_hoe");
	public static final Item cold_iron_sword = Util.registerItem(new ItemSword(TOOL_COLD_IRON), "cold_iron_sword", Collections.singletonList(s -> s.getDisplayName().equalsIgnoreCase("Hudson Bat") || s.getDisplayName().equalsIgnoreCase("Masashi Bat") || s.getDisplayName().equalsIgnoreCase("Emmanuel Bat") || s.getDisplayName().equalsIgnoreCase("Michael Bat") || s.getDisplayName().equalsIgnoreCase("Yoshihiro Bat") || s.getDisplayName().equalsIgnoreCase("Lewis Bat") || s.getDisplayName().equalsIgnoreCase("Katushiro Bat") || s.getDisplayName().equalsIgnoreCase("Ashley Bat")));
	public static final Item cold_iron_pickaxe = Util.registerItem(new ModItemPickaxe(TOOL_COLD_IRON), "cold_iron_pickaxe");
	public static final Item cold_iron_axe = Util.registerItem(new ModItemAxe(TOOL_COLD_IRON), "cold_iron_axe");
	public static final Item cold_iron_shovel = Util.registerItem(new ItemSpade(TOOL_COLD_IRON), "cold_iron_shovel");
	public static final Item cold_iron_hoe = Util.registerItem(new ItemHoe(TOOL_COLD_IRON), "cold_iron_hoe");
	public static final Item athame = new ItemAthame();
	public static final Item boline = new ItemBoline();
	public static final Item broom = Util.registerItem(new ItemBroom(null), "broom");
	public static final Item cypress_broom = Util.registerItem(new ItemBroom(ModEntities.cypress_broom), "cypress_broom");
	public static final Item elder_broom = Util.registerItem(new ItemBroom(ModEntities.elder_broom), "elder_broom");
	public static final Item juniper_broom = Util.registerItem(new ItemBroom(ModEntities.juniper_broom), "juniper_broom");
	public static final Item yew_broom = Util.registerItem(new ItemBroom(ModEntities.yew_broom), "yew_broom");
	public static final Item waystone = new ItemWaystone();
	public static final Item focal_chalk = new ItemChalk("focal_chalk");
	public static final Item ritual_chalk = new ItemChalk("ritual_chalk");
	public static final Item fiery_chalk = new ItemChalk("fiery_chalk");
	public static final Item phasing_chalk = new ItemChalk("phasing_chalk");
	//Baubles
	public static final ModItemBauble girdle_of_the_dryads = new ItemGirdleOfTheDryads();
	public static final ModItemBauble hellish_bauble = new ItemHellishBauble();
	public static final Item horseshoe = new ItemHorseshoe();
	public static final Item nazar = new ItemNazar();
	public static final Item token_of_remedies = new ItemTokenOfRemedies();
	//Misc Tools
	public static final Item grimoire_magia = new ItemGrimoireMagia();
	public static final Item juniper_key = new ItemJuniperKey();
	//Util Items
	public static final Item bottled_frostfire = new ItemBottledFrostfire();
	public static final Item bone_needle = Util.registerItem("bone_needle");
	public static final Item taglock = new ItemTaglock();
	//Materials
	public static final Item amethyst = Util.registerItem("amethyst", "gemAmethyst", "gemAll");
	public static final Item garnet = Util.registerItem("garnet", "gemGarnet", "gemAll");
	public static final Item opal = Util.registerItem("opal", "gemOpal", "gemAll");
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
	public static final Item fiery_unguent = Util.registerItem("fiery_unguent");
	//Loom
	public static final Item diabolical_vein = Util.registerItem("diabolical_vein");
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
	public static final Item elderberries = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100), 0.1f), "elderberries");
	public static final Item juniper_berries = Util.registerItem(new ItemFood(1, 0.5f, false), "juniper_berries");
	public static final Item yew_aril = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 100), 0.5f), "yew_aril");
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
	//Misc
	public static final Item catechu_brown = Util.registerItem("catechu_brown", "dye", "dyeBrown");
	public static final Item dimensional_sand = Util.registerItem("dimensional_sand");
	public static final Item ectoplasm = Util.registerItem("ectoplasm");
	public static final Item flying_ointment = Util.registerItem("flying_ointment");
	public static final Item iron_gall_ink = Util.registerItem("iron_gall_ink", "dye", "dyeBlack");
	public static final Item oak_apple_gall = Util.registerItem("oak_apple_gall");
	public static final Item pentacle = Util.registerItem("pentacle");
	public static final Item spectral_dust = Util.registerItem("spectral_dust");
	public static final Item tallow = Util.registerItem("tallow", "materialWax", "materialBeeswax", "materialPressedWax", "itemBeeswax", "wax", "tallow", "clumpWax", "beeswax", "itemWax");
	public static final Item wood_ash = Util.registerItem("wood_ash");
	
	public static void preInit() {
		if (Loader.isModLoaded("chisel")) {
			CarvingUtils.getChiselRegistry().addVariation("coquina", CarvingUtils.variationFor(ModObjects.coquina_bricks.getDefaultState(), 1));
			CarvingUtils.getChiselRegistry().addVariation("coquina", CarvingUtils.variationFor(ModObjects.chiseled_coquina.getDefaultState(), 1));
			CarvingUtils.getChiselRegistry().addVariation("scorned_bricks", CarvingUtils.variationFor(ModObjects.cracked_scorned_bricks.getDefaultState(), 1));
			CarvingUtils.getChiselRegistry().addVariation("scorned_bricks", CarvingUtils.variationFor(ModObjects.chiseled_scorned_bricks.getDefaultState(), 1));
		}
	}
	
	private static Block[] createChiselBlocks(String name, Material mat, SoundType sound, float hardness, float resistance, String tool, int level, List<String> oreDictionaryNames, String... names) {
		List<Block> list = new ArrayList<>();
		Block base = new ModBlock(name, mat, sound, hardness, resistance, tool, level, oreDictionaryNames.toArray(new String[0]));
		list.add(base);
		if (Loader.isModLoaded("chisel")) {
			if (!name.contains("silver")) CarvingUtils.getChiselRegistry().addVariation(name, CarvingUtils.variationFor(base.getDefaultState(), 0));
			for (String variant : names) {
				Block block = new ModBlockChisel(variant, base, oreDictionaryNames.toArray(new String[0]));
				if (!name.contains("silver")) CarvingUtils.getChiselRegistry().addVariation(name, CarvingUtils.variationFor(block.getDefaultState(), list.size() + 1));
				if (block != base) list.add(block);
			}
		}
		return list.toArray(new Block[0]);
	}
	
	private static Block registerTileEntity(Block block, Class<? extends TileEntity> tile) {
		GameRegistry.registerTileEntity(tile, block.getRegistryName());
		return block;
	}
}