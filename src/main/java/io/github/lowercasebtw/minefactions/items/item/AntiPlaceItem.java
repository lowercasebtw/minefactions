package io.github.lowercasebtw.minefactions.items.item;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class AntiPlaceItem extends Item {
	public AntiPlaceItem() {
		super(
				Util.identifier("anti_place"),
				Material.BRICK,
				4,
				45,
				"&cAnti-Place",
				List.of("&7When a player is hit with this, they can't place blocks for 10 seconds")
		);
		
		// TODO: durability 20
		// TODO: set other player placing ability cooldown to 10 sec
	}
}
