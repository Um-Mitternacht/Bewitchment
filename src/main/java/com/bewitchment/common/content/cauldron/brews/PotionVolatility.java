package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.api.cauldron.DefaultModifiers;
import com.bewitchment.api.cauldron.IBrewModifierList;
import com.bewitchment.common.content.cauldron.BrewMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class PotionVolatility extends BrewMod {

	private static final Random rand = new Random();

	public PotionVolatility() {
		super("volatility", true, 0xFF3800, false, 20 * 45);
		this.setIconIndex(6, 1);
	}

	@Override
	public boolean isReady(int duration, int amplifier) {
		return rand.nextInt(100) == 0;
	}

	@Override
	public void performEffect(EntityLivingBase entity, int amplifier) {
		entity.world.newExplosion(null, entity.posX, entity.posY + entity.height, entity.posZ, (1 + amplifier) / 2f, false, false);
	}

	@Override
	public void applyInWorld(World world, BlockPos pos, EnumFacing side, IBrewModifierList modifiers, EntityLivingBase thrower) {
		int power = modifiers.getLevel(DefaultModifiers.POWER).orElse(0);
		world.newExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (1 + power) / 2f, power >= 5, power >= 2);
	}

}
