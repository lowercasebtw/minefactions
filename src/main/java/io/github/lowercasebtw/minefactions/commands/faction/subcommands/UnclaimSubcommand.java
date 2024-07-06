package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class UnclaimSubcommand extends CommandAPICommand implements CommandImpl {
	public UnclaimSubcommand() {
		super("unclaim");
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
			Util.sendMessage(player, "&cOnly the faction leader can do this!");
			return;
		}
		
		if (faction.getBoundingBox() == null) {
			Util.sendMessage(player, "&cThe faction does not have a claim!");
			return;
		}
		
		faction.setHome(null);
		faction.setBoundingBox(null);
		Util.sendMessage(player, "&aArea unclaimed!");
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "Un-claim your factions area";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.unclaim");
	}
}
