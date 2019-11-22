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

public class TreeJuniper extends TreeFamily {
    private final BlockSurfaceRoot surfaceRootBlock;

    public TreeJuniper() {
        super(new ResourceLocation(Bewitchment.MODID, "juniper"));
        IBlockState primLog = ModObjects.juniper_wood.getDefaultState();
        this.setPrimitiveLog(primLog, new ItemStack(ModObjects.juniper_wood));
        DynamicTreesCompat.juniperLeavesProperties.setTree(this);
        this.surfaceRootBlock = new BlockSurfaceRoot(Material.WOOD, this.getName() + "root");
    }

    public void createSpecies() {
        this.setCommonSpecies(new SpeciesJuniper(this));
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

    public class SpeciesJuniper extends Species {
        SpeciesJuniper(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, DynamicTreesCompat.juniperLeavesProperties);
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
