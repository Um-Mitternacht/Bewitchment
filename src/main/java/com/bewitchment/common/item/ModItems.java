package com.bewitchment.common.item;

import baubles.api.BaubleType;
import com.bewitchment.common.block.ModBlocks;
import com.bewitchment.common.block.chisel.BlockColdIronChiseled;
import com.bewitchment.common.block.chisel.BlockNetherSteelChiseled;
import com.bewitchment.common.block.chisel.BlockSilverChiseled;
import com.bewitchment.common.block.natural.BlockGemOre.Gem;
import com.bewitchment.common.block.natural.fluid.Fluids;
import com.bewitchment.common.core.helper.CropHelper;
import com.bewitchment.common.core.statics.ModCreativeTabs;
import com.bewitchment.common.item.block.*;
import com.bewitchment.common.item.block.ItemBlockMeta.EnumNameMode;
import com.bewitchment.common.item.equipment.ItemSilverArmor;
import com.bewitchment.common.item.equipment.ItemVampireArmor;
import com.bewitchment.common.item.equipment.ItemWitchesArmor;
import com.bewitchment.common.item.equipment.baubles.*;
import com.bewitchment.common.item.food.*;
import com.bewitchment.common.item.magic.*;
import com.bewitchment.common.item.magic.brew.ItemBrewArrow;
import com.bewitchment.common.item.magic.brew.ItemBrewDrinkable;
import com.bewitchment.common.item.magic.brew.ItemBrewThrowable;
import com.bewitchment.common.item.secrets.ItemEyeOfOld;
import com.bewitchment.common.item.tool.*;
import com.bewitchment.common.lib.LibItemName;
import com.bewitchment.common.lib.LibMod;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.LoaderException;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * This class was created by <Arekkuusu> on 26/02/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
@SuppressWarnings("ConstantConditions")
@ObjectHolder(LibMod.MOD_ID)
public final class ModItems {

	public static final Item gem = null;
	public static final Item gem_powder = null;

	public static final Item fume = null;

	public static final Item mandrake_root = null;
	public static final Item seed_mandrake = null;
	public static final Item lavender = null;
	public static final Item seed_lavender = null;
	public static final Item belladonna = null;
	public static final Item seed_belladonna = null;
	public static final Item asphodel = null;
	public static final Item seed_asphodel = null;
	public static final Item kelp = null;
	public static final Item seed_kelp = null;
	public static final Item ginger = null;
	public static final Item seed_ginger = null;
	public static final Item mint = null;
	public static final Item seed_mint = null;
	public static final Item thistle = null;
	public static final Item seed_thistle = null;
	public static final Item garlic = null;
	public static final Item seed_garlic = null;
	public static final Item aconitum = null;
	public static final Item seed_aconitum = null;
	public static final Item white_sage = null;
	public static final Item seed_white_sage = null;
	public static final Item tulsi = null;
	public static final Item seed_tulsi = null;
	public static final Item kenaf = null;
	public static final Item seed_kenaf = null;
	public static final Item silphium = null;
	public static final Item seed_silphium = null;
	public static final Item wormwood = null;
	public static final Item seed_wormwood = null;
	public static final Item hellebore = null;
	public static final Item seed_hellebore = null;
	public static final Item sagebrush = null;
	public static final Item seed_sagebrush = null;
	public static final Item chrysanthemum = null;
	public static final Item seed_chrysanthemum = null;
	public static final Item moonbell = null;
	public static final Item infested_wheat = null;
	public static final Item witchweed = null;

	public static final Item glass_jar = null;

	public static final Item brew_phial_drink = null;
	public static final Item brew_phial_splash = null;
	public static final Item brew_phial_linger = null;
	public static final Item brew_arrow = null;
	public static final Item empty_brew_drink = null;
	public static final Item empty_brew_splash = null;
	public static final Item empty_brew_linger = null;

