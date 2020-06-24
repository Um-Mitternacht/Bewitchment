package com.bewitchment.common.block.plants;

import com.bewitchment.common.block.plants.util.BlockBushSpreading;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/**
 * Created by Joseph on 5/25/2019.
 */
public class BlockTorchwood extends BlockBushSpreading {
    public BlockTorchwood() {
        super("torchwood");
        setLightLevel(0.7f);
    }

    @Override
    public boolean canSustainBush(IBlockState state) {
        return super.canSustainBush(state) || state.getMaterial() == Material.ROCK || state.getMaterial() == Material.GROUND || state.getMaterial() == Material.SAND;
    }
}