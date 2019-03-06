package com.bewitchment.common.block;

import com.bewitchment.common.block.decorations.*;
import com.bewitchment.common.block.misc.*;
import com.bewitchment.common.block.natural.*;
import com.bewitchment.common.block.natural.crop.*;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.block.natural.plants.BlockEmberGrass;
import com.bewitchment.common.block.natural.plants.BlockMoonbell;
import com.bewitchment.common.block.natural.plants.BlockMoss;
import com.bewitchment.common.block.natural.plants.BlockTorchwood;
import com.bewitchment.common.block.natural.tree.BlockModLeaves;
import com.bewitchment.common.block.natural.tree.BlockModLog;
import com.bewitchment.common.block.natural.tree.BlockModSapling;
import com.bewitchment.common.block.natural.tree.BlockPlanks;
import com.bewitchment.common.block.tiles.*;
import com.bewitchment.common.crafting.VanillaCrafting;
import com.bewitchment.common.integration.chisel.*;
import com.bewitchment.common.integration.chisel.BlockColdIronChiseled.BlockColdIronVariant;
import com.bewitchment.common.integration.chisel.BlockNetherSteelChiseled.BlockSteelVariant;
import com.bewitchment.common.integration.chisel.BlockSilverChiseled.BlockSilverVariant;
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@ObjectHolder(LibMod.MOD_ID)
public final class ModBlocks {

	//Todo: Add new gemstone blocks, and meta the existing ones.

	public static final BlockCrop crop_aconitum = null;
	public static final BlockCrop crop_asphodel = null;
	public static final BlockCrop crop_belladonna = null;
	public static final BlockCrop crop_ginger = null;
	public static final BlockCrop crop_kelp = null;
	public static final BlockCrop crop_mint = null;
	public static final BlockCrop crop_white_sage = null;
	public static final BlockCrop crop_mandrake_root = null;
	public static final BlockCrop crop_lavender = null;
	public static final BlockCrop crop_thistle = null;
	public static final BlockCrop crop_tulsi = null;
	public static final BlockCrop crop_kenaf = null;
	public static final BlockCrop crop_silphium = null;
	public static final BlockCrop crop_garlic = null;
	public static final BlockCrop crop_wormwood = null;
	public static final BlockCrop crop_hellebore = null;
	public static final BlockCrop crop_chrysanthemum = null;
	public static final BlockCrop crop_sagebrush = null;
	public static final BlockCrop crop_infested_wheat = null;
	//--------------------------------Blocks--------------------------------//
	public static final Block silver_block = null;
	public static final Block silver_ore = null;
	public static final Block coquina = null;
	public static final Block gem_block = null;
	public static final Block cauldron = null;
	public static final Block magic_mirror = null;
	public static final Block candle_medium = null;
	public static final Block candle_small = null;
	public static final Block candle_medium_lit = null;
	public static final Block candle_small_lit = null;
	public static final Block salt_barrier = null;
	public static final Block beehive = null;
	public static final Block oven = null;
	public static final Block distillery = null;
	public static final Block apiary = null;
	public static final Block brazier = null;
	public static final Block salt_ore = null;
	public static final Block gem_ore = null;
	public static final Block nethersteel = null;
	public static final Block fake_ice = null;
	public static final Block fake_ice_fence = null;
	public static final Block fake_ice_stairs = null;
	public static final Block fake_ice_slab_half = null;
	public static final Block fake_ice_slab_double = null;
	public static final Block scorned_brick_stairs = null;
	public static final Block embittered_brick_stairs = null;
	public static final Block elder_stairs = null;
	public static final Block juniper_stairs = null;
	public static final Block yew_stairs = null;
	public static final Block cypress_stairs = null;
	public static final Block torchwood = null;
	public static final Block ember_grass = null;
	public static final Block log_elder = null;
	public static final Block log_juniper = null;
	public static final Block log_yew = null;
	public static final Block log_cypress = null;
	public static final Block leaves_elder = null;
	public static final Block leaves_juniper = null;
	public static final Block leaves_yew = null;
	public static final Block leaves_cypress = null;
	public static final Block planks_elder = null;
	public static final Block planks_juniper = null;
	public static final Block planks_yew = null;
	public static final Block planks_cypress = null;
	public static final Block sapling = null;
	public static final Block moonbell = null;
	public static final Block witch_altar = null;
	public static final Block thread_spinner = null;
	public static final Block ritual_glyphs = null;
	public static final Block crystal_ball = null;
	public static final Block embittered_bricks = null;
	public static final Block embittered_brick_fence = null;
	public static final Block scorned_bricks = null;
	public static final Block scorned_brick_fence = null;
	public static final Block goblet = null;
	public static final Block gem_bowl = null;
	public static final Block tarot_table = null;
	public static final Block cold_iron_block = null;
	public static final Block graveyard_dirt = null;
	public static final Block purifying_earth = null;
	public static final Block spanish_moss = null;
	public static final Block spanish_moss_end = null;

