package io.github.lowercasebtw.minefactions.items.item.cooldown;

import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class BlinderItem extends CooldownItem {
	public BlinderItem() {
		super(
			Util.identifier("blinder"),
			Material.CARVED_PUMPKIN,
			"&6Blinder",
			List.of("&7Blinds the player with a pumpkin"),
			1,
			20
		);
		
//		effects: blindness and darkness 3 for 10 seconds
//		enchantments: curse of binding
	}
}
