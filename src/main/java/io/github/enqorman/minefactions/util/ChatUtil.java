package io.github.enqorman.minefactions.util;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}
