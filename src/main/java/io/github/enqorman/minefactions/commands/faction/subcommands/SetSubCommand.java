package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class SetSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        sender.sendMessage("set cmd");
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Set certain data in the faction";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.set";
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
