package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CreateSubcommand extends CommandAPICommand implements CommandImpl {
	public CreateSubcommand() {
		super("create");
		this.withArguments(new LiteralArgument("faction_name"));
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
		FactionManager factionManager = plugin.getFactionManager();

		Faction faction = factionManager.getFactionByPlayer(player);
		if (faction != null) {
			Util.sendMessage(player, Util.colorize("&cYou are already in a faction and cannot create another whilst in it!"));
			return;
		}

		String factionName = args.getByClassOrDefault("faction_name", String.class, null);
		if (factionName == null) {
			Util.sendMessage(player, Util.colorize("&cYou must provide a faction name!"));
			return;
		}

		Faction otherExists = factionManager.findFactionByName(factionName, true);
		if (otherExists != null) {
			Util.sendMessage(player, Util.colorize("&cSorry but that name is already taken!"));
			Util.sendMessage(player, Util.colorize("&aUse \"/f list\" to list all existing factions!"));
			return;
		}

		faction = new Faction(factionName, "(empty)", player.getUniqueId(), new ArrayList<>(), 0, 2, null, null);
		factionManager.addFaction(faction);

		if (!factionManager.saveFactions()) {
			Util.sendMessage(player, Util.colorize("&cFailed to create faction! Reason: Internal Error"));
			return;
		}

		Util.sendMessage(player, Util.colorize("&aSuccessfully created a faction named '" + factionName + "&a'!"));
		plugin.getServer().broadcastMessage(Util.colorize("&eFaction &r" + factionName + " &ehas been created!"));
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "Create a faction";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.create");
	}
}
