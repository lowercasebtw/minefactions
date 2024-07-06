package io.github.lowercasebtw.minefactions.items.item;

import io.github.lowercasebtw.minefactions.items.Item;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import java.util.List;

public class ClaimWandItem extends Item {
	public ClaimWandItem() {
		super(
			Util.identifier("claim_wand"),
			Material.GOLDEN_HOE,
			"&eClaim Wand",
			List.of()
		);
	}
}
