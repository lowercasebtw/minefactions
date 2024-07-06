package io.github.lowercasebtw.minefactions.manager;

import io.github.lowercasebtw.minefactions.items.Items;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.BoundingBox;

import java.util.*;

public class WandManager {
	public static class ClaimHandle {
		private Location topLeft;
		private Location bottomRight;
		
		public ClaimHandle() {
			this.topLeft = null;
			this.bottomRight = null;
		}
		
		public BoundingBox getBoundingBox() {
			return BoundingBox.of(topLeft, bottomRight);
		}
		
		public Location getTopLeft() {
			return topLeft;
		}
		
		public void setTopLeft(Location topLeft) {
			topLeft.setY(320);
			this.topLeft = topLeft;
		}
		
		public Location getBottomRight() {
			return bottomRight;
		}
		
		public void setBottomRight(Location bottomRight) {
			bottomRight.setY(-64);
			this.bottomRight = bottomRight;
		}
	}
	
	private final Map<UUID, ClaimHandle> claimCache;
	private final List<UUID> wandCache;
	
	public WandManager() {
		this.claimCache = new HashMap<>();
		this.wandCache = new ArrayList<>();
	}
	
	public boolean giveWand(Player player) {
		PlayerInventory inventory = player.getInventory();
		if (inventory.firstEmpty() == -1)
			return false;
		if (wandCache.contains(player.getUniqueId()))
			return false;
		ItemManager.giveItem(player, Items.CLAIM_WAND);
		wandCache.add(player.getUniqueId());
		return true;
	}
	
	public void removeWand(Player player) {
		PlayerInventory inventory = player.getInventory();
		for (ItemStack stack : inventory.getContents()) {
			if (stack != null && ItemManager.isCustomItem(stack) && Items.CLAIM_WAND.equalsStack(stack)) {
				inventory.remove(stack);
			}
		}
		claimCache.remove(player.getUniqueId());
		wandCache.remove(player.getUniqueId());
	}
	
	public boolean hasWand(UUID uuid) {
		return wandCache.contains(uuid);
	}
	
	public ClaimHandle getHandle(UUID uuid) {
		if (!claimCache.containsKey(uuid))
			claimCache.put(uuid, new ClaimHandle());
		return claimCache.get(uuid);
	}
}
