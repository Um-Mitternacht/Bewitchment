package com.bewitchment.common.block;

import com.bewitchment.common.block.chisel.BlockColdIronChiseled;
import com.bewitchment.common.block.chisel.BlockColdIronChiseled.BlockColdIronVariant;
import com.bewitchment.common.block.chisel.BlockNetherSteelChiseled;
import com.bewitchment.common.block.chisel.BlockNetherSteelChiseled.BlockSteelVariant;
import com.bewitchment.common.block.chisel.BlockSilverChiseled;
import com.bewitchment.common.block.chisel.BlockSilverChiseled.BlockSilverVariant;
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
import com.bewitchment.common.item.ModItems;
import com.bewitchment.common.lib.LibBlockName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidBlock;
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

	private static Block STAIRS_ICE = new Block(Material.ICE);
	private static Block STAIRS_SCORNED_BRICK = new Block(Material.ROCK);

	private ModBlocks() {

	}

	public static void register(final IForgeRegistry<Block> registry) {
		for (final IFluidBlock fluidBlock : Fluids.MOD_FLUID_BLOCKS) {
			registry.register((Block) fluidBlock);
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
				new BlockCrop(LibBlockName.CROP_INFESTED_WHEAT),
				new CropWormwood(),
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
				new BlockFakeIceStairs("fake_ice_stairs", STAIRS_ICE.getDefaultState(), Material.ICE),
				new BlockScornedBrickStairs("scorned_brick_stairs", STAIRS_SCORNED_BRICK.getDefaultState(), Material.ROCK),
				new BlockFakeIceSlabDouble("fake_ice_slab_double"),
				new BlockFakeIceSlabHalf("fake_ice_slab_half"),
				new BlockBeehive(LibBlockName.BEEHIVE, Material.GRASS),
				new BlockWitchAltar(LibBlockName.WITCH_ALTAR, Material.ROCK),
				new BlockThreadSpinner(LibBlockName.THREAD_SPINNER),
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
				new BlockNetherSteelChiseled(Material.IRON, SoundType.METAL).setHardness(5.0F),
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
		OreDictionary.registerOre("oreNuummite", new ItemStack(ModBlocks.gem_ore, 1, 2));
		OreDictionary.registerOre("oreAmethyst", new ItemStack(ModBlocks.gem_ore, 1, 8));
		OreDictionary.registerOre("oreAlexandrite", new ItemStack(ModBlocks.gem_ore, 1, 9));
		OreDictionary.registerOre("oreSilver", new ItemStack(ModBlocks.silver_ore));
		OreDictionary.registerOre("oreBloodstone", new ItemStack(ModBlocks.gem_ore, 1, 5));
		OreDictionary.registerOre("oreTourmaline", new ItemStack(ModBlocks.gem_ore, 1, 4));
		OreDictionary.registerOre("oreMalachite", new ItemStack(ModBlocks.gem_ore, 1, 7));
		OreDictionary.registerOre("oreTigersEye", new ItemStack(ModBlocks.gem_ore, 1, 3));
		OreDictionary.registerOre("oreJasper", new ItemStack(ModBlocks.gem_ore, 1, 6));
		OreDictionary.registerOre("blockNethersteel", new ItemStack(ModBlocks.nethersteel));
		OreDictionary.registerOre("oreSalt", new ItemStack(ModBlocks.salt_ore));
		//Candles
		for (int i = 0; i < 16; i++) {
			OreDictionary.registerOre("blockCandle", new ItemStack(ModBlocks.candle_small, 1, i));
			OreDictionary.registerOre("blockCandle", new ItemStack(ModBlocks.candle_medium, 1, i));
		}

		//Wool oredicts, used for coloring brews
		OreDictionary.registerOre("blockWoolWHITE", new ItemStack(Blocks.WOOL, 1, 0));
		OreDictionary.registerOre("blockWoolORANGE", new ItemStack(Blocks.WOOL, 1, 1));
		OreDictionary.registerOre("blockWoolMAGENTA", new ItemStack(Blocks.WOOL, 1, 2));
		OreDictionary.registerOre("blockWoolLIGHT_BLUE", new ItemStack(Blocks.WOOL, 1, 3));
		OreDictionary.registerOre("blockWoolYELLOW", new ItemStack(Blocks.WOOL, 1, 4));
		OreDictionary.registerOre("blockWoolLIME", new ItemStack(Blocks.WOOL, 1, 5));
		OreDictionary.registerOre("blockWoolPINK", new ItemStack(Blocks.WOOL, 1, 6));
		OreDictionary.registerOre("blockWoolGRAY", new ItemStack(Blocks.WOOL, 1, 7));
		OreDictionary.registerOre("blockWoolSILVER", new ItemStack(Blocks.WOOL, 1, 8));
		OreDictionary.registerOre("blockWoolCYAN", new ItemStack(Blocks.WOOL, 1, 9));
		OreDictionary.registerOre("blockWoolPURPLE", new ItemStack(Blocks.WOOL, 1, 10));
		OreDictionary.registerOre("blockWoolBLUE", new ItemStack(Blocks.WOOL, 1, 11));
		OreDictionary.registerOre("blockWoolBROWN", new ItemStack(Blocks.WOOL, 1, 12));
		OreDictionary.registerOre("blockWoolGREEN", new ItemStack(Blocks.WOOL, 1, 13));
		OreDictionary.registerOre("blockWoolRED", new ItemStack(Blocks.WOOL, 1, 14));
		OreDictionary.registerOre("blockWoolBLACK", new ItemStack(Blocks.WOOL, 1, 15));

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

		// Misc
		OreDictionary.registerOre("kelp", ModItems.kelp);
	}
}
