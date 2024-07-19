package io.github.lowercasebtw.minefactions.manager;

import dev.jcsoftware.jscoreboards.JScoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ScoreboardManager {
	private Map<UUID, JScoreboard> scoreboards;
	
	public ScoreboardManager() {
		this.scoreboards = new HashMap<>();
	}
	
	public JScoreboard getScoreboardFor(UUID uuid) {
		return this.scoreboards.get(uuid);
	}
	
	public void addScoreboard(UUID uuid, JScoreboard scoreboard) {
		this.scoreboards.put(uuid, scoreboard);
	}
	
	public boolean hasScoreboard(UUID uuid) {
		return this.scoreboards.containsKey(uuid);
	}
}
