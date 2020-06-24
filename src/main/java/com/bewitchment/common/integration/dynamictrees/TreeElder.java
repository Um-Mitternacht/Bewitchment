package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenClearVolume;
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
		return false;
	}

	public BlockSurfaceRoot getSurfaceRoots() {
		return this.surfaceRootBlock;
	}

	public class SpeciesElder extends Species {
		SpeciesElder(TreeFamily treeFamily) {
			super(treeFamily.getName(), treeFamily, DynamicTreesCompat.elderLeavesProperties);
			this.setBasicGrowingParameters(0.4F, 14.0F, 3, 2, 1.0F);
			this.envFactor(BiomeDictionary.Type.FOREST, 1.05F);
			this.addDropCreator(new DropCreatorFruit(ModObjects.elderberries, 45));
			this.addGenFeature(new FeatureGenClearVolume(6));
		}

		public boolean isThick() {
			return true;
		}

		public boolean isBiomePerfect(Biome biome) {
			return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST);
		}
	}
}
