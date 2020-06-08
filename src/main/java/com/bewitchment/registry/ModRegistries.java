package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.Util;
import com.bewitchment.api.BewitchmentAPI;
import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.*;
import com.bewitchment.common.block.BlockCandleBase;
import com.bewitchment.common.block.BlockDragonsBloodLog;
import com.bewitchment.common.block.BlockStatue;
import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import com.bewitchment.common.block.tile.entity.*;
import com.bewitchment.common.crafting.RecipeDuplicateKey;
import com.bewitchment.common.crafting.RecipeJuniperKeyRing;
import com.bewitchment.common.fortune.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.RegistryBuilder;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

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
		new RegistryBuilder<Incense>().setName(new ResourceLocation(Bewitchment.MODID, "incense")).setType(Incense.class).create();
		new RegistryBuilder<Fortune>().setName(new ResourceLocation(Bewitchment.MODID, "fortune")).setType(Fortune.class).create();
		new RegistryBuilder<Tarot>().setName(new ResourceLocation(Bewitchment.MODID, "tarot")).setType(Tarot.class).create();
		new RegistryBuilder<Curse>().setName(new ResourceLocation(Bewitchment.MODID, "curse")).setType(Curse.class).create();
		new RegistryBuilder<SigilRecipe>().setName(new ResourceLocation(Bewitchment.MODID, "sigil")).setType(SigilRecipe.class).create();
	}
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		GameRegistry.registerTileEntity(TileEntityStatue.class, new ResourceLocation(Bewitchment.MODID, "statue"));
		GameRegistry.registerTileEntity(TileEntityGoddessStatue.class, new ResourceLocation(Bewitchment.MODID, "creative"));
		GameRegistry.registerTileEntity(TileEntityPoppetShelf.class, new ResourceLocation(Bewitchment.MODID, "poppet_shelf"));
		GameRegistry.registerTileEntity(TileEntityDragonsBlood.class, new ResourceLocation(Bewitchment.MODID, "dragons_blood"));
		GameRegistry.registerTileEntity(TileEntitySiphoningFlower.class, new ResourceLocation(Bewitchment.MODID, "siphoning_flower"));
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
		Bewitchment.proxy.ignoreProperty(ModObjects.blue_ink_cap, BlockBushSpreading.TIMES_SPREAD);
		Bewitchment.proxy.ignoreProperty(ModObjects.frostflower, BlockBushSpreading.TIMES_SPREAD);
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
		
		Bewitchment.proxy.ignoreProperty(ModObjects.dragons_blood_wood, BlockDragonsBloodLog.NATURAL);
		
		Bewitchment.proxy.ignoreProperty(ModObjects.filler, BlockStatue.BlockFiller.HEIGHT);
		
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
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/lizard"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/owl"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/snake"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/raven"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/toad"));
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/black_dog"));
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/hellhound"));
		
		for (int i = 0; i < 4; i++) LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/demon" + i));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/imp"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/druden"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/feuerwurm"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/ghost"));
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
		ModRecipes.addRitualRecipe();
		for (Ritual recipe : ModRecipes.ritualRecipes) {
			event.getRegistry().register(recipe);
		}
	}
	
	@SubscribeEvent
	public static void registerCauldronRecipes(RegistryEvent.Register<CauldronRecipe> event) {
		ModRecipes.addCauldronRecipes();
		for (CauldronRecipe recipe : ModRecipes.cauldronRecipes) {
			event.getRegistry().register(recipe);
		}
	}
	
	@SubscribeEvent
	public static void registerBrews(RegistryEvent.Register<Brew> event) {
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "instant_health"), Util.get(Items.APPLE), new PotionEffect(MobEffects.INSTANT_HEALTH, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "instant_damage"), Util.get(Items.SPIDER_EYE), new PotionEffect(MobEffects.INSTANT_DAMAGE, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "regeneration"), Util.get(Items.SPECKLED_MELON), new PotionEffect(MobEffects.REGENERATION, (20 * 20))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "poison"), Util.get(ModObjects.snake_venom, ModObjects.belladonna), s -> s.getItem() == ModObjects.snake_venom, new ItemStack(Items.GLASS_BOTTLE), new PotionEffect(MobEffects.POISON, (20 * 20))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "wither"), Util.get(ModObjects.bottle_of_blood), new ItemStack(Items.GLASS_BOTTLE), new PotionEffect(MobEffects.WITHER, (20 * 15))));
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
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "grace"), Util.get(Items.FEATHER), new PotionEffect(ModPotions.grace, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "shell_armor"), Util.get("coquina"), new PotionEffect(ModPotions.shell_armor, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "deflection"), Util.get(Items.CHORUS_FRUIT_POPPED), new PotionEffect(ModPotions.deflection, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "revealing"), Util.get(Items.ENDER_PEARL, ModObjects.eye_of_old), new PotionEffect(ModPotions.revealing, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "disrobing"), Util.get(ModObjects.tallow), new PotionEffect(ModPotions.disrobing, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "sinking"), Util.get("nuggetGold", "nuggetIron", "nuggetColdIron", "nuggetCopper", "nuggetTin", "nuggetBronze", "nuggetLead"), new PotionEffect(ModPotions.sinking, (20 * 20))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "cursed_leaps"), Util.get(ModObjects.toe_of_frog), new PotionEffect(ModPotions.cursed_leaps, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "fear"), Util.get(ModObjects.demon_heart), new PotionEffect(ModPotions.fear, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "corrosion"), Util.get(ModObjects.oil_of_vitriol), new PotionEffect(ModPotions.corrosion, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "bulk"), Util.get(ModObjects.stone_ichor), new PotionEffect(ModPotions.bulk, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "butterfingers"), Util.get(ModObjects.cloudy_oil), new PotionEffect(ModPotions.butterfingers, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "rhino_hide"), Util.get(Items.DIAMOND), new PotionEffect(ModPotions.rhino_hide, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "paper_skin"), Util.get(Items.PAPER), new PotionEffect(ModPotions.paper_skin, (20 * 30))));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "rage"), Util.get(Items.BONE), new PotionEffect(ModPotions.rage, 1)));
		event.getRegistry().register(new Brew(new ResourceLocation(Bewitchment.MODID, "friendship"), Util.get(Items.COOKIE), new PotionEffect(ModPotions.friendship, 1)));
	}
	
	@SubscribeEvent
	public static void registerOvenRecipes(RegistryEvent.Register<OvenRecipe> event) {
		ModRecipes.addOvenRecipes();
		for (OvenRecipe recipe : ModRecipes.ovenRecipes) {
			event.getRegistry().register(recipe);
		}
	}
	
	@SubscribeEvent
	public static void registerDistilleryRecipes(RegistryEvent.Register<DistilleryRecipe> event) {
		ModRecipes.addDistilleryRecipes();
		for (DistilleryRecipe recipe : ModRecipes.distilleryRecipes) {
			event.getRegistry().register(recipe);
		}
	}
	
	@SubscribeEvent
	public static void registerSpinningWheelRecipes(RegistryEvent.Register<SpinningWheelRecipe> event) {
		ModRecipes.addSpinningWheelRecipes();
		for (SpinningWheelRecipe recipe : ModRecipes.spinningWheelRecipes) {
			event.getRegistry().register(recipe);
		}
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
	public static void registerIncenses(RegistryEvent.Register<Incense> event) {
		event.getRegistry().register(new Incense(new ResourceLocation(Bewitchment.MODID, "vitality"), Arrays.asList(Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.essence_of_vitality), Util.get(ModObjects.catechu_brown), Util.get("nuggetIron")), Arrays.asList(MobEffects.RESISTANCE, MobEffects.STRENGTH), 600));
		event.getRegistry().register(new Incense(new ResourceLocation(Bewitchment.MODID, "deftness"), Arrays.asList(Util.get(ModObjects.dragons_blood_resin), Util.get(Items.SUGAR), Util.get("feather"), Util.get(ModObjects.ectoplasm), Util.get(ModObjects.essence_of_vitality)), Arrays.asList(MobEffects.JUMP_BOOST, MobEffects.SPEED), 600));
		event.getRegistry().register(new Incense(new ResourceLocation(Bewitchment.MODID, "invigorating"), Arrays.asList(Util.get(ModObjects.dragons_blood_resin), Util.get(Items.SUGAR), Util.get(Items.POTATO), Util.get(Blocks.YELLOW_FLOWER), Util.get(ModObjects.juniper_berries)), Arrays.asList(MobEffects.HASTE, MobEffects.ABSORPTION), 600));
		event.getRegistry().register(new Incense(new ResourceLocation(Bewitchment.MODID, "cats_eye"), Arrays.asList(Util.get(ModObjects.dragons_blood_resin), Util.get(Items.GLOWSTONE_DUST), Util.get(Blocks.RED_FLOWER), Util.get(Items.CARROT)), Collections.singletonList(MobEffects.NIGHT_VISION), 600));
		event.getRegistry().register(new Incense(new ResourceLocation(Bewitchment.MODID, "fullness"), Arrays.asList(Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.elderberries), Util.get(ModObjects.salt), Util.get(Items.POTATO), Util.get(Blocks.YELLOW_FLOWER)), Arrays.asList(MobEffects.ABSORPTION, MobEffects.SATURATION), 600));
		event.getRegistry().register(new Incense(new ResourceLocation(Bewitchment.MODID, "intensity"), Arrays.asList(Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.dragons_blood_resin), Util.get(Items.GLOWSTONE_DUST), Util.get(ModObjects.juniper_berries), Util.get(new ItemStack(Items.COAL, 1, 1)), Util.get(ModObjects.salt)), null, 600));
		event.getRegistry().register(new Incense(new ResourceLocation(Bewitchment.MODID, "concentration"), Arrays.asList(Util.get(ModObjects.dragons_blood_resin), Util.get(ModObjects.dragons_blood_resin), Util.get(Items.REDSTONE), Util.get(ModObjects.elderberries), Util.get(new ItemStack(Items.COAL, 1, 1)), Util.get(ModObjects.salt)), null, 600));
	}
	
	@SubscribeEvent
	public static void registerSigilRecipes(RegistryEvent.Register<SigilRecipe> event) {
		event.getRegistry().register(getSigilRecipe("mending", Util.get("dyePurple"), Util.get(ModObjects.essence_of_vitality), Util.get(ModObjects.oak_apple_gall), new ItemStack(ModObjects.sigil_mending)));
		event.getRegistry().register(getSigilRecipe("ruin", Util.get(ModObjects.snake_venom), Util.get(ModObjects.white_sage), Util.get(ModObjects.cleansing_balm), new ItemStack(ModObjects.sigil_ruin)));
		event.getRegistry().register(getSigilRecipe("binding", Util.get("slimeball"), Util.get("string"), Util.get(ModObjects.belladonna), new ItemStack(ModObjects.sigil_binding)));
		event.getRegistry().register(getSigilRecipe("cleansing", Util.get("dyeWhite"), Util.get(ModObjects.cleansing_balm), Util.get("dyeGreen"), new ItemStack(ModObjects.sigil_cleansing)));
		event.getRegistry().register(getSigilRecipe("failure", Util.get("dyeBlack"), Util.get(ModObjects.oil_of_vitriol), Util.get("dyeRed"), new ItemStack(ModObjects.sigil_failure)));
		event.getRegistry().register(getSigilRecipe("purity", Util.get(ModObjects.cleansing_balm), Util.get(ModObjects.juniper_berries), Util.get(ModObjects.white_sage), new ItemStack(ModObjects.sigil_purity)));
		event.getRegistry().register(getSigilRecipe("luck", Util.get("dyeGreen"), Util.get("dyeRed"), Util.get("dyeRed"), new ItemStack(ModObjects.sigil_luck)));
		event.getRegistry().register(getSigilRecipe("battle", Util.get(ModObjects.bottle_of_blood), Util.get(ModObjects.iron_gall_ink), Util.get(ModObjects.bottle_of_blood), new ItemStack(ModObjects.sigil_battle)));
		event.getRegistry().register(getSigilRecipe("disorientation", Util.get(ModObjects.iron_gall_ink), Util.get("dyePurple"), Util.get(ModObjects.ectoplasm), new ItemStack(ModObjects.sigil_disorientation)));
		event.getRegistry().register(getSigilRecipe("shrieking", Util.get("dyeRed"), Util.get(ModObjects.ectoplasm), Util.get(ModObjects.toe_of_frog), new ItemStack(ModObjects.sigil_shrieking)));
		event.getRegistry().register(getSigilRecipe("sentinel", Util.get("dyeWhite"), Util.get(ModObjects.spruce_heart), Util.get(ModObjects.ectoplasm), new ItemStack(ModObjects.sigil_sentinel)));
	}
	
	private static SigilRecipe getSigilRecipe(String name, Ingredient dye, Ingredient cross, Ingredient center, ItemStack result) {
		final Ingredient resin = Util.get(ModObjects.dragons_blood_resin);
		final Ingredient paper = Util.get(Items.PAPER);
		return new SigilRecipe(new ResourceLocation(Bewitchment.MODID, name), Arrays.asList(paper, resin, dye, resin, paper, resin, dye, cross, dye, resin, paper, cross, center, cross, paper, resin, dye, cross, dye, resin, paper, resin, dye, resin, paper), result);
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
		event.getRegistry().register(new FortuneMeetDruden());
		event.getRegistry().register(new FortuneMeetShadowPerson());
		event.getRegistry().register(new FortuneResilience());
		event.getRegistry().register(new FortuneThunderbolt());
		event.getRegistry().register(new FortuneCourage());
		event.getRegistry().register(new FortuneMeetCambion());
		if (ModConfig.memes.enableCatsAndDogsFortune) event.getRegistry().register(new FortuneCatsAndDogs());
	}
	
	@SubscribeEvent
	public static void registerCurses(RegistryEvent.Register<Curse> event) {
		try {
			for (Field f : ModCurses.class.getFields()) {
				Object obj = f.get(null);
				if (obj instanceof Curse) {
					event.getRegistry().register((Curse) obj);
				}
			}
		}
		catch (Exception ignored) {}
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
				return !player.getCapability(ExtendedPlayer.CAPABILITY, null).curses.isEmpty();
			}
			
			@Override
			public int getNumber(EntityPlayer player) {
				return player.getCapability(ExtendedPlayer.CAPABILITY, null).curses.size();
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
	}
	
	@SubscribeEvent
	public static void registerSpecialRecipes(RegistryEvent.Register<IRecipe> event) {
		event.getRegistry().register(new RecipeJuniperKeyRing().setRegistryName(new ResourceLocation(Bewitchment.MODID, "recipe_keyring")));
		event.getRegistry().register(new RecipeDuplicateKey().setRegistryName(new ResourceLocation(Bewitchment.MODID, "recipe_duplicate_key")));
	}
}