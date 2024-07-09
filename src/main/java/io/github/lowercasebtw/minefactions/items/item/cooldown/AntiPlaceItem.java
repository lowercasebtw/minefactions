package io.github.lowercasebtw.minefactions.items.item.cooldown;

import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class AntiPlaceItem extends CooldownItem {
	public AntiPlaceItem() {
		super(
			Util.identifier("anti_place"),
			Material.BRICK,
			"&cAnti-Place",
			List.of("&7When a player is hit with this, they can't place blocks for 10 seconds"),
			4,
			45
		);
		
		// TODO: durability 20
		// TODO: set other player placing ability cooldown to 10 sec
	}
}
