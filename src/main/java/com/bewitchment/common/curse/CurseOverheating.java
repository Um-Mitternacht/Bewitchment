package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import com.bewitchment.registry.ModPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;

public class CurseOverheating extends Curse {
    public CurseOverheating() {
        super(new ResourceLocation(Bewitchment.MODID, "overheating"), Arrays.asList(Util.get(ModObjects.oil_of_vitriol), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.tallow), Util.get(Items.BONE), Util.get(Items.BONE), Util.get(ModObjects.taglock)), true, false, CurseCondition.EXIST, 0.001);
    }

    @Override
    public boolean doCurse(Event event, EntityPlayer target) {
        BlockPos pos = target.getPosition();
        if (BiomeDictionary.hasType(target.world.getBiome(pos), BiomeDictionary.Type.HOT)) {
            target.setFire(5);
            target.addPotionEffect(new PotionEffect(ModPotions.hellfire, 200));
        }
        return false;
    }
}
