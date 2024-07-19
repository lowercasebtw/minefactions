package io.github.lowercasebtw.minefactions.timers;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ItemCooldownTimer extends BukkitRunnable {
	private final MineFactionsPlugin plugin;
	private final UUID playerUuid;
	private final Item item;
	private int currentTicks = 0;
	
	public ItemCooldownTimer(MineFactionsPlugin plugin, UUID playerUuid, CooldownItem item) {
		this.plugin = plugin;
		this.playerUuid = playerUuid;
		this.item = item;
		this.currentTicks = item.getCooldownTicks();
	}
	
	public int getTicks() {
		return currentTicks;
	}
	
	public Item getItem() {
		return item;
	}
	
	@Override
	public void run() {
		if (currentTicks <= 0) {
			OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(playerUuid);
			if (offlinePlayer.isOnline())
				Util.sendMessage(offlinePlayer.getPlayer(), "&aYour &r[" + item.getDisplayName() + "&r]&a cooldown has ended!");
			this.cancel();
			plugin.getItemManager().removeCooldown(offlinePlayer.getUniqueId(), item);
			return;
		}
		
		currentTicks--;
	}
}
