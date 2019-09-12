package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.*;
import com.bewitchment.common.block.BlockCandleBase;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import com.bewitchment.common.block.tile.entity.TileEntityIdol;
import com.bewitchment.common.block.tile.entity.TileEntityPoppetShelf;
import com.bewitchment.common.block.util.ModBlockLog;
import com.bewitchment.common.entity.misc.ModEntityPotion;
import com.bewitchment.common.entity.misc.ModEntityTippedArrow;
import com.bewitchment.common.fortune.*;
import com.bewitchment.common.item.tool.ItemGrimoireMagia;
import com.bewitchment.common.ritual.*;
import com.ferreusveritas.dynamictrees.ModTrees;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamilyVanilla;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.RegistryBuilder;
import rustic.common.items.ModItems;
import rustic.common.potions.PotionsRustic;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

import static vazkii.quark.misc.feature.ExtraPotions.dangerSight;
import static vazkii.quark.world.feature.Biotite.biotite;
import static vazkii.quark.world.feature.CaveRoots.*;
import static vazkii.quark.world.feature.Crabs.crabShell;
import static vazkii.quark.world.feature.Crabs.resilience;
import static vazkii.quark.world.feature.Frogs.gildedFrogLeg;
import static vazkii.quark.world.feature.UndergroundBiomes.glowshroom;

@SuppressWarnings({"ConstantConditions", "unused"})
@Mod.EventBusSubscriber
public class ModRegistries {
	public static final Map<Item, String[]> ORE_DICTIONARY_ENTRIES = new HashMap<>();
	public static final Map<Item, List<Predicate<ItemStack>>> MODEL_PREDICATES = new HashMap<>();
	
