package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class OutcastsShameBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (amplifier >= 3) {
			if (entity instanceof EntityWitch) {
				entity.setFire(500);
				entity.attackEntityFrom(DamageSource.MAGIC, 20);
			} else if (entity.getCreatureAttribute() == EnumCreatureAttribute.ILLAGER) {
				entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 1500, 0));
				entity.attackEntityFrom(DamageSource.MAGIC, 20);
			}
		} else if (amplifier == 2 && entity.getCreatureAttribute() == EnumCreatureAttribute.ILLAGER || entity instanceof EntityWitch) {
			entity.attackEntityFrom(DamageSource.MAGIC, 16);
		} else if (entity.getCreatureAttribute() == EnumCreatureAttribute.ILLAGER) {
			entity.attackEntityFrom(DamageSource.MAGIC, 10);
		}
	}

	@Override
	public boolean isBad() {
		return false;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getColor() {
		return 0x8A3324;
	}

	@Override
	public String getName() {
		return "outcasts_shame";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 14);
	}

}
