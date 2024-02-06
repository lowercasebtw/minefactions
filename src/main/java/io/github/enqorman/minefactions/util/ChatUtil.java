package io.github.enqorman.minefactions.util;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    public static String colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static String filter(String input)
    {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        if (plugin == null)
            return input;

        List<String> bannedWords = plugin.getConfig().getStringList("banned-words");
        if (bannedWords.isEmpty())
            return input;

        for (String word : bannedWords) {
            String bw = word.trim().toLowerCase();
            final Pattern pattern = Pattern.compile("\\b" + bw + "\\b", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
            final Matcher matcher = pattern.matcher(input);
            input = matcher.replaceAll("*".repeat(bw.length()));
        }

        return input;
    }
}