	@SubscribeEvent
	public static void registerRegistries(RegistryEvent.NewRegistry event) {
		new RegistryBuilder<OvenRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "oven_recipe")).setType(OvenRecipe.class).create();
		new RegistryBuilder<DistilleryRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "distillery_recipe")).setType(DistilleryRecipe.class).create();
		new RegistryBuilder<SpinningWheelRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "spinning_wheel_recipe")).setType(SpinningWheelRecipe.class).create();
		new RegistryBuilder<FrostfireRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "frostfire_recipe")).setType(FrostfireRecipe.class).create();
		new RegistryBuilder<Ritual>().setName(new ResourceLocation(Bewitchment.MODID, "ritual")).setType(Ritual.class).create();
		new RegistryBuilder<CauldronRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "cauldron_recipe")).setType(CauldronRecipe.class).create();
		new RegistryBuilder<Brew>().setName(new ResourceLocation(Bewitchment.MODID, "brew")).setType(Brew.class).create();
		new RegistryBuilder<Fortune>().setName(new ResourceLocation(Bewitchment.MODID, "fortune")).setType(Fortune.class).create();
		new RegistryBuilder<Tarot>().setName(new ResourceLocation(Bewitchment.MODID, "tarot")).setType(Tarot.class).create();
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		GameRegistry.registerTileEntity(TileEntityIdol.class, new ResourceLocation(Bewitchment.MODID, "idol"));
		GameRegistry.registerTileEntity(TileEntityPoppetShelf.class, new ResourceLocation(Bewitchment.MODID, "poppet_shelf"));
		try {
			for (Field f : ModObjects.class.getFields()) {
				Object obj = f.get(null);
				if (obj instanceof Block) {
					event.getRegistry().register((Block) obj);
					if (obj instanceof BlockCandleBase) Bewitchment.proxy.ignoreProperty((Block) obj, BlockCandleBase.LIT);
				}
				else if (obj instanceof Block[]) for (Block block : (Block[]) obj) event.getRegistry().register(block);
			}
		}
		catch (Exception ignored) {}
		Bewitchment.proxy.ignoreProperty(ModObjects.embergrass, BlockBushSpreading.TIMES_SPREAD);
		Bewitchment.proxy.ignoreProperty(ModObjects.torchwood, BlockBushSpreading.TIMES_SPREAD);
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(ModObjects.dragons_blood_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.dragons_blood_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.dragons_blood_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.dragons_blood_fence_gate, BlockFenceGate.POWERED);
		
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_wood, ModBlockLog.IS_NATURAL, ModBlockLog.IS_SLASHED);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_wood, ModBlockLog.IS_NATURAL, ModBlockLog.IS_SLASHED);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_wood, ModBlockLog.IS_NATURAL, ModBlockLog.IS_SLASHED);
		Bewitchment.proxy.ignoreProperty(ModObjects.dragons_blood_wood, ModBlockLog.IS_NATURAL);
		
		ModObjects.crop_aconitum.setItems(ModObjects.aconitum_seeds, ModObjects.aconitum);
		ModObjects.crop_belladonna.setItems(ModObjects.belladonna_seeds, ModObjects.belladonna);
		ModObjects.crop_garlic.setItems(ModObjects.garlic_seeds, ModObjects.garlic);
		ModObjects.crop_hellebore.setItems(ModObjects.hellebore_seeds, ModObjects.hellebore);
		ModObjects.crop_mandrake.setItems(ModObjects.mandrake_seeds, ModObjects.mandrake_root);
		ModObjects.crop_white_sage.setItems(ModObjects.white_sage_seeds, ModObjects.white_sage);
		ModObjects.crop_wormwood.setItems(ModObjects.wormwood_seeds, ModObjects.wormwood);
	}
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		try {
			for (Field f : ModObjects.class.getFields()) {
				Object obj = f.get(null);
				if (obj instanceof Item) {
					Item item = (Item) obj;
					List<Predicate<ItemStack>> predicates = MODEL_PREDICATES.get(item);
					event.getRegistry().register(item);
					if (predicates.isEmpty()) Bewitchment.proxy.registerTexture(item, "normal");
					else Bewitchment.proxy.registerTextureVariant(item, predicates);
				}
			}
		}
		catch (Exception ignored) {}
		for (Item item : ORE_DICTIONARY_ENTRIES.keySet()) for (String ore : ORE_DICTIONARY_ENTRIES.get(item)) OreDictionary.registerOre(ore, item);
		
		OreDictionary.registerOre("gemAll", new ItemStack(Items.QUARTZ));
		OreDictionary.registerOre("gemAll", new ItemStack(Items.DIAMOND));
		OreDictionary.registerOre("gemAll", new ItemStack(Items.EMERALD));
		OreDictionary.registerOre("gemAll", new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()));
	}
	
	@SubscribeEvent
	public static void registerEntities(RegistryEvent.Register<EntityEntry> event) {
		try {
			for (Field f : ModEntities.class.getFields()) {
				Object obj = f.get(null);
				if (obj instanceof EntityEntry) event.getRegistry().register((EntityEntry) obj);
			}
		}
		catch (Exception ignored) {}
		event.getRegistry().register(EntityEntryBuilder.create().entity(ModEntityPotion.class).id(new ResourceLocation(Bewitchment.MODID, "potion"), ModEntities.entity_id++).name("ThrownPotion").tracker(64, 1, true).build());
		event.getRegistry().register(EntityEntryBuilder.create().entity(ModEntityTippedArrow.class).id(new ResourceLocation(Bewitchment.MODID, "arrow"), ModEntities.entity_id++).name("Arrow").tracker(64, 1, true).build());
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/lizard"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/owl"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/snake"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/raven"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/toad"));
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/black_dog"));
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/hellhound"));
		
		for (int i = 0; i < 4; i++) LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/demon" + i));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/imp"));
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
		try {
			for (Field f : ModSounds.class.getFields()) {
				Object obj = f.get(null);
				if (obj instanceof SoundEvent) event.getRegistry().register((SoundEvent) obj);
			}
		}
		catch (Exception ignored) {}
	}
	
	@SubscribeEvent
	public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
		try {
			for (Field f : ModEnchantments.class.getFields()) {
				Object obj = f.get(null);
				if (obj instanceof Enchantment) event.getRegistry().register((Enchantment) obj);
			}
		}
		catch (Exception ignored) {}
	}
	
	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> event) {
		try {
			for (Field f : ModPotions.class.getFields()) {
				Object obj = f.get(null);
				if (obj instanceof Potion) event.getRegistry().register((Potion) obj);
			}
		}
		catch (Exception ignored) {}
	}
	
	@SubscribeEvent
	public static void registerRituals(RegistryEvent.Register<Ritual> event) {
		event.getRegistry().register(new RitualSolarGlory());
		event.getRegistry().register(new RitualHighMoon());
		event.getRegistry().register(new RitualSandsOfTime());
		event.getRegistry().register(new RitualDeluge());
		event.getRegistry().register(new RitualTeleport());
		event.getRegistry().register(new RitualPerception());
		event.getRegistry().register(new RitualHungryFlames());
		event.getRegistry().register(new RitualFrenziedGrowth());
		event.getRegistry().register(new RitualRevealing());
		event.getRegistry().register(new RitualCallOfTheWild());
		event.getRegistry().register(new RitualLesserHellMouth());
		event.getRegistry().register(new RitualHellmouth());
		event.getRegistry().register(new RitualGreaterHellmouth());
		event.getRegistry().register(new RitualConjureWitch());
		event.getRegistry().register(new RitualConjureWither());
		event.getRegistry().register(new RitualConjureDemon());
		event.getRegistry().register(new RitualConjureImp());
		event.getRegistry().register(new RitualSpiritualRift());
		event.getRegistry().register(new RitualDrawing(new ResourceLocation(Bewitchment.MODID, "draw_small"), Collections.singletonList(Util.get(Items.CLAY_BALL)), 150, BlockGlyph.ANY, -1, -1, Ritual.small));
		event.getRegistry().register(new RitualDrawing(new ResourceLocation(Bewitchment.MODID, "draw_medium"), Arrays.asList(Util.get(Items.CLAY_BALL), Util.get(ModObjects.wood_ash)), 300, BlockGlyph.ANY, BlockGlyph.ANY, -1, Ritual.medium));
		event.getRegistry().register(new RitualDrawing(new ResourceLocation(Bewitchment.MODID, "draw_large"), Arrays.asList(Util.get(Items.CLAY_BALL), Util.get(Items.CLAY_BALL), Util.get(ModObjects.wood_ash), Util.get(ModObjects.wood_ash)), 450, BlockGlyph.ANY, BlockGlyph.ANY, BlockGlyph.ANY, Ritual.large));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "crystal_ball"), Arrays.asList(Util.get("blockGlass"), Util.get("gemQuartz"), Util.get("gemQuartz"), Util.get("gemQuartz"), Util.get(ModObjects.droplet_of_wisdom)), null, Collections.singletonList(new ItemStack(ModObjects.crystal_ball)), 5, 500, 50, BlockGlyph.NORMAL, BlockGlyph.ENDER, -1));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "tarot_table"), Arrays.asList(Util.get(Blocks.STONEBRICK), Util.get(ModObjects.liquid_witchcraft), Util.get(ModObjects.juniper_planks), Util.get(new ItemStack(Blocks.WOOL, 1, Short.MAX_VALUE))), null, Collections.singletonList(new ItemStack(ModObjects.tarot_table)), 7, 750, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "tarot_cards"), Arrays.asList(Util.get("dye"), Util.get("dye"), Util.get("dye"), Util.get("paper"), Util.get("materialWax", "materialBeeswax", "wax", "tallow", "materialPressedWax", "itemBeeswax", "clumpWax", "beeswax", "itemWax")), null, Collections.singletonList(new ItemStack(ModObjects.tarot_cards)), 4, 500, 25, BlockGlyph.NORMAL, -1, -1));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "grimoire_magia"), Arrays.asList(Util.get("leather"), Util.get("leather"), Util.get("paper"), Util.get("paper"), Util.get("paper"), Util.get(ModObjects.liquid_witchcraft), Util.get(ModObjects.opal)), null, Collections.singletonList(ItemGrimoireMagia.create(0)), 5, 150, 20, BlockGlyph.NORMAL, -1, -1));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "purifying_earth"), Arrays.asList(Util.get("dirt"), Util.get("dirt"), Util.get("dirt"), Util.get("dirt"), Util.get("cropWhiteSage"), Util.get("cropWhiteSage"), Util.get("salt"), Util.get("salt")), null, Collections.singletonList(new ItemStack(ModObjects.purifying_earth, 16)), 2, 200, 20, BlockGlyph.NORMAL, BlockGlyph.NORMAL, -1));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "cypress_broom"), Arrays.asList(Util.get(ModObjects.broom), Util.get(ModObjects.cypress_wood), Util.get(ModObjects.cypress_sapling), Util.get(ModObjects.flying_ointment), Util.get(ModObjects.ebb_of_death)), null, Collections.singletonList(new ItemStack(ModObjects.cypress_broom)), 10, 1250, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.ENDER));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "elder_broom"), Arrays.asList(Util.get(ModObjects.broom), Util.get(ModObjects.elder_wood), Util.get(ModObjects.elder_sapling), Util.get(ModObjects.flying_ointment), Util.get(ModObjects.droplet_of_wisdom)), null, Collections.singletonList(new ItemStack(ModObjects.elder_broom)), 10, 1250, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.ENDER));
		event.getRegistry().register(new Ritual(new ResourceLocation(Bewitchment.MODID, "juniper_broom"), Arrays.asList(Util.get(ModObjects.broom), Util.get(ModObjects.juniper_wood), Util.get(ModObjects.juniper_sapling), Util.get(ModObjects.flying_ointment), Util.get(ModObjects.liquid_witchcraft)), null, Collections.singletonList(new ItemStack(ModObjects.juniper_broom)), 10, 1250, 60, BlockGlyph.NORMAL, BlockGlyph.NORMAL, BlockGlyph.ENDER));
		if (ModConfig.memes.wednesday) event.getRegistry().register(new RitualWednesday());
	}
	
	@SubscribeEvent
	public static void registerCauldronRecipes(RegistryEvent.Register<CauldronRecipe> event) {
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "focal_chalk"), Arrays.asList(Util.get(ModObjects.ritual_chalk), Util.get(ModObjects.liquid_witchcraft), Util.get("nuggetGold")), Arrays.asList(new ItemStack(ModObjects.focal_chalk), new ItemStack(ModObjects.empty_jar))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "fiery_chalk"), Arrays.asList(Util.get(ModObjects.ritual_chalk), Util.get(Items.BLAZE_POWDER), Util.get("netherrack")), Collections.singletonList(new ItemStack(ModObjects.fiery_chalk))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "phasing_chalk"), Arrays.asList(Util.get(ModObjects.ritual_chalk), Util.get(ModObjects.dimensional_sand), Util.get("dustGlowstone")), Collections.singletonList(new ItemStack(ModObjects.phasing_chalk))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "filled_goblet"), Arrays.asList(Util.get(ModObjects.goblet), Util.get(ModObjects.cloudy_oil), Util.get(Items.GHAST_TEAR), Util.get("dustRedstone"), Util.get("dustRedstone"), Util.get("dustRedstone")), Arrays.asList(new ItemStack(ModObjects.filled_goblet), new ItemStack(ModObjects.empty_jar))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "embergrass"), Arrays.asList(Util.get(new ItemStack(Blocks.TALLGRASS, 1, 1)), Util.get(ModObjects.liquid_witchcraft), Util.get(Items.BLAZE_POWDER), Util.get(new ItemStack(Blocks.YELLOW_FLOWER, 1, Short.MAX_VALUE), new ItemStack(Blocks.RED_FLOWER, 1, Short.MAX_VALUE))), Arrays.asList(new ItemStack(ModObjects.embergrass), new ItemStack(ModObjects.empty_jar))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "torchwood"), Arrays.asList(Util.get(new ItemStack(Blocks.TALLGRASS, 1, 1)), Util.get(ModObjects.liquid_witchcraft), Util.get("logWood"), Util.get("glowstone")), Arrays.asList(new ItemStack(ModObjects.torchwood), new ItemStack(ModObjects.empty_jar))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "tallow"), Arrays.asList(Util.get(Items.ROTTEN_FLESH), Util.get(Items.ROTTEN_FLESH)), Collections.singletonList(new ItemStack(ModObjects.tallow))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "slimeball"), Collections.singletonList(Util.get(ModObjects.hoof)), Collections.singletonList(new ItemStack(Items.SLIME_BALL))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "iron_gall_ink"), Arrays.asList(Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get("nuggetIron")), Collections.singletonList(new ItemStack(ModObjects.iron_gall_ink, 3))));
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "catechu_brown"), Collections.singletonList(Util.get("logWood")), Collections.singletonList(new ItemStack(ModObjects.catechu_brown, 4))));
		
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "banner_pattern_removal"), Collections.singletonList(Util.get(new ItemStack(Items.BANNER, 1, Short.MAX_VALUE))), Collections.singletonList(new ItemStack(Items.BANNER, 1, EnumDyeColor.WHITE.getDyeDamage()))));
		
		//There, the witches danced, and ate the flesh of an unborn lamb, tainted with reptiles and amphibians, to sully it's sinless nature.
		event.getRegistry().register(new CauldronRecipe(new ResourceLocation(Bewitchment.MODID, "stew_of_the_grotesque"), Arrays.asList(Util.get(ModObjects.demonic_elixir), Util.get(ModObjects.heart), Util.get(Items.MUTTON), Util.get(Items.SLIME_BALL), Util.get(ModObjects.lizard_leg), Util.get(ModObjects.toe_of_frog), Util.get(ModObjects.adders_fork), Util.get(ModObjects.belladonna), Util.get(ModObjects.hellebore)), Arrays.asList(new ItemStack(ModObjects.stew_of_the_grotesque), new ItemStack(ModObjects.empty_jar))));
	}
	
	@SubscribeEvent
	public static void registerBrews(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "instant_health"), Util.get(Items.APPLE), new PotionEffect(MobEffects.INSTANT_HEALTH, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "instant_damage"), Util.get(Items.SPIDER_EYE), new PotionEffect(MobEffects.INSTANT_DAMAGE, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "regeneration"), Util.get(Items.SPECKLED_MELON), new PotionEffect(MobEffects.REGENERATION, (20 * 20))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "poison"), Util.get(ModObjects.snake_venom, ModObjects.belladonna), s -> s.getItem() == ModObjects.snake_venom, new ItemStack(Items.GLASS_BOTTLE), new PotionEffect(MobEffects.POISON, (20 * 20))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "wither"), Util.get(ModObjects.liquid_wroth), new ItemStack(Items.GLASS_BOTTLE), new PotionEffect(MobEffects.WITHER, (20 * 15))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "night_vision"), Util.get(Items.CARROT, ModObjects.eye_of_old), new PotionEffect(MobEffects.NIGHT_VISION, (20 * 120))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blindness"), Util.get(ModObjects.iron_gall_ink, new ItemStack(Items.DYE, 1, EnumDyeColor.BLACK.getDyeDamage())), new PotionEffect(MobEffects.BLINDNESS, (20 * 10))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "strength"), Util.get(Items.PORKCHOP, Items.BEEF, Items.MUTTON, Items.CHICKEN, Items.RABBIT), new PotionEffect(MobEffects.STRENGTH, (20 * 65))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "weakness"), Util.get(ModObjects.hellebore), new PotionEffect(MobEffects.WEAKNESS, (20 * 45))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "resistance"), Util.get(Items.COOKED_PORKCHOP, Items.COOKED_BEEF, Items.COOKED_MUTTON, Items.COOKED_CHICKEN, Items.COOKED_RABBIT), new PotionEffect(MobEffects.RESISTANCE, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "fire_resistance"), Util.get(ModObjects.embergrass), new PotionEffect(MobEffects.FIRE_RESISTANCE, (20 * 150))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "absorption"), Util.get("treeSapling"), new PotionEffect(MobEffects.ABSORPTION, (20 * 35))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "jump_boost"), Util.get(Items.RABBIT_HIDE, ModObjects.hoof), new PotionEffect(MobEffects.JUMP_BOOST, (20 * 45))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "speed"), Util.get(Items.SUGAR), new PotionEffect(MobEffects.SPEED, (20 * 45))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "slowness"), Util.get(Blocks.BROWN_MUSHROOM, "slimeball"), new PotionEffect(MobEffects.SLOWNESS, (20 * 40))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "haste"), Util.get("sugarcane"), new PotionEffect(MobEffects.HASTE, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "mining_fatigue"), Util.get(ModObjects.lizard_leg), new PotionEffect(MobEffects.MINING_FATIGUE, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "luck"), Util.get(Items.RABBIT_FOOT), new PotionEffect(MobEffects.LUCK, (20 * 120))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "unluck"), Util.get(ModObjects.adders_fork), new PotionEffect(MobEffects.UNLUCK, (20 * 120))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "water_breathing"), Util.get(Items.FISH, ModObjects.eye_of_old), new PotionEffect(MobEffects.WATER_BREATHING, (20 * 150))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "invisibility"), Util.get(ModObjects.ectoplasm), new PotionEffect(MobEffects.INVISIBILITY, (20 * 25))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "hunger"), Util.get(ModObjects.tongue_of_dog), new PotionEffect(MobEffects.HUNGER, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "nausea"), Util.get(Blocks.RED_MUSHROOM), new PotionEffect(MobEffects.NAUSEA, (20 * 10))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "glowing"), Util.get(ModObjects.spectral_dust), new PotionEffect(MobEffects.GLOWING, (20 * 40))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "levitation"), Util.get(Items.CHORUS_FRUIT), new PotionEffect(MobEffects.LEVITATION, (20 * 4))));
		
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "absence"), Util.get(Items.MILK_BUCKET), new PotionEffect(ModPotions.absence, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "purification"), Util.get(Items.MELON), new PotionEffect(ModPotions.purification, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "corruption"), Util.get(Items.FERMENTED_SPIDER_EYE), new PotionEffect(ModPotions.corruption, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "magic_resistance"), Util.get(ModObjects.dragons_blood_resin), new PotionEffect(ModPotions.magic_resistance, (20 * 60))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "magic_weakness"), Util.get(ModObjects.juniper_berries), new PotionEffect(ModPotions.magic_weakness, (20 * 60))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "hellworld"), Util.get(ModObjects.hellhound_horn), new PotionEffect(ModPotions.hellworld, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "iceworld"), Util.get(Blocks.ICE, Blocks.PACKED_ICE, Blocks.SNOW), new PotionEffect(ModPotions.iceworld, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "rubedo"), Util.get(ModObjects.acacia_resin), new ItemStack(ModObjects.empty_jar), new PotionEffect(ModPotions.rubedo, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "citrinitas"), Util.get(ModObjects.birch_soul), new ItemStack(ModObjects.empty_jar), new PotionEffect(ModPotions.citrinitas, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "desertification"), Util.get(new ItemStack(Items.COAL, 1, Short.MAX_VALUE)), new PotionEffect(ModPotions.desertification, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "fertility"), Util.get(new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.BLUE_ORCHID.getMeta())), new PotionEffect(ModPotions.fertility, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "harvest"), Util.get(Blocks.PUMPKIN), new PotionEffect(ModPotions.harvest, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "spore_cloud"), Util.get(Items.MUSHROOM_STEW), new ItemStack(Items.BOWL), new PotionEffect(ModPotions.spore_cloud, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blight"), Util.get(Items.ROTTEN_FLESH), new PotionEffect(ModPotions.blight, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "ruin"), Util.get(Blocks.CACTUS, Blocks.DEADBUSH), new PotionEffect(ModPotions.ruin, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "pesticide"), Util.get("cropWormwood"), new PotionEffect(ModPotions.pesticide, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "arachnophobia"), Util.get(Blocks.WEB), new PotionEffect(ModPotions.arachnophobia, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "mending"), Util.get(Items.GOLDEN_APPLE, new ItemStack(Items.GOLDEN_APPLE, 1, 1)), new PotionEffect(ModPotions.mending, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "holy_water"), Util.get("cropWhiteSage", "cropGarlic", new ItemStack(Blocks.RED_FLOWER, 1, BlockFlower.EnumFlowerType.ALLIUM.getMeta())), new PotionEffect(ModPotions.holy_water, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "wolfsbane"), Util.get("cropAconitum"), new PotionEffect(ModPotions.wolfsbane, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "deviants_decomposure"), Util.get(ModObjects.elderberries), new PotionEffect(ModPotions.deviants_decomposure, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "sleeping"), Util.get(Blocks.RED_FLOWER), new PotionEffect(ModPotions.sleeping, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "volatility"), Util.get(Items.FIRE_CHARGE), new PotionEffect(ModPotions.volatility, (20 * 45))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "grace"), Util.get("feather"), new PotionEffect(ModPotions.grace, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "shell_armor"), Util.get("coquina"), new PotionEffect(ModPotions.shell_armor, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "deflection"), Util.get(Items.CHORUS_FRUIT_POPPED), new PotionEffect(ModPotions.deflection, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "revealing"), Util.get(Items.ENDER_PEARL, ModObjects.eye_of_old), new PotionEffect(ModPotions.revealing, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "disrobing"), Util.get(ModObjects.tallow), new PotionEffect(ModPotions.disrobing, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "sinking"), Util.get("nuggetGold", "nuggetIron", "nuggetColdIron", "nuggetCopper", "nuggetTin", "nuggetBronze", "nuggetLead"), new PotionEffect(ModPotions.sinking, (20 * 20))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "cursed_leaps"), Util.get(ModObjects.toe_of_frog), new PotionEffect(ModPotions.cursed_leaps, 1)));
		
		if (Loader.isModLoaded("rustic")) {
			event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "blazing_trail"), Util.get(ModItems.CHILI_PEPPER), new PotionEffect(PotionsRustic.BLAZING_TRAIL_POTION, (20 * 30))));
			event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "iron_skin"), Util.get(ModItems.IRONBERRIES), new PotionEffect(PotionsRustic.IRON_SKIN_POTION, (20 * 30))));
		}
	}
	
	@SubscribeEvent
	public static void registerOvenRecipes(RegistryEvent.Register<OvenRecipe> event) {
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit"), new ItemStack(Blocks.SAPLING, 1), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.oak_spirit), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "oak_spirit_alt"), new ItemStack(Blocks.SAPLING, 1, 5), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.oak_spirit), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "spruce_heart"), new ItemStack(Blocks.SAPLING, 1, 1), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.spruce_heart), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "birch_soul"), new ItemStack(Blocks.SAPLING, 1, 2), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.birch_soul), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil"), new ItemStack(Blocks.SAPLING, 1, 3), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.cloudy_oil), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "acacia_resin"), new ItemStack(Blocks.SAPLING, 1, 4), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.acacia_resin), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ebb_of_death"), new ItemStack(ModObjects.cypress_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.ebb_of_death), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "droplet_of_wisdom"), new ItemStack(ModObjects.elder_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.droplet_of_wisdom), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "liquid_witchcraft"), new ItemStack(ModObjects.mandrake_root), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.liquid_witchcraft), 0.75f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "essence_of_vitality"), new ItemStack(ModObjects.juniper_sapling), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(ModObjects.essence_of_vitality), 0.75f));
		
		if (Loader.isModLoaded("dynamictrees")) {
			for (TreeFamilyVanilla family : ModTrees.baseFamilies) {
				Species species = family.getCommonSpecies();
				String name = species.getSaplingName().toString().toLowerCase();
				name = name.substring(name.indexOf(":") + 1);
				event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, name), species.getSeedStack(1), new ItemStack(ModObjects.wood_ash, 4), new ItemStack(name.contains("oak") ? ModObjects.oak_spirit : name.contains("spruce") ? ModObjects.spruce_heart : name.contains("birch") ? ModObjects.birch_soul : name.contains("acacia") ? ModObjects.acacia_resin : ModObjects.cloudy_oil), 0.75f));
			}
		}
		
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil_alt0"), new ItemStack(Blocks.CACTUS), new ItemStack(Items.DYE, 1, 2), new ItemStack(ModObjects.cloudy_oil), 0.55f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "cloudy_oil_alt1"), new ItemStack(ModObjects.mandrake_root), new ItemStack(ModObjects.wood_ash), new ItemStack(ModObjects.cloudy_oil), 0.85f));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ectoplasm"), new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER), new ItemStack(ModObjects.ectoplasm, 3), 0.65f, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "ectoplasm_alt"), new ItemStack(Items.BONE), new ItemStack(Items.DYE, 1, 15), new ItemStack(ModObjects.ectoplasm), 0.65f, false));
		
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand"), new ItemStack(Items.ENDER_EYE), new ItemStack(Items.BLAZE_POWDER, 1, 0), new ItemStack(ModObjects.dimensional_sand, 2), 0.8f, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand_alt0"), new ItemStack(Items.SHULKER_SHELL), new ItemStack(Items.CHORUS_FRUIT_POPPED, 1, 0), new ItemStack(ModObjects.dimensional_sand, 4), 1, false));
		event.getRegistry().register(new OvenRecipe(new ResourceLocation(Bewitchment.MODID, "dimensional_sand_alt1"), new ItemStack(Items.CHORUS_FRUIT), new ItemStack(Items.CHORUS_FRUIT_POPPED), new ItemStack(ModObjects.dimensional_sand, 2), 0.75f, false));
	}
	
	@SubscribeEvent
	public static void registerDistilleryRecipes(RegistryEvent.Register<DistilleryRecipe> event) {
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "bottled_frostfire"), Arrays.asList(Util.get(Items.GLASS_BOTTLE), Util.get(ModObjects.perpetual_ice), Util.get(ModObjects.cleansing_balm), Util.get(ModObjects.fiery_unguent)), Arrays.asList(new ItemStack(ModObjects.bottled_frostfire), new ItemStack(ModObjects.empty_jar, 2))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "cleansing_balm"), Arrays.asList(Util.get(ModObjects.acacia_resin), Util.get("cropWhiteSage"), Util.get("salt"), Util.get("cropGarlic")), Arrays.asList(new ItemStack(ModObjects.cleansing_balm), new ItemStack(ModObjects.wood_ash))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "demonic_elixir"), Arrays.asList(Util.get(ModObjects.demon_heart), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.empty_jar), Util.get(ModObjects.empty_jar)), Collections.singletonList(new ItemStack(ModObjects.demonic_elixir, 3))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "fiery_unguent"), Arrays.asList(Util.get(Items.BLAZE_POWDER), Util.get(ModObjects.cloudy_oil), Util.get("wax")), Collections.singletonList(new ItemStack(ModObjects.fiery_unguent))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "blood_from_hearts"), Arrays.asList(Util.get(ModObjects.heart), Util.get(Items.GLASS_BOTTLE), Util.get(Items.GLASS_BOTTLE), Util.get(Items.GLASS_BOTTLE)), Collections.singletonList(new ItemStack(ModObjects.bottle_of_blood, 3))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "bottled_hellfire"), Arrays.asList(Util.get(Items.GLASS_BOTTLE), Util.get("wax"), Util.get("wax"), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.dragons_blood_resin)), Arrays.asList(new ItemStack(ModObjects.bottled_hellfire), new ItemStack(ModObjects.empty_jar, 1))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "swirl_of_depths"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get(Items.FISH), Util.get(ModObjects.coquina), Util.get(new ItemStack(Items.POTIONITEM, 1, 0))), Arrays.asList(new ItemStack(ModObjects.swirl_of_depths), new ItemStack(Items.GLASS_BOTTLE, 1))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "oil_of_vitriol"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get("nuggetIron"), Util.get("gunpowder"), Util.get(ModObjects.dragons_blood_resin)), Arrays.asList(new ItemStack(ModObjects.oil_of_vitriol), new ItemStack(ModObjects.empty_jar))));
		event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "otherworld_tears"), Arrays.asList(Util.get(ModObjects.cloudy_oil), Util.get(ModObjects.dimensional_sand), Util.get("enderpearl"), Util.get("dustGlowstone")), Arrays.asList(new ItemStack(ModObjects.otherworldly_tears), new ItemStack(ModObjects.empty_jar))));
		//event.getRegistry().register(new DistilleryRecipe(new ResourceLocation(Bewitchment.MODID, "aetherial_substance"), Arrays.asList(Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.swirl_of_depths), Util.get(ModObjects.stone_ichore), Util.get(ModObjects.heaven_extract)), Arrays.asList(new ItemStack(ModObjects.aetherial_substance), new ItemStack(ModObjects.empty_jar, 4))));
	}
	
	@SubscribeEvent
	public static void registerSpinningWheelRecipes(RegistryEvent.Register<SpinningWheelRecipe> event) {
		event.getRegistry().register(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "cobweb"), Arrays.asList(Util.get("string"), Util.get("string"), Util.get("string")), Collections.singletonList(new ItemStack(Blocks.WEB))));
		event.getRegistry().register(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "witches_stitching"), Arrays.asList(Util.get("string"), Util.get("string"), Util.get(ModObjects.liquid_witchcraft), Util.get(ModObjects.liquid_witchcraft)), Arrays.asList(new ItemStack(ModObjects.witches_stitching, 2), new ItemStack(ModObjects.empty_jar, 2))));
		event.getRegistry().register(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "diabolical_vein"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.witches_stitching), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.heart)), Arrays.asList(new ItemStack(ModObjects.diabolical_vein, 2), new ItemStack(ModObjects.empty_jar))));
		event.getRegistry().register(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "pure_filament"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.witches_stitching), Util.get(ModObjects.acacia_resin), Util.get("cropWhiteSage")), Arrays.asList(new ItemStack(ModObjects.pure_filament, 2), new ItemStack(ModObjects.empty_jar))));
		event.getRegistry().register(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "sanguine_cloth"), Arrays.asList(Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 15)), Ingredient.fromStacks(new ItemStack(Blocks.WOOL, 1, 15)), Util.get(ModObjects.bottle_of_blood), Util.get(ModObjects.diabolical_vein)), Arrays.asList(new ItemStack(ModObjects.sanguine_cloth, 2), new ItemStack(Items.GLASS_BOTTLE))));
		event.getRegistry().register(new SpinningWheelRecipe(new ResourceLocation(Bewitchment.MODID, "spirit_string"), Arrays.asList(Util.get(ModObjects.witches_stitching), Util.get(ModObjects.witches_stitching), Util.get(ModObjects.ectoplasm), Util.get(ModObjects.spanish_moss)), Collections.singletonList(new ItemStack(ModObjects.spirit_string, 2))));
	}
	
	@SubscribeEvent
	public static void registerFrostfireRecipes(RegistryEvent.Register<FrostfireRecipe> event) {
		event.getRegistry().register(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_ingot"), Util.get("oreIron"), new ItemStack(ModObjects.cold_iron_ingot)));
		if (!Arrays.asList(Util.get("clusterIron").getMatchingStacks()).isEmpty())
			event.getRegistry().register(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_cluster"), Util.get("clusterIron"), new ItemStack(ModObjects.cold_iron_nugget, 18)));
		if (!Arrays.asList(Util.get("dustIron", "gritIron").getMatchingStacks()).isEmpty())
			event.getRegistry().register(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_ingot_alt"), Util.get("dustIron", "gritIron"), new ItemStack(ModObjects.cold_iron_ingot)));
		if (!Arrays.asList(Util.get("dustTinyIron").getMatchingStacks()).isEmpty())
			event.getRegistry().register(new FrostfireRecipe(new ResourceLocation(Bewitchment.MODID, "cold_iron_nugget"), Util.get("dustTinyIron"), new ItemStack(ModObjects.cold_iron_nugget)));
	}
	
	@SubscribeEvent
	public static void registerFortunes(RegistryEvent.Register<Fortune> event) {
		event.getRegistry().register(new FortuneBadLuck());
		event.getRegistry().register(new FortuneGoodLuck());
		event.getRegistry().register(new FortuneIllness());
		event.getRegistry().register(new FortuneVitality());
		event.getRegistry().register(new FortuneMeetPet());
		event.getRegistry().register(new FortuneMeetMerchant());
		event.getRegistry().register(new FortuneMeetDemon());
		event.getRegistry().register(new FortuneMeetFeuerwurm());
		event.getRegistry().register(new FortuneMeetBlaze());
		event.getRegistry().register(new FortuneMeetDireWolf());
		event.getRegistry().register(new FortuneMeetSilverfish());
		event.getRegistry().register(new FortuneMeetWitch());
		event.getRegistry().register(new FortuneMeetZombie());
		event.getRegistry().register(new FortuneDeath());
		event.getRegistry().register(new FortuneDropItem());
		event.getRegistry().register(new FortuneTreasure());
		if (ModConfig.memes.enableCatsAndDogsFortune) event.getRegistry().register(new FortuneCatsAndDogs());
	}
	
	@SubscribeEvent
	public static void registerTarots(RegistryEvent.Register<Tarot> event) {
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "player"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/00player.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return BewitchmentAPI.isWitch(player);
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "witch"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/01witch.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when spells are added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "enderman"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/02enderman.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when end infusion is added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "cat"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/03cat.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when infusions are added
			}
			
			@Override
			public boolean isReversed(EntityPlayer player) {
				return false; //change when infusions are added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "guardian"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/04guardian.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return getNumber(player) > 0;
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return player.getCapability(ExtendedPlayer.CAPABILITY, null).uniqueDefeatedBosses.tagCount();
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "illusioner"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/05illusioner.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return !player.getActivePotionEffects().isEmpty();
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				int max = 0;
				for (PotionEffect effect : player.getActivePotionEffects()) if (effect.getAmplifier() > max) max = effect.getAmplifier();
				return max;
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "companions"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/06companions.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when familiars are added
			}
			
			@Override
			public boolean isReversed(EntityPlayer player) {
				return false; //change when familiars are added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "mounts"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/07mounts.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when familiars are added, and add compat for respawnable pets
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return 0; //change when familiars are added, and add compat for respawnable pets
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "silver_sword"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/08silver_sword.png")) {
			@Override
			public boolean isReversed(EntityPlayer player) {
				return BewitchmentAPI.isWitchHunter(player);
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "evoker"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/09evoker.png")) {
			@Override
			public boolean isReversed(EntityPlayer player) {
				return BewitchmentAPI.isSpectre(player);
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "diamonds"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/10diamonds.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune != null;
			}
			
			@Override
			public boolean isReversed(EntityPlayer player) {
				return isCounted(player) && player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune.isNegative;
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "iron_golem"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/11iron_golem.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when overworld infusion is added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "zombie"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/12zombie.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when protection poppets are added
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return 0; //change when protection poppets are added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "wither_skeleton"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/13wither_skeleton.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when nether infusion is added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "villager"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/14villager.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when covens are added
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return 0; //change when covens are added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "wither"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/15wither.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when pacts are added
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return 0; //change when pacts are added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "ender_dragon"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/16ender_dragon.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return getNumber(player) > 0;
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return player.getCapability(ExtendedPlayer.CAPABILITY, null).mobsKilled / 100;
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "star"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/17star.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when dream infusion is added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "moon"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/18moon.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return BewitchmentAPI.isVampire(player) || BewitchmentAPI.isWerewolf(player);
			}
			
			@Override
			public boolean isReversed(EntityPlayer player) {
				return BewitchmentAPI.isWerewolf(player);
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "sun"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/19sun.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return false; //change when curses are added
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "stronghold"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/20stronghold.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return getNumber(player) > 0;
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return player.getCapability(ExtendedPlayer.CAPABILITY, null).ritualsCast;
			}
		});
		event.getRegistry().register(new Tarot(new ResourceLocation(Bewitchment.MODID, "world"), new ResourceLocation(Bewitchment.MODID, "textures/gui/tarot/21world.png")) {
			@Override
			public boolean isCounted(EntityPlayer player) {
				return getNumber(player) > 0;
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return player.getCapability(ExtendedPlayer.CAPABILITY, null).exploredChunks.tagCount() / 100;
			}
		});
	}
}