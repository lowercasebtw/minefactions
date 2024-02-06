package io.github.enqorman.minefactions.manager;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import net.royawesome.jlibnoise.module.combiner.Min;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class FactionManager implements Manager {
    private final List<Faction> factions = new LinkedList<>();

    public List<Faction> getFactions() {
        return factions;
    }

    @Nullable
    public Faction getFactionByPlayer(Player player) {
        for (Faction faction : factions) {
            if (faction.getLeaderUUID() == player.getUniqueId() || faction.getMembers().contains(player.getUniqueId()))
                return faction;
        }
        return null;
    }

    public void loadFactions() {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        Configuration configuration = plugin.getConfig();

        ConfigurationSection factionsSection = configuration.getConfigurationSection("factions");
        if (factionsSection == null) {
            configuration.set("factions", new LinkedList<>());
            return;
        }

        Set<String> keys = factionsSection.getKeys(false);
        for (String key : keys) {
            ConfigurationSection factionSection = factionsSection.getConfigurationSection(key);
            Faction faction = Faction.loadFaction(factionSection);
            if (faction == null)
                continue;
            factions.add(faction);
        }
    }

    public void saveFactions() {
        Configuration configuration = MineFactionsPlugin.getInstance().getConfig();

        ConfigurationSection factionsSection = configuration.getConfigurationSection("factions");
        if (factionsSection == null) {
            configuration.createSection("factions");
            factionsSection = configuration.getConfigurationSection("factions");
        }

        assert factionsSection != null;
        for (Faction faction : factions) {
            faction.saveFaction(factionsSection);
        }
    }
}
