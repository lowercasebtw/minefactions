package io.github.lowercasebtw.minefactions.util;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.BoundingBox;

import javax.annotation.Nullable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializeUtil {
    public static ConfigurationSection saveLocation(Location location, ConfigurationSection section) {
        if (location != null) {
            section.set("x", location.getX());
            section.set("y", location.getY());
            section.set("z", location.getZ());
            
            World world = location.getWorld();
            if (world != null) {
                section.set("world", location.getWorld().getName());
            }
            
            section.set("yaw", location.getYaw());
            section.set("pitch", location.getPitch());
        }
        
        return section;
    }

    public static Location loadLocation(ConfigurationSection section) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        try {
            double x = section.getDouble("x", 0);
            double y = section.getDouble("y", 0);
            double z = section.getDouble("z", 0);
            // Hacky but will make sure it works hopefully all the time
            String worldName = section.getString("world", plugin.getServer().getWorlds().get(0).getName());
            float yaw = (float) section.getDouble("yaw", 0);
            float pitch = (float) section.getDouble("pitch", 0);
			return new Location(plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);
        } catch (Exception exception) {
            Logger logger = plugin.getLogger();
            logger.log(Level.SEVERE, "Failed to load location from config.");
            logger.log(Level.SEVERE, exception.getLocalizedMessage());
            return new Location(null, 0, 0, 0, 0, 0);
        }
    }
    
    public static ConfigurationSection saveBoundingBox(BoundingBox boundingBox, ConfigurationSection section) {
        if (boundingBox != null) {
            section.set("minX", boundingBox.getMinX());
            section.set("minY", boundingBox.getMinY());
            section.set("minZ", boundingBox.getMinZ());
            section.set("maxX", boundingBox.getMaxX());
            section.set("maxY", boundingBox.getMaxY());
            section.set("maxZ", boundingBox.getMaxZ());
        }
        
        return section;
    }
    
    @Nullable
    public static BoundingBox loadBoundingBox(ConfigurationSection section) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        try {
            if (!section.contains("minX") ||
                !section.contains("minY") ||
                !section.contains("minZ") ||
                !section.contains("maxX") ||
                !section.contains("maxY") ||
                !section.contains("maxZ")) {
                throw new Exception("Bounding box section was missing crucial data!");
            }
            
            double minX = section.getDouble("minX");
            double minY = section.getDouble("minY");
            double minZ = section.getDouble("minZ");
            double maxX = section.getDouble("maxX");
            double maxY = section.getDouble("maxY");
            double maxZ = section.getDouble("maxZ");
            
            return new BoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
        } catch (Exception exception) {
            Logger logger = plugin.getLogger();
            logger.log(Level.SEVERE, "Failed to load faction claim from config.");
            logger.log(Level.SEVERE, exception.getLocalizedMessage());
            return null;
        }
    }
}
