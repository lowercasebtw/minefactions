package io.github.enqorman.minefactions.util;

import io.github.enqorman.minefactions.MineFactionsPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFilter {
    private static List<Pattern> patternCache = new LinkedList<>();

    public static void init() {
        patternCache.clear();
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        List<String> bannedWords = plugin.getConfig().getStringList("banned-words");
        if (!bannedWords.isEmpty()) {
            final String formattingRegex = "(\\b|&[a-fA-F0-9klmnorKLMNOR])";
            for (String bannedWord : bannedWords) {
                bannedWord = bannedWord.trim().toLowerCase();
                Pattern pattern = Pattern.compile(formattingRegex + "(" + bannedWord + ")" + formattingRegex,
                        Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                patternCache.add(pattern);
            }
        }
    }

    public static boolean isFilteredWord(String word) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        if (plugin == null)
            return false;
        List<String> bannedWords = plugin.getConfig().getStringList("banned-words");
        if (bannedWords.isEmpty())
            return false;
        return bannedWords.contains(word);
    }

    public static String filter(String input) {
        for (Pattern phrase : patternCache) {
            final Matcher matcher = phrase.matcher(input);
            input = matcher.replaceAll(match -> {
                // Get the first group (color) and append the *s and then append the last group
                // (another color)
                return match.group(1) + "*".repeat(match.group(2).length()) + match.group(3);
            });
        }
        return input;
    }
}
