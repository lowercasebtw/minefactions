package io.github.lowercasebtw.minefactions.commands;

import dev.jorel.commandapi.CommandAPICommand;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.faction.FactionCommand;
import io.github.lowercasebtw.minefactions.util.Util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Commands {
	private final MineFactionsPlugin plugin;
	
	public Commands(MineFactionsPlugin plugin) throws InvocationTargetException, InstantiationException, IllegalAccessException {
		this.plugin = plugin;
		register(FactionCommand.class);
	}
	
	private CommandImpl getInstance(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
		Constructor<?> constructor = clazz.getDeclaredConstructors()[0];
		if (constructor == null)
			return null;
		return (CommandImpl) constructor.newInstance(this.plugin, this);
	}
	
	private CommandAPICommand setupCommand(CommandImpl command) {
		command.executesPlayer(command::executePlayer);
		command.executesConsole(command::executeConsole);
		command.withPermission(command.getPermission());
		command.withFullDescription(command.getFullDescription());
		command.withShortDescription(command.getShortDescription());
		return command;
	}
	
	private void register(Class<?> clazz) throws InvocationTargetException, InstantiationException, IllegalAccessException {
		CommandImpl command = getInstance(clazz);
		if (command == null) {
			plugin.getLogger().severe("Failed to register command: " + clazz.getName());
			return;
		}
		
		setupCommand(command).register(Util.NAMESPACE);
	}
	
	public void withSubcommands(CommandImpl it, List<CommandImpl> commands) {
		for (CommandImpl command : commands) {
			it.withSubcommand(setupCommand(command));
		}
	}
	
	public void withSubcommands(CommandImpl it, Class<?>... clazzes) {
		List<CommandImpl> commands = new ArrayList<>();
		for (Class<?> clazz : clazzes) {
			try {
				commands.add(getInstance(clazz));
			} catch (InvocationTargetException | InstantiationException | IllegalAccessException exception) {
				plugin.getLogger().severe("Failed to register sub-command: " + clazz.getName());
				exception.printStackTrace();
			}
		}
		withSubcommands(it, commands);
	}
}
