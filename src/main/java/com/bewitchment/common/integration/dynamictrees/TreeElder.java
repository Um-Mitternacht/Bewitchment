package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenClearVolume;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenFlareBottom;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenMound;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenRoots;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.function.BiFunction;

public class TreeElder extends TreeFamily {
    private final BlockSurfaceRoot surfaceRootBlock;

    public TreeElder() {
        super(new ResourceLocation(Bewitchment.MODID, "elder"));
        IBlockState primLog = ModObjects.elder_wood.getDefaultState();
        this.setPrimitiveLog(primLog, new ItemStack(ModObjects.elder_wood));
        DynamicTreesCompat.elderLeavesProperties.setTree(this);
        this.surfaceRootBlock = new BlockSurfaceRoot(Material.WOOD, this.getName() + "root");
    }

    public void createSpecies() {
        this.setCommonSpecies(new SpeciesElder(this));
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

    public class SpeciesElder extends Species {
        SpeciesElder(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, DynamicTreesCompat.elderLeavesProperties);
            this.setBasicGrowingParameters(0.4F, 14.0F, 4, 4, 1.0F);
            this.envFactor(BiomeDictionary.Type.FOREST, 1.05F);
            this.generateSeed();
            this.setupStandardSeedDropping();
            this.addGenFeature(new FeatureGenClearVolume(6));
            this.addGenFeature(new FeatureGenFlareBottom());
            this.addGenFeature(new FeatureGenMound(5));
            this.addGenFeature((new FeatureGenRoots(11)).setScaler(this.getRootScaler()));
        }

        protected BiFunction<Integer, Integer, Integer> getRootScaler() {
            return (inRadius, trunkRadius) -> {
                float scale = MathHelper.clamp(trunkRadius >= 11 ? (float)trunkRadius / 20.0F : 0.0F, 0.0F, 1.0F);
                return (int)((float)inRadius * scale);
            };
        }

        public boolean isBiomePerfect(Biome biome) {
            return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST);
        }

        public boolean isThick() {
            return true;
        }
    }
}
