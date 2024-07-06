package io.github.lowercasebtw.minefactions.items.item;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class BlinderItem extends Item {
	public BlinderItem() {
		super(
			Util.identifier("blinder"),
			Material.CARVED_PUMPKIN,
			1,
			20,
			"&6Blinder",
			List.of("&7Blinds the player with a pumpkin")
		);
		
//		effects: blindness and darkness 3 for 10 seconds
//		enchantments: curse of binding
	}
}
