package io.github.lowercasebtw.minefactions.commands.faction.subcommands.admin;

import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GiveSubcommand extends CommandImpl {
	private final ItemManager itemManager;
	
	public GiveSubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "give");
		
		itemManager = plugin.getItemManager();
		
		List<String> keys = new ArrayList<>();
		for (NamespacedKey namespacedKey : itemManager.keySet()) {
			keys.add(namespacedKey.toString());
			keys.add(namespacedKey.getKey());
		}
		
		this.withArguments(new MultiLiteralArgument("item", keys.toArray(new String[0])));
	}
	
	@Override
	public void executePlayer(Player player, CommandArguments args) {
		String identifierString = args.getByClassOrDefault("item", String.class, "");
		
		NamespacedKey namespacedKey;
		if (!identifierString.contains(":"))
			namespacedKey = Util.identifier(identifierString);
		else
			namespacedKey = NamespacedKey.fromString(identifierString);
		
		if (!itemManager.has(namespacedKey)) {
			Util.sendMessage(player, "&cThat item does not exist!");
			Util.sendMessage(player, "&aCurrent Existing Items:");
			for (Item item : itemManager.values()) {
				Util.sendMessage(player, " &7&l- &r(id=" + item.getNamespacedKey() + ") &6\"" + item.getDisplayName() + "&6\"");
			}
			return;
		}
		
		Item item = itemManager.get(namespacedKey);
		if (!itemManager.giveItem(player, item)) {
			Util.sendMessage(player, "&cCould not give item, your inventory is full!");
			return;
		}
		
		Util.sendMessage(player, "Gave 1 [" + item.getDisplayName() + "&r] to " + player.getDisplayName());
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "Gives the player a custom-made item";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.admin.give");
	}
}
