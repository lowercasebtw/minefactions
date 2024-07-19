package io.github.lowercasebtw.minefactions.commands.faction;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.commands.faction.subcommands.*;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FactionCommand extends CommandImpl {
	public FactionCommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "faction");
		this.withAliases("factions", "f");
		commands.withSubcommands(this,
				AboutSubcommand.class,
				AcceptSubcommand.class,
				AdminSubcommand.class,
				ClaimSubcommand.class,
				CreateSubcommand.class,
				DisbandSubcommand.class,
				GuiSubcommand.class,
				HelpSubcommand.class,
				HomeSubcommand.class,
				InfoSubcommand.class,
				InviteSubcommand.class,
				ListSubcommand.class,
				ModifySubcommand.class,
				PreviewSubcommand.class,
				UnclaimSubcommand.class);
	}
	
	private void showCommandsList(CommandSender sender) {
		Util.sendMessage(sender, Util.colorize("&e----==== Factions Command List ====----"));
		for (CommandAPICommand command : this.getSubcommands()) {
			Util.sendMessage(sender, Util.colorize("/f " + command.getName() + " - &b" + command.getFullDescription() + " &r&7(" + command.getPermission() + ")"));
		}
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		// reload (TODO)
		// help (TODO)
		// invite <player> (TODO)
		// accept (TODO)
		// gui? (TODO)
		// set (description, prefix, name) <text> (TODO)
		showCommandsList(player);
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		showCommandsList(sender);
	}
	
	@Override
	public String getFullDescription() {
		return "";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction");
	}
}