	public static final Block infested_farmland = null;
	public static final Block witchfire = null;
	public static final Block revealing_lantern = null;
	public static final Block lantern = null;
	public static final Block witches_light = null;
	public static final Block placed_item = null;

	public static final Block silver_block_chisel = null;
	public static final Block cold_iron_block_chisel = null;
	public static final Block nethersteel_chisel = null;
	public static final Block coquina_chisel = null;
	public static final Block scorned_bricks_chisel = null;

	private static Block STAIRS_BW_ICE = new Block(Material.ICE);
	private static Block STAIRS_BW_STONE = new Block(Material.ROCK);
	private static Block STAIRS_BW_METAL = new Block(Material.IRON);
	private static Block STAIRS_BW_GLASS = new Block(Material.GLASS);
	private static Block STAIRS_BW_WOOD = new Block(Material.WOOD);

	private ModBlocks() {

	}

	public static void register(final IForgeRegistry<Block> registry) {
		for (final Block fluidBlock : Fluids.MOD_FLUID_BLOCKS) {
			registry.register(fluidBlock);
		}
		//Crops
		//Todo: Make the rest of the crops flammable.
		registry.registerAll(
				new BlockCrop(LibBlockName.CROP_ACONITUM),
				new BlockCrop(LibBlockName.CROP_ASPHODEL),
				new BlockCrop(LibBlockName.CROP_GINGER),
				new BlockCrop(LibBlockName.CROP_WHITE_SAGE),
				new BlockCrop(LibBlockName.CROP_MANDRAKE),
				new BlockCrop(LibBlockName.CROP_LAVENDER),
				new BlockCrop(LibBlockName.CROP_TULSI),
				new BlockCrop(LibBlockName.CROP_GARLIC),
				new BlockCrop(LibBlockName.CROP_HELLEBORE),
				new BlockCrop(LibBlockName.CROP_CHRYSANTHEMUM),
				new BlockCrop(LibBlockName.CROP_SAGEBRUSH),
				new BlockCrop(LibBlockName.CROP_WORMWOOD),
				new BlockCrop(LibBlockName.CROP_INFESTED_WHEAT),
				new CropSilphium(),
				new CropKenaf(),
				new CropThistle(),
				new CropKelp(),
				new CropBelladonna(),
				new CropMint(),
				new BlockMoonbell(),
				new BlockInfestedFarmland(),
				new BlockMoss(true),
				new BlockMoss(false)
		);
		//Ore
		registry.register(new BlockSilverOre());
		registry.register(new BlockSaltOre());
		registry.register(new BlockGemOre());
		registry.register(new BlockWitchFire());
		//Tool Blocks
		registry.registerAll(
				new BlockCauldron(),
				new BlockMagicMirror(),
				new BlockOven(),
				new BlockBrazier(),
				new BlockCandleMedium(LibBlockName.CANDLE_MEDIUM, false),
				new BlockCandleSmall(LibBlockName.CANDLE_SMALL, false),
				new BlockCandleMedium(LibBlockName.CANDLE_MEDIUM_LIT, true),
				new BlockCandleSmall(LibBlockName.CANDLE_SMALL_LIT, true),
				new BlockSaltBarrier(),
				new BlockApiary(),
				new BlockTorchwood(),
				new BlockEmberGrass(),
				new BlockFakeIce(),
				new BlockFakeIceFence(),
				new BlockScornedBrickFence(),
				new BlockEmbitteredBrickFence(),
				new BlockFakeIceStairs("fake_ice_stairs", STAIRS_BW_ICE.getDefaultState(), Material.ICE),
				new BlockScornedBrickStairs("scorned_brick_stairs", STAIRS_BW_STONE.getDefaultState(), Material.ROCK),
				new BlockEmbitteredBrickStairs("embittered_brick_stairs", STAIRS_BW_STONE.getDefaultState(), Material.ROCK),
				new BlockWoodStairs("elder_stairs", STAIRS_BW_WOOD.getDefaultState(), Material.WOOD),
				new BlockWoodStairs("juniper_stairs", STAIRS_BW_WOOD.getDefaultState(), Material.WOOD),
				new BlockWoodStairs("yew_stairs", STAIRS_BW_WOOD.getDefaultState(), Material.WOOD),
				new BlockWoodStairs("cypress_stairs", STAIRS_BW_WOOD.getDefaultState(), Material.WOOD),
				new BlockFakeIceSlabDouble("fake_ice_slab_double"),
				new BlockFakeIceSlabHalf("fake_ice_slab_half"),
				new BlockBeehive(LibBlockName.BEEHIVE, Material.GRASS),
				new BlockWitchAltar(LibBlockName.WITCH_ALTAR, Material.ROCK),
				new BlockLoom(LibBlockName.THREAD_SPINNER),
				new BlockCircleGlyph(LibBlockName.GLYPHS),
				new BlockCrystalBall(LibBlockName.CRYSTAL_BALL),
				new BlockGoblet(LibBlockName.GOBLET),
				new BlockGemBowl(LibBlockName.GEM_BOWL),
				new BlockTarotTable(),
				new BlockLantern(true),
				new BlockLantern(false),
				new BlockWitchesLight(),
				new BlockPurifyingEarth(),
				new BlockPlacedItem()
		);

		registry.register(new BlockDistillery(LibBlockName.DISTILLERY));

		//Normal Blocks
		registry.registerAll(
				new BlockMod(LibBlockName.SILVER_BLOCK, Material.IRON, SoundType.METAL).setHardness(5.0F),
				new BlockSilverChiseled(Material.IRON, SoundType.METAL).setHardness(5.0F),
				new BlockColdIronChiseled(Material.IRON, SoundType.METAL).setHardness(5.0F),
				new BlockCoquinaChiseled(Material.ROCK, SoundType.STONE).setHardness(3.0F),
				new BlockNetherSteelChiseled(Material.IRON, SoundType.METAL).setHardness(5.0F),
				new BlockScornedBricksChiseled(Material.ROCK, SoundType.STONE).setHardness(5.0F),
				new BlockMod(LibBlockName.COLD_IRON_BLOCK, Material.IRON, SoundType.METAL).setHardness(5.0F),
				new BlockMod(LibBlockName.NETHERSTEEL, Material.IRON, SoundType.METAL).setHardness(5.0F),
				new BlockGem(),
				new BlockMod(LibBlockName.COQUINA, Material.ROCK).setHardness(5.0F),
				new BlockMod(LibBlockName.EMBITTERED_BRICKS, Material.ROCK, SoundType.STONE).setHardness(5.0F),
				new BlockMod(LibBlockName.SCORNED_BRICKS, Material.ROCK, SoundType.STONE).setHardness(5.0F),
				new BlockGraveyardDirt()
		);

		//Trees
		registry.registerAll(
				new BlockModLog(LibBlockName.LOG_ELDER),
				new BlockModLog(LibBlockName.LOG_JUNIPER),
				new BlockModLog(LibBlockName.LOG_YEW),
				new BlockModLog(LibBlockName.LOG_CYPRESS),
				new BlockModLeaves(LibBlockName.LEAVES_ELDER),
				new BlockModLeaves(LibBlockName.LEAVES_JUNIPER),
				new BlockModLeaves(LibBlockName.LEAVES_YEW),
				new BlockModLeaves(LibBlockName.LEAVES_CYPRESS),
				new BlockPlanks(LibBlockName.PLANKS_ELDER),
				new BlockPlanks(LibBlockName.PLANKS_JUNIPER),
				new BlockPlanks(LibBlockName.PLANKS_YEW),
				new BlockPlanks(LibBlockName.PLANKS_CYPRESS),
				new BlockModSapling(LibBlockName.SAPLING)
		);
	}

