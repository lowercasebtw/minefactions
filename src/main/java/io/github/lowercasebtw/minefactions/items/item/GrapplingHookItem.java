package io.github.lowercasebtw.minefactions.items.item;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class GrapplingHookItem extends Item {
	public GrapplingHookItem() {
		super(
			Util.identifier("grappling_hook"),
			Material.FISHING_ROD,
			1,
			10,
			"&7Grappling Hook",
			List.of("&7Pull the player towards the rods hook")
		);
		
		// TODO: durability 20
	}
}
