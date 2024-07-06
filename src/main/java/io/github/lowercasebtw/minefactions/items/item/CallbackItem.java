package io.github.lowercasebtw.minefactions.items.item;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class CallbackItem extends Item {
	public CallbackItem() {
		super(
			Util.identifier("callback"),
			Material.CLOCK,
			4,
			60,
			"&eCallback",
			List.of("&7Rewinds the player back 10 seconds"));
	}
}
