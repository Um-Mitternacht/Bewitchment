package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
import com.bewitchment.api.brew.IBrewEnderTeleport;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This class was created by Arekkuusu on 11/06/2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class EnderInhibitionBrew implements IBrew, IBrewEnderTeleport {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		//NO-OP
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
		return 0x86608E;
	}

	@Override
	public String getName() {
		return "ender_inhibition";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 6);
	}

	@Override
	public void onTeleport(EnderTeleportEvent event, EntityLivingBase entity, double targetX, double targetY, double targetZ, int amplifier) {
		int redo = 5 - amplifier;
		if (redo < 0 || entity.world.rand.nextInt(redo) == 0) {
			event.setCanceled(true);
		}
	}
}
