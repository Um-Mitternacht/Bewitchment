package com.bewitchment.common.brew.brews;

import com.bewitchment.api.cauldron.brew.IBrew;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class NotchedBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		entity.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 400, 1));
		entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 6000, 0));
		entity.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 6000, 0));
		entity.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 2400, 3));
	}

	@Override
	public boolean isBad() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return true;
	}

	@Override
	public int getColor() {
		return 0xA91101;
	}

	@Override
	public String getName() {
		return "notched";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 14);
	}

}
