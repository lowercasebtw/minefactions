package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class HelpSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender player, String[] args) {
        player.sendMessage("help cmd");
        return CommandResult.SUCCESS;
    }
}
