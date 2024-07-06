package io.github.lowercasebtw.minefactions.events;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.items.item.ClaimWandItem;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.manager.WandManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

public class PlayerInteractEventHandler implements Listener {
	@EventHandler
	public void onPlayerInteractFaction(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
		FactionManager factionManager = plugin.getFactionManager();
	
		for (Faction faction : factionManager.getFactions()) {
			BoundingBox boundingBox = faction.getBoundingBox();
			if (boundingBox == null)
				continue;
			Location location = player.getLocation();
			if (boundingBox.contains(new Vector(location.getX(), location.getY(), location.getZ())) && !faction.isMember(player.getUniqueId())) {
				event.setCancelled(true);
				Util.sendMessage(player, "&cYou cannot interact with stuff inside someone else's faction!");
			}
		}
	}

	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		
		ItemStack itemStack = event.getItem();
		if (itemStack == null || !ItemManager.isCustomItem(itemStack)) {
			return;
		}
		
		Item item = ItemManager.getItemForStack(itemStack);
		if (item == null) {
			return;
		}
		
		event.setCancelled(true);
		event.setUseItemInHand(Event.Result.DENY);
		
		if (item instanceof ClaimWandItem) {
			MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
			WandManager wandManager = plugin.getWandManager();
			
			Action action = event.getAction();
			if (action == Action.RIGHT_CLICK_AIR) {
				FactionManager.claimRegion(wandManager, player);
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
		} else if (item.isSpecial()) {
			int cooldownTicks = ItemManager.getCooldown(player, item);
			if (cooldownTicks == -1) {
				Util.sendMessage(player, "&aYou used the item!");
				ItemManager.startCooldown(player.getUniqueId(), item);
				return;
			}
			
			int seconds = cooldownTicks / 20;
			Util.sendMessage(player, "&cYou cannot use the item again for " + seconds + (seconds > 1 ? "s" : ""));
		}
	}
}