	public static final Item wax = null;
	public static final Item honey = null;
	public static final Item salt = null;
	public static final Item wool_of_bat = null;
	public static final Item tongue_of_dog = null;
	public static final Item wood_ash = null;
	public static final Item honeycomb = null;
	public static final Item empty_honeycomb = null;
	public static final Item needle_bone = null;
	public static final Item cold_iron_ingot = null;
	public static final Item silver_ingot = null;
	public static final Item silver_powder = null;
	public static final Item silver_nugget = null;
	public static final Item mortar_and_pestle = null;
	public static final Item mortar_and_pestle_stone = null;
	public static final Item athame = null;
	public static final Item boline = null;
	public static final Item taglock = null;
	public static final Item ectoplasm = null;
	public static final Item spectral_dust = null;
	public static final Item silver_scales = null;
	public static final Item eye_of_old = null;
	public static final Item heart = null;
	public static final Item envenomed_fang = null;
	public static final Item dimensional_sand = null;
	public static final Item chromatic_quill = null;
	public static final Item carnivorous_tooth = null;
	public static final Item catechu = null;
	public static final Item eye_of_ancient = null;
	public static final Item hoof = null;
	public static final Item owlets_wing = null;
	public static final Item ravens_feather = null;
	public static final Item equine_tail = null;
	public static final Item oak_apple_gall = null;
	public static final Item iron_gall_ink = null;
	public static final Item grilled_watermelon = null;
	public static final Item stew = null;
	public static final Item cold_iron_nugget = null;
	public static final Item spanish_moss = null;

	public static final Item quartz_powder = null;
	public static final Item lapis_powder = null;

	public static final Item juniper_berries = null;
	public static final Item yew_aril = null;

	public static final Item sanguine_fabric = null;

	public static final Item golden_thread = null;
	public static final Item regal_silk = null;
	public static final Item witches_stitching = null;
	public static final Item diabolic_vein = null;
	public static final Item pure_filament = null;
	public static final Item soul_string = null;
	public static final Item graveyard_dust = null;

	public static final Item cold_iron_dust_small = null;
	public static final Item cold_iron_dust = null;

	public static final Item silver_pickaxe = null;
	public static final Item silver_axe = null;
	public static final Item silver_spade = null;
	public static final Item silver_hoe = null;
	public static final Item silver_sword = null;
	public static final Item silver_helmet = null;
	public static final Item silver_chestplate = null;
	public static final Item silver_leggings = null;
	public static final Item silver_boots = null;

	public static final Item witches_hat = null;
	public static final Item witches_robes = null;
	public static final Item witches_pants = null;
	public static final Item vampire_hat = null;
	public static final Item vampire_vest = null;
	public static final Item vampire_pants = null;

	// Baubles
	public static final Item nazar = null;
	public static final Item talisman_aquamarine_crown = null;
	public static final Item talisman_adamantine_star_ring = null;
	public static final Item talisman_diamond_star = null;
	public static final Item talisman_emerald_pendant = null;
	public static final Item talisman_watching_eye = null;
	public static final Item talisman_ruby_orb = null;
	public static final Item horseshoe = null;
	public static final Item remedy_talisman = null;
	public static final Item girdle_of_the_wooded = null;
	public static final Item wrathful_eye = null;
	public static final Item mantle = null;
	public static final Item pouch = null;

	public static final Item magic_salve = null;

	public static final Item tarots = null;
	public static final Item broom = null;
	public static final Item spell_page = null;
	public static final Item ritual_chalk = null;
	public static final Item location_stone = null;
	public static final Item knowledge_fragment = null;

	public static final Item snake_venom = null;
	public static final Item filet_of_fenny_snake = null;
	public static final Item adders_fork = null;

	public static final Item toe_of_frog = null;
	public static final Item eye_of_newt = null;
	public static final Item lizard_leg = null;
	public static final Item blindworms_sting = null;

	public static final Item pentacle = null;

	public static final Item cold_iron_sword = null;
	public static final Item cold_iron_axe = null;
	public static final Item cold_iron_hoe = null;
	public static final Item cold_iron_spade = null;
	public static final Item cold_iron_pickaxe = null;


	private ModItems() {
	}

	public static void register(final IForgeRegistry<Item> registry) {
		CropHelper.getFoods().forEach((crop, item) -> registry.register(item));
		CropHelper.getSeeds().forEach((crop, item) -> registry.register(item));
		for (final IFluidBlock fluidBlock : Fluids.MOD_FLUID_BLOCKS) {
			registry.register(itemBlock((Block) fluidBlock));
		}
		registry.register(new ItemGem());
		// registry.register(new ItemFume());
		registry.register(new ItemFumes(LibItemName.FUME));
		registry.register(new ItemGemPowder(LibItemName.GEM_POWDER));
		registry.register(new ItemMod(LibItemName.COLD_IRON_INGOT));
		registry.register(new ItemMod(LibItemName.SILVER_POWDER));
		registry.register(new ItemMod(LibItemName.SILVER_INGOT));
		registry.register(new ItemMod(LibItemName.SILVER_NUGGET));

		registry.register(new ItemSpellPage(LibItemName.SPELL_PAGE));
		registry.register(new ItemKnowledgeFragment());

		//Misc
		registry.registerAll(
				new ItemHoney(),
				new ItemSalt(),
				new ItemMod(LibItemName.WAX),
				new ItemMod(LibItemName.HONEYCOMB),
				new ItemMod(LibItemName.EMPTY_HONEYCOMB),
				new ItemMod(LibItemName.MORTAR_AND_PESTLE),
				new ItemMod(LibItemName.MORTAR_AND_PESTLE_STONE),
				new ItemBrewDrinkable(),
				new ItemBrewThrowable(LibItemName.BREW_PHIAL_SPLASH),
				new ItemBrewThrowable(LibItemName.BREW_PHIAL_LINGER),
				new ItemMod(LibItemName.EMPTY_BREW_DRINK),
				new ItemMod(LibItemName.EMPTY_BREW_SPLASH),
				new ItemMod(LibItemName.EMPTY_BREW_LINGER),
				new ItemBrewArrow(),
				new ItemMod(LibItemName.GLASS_JAR),
				new ItemAthame(),
				new ItemBoline(),
				new ItemTaglock(),
				new ItemMod(LibItemName.NEEDLE_BONE),
				new ItemMod(LibItemName.WOOL_OF_BAT),
				new ItemMod(LibItemName.TONGUE_OF_DOG),
				new ItemMod(LibItemName.WOOD_ASH),
				new ItemMod(LibItemName.ECTOPLASM),
				new ItemMod(LibItemName.SPECTRAL_DUST),
				new ItemMod(LibItemName.SILVER_SCALES),
				new ItemEyeOfOld(),
				new ItemMod(LibItemName.ENVENOMED_FANG),
				new ItemMod(LibItemName.DIMENSIONAL_SAND),
				new ItemMod(LibItemName.CHROMATIC_QUILL),
				new ItemMod(LibItemName.CARNIVOROUS_TOOTH),
				new ItemMod(LibItemName.EYE_OF_ANCIENT),
				new ItemMod(LibItemName.HOOF),
				new ItemMod(LibItemName.EQUINE_TAIL),
				new ItemMod(LibItemName.CATECHU),
				new ItemMod(LibItemName.OAK_APPLE_GALL),
				new ItemMod(LibItemName.IRON_GALL_INK),
				new ItemMod(LibItemName.GOLDEN_THREAD),
				new ItemMod(LibItemName.COLD_IRON_NUGGET),
				new ItemMod(LibItemName.OWLETS_WING),
				new ItemMod(LibItemName.RAVENS_FEATHER),
				new ItemMod(LibItemName.COLD_IRON_DUST),
				new ItemMod(LibItemName.COLD_IRON_DUST_SMALL),
				new ItemMod(LibItemName.REGAL_SILK),
				new ItemMod(LibItemName.WITCHES_STITCHING),
				new ItemMod(LibItemName.DIABOLIC_VEIN),
				new ItemMod(LibItemName.PURE_FILAMENT),
				new ItemMod(LibItemName.SOUL_STRING),
				new ItemMod(LibItemName.GRAVEYARD_DUST),
				new ItemMod(LibItemName.SANGUINE_FABRIC),
				new ItemMod(LibItemName.PENTACLE),
				new ItemMod(LibItemName.ADDERS_FORK),
				new ItemMod(LibItemName.FILET_OF_FENNY_SNAKE),
				new ItemMod(LibItemName.SNAKE_VENOM),
				new ItemMod(LibItemName.TOE_OF_FROG),
				new ItemMod(LibItemName.BLINDWORMS_STING),
				new ItemMod(LibItemName.LIZARD_LEG),
				new ItemMod(LibItemName.EYE_OF_NEWT),
				new ItemMod(LibItemName.QUARTZ_POWDER),
				new ItemMod(LibItemName.LAPIS_POWDER),
				new ItemHeart(),
				new ItemGrilledWatermelon(),
				new ItemJuniperBerries(),
				new ItemYewAril(),
				new ItemFilledBowl(),
				new ItemRitualChalk(LibItemName.RITUAL_CHALK),
				new ItemRemedyTalisman(),
				new ItemMagicSalve(),
				new ItemLocationStone(),
				new ItemTarots(LibItemName.TAROTS),
				new ItemBroom(LibItemName.BROOM)// ,
				// new ItemMod(LibItemName.WITCHWEED),
				// new ItemMod(LibItemName.INFESTED_WHEAT)
		);

		// Baubles
		registry.registerAll(//
				new ItemNazar(),
				new ItemHorseshoe(),
				new ItemTriskelionAmulet(),
				new ItemHellishBauble(),
				new ItemWrathfulEye(),
				new ItemTalisman(BaubleType.HEAD, 35, LibItemName.TALISMAN_AQUAMARINE_CROWN),
				new ItemTalisman(BaubleType.RING, 18, LibItemName.TALISMAN_ADAMANTINE_STAR_RING),
				new ItemTalisman(BaubleType.AMULET, 18, LibItemName.TALISMAN_EMERALD_PENDANT),
				new ItemTalisman(BaubleType.BELT, 30, LibItemName.TALISMAN_RUBY_ORB),
				new ItemTalisman(BaubleType.CHARM, 18, LibItemName.TALISMAN_WATCHING_EYE),
				new ItemGirdleOfTheWooded(LibItemName.GIRDLE_OF_THE_WOODED),
				new ItemMantle(LibItemName.MANTLE), new ItemPouch(LibItemName.POUCH)
		);

		//Equipment
		registry.registerAll(
				new ItemSilverPickaxe(),
				new ItemSilverAxe(),
				new ItemSilverSpade(),
				new ItemSilverHoe(),
				new ItemSilverSword(), new ItemSilverArmor(LibItemName.SILVER_HELMET, ModMaterials.ARMOR_SILVER, 1, EntityEquipmentSlot.HEAD),
				new ItemSilverArmor(LibItemName.SILVER_CHESTPLATE, ModMaterials.ARMOR_SILVER, 1, EntityEquipmentSlot.CHEST),
				new ItemSilverArmor(LibItemName.SILVER_LEGGINGS, ModMaterials.ARMOR_SILVER, 2, EntityEquipmentSlot.LEGS),
				new ItemSilverArmor(LibItemName.SILVER_BOOTS, ModMaterials.ARMOR_SILVER, 1, EntityEquipmentSlot.FEET),
				new ItemWitchesArmor(LibItemName.WITCHES_HAT, ModMaterials.ARMOR_BEWITCHED_LEATHER, 1, EntityEquipmentSlot.HEAD),
				new ItemWitchesArmor(LibItemName.WITCHES_ROBES, ModMaterials.ARMOR_BEWITCHED_LEATHER, 1, EntityEquipmentSlot.CHEST),
				new ItemWitchesArmor(LibItemName.WITCHES_PANTS, ModMaterials.ARMOR_BEWITCHED_LEATHER, 2, EntityEquipmentSlot.LEGS),
				new ItemVampireArmor(LibItemName.VAMPIRE_HAT, ModMaterials.ARMOR_VAMPIRE, 1, EntityEquipmentSlot.HEAD),
				new ItemVampireArmor(LibItemName.VAMPIRE_VEST, ModMaterials.ARMOR_VAMPIRE, 2, EntityEquipmentSlot.CHEST),
				new ItemVampireArmor(LibItemName.VAMPIRE_PANTS, ModMaterials.ARMOR_VAMPIRE, 2, EntityEquipmentSlot.LEGS),

				new ItemColdIronSword(),
				new ItemColdIronAxe(),
				new ItemColdIronHoe(),
				new ItemColdIronPickaxe(),
				new ItemColdIronSpade()
		);
		// Item Blocks
		registry.registerAll(
				new ItemBlockColor(ModBlocks.candle_medium),
				new ItemBlockColor(ModBlocks.candle_small),
				itemBlock(ModBlocks.fake_ice),
				itemBlock(ModBlocks.fake_ice_fence),
				itemBlock(ModBlocks.fake_ice_stairs),
				itemBlock(ModBlocks.embittered_bricks),
				itemBlock(ModBlocks.scorned_bricks),
				itemBlock(ModBlocks.scorned_brick_fence),
				itemBlock(ModBlocks.scorned_brick_stairs),

				new ItemGemOre(ModBlocks.gem_ore).setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB),
				new ItemSlab(ModBlocks.fake_ice_slab_half, (BlockSlab) ModBlocks.fake_ice_slab_half, (BlockSlab) ModBlocks.fake_ice_slab_double).setRegistryName(ModBlocks.fake_ice_slab_half.getRegistryName()),
				itemBlock(ModBlocks.silver_block),
				itemBlock(ModBlocks.silver_ore),
				itemBlock(ModBlocks.coquina),
				itemBlock(ModBlocks.cauldron),
				itemBlock(ModBlocks.oven),
				itemBlock(ModBlocks.distillery),
				itemBlock(ModBlocks.apiary),
				itemBlock(ModBlocks.brazier),
				itemBlock(ModBlocks.torchwood),
				itemBlock(ModBlocks.ember_grass),
				itemBlock(ModBlocks.beehive),
				new ItemGemBlock(ModBlocks.gem_block).setCreativeTab(ModCreativeTabs.BLOCKS_CREATIVE_TAB),
				itemBlock(ModBlocks.salt_ore),
				itemBlock(ModBlocks.nethersteel),
				itemBlock(ModBlocks.log_elder),
				itemBlock(ModBlocks.log_juniper),
				itemBlock(ModBlocks.log_yew),
				itemBlock(ModBlocks.log_cypress),
				itemBlock(ModBlocks.leaves_elder),
				itemBlock(ModBlocks.leaves_juniper),
				itemBlock(ModBlocks.leaves_yew),
				itemBlock(ModBlocks.leaves_cypress),
				itemBlock(ModBlocks.planks_elder),
				itemBlock(ModBlocks.planks_juniper),
				itemBlock(ModBlocks.planks_yew),
				itemBlock(ModBlocks.planks_cypress),
				itemBlock(ModBlocks.purifying_earth),
				new ItemBlockSapling(),
				itemBlock(ModBlocks.moonbell),
				itemBlock(ModBlocks.witch_altar),
				itemBlock(ModBlocks.thread_spinner),
				itemBlock(ModBlocks.infested_farmland),
				itemBlock(ModBlocks.crystal_ball),
				new ItemGoblet(),
				itemBlock(ModBlocks.gem_bowl),
				itemBlock(ModBlocks.magic_mirror),
				itemBlock(ModBlocks.tarot_table),
				itemBlock(ModBlocks.cold_iron_block),
				itemBlock(ModBlocks.graveyard_dirt),
				new ItemBlockRevealingLantern(ModBlocks.lantern, false),
				new ItemBlockRevealingLantern(ModBlocks.revealing_lantern, true),
				itemBlock(ModBlocks.spanish_moss)
		);

