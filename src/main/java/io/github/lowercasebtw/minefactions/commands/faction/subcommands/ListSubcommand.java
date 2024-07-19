package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ListSubcommand extends CommandImpl {
	public ListSubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "list");
	}
	
	private void showList(CommandSender sender) {
		FactionManager factionManager = plugin.getFactionManager();
		List<Faction> factions = factionManager.getFactions();
		if (factions.isEmpty()) {
			Util.sendMessage(sender, Util.colorize("&7&lNo factions exist currently."));
			return;
		}
		
		Util.sendMessage(sender, Util.colorize("&e----==== Factions List ====----"));
		for (Faction faction : factions) {
			int count = 1 + faction.getMemberUUIDs().size();
			Util.sendMessage(sender, Util.colorize(faction.getName() + " &e| &b" + count + " " + (count > 1 ? "members" : "member") + " &e| &r" + faction.getDTR() + "/5 DTR"));
		}
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		showList(player);
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		showList(sender);
	}
	
	@Override
	public String getFullDescription() {
		return "List all existing factions";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.list");
	}
}
