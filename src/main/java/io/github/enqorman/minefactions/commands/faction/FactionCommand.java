package io.github.enqorman.minefactions.commands.faction;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.commands.faction.subcommands.*;
import io.github.enqorman.minefactions.util.ChatUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class FactionCommand implements CommandExecutor {
    private final Map<String, SubCommand> subCommands;

    public FactionCommand() {
        subCommands = new HashMap<>();
        subCommands.put("reload", new ReloadSubCommand());
        subCommands.put("about", new AboutSubCommand());
        subCommands.put("info", new InfoSubCommand());
        subCommands.put("list", new ListSubCommand());
        subCommands.put("create", new CreateSubCommand());
        subCommands.put("help", new HelpSubCommand());
        subCommands.put("disband", new DisbandSubCommand());
        subCommands.put("invite", new InviteSubCommand());
        subCommands.put("accept", new AcceptSubCommand());
        subCommands.put("claim", new ClaimSubCommand());
        subCommands.put("unclaim", new UnclaimSubCommand());
        subCommands.put("gui", new GUISubCommand());
        subCommands.put("set", new SetSubCommand());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String name,
            @NotNull String[] args) {
        // reload (TODO)
        // help (TODO)
        // invite <player> (TODO)
        // accept (TODO)
        // claim (system) (TODO)
        // unclaim (TODO)
        // gui? (TODO)
        // set (description, prefix, name) <text> (TODO)

        if (args.length > 0) {
            String cmdName = args[0];
            if (!subCommands.containsKey(cmdName)) {
                sender.sendMessage("Could not find subcommand named '" + cmdName + "'");
                sender.sendMessage("Use /f help to list all commands!");
                return true;
            }

            SubCommand subCommand = subCommands.get(cmdName);
            if (!subCommand.isConsoleAllowed() && !(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }

            if (!sender.hasPermission(subCommand.getPermission()) && !sender.isOp()) {
                sender.sendMessage(ChatUtil.colorize("&You do not have permission to use this subcommand!"));
                return true;
            }

            if (subCommand.execute(sender, Arrays.copyOfRange(args, 1, args.length)) == CommandResult.FAIL) {
                MineFactionsPlugin.getInstance().getLogger().log(Level.SEVERE,
                        "Failed to execute subcommand " + cmdName + " for faction command!");
                return true;
            }

            return true;
        }

        sender.sendMessage(ChatUtil.colorize("&e----==== Factions Command List ====----"));
        for (Map.Entry<String, SubCommand> entry : subCommands.entrySet()) {
            String subCommandName = entry.getKey();
            SubCommand subCommand = entry.getValue();
            sender.sendMessage(ChatUtil.colorize(
                    "/f " + subCommandName + " - &b" + subCommand.getDescription() + " &r&7("
                            + subCommand.getPermission()
                            + ")"));
        }

        return true;
    }
}
