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
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class EqualsSubcommand extends CommandImpl {
	private final ItemManager itemManager;
	
	public EqualsSubcommand(MineFactionsPlugin plugin, Commands commands) {
		super(plugin, commands, "equals");
		
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
			Util.sendMessage(player, Util.colorize("&cThat item does not exist!"));
			Util.sendMessage(player, Util.colorize("&aCurrent Existing Items:"));
			for (Item item : itemManager.values()) {
				Util.sendMessage(player, Util.colorize(" &7&l- &r(id=" + item.getNamespacedKey() + ") &6\"" + item.getDisplayName() + "&6\""));
			}
			return;
		}
		
		PlayerInventory inventory = player.getInventory();
		ItemStack itemStack = inventory.getItem(inventory.getHeldItemSlot());
		if (itemStack == null || itemStack.getType() == Material.AIR) {
			Util.sendMessage(player, Util.colorize("&cPlease hold a item!"));
			return;
		}
		
		Item item = itemManager.get(namespacedKey);
		player.sendMessage("Item name: " + item.getDisplayName());
		player.sendMessage("Item material: " + item.getMaterial());
		player.sendMessage("Item stack size: " + item.getMaxStackSize());
		player.sendMessage("Item enchanted: " + item.isEnchanted());
		Util.sendMessage(player, Util.colorize(item.equalsStack(itemStack) ? "&aThey equal!" : "&cThey do not equal!"));
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
