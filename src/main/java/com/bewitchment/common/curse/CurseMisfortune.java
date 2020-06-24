package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CurseMisfortune extends Curse {
    public CurseMisfortune() {
        super(new ResourceLocation(Bewitchment.MODID, "misfortune"), Arrays.asList(Util.get(ModObjects.oil_of_vitriol), Util.get(ModObjects.elderberries), Util.get(ModObjects.elderberries), Util.get(ModObjects.belladonna), Util.get(ModObjects.snake_venom), Util.get(ModObjects.ravens_feather), Util.get(ModObjects.taglock)), true, false, CurseCondition.EXIST, 0.0005);
    }

    @Override
    public boolean doCurse(Event event, EntityPlayer target) {
        Random rand = target.getRNG();
        target.addPotionEffect(new PotionEffect(getRandomNegativePotion(rand), (rand.nextInt(20) + 10) * 20, rand.nextInt(1)));
        return false;
    }

    private Potion getRandomNegativePotion(Random rand) {
        List<Potion> potionList = GameRegistry.findRegistry(Potion.class).getValuesCollection().stream().filter(Potion::isBadEffect).filter(p -> !p.isInstant()).filter(p -> p != MobEffects.WITHER).filter(p -> p != MobEffects.POISON).collect(Collectors.toList());
        return potionList.get(rand.nextInt(potionList.size()));
    }
}
