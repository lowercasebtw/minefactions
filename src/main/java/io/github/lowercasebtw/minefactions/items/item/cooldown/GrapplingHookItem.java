package io.github.lowercasebtw.minefactions.items.item.cooldown;

import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class GrapplingHookItem extends CooldownItem {
	public GrapplingHookItem() {
		super(
			Util.identifier("grappling_hook"),
			Material.FISHING_ROD,
			"&7Grappling Hook",
			List.of("&7Pull the player towards the rods hook"),
			1,
			10
		);
		
		// TODO: durability 20
	}
}
