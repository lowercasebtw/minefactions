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

public final class MineFactionsPlugin extends JavaPlugin {
    private static MineFactionsPlugin instance;

    private FactionManager factionManager;
    private ItemManager itemManager;
    private WandManager wandManager;

    @Override
    public void onEnable() {
        instance = this;
        
        saveDefaultConfig();
        reloadConfig();
        
        // Initialize all custom items
        Items.initialize();
        
        // Initialize all commands
        Commands.initialize();
        
        // Initialize the Chat Filter
        ChatFilter.initialize();

        factionManager = new FactionManager();
        factionManager.loadFactions();
        
        itemManager = new ItemManager();
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

    public static MineFactionsPlugin getInstance() {
        return instance;
    }

    public FactionManager getFactionManager() {
        return factionManager;
    }
    
    public WandManager getWandManager() {
        return wandManager;
    }
}
