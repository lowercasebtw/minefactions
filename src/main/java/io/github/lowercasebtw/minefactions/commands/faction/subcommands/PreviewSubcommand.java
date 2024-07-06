package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.manager.ParticleManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

public class PreviewSubcommand  extends CommandAPICommand implements CommandImpl {
	public PreviewSubcommand() {
		super("preview");
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
		FactionManager factionManager = plugin.getFactionManager();
		Faction faction = factionManager.getFactionByPlayer(player);
		if (faction == null) {
			Util.sendMessage(player, "&cYou are not in a faction! You must be in a faction to use this command.");
			return;
		}
		
		BoundingBox boundingBox = faction.getBoundingBox();
		if (boundingBox == null) {
			Util.sendMessage(player, "&cYour faction does not have a claim to preview!");
			return;
		}
		
		if (ParticleManager.toggleParticleBoundary(player, boundingBox)) {
			Util.sendMessage(player, "&aOn");
		} else {
			Util.sendMessage(player, "&cOff");
		}
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "Shows a particle border boundary of your factions claim";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.preview");
	}
}