	public static void init() {
		VanillaCrafting.blocks();
		initOreDictionary();
		//Silver is supported by default.
		FMLInterModComms.sendMessage("chisel", "variation:add", "coquina|bewitchment:coquina|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "coquina|bewitchment:coquina_chisel|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "coquina|bewitchment:coquina_chisel|1");
		FMLInterModComms.sendMessage("chisel", "variation:add", "coquina|bewitchment:coquina_chisel|2");
		FMLInterModComms.sendMessage("chisel", "variation:add", "coquina|bewitchment:coquina_chisel|3");

		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|1");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|2");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|3");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|4");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|5");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|6");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|7");
		FMLInterModComms.sendMessage("chisel", "variation:add", "cold_iron|bewitchment:cold_iron_block_chisel|8");

		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|1");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|2");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|3");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|4");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|5");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|6");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|7");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|8");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|9");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|10");
		FMLInterModComms.sendMessage("chisel", "variation:add", "nethersteel|bewitchment:nethersteel_chisel|11");

		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks_chisel|0");
		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks_chisel|1");
		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks_chisel|2");
		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks_chisel|3");
		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks_chisel|4");
		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks_chisel|5");
		FMLInterModComms.sendMessage("chisel", "variation:add", "scorn|bewitchment:scorned_bricks_chisel|6");
	}


	private static void initOreDictionary() {
		//Crystals, Minerals, and Metals
		OreDictionary.registerOre("coquina", new ItemStack(ModBlocks.coquina));
		OreDictionary.registerOre("limestone", new ItemStack(ModBlocks.coquina));
		OreDictionary.registerOre("blockSilver", new ItemStack(ModBlocks.silver_block));
		OreDictionary.registerOre("blockColdIron", new ItemStack(ModBlocks.cold_iron_block, 1, 0));
		OreDictionary.registerOre("blockNethersteel", new ItemStack(ModBlocks.nethersteel, 1, 0));
		for (BlockSilverVariant sv : BlockSilverVariant.values()) {
			OreDictionary.registerOre("blockSilver", new ItemStack(ModBlocks.silver_block_chisel, 1, sv.ordinal()));
		}
		for (BlockColdIronVariant sv : BlockColdIronVariant.values()) {
			OreDictionary.registerOre("blockColdIron", new ItemStack(ModBlocks.cold_iron_block_chisel, 1, sv.ordinal()));
		}
		for (BlockSteelVariant sv : BlockSteelVariant.values()) {
			OreDictionary.registerOre("blockNethersteel", new ItemStack(ModBlocks.nethersteel_chisel, 1, sv.ordinal()));
		}
		OreDictionary.registerOre("blockGarnet", new ItemStack(ModBlocks.gem_block, 1, 0));
		OreDictionary.registerOre("blockNuummite", new ItemStack(ModBlocks.gem_block, 1, 1));
		OreDictionary.registerOre("blockTigersEye", new ItemStack(ModBlocks.gem_block, 1, 2));
		OreDictionary.registerOre("blockTourmaline", new ItemStack(ModBlocks.gem_block, 1, 3));
		OreDictionary.registerOre("blockBloodstone", new ItemStack(ModBlocks.gem_block, 1, 4));
		OreDictionary.registerOre("blockJasper", new ItemStack(ModBlocks.gem_block, 1, 5));
		OreDictionary.registerOre("blockMalachite", new ItemStack(ModBlocks.gem_block, 1, 6));
		OreDictionary.registerOre("blockAmethyst", new ItemStack(ModBlocks.gem_block, 1, 7));
		OreDictionary.registerOre("blockAlexandrite", new ItemStack(ModBlocks.gem_block, 1, 8));
		OreDictionary.registerOre("oreGarnet", new ItemStack(ModBlocks.gem_ore, 1, 0));
		OreDictionary.registerOre("oreNuummite", new ItemStack(ModBlocks.gem_ore, 1, 1));
		OreDictionary.registerOre("oreAmethyst", new ItemStack(ModBlocks.gem_ore, 1, 7));
		OreDictionary.registerOre("oreAlexandrite", new ItemStack(ModBlocks.gem_ore, 1, 8));
		OreDictionary.registerOre("oreSilver", new ItemStack(ModBlocks.silver_ore));
		OreDictionary.registerOre("oreSalt", new ItemStack(ModBlocks.salt_ore));
		OreDictionary.registerOre("oreBloodstone", new ItemStack(ModBlocks.gem_ore, 1, 4));
		OreDictionary.registerOre("oreTourmaline", new ItemStack(ModBlocks.gem_ore, 1, 3));
		OreDictionary.registerOre("oreMalachite", new ItemStack(ModBlocks.gem_ore, 1, 6));
		OreDictionary.registerOre("oreTigersEye", new ItemStack(ModBlocks.gem_ore, 1, 2));
		OreDictionary.registerOre("oreJasper", new ItemStack(ModBlocks.gem_ore, 1, 5));
		OreDictionary.registerOre("blockNethersteel", new ItemStack(ModBlocks.nethersteel));
		OreDictionary.registerOre("oreSalt", new ItemStack(ModBlocks.salt_ore));
		//Candles
		for (int i = 0; i < 16; i++) {
			OreDictionary.registerOre("blockCandle", new ItemStack(ModBlocks.candle_small, 1, i));
			OreDictionary.registerOre("blockCandle", new ItemStack(ModBlocks.candle_medium, 1, i));
		}

		//Imported oredicts
		OreDictionary.registerOre("logWood", ModBlocks.log_elder);
		OreDictionary.registerOre("logWood", ModBlocks.log_juniper);
		OreDictionary.registerOre("logWood", ModBlocks.log_yew);
		OreDictionary.registerOre("logWood", ModBlocks.log_cypress);
		OreDictionary.registerOre("plankWood", ModBlocks.planks_elder);
		OreDictionary.registerOre("plankWood", ModBlocks.planks_juniper);
		OreDictionary.registerOre("plankWood", ModBlocks.planks_yew);
		OreDictionary.registerOre("plankWood", ModBlocks.planks_cypress);
		OreDictionary.registerOre("treeLeaves", ModBlocks.leaves_elder);
		OreDictionary.registerOre("treeLeaves", ModBlocks.leaves_juniper);
		OreDictionary.registerOre("treeLeaves", ModBlocks.leaves_yew);
		OreDictionary.registerOre("treeLeaves", ModBlocks.leaves_cypress);
		OreDictionary.registerOre("treeSapling", ModBlocks.sapling);
		OreDictionary.registerOre("treeSapling", new ItemStack(ModBlocks.sapling, 1, 1));
		OreDictionary.registerOre("treeSapling", new ItemStack(ModBlocks.sapling, 1, 2));
		OreDictionary.registerOre("treeSapling", new ItemStack(ModBlocks.sapling, 1, 3));

		// Misc
		OreDictionary.registerOre("kelp", ModItems.kelp);
	}
}
