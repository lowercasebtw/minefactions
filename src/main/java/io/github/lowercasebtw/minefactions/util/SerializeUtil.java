package io.github.lowercasebtw.minefactions.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.BoundingBox;

import javax.annotation.Nullable;

public class SerializeUtil {
	public static ConfigurationSection saveLocation(Location location, ConfigurationSection section) {
		if (location == null)
			return null;
		World world = location.getWorld();
		if (world == null)
			return null;
		section.set("world", location.getWorld().getName());
		section.set("x", location.getX());
		section.set("y", location.getY());
		section.set("z", location.getZ());
		section.set("yaw", location.getYaw());
		section.set("pitch", location.getPitch());
		return section;
	}
	
	public static Location loadLocation(ConfigurationSection section) {
		World world = Bukkit.getServer().getWorld(section.getString("world", ""));
		if (world == null)
			return null;
		double x = section.getDouble("x", 0);
		double y = section.getDouble("y", 0);
		double z = section.getDouble("z", 0);
		float yaw = (float) section.getDouble("yaw", 0);
		float pitch = (float) section.getDouble("pitch", 0);
		return new Location(world, x, y, z, yaw, pitch);
	}
	
	public static ConfigurationSection saveBoundingBox(BoundingBox boundingBox, ConfigurationSection section) {
		if (boundingBox == null)
			return null;
		section.set("minX", boundingBox.getMinX());
		section.set("minY", boundingBox.getMinY());
		section.set("minZ", boundingBox.getMinZ());
		section.set("maxX", boundingBox.getMaxX());
		section.set("maxY", boundingBox.getMaxY());
		section.set("maxZ", boundingBox.getMaxZ());
		return section;
	}
	
	@Nullable
	public static BoundingBox loadBoundingBox(ConfigurationSection section) {
		if (!section.contains("minX") ||
				!section.contains("minY") ||
				!section.contains("minZ") ||
				!section.contains("maxX") ||
				!section.contains("maxY") ||
				!section.contains("maxZ"))
			return null;
		double minX = section.getDouble("minX");
		double minY = section.getDouble("minY");
		double minZ = section.getDouble("minZ");
		double maxX = section.getDouble("maxX");
		double maxY = section.getDouble("maxY");
		double maxZ = section.getDouble("maxZ");
		return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}
}