		//Chisel
		registry.registerAll(
				new ItemBlockMeta<>(ModBlocks.silver_block_chisel, BlockSilverChiseled.BlockSilverVariant.values(), EnumNameMode.TOOLTIP),
				new ItemBlockMeta<>(ModBlocks.cold_iron_block_chisel, BlockColdIronChiseled.BlockColdIronVariant.values(), EnumNameMode.TOOLTIP),
				new ItemBlockMeta<>(ModBlocks.nethersteel_chisel, BlockNetherSteelChiseled.BlockSteelVariant.values(), EnumNameMode.TOOLTIP)
		);

	}

	private static Item itemBlock(Block block) {
		if (block == null) {
			throw new LoaderException("[" + LibMod.MOD_NAME + "] Trying to register an ItemBlock for a null block");
		}
		if (block.getRegistryName() == null) {
			throw new LoaderException("[" + LibMod.MOD_NAME + "] Trying to register an ItemBlock for a block with null name - " + block.getTranslationKey());
		}
		if (block.getRegistryName().toString() == null) {
			throw new LoaderException("[" + LibMod.MOD_NAME + "] There's something wrong with the registry implementation of " + block.getTranslationKey());
		}
		return new ItemBlock(block).setRegistryName(block.getRegistryName());
	}

	public static void init() {
		initOreDictionary();

		snake_venom.setContainerItem(glass_jar);
	}

	private static void initOreDictionary() {
		OreDictionary.registerOre("gemBloodstone", new ItemStack(ModItems.gem, 1, Gem.BLOODSTONE.ordinal()));
		OreDictionary.registerOre("gemNuummite", new ItemStack(ModItems.gem, 1, Gem.NUUMMITE.ordinal()));
		OreDictionary.registerOre("gemGarnet", new ItemStack(ModItems.gem, 1, Gem.GARNET.ordinal()));
		OreDictionary.registerOre("gemTourmaline", new ItemStack(ModItems.gem, 1, Gem.TOURMALINE.ordinal()));
		OreDictionary.registerOre("gemTigersEye", new ItemStack(ModItems.gem, 1, Gem.TIGERS_EYE.ordinal()));
		OreDictionary.registerOre("gemJasper", new ItemStack(ModItems.gem, 1, Gem.JASPER.ordinal()));
		OreDictionary.registerOre("gemMalachite", new ItemStack(ModItems.gem, 1, Gem.MALACHITE.ordinal()));
		OreDictionary.registerOre("gemAmethyst", new ItemStack(ModItems.gem, 1, Gem.AMETHYST.ordinal()));
		OreDictionary.registerOre("gemAlexandrite", new ItemStack(ModItems.gem, 1, Gem.ALEXANDRITE.ordinal()));
		OreDictionary.registerOre("nuggetSilver", new ItemStack(ModItems.silver_nugget));
		OreDictionary.registerOre("ingotSilver", new ItemStack(ModItems.silver_ingot));
		OreDictionary.registerOre("honeyDrop", new ItemStack(ModItems.honey));
		OreDictionary.registerOre("dropHoney", new ItemStack(ModItems.honey));
		OreDictionary.registerOre("foodHoneydrop", new ItemStack(ModItems.honey));
		OreDictionary.registerOre("listAllsugar", new ItemStack(ModItems.honey));
		OreDictionary.registerOre("materialWax", new ItemStack(ModItems.wax));
		OreDictionary.registerOre("materialBeeswax", new ItemStack(ModItems.wax));
		OreDictionary.registerOre("materialPressedWax", new ItemStack(ModItems.wax));
		OreDictionary.registerOre("itemBeeswax", new ItemStack(ModItems.wax));
		OreDictionary.registerOre("foodSalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("dustSalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("materialSalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("lumpSalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("salt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("listAllsalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("ingredientSalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("pinchSalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("portionSalt", new ItemStack(ModItems.salt));
		OreDictionary.registerOre("cropLavender", new ItemStack(ModItems.lavender));
		OreDictionary.registerOre("listAllherb", new ItemStack(ModItems.lavender));
		OreDictionary.registerOre("cropBelladonna", new ItemStack(ModItems.belladonna));
		OreDictionary.registerOre("cropMandrake", new ItemStack(ModItems.mandrake_root));
		OreDictionary.registerOre("cropKelp", new ItemStack(ModItems.kelp));
		OreDictionary.registerOre("cropSeaweed", new ItemStack(ModItems.kelp));
		OreDictionary.registerOre("listAllveggie", new ItemStack(ModItems.kelp));
		OreDictionary.registerOre("listAllgreenveggie", new ItemStack(ModItems.kelp));
		OreDictionary.registerOre("cropAsphodel", new ItemStack(ModItems.asphodel));
		OreDictionary.registerOre("listAllspice", new ItemStack(ModItems.ginger));
		OreDictionary.registerOre("cropGinger", new ItemStack(ModItems.ginger));
		OreDictionary.registerOre("cropMint", new ItemStack(ModItems.mint));
		OreDictionary.registerOre("listAllspice", new ItemStack(ModItems.mint));
		OreDictionary.registerOre("cropSpiceleaf", new ItemStack(ModItems.mint));
		OreDictionary.registerOre("listAllgreenveggie", new ItemStack(ModItems.mint));
		OreDictionary.registerOre("cropThistle", new ItemStack(ModItems.thistle));
		OreDictionary.registerOre("cropGarlic", new ItemStack(ModItems.garlic));
		OreDictionary.registerOre("listAllherb", new ItemStack(ModItems.garlic));
		OreDictionary.registerOre("cropAconitum", new ItemStack(ModItems.aconitum));
		OreDictionary.registerOre("cropWhiteSage", new ItemStack(ModItems.white_sage));
		OreDictionary.registerOre("cropTulsi", new ItemStack(ModItems.tulsi));
		OreDictionary.registerOre("listAllherb", new ItemStack(ModItems.tulsi));
		OreDictionary.registerOre("cropKenaf", new ItemStack(ModItems.kenaf));
		OreDictionary.registerOre("cropSilphium", new ItemStack(ModItems.silphium));
		OreDictionary.registerOre("listAllgreenveggie", new ItemStack(ModItems.silphium));
		OreDictionary.registerOre("listAllherb", new ItemStack(ModItems.silphium));
		OreDictionary.registerOre("listAllspice", new ItemStack(ModItems.silphium));
		OreDictionary.registerOre("listAllspice", new ItemStack(ModItems.wormwood));
		OreDictionary.registerOre("cropWormwood", new ItemStack(ModItems.wormwood));
		OreDictionary.registerOre("feather", new ItemStack(ModItems.chromatic_quill));
		OreDictionary.registerOre("dyeBrown", new ItemStack(ModItems.catechu));
		OreDictionary.registerOre("dyeBlack", new ItemStack(ModItems.iron_gall_ink));
		OreDictionary.registerOre("dye", new ItemStack(ModItems.iron_gall_ink));
		OreDictionary.registerOre("dye", new ItemStack(ModItems.catechu));
		OreDictionary.registerOre("ingotColdIron", new ItemStack(ModItems.cold_iron_ingot));
		OreDictionary.registerOre("nuggetColdIron", new ItemStack(ModItems.cold_iron_nugget));
	}
}
