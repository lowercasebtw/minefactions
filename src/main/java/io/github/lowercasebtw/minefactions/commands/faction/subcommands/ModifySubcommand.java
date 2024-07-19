package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ModifySubcommand extends CommandImpl {
	public ModifySubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "modify");
	}
	
	@Override
	public void executePlayer(Player sender, CommandArguments args) {
	
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
	
	}
	
	@Override
	public String getFullDescription() {
		return "Modify your factions info";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.modify");
	}
}
