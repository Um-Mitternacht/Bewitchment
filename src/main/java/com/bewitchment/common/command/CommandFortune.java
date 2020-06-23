package com.bewitchment.common.command;

import com.bewitchment.api.capability.extendedplayer.ExtendedPlayer;
import com.bewitchment.api.registry.Fortune;
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

@SuppressWarnings({"NullableProblems", "ConstantConditions", "StatementWithEmptyBody"})
public class CommandFortune extends CommandBase {
    @Override
    public String getName() {
        return "fortune";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/fortune <player> <get/set/remove/execute>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) throw new WrongUsageException(getUsage(sender));
        EntityPlayer player = null;
        for (WorldServer world : server.worlds) {
            EntityPlayer test = world.getPlayerEntityByName(args[0]);
            if (test != null) player = test;
        }
        if (player == null) throw new CommandException("commands.generic.player.notFound", args[0]);
        if (args.length > 1) {
            if (args[1].equals("get")) {
                if (args.length == 2) {
                    Fortune fortune = player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune;
                    sender.sendMessage(new TextComponentTranslation("commands.fortune.get", args[0], fortune == null ? "null" : fortune.getRegistryName().toString()));
                } else throw new CommandException("commands.generic.syntax");
            }
            if (args[1].equals("set")) {
                if (args.length == 3) {
                    Fortune fortune = GameRegistry.findRegistry(Fortune.class).getValue(new ResourceLocation(args[2]));
                    if (fortune == null) throw new CommandException("commands.fortune.null", args[2]);
                    else {
                        player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune = fortune;
                        sender.sendMessage(new TextComponentTranslation("commands.fortune.set", args[0], args[2]));
                    }
                } else throw new CommandException("commands.generic.syntax");
            }
            if (args[1].equals("remove")) {
                if (args.length == 2) {
                    if (player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune == null)
                        throw new CommandException("commands.fortune.nofortune", player.getDisplayNameString());
                    else {
                        player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune = null;
                        sender.sendMessage(new TextComponentTranslation("commands.fortune.remove", args[0]));
                    }
                } else throw new CommandException("commands.generic.syntax");
            }
            if (args[1].equals("execute")) {
                if (args.length == 2) {
                    if (player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune == null)
                        throw new CommandException("commands.fortune.nofortune", player.getDisplayNameString());
                    else {
                        if (player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune.apply(player)) {
                            player.getCapability(ExtendedPlayer.CAPABILITY, null).fortune = null;
                            sender.sendMessage(new TextComponentTranslation("commands.fortune.execute", args[0]));
                        } else {
                            sender.sendMessage(new TextComponentTranslation("commands.fortune.execute.fail", args[0]));
                        }
                    }
                } else throw new CommandException("commands.generic.syntax");
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
        if (args.length == 2) return Arrays.asList("get", "set", "remove", "execute");
        if (args.length == 3 && args[1].equals("set")) {
            List<String> fortunes = new ArrayList<>();
            for (Fortune fortune : GameRegistry.findRegistry(Fortune.class))
                fortunes.add(fortune.getRegistryName().toString());
            return fortunes;
        }
        return Collections.emptyList();
    }
}