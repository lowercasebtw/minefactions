package io.github.lowercasebtw.minefactions;

import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.expansion.FactionExpansion;
import io.github.lowercasebtw.minefactions.items.Items;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.manager.ScoreboardManager;
import io.github.lowercasebtw.minefactions.manager.WandManager;
import io.github.lowercasebtw.minefactions.util.ChatFilter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public final class MineFactionsPlugin extends JavaPlugin {
	private FactionManager factionManager;
	private ItemManager itemManager;
	private ScoreboardManager scoreboardManager;
	private WandManager wandManager;
	
	private static MineFactionsPlugin plugin;
	
	@Override
	public void onEnable() {
		plugin = this; // TODO: Get rid of bruh, I was so close
		
		// Config
		File dataFolder = getDataFolder();
		if (!dataFolder.exists()) {
			dataFolder.mkdirs();
		}
		
		saveDefaultConfig();
		reloadConfig();
		
		// Initialize all custom items
		Items.initialize(); // Requires a plugin?
		
		// Initialize all commands
		try {
			new Commands(this); // Requires a plugin
		} catch (InvocationTargetException | InstantiationException | IllegalAccessException exception) {
			getLogger().severe("Failed to load minefactions plugin! Commands failed to register. Shutting down..");
			exception.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
			return;
		}
		
		// Initialize the Chat Filter
		ChatFilter.initialize(this); // Requires a plugin
		
		factionManager = new FactionManager(this);
		factionManager.loadFactions();
		itemManager = new ItemManager(this);
		scoreboardManager = new ScoreboardManager();
		wandManager = new WandManager(this);
		
		getServer().getPluginManager().registerEvents(factionManager, this);
		getServer().getPluginManager().registerEvents(itemManager, this);
		
		// Register the placeholder if placeholder-api is installed
		if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			new FactionExpansion(this).register();
		}
	}
	
	@Override
	public void onDisable() {
		factionManager.saveFactions();
	}
	
	public FactionManager getFactionManager() {
		return factionManager;
	}
	
	public ItemManager getItemManager() {
		return itemManager;
	}
	
	public ScoreboardManager getScoreboardManager() {
		return scoreboardManager;
	}
	
	public WandManager getWandManager() {
		return wandManager;
	}
	
	public static MineFactionsPlugin getInstance() {
		return plugin;
	}
}
