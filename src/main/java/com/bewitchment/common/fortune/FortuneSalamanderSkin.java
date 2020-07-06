package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.BiomeDictionary;

public class FortuneSalamanderSkin extends Fortune {
	public FortuneSalamanderSkin() {
		super(new ResourceLocation(Bewitchment.MODID, "salamander_skin"), false, (60), (60 * 10));
	}

	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = player.getPosition();
		if (BiomeDictionary.hasType(player.world.getBiome(pos), BiomeDictionary.Type.NETHER)) {
			player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3600, 0, false, false));
			return true;
		}
		return false;
	}
}