package io.github.lowercasebtw.minefactions.commands.faction;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.commands.faction.subcommands.*;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class FactionCommand extends CommandAPICommand implements CommandImpl {
	public FactionCommand() {
		super("faction");
		this.withAliases("factions", "f");
		Commands.withSubcommands(this,
			new AboutSubcommand(),
			new AcceptSubcommand(),
			new AdminSubcommand(),
			new ClaimSubcommand(),
			new CreateSubcommand(),
			new DisbandSubcommand(),
			new GuiSubcommand(),
			new HelpSubcommand(),
			new HomeSubcommand(),
			new InfoSubcommand(),
			new InviteSubcommand(),
			new ListSubcommand(),
			new PreviewSubcommand(),
			new UnclaimSubcommand());
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
		// claim (system) (TODO)
		// unclaim (TODO)
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
