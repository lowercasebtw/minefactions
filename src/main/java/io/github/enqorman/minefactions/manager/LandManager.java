package io.github.enqorman.minefactions.manager;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.bukkit.Location;

import com.sk89q.worldedit.entity.Player;

public class LandManager implements Manager {
    Map<UUID, List<Location>> playerLocationCache = new HashMap<>();

    // idk how to lay this out right now
    // but this will be the manager for setting either Faction Home or the claim
    // corners

    public List<Location> getLocationsFromPlayer(Player player) {
        return playerLocationCache.get(player.getUniqueId());
    }

    public List<Location> getLocationsFromUUID(UUID uuid) {
        return playerLocationCache.get(uuid);
    }
}
