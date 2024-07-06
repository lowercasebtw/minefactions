package io.github.lowercasebtw.minefactions.manager;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import io.github.lowercasebtw.minefactions.util.SerializeUtil;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Faction {
    private String name;
    private String description;
    private UUID leader;
    private final List<UUID> members;
    private int killCount;
    private int dtr;

    private Location homeLocation;
    private BoundingBox boundingBox;

    public Faction(String name, String description, UUID leader, List<UUID> members, int killCount, int dtr,
            Location homeLocation, BoundingBox boundingBox) {
        this.name = name;
        this.description = description;
        this.leader = leader;
        this.members = members;
        this.killCount = killCount;
        this.dtr = dtr;
        this.homeLocation = homeLocation;
        this.boundingBox = boundingBox;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getLeaderUUID() {
        return leader;
    }

    public void setLeaderUUID(UUID leader) {
        this.leader = leader;
    }

    public List<UUID> getMemberUUIDs() {
        return members;
    }

    public void addMember(UUID memberUUID) {
        members.add(memberUUID);
    }

    public boolean isMember(UUID uuid) {
        return leader.equals(uuid) || members.contains(uuid);
    }

    public boolean isMember(Player player) {
        return isMember(player.getUniqueId());
    }

    public int getKillCount() {
        return killCount;
    }

    public void setKillCount(int killCount) {
        this.killCount = killCount;
    }

    public int getDTR() {
        return dtr;
    }

    public void setDTR(int dtr) {
        if (dtr < 0)
            dtr = 0;
        this.dtr = dtr;
    }

    public Location getHome() {
        return homeLocation;
    }

    public void setHome(Location homeLocation) {
        this.homeLocation = homeLocation;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public ConfigurationSection saveFaction(ConfigurationSection factionsSection) {
        ConfigurationSection section = factionsSection.createSection(leader.toString());

        section.set("name", name);
        section.set("description", description);
        section.set("members", members.stream().map(UUID::toString).toList());
        section.set("kills", killCount);
        section.set("dtr", dtr);

        if (homeLocation != null) {
            ConfigurationSection homeSection = section.createSection("home");
            section.set("home", SerializeUtil.saveLocation(homeLocation, homeSection));
        }

        if (boundingBox != null) {
            ConfigurationSection claimSection = section.createSection("claim");
            section.set("claim", SerializeUtil.saveBoundingBox(this.boundingBox, claimSection));
        }
        
        return factionsSection;
    }

    public static Faction loadFaction(ConfigurationSection section) {
        // section name is leader uuid
        try {
            if (section == null) {
                throw new Exception("Failed to load faction. Reason: Configuration section is null");
            }

            String name = section.getString("name");
            if (name == null) {
                throw new Exception("No name for faction '" + section.getName() + "' in config");
            }

            String description = section.getString("description", "(empty)");
            UUID leader = UUID.fromString(section.getName());

            List<String> membersUUIDstrings = section.getStringList("members");
            List<UUID> membersUUIDs = membersUUIDstrings.stream().map(UUID::fromString).toList();

            int killCount = section.getInt("kills", 0);
            int dtr = section.getInt("dtr", 2);
            
            ConfigurationSection homeSection = section.getConfigurationSection("home");
            Location homeLocation = null;
            if (homeSection != null) {
                homeLocation = SerializeUtil.loadLocation(homeSection);
            }
            
            ConfigurationSection claimSection = section.getConfigurationSection("claim");
            BoundingBox boundingBox = null;
            if (claimSection != null) {
                boundingBox = SerializeUtil.loadBoundingBox(claimSection);
            }

            return new Faction(name, description, leader, membersUUIDs, killCount, dtr, homeLocation, boundingBox);
        } catch (Exception exception) {
            Logger logger = MineFactionsPlugin.getInstance().getLogger();
            logger.log(Level.SEVERE, "Failed to load faction", exception);
            return null;
        }
    }
}
