package io.github.lowercasebtw.minefactions.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public abstract class CommandImpl extends CommandAPICommand {
	protected MineFactionsPlugin plugin;
	protected Commands commands;
	
	public CommandImpl(MineFactionsPlugin plugin, Commands commands, String commandName) {
		super(commandName);
		this.plugin = plugin;
		this.commands = commands;
	}
	
	public abstract void executePlayer(Player sender, CommandArguments args);
	
	public abstract void executeConsole(ConsoleCommandSender sender, CommandArguments args);
}
