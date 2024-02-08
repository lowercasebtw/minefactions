package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class InviteSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        sender.sendMessage("invite cmd");
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Invite player to faction";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.invite";
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
