package io.github.enqorman.minefactions;

import io.github.enqorman.minefactions.commands.faction.FactionCommand;
import io.github.enqorman.minefactions.manager.FactionManager;
import io.github.enqorman.minefactions.manager.LandManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.logging.Logger;

public final class MineFactionsPlugin extends JavaPlugin {
    private static MineFactionsPlugin instance;

    private static FactionManager factionManager;
    private static LandManager landManager;

    @Override
    public void onEnable() {
        instance = this;
        Objects.requireNonNull(getCommand("faction")).setExecutor(new FactionCommand());
        saveDefaultConfig();
        reloadConfig();

        factionManager = new FactionManager();
        landManager = new LandManager();

        factionManager.loadFactions();
    }

    @Override
    public void onDisable() {
        factionManager.saveFactions();
    }

    public static MineFactionsPlugin getInstance() {
        return instance;
    }

    public static FactionManager getFactionManager() {
        return factionManager;
    }

    public static LandManager getLandManager() {
        return landManager;
    }
}
