package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class UnclaimSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        sender.sendMessage("unclaim cmd");
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Unclaim your factions area";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.unclaim";
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
