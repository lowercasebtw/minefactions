package io.github.lowercasebtw.minefactions.commands;

import dev.jorel.commandapi.executors.CommandArguments;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface CommandImpl {
	void executePlayer(Player sender, CommandArguments args);
	void executeConsole(ConsoleCommandSender sender, CommandArguments args);
}
