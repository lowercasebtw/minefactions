package io.github.lowercasebtw.minefactions.util;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatFilter {
	private static final List<Pattern> patternCache = new ArrayList<>();
	private static final String formattingRegex = "(\\b|&[a-fA-F0-9klmnorKLMNOR])";
	private static List<String> bannedWords = new ArrayList<>();
	
	public static void initialize(MineFactionsPlugin plugin) {
		patternCache.clear();
		bannedWords = plugin.getConfig().getStringList("banned-words");
		if (!bannedWords.isEmpty()) {
			for (String bannedWord : bannedWords) {
				bannedWord = bannedWord.trim().toLowerCase();
				Pattern pattern = Pattern.compile(formattingRegex + "(" + bannedWord + ")" + formattingRegex,
						Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
				patternCache.add(pattern);
			}
		}
	}
	
	public static boolean isFilteredWord(String word) {
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
