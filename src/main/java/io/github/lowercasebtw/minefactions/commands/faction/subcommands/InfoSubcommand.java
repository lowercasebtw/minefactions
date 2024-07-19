package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.ArgumentSuggestions;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class InfoSubcommand extends CommandImpl {
	public InfoSubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "info");
		// TODO: look if they used ign or uuid
		this.withArguments(new LiteralArgument("faction_name").setOptional(true).replaceSuggestions(ArgumentSuggestions.stringCollectionAsync(info -> CompletableFuture.supplyAsync(() -> {
			FactionManager factionManager = plugin.getFactionManager();
			return factionManager.getFactions().stream().map(Faction::getName).map(ChatColor::stripColor).toList();
		}))));
	}
	
	private void showInfo(MineFactionsPlugin plugin, CommandSender sender, Faction faction) {
		Util.sendMessage(sender, Util.colorize("&e----==== Faction info: &r" + faction.getName() + " &e====----"));
		Util.sendMessage(sender, Util.colorize("&eKills: &r" + faction.getKillCount()));
		Util.sendMessage(sender, Util.colorize("&eDescription: &r" + faction.getDescription()));
		Util.sendMessage(sender, Util.colorize("&eDTR: &r" + faction.getDTR() + "/5 " + (faction.getDTR() == 0 ? "&cRAIDABLE" : "")));
		Util.sendMessage(sender, Util.colorize("&eLeader: &r" + plugin.getServer().getOfflinePlayer(faction.getLeaderUUID()).getName()));
		
		List<UUID> memberUuids = faction.getMemberUUIDs();
		Util.sendMessage(sender, Util.colorize("&eMembers &7(" + memberUuids.size() + ")&e:" + (memberUuids.isEmpty() ? " &cNone" : "")));
		if (!memberUuids.isEmpty()) {
			for (UUID memberUuid : faction.getMemberUUIDs()) {
				OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(memberUuid);
				Util.sendMessage(sender, Util.colorize(" &r- &" + (offlinePlayer.isOnline() ? "a" : "e") + offlinePlayer.getName()));
			}
		}
		
		{
			Location homeLocation = faction.getHome();
			String coords = "";
			if (homeLocation != null) {
				int x = (int) Math.floor(homeLocation.getX());
				int y = (int) Math.floor(homeLocation.getY());
				int z = (int) Math.floor(homeLocation.getZ());
				coords = "&bx: &r" + x + "&7, &by: &r" + y + "&7, &bz: &r" + z;
			}
			Util.sendMessage(sender, Util.colorize("&eHome: " + (homeLocation == null ? "&cNot set" : coords)));
		}
		
		{
			BoundingBox boundingBox = faction.getBoundingBox();
			String coords = "";
			if (boundingBox != null) {
				Vector center = boundingBox.getCenter();
				coords = "&bx: &r" + Math.floor(center.getX()) + "&7, &bz: &r" + Math.floor(center.getZ());
			}
			Util.sendMessage(sender, Util.colorize("&eClaim: " + (boundingBox == null ? "&cNot set" : coords)));
		}
	}
	
	private void execute(CommandSender sender, String factionName) {
		FactionManager factionManager = plugin.getFactionManager();
		Faction faction = factionManager.findFactionByName(factionName, true);
		if (faction == null) {
			// TODO: look if they used ign or uuid
			Util.sendMessage(sender, "Could not find a faction by that name!");
			return;
		}
		
		showInfo(plugin, sender, faction);
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		String factionName = args.getByClassOrDefault("faction_name", String.class, null);
		
		FactionManager factionManager = plugin.getFactionManager();
		if (factionName == null) {
			Faction faction = factionManager.getFactionByPlayer(player);
			if (faction == null) {
				Util.sendMessage(player, Util.colorize("&cYou are not in a faction!"));
				Util.sendMessage(player, Util.colorize("&aDo \"/f info <faction>\" to check the info of other factions!"));
				return;
			}
			
			showInfo(plugin, player, faction);
			return;
		}
		
		execute(player, factionName);
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		String factionName = args.getByClassOrDefault("faction_name", String.class, null);
		if (factionName == null && !(sender instanceof Player)) {
			Util.sendMessage(sender, "Only players can use this command like this!");
			Util.sendMessage(sender, "To use this in console, do \"/f info <faction>\"");
			return;
		}
		
		execute(sender, factionName);
	}
	
	@Override
	public String getFullDescription() {
		return "About a faction";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.info");
	}
}
