package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.api.registry.item.ItemBroom;
import com.bewitchment.api.registry.item.ItemFume;
import com.bewitchment.client.misc.Statues;
import com.bewitchment.common.block.*;
import com.bewitchment.common.block.plants.*;
import com.bewitchment.common.block.tile.entity.*;
import com.bewitchment.common.block.util.*;
import com.bewitchment.common.entity.misc.EntitySilverArrow;
import com.bewitchment.common.entity.spirit.ghost.EntityGhost;
import com.bewitchment.common.integration.chisel.ModBlockChisel;
import com.bewitchment.common.item.*;
import com.bewitchment.common.item.equipment.armor.ItemWitchesArmor;
import com.bewitchment.common.item.equipment.baubles.*;
import com.bewitchment.common.item.food.*;
import com.bewitchment.common.item.poppet.ItemPoppet;
import com.bewitchment.common.item.poppet.ItemVoodooPoppet;
import com.bewitchment.common.item.tool.*;
import com.bewitchment.common.item.util.*;
import com.bewitchment.common.world.gen.tree.WorldGenCypressTree;
import com.bewitchment.common.world.gen.tree.WorldGenDragonTree;
import com.bewitchment.common.world.gen.tree.WorldGenElderTree;
import com.bewitchment.common.world.gen.tree.WorldGenJuniperTree;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import team.chisel.api.carving.CarvingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions", "SameParameterValue"})
public class ModObjects {
	public static final ItemArmor.ArmorMaterial ARMOR_SILVER = EnumHelper.addArmorMaterial("silver", Bewitchment.MODID + ":silver", 11, new int[]{2, 4, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0);
	public static final ItemArmor.ArmorMaterial ARMOR_COLD_IRON = EnumHelper.addArmorMaterial("cold_iron", Bewitchment.MODID + ":cold_iron", 16, new int[]{2, 5, 6, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);
	public static final ItemArmor.ArmorMaterial ARMOR_WITCHES = EnumHelper.addArmorMaterial("witches", Bewitchment.MODID + ":witches", 8, new int[]{1, 2, 3, 1}, 20, SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, 0);

	public static final Item.ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial("silver", 1, 131, 12, 1.5f, 22);
	public static final Item.ToolMaterial TOOL_COLD_IRON = EnumHelper.addToolMaterial("cold_iron", 2, 260, 6, 2, 14);
	public static final Item.ToolMaterial TOOL_HELLISH = EnumHelper.addToolMaterial("hellish", 2, 616, 16, 3.0f, 26);
	public static final Item.ToolMaterial TOOL_THYRSUS = EnumHelper.addToolMaterial("mat_thyrsus", 1, 72, 12, 3.0f, 20);

	//No Item
	public static final ModBlockCrops crop_aconitum = new ModBlockCrops("crop_aconitum");
	public static final ModBlockCrops crop_belladonna = new ModBlockCrops("crop_belladonna");
	public static final ModBlockCrops crop_garlic = new ModBlockCrops("crop_garlic");
	public static final ModBlockCrops crop_hellebore = new ModBlockCrops("crop_hellebore");
	public static final ModBlockCrops crop_mandrake = new ModBlockCrops("crop_mandrake");
	public static final ModBlockCrops crop_white_sage = new ModBlockCrops("crop_white_sage");
	public static final ModBlockCrops crop_wormwood = new ModBlockCrops("crop_wormwood");
	public static final Block salt_barrier = new BlockSaltBarrier();
	public static final Block glyph = registerTileEntity(new BlockGlyph(), TileEntityGlyph.class);
	public static final Block placed_item = registerTileEntity(new BlockPlacedItem(), TileEntityPlacedItem.class);
	public static final Block sigil = registerTileEntity(new BlockSigil(), TileEntitySigil.class);
	//Material Blocks
	public static final Block blessed_stone = new ModBlock("blessed_stone", Blocks.BEDROCK);

	public static final Block block_of_amethyst = new ModBlock("block_of_amethyst", Blocks.DIAMOND_BLOCK, "blockAmethyst");
	public static final Block block_of_garnet = new ModBlock("block_of_garnet", Blocks.DIAMOND_BLOCK, "blockGarnet");
	public static final Block block_of_opal = new ModBlock("block_of_opal", Blocks.DIAMOND_BLOCK, "blockOpal");
	public static final Block[] block_of_silver = createChiselBlocks("block_of_silver", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, Collections.singletonList("blockSilver"), "symbol", "bevel", "sun", "moon", "cup", "pentacle", "sword", "wand", "pentagram", "old");
	public static final Block[] block_of_cold_iron = createChiselBlocks("block_of_cold_iron", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, Collections.singletonList("blockColdIron"), "symbol", "bevel", "sun", "moon", "cup", "pentacle", "sword", "wand", "pentagram");
	public static final Block block_of_salt = new ModBlock("block_of_salt", Material.SAND, SoundType.SAND, 1, 15, "shovel", 0, "blockSalt");
	public static final Block amethyst_ore = new ModBlockOre("amethyst_ore", Blocks.REDSTONE_ORE, "oreAmethyst");
	public static final Block garnet_ore = new ModBlockOre("garnet_ore", Blocks.REDSTONE_ORE, "oreGarnet");
	public static final Block opal_ore = new ModBlockOre("opal_ore", Blocks.REDSTONE_ORE, "oreOpal");
	public static final Block silver_ore = new ModBlock("silver_ore", Blocks.IRON_ORE, "oreSilver");
	public static final Block salt_ore = new ModBlockOre("salt_ore", Blocks.COAL_ORE, "oreSalt");

	public static final Block purifying_earth = new BlockPurifyingEarth();
	public static final Block[] coquina = createChiselBlocks("coquina", Material.ROCK, SoundType.STONE, 5, 30, "pickaxe", 0, Collections.singletonList("coquina"), "smooth", "shell");
	public static final Block coquina_bricks = new ModBlock("coquina_bricks", coquina[0]);
	public static final Block chiseled_coquina = new ModBlock("chiseled_coquina", coquina[0]);
	public static final Block[] nethersteel = createChiselBlocks("nethersteel", Material.IRON, SoundType.METAL, 5, 30, "pickaxe", 2, Collections.singletonList("blockNethersteel"), "symbol", "bevel", "polished", "sentient", "pentacle", "pentagram", "skull", "eye", "watching_eye", "hellish", "watching_hellish");
	public static final Block perpetual_ice = new BlockTransparent("perpetual_ice", Blocks.ICE);
	public static final Block perpetual_ice_stairs = new ModBlockStairs("perpetual_ice_stairs", perpetual_ice);
	public static final Block perpetual_ice_slab = new BlockSlabTransparent("perpetual_ice_slab", perpetual_ice);
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
	public static final Block mossy_scorned_bricks = new ModBlock("mossy_scorned_bricks", Blocks.STONE);
	public static final Block crying_scorned_bricks = new ModBlock("crying_scorned_bricks", Blocks.STONE);
	public static final Block despair_bricks = new ModBlock("despair_bricks", Blocks.STONE);
	public static final Block ochre_despair_bricks = new ModBlock("ochre_despair_bricks", Blocks.STONE);

	public static final Block nethersteel_fence = new ModBlockFence("nethersteel_fence", nethersteel[0]);
	public static final Block coquina_fence = new ModBlockFence("coquina_fence", coquina[0]);
	public static final Block mossy_scorned_brick_fence = new ModBlockFence("mossy_scorned_brick_fence", mossy_scorned_bricks);
	public static final Block crying_scorned_brick_fence = new ModBlockFence("crying_scorned_brick_fence", crying_scorned_bricks);
	public static final Block despair_brick_fence = new ModBlockFence("despair_brick_fence", despair_bricks);
	public static final Block ochre_despair_brick_fence = new ModBlockFence("ochre_despair_brick_fence", ochre_despair_bricks);

	public static final Block alchemists_carpet = new ModCarpet("alchemists_carpet", Blocks.WOOL);
	public static final Block hedgewitches_carpet = new ModCarpet("hedgewitches_carpet", Blocks.WOOL);
	public static final Block besmirched_carpet = new ModCarpet("besmirched_carpet", Blocks.WOOL);

	public static final Block mossy_scorned_brick_stairs = new ModBlockStairs("mossy_scorned_brick_stairs", mossy_scorned_bricks);
	public static final Block crying_scorned_brick_stairs = new ModBlockStairs("crying_scorned_brick_stairs", crying_scorned_bricks);
	public static final Block despair_brick_stairs = new ModBlockStairs("despair_brick_stairs", despair_bricks);
	public static final Block ochre_despair_brick_stairs = new ModBlockStairs("ochre_despair_brick_stairs", ochre_despair_bricks);
	public static final Block nethersteel_stairs = new ModBlockStairs("nethersteel_stairs", nethersteel[0]);
	public static final Block coquina_stairs = new ModBlockStairs("coquina_stairs", coquina[0]);

	public static final ModBlockSlab coquina_slab = new ModBlockSlab("coquina_slab", coquina[0]);
	public static final ModBlockSlab nethersteel_slab = new ModBlockSlab("nethersteel_slab", nethersteel[0]);
	public static final ModBlockSlab mossy_scorned_brick_slab = new ModBlockSlab("mossy_scorned_brick_slab", mossy_scorned_bricks);
	public static final ModBlockSlab crying_scorned_brick_slab = new ModBlockSlab("crying_scorned_brick_slab", crying_scorned_bricks);
	public static final ModBlockSlab despair_brick_slab = new ModBlockSlab("despair_brick_slab", despair_bricks);
	public static final ModBlockSlab ochre_despair_brick_slab = new ModBlockSlab("ochre_despair_brick_slab", ochre_despair_bricks);

	public static final Block coquina_wall = new ModBlockWall("coquina_wall", coquina[0]);
	public static final Block scorned_brick_wall = new ModBlockWall("scorned_brick_wall", scorned_bricks[0]);
	public static final Block despair_brick_wall = new ModBlockWall("despair_brick_wall", despair_bricks);
	public static final Block ochre_despair_brick_wall = new ModBlockWall("ochre_despair_brick_wall", ochre_despair_bricks);
	public static final Block embittered_brick_wall = new ModBlockWall("embittered_brick_wall", embittered_bricks);
	public static final Block mossy_scorned_brick_wall = new ModBlockWall("mossy_scorned_brick_wall", mossy_scorned_bricks);
	public static final Block crying_scorned_brick_wall = new ModBlockWall("crying_scorned_brick_wall", crying_scorned_bricks);
	public static final Block nethersteel_wall = new ModBlockWall("nethersteel_wall", nethersteel[0]);

	//Walls

	//Decorative wools and carpets
	public static final Block greenwitch_fleece = new ModBlock("greenwitch_fleece", Blocks.WOOL);
	public static final Block besmirched_fleece = new ModBlock("besmirched_fleece", Blocks.WOOL);
	public static final Block alchemists_fleece = new ModBlock("alchemists_fleece", Blocks.WOOL);

	public static final Block dragons_blood_resin_block = new ModBlock("dragons_blood_resin_block", Blocks.SLIME_BLOCK, "blockDragonsBloodResin");
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
	//Plants
	public static final Block embergrass = new BlockEmbergrass();
	public static final Block torchwood = new BlockTorchwood();
	public static final Block spanish_moss = new BlockSpanishMoss(false);
	public static final Block spanish_moss_end = new BlockSpanishMoss(true);
	public static final Block blue_ink_cap = new BlockBlueInkCap();
	public static final Block frostflower = new BlockFrostFlower();

	public static final Block flower_siphoning_allium = new BlockSiphoningFlower("allium");
	public static final Block flower_siphoning_blue_orchid = new BlockSiphoningFlower("blue_orchid");
	public static final Block flower_siphoning_dandelion = new BlockSiphoningFlower("dandelion");
	public static final Block flower_siphoning_azure_bluet = new BlockSiphoningFlower("azure_bluet");
	public static final Block flower_siphoning_oxeye_daisy = new BlockSiphoningFlower("oxeye_daisy");
	public static final Block flower_siphoning_tulip_orange = new BlockSiphoningFlower("tulip_orange");
	public static final Block flower_siphoning_tulip_pink = new BlockSiphoningFlower("tulip_pink");
	public static final Block flower_siphoning_tulip_red = new BlockSiphoningFlower("tulip_red");
	public static final Block flower_siphoning_tulip_white = new BlockSiphoningFlower("tulip_white");
	public static final Block flower_siphoning_poppy = new BlockSiphoningFlower("poppy");

	public static final Block candelabra_gold = new BlockCandelabra("gold");
	public static final Block candelabra_iron = new BlockCandelabra("iron");
	public static final Block candelabra_silver = new BlockCandelabra("silver");

	//Creative Stuff
	public static final Block goddess_statue = new BlockGoddessStatue(Statues.stone_goddess_statue);

	//Trees
	public static final Block cypress_sapling = new ModBlockSapling("cypress_sapling", new WorldGenCypressTree(false), "treeSapling");
	public static final Block elder_sapling = new ModBlockSapling("elder_sapling", new WorldGenElderTree(false), "treeSapling");
	public static final Block juniper_sapling = new ModBlockSapling("juniper_sapling", new WorldGenJuniperTree(false), "treeSapling");
	public static final Block dragons_blood_sapling = new ModBlockSapling("dragons_blood_sapling", new WorldGenDragonTree(false), "treeSapling");
	public static final Block cypress_wood = new ModBlockPillar("cypress_wood", Blocks.LOG, "logWood");
	public static final Block elder_wood = new ModBlockPillar("elder_wood", Blocks.LOG, "logWood");
	public static final Block juniper_wood = new ModBlockPillar("juniper_wood", Blocks.LOG, "logWood");
	public static final Block dragons_blood_wood = new BlockDragonsBloodLog();
	public static final Block cypress_planks = new ModBlock("cypress_planks", Blocks.PLANKS, "plankWood");
	public static final Block elder_planks = new ModBlock("elder_planks", Blocks.PLANKS, "plankWood");
	public static final Block juniper_planks = new ModBlock("juniper_planks", Blocks.PLANKS, "plankWood");
	public static final Block dragons_blood_planks = new ModBlock("dragons_blood_planks", Blocks.PLANKS, "plankWood");
	public static final Block cypress_leaves = new ModBlockLeaves("cypress_leaves", "treeLeaves");
	public static final Block elder_leaves = new ModBlockLeaves("elder_leaves", "treeLeaves");
	public static final Block juniper_leaves = new ModBlockLeaves("juniper_leaves", "treeLeaves");
	public static final Block dragons_blood_leaves = new ModBlockLeaves("dragons_blood_leaves", "treeLeaves");
	//Decor
	public static final ModItemDoor cypress_door = new ModItemDoor("cypress_door", cypress_planks);
	public static final ModItemDoor elder_door = new ModItemDoor("elder_door", elder_planks);
	public static final ModItemDoor juniper_door = new ModItemDoor("juniper_door", juniper_planks);
	public static final ModItemDoor dragons_blood_door = new ModItemDoor("dragons_blood_door", dragons_blood_planks);
	public static final Block juniper_chest = registerTileEntity(new BlockJuniperChest(), TileEntityJuniperChest.class);
	public static final Block dragons_blood_chest = registerTileEntity(new BlockDBChest(), TileEntityDBChest.class);
	public static final Block cypress_wood_stairs = new ModBlockStairs("cypress_wood_stairs", cypress_planks, "stairWood");
	public static final Block elder_wood_stairs = new ModBlockStairs("elder_wood_stairs", elder_planks, "stairWood");
	public static final Block juniper_wood_stairs = new ModBlockStairs("juniper_wood_stairs", juniper_planks, "stairWood");
	public static final Block dragons_blood_stairs = new ModBlockStairs("dragons_blood_stairs", dragons_blood_planks, "stairWood");
	public static final Block cypress_wood_slab = new ModBlockSlab("cypress_wood_slab", cypress_planks, "slabWood");
	public static final Block elder_wood_slab = new ModBlockSlab("elder_wood_slab", elder_planks, "slabWood");
	public static final Block juniper_wood_slab = new ModBlockSlab("juniper_wood_slab", juniper_planks, "slabWood");
	public static final Block dragons_blood_slab = new ModBlockSlab("dragons_blood_slab", dragons_blood_planks, "slabWood");
	public static final Block cypress_trapdoor = new ModBlockTrapdoor("cypress_trapdoor", cypress_planks);
	public static final Block elder_trapdoor = new ModBlockTrapdoor("elder_trapdoor", elder_planks);
	public static final Block juniper_trapdoor = new ModBlockTrapdoor("juniper_trapdoor", juniper_planks);
	public static final Block dragons_blood_trapdoor = new BlockDBTrapdoor();
	public static final Block cypress_fence_gate = new ModBlockFenceGate("cypress_fence_gate", cypress_planks, "fenceGateWood");
	public static final Block elder_fence_gate = new ModBlockFenceGate("elder_fence_gate", elder_planks, "fenceGateWood");
	public static final Block juniper_fence_gate = new ModBlockFenceGate("juniper_fence_gate", juniper_planks, "fenceGateWood");
	public static final Block dragons_blood_fence_gate = new ModBlockFenceGate("dragons_blood_fence_gate", dragons_blood_planks, "fenceGateWood");
	public static final Block cypress_fence = new ModBlockFence("cypress_fence", cypress_planks, "fenceWood");
	public static final Block elder_fence = new ModBlockFence("elder_fence", elder_planks, "fenceWood");
	public static final Block juniper_fence = new ModBlockFence("juniper_fence", juniper_planks, "fenceWood");
	public static final Block dragons_blood_fence = new ModBlockFence("dragons_blood_fence", dragons_blood_planks, "fenceWood");
	public static final Block cypress_pressure_plate = new ModBlockPressurePlate("cypress_pressure_plate", cypress_planks);
	public static final Block elder_pressure_plate = new ModBlockPressurePlate("elder_pressure_plate", elder_planks);
	public static final Block juniper_pressure_plate = new ModBlockPressurePlate("juniper_pressure_plate", juniper_planks);
	public static final Block dragons_blood_plate = new ModBlockPressurePlate("dragons_blood_pressure_plate", dragons_blood_planks);
	public static final Block cypress_button = new ModBlockButton("cypress_button", cypress_planks);
	public static final Block elder_button = new ModBlockButton("elder_button", elder_planks);
	public static final Block juniper_button = new ModBlockButton("juniper_button", juniper_planks);
	public static final Block dragons_blood_button = new ModBlockButton("dragons_blood_button", dragons_blood_planks);
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
	public static final Block frostfire = registerTileEntity(new BlockFrostfire(), TileEntityFrostfire.class);
	public static final Block hellfire = new BlockHellfire();
	public static final Block brazier = registerTileEntity(new BlockBrazier(), TileEntityBrazier.class);
	public static final Block sigil_table = registerTileEntity(new BlockSigilTable(), TileEntitySigilTable.class);

	//Poppet Shelves
	public static final Block oak_poppet_shelf = new BlockPoppetShelf("oak");
	public static final Block spruce_poppet_shelf = new BlockPoppetShelf("spruce");
	public static final Block dark_oak_poppet_shelf = new BlockPoppetShelf("dark_oak");
	public static final Block jungle_poppet_shelf = new BlockPoppetShelf("jungle");
	public static final Block birch_poppet_shelf = new BlockPoppetShelf("birch");
	public static final Block acacia_poppet_shelf = new BlockPoppetShelf("acacia");
	public static final Block juniper_poppet_shelf = new BlockPoppetShelf("juniper");
	public static final Block cypress_poppet_shelf = new BlockPoppetShelf("cypress");
	public static final Block elder_poppet_shelf = new BlockPoppetShelf("elder");
	public static final Block dragons_blood_poppet_shelf = new BlockPoppetShelf("dragons_blood");

	//Armor
	public static final Item silver_helmet = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.HEAD), "silver_helmet", "helmetSilver");
	public static final Item silver_chestplate = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.CHEST), "silver_chestplate", "chestplateSilver");
	public static final Item silver_leggings = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.LEGS), "silver_leggings", "leggingsSilver");
	public static final Item silver_boots = Util.registerItem(new ItemArmor(ARMOR_SILVER, 0, EntityEquipmentSlot.FEET), "silver_boots", "bootsSilver");
	public static final Item cold_iron_helmet = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.HEAD), "cold_iron_helmet");
	public static final Item cold_iron_chestplate = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.CHEST), "cold_iron_chestplate");
	public static final Item cold_iron_leggings = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.LEGS), "cold_iron_leggings");
	public static final Item cold_iron_boots = Util.registerItem(new ItemArmor(ARMOR_COLD_IRON, 0, EntityEquipmentSlot.FEET), "cold_iron_boots");
	public static final Item witches_cowl = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/witches"), "witches_cowl");
	public static final Item witches_hat = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/witches"), "witches_hat", Collections.singletonList(s -> s.getDisplayName().toLowerCase().contains("faith")));
	public static final Item witches_robes = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.CHEST, Bewitchment.MODID + ":textures/models/armor/witches"), "witches_robes");
	public static final Item witches_pants = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.LEGS, Bewitchment.MODID + ":textures/models/armor/witches"), "witches_pants");
	public static final Item besmirched_cowl = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/besmirched_cloak"), "besmirched_hood");
	public static final Item besmirched_hat = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/besmirched_cloak"), "besmirched_hat");
	public static final Item besmirched_robes = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.CHEST, Bewitchment.MODID + ":textures/models/armor/besmirched_cloak"), "besmirched_robes");
	public static final Item besmirched_pants = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.LEGS, Bewitchment.MODID + ":textures/models/armor/besmirched_cloak"), "besmirched_pants");
	public static final Item alchemist_cowl = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/alchemists_attire"), "alchemist_hood");
	public static final Item alchemist_hat = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/alchemists_attire"), "alchemist_hat");
	public static final Item alchemist_robes = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.CHEST, Bewitchment.MODID + ":textures/models/armor/alchemists_attire"), "alchemist_robes");
	public static final Item alchemist_pants = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.LEGS, Bewitchment.MODID + ":textures/models/armor/alchemists_attire"), "alchemist_pants");
	public static final Item green_witch_cowl = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/green_witch_robes"), "green_witch_hood");
	public static final Item green_witch_hat = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.HEAD, Bewitchment.MODID + ":textures/models/armor/green_witch_robes"), "green_witch_hat");
	public static final Item green_witch_robes = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.CHEST, Bewitchment.MODID + ":textures/models/armor/green_witch_robes"), "green_witch_robes");
	public static final Item green_witch_pants = Util.registerItem(new ItemWitchesArmor(EntityEquipmentSlot.LEGS, Bewitchment.MODID + ":textures/models/armor/green_witch_robes"), "green_witch_pants");
	//Tools
	public static final Item silver_sword = Util.registerItem(new ItemSword(TOOL_SILVER), "silver_sword", "swordSilver");
	public static final Item silver_pickaxe = Util.registerItem(new ModItemPickaxe(TOOL_SILVER), "silver_pickaxe", "pickaxeSilver");
	public static final Item silver_axe = Util.registerItem(new ModItemAxe(TOOL_SILVER), "silver_axe", "axeSilver");
	public static final Item silver_shovel = Util.registerItem(new ItemSpade(TOOL_SILVER), "silver_shovel", "shovelSilver");
	public static final Item silver_hoe = Util.registerItem(new ItemHoe(TOOL_SILVER), "silver_hoe", "hoeSilver");
	public static final Item cold_iron_sword = Util.registerItem(new ItemSword(TOOL_COLD_IRON), "cold_iron_sword", Collections.singletonList(s -> s.getDisplayName().equalsIgnoreCase("Hudson Bat") || s.getDisplayName().equalsIgnoreCase("Masashi Bat") || s.getDisplayName().equalsIgnoreCase("Emmanuel Bat") || s.getDisplayName().equalsIgnoreCase("Michael Bat") || s.getDisplayName().equalsIgnoreCase("Yoshihiro Bat") || s.getDisplayName().equalsIgnoreCase("Lewis Bat") || s.getDisplayName().equalsIgnoreCase("Katushiro Bat") || s.getDisplayName().equalsIgnoreCase("Ashley Bat")));
	public static final Item cold_iron_pickaxe = Util.registerItem(new ModItemPickaxe(TOOL_COLD_IRON), "cold_iron_pickaxe");
	public static final Item cold_iron_axe = Util.registerItem(new ModItemAxe(TOOL_COLD_IRON), "cold_iron_axe");
	public static final Item cold_iron_shovel = Util.registerItem(new ItemSpade(TOOL_COLD_IRON), "cold_iron_shovel");
	public static final Item cold_iron_hoe = Util.registerItem(new ItemHoe(TOOL_COLD_IRON), "cold_iron_hoe");
	public static final Item athame = new ItemAthame();
	public static final Item boline = new ItemBoline();
	public static final Item cleaver_sword = new ItemCleaver();
	public static final Item broom = Util.registerItem(new ItemBroom(null), "broom");
	public static final Item cypress_broom = Util.registerItem(new ItemBroom(ModEntities.cypress_broom), "cypress_broom");
	public static final Item elder_broom = Util.registerItem(new ItemBroom(ModEntities.elder_broom), "elder_broom");
	public static final Item juniper_broom = Util.registerItem(new ItemBroom(ModEntities.juniper_broom), "juniper_broom");
	public static final Item dragons_blood_broom = Util.registerItem(new ItemBroom(ModEntities.dragons_blood_broom), "dragons_blood_broom");
	public static final Item waystone = new ItemWaystone();
	public static final Item focal_chalk = new ItemChalk("focal_chalk");
	public static final Item ritual_chalk = new ItemChalk("ritual_chalk");
	public static final Item fiery_chalk = new ItemChalk("fiery_chalk");
	public static final Item phasing_chalk = new ItemChalk("phasing_chalk");
	public static final Item caduceus = new ItemCaduceus();
	public static final Item leonards_wand = new ItemLeonardsWand();
	public static final Item thyrsus = new ItemThyrsus();

	public static final Item silver_arrow = Util.registerItem(new ItemArrow() {
		@Override
		public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
			return new EntitySilverArrow(worldIn, shooter, false);
		}
	}, "silver_arrow");

	public static final Item cold_iron_arrow = Util.registerItem(new ItemArrow() {
		@Override
		public EntityArrow createArrow(World worldIn, ItemStack stack, EntityLivingBase shooter) {
			return new EntitySilverArrow(worldIn, shooter, true);
		}
	}, "cold_iron_arrow");

	//Baubles
	public static final ModItemBauble girdle_of_the_dryads = new ItemGirdleOfTheDryads();
	public static final ModItemBauble hellish_bauble = new ItemHellishBauble();
	public static final Item fortunes_favor = new ItemFortuneFavor();
	public static final Item horseshoe = new ItemHorseshoe();
	public static final Item nazar = new ItemNazar();
	public static final Item token_of_remedies = new ItemTokenOfRemedies();
	public static final ModItemBauble hecates_visage = new ItemHecatesVisage();
	public static final Item triskelion = new ItemTriskelion();
	public static final Item vampiric_amulet = new ItemVampiricAmulet();
	public static final Item gluttons_sash = new ItemGluttonsSash();
	//Misc Tools
	public static final Item grimoire_magia = new ItemGrimoireMagia();
	public static final Item bastards_grimoire = new ItemBastardsGrimoire();
	public static final Item juniper_key = Util.registerItem(new ItemJuniperKey(), "juniper_key");
	public static final Item juniper_key_ring = Util.registerItem(new ItemJuniperKeyRing(), "juniper_key_ring");

	//Lenny Statue
	public static final Block stone_leonard_statue = new BlockStatue(Statues.stone_leonard_statue);
	public static final Block nether_brick_leonard_statue = new BlockStatue(Statues.nether_brick_leonard_statue);
	public static final Block scorned_brick_leonard_statue = new BlockStatue(Statues.scorned_brick_leonard_statue);
	public static final Block gold_leonard_statue = new BlockStatue(Statues.gold_leonard_statue);
	//Lilith Statue
	public static final Block stone_lilith_statue = new BlockStatue(Statues.stone_lilith_statue);
	public static final Block nether_brick_lilith_statue = new BlockStatue(Statues.nether_brick_lilith_statue);
	public static final Block scorned_brick_lilith_statue = new BlockStatue(Statues.scorned_brick_lilith_statue);
	public static final Block gold_lilith_statue = new BlockStatue(Statues.gold_lilith_statue);
	//Baphomet Statue
	public static final Block stone_baphomet_statue = new BlockStatue(Statues.stone_baphomet_statue);
	public static final Block nether_brick_baphomet_statue = new BlockStatue(Statues.nether_brick_baphomet_statue);
	public static final Block scorned_brick_baphomet_statue = new BlockStatue(Statues.scorned_brick_baphomet_statue);
	public static final Block gold_baphomet_statue = new BlockStatue(Statues.gold_baphomet_statue);
	//Herne Statue
	public static final Block stone_herne_statue = new BlockStatue(Statues.stone_herne_statue);
	public static final Block nether_brick_herne_statue = new BlockStatue(Statues.nether_brick_herne_statue);
	public static final Block scorned_brick_herne_statue = new BlockStatue(Statues.scorned_brick_herne_statue);
	public static final Block gold_herne_statue = new BlockStatue(Statues.gold_herne_statue);
	//Moloch Statue
	public static final Block stone_moloch_statue = new BlockStatue(Statues.stone_moloch_statue);
	public static final Block nether_brick_moloch_statue = new BlockStatue(Statues.nether_brick_moloch_statue);
	public static final Block scorned_brick_moloch_statue = new BlockStatue(Statues.scorned_brick_moloch_statue);
	public static final Block gold_moloch_statue = new BlockStatue(Statues.gold_moloch_statue);
	//Leonard Idols
	public static final Block stone_leonard_idol = new BlockIdol(Statues.stone_leonard_idol);
	public static final Block nether_brick_leonard_idol = new BlockIdol(Statues.nether_brick_leonard_idol);
	public static final Block scorned_brick_leonard_idol = new BlockIdol(Statues.scorned_brick_leonard_idol);
	public static final Block gold_leonard_idol = new BlockIdol(Statues.gold_leonard_idol);
	//Herne Idols
	public static final Block stone_herne_idol = new BlockIdol(Statues.stone_herne_idol);
	public static final Block nether_brick_herne_idol = new BlockIdol(Statues.nether_brick_herne_idol);
	public static final Block scorned_brick_herne_idol = new BlockIdol(Statues.scorned_brick_herne_idol);
	public static final Block gold_herne_idol = new BlockIdol(Statues.gold_herne_idol);
	//Baphomet Idols
	public static final Block stone_baphomet_idol = new BlockIdol(Statues.stone_baphomet_idol);
	public static final Block nether_brick_baphomet_idol = new BlockIdol(Statues.nether_brick_baphomet_idol);
	public static final Block scorned_brick_baphomet_idol = new BlockIdol(Statues.scorned_brick_baphomet_idol);
	public static final Block gold_baphomet_idol = new BlockIdol(Statues.gold_baphomet_idol);
	//Lilith Idols
	public static final Block stone_lilith_idol = new BlockIdol(Statues.stone_lilith_idol);
	public static final Block nether_brick_lilith_idol = new BlockIdol(Statues.nether_brick_lilith_idol);
	public static final Block scorned_brick_lilith_idol = new BlockIdol(Statues.scorned_brick_lilith_idol);
	public static final Block gold_lilith_idol = new BlockIdol(Statues.gold_lilith_idol);
	//Moloch Idols
	public static final Block stone_moloch_idol = new BlockIdol(Statues.stone_moloch_idol);
	public static final Block nether_brick_moloch_idol = new BlockIdol(Statues.nether_brick_moloch_idol);
	public static final Block scorned_brick_moloch_idol = new BlockIdol(Statues.scorned_brick_moloch_idol);
	public static final Block gold_moloch_idol = new BlockIdol(Statues.gold_moloch_idol);

	public static final Block filler = new BlockStatue.BlockFiller();

	//Util Items
	public static final Item bottled_frostfire = new ItemBottledFrostfire();
	public static final Item bottled_hellfire = new ItemBottledHellfire();
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

	//Compat Materials
	public static final Item cold_iron_plate = Util.registerItem("cold_iron_plate", "plateColdIron");
	public static final Item silver_plate = Util.registerItem("silver_plate", "plateSilver");

	//Technical content
	public static final Item hidden_bat = new ItemHiddenBat();

	//Oven
	public static final Item unfired_jar = Util.registerItem("unfired_jar");
	public static final Item empty_jar = Util.registerItem("empty_jar");
	public static final Item oak_spirit = Util.registerItem(new ItemFume(), "oak_spirit");
	public static final Item spruce_heart = Util.registerItem(new ItemFume(), "spruce_heart");
	public static final Item birch_soul = Util.registerItem(new ItemFume(), "birch_soul");
	public static final Item cloudy_oil = Util.registerItem(new ItemFume(), "cloudy_oil");
	public static final Item acacia_resin = Util.registerItem(new ItemFume(), "acacia_resin");
	public static final Item ebb_of_death = Util.registerItem(new ItemFume(), "ebb_of_death");
	public static final Item droplet_of_wisdom = Util.registerItem(new ItemFume(), "droplet_of_wisdom");
	public static final Item liquid_witchcraft = Util.registerItem(new ItemFume(), "liquid_witchcraft");
	public static final Item essence_of_vitality = Util.registerItem(new ItemFume(), "essence_of_vitality");
	//Distillery
	public static final Item cleansing_balm = Util.registerItem(new ItemFume(), "cleansing_balm");
	public static final Item demonic_elixir = Util.registerItem(new ItemFume(), "demonic_elixir");
	public static final Item fiery_unguent = Util.registerItem(new ItemFume(), "fiery_unguent", "elementalFume");
	public static final Item swirl_of_depths = Util.registerItem(new ItemFume(), "swirl_of_depths", "elementalFume");
	public static final Item oil_of_vitriol = Util.registerItem(new ItemFume(), "oil_of_vitriol");
	public static final Item otherworldly_tears = Util.registerItem(new ItemFume(), "otherworldly_tears");
	public static final Item heaven_extract = Util.registerItem(new ItemFume(), "heaven_extract", "elementalFume");
	public static final Item stone_ichor = Util.registerItem(new ItemFume(), "stone_ichor", "elementalFume");
	//Spinning Wheel
	public static final Item diabolical_vein = Util.registerItem("diabolical_vein");
	public static final Item pure_filament = Util.registerItem("pure_filament");
	public static final Item witches_stitching = Util.registerItem("witches_stitching");
	public static final Item sanguine_cloth = Util.registerItem("sanguine_cloth");
	public static final Item spirit_string = Util.registerItem("spirit_string");
	public static final Item golden_thread = Util.registerItem("golden_thread");
	//Poppets
	public static final Item poppet = Util.registerItem("poppet");
	public static final Item poppet_binding = Util.registerItem(new ItemPoppet(false), "poppet_binding");
	public static final Item poppet_clumsy = Util.registerItem(new ItemPoppet(false), "poppet_clumsy");
	public static final Item poppet_deathprotection = Util.registerItem(new ItemPoppet(true), "poppet_deathprotection");
	public static final Item poppet_earthprotection = Util.registerItem(new ItemPoppet(false), "poppet_earthprotection");
	public static final Item poppet_flameprotection = Util.registerItem(new ItemPoppet(false), "poppet_flameprotection");
	public static final Item poppet_hungerprotection = Util.registerItem(new ItemPoppet(false), "poppet_hungerprotection");
	public static final Item poppet_spiritbane = Util.registerItem(new ItemPoppet(false), "poppet_spiritbane");
	public static final Item poppet_tool = Util.registerItem(new ItemPoppet(true), "poppet_tool");
	public static final Item poppet_vampiric = Util.registerItem(new ItemPoppet(false), "poppet_vampiric");
	public static final Item poppet_voodooprotection = Util.registerItem(new ItemPoppet(false), "poppet_voodooprotection");
	public static final Item poppet_voodoo = Util.registerItem(new ItemVoodooPoppet(), "poppet_voodoo");
	public static final Item poppet_wasting = Util.registerItem(new ItemPoppet(false), "poppet_wasting");
	public static final Item poppet_waterprotection = Util.registerItem(new ItemPoppet(true), "poppet_waterprotection");
	//Plants
	public static final Item aconitum = Util.registerItem("aconitum", "cropAconitum", "wolfsbane");
	public static final Item belladonna = Util.registerItem("belladonna", "cropBelladonna");
	public static final Item garlic = new ItemGarlic();
	public static final Item hellebore = Util.registerItem("hellebore", "cropHellebore");
	public static final Item mandrake_root = Util.registerItem("mandrake_root", "cropMandrake");
	public static final Item white_sage = Util.registerItem("white_sage", "cropWhiteSage");
	public static final Item wormwood = Util.registerItem("wormwood", "cropWormwood");
	//Seeds
	public static final Item aconitum_seeds = Util.registerItem(new ItemModSeeds(crop_aconitum, Blocks.FARMLAND), "aconitum_seeds", "listAllseed");
	public static final Item belladonna_seeds = Util.registerItem(new ItemModSeeds(crop_belladonna, Blocks.FARMLAND), "belladonna_seeds", "listAllseed");
	public static final Item garlic_seeds = Util.registerItem(new ItemModSeeds(crop_garlic, Blocks.FARMLAND), "garlic_seeds", "listAllseed");
	public static final Item hellebore_seeds = Util.registerItem(new ItemModSeeds(crop_hellebore, Blocks.FARMLAND), "hellebore_seeds", "listAllseed");
	public static final Item mandrake_seeds = Util.registerItem(new ItemModSeeds(crop_mandrake, Blocks.FARMLAND), "mandrake_seeds", "listAllseed");
	public static final Item white_sage_seeds = Util.registerItem(new ItemModSeeds(crop_white_sage, Blocks.FARMLAND), "white_sage_seeds", "listAllseed");
	public static final Item wormwood_seeds = Util.registerItem(new ItemModSeeds(crop_wormwood, Blocks.FARMLAND), "wormwood_seeds", "listAllseed");
	//Food
	public static final Item elderberries = Util.registerItem(new ItemFoodSeed(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100), 0.1f), "elderberries");
	public static final Item juniper_berries = Util.registerItem(new ItemFoodSeed(1, 0.5f, false), "juniper_berries");
	public static final ItemFood stew_of_the_grotesque = new ItemStewOfTheGrotesque();
	//Drops
	public static final Item lizard_leg = Util.registerItem("lizard_leg");
	public static final Item owlets_wing = Util.registerItem("owlets_wing");
	public static final Item ravens_feather = Util.registerItem("ravens_feather", "feather");
	public static final Item adders_fork = Util.registerItem("adders_fork");
	public static final Item toe_of_frog = Util.registerItem("toe_of_frog");
	public static final Item hellhound_horn = Util.registerItem("hellhound_horn");
	public static final Item heart = new ItemHeart();
	public static final Item demon_heart = Util.registerItem("demon_heart");
	public static final Item snake_venom = Util.registerItem("snake_venom").setContainerItem(Items.GLASS_BOTTLE);
	public static final Item bottle_of_blood = Util.registerItem(new ItemBloodBottle(), "bottle_of_blood");
	public static final Item bottle_of_vampire_blood = Util.registerItem("bottle_of_vampire_blood");
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
	public static final Item dragons_blood_resin = Util.registerItem("dragons_blood_resin", "resinDragonsBlood");
	public static final Item box_of_sealed_evil = new ItemBoxSealedEvil();
	public static final Item contract = new ItemContract();
	public static final Item skeleton_key = new ItemSkeletonKey();

	public static final ItemFood garlic_grilled = new ItemGarlicGrilled();
	public static final ItemFood elderberry_jelly = new ItemElderberryJelly();
	public static final ItemFood juniper_tea = new ItemJuniperTea();
	public static final ItemFood garlic_bread = new ItemGarlicBread();

	public static final Item sigil_mending = Util.registerItem(new ItemSigil(600, true) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 100, 0));
		}
	}, "sigil_mending");
	public static final Item sigil_ruin = Util.registerItem(new ItemSigil(600, false) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			entity.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 200, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 200, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 200, 0));
			entity.addPotionEffect(new PotionEffect(ModPotions.magic_weakness, 200, 0));
		}
	}, "sigil_ruin");
	public static final Item sigil_binding = Util.registerItem(new ItemSigil(600, false) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 300, 5));
		}
	}, "sigil_binding");
	public static final Item sigil_cleansing = Util.registerItem(new ItemSigil(1200, true) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			List<PotionEffect> result = entity.getActivePotionEffects().stream().filter(p -> !p.getPotion().isBadEffect()).collect(Collectors.toList());
			entity.clearActivePotions();
			for (PotionEffect effect : result) {
				entity.addPotionEffect(effect);
			}
		}
	}, "sigil_cleansing");
	public static final Item sigil_failure = Util.registerItem(new ItemSigil(600, false) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			if (!entity.world.isRemote && entity instanceof EntityPlayer) {
				ExtendedPlayer ep = entity.getCapability(ExtendedPlayer.CAPABILITY, null);
				ep.canRitual = false;
				ep.ritualDisabledTime = 24000;
				ExtendedPlayer.syncToClient((EntityPlayer) entity);
				((WorldServer) entity.world).spawnParticle(EnumParticleTypes.SMOKE_LARGE, false, entity.posX, entity.posY, entity.posZ, 100, 0.5, 1, 0.5, 0.05);
			}
		}
	}, "sigil_failure");
	public static final Item sigil_purity = Util.registerItem(new ItemSigil(6000, true) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			if (!entity.world.isRemote && entity instanceof EntityPlayer) {
				ExtendedPlayer ep = entity.getCapability(ExtendedPlayer.CAPABILITY, null);
				List<Curse> weakerCurses = ep.getCurses().stream().filter(Curse::isLesser).filter(c -> !c.isPositive()).collect(Collectors.toList());
				for (Curse curse : weakerCurses) {
					// 50% chance to remove curse for each lesser curse
					if (entity.getRNG().nextDouble() < 0.5) ep.removeCurse(curse);
				}
			}
		}
	}, "sigil_purity");
	public static final Item sigil_luck = Util.registerItem(new ItemSigil(6000, true) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			entity.addPotionEffect(new PotionEffect(MobEffects.LUCK, 6000, 0));
		}
	}, "sigil_luck");
	public static final Item sigil_battle = Util.registerItem(new ItemSigil(3000, true) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			entity.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 0));
			entity.addPotionEffect(new PotionEffect(ModPotions.rhino_hide, 1200, 0));
		}
	}, "sigil_battle");
	public static final Item sigil_disorientation = Util.registerItem(new ItemSigil(600, false) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			entity.addPotionEffect(new PotionEffect(ModPotions.fear, 300));
		}
	}, "sigil_disorientation");
	public static final Item sigil_shrieking = Util.registerItem(new ItemSigil(0, false) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			if (entity instanceof EntityPlayer && entity.world.isRemote)
				entity.world.playSound((EntityPlayer) entity, entity.getPosition(), SoundEvents.ENTITY_GHAST_HURT, SoundCategory.BLOCKS, 100.0F, 1.0F);
			entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0));
		}
	}, "sigil_shrieking");
	public static final Item sigil_sentinel = Util.registerItem(new ItemSigil(600, false) {
		@Override
		public void applyEffects(EntityLivingBase entity) {
			if (!entity.world.isRemote && entity instanceof EntityPlayer) {
				int amount = entity.getRNG().nextInt(3) + 1;
				EntityGhost temp = (EntityGhost) ModEntities.ghost.newInstance(entity.world);
				temp.setAttackTarget(entity);
				for (int i = 0; i < amount; i++) {
					temp.setPosition(entity.posX + entity.getRNG().nextGaussian() * 5, entity.posY + 3, entity.posZ + entity.getRNG().nextGaussian() * 5);
					entity.world.spawnEntity(temp);
				}
			}
		}
	}, "sigil_sentinel");

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
			if (!name.contains("silver"))
				CarvingUtils.getChiselRegistry().addVariation(name, CarvingUtils.variationFor(base.getDefaultState(), 0));
			for (String variant : names) {
				Block block = new ModBlockChisel(variant, base, oreDictionaryNames.toArray(new String[0]));
				if (!name.contains("silver"))
					CarvingUtils.getChiselRegistry().addVariation(name, CarvingUtils.variationFor(block.getDefaultState(), list.size() + 1));
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