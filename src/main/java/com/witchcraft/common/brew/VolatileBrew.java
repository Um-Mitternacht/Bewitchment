package com.witchcraft.common.brew;

import com.witchcraft.api.brew.IBrew;
import com.witchcraft.api.brew.IBrewAttack;
import com.witchcraft.common.core.capability.brew.BrewStorageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class VolatileBrew implements IBrew, IBrewAttack {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//Todo: Add explosions. Come, now, and walk the path of explosions with me!
		//FIXME: KASUMAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!
	}

	@Override
	public boolean isBad() {
		return true;
	}

	@Override
	public boolean isInstant() {
		return false;
	}

	@Override
	public int getColor() {
		return 0xFF3800;
	}

	@Override
	public String getName() {
		return "volatility";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 5);
	}

	@Override
	public void onAttack(LivingAttackEvent event, DamageSource source, EntityLivingBase affected, int amplifier) {
		if (amplifier >= 3) {
			if (!source.isExplosion()) {
				BrewStorageHandler.removeActiveBrew(affected, this);
				affected.world.createExplosion(source.getImmediateSource(), affected.posX, affected.posY + 1.2D, affected.posZ, amplifier + 3, true);

			}
		}

		if (amplifier == 2) {
			if (!source.isExplosion()) {
				BrewStorageHandler.removeActiveBrew(affected, this);
				affected.world.createExplosion(source.getImmediateSource(), affected.posX, affected.posY + 0.7D, affected.posZ, amplifier + 2, true);

			}
		}
		if (!source.isExplosion()) {
			BrewStorageHandler.removeActiveBrew(affected, this);
			affected.world.createExplosion(source.getImmediateSource(), affected.posX, affected.posY + 0.5D, affected.posZ, amplifier + 1, true);
		}
	}
}