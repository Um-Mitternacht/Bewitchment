package com.bewitchment.common.ritual;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Ritual;
import com.bewitchment.common.block.BlockGlyph;
import com.bewitchment.common.block.tile.entity.TileEntityGlyph;
import com.bewitchment.common.item.tool.ItemBoline;
import com.bewitchment.common.world.BiomeChangingUtils;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Arrays;
import java.util.Collections;

/**
 * Original code by Zabi94, modified by Sunconure11 afterwards, with bits stuck on from Ael.
 */
public class RitualBiomeShift extends Ritual {
    public RitualBiomeShift() {
        super(new ResourceLocation(Bewitchment.MODID, "biome_shift"), Arrays.asList(Util.get("treeSapling"), Util.get("logWood"), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.oak_apple_gall), Util.get(ModObjects.pentacle), Util.get(new ItemStack(ModObjects.oak_spirit)), Util.get(new ItemStack(ModObjects.dimensional_sand)), Util.get(new ItemStack(ModObjects.boline))), null, Collections.singletonList(new ItemStack(ModObjects.boline, 1, 0)), 50, 1300, 33, BlockGlyph.ENDER, BlockGlyph.ENDER, BlockGlyph.ENDER);
    }

    @Override
    public void onFinished(World world, BlockPos altarPos, BlockPos effectivePos, EntityPlayer caster, ItemStackHandler inventory) {
        int id = Biome.getIdForBiome(Biomes.PLAINS);
        if (world.getTileEntity(effectivePos) instanceof TileEntityGlyph) {
            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (stack.getItem() instanceof ItemBoline) {
                    id = stack.getTagCompound().getInteger("biome_id");
                    stack.damageItem(25, caster);
                    {
                        //
                        BlockPos.MutableBlockPos Z = new BlockPos.MutableBlockPos();
                        Z.setPos(16, 0, 16);
                        //
                        BlockPos.MutableBlockPos X = new BlockPos.MutableBlockPos();
                        X.setPos(-16, 0, -16);

                        Iterable<BlockPos> pos1 = BlockPos.getAllInBox(Z, X);
                        int finalId = id;
                        pos1.forEach(pos -> {
                            BiomeChangingUtils.setBiome(world, Biome.getBiomeForId(finalId), effectivePos);
                        });
                    }
                }
            }
        }
    }
}
