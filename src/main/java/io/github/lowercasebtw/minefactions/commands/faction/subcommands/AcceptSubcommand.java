package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AcceptSubcommand extends CommandAPICommand implements CommandImpl {
	public AcceptSubcommand() {
		super("accept");
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		Util.sendMessage(player, "accept cmd");
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
	}
	
	@Override
	public String getFullDescription() {
		return "Accept a faction invite";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.accept");
	}
}
