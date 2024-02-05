package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GUISubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return CommandResult.SUCCESS;
        }

        sender.sendMessage("gui cmd");
        return CommandResult.SUCCESS;
    }
}
