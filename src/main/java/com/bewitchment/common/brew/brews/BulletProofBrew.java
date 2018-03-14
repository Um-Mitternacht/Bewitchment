package com.bewitchment.common.brew.brews;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.brew.special.IBrewHurt;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 23/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class BulletProofBrew implements IBrew, IBrewHurt {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO - OP
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
		return 0x2a3439;
	}

	@Override
	public String getName() {
		return "bulletproof";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 14);
	}

	@Override
	public void onHurt(LivingHurtEvent event, DamageSource source, EntityLivingBase affected, int amplifier) {
		Entity attacker = source.getImmediateSource();
		if (attacker != null && source.isProjectile()) {
			event.setCanceled(true);
		}
	}
}
