package io.github.lowercasebtw.minefactions.items.item;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class SwapperItem extends Item {
	public SwapperItem() {
		super(
			Util.identifier("swapper"),
			Material.EGG,
			4,
			15,
			"&6Swapper",
			List.of("&7Swaps the player's and the opponent's positions")
		);
	}
}
