package io.github.lowercasebtw.minefactions.items.item.cooldown;

import io.github.lowercasebtw.minefactions.items.CooldownItem;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class InfinityPearlItem extends CooldownItem {
	public InfinityPearlItem() {
		super(
			Util.identifier("infinity_pearl"),
			Material.ENDER_PEARL,
			"&dInfinity Pearl",
			List.of("&7Infinite Ender Pearl that never runs out"),
			1,
			15
		);
	}
}
