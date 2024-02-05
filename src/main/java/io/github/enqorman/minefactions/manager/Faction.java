package io.github.enqorman.minefactions.manager;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import io.github.enqorman.minefactions.MineFactionsPlugin;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class Faction {
    private String name;
    private String description;
    private String color;
    private UUID leader;
    private final List<UUID> members;
    private int killCount;
    private int dtr;

    private Location homeLocation;
    private ProtectedRegion claimRegion;

    public Faction(String name, String description, String color, UUID leader, List<UUID> members, int killCount, int dtr, Location homeLocation, ProtectedRegion claimRegion) {
        this.name = name;
        this.description = description;
        this.color = color;
        this.leader = leader;
        this.members = members;
        this.killCount = killCount;
        this.dtr = dtr;
        this.homeLocation = homeLocation;
        this.claimRegion = claimRegion;
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

    public String getColor() {
        return color;
    }

    public Color getBukkitColor() {
        switch (color.toLowerCase()) {
            case "red":
                return Color.RED;
            case "green":
                return Color.GREEN;
            case "lime":
                return Color.LIME;
            case "olive":
                return Color.OLIVE;
            case "black":
                return Color.BLACK;
            case "blue":
                return Color.BLUE;
            case "aqua":
                return Color.AQUA;
            case "teal":
                return Color.TEAL;
            case "purple":
                return Color.PURPLE;
            case "silver":
                return Color.SILVER;
            case "navy":
                return Color.NAVY;
            case "maroon":
                return Color.MAROON;
            case "fuchsia":
                return Color.FUCHSIA;
            case "yellow":
                return Color.YELLOW;
            case "orange":
                return Color.ORANGE;
        }
        MineFactionsPlugin.getInstance().getLogger().log(Level.WARNING, "Faction '" + name + "' has color '" + color + "' which is not recognised or not a bukkit color.");
        return Color.WHITE;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public UUID getLeaderUUID() {
        return leader;
    }

    public void setLeaderUUID(UUID leader) {
        this.leader = leader;
    }

    public List<UUID> getMembers() {
        return members;
    }

    public void addMember(UUID memberUUID) {
        members.add(memberUUID);
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
        this.dtr = dtr;
    }

    // TODO
    public Location getHomeLocation() {
        return homeLocation;
    }

    public void setHomeLocation(Location homeLocation) {
        this.homeLocation = homeLocation;
    }

    public ProtectedRegion getClaimRegion() {
        return claimRegion;
    }

    public void setClaimRegion(ProtectedRegion claimRegion) {
        this.claimRegion = claimRegion;
    }

    public ConfigurationSection saveFaction(ConfigurationSection section) {
        section.set("name", name);
        section.set("description", description);
        section.set("color", color);
        section.set("leader", leader);
        section.set("members", members);
        section.set("kills", killCount);
        section.set("dtr", dtr);
        return section;
    }

    public static Faction loadFaction(ConfigurationSection section) {
        try {
            String name = section.getString("name");
            String description = section.getString("description");
            String color = section.getString("color");
            UUID leader = UUID.fromString(section.getString("leader"));
            List<UUID> members = (List<UUID>) section.getList("members");
            int killCount = section.getInt("kills");
            int dtr = section.getInt("dtr");
            return new Faction(name, description, color, leader, members, killCount, dtr, null, null);
        } catch(Exception exception) {
            MineFactionsPlugin.getInstance().getLogger().log(Level.SEVERE, exception.getLocalizedMessage());
            return null;
        }
    }
}
