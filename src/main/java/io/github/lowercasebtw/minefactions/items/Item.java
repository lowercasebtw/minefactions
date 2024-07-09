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
	private final NamespacedKey namespacedKey;
	private Material material;
	private String displayName;
	private final List<String> lore;
	private final int maxStackSize;
	private boolean isEnchanted;
	
	public Item(NamespacedKey namespacedKey, Material material, String displayName, List<String> lore, int maxStackSize, boolean isEnchanted) {
		this.namespacedKey = namespacedKey;
		this.material = material;
		this.displayName = displayName;
		this.lore = lore;
		this.maxStackSize = maxStackSize;
		this.isEnchanted = isEnchanted;
		ItemManager.register(this);
	}
	
	public NamespacedKey getNamespacedKey() {
		return namespacedKey;
	}
	
	public Material getMaterial() {
		return material;
	}
	
	public void setMaterial(Material material) {
		if (this.material == material)
			return;
		this.material = material;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		if (this.displayName.equals(displayName))
			return;
		this.displayName = displayName;
	}
	
	public List<String> getLore() {
		return lore;
	}
	
	public int getMaxStackSize() {
		return maxStackSize;
	}
	
	public boolean isEnchanted() {
		return isEnchanted;
	}
	
	public void setEnchanted(boolean enchanted) {
		this.isEnchanted = enchanted;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Item other) {
			System.out.println("meow " + other.getDisplayName() + " " + this.displayName);
			return other.getNamespacedKey().equals(this.namespacedKey) &&
					other.getMaterial().equals(this.material) &&
					other.displayName.equals(this.displayName) &&
					other.maxStackSize == this.maxStackSize &&
					other.isEnchanted == this.isEnchanted &&
					other.getLore().equals(this.lore);
		} else if (object instanceof ItemStack itemStack) {
			return this.equalsStack(itemStack);
		} else {
			return false;
		}
	}
	
	public boolean equalsStack(ItemStack stack) {
		if (!stack.getType().equals(this.getMaterial())) {
			System.out.println("Material didn't match");
			return false;
		}
		
		ItemMeta meta = stack.getItemMeta();
		if (meta == null) {
			System.out.println("Meta was null");
			return false;
		}
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if (!container.has(CUSTOM_ITEM_IDENTIFIER_NAMESPACE)) {
			System.out.println("Didn't have name");
			return false;
		}
		
		String identifier = container.getOrDefault(CUSTOM_ITEM_IDENTIFIER_NAMESPACE, PersistentDataType.STRING, "");
		if (!identifier.equals(this.namespacedKey.toString())) {
			System.out.println("Namespaced key didnt match");
			return false;
		}
		
		if (!meta.getDisplayName().equals(Util.colorize(this.getDisplayName()))) {
			System.out.println("Display name didn't match");
			return false;
		}
		
		if (meta.getLore() != null) {
			List<String> itemLore = this.getLore().stream().map(Util::colorize).toList();
			if ((!meta.getLore().isEmpty() && itemLore.isEmpty()) || (!itemLore.isEmpty() && meta.getLore().isEmpty()))
				return false;
			for (String line : itemLore) {
				if (!meta.getLore().contains(line)) {
					System.out.println("Didn't have lore key " + line);
					return false;
				}
			}
		}
		
		if ((!meta.hasMaxStackSize() || meta.getMaxStackSize() != this.maxStackSize) && this.maxStackSize != 1) {
			System.out.println("Didn't have max stack size");
			return false;
		}
		
		if (!meta.hasEnchantmentGlintOverride() || meta.getEnchantmentGlintOverride() != this.isEnchanted) {
			System.out.println("Didn't have enchant glint override");
			return false;
		}
		
		return true;
	}
	
	public ItemStack getStack() {
		ItemStack itemStack = new ItemStack(this.getMaterial());
		ItemMeta meta = itemStack.getItemMeta();
		assert meta != null;
		
		meta.setDisplayName(Util.colorize(this.getDisplayName()));
		meta.setMaxStackSize(this.getMaxStackSize());
		meta.setLore(this.getLore().stream().map(Util::colorize).toList());
		meta.setEnchantmentGlintOverride(this.isEnchanted);
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(CUSTOM_ITEM_IDENTIFIER_NAMESPACE, PersistentDataType.STRING, this.getNamespacedKey().toString());
		
		itemStack.setItemMeta(meta);
		return itemStack;
	}
}
