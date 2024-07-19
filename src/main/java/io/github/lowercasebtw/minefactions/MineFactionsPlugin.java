package io.github.lowercasebtw.minefactions;

import io.github.lowercasebtw.minefactions.commands.Commands;
import io.github.lowercasebtw.minefactions.expansion.FactionExpansion;
import io.github.lowercasebtw.minefactions.items.Items;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.manager.ItemManager;
import io.github.lowercasebtw.minefactions.manager.WandManager;
import io.github.lowercasebtw.minefactions.util.ChatFilter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.InvocationTargetException;

public final class MineFactionsPlugin extends JavaPlugin {
    private static MineFactionsPlugin INSTANCE;
    
    private FactionManager factionManager;
	private ItemManager itemManager;
	private WandManager wandManager;

    @Override
    public void onEnable() {
        INSTANCE = this;
        
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
        ChatFilter.initialize(); // Requires a plugin

        factionManager = new FactionManager(this);
        factionManager.loadFactions();
		itemManager = new ItemManager(); // Requires a plugin
        wandManager = new WandManager();

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
    
    public WandManager getWandManager() {
        return wandManager;
    }
    
    public static MineFactionsPlugin getInstance() {
        return INSTANCE;
    }
}
