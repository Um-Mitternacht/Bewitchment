package com.bewitchment.common.brew.brews;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.common.brew.BrewEffect;
import com.bewitchment.common.core.capability.brew.BrewStorageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 24/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class AbsenceBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		BrewStorageHandler.getBrewEffects(entity).stream().map(BrewEffect::getBrew)
				.forEach((brew -> BrewStorageHandler.removeActiveBrew(entity, brew)));
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
		return 0x808080;
	}

	@Override
	public String getName() {
		return "absence";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 4);
	}
}
