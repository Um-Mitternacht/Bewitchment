package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.ModConfig;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.api.WorldGenRegistry;
import com.ferreusveritas.dynamictrees.api.client.ModelHelper;
import com.ferreusveritas.dynamictrees.api.treedata.ILeavesProperties;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.blocks.LeavesProperties;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;

public class DynamicTreesCompat {
    public static ILeavesProperties cypressLeavesProperties;
    public static ILeavesProperties elderLeavesProperties;
    public static ILeavesProperties juniperLeavesProperties;
    public static ILeavesProperties dragonsbloodLeavesProperties;
    public static TreeFamily cypressTree;
    public static TreeFamily elderTree;
    public static TreeFamily juniperTree;
    public static TreeFamily dragonsbloodTree;

    public DynamicTreesCompat() {
    }

    @Optional.Method(modid = "dynamictrees")
    @SubscribeEvent
    public static void registerDataBasePopulators(WorldGenRegistry.BiomeDataBasePopulatorRegistryEvent event) {
        event.register(new BiomeDataBasePopulator());
    }

    public static void preInit() {
        IForgeRegistry<Block> blockRegistry = GameRegistry.findRegistry(Block.class);
        IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);
        cypressLeavesProperties = new LeavesProperties(ModObjects.cypress_leaves.getDefaultState(), new ItemStack(ModObjects.cypress_leaves));
        elderLeavesProperties = new LeavesProperties(ModObjects.elder_leaves.getDefaultState(), new ItemStack(ModObjects.elder_leaves));
        juniperLeavesProperties = new LeavesProperties(ModObjects.juniper_leaves.getDefaultState(), new ItemStack(ModObjects.juniper_leaves));
        dragonsbloodLeavesProperties = new LeavesProperties(ModObjects.dragons_blood_leaves.getDefaultState(), new ItemStack(ModObjects.dragons_blood_leaves));
        LeavesPaging.getLeavesBlockForSequence(Bewitchment.MODID, 0, cypressLeavesProperties);
        LeavesPaging.getLeavesBlockForSequence(Bewitchment.MODID, 1, elderLeavesProperties);
        LeavesPaging.getLeavesBlockForSequence(Bewitchment.MODID, 2, juniperLeavesProperties);
        LeavesPaging.getLeavesBlockForSequence(Bewitchment.MODID, 3, dragonsbloodLeavesProperties);
        cypressTree = new TreeCypress();
        elderTree = new TreeElder();
        juniperTree = new TreeJuniper();
        dragonsbloodTree = new TreeDragonsBlood();
        cypressTree.registerSpecies(Species.REGISTRY);
        elderTree.registerSpecies(Species.REGISTRY);
        juniperTree.registerSpecies(Species.REGISTRY);
        dragonsbloodTree.registerSpecies(Species.REGISTRY);
        ArrayList<Block> treeBlocks = new ArrayList<>();
        cypressTree.getRegisterableBlocks(treeBlocks);
        elderTree.getRegisterableBlocks(treeBlocks);
        juniperTree.getRegisterableBlocks(treeBlocks);
        dragonsbloodTree.getRegisterableBlocks(treeBlocks);
        treeBlocks.addAll(LeavesPaging.getLeavesMapForModId(Bewitchment.MODID).values());
        blockRegistry.registerAll(treeBlocks.toArray(new Block[treeBlocks.size()]));
        ArrayList<Item> treeItems = new ArrayList<>();
        cypressTree.getCommonSpecies().getSeed().ifValid(treeItems::add);
        elderTree.getCommonSpecies().getSeed().ifValid(treeItems::add);
        juniperTree.getCommonSpecies().getSeed().ifValid(treeItems::add);
        dragonsbloodTree.getCommonSpecies().getSeed().ifValid(treeItems::add);
        itemRegistry.registerAll(treeItems.toArray(new Item[treeItems.size()]));
        if (ModConfig.compat.replaceSapling) {
            MinecraftForge.EVENT_BUS.register(new SaplingReplacer());
        }
    }

    public static void init() {
    }

    @SideOnly(Side.CLIENT)
    public static void clientPreInit() {
        ModelHelper.regModel(cypressTree);
        ModelHelper.regModel(elderTree);
        ModelHelper.regModel(juniperTree);
        ModelHelper.regModel(dragonsbloodTree);
        LeavesPaging.getLeavesMapForModId(Bewitchment.MODID).forEach((key, leaves) -> {
            ModelLoader.setCustomStateMapper(leaves, (new StateMap.Builder()).ignore(new IProperty[]{BlockLeaves.DECAYABLE}).build());
        });
    }

    public static boolean replaceWorldGen() {
        return ModConfig.compat.genDynamic && WorldGenRegistry.isWorldGenEnabled();
    }
}
