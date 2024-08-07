package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class DisbandSubcommand extends CommandImpl {
	public DisbandSubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "disband");
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		FactionManager factionManager = plugin.getFactionManager();
		
		Faction faction = factionManager.getFactionByPlayer(player);
		if (faction == null) {
			Util.sendMessage(player, "&cYou are not in a faction!");
			return;
		}
		
		if (!faction.getLeaderUUID().equals(player.getUniqueId())) {
			Util.sendMessage(player, "&cOnly the faction leader can do that!");
			return;
		}
		
		if (!factionManager.remove(faction)) {
			Util.sendMessage(player, "&cFailed to disband faction!");
			return;
		}
		
		Util.sendMessage(player, "&aSuccessfully disbanded the faction!");
		plugin.getServer().broadcastMessage(Util.colorize("&eFaction &r" + faction.getName() + " &ehas been disbanded!"));
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "Delete the faction";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.disband");
	}
}
