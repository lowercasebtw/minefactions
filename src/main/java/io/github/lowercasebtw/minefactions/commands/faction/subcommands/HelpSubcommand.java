package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class HelpSubcommand extends CommandAPICommand implements CommandImpl {
	public HelpSubcommand() {
		super("help");
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		// TODO
		Util.sendMessage(player, "help cmd");
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		// TODO
		Util.sendMessage(sender, "help cmd");
	}
	
	@Override
	public String getFullDescription() {
		return "Info about a subcommand";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.help");
	}
}
