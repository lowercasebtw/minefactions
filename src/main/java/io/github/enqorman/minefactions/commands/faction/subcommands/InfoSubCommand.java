package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;

public class InfoSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender player, String[] args) {
        player.sendMessage("info cmd");
        return CommandResult.SUCCESS;
    }
}
