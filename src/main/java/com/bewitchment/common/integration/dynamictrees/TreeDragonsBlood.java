package com.bewitchment.common.integration.dynamictrees;

import com.bewitchment.Bewitchment;
import com.bewitchment.registry.ModObjects;
import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.systems.GrowSignal;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenClearVolume;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenRoots;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;
import java.util.function.BiFunction;

public class TreeDragonsBlood extends TreeFamily {
	private final BlockSurfaceRoot surfaceRootBlock;
	
	public TreeDragonsBlood() {
		super(new ResourceLocation(Bewitchment.MODID, "dragonsblood"));
		IBlockState primLog = ModObjects.dragons_blood_wood.getDefaultState();
		this.setPrimitiveLog(primLog, new ItemStack(ModObjects.dragons_blood_wood));
		DynamicTreesCompat.dragonsbloodLeavesProperties.setTree(this);
		this.surfaceRootBlock = new BlockSurfaceRoot(Material.WOOD, this.getName() + "root");
	}
	
	@Override
	public void createSpecies() {
		this.setCommonSpecies(new SpeciesDragonsBlood(this));
	}
	
	@Override
	public List<Block> getRegisterableBlocks(List<Block> blockList) {
		blockList.add(this.surfaceRootBlock);
		return super.getRegisterableBlocks(blockList);
	}
	
	@Override
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
			this.addDropCreator(new DropCreatorFruit(ModObjects.dragons_blood_resin, 10));
			this.addGenFeature(new FeatureGenClearVolume(6));
			this.addGenFeature((new FeatureGenRoots(7)).setScaler(this.getRootScaler()));
		}
		
		protected BiFunction<Integer, Integer, Integer> getRootScaler() {
			return (inRadius, trunkRadius) -> {
				float scale = MathHelper.clamp(trunkRadius >= 8 ? (float) trunkRadius / 20.0F : 0.0F, 0.0F, 1.0F);
				return (int) ((float) inRadius * scale);
			};
		}
		
		@Override
		public boolean isThick() {
			return false;
		}
		
		@Override
		protected int[] customDirectionManipulation(World world, BlockPos pos, int radius, GrowSignal signal, int[] probMap) {
			EnumFacing originDir = signal.dir.getOpposite();
			
			probMap[0] = 0; // disallow down
			
			if (signal.energy < 3.5 && signal.energy > 1) {
				probMap[1] = 1;
				probMap[2] = probMap[3] = probMap[4] = probMap[5] = 0;
			}
			else {
				float r = Math.max(Math.abs(signal.delta.getX()), Math.abs(signal.delta.getZ()));
				
				probMap[2] = probMap[3] = probMap[4] = probMap[5] = 3;
				probMap[1] = 1 + (int) (r * 2.5);
				
				if (signal.delta.getZ() > 0) probMap[2] = 0;
				if (signal.delta.getZ() < 0) probMap[3] = 0;
				if (signal.delta.getX() > 0) probMap[4] = 0;
				if (signal.delta.getX() < 0) probMap[5] = 0;
				
				probMap[originDir.ordinal()] = 0; // Disable the direction we came from
				probMap[signal.dir.ordinal()] += signal.dir == EnumFacing.UP ? (int) ((r - 1.75) * 1.5f) : 0;
			}
			
			return probMap;
		}
		
		@Override
		protected EnumFacing newDirectionSelected(EnumFacing newDir, GrowSignal signal) {
			if (newDir != EnumFacing.UP) {
				signal.energy += 0.75f;
			}
			if (newDir == EnumFacing.UP && signal.dir != EnumFacing.UP) {
				signal.energy += (Math.max(Math.abs(signal.delta.getX()), Math.abs(signal.delta.getZ())) - 2f) * 1.5f;
			}
			return newDir;
		}
		
		public boolean isBiomePerfect(Biome biome) {
			return BiomeDictionary.hasType(biome, BiomeDictionary.Type.FOREST) || BiomeDictionary.hasType(biome, BiomeDictionary.Type.SAVANNA);
		}
	}
}
