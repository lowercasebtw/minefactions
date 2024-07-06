package io.github.lowercasebtw.minefactions.timers;

import org.bukkit.HeightMap;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BoundingBox;

public class ParticleTimer extends BukkitRunnable {
	private int currentTicks = 0;
	private final Player player;
	private final World world;
	private final BoundingBox boundingBox;
	
	public ParticleTimer(Player player, World world, BoundingBox boundingBox) {
		this.player = player;
		this.world = world;
		this.boundingBox = boundingBox;
	}
	
	private void spawnParticle(double x, double z, double minY, double maxY) {
		for (double y = minY; y < maxY; ++y) {
			this.player.spawnParticle(Particle.COMPOSTER, x, y, z, 1);
		}
	}
	
	@Override
	public void run() {
		if (currentTicks % 5 == 0) {
			BoundingBox boundingBox = this.boundingBox.clone().expand(0, 0, 0, 1, 0, 1);
			for (double x = boundingBox.getMinX(); x < boundingBox.getMaxX(); ++x) {
				for (double z = boundingBox.getMinZ(); z < boundingBox.getMaxZ(); ++z) {
					if ((x > boundingBox.getMinX() && x < boundingBox.getMaxX() - 1) && (z > boundingBox.getMinZ() && z < boundingBox.getMaxZ() - 1))
						continue;
					double y = world.getHighestBlockYAt((int) x, (int) z, HeightMap.MOTION_BLOCKING);
					spawnParticle(x, z, y, y + 10);
				}
			}
		}
		
		currentTicks++;
	}
}