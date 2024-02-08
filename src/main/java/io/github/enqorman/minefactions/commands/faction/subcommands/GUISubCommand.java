package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class GUISubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        sender.sendMessage("gui cmd");
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.gui";
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
