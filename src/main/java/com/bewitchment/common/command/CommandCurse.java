package com.bewitchment.common.command;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Curse;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"NullableProblems", "ConstantConditions"})
public class CommandCurse extends CommandBase {
	@Override
	public String getName() {
		return "curse";
	}
	
	@Override
	public String getUsage(ICommandSender iCommandSender) {
		return "/curse <player> <get/add/remove/clear>";
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0) throw new WrongUsageException(getUsage(sender));
		EntityPlayer player = null;
		for (WorldServer world : server.worlds) {
			EntityPlayer temp = world.getPlayerEntityByName(args[0]);
			if (temp != null) player = temp;
		}
		if (args[0].equals("@p")) player = (EntityPlayer) sender.getCommandSenderEntity();
		if (player == null) throw new CommandException("commands.generic.player.notFound", args[0]);
		if (args.length > 1) {
			if (args[1].equals("get")) {
				if (args.length == 2) {
					List<Curse> curses = player.getCapability(ExtendedPlayer.CAPABILITY, null).getCurses();
					List<String> cursesStrings = new ArrayList<>();
					for (Curse curse : curses) {
						cursesStrings.add(curse.getRegistryName().toString());
					}
					if (!cursesStrings.isEmpty()) sender.sendMessage(new TextComponentTranslation("commands.curse.get", player.getDisplayNameString(), cursesStrings.toString()));
					else throw new CommandException("commands.curse.empty", player.getDisplayNameString());
				}
				else throw new CommandException("commands.generic.syntax");
			}
			if (args[1].equals("add")) {
				if (args.length == 4) {
					Curse curse = GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(args[2]));
					if (curse == null) throw new CommandException("commands.curse.null", args[2]);
					else {
						player.getCapability(ExtendedPlayer.CAPABILITY, null).addCurse(curse, Integer.parseInt(args[3]));
						sender.sendMessage(new TextComponentTranslation("commands.curse.add", args[2], player.getDisplayNameString()));
					}
				}
				else throw new CommandException("commands.generic.syntax");
			}
			if (args[1].equals("remove")) {
				if (args.length == 3) {
					Curse curse = GameRegistry.findRegistry(Curse.class).getValue(new ResourceLocation(args[2]));
					if (curse == null) throw new CommandException("commands.fortune.null", args[2]);
					if (!player.getCapability(ExtendedPlayer.CAPABILITY, null).hasCurse(curse)) throw new CommandException("commands.curse.nocurse", player.getDisplayNameString(), args[2]);
					else {
						player.getCapability(ExtendedPlayer.CAPABILITY, null).removeCurse(curse);
						sender.sendMessage(new TextComponentTranslation("commands.curse.remove", args[2], player.getDisplayNameString()));
					}
				}
				else throw new CommandException("commands.generic.syntax");
			}
			if (args[1].equals("clear")) {
				if (args.length == 2) {
					List<Curse> curses = player.getCapability(ExtendedPlayer.CAPABILITY, null).getCurses();
					if (!curses.isEmpty()) {
						for (Curse curse : curses) {
							player.getCapability(ExtendedPlayer.CAPABILITY, null).removeCurse(curse);
						}
						sender.sendMessage(new TextComponentTranslation("commands.curse.clear", player.getDisplayNameString()));
					}
					else throw new CommandException("commands.curse.empty", player.getDisplayNameString());
				}
				else throw new CommandException("commands.generic.syntax");
			}
		}
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		return 2;
	}
	
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
		if (args.length == 1) return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
		if (args.length == 2) return getListOfStringsMatchingLastWord(args, Arrays.asList("get", "add", "remove", "clear"));
		if (args.length == 3 && (args[1].equals("add") || args[1].equals("remove"))) {
			List<String> curses = new ArrayList<>();
			for (Curse curse : GameRegistry.findRegistry(Curse.class)) curses.add(curse.getRegistryName().toString());
			return getListOfStringsMatchingLastWord(args, curses);
		}
		return Collections.emptyList();
	}
}
