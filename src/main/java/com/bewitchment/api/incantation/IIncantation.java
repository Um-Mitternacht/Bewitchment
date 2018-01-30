package com.bewitchment.api.incantation;

import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

/**
 * This class was created by Arekkuusu on 19/04/2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public interface IIncantation {

	void cast(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException;
	
	int getCost();
}
