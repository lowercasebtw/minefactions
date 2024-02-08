package io.github.enqorman.minefactions.util;

import java.util.logging.Logger;
import java.util.logging.Level;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import io.github.enqorman.minefactions.MineFactionsPlugin;

public class SerializeUtil {
    public static ConfigurationSection saveLocation(Location location, ConfigurationSection section) {
        section.set("x", location.getX());
        section.set("y", location.getY());
        section.set("z", location.getZ());
        section.set("world", location.getWorld().getName());
        section.set("yaw", location.getYaw());
        section.set("pitch", location.getPitch());
        return section;
    }

    public static Location loadLocation(ConfigurationSection section) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        try {
            double x = section.getDouble("x");
            double y = section.getDouble("y");
            double z = section.getDouble("z");
            String worldName = section.getString("world");
            float yaw = (float) section.getDouble("yaw");
            float pitch = (float) section.getDouble("pitch");
            return new Location(plugin.getServer().getWorld(worldName), x, y, z, yaw, pitch);
        } catch (Exception exception) {
            Logger logger = plugin.getLogger();
            logger.log(Level.SEVERE, "Failed to load Location");
            logger.log(Level.SEVERE, exception.getLocalizedMessage());
            return new Location(null, 0, 0, 0, 0, 0);
        }
    }
}
