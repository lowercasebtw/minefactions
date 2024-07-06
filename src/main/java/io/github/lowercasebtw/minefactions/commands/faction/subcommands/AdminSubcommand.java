package io.github.lowercasebtw.minefactions.commands.faction.subcommands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.commands.faction.subcommands.admin.EqualsSubcommand;
import io.github.lowercasebtw.minefactions.commands.faction.subcommands.admin.GiveSubcommand;
import io.github.lowercasebtw.minefactions.commands.faction.subcommands.admin.ReloadSubcommand;
import io.github.lowercasebtw.minefactions.commands.faction.subcommands.admin.SetSubcommand;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AdminSubcommand extends CommandAPICommand implements CommandImpl {
	public AdminSubcommand() {
		super("admin");
		Commands.withSubcommands(this,
			new ReloadSubcommand(),
			new SetSubcommand(),
			new GiveSubcommand(),
			new EqualsSubcommand());
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		Util.sendMessage(player, Util.colorize("&aThe Admin Command, sponsored by Squarespace."));
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "your moms balls";
	}

	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.admin");
	}
}
