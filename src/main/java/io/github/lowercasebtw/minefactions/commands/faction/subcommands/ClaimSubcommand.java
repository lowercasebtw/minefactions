package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.manager.WandManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ClaimSubcommand extends CommandAPICommand implements CommandImpl {
	public ClaimSubcommand() {
		super("claim");
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
		WandManager wandManager = plugin.getWandManager();
		if (!wandManager.hasWand(player.getUniqueId())) {
			if (wandManager.giveWand(player)) {
				Util.sendMessage(player, "&aGiven 1x claim wand");
			} else {
				Util.sendMessage(player, "&cYou already have a wand!");
			}
		} else {
			FactionManager.claimRegion(wandManager, player);
		}
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
	}
	
	@Override
	public String getFullDescription() {
		return "Claim land for your faction";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.claim");
	}
}
