package com.bewitchment.api.recipe;

import com.bewitchment.common.brew.BrewEffect;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.MathHelper;

import java.util.List;

/**
 * This class was created by Arekkuusu on 30/05/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BrewSimpleModifier implements IBrewModifier {

	private final int duration;
	private final int amplifier;

	public BrewSimpleModifier(int duration, int amplifier) {
		this.duration = duration;
		this.amplifier = amplifier;
	}

	@Override
	public boolean apply(List<Object> brews, Object current) {
		if (current instanceof PotionEffect) {
			PotionEffect effect = (PotionEffect) current;
			if (effect.getDuration() < 9600) {
				int hue = MathHelper.clamp(effect.getDuration() + duration, 0, 9600);
				effect.combine(new PotionEffect(effect.getPotion(), hue, effect.getAmplifier()));
			}
			if (effect.getAmplifier() < 3) {
				int hue = MathHelper.clamp(effect.getAmplifier() + amplifier, 0, 3);
				effect.combine(new PotionEffect(effect.getPotion(), effect.getDuration(), hue));
			}
		} else if (current instanceof BrewEffect) {
			BrewEffect effect = (BrewEffect) current;
			if (effect.getDuration() < 9600) {
				int hue = MathHelper.clamp(effect.getDuration() + duration, 0, 9600);
				effect.setDuration(hue);
			}
			if (effect.getAmplifier() < 3) {
				int hue = MathHelper.clamp(effect.getAmplifier() + amplifier, 0, 3);
				effect.setAmplifier(hue);
			}
		}
		return true;
	}
}
