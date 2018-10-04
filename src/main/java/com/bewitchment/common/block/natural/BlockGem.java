package com.bewitchment.common.block.natural;

import com.bewitchment.common.block.BlockMod;
import com.bewitchment.common.lib.LibBlockName;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import thaumcraft.api.crafting.IInfusionStabiliser;


@Optional.Interface(iface = "thaumcraft.api.crafting.IInfusionStabiliserr", modid = "thaumcraft")
public class BlockGem extends BlockMod implements IInfusionStabiliser {

    public BlockGem(String id) {
        super(id, Material.ROCK);
        setHardness(5.0F);
    }

    @Optional.Method(modid = "thaumcraft")
    @Override
    public boolean canStabaliseInfusion(World world, BlockPos blockPos) {
        return true;
    }
}
