package com.bewitchment.common.core.command;

import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.bewitchment.api.capability.transformations.EnumTransformationType;
import com.bewitchment.api.capability.transformations.TransformationHelper;

public class CommandTransformationModifier extends CommandBase {

	private static final List<String> aliases = Arrays.asList("setTransformation", "bw-st");

	@Override
	public int compareTo(ICommand arg0) {
		return 0;
	}

	@Override
	public String getName() {
		return "setTransformation";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "/setTransformation [path] [level]";
	}

	@Override
	public List<String> getAliases() {
		return aliases;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if (args.length == 0)
			throw new WrongUsageException("commands.set_transformation.usage");
		if (sender instanceof EntityPlayer) {
			String typeStr = args[0].toLowerCase();
			EnumTransformationType transf = null;

			if (typeStr.equals("v") || typeStr.equals("vamp")) {
				transf = EnumTransformationType.VAMPIRE;
			} else if (typeStr.equals("w") || typeStr.equals("ww") || typeStr.equals("wolf")) {
				transf = EnumTransformationType.WEREWOLF;
			} else if (typeStr.equals("h") || typeStr.equals("hunt") || typeStr.equals("wh")) {
				transf = EnumTransformationType.HUNTER;
			} else if (typeStr.equals("s") || typeStr.equals("ghost") || typeStr.equals("phantom")) {
				transf = EnumTransformationType.SPECTRE;
			} else if (typeStr.equals("n")) {
				transf = EnumTransformationType.NONE;
			} else
				for (EnumTransformationType tt : EnumTransformationType.values()) {
					if (typeStr.equals(tt.name().toLowerCase())) {
						transf = tt;
						break;
					}
				}

			if (transf == null)
				throw new WrongUsageException("commands.set_transformation.usage.no_transformation");
			int level = 0;
			try {
				if (transf != EnumTransformationType.NONE) {
					level = Integer.valueOf(args[1]);
				}
			} catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
				throw new WrongUsageException("commands.set_transformation.usage.invalid_level");
			}
			if (level < 0 || level > 10) {
				throw new WrongUsageException("commands.set_transformation.usage.invalid_level");
			}
			TransformationHelper.setTypeAndLevel((EntityPlayer) sender, transf, level, false);
			sender.sendMessage(new TextComponentTranslation("commands.set_transformation.success", transf.name().toLowerCase(), level));
		} else {
			throw new WrongUsageException("commands.error.no_console");
		}
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length == 1)
			return Arrays.asList(EnumTransformationType.values()).stream().map(t -> t.name().toLowerCase()).filter(s -> s.startsWith(args[args.length - 1].toLowerCase())).collect(Collectors.toList());
		return super.getTabCompletions(server, sender, args, targetPos);
	}

}
