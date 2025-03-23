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
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Random;

public class ModBlockSapling extends BlockSapling {
    private final WorldGenModTree generator;

    public ModBlockSapling(String name, WorldGenModTree generator, String... oreDictionaryNames) {
        super();
        Util.registerBlock(this, name, Blocks.SAPLING, oreDictionaryNames);
        this.generator = generator;
    }

    @Override
    public void generateTree(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull Random rand) {
        if (TerrainGen.saplingGrowTree(world, rand, pos) && generator != null && generator.canSaplingGrow(world, pos))
            generator.generate(world, rand, pos);
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> list) {
        list.add(new ItemStack(this));
    }
}