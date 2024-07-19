package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AboutSubcommand extends CommandImpl {
	public AboutSubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "about");
	}
	
	private void showAbout(CommandSender sender) {
		Util.sendMessage(sender, Util.colorize("&6&l------- &6MineFactions &6-------"));
		Util.sendMessage(sender, Util.colorize("&b&lCreator:&r &alowercasebtw"));
		Util.sendMessage(sender, Util.colorize("&b&lVersion:&r &a1.0"));
		Util.sendMessage(sender, Util.colorize("&b&lGitHub: &7https://github.com/lowercasebtw/minefactions"));
		Util.sendMessage(sender, Util.colorize("&6&l----------------------------"));
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		showAbout(player);
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		showAbout(sender);
	}
	
	@Override
	public String getFullDescription() {
		return "About the plugin";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.about");
	}
}
