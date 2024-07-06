package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.commands.faction.subcommands.home.SetSubcommand;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;

public class HomeSubcommand extends CommandAPICommand implements CommandImpl {
	public HomeSubcommand() {
		super("home");
		Commands.withSubcommands(this, new SetSubcommand());
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
		
		if (faction.getHome() == null) {
			Util.sendMessage(player, "&cThe faction does not have a home!");
			return;
		}
		
		// TODO: Start telelport timer
		player.teleport(faction.getHome(), PlayerTeleportEvent.TeleportCause.COMMAND);
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
		return CommandPermission.fromString("minefactions.command.faction.home");
	}
}
