package io.github.lowercasebtw.minefactions.items.item.cooldown;

import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class SwapperItem extends CooldownItem {
	public SwapperItem() {
		super(
			Util.identifier("swapper"),
			Material.EGG,
			"&6Swapper",
			List.of("&7Swaps the player's and the opponent's positions"),
			4,
			15
		);
	}
}
