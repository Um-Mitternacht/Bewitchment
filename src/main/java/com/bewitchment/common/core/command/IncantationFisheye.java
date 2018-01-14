package com.bewitchment.common.core.command;

import com.bewitchment.common.core.capability.energy.EnergyHandler;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;

/**
 * This class was created by Arekkuusu on 19/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
class IncantationFisheye implements IIncantation {

	//Todo: Make this only affect vision.
	@SuppressWarnings("ConstantConditions")
	@Override
	public void cast(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		final EntityLivingBase entity = (EntityLivingBase) sender.getCommandSenderEntity();
		if (entity.isEntityAlive()) {
			entity.addPotionEffect(new PotionEffect(MobEffects.WATER_BREATHING, 275, 0));
			entity.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 275, 0));

			EnergyHandler.addEnergy((EntityPlayer) sender, 800);
		}
	}
}
