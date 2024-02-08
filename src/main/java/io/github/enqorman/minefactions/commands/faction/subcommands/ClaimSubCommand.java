package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class ClaimSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        sender.sendMessage("claim cmd");
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Claim land for your faction";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.claim";
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
