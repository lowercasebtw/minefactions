package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.manager.Faction;
import io.github.enqorman.minefactions.manager.FactionManager;
import io.github.enqorman.minefactions.util.ChatUtil;

import java.util.List;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoSubCommand implements SubCommand {
    private void showInfo(MineFactionsPlugin plugin, CommandSender sender, Faction faction) {
        sender.sendMessage(ChatUtil.colorize("&e----==== Faction info: &r" + faction.getName() + " &e====----"));
        sender.sendMessage(ChatUtil.colorize("&eKills: &r" + faction.getKillCount()));
        sender.sendMessage(ChatUtil.colorize("&eDescription: &r" + faction.getDescription()));
        sender.sendMessage(ChatUtil.colorize("&eDTR: &r" + faction.getDTR() + "/5"));
        sender.sendMessage(ChatUtil
                .colorize("&eLeader: &r" + plugin.getServer().getOfflinePlayer(faction.getLeaderUUID()).getName()));
        List<UUID> memberUuids = faction.getMemberUUIDs();
        sender.sendMessage(ChatUtil.colorize("&eMembers &7(" + memberUuids.size() + ")&e: &r"));
        if (memberUuids.isEmpty())
            sender.sendMessage(ChatUtil.colorize("  &cNone"));
        else {
            for (UUID memberUuid : faction.getMemberUUIDs()) {
                OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(memberUuid);
                sender.sendMessage(
                        ChatUtil.colorize(" &r- &" + (offlinePlayer.isOnline() ? "a" : "e") + offlinePlayer.getName()));
            }
        }

        Location homeLocation = faction.getHomeLocation();
        sender.sendMessage(ChatUtil.colorize("&eHome:"));
        if (homeLocation != null) {
            sender.sendMessage(ChatUtil.colorize("  &bx: &r" + homeLocation.getX()));
            sender.sendMessage(ChatUtil.colorize("  &by: &r" + homeLocation.getY()));
            sender.sendMessage(ChatUtil.colorize("  &bz: &r" + homeLocation.getZ()));
        } else {
            sender.sendMessage(ChatUtil.colorize("  &cNot set"));
        }
    }

    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        if (args.length < 1 && !(sender instanceof Player)) {
            sender.sendMessage(ChatUtil.colorize("&cOnly players can use this command like this!"));
            sender.sendMessage(ChatUtil.colorize("&aTo use this in console, do \"/f info <faction>\""));
            return CommandResult.SUCCESS;
        }

        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();
        if (args.length < 1) {
            // if they in faction yada yada
            Faction faction = factionManager.getFactionByPlayer((Player) sender);
            if (faction == null) {
                sender.sendMessage(ChatUtil.colorize("&cYou are not in a faction!"));
                sender.sendMessage(
                        ChatUtil.colorize("&aDo \"/f info <faction>\" to check the info of other factions!"));
                return CommandResult.SUCCESS;
            }

            showInfo(plugin, sender, faction);
            return CommandResult.SUCCESS;
        }

        String factionName = args[0];
        Faction faction = factionManager.findFactionByName(factionName, true);
        if (faction == null) {
            // TODO: look if they used ign or uuid
            sender.sendMessage(ChatUtil.colorize("&cCould not find a faction by that name!"));
            return CommandResult.SUCCESS;
        }

        showInfo(plugin, sender, faction);
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "About a faction";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.info";
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}
