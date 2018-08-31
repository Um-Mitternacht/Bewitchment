package com.bewitchment.common.core.command;

import com.bewitchment.api.divination.IFortune;
import com.bewitchment.common.content.crystalBall.Fortune;
import com.bewitchment.common.content.crystalBall.capability.CapabilityFortune;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommandForceFortune extends CommandBase {

	private static final List<String> aliases = Arrays.asList("setFortune");

	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "setFortune";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/setFortune <fortune>";
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0)
			throw new WrongUsageException("commands.set_fortune.usage");
		if (sender instanceof EntityPlayer) {
			CapabilityFortune dc = ((EntityPlayer) sender).getCapability(CapabilityFortune.CAPABILITY, null);
			IFortune add = Fortune.REGISTRY.getValue(new ResourceLocation(args[0]));
			if (add == null) {
				throw new CommandException("commands.set_fortune.error.no_fortune");
			}
			dc.setFortune(add);
			sender.sendMessage(new TextComponentTranslation("commands.set_fortune.success"));
		} else {
			throw new CommandException("commands.error.no_console");
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length == 1)
			return Fortune.REGISTRY.getKeys().stream().map(t -> t.toString()).filter(s -> s.toLowerCase().startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
		return super.getTabCompletions(server, sender, args, targetPos);
	}

}
