package com.bewitchment.common.core.command;

import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Arrays;
import java.util.List;

public class CommandFortuneActivator extends CommandBase {

	private static final List<String> aliases = Arrays.asList("activateFortune");

	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "activateFortune";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/activateFortune";
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (sender instanceof EntityPlayer) {
			CapabilityFortune dc = ((EntityPlayer) sender).getCapability(CapabilityFortune.CAPABILITY, null);
			if (dc.getFortune() != null) {
				if (dc.isActive()) {
					throw new CommandException("commands.enable_fortune.error.already_active");
				}
				dc.setActive();
				sender.sendMessage(new TextComponentTranslation("commands.enable_fortune.success"));
			} else {
				throw new CommandException("commands.enable_fortune.error.no_fortune");
			}
		} else {
			throw new CommandException("commands.error.no_console");
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}
}
