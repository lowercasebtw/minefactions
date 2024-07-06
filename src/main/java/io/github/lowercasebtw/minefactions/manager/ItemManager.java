package io.github.lowercasebtw.minefactions.manager;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.timers.ItemCooldownTimer;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public class ItemManager {
	public static NamespacedKey CUSTOM_ITEM_IDENTIFIER_NAMESPACE = NamespacedKey.fromString("minefactions:custom_item_identifier");
	private static final Map<NamespacedKey, Item> ITEMS = new HashMap<>();
	
	private static final Map<UUID, List<ItemCooldownTimer>> COOLDOWN_TIMERS = new HashMap<>();
	
	public static void register(Item item) {
		if (has(item.getIdentifier())) {
			throw new Error("Item with identifier '" + item.getIdentifier() + "' already registered!");
		}
		
		ITEMS.put(item.getIdentifier(), item);
	}
	
	public static boolean giveItem(Player player, Item item) {
		PlayerInventory inventory = player.getInventory();
		if (inventory.firstEmpty() == -1)
			return false;
		
		ItemStack itemStack = new ItemStack(item.getMaterial());
		ItemMeta meta = itemStack.getItemMeta();
		assert meta != null;
		
		meta.setDisplayName(Util.colorize(item.getDisplayName()));

		List<String> lore = new LinkedList<>(item.getLore());
		if (item.getCooldownSeconds() > 0 && item.isSpecial()) {
			lore.add("");
			lore.add("&6Cooldown: &b" + item.getCooldownSeconds() + " second" + (item.getCooldownSeconds() > 1 ? "s" : ""));
		}
		meta.setLore(lore.stream().map(Util::colorize).toList());
		
		meta.getPersistentDataContainer().set(CUSTOM_ITEM_IDENTIFIER_NAMESPACE, PersistentDataType.STRING, item.getIdentifier().toString());
		
		itemStack.setItemMeta(meta);
		inventory.addItem(itemStack);
		return true;
	}
	
	public static boolean has(NamespacedKey identifier) {
		return ITEMS.containsKey(identifier);
	}
	
	public static Item get(NamespacedKey identifier) {
		return ITEMS.get(identifier);
	}
	
	public static int getCooldown(Player player, Item item) {
		if (!COOLDOWN_TIMERS.containsKey(player.getUniqueId()))
			return -1;
		for (ItemCooldownTimer timer : COOLDOWN_TIMERS.get(player.getUniqueId())) {
			if (timer.getItem().equals(item)) {
				return timer.getTicks();
			}
		}
		return -1;
	}
	
	public static void startCooldown(UUID uuid, Item item) {
		if (!COOLDOWN_TIMERS.containsKey(uuid))
			COOLDOWN_TIMERS.put(uuid, new ArrayList<>());
		List<ItemCooldownTimer> timers = COOLDOWN_TIMERS.get(uuid);
		ItemCooldownTimer timer = new ItemCooldownTimer(uuid, item);
		timer.runTaskTimer(MineFactionsPlugin.getInstance(), 1, 1);
		timers.add(timer);
	}
	
	public static void removeCooldown(UUID uuid, Item item) {
		if (!COOLDOWN_TIMERS.containsKey(uuid))
			return;
		
		List<ItemCooldownTimer> timers = COOLDOWN_TIMERS.get(uuid);
		for (ItemCooldownTimer timer : timers) {
			if (timer.getItem().equals(item)) {
				timer.cancel();
				timers.remove(timer);
				break;
			}
		}
		
		if (timers.isEmpty())
			COOLDOWN_TIMERS.remove(uuid);
	}
	
	public static boolean isCustomItem(ItemStack itemStack) {
		for (Item item : values()) {
			if (item.equalsStack(itemStack)) {
				return true;
			}
		}
		return false;
	}
	
	public static Item getItemForStack(ItemStack itemStack) {
		if (!isCustomItem(itemStack))
			return null;
		for (Item item : values()) {
			if (item.equalsStack(itemStack))
				return item;
		}
		return null;
	}
	
	public static Set<Map.Entry<NamespacedKey, Item>> entries() {
		return ITEMS.entrySet();
	}
	
	public static Set<NamespacedKey> keySet() {
		return ITEMS.keySet();
	}
	
	public static Collection<Item> values() {
		return ITEMS.values();
	}
}
