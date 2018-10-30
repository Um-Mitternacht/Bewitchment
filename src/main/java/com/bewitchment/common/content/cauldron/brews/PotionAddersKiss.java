package com.bewitchment.common.content.cauldron.brews;

import com.bewitchment.common.core.helper.MobHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityRabbit;

//Todo: Make snake venom return glass bottles after being used in the cauldron
public class PotionAddersKiss extends GenericBrewDamageVS {

	public PotionAddersKiss() {
		super("adders_kiss", 0xEDC9AF);
	}

	@Override
	protected boolean shouldAffect(EntityLivingBase entity) {
		String className = entity.getClass().getSimpleName();
		return entity instanceof EntityRabbit || entity instanceof EntitySpider || entity instanceof EntityChicken || className.contains("Rat") || className.contains("Mouse") || className.contains("Hamster") || className.contains("Mole") || className.contains("Blindworm") || className.contains("Frog") || className.contains("Toad") || className.contains("Newt") || className.contains("Salamander") || className.contains("GuineaPig") || className.contains("Cavy") || className.contains("Chick") || className.contains("Chinchilla");
	}

	@Override
	protected float getDamage(int amplifier) {
		return 4 + amplifier * 1.7f;
	}

}
