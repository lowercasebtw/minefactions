package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        sender.sendMessage("reload cmd");
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Reload the plugin";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.reload";
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}
