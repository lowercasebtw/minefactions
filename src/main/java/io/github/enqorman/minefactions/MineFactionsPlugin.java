package io.github.enqorman.minefactions;

import io.github.enqorman.minefactions.commands.faction.FactionCommand;
import io.github.enqorman.minefactions.manager.FactionManager;
import io.github.enqorman.minefactions.manager.LandManager;
import io.github.enqorman.minefactions.util.ChatFilter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class MineFactionsPlugin extends JavaPlugin {
    private static MineFactionsPlugin instance;

    private FactionManager factionManager;
    private LandManager landManager;

    @Override
    public void onEnable() {
        instance = this;
        Objects.requireNonNull(getCommand("supersecretfactioncommand")).setExecutor(new FactionCommand());
        saveDefaultConfig();
        reloadConfig();

        ChatFilter.init();

        factionManager = new FactionManager();
        landManager = new LandManager();

        factionManager.loadFactions();

        // TODO
        // getServer().getPluginManager().registerEvents(new BlockBreakEventHandler(),
        // this);
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

    public LandManager getLandManager() {
        return landManager;
    }
}
