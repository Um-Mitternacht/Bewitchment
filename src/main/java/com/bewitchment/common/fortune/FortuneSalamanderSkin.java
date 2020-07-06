package com.bewitchment.common.fortune;

import com.bewitchment.Bewitchment;
import com.bewitchment.api.registry.Fortune;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.BiomeDictionary;

public class FortuneSalamanderSkin extends Fortune {
	public FortuneSalamanderSkin() {
		super(new ResourceLocation(Bewitchment.MODID, "salamander_skin"), false, 66, 6666);
	}

	@Override
	public boolean apply(EntityPlayer player) {
		BlockPos pos = player.getPosition();
		if (BiomeDictionary.hasType(player.world.getBiome(pos), BiomeDictionary.Type.NETHER)) {
			if (player.world.provider.getDimensionType() == DimensionType.NETHER) {
				player.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 3600, 0, false, false));
				return true;
			}
		}
		return false;
	}
}