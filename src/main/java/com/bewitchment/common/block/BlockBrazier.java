package com.bewitchment.common.block;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.block.tile.entity.TileEntityBrazier;
import com.bewitchment.common.block.util.ModBlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

@SuppressWarnings({"deprecation", "NullableProblems"})
public class BlockBrazier extends ModBlockContainer {
    public BlockBrazier() {
        super(Bewitchment.instance, "brazier", Material.IRON, SoundType.STONE, 5, 30, "pickaxe", -1);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBrazier();
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(3/16d, 0, 3/16d, 13/16d, 1, 13/16d);
    }
}
