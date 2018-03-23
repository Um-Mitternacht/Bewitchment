package com.bewitchment.common.brew.brews;

import com.bewitchment.api.cauldron.brew.IBrew;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by Joseph on 3/23/2018. Original code was created by BerciTheBeast.
 */
//FIXME: This obviously needs a rewrite, as it's just a port of Berci's old code.
public class ParalysisBrew implements IBrew {

	@Override
	public void apply(World world, BlockPos pos, EntityLivingBase entity, int amplifier, int tick) {
		if (entity instanceof EntityPlayer) {
			if (!((EntityPlayer) entity).capabilities.isCreativeMode) {
				entity.motionX = 0;
				if (!entity.onGround) {
					entity.motionY -= (0.05 * ((amplifier + 1) + 1));
				}
				entity.motionZ = 0;
				((EntityPlayer) entity).rotationYaw = ((EntityPlayer) entity).prevRotationYaw;
				((EntityPlayer) entity).rotationPitch = ((EntityPlayer) entity).prevRotationPitch;
			}
		} else {
			entity.motionX = 0;
			if (!entity.onGround) {
				entity.motionY -= (0.05 * (amplifier + 1));
			}
			entity.motionZ = 0;
			((EntityPlayer) entity).rotationYaw = ((EntityPlayer) entity).prevRotationYaw;
			((EntityPlayer) entity).rotationPitch = ((EntityPlayer) entity).prevRotationPitch;
		}
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
		return 0x001234;
	}

	@Override
	public String getName() {
		return "paralysis";
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void renderHUD(int x, int y, Minecraft mc, int amplifier) {
		render(x, y, mc, 13);
	}
}
