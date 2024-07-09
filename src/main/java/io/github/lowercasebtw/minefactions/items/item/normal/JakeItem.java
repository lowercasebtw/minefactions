package io.github.lowercasebtw.minefactions.items.item.normal;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class JakeItem extends Item {
	private final boolean isGolden;
	
	public JakeItem(boolean isGolden, boolean isEnchanted) {
		super(
			Util.identifier(isGolden ? (isEnchanted ? "enchanted_golden_jake" : "golden_jake") : "jake"),
			Material.APPLE,
			"&9&lJake",
			List.of(
				"&7he is very stinky" + (isGolden ? " and delicious" : "")
			),
			1,
			isEnchanted
		);
		this.isGolden = isGolden;
		if (this.isGolden) {
			this.setDisplayName((this.isEnchanted() ? "&b&l&o" : "&l&6") + "Jake");
		}
	}
	
	public int getCustomModelData() {
		return isGolden ? (this.isEnchanted() ? 71 : 70) : 69;
	}
	
	public boolean isGolden() {
		return isGolden;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof JakeItem other) {
			return super.equals(object) && other.isGolden == this.isGolden && other.getCustomModelData() == this.getCustomModelData();
		}
		return super.equals(object);
	}
	
	@Override
	public ItemStack getStack() {
		ItemStack stack = super.getStack();
		ItemMeta meta = stack.getItemMeta();
		
		meta.setCustomModelData(this.getCustomModelData());
		
		FoodComponent foodComponent = meta.getFood();
		foodComponent.setCanAlwaysEat(true);
		foodComponent.setEatSeconds(1.6F);
		foodComponent.setNutrition(4);
		
		if (this.isGolden()) {
			foodComponent.setSaturation(1.2F);
			if (!this.isEnchanted()) {
				foodComponent.addEffect(PotionEffectType.REGENERATION.createEffect(100, 1), 1.0F);
				foodComponent.addEffect(PotionEffectType.ABSORPTION.createEffect(2400, 1), 1.0F);
			} else {
				foodComponent.addEffect(PotionEffectType.REGENERATION.createEffect(400, 1), 1.0F);
				foodComponent.addEffect(PotionEffectType.ABSORPTION.createEffect(2400, 3), 1.0F);
				foodComponent.addEffect(PotionEffectType.RESISTANCE.createEffect(6000, 0), 1.0F);
				foodComponent.addEffect(PotionEffectType.FIRE_RESISTANCE.createEffect(6000, 0), 1.0F);
			}
		} else {
			foodComponent.setSaturation(0.3F);
		}
		
		meta.setFood(foodComponent);
		
		stack.setItemMeta(meta);
		return stack;
	}
}
