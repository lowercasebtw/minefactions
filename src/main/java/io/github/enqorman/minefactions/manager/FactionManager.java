package io.github.enqorman.minefactions.manager;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import io.github.enqorman.minefactions.timers.DTRTimer;

import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public class FactionManager implements Manager {
    private final List<Faction> factions = new LinkedList<>();

    private DTRTimer timer = new DTRTimer();

    public List<Faction> getFactions() {
        return factions;
    }

    public void addFaction(Faction faction) {
        factions.add(faction);
    }

    public boolean remove(Faction faction) {
        return factions.removeIf(faction2 -> faction2.equals(faction));
    }

    @Nullable
    public Faction getFactionByPlayer(Player player) {
        return factions.stream().filter(faction -> {
            return faction.getLeaderUUID().equals(player.getUniqueId())
                    || faction.getMemberUUIDs().contains(player.getUniqueId());
        }).findFirst().orElse(null);
    }

    private static String STRIP_COLOR_PATTERN = "&[a-z]";

    @Nullable
    public Faction findFactionByName(String name, boolean stripColor) {
        // i wish this was cleaner bruh
        return factions.stream().filter(faction -> {
            String factionName = faction.getName().replaceAll(STRIP_COLOR_PATTERN, "");
            return factionName.equalsIgnoreCase(name.replaceAll(STRIP_COLOR_PATTERN, ""));
        }).findFirst().orElse(null);
    }

    public boolean loadFactions() {
        factions.clear();

        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        Configuration configuration = plugin.getConfig();

        ConfigurationSection factionsSection = configuration.getConfigurationSection("factions");
        if (factionsSection == null) {
            configuration.createSection("factions");
            return true;
        }

        Set<String> keys = factionsSection.getKeys(false);
        for (String leaderUUID : keys) {
            ConfigurationSection factionSection = factionsSection.getConfigurationSection(leaderUUID);
            Faction faction = Faction.loadFaction(factionSection);
            if (faction == null)
                continue;
            factions.add(faction);
        }

        // Needs a last dtr time set somewhere, this is shit
        // im tired and gonna go to bed
        // fix this later
        timer.runTask(plugin);
        return true;
    }

    public boolean saveFactions() {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        Configuration configuration = plugin.getConfig();
        try {
            ConfigurationSection factionsSection = configuration.getConfigurationSection("factions");
            if (factionsSection == null) {
                configuration.createSection("factions");
                factionsSection = configuration.getConfigurationSection("factions");
            }

            assert factionsSection != null;
            for (Faction faction : factions) {
                faction.saveFaction(factionsSection);
            }

            plugin.saveConfig();
            return true;
        } catch (Exception exception) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save factions!", exception);
            return false;
        }
    }
}
