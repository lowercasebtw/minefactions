package io.github.lowercasebtw.minefactions.items.item;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class InfinityPearlItem extends Item {
	public InfinityPearlItem() {
		super(
			Util.identifier("infinity_pearl"),
			Material.ENDER_PEARL,
			1,
			15,
			"&dInfinity Pearl",
			List.of("&7Infinite Ender Pearl that never runs out")
		);
	}
}
