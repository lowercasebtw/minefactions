package io.github.lowercasebtw.minefactions.manager;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.items.item.normal.ClaimWandItem;
import io.github.lowercasebtw.minefactions.items.item.normal.JakeItem;
import io.github.lowercasebtw.minefactions.timers.ItemCooldownTimer;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.*;

public class ItemManager implements Listener {
	public static final NamespacedKey CUSTOM_ITEM_IDENTIFIER_NAMESPACE = Util.identifier("custom_item_identifier");
	
	private static MineFactionsPlugin plugin;
	private static final Map<NamespacedKey, Item> ITEMS = new HashMap<>();
	private static final Map<UUID, List<ItemCooldownTimer>> COOLDOWN_TIMERS = new HashMap<>();
	
	public ItemManager(MineFactionsPlugin plugin) {
		this.plugin = plugin;
	}
	
	public void register(Item item) {
		if (has(item.getNamespacedKey())) {
			throw new Error("Item with identifier '" + item.getNamespacedKey() + "' already registered!");
		}
		
		ITEMS.put(item.getNamespacedKey(), item);
	}
	
	public boolean giveItem(Player player, Item item) {
		PlayerInventory inventory = player.getInventory();
		if (inventory.firstEmpty() == -1)
			return false;
		inventory.addItem(item.getStack());
		return true;
	}
	
	public boolean has(NamespacedKey identifier) {
		return ITEMS.containsKey(identifier);
	}
	
	public Item get(NamespacedKey identifier) {
		return ITEMS.get(identifier);
	}
	
	public int getCooldown(Player player, Item item) {
		if (!COOLDOWN_TIMERS.containsKey(player.getUniqueId()))
			return -1;
		for (ItemCooldownTimer timer : COOLDOWN_TIMERS.get(player.getUniqueId())) {
			if (timer.getItem().equals(item)) {
				return timer.getTicks();
			}
		}
		return -1;
	}
	
	public void startCooldown(UUID uuid, CooldownItem item) {
		if (!COOLDOWN_TIMERS.containsKey(uuid))
			COOLDOWN_TIMERS.put(uuid, new ArrayList<>());
		List<ItemCooldownTimer> timers = COOLDOWN_TIMERS.get(uuid);
		ItemCooldownTimer timer = new ItemCooldownTimer(plugin, uuid, item);
		timer.runTaskTimer(plugin, 1, 1);
		timers.add(timer);
	}
	
	public void removeCooldown(UUID uuid, Item item) {
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
	
	public boolean isCustomItem(ItemStack itemStack) {
		for (Item item : values()) {
			if (item.equalsStack(itemStack)) {
				return true;
			}
		}
		return false;
	}
	
	public Item getItemForStack(ItemStack itemStack) {
		if (!isCustomItem(itemStack))
			return null;
		for (Item item : values()) {
			if (item.equalsStack(itemStack))
				return item;
		}
		return null;
	}
	
	public Set<NamespacedKey> keySet() {
		return ITEMS.keySet();
	}
	
	public Collection<Item> values() {
		return ITEMS.values();
	}
	
	// Events
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		ItemStack itemStack = event.getItem();
		if (itemStack == null || !this.isCustomItem(itemStack)) {
			return;
		}
		
		Item item = this.getItemForStack(itemStack);
		if (item == null) {
			return;
		}
		
		// TODO: Components
		if (item instanceof JakeItem) {
			return;
		}
		
		event.setCancelled(true);
		event.setUseItemInHand(Event.Result.DENY);
		if (item instanceof ClaimWandItem) {
			WandManager wandManager = plugin.getWandManager();
			
			Action action = event.getAction();
			if (action == Action.RIGHT_CLICK_AIR) {
				plugin.getFactionManager().claimRegion(wandManager, player);
				return;
			}
			
			Block block = event.getClickedBlock();
			if (block == null)
				return;
			
			WandManager.ClaimHandle claimHandle = wandManager.getHandle(player.getUniqueId());
			if (action == Action.LEFT_CLICK_BLOCK) {
				Util.sendMessage(player, "&aSet 1st corner of claim");
				claimHandle.setTopLeft(block.getLocation());
			} else {
				Util.sendMessage(player, "&aSet 2st corner of claim");
				claimHandle.setBottomRight(block.getLocation());
			}
		} else if (item instanceof CooldownItem cooldownItem) {
			int cooldownTicks = this.getCooldown(player, item);
			if (cooldownTicks == -1) {
				Util.sendMessage(player, "&aYou used the item!");
				this.startCooldown(player.getUniqueId(), cooldownItem);
				return;
			}
			
			int seconds = cooldownTicks / 20;
			Util.sendMessage(player, "&cYou cannot use the item again for " + seconds + (seconds > 1 ? "s" : ""));
		}
	}
}
