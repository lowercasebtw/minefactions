package io.github.enqorman.minefactions.commands;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    CommandResult execute(CommandSender player, String[] args);
}
