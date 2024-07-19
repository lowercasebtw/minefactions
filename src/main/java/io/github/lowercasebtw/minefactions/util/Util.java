package io.github.lowercasebtw.minefactions.util;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Util {
    public static String NAMESPACE = "minefactions";
    
    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static void sendMessage(CommandSender sender, String message) {
        if (!(sender instanceof Player))
            message = ChatColor.stripColor(message);
        else
            message = colorize(message);
        sender.sendMessage(message);
    }
    
    public static NamespacedKey identifier(String key) {
        return NamespacedKey.fromString(NAMESPACE + ":" + key);
    }
}
