package io.github.lowercasebtw.minefactions.manager;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.timers.ParticleTimer;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParticleManager {
	private static final Map<UUID, ParticleTimer> timers = new HashMap<>();
	
	public static boolean toggleParticleBoundary(Player player, BoundingBox boundingBox) {
		UUID uuid = player.getUniqueId();
		if (timers.containsKey(uuid)) {
			timers.get(uuid).cancel();;
			timers.remove(uuid);
			return false;
		} else {
			ParticleTimer timer = new ParticleTimer(player, player.getWorld(), boundingBox);
			timers.put(uuid, timer);
			timer.runTaskTimer(MineFactionsPlugin.getInstance(), 0, 1);
			return true;
		}
	}
}
