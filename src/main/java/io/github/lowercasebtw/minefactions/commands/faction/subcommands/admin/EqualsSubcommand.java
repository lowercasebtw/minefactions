package io.github.lowercasebtw.minefactions.commands.faction.subcommands.admin;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.CommandPermission;
import dev.jorel.commandapi.arguments.MultiLiteralArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.lowercasebtw.minefactions.commands.CommandImpl;
import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class EqualsSubcommand extends CommandAPICommand implements CommandImpl {
	public EqualsSubcommand() {
		super("equals");
		
		List<String> keys = new ArrayList<>();
		for (NamespacedKey namespacedKey : ItemManager.keySet()) {
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
		
		if (!ItemManager.has(namespacedKey)) {
			Util.sendMessage(player, Util.colorize("&cThat item does not exist!"));
			Util.sendMessage(player, Util.colorize("&aCurrent Existing Items:"));
			for (Item item : ItemManager.values()) {
				Util.sendMessage(player, Util.colorize(" &7&l- &r(id=" + item.getIdentifier() + ") &6\"" + item.getDisplayName() + "&6\""));
			}
			return;
		}

		PlayerInventory inventory = player.getInventory();
		ItemStack itemStack = inventory.getItem(inventory.getHeldItemSlot());
		if (itemStack == null || itemStack.getType() == Material.AIR) {
			Util.sendMessage(player, Util.colorize("&cPlease hold a item!"));
			return;
		}
		
		Item item = ItemManager.get(namespacedKey);
		boolean equals = item.equalsStack(itemStack);
		Util.sendMessage(player, Util.colorize(equals ? "&aThey equal!" : "&cThey do not equal!"));
	}
	
	@Override
	public void executeConsole(ConsoleCommandSender sender, CommandArguments args) {
		Util.sendMessage(sender, "Only players can run this command!");
	}
	
	@Override
	public String getFullDescription() {
		return "Tells whether or not if a custom-item is equal to the held item";
	}
	
	@Override
	public CommandPermission getPermission() {
		return CommandPermission.fromString("minefactions.command.faction.admin.equals");
	}
}
