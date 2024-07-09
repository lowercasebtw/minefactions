package io.github.lowercasebtw.minefactions.items.item.cooldown;

import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class CallbackItem extends CooldownItem {
	public CallbackItem() {
		super(
			Util.identifier("callback"),
			Material.CLOCK,
			"&eCallback",
			List.of("&7Rewinds the player back 10 seconds"),
			4,
			60
		);
	}
}
