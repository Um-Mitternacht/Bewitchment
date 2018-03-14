package com.bewitchment.common.brew.brews;

import com.bewitchment.api.cauldron.brew.IBrew;
import com.bewitchment.common.brew.ModBrews;
import com.bewitchment.common.core.capability.brew.BrewStorageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 12/06/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class PathOfTheDeepBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity.moveForward > 0F && (!(entity instanceof EntityPlayer) || !((EntityPlayer) entity).capabilities.isFlying)) {
			if (entity.isInWater()) {
				if (BrewStorageHandler.isBrewActive(entity, ModBrews.PATH_OF_THE_DEEP)) {
					entity.moveRelative(0F, 0.5F, 0.085F, 0.85F);
				}
			}
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
		return 0x59d2ff;
	}

	@Override
	public String getName() {
		return "path_of_the_deep";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 7);
	}
}
