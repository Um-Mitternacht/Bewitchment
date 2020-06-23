package com.bewitchment.common.world.gen.structures;

import com.bewitchment.Bewitchment;
import com.bewitchment.common.world.gen.ModWorldGen;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

import java.util.Random;

public class WorldGenMenhir extends WorldGenerator {

    /**
     * type has 5 possible values:
     * 1. default variant1
     * 2. default variant2
     * 3. regular taiga variant
     * 4. megataiga (redwood) variant
     * 5. cold taiga variant
     */

    public WorldGenMenhir() {
        super();
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        int type = getType(worldIn, rand, position);

        WorldServer worldServer = (WorldServer) worldIn;
        MinecraftServer minecraftServer = worldIn.getMinecraftServer();
        TemplateManager templateManager = worldServer.getStructureTemplateManager();
        Template template = templateManager.getTemplate(minecraftServer, new ResourceLocation(Bewitchment.MODID + ":menhir" + type));

        if (ModWorldGen.canSpawnHere(template, worldServer, position)) {
            IBlockState iBlockState = worldIn.getBlockState(position);
            worldIn.notifyBlockUpdate(position, iBlockState, iBlockState, 3);
            PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk(null).setReplacedBlock(null).setIgnoreStructureBlock(false);
            template.addBlocksToWorld(worldIn, position.add(0, -1, 0), placementsettings);
            return true;
        }
        return false;
    }

    private int getType(World world, Random rand, BlockPos position) {
        Biome current = world.getBiome(position);
        if (current == Biomes.TAIGA) return 3;
        if (current == Biomes.REDWOOD_TAIGA) return 4;
        if (current == Biomes.COLD_TAIGA) return 5;
        else return rand.nextInt(1) + 1;
    }
}
