package com.bewitchment.common.core.command;

import com.google.common.collect.Lists;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class was created by BerciTheBeast on 19.4.2017.
 * It's distributed as part of Witchcraft under
 * the MIT license.
 */
public class CommandIncantation implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getName() {
		return "incant";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/incant [incantation]";
	}

	@Override
	public List<String> getAliases() {
		final List<String> list = new ArrayList<>();
		list.add("i");
		list.add(getName());
		return list;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1) throw new WrongUsageException("commands.incantation.usage");
		if (sender.getCommandSenderEntity() == null) return;
		final String command = args[0];
		if (ModCommands.commands.containsKey(command)) {
			ModCommands.commands.get(command).cast(server, sender, args);
		} else {
			throw new CommandException("commands.incantation.notFound", sender.getName());
		}
	}

	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		return true;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return Lists.newArrayList(ModCommands.commands.keySet());
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}


}
