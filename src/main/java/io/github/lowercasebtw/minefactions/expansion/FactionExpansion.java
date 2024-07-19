package io.github.lowercasebtw.minefactions.expansion;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class FactionExpansion extends PlaceholderExpansion {
	private final MineFactionsPlugin plugin;
	
	public FactionExpansion(MineFactionsPlugin plugin) {
		this.plugin = plugin;
	}
	
	@Override
	@NotNull
	public String getIdentifier() {
		return "minefactions";
	}
	
	@Override
	@NotNull
	public String getAuthor() {
		return "lowercasebtw";
	}
	
	@Override
	@NotNull
	public String getVersion() {
		return "1.0";
	}
	
	@Override
	public String onRequest(OfflinePlayer player, @NotNull String identifier) {
		if (identifier.equalsIgnoreCase("faction")) {
			FactionManager factionManager = plugin.getFactionManager();
			Faction faction = factionManager.getFactionByUUID(player.getUniqueId());
			if (faction == null)
				return null;
			return faction.getName();
		}
		
		return null;
	}
}
