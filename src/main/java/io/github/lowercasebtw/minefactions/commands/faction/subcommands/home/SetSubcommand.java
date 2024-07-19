package io.github.lowercasebtw.minefactions.commands.faction.subcommands.home;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Location;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class SetSubcommand extends CommandImpl {
	public SetSubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "set");
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
		FactionManager factionManager = plugin.getFactionManager();
		
		Faction faction = factionManager.getFactionByPlayer(player);
		if (faction == null) {
			Util.sendMessage(player, "&cYou are not in a faction!");
			return;
		}
		
		if (!faction.getLeaderUUID().equals(player.getUniqueId())) {
			Util.sendMessage(player, "&cYou cannot do this as you aren't the faction leader!");
			return;
		}
		
		BoundingBox boundingBox = faction.getBoundingBox();
		if (boundingBox == null) {
			Util.sendMessage(player, "&cYou cannot do this if your faction does not have a claim!");
			return;
		}
		
		Location location = player.getLocation();
		if (!boundingBox.contains(new Vector(location.getX(), location.getY(), location.getZ()))) {
			Util.sendMessage(player, "&cYou must be inside your factions claim!");
			return;
		}
		
		faction.setHome(player.getLocation());
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "Info about a subcommand";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.home.set");
	}
}
