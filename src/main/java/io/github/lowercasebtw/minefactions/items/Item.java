package io.github.lowercasebtw.minefactions.items;

import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

import static io.github.lowercasebtw.minefactions.manager.ItemManager.CUSTOM_ITEM_IDENTIFIER_NAMESPACE;

public abstract class Item {
	private final NamespacedKey identifier;
	private final Material material;
	private final String displayName;
	private final int maxStackSize;
	private final int cooldownTicks;
	private boolean isSpecial;
	private final List<String> lore;
	
	public Item(NamespacedKey namespacedKey, Material material, int maxStackSize, int cooldownSeconds, String displayName, boolean isSpecial, List<String> lore) {
		this.identifier = namespacedKey;
		this.material = material;
		this.displayName = displayName;
		this.maxStackSize = maxStackSize;
		this.cooldownTicks = cooldownSeconds * 20;
		this.lore = lore;
		ItemManager.register(this);
	}
	
	public Item(NamespacedKey namespacedKey, Material material, int maxStackSize, int cooldownSeconds, String displayName, List<String> lore) {
		this(namespacedKey, material, maxStackSize, cooldownSeconds, displayName, true, lore);
	}
	
	public Item(NamespacedKey namespacedKey, Material material, String displayName, List<String> lore) {
		this(namespacedKey, material, 1, -999999, displayName, false, lore);
	}
	
	public NamespacedKey getIdentifier() {
		return identifier;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public int getMaxStackSize() {
		return maxStackSize;
	}
	
	public int getCooldownTicks() {
		return cooldownTicks;
	}
	
	public int getCooldownSeconds() {
		return cooldownTicks / 20;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public boolean isSpecial() {
		return isSpecial;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Item other) {
			return other.identifier.equals(this.identifier) &&
					other.getMaterial().equals(this.material) &&
					other.getMaxStackSize() == this.maxStackSize &&
					other.getCooldownTicks() == this.cooldownTicks &&
					other.isSpecial() == this.isSpecial &&
					other.getLore().equals(this.lore);
		} else if (object instanceof ItemStack itemStack) {
			return this.equalsStack(itemStack);
		} else {
			return false;
		}
	}
	
	public boolean equalsStack(ItemStack stack) {
		if (!stack.getType().equals(this.getMaterial()))
			return false;
		
		ItemMeta meta = stack.getItemMeta();
		if (meta == null)
			return false;
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if (!container.has(CUSTOM_ITEM_IDENTIFIER_NAMESPACE))
			return false;
		
		String identifier = container.getOrDefault(CUSTOM_ITEM_IDENTIFIER_NAMESPACE, PersistentDataType.STRING, "");
		if (!identifier.equals(this.identifier.toString()))
			return false;
		
		if (!meta.getDisplayName().equals(Util.colorize(this.getDisplayName())))
			return false;
		
		if (meta.getLore() != null) {
			List<String> itemLore = this.getLore().stream().map(Util::colorize).toList();
			if ((!meta.getLore().isEmpty() && itemLore.isEmpty()) || (!itemLore.isEmpty() && meta.getLore().isEmpty()))
				return false;
			for (String line : itemLore) {
				if (!meta.getLore().contains(line))
					return false;
			}
		}
		
		return true;
	}
}
