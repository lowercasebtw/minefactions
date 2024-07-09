package io.github.lowercasebtw.minefactions.items.item.normal;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;

import java.util.List;

public class ClaimWandItem extends Item {
	public ClaimWandItem() {
		super(
			Util.identifier("claim_wand"),
			Material.GOLDEN_HOE,
			"&eClaim Wand",
			List.of(),
			1,
			true
		);
	}
}
