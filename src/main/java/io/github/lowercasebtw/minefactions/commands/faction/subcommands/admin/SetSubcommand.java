package io.github.lowercasebtw.minefactions.commands.faction.subcommands.admin;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class SetSubcommand extends CommandAPICommand implements CommandImpl {
	public SetSubcommand() {
		super("set");
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		Util.sendMessage(player, "set cmd");
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
	}
	
	@Override
	public String getFullDescription() {
		return "Set certain data in the faction";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.admin.set");
	}
}
