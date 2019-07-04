package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.BlockCandleBase;
import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import com.bewitchment.common.entity.misc.ModEntityPotion;
import com.bewitchment.common.entity.misc.ModEntityTippedArrow;
import net.minecraft.block.*;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.oredict.OreDictionary;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Mod.EventBusSubscriber
public class ModRegistries {
	public static final Map<Item, String[]> ORE_DICTIONARY_ENTRIES = new HashMap<>();
	public static final Map<Item, List<Predicate<ItemStack>>> MODEL_PREDICATES = new HashMap<>();
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
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
		Bewitchment.proxy.ignoreProperty(ModObjects.yew_sapling, BlockSapling.STAGE, BlockSapling.TYPE);
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.yew_leaves, BlockLeaves.CHECK_DECAY, BlockLeaves.DECAYABLE);
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.yew_door.door, BlockDoor.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.cypress_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.elder_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.juniper_fence_gate, BlockFenceGate.POWERED);
		Bewitchment.proxy.ignoreProperty(ModObjects.yew_fence_gate, BlockFenceGate.POWERED);
		
		ModObjects.crop_aconitum.setItems(ModObjects.aconitum_seeds, ModObjects.aconitum);
		ModObjects.crop_belladonna.setItems(ModObjects.belladonna_seeds, ModObjects.belladonna);
		ModObjects.crop_garlic.setItems(ModObjects.garlic_seeds, ModObjects.garlic);
		ModObjects.crop_hellebore.setItems(ModObjects.hellebore_seeds, ModObjects.hellebore);
		ModObjects.crop_mandrake.setItems(ModObjects.mandrake_seeds, ModObjects.mandrake_root);
		ModObjects.crop_white_sage.setItems(ModObjects.white_sage_seeds, ModObjects.white_sage);
		ModObjects.crop_wormwood.setItems(ModObjects.wormwood_seeds, ModObjects.wormwood);
		
		OreDictionary.registerOre("gemAll", new ItemStack(Items.QUARTZ));
		OreDictionary.registerOre("gemAll", new ItemStack(Items.DIAMOND));
		OreDictionary.registerOre("gemAll", new ItemStack(Items.EMERALD));
		OreDictionary.registerOre("gemAll", new ItemStack(Items.DYE, 1, EnumDyeColor.BLUE.getDyeDamage()));
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
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/newt"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/owl"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/snake"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/raven"));
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/toad"));
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/black_dog"));
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/hellhound"));
		
		LootTableList.register(new ResourceLocation(Bewitchment.MODID, "entities/demon"));
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
}