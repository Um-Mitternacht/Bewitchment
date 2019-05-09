package com.bewitchment.common.block.util;

import com.bewitchment.Util;
import com.bewitchment.common.world.gen.tree.util.WorldGenModTree;
import net.minecraft.block.BlockSapling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("NullableProblems")
public class ModBlockSapling extends BlockSapling {
	private final Class<? extends WorldGenModTree> gen;

	public ModBlockSapling(String name, Class<? extends WorldGenModTree> gen, String... oreDictionaryNames) {
		super();
		Util.registerBlock(this, name, Blocks.SAPLING, oreDictionaryNames);
		this.gen = gen;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> list) {
		list.add(new ItemStack(this));
	}

	@Override
	public void generateTree(World world, BlockPos pos, IBlockState state, Random rand) {
		WorldGenModTree generator = null;
		try {generator = gen.getDeclaredConstructor(boolean.class).newInstance(false);}
		catch (Exception e) {e.printStackTrace();}
		if (generator != null && generator.canSaplingGrow(world, pos)) generator.generate(world, rand, pos);
	}
}