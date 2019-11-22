package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.systems.featuregen.*;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

public class TreeCypress extends TreeFamily {
    private final BlockSurfaceRoot surfaceRootBlock;

    public TreeCypress() {
        super(new ResourceLocation(Bewitchment.MODID, "cypress"));
        IBlockState primLog = ModObjects.cypress_wood.getDefaultState();
        this.setPrimitiveLog(primLog, new ItemStack(ModObjects.cypress_wood));
        DynamicTreesCompat.cypressLeavesProperties.setTree(this);
        this.surfaceRootBlock = new BlockSurfaceRoot(Material.WOOD, this.getName() + "root");
    }

    public void createSpecies() {
        this.setCommonSpecies(new SpeciesCypress(this));
    }

    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(this.surfaceRootBlock);
        return super.getRegisterableBlocks(blockList);
    }

    public boolean isThick() {
        return true;
    }

    public BlockSurfaceRoot getSurfaceRoots() {
        return this.surfaceRootBlock;
    }

    public class SpeciesCypress extends Species {
        SpeciesCypress(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, DynamicTreesCompat.cypressLeavesProperties);
            this.setBasicGrowingParameters(0.4F, 14.0F, 4, 4, 1.0F);
            this.envFactor(BiomeDictionary.Type.FOREST, 1.05F);
            this.generateSeed();
            this.setupStandardSeedDropping();
            this.addGenFeature(new FeatureGenClearVolume(6));
            this.addGenFeature(new FeatureGenConiferTopper(DynamicTreesCompat.cypressLeavesProperties));
        }

        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST);
        }

        public boolean isThick() {
            return true;
        }
    }
}
