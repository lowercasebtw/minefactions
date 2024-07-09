package io.github.lowercasebtw.minefactions.manager;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.timers.DTRTimer;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;

public class FactionManager implements Listener {
    private final List<Faction> factions = new LinkedList<>();

    private final DTRTimer timer = new DTRTimer();

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
    public Faction getFactionByUUID(UUID uuid) {
        return factions.stream().filter(faction -> faction.getLeaderUUID().equals(uuid)
                || faction.getMemberUUIDs().contains(uuid)).findFirst().orElse(null);
    }
    
    public Faction getFactionByPlayer(Player player) {
        return getFactionByUUID(player.getUniqueId());
    }

    private static final String STRIP_COLOR_PATTERN = "&[a-z]";

    @Nullable
    public Faction findFactionByName(String name, boolean stripColor) {
        return factions.stream().filter(faction -> {
            String factionName = stripColor ? faction.getName() : faction.getName().replaceAll(STRIP_COLOR_PATTERN, "");
            return factionName.equalsIgnoreCase(stripColor ? name : name.replaceAll(STRIP_COLOR_PATTERN, ""));
        }).findFirst().orElse(null);
    }
    
    public static void claimRegion(WandManager wandManager, Player player) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();
        Faction faction = factionManager.getFactionByPlayer(player);
        if (faction == null) {
            Util.sendMessage(player, "&cYou are not in a faction!");
            return;
        }
        
        if (!faction.getLeaderUUID().equals(player.getUniqueId())) {
            Util.sendMessage(player, "&cOnly the faction leader can do this!");
            return;
        }
        
        if (faction.getBoundingBox() != null) {
            Util.sendMessage(player, "&cThe faction already has a claim! Run /faction unclaim to make a new claim.");
            return;
        }
        
        WandManager.ClaimHandle claimHandle = wandManager.getHandle(player.getUniqueId());
        
        Location topLeft = claimHandle.getTopLeft();
        if (topLeft == null) {
            Util.sendMessage(player, "&cYou must set a 1st corner for the claim");
            return;
        }
        
        Location bottomRight = claimHandle.getBottomRight();
        if (bottomRight == null) {
            Util.sendMessage(player, "&cYou must set a 2nd corner for the claim");
            return;
        }
        
        BoundingBox boundingBox = claimHandle.getBoundingBox();
        boundingBox.expand(new Vector(1, 0, 1), 1);
        if (boundingBox.getWidthX() > 32 || boundingBox.getWidthZ() > 32) {
            Util.sendMessage(player, "&cClaim's cannot be greater than size 16x16!");
            return;
        }
        
        for (Faction other : factionManager.getFactions()) {
            BoundingBox otherClaimHandle = other.getBoundingBox();
            if (otherClaimHandle == null)
                continue;
            if (otherClaimHandle.contains(boundingBox)) {
                Util.sendMessage(player, "&cClaim's cannot be intersect with someone else's faction claim!");
                return;
            }
        }
        
        faction.setBoundingBox(boundingBox);
        Util.sendMessage(player, "&aClaimed!");
        wandManager.removeWand(player);
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
        timer.runTaskTimer(plugin, 0, 1);
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
    
    // Events
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();
        Block block = event.getBlock();
        Player player = event.getPlayer();
        for (Faction faction : factionManager.getFactions()) {
            if (faction.getBoundingBox() == null)
                continue;
            BoundingBox boundingBox = faction.getBoundingBox();
            if (boundingBox.contains(block.getBoundingBox()) && !faction.isMember(player)) {
                event.setCancelled(true);
                Util.sendMessage(player, "&cYou cannot break blocks in someone else's claim!");
            }
        }
    }
    
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();
        for (Faction faction : factionManager.getFactions()) {
            BoundingBox boundingBox = faction.getBoundingBox();
            if (boundingBox == null)
                continue;
            Location location = player.getLocation();
            if (boundingBox.contains(new Vector(location.getX(), location.getY(), location.getZ())) && !faction.isMember(player.getUniqueId())) {
                event.setCancelled(true);
                Util.sendMessage(player, "&cYou cannot interact with stuff inside someone else's faction!");
            }
        }
    }
}
