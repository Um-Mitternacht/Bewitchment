package com.bewitchment.common.curse;

import com.bewitchment.Bewitchment;
import com.bewitchment.Util;
import com.bewitchment.api.registry.Curse;
import com.bewitchment.registry.ModObjects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Arrays;

public class CurseSolarHatred extends Curse {
	public CurseSolarHatred() {
		super(new ResourceLocation(Bewitchment.MODID, "solar_hatred"), Arrays.asList(Util.get(ModObjects.snake_venom), Util.get("nuggetGold"), Util.get(ModObjects.fiery_unguent), Util.get(ModObjects.salt), Util.get(Items.ROTTEN_FLESH), Util.get(ModObjects.taglock)), false, false, CurseCondition.EXIST);
	}
	
	@Override
	public boolean doCurse(Event event, EntityPlayer target) {
		if (!target.world.isRemote && hasSunlight(target)) {
			target.setFire(2);
			target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 1));
			target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 100, 1));
			target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 1));
			return true;
		}
		return false;
	}
	
	private boolean hasSunlight(EntityPlayer player) {
		BlockPos pos = player.getPosition();
		if (player.world.provider.hasSkyLight()) {
			int i = player.world.getLightFor(EnumSkyBlock.SKY, pos) - player.world.getSkylightSubtracted() - 15;
			return i == 0;
		}
		else return false;
	}
}
