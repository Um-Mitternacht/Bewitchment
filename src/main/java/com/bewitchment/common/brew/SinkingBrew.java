package com.bewitchment.common.brew;

import com.bewitchment.api.brew.IBrew;
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
public class SinkingBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity.isInWater() && entity.motionY > 0)
			entity.motionY *= -10;
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
		return 0x333399;
	}

	@Override
	public String getName() {
		return "sinking";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 11);
	}
}
