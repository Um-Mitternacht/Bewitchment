package com.bewitchment.common.core.command;

import com.bewitchment.api.incantation.IIncantation;
import com.bewitchment.api.mp.IMagicPowerContainer;
import com.bewitchment.common.content.incantation.ModIncantations;
import com.google.common.collect.Lists;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class was created by BerciTheBeast on 19.4.2017.
 * It's distributed as part of Bewitchment under
 * the MIT license.
 */
public class CommandIncantation implements ICommand {

	@Override
	public int compareTo(ICommand o) {
		return 0;
	}

	@Override
	public String getName() {
		return "chant";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/chant [incantation]";
	}

	@Override
	public List<String> getAliases() {
		final List<String> list = new ArrayList<>();
		list.add("i");
		list.add(getName());
		return list;
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length < 1) throw new WrongUsageException("commands.incantation.usage");
		if (sender.getCommandSenderEntity() == null) return;
		final String command = args[0];
		if (ModIncantations.getCommands().containsKey(command)) {
			IIncantation incantation = ModIncantations.getCommands().get(command);
			if (sender.getCommandSenderEntity() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) sender.getCommandSenderEntity();
				if (player.getCapability(IMagicPowerContainer.CAPABILITY, null).drain(incantation.getCost())) {
					incantation.cast(player, args);
				} else {
					throw new CommandException("commands.incantation.no_energy", sender.getName());
				}
			}
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
		return Lists.newArrayList(ModIncantations.getCommands().keySet().stream().filter(s -> args.length == 0 || s.startsWith(args[args.length - 1])).collect(Collectors.toList()));
	}

	@Override
	public boolean isUsernameIndex(String[] args, int index) {
		return false;
	}


}
