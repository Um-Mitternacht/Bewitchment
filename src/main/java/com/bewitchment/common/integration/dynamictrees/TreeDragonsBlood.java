package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenClearVolume;
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

public class TreeDragonsBlood extends TreeFamily {
	private final BlockSurfaceRoot surfaceRootBlock;

	public TreeDragonsBlood() {
		super(new ResourceLocation(Bewitchment.MODID, "dragonsblood"));
		IBlockState primLog = ModObjects.dragons_blood_wood.getDefaultState();
		this.setPrimitiveLog(primLog, new ItemStack(ModObjects.cypress_wood));
		DynamicTreesCompat.dragonsbloodLeavesProperties.setTree(this);
		this.surfaceRootBlock = new BlockSurfaceRoot(Material.WOOD, this.getName() + "root");
	}

	public void createSpecies() {
		this.setCommonSpecies(new SpeciesDragonsBlood(this));
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

	public class SpeciesDragonsBlood extends Species {
		SpeciesDragonsBlood(TreeFamily treeFamily) {
			super(treeFamily.getName(), treeFamily, DynamicTreesCompat.dragonsbloodLeavesProperties);
			this.setBasicGrowingParameters(0.4F, 14.0F, 4, 4, 1.0F);
			this.envFactor(BiomeDictionary.Type.FOREST, 1.05F);
			this.generateSeed();
			this.setupStandardSeedDropping();
			this.addDropCreator(new DropCreatorResin(10));
			this.addGenFeature(new FeatureGenClearVolume(6));
			this.addGenFeature((new FeatureGenRoots(8)).setScaler(this.getRootScaler()));
		}

		protected BiFunction<Integer, Integer, Integer> getRootScaler() {
			return (inRadius, trunkRadius) -> {
				float scale = MathHelper.clamp(trunkRadius >= 8 ? (float) trunkRadius / 20.0F : 0.0F, 0.0F, 1.0F);
				return (int) ((float) inRadius * scale);
			};
		}

		public boolean isThick() {
			return true;
		}

		public boolean isBiomePerfect(Biome biome) {
			return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST);
		}
	}
}
