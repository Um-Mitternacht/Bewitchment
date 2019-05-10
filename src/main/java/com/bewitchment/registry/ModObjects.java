package com.bewitchment.registry;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.common.block.BlockSaltBarrier;
import com.bewitchment.common.block.util.*;
import com.bewitchment.common.item.ItemSalt;
import com.bewitchment.common.item.food.ItemGarlic;
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
import net.minecraftforge.common.util.EnumHelper;

@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions"})
public class ModObjects {
	public static final ItemArmor.ArmorMaterial ARMOR_SILVER = EnumHelper.addArmorMaterial("silver", Bewitchment.MODID + ":silver", 11, new int[]{2, 4, 5, 2}, 25, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0);
	public static final ItemArmor.ArmorMaterial ARMOR_COLD_IRON = EnumHelper.addArmorMaterial("cold_iron", Bewitchment.MODID + ":cold_iron", 18, new int[]{2, 6, 7, 2}, 9, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0);

	public static final Item.ToolMaterial TOOL_SILVER = EnumHelper.addToolMaterial("silver", 1, 131, 12, 1.5f, 22);
	public static final Item.ToolMaterial TOOL_COLD_IRON = EnumHelper.addToolMaterial("cold_iron", 2, 425, 7, 2.5f, 14);

	public static final ModBlockCrops crop_aconitum = new ModBlockCrops("crop_aconitum");
	public static final ModBlockCrops crop_belladonna = new ModBlockCrops("crop_belladonna");
	public static final ModBlockCrops crop_garlic = new ModBlockCrops("crop_garlic");
	public static final ModBlockCrops crop_hellebore = new ModBlockCrops("crop_hellebore");
	public static final ModBlockCrops crop_mandrake = new ModBlockCrops("crop_mandrake");
	public static final ModBlockCrops crop_white_sage = new ModBlockCrops("crop_white_sage");
	public static final ModBlockCrops crop_wormwood = new ModBlockCrops("crop_wormwood");
	public static final Block salt_barrier = new BlockSaltBarrier();

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

	public static final Block cypress_sapling = new ModBlockSapling("cypress_sapling", WorldGenCypressTree.class, "treeSapling");
	public static final Block elder_sapling = new ModBlockSapling("elder_sapling", WorldGenElderTree.class, "treeSapling");
	public static final Block juniper_sapling = new ModBlockSapling("juniper_sapling", WorldGenJuniperTree.class, "treeSapling");
	public static final Block yew_sapling = new ModBlockSapling("yew_sapling", WorldGenYewTree.class, "treeSapling");
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

	public static final Item[] silver_armor = registerArmor(ARMOR_SILVER);
	public static final Item[] cold_iron_armor = registerArmor(ARMOR_COLD_IRON);
	public static final Item[] silver_tools = registerTools(TOOL_SILVER);
	public static final Item[] cold_iron_tools = registerTools(TOOL_COLD_IRON);

	public static final Item amethyst = Util.registerItem("amethyst", "gemAmethyst", "gemAll");
	public static final Item garnet = Util.registerItem("garnet", "gemGarnet", "gemAll");
	public static final Item moonstone = Util.registerItem("moonstone", "gemMoonstone", "gemAll");
	public static final Item silver_ingot = Util.registerItem("silver_ingot", "ingotSilver");
	public static final Item cold_iron_ingot = Util.registerItem("cold_iron_ingot", "ingotColdIron");
	public static final Item silver_nugget = Util.registerItem("silver_nugget", "nuggetSilver");
	public static final Item cold_iron_nugget = Util.registerItem("cold_iron_nugget", "nuggetColdIron");
	public static final Item salt = new ItemSalt();

	public static final Item aconitum = Util.registerItem("aconitum", "cropAconitum");
	public static final Item belladonna = Util.registerItem("belladonna", "cropBelladonna");
	public static final Item garlic = new ItemGarlic();
	public static final Item hellebore = Util.registerItem("hellebore", "cropHellebore");
	public static final Item mandrake_root = Util.registerItem("mandrake_root", "cropMandrake");
	public static final Item white_sage = Util.registerItem("white_sage", "cropWhiteSage");
	public static final Item wormwood = Util.registerItem("wormwood", "cropWormwood");

	public static final Item aconitum_seeds = Util.registerItem(new ItemSeeds(crop_aconitum, Blocks.FARMLAND), "aconitum_seeds");
	public static final Item belladonna_seeds = Util.registerItem(new ItemSeeds(crop_belladonna, Blocks.FARMLAND), "belladonna_seeds");
	public static final Item garlic_seeds = Util.registerItem(new ItemSeeds(crop_garlic, Blocks.FARMLAND), "garlic_seeds");
	public static final Item hellebore_seeds = Util.registerItem(new ItemSeeds(crop_hellebore, Blocks.FARMLAND), "hellebore_seeds");
	public static final Item mandrake_seeds = Util.registerItem(new ItemSeeds(crop_mandrake, Blocks.FARMLAND), "mandrake_seeds");
	public static final Item white_sage_seeds = Util.registerItem(new ItemSeeds(crop_white_sage, Blocks.FARMLAND), "white_sage_seeds");
	public static final Item wormwood_seeds = Util.registerItem(new ItemSeeds(crop_wormwood, Blocks.FARMLAND), "wormwood_seeds");

	public static final Item oak_apple_gall = Util.registerItem("oak_apple_gall");
	public static final Item elderberries = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 100), 0.1f), "elderberries");
	public static final Item juniper_berries = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 100), 0.1f), "juniper_berries");
	public static final Item yew_aril = Util.registerItem(new ItemFood(1, 0.5f, false).setPotionEffect(new PotionEffect(MobEffects.POISON, 100), 0.1f), "yew_aril");

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

	private static Item[] registerArmor(ItemArmor.ArmorMaterial mat) {
		Item[] ret = new Item[4];
		String name = mat.name();
		ret[0] = Util.registerItem(new ItemArmor(mat, 0, EntityEquipmentSlot.HEAD), name + "_helmet");
		ret[1] = Util.registerItem(new ItemArmor(mat, 0, EntityEquipmentSlot.CHEST), name + "_chestplate");
		ret[2] = Util.registerItem(new ItemArmor(mat, 0, EntityEquipmentSlot.LEGS), name + "_leggings");
		ret[3] = Util.registerItem(new ItemArmor(mat, 0, EntityEquipmentSlot.FEET), name + "_boots");
		return ret;
	}

	private static Item[] registerTools(Item.ToolMaterial mat) {
		Item[] ret = new Item[5];
		String name = mat.name();
		ret[0] = Util.registerItem(new ItemSword(mat), name + "_sword");
		ret[1] = Util.registerItem(new ModItemPickaxe(mat), name + "_pickaxe");
		ret[2] = Util.registerItem(new ModItemAxe(mat), name + "_axe");
		ret[3] = Util.registerItem(new ItemSpade(mat), name + "_shovel");
		ret[4] = Util.registerItem(new ItemHoe(mat), name + "_hoe");
		return ret;
	}
}