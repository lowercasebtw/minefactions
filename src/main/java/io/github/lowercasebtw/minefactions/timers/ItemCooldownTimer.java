package io.github.lowercasebtw.minefactions.timers;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class ItemCooldownTimer extends BukkitRunnable {
	private int currentTicks = 0;
	private final UUID playerUuid;
	private final Item item;
	
	public ItemCooldownTimer(UUID playerUuid, CooldownItem item) {
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
			OfflinePlayer offlinePlayer = MineFactionsPlugin.getInstance().getServer().getOfflinePlayer(playerUuid);
			if (offlinePlayer.isOnline())
				Util.sendMessage(offlinePlayer.getPlayer(), "&aYour &r[" + item.getDisplayName() + "&r]&a cooldown has ended!");
			this.cancel();
			ItemManager.removeCooldown(offlinePlayer.getUniqueId(), item);
			return;
		}
		
		currentTicks--;
	}
}
