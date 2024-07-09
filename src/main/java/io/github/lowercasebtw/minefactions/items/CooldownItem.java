package io.github.lowercasebtw.minefactions.items;

import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

public class CooldownItem extends Item {
	private final int cooldownTicks;
	
	public CooldownItem(NamespacedKey namespacedKey, Material material, String displayName, List<String> lore, int maxStackSize, int cooldownTicks) {
		super(namespacedKey, material, displayName, lore, maxStackSize, false);
		this.cooldownTicks = cooldownTicks;
	}
	
	public int getCooldownTicks() {
		return cooldownTicks;
	}
	
	public int getCooldownSeconds() {
		return cooldownTicks / 20;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof CooldownItem other) {
			return super.equals(object) && other.cooldownTicks == this.cooldownTicks;
		}
		return super.equals(object);
	}
	
	@Override
	public ItemStack getStack() {
		ItemStack stack = super.getStack();
		ItemMeta meta = stack.getItemMeta();
		assert meta != null;
		List<String> lore = Objects.requireNonNullElse(meta.getLore(), List.of());
		if (this.getCooldownSeconds() > 0) {
			lore.add("");
			lore.add("&6Cooldown: &b" + this.getCooldownSeconds() + " second" + (this.getCooldownSeconds() > 1 ? "s" : ""));
		}
		meta.setLore(lore.stream().map(Util::colorize).toList());
		stack.setItemMeta(meta);
		return stack;
	}
}
