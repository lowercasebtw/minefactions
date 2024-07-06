package io.github.lowercasebtw.minefactions.commands;

import dev.jorel.commandapi.CommandAPICommand;
import io.github.lowercasebtw.minefactions.commands.faction.FactionCommand;

public class Commands {
	public static void initialize() {
		register(new FactionCommand());
	}
	
	private static CommandAPICommand setupCommand(CommandAPICommand command) {
		command.executesPlayer(((CommandImpl) command)::executePlayer);
		command.executesConsole(((CommandImpl) command)::executeConsole);
		command.withPermission(command.getPermission());
		command.withFullDescription(command.getFullDescription());
		command.withShortDescription(command.getShortDescription());
		return command;
	}
	
	private static void register(CommandAPICommand command) {
		setupCommand(command).register();
	}
	
	public static void withSubcommands(CommandAPICommand it, CommandAPICommand... commands) {
		for (CommandAPICommand command : commands) {
			it.withSubcommand(setupCommand(command));
		}
	}
}
