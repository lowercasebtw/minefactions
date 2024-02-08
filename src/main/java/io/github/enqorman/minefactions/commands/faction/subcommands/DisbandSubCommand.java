package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.manager.Faction;
import io.github.enqorman.minefactions.manager.FactionManager;
import io.github.enqorman.minefactions.util.ChatUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DisbandSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();

        Faction faction = factionManager.getFactionByPlayer(player);
        if (faction == null) {
            player.sendMessage(ChatUtil.colorize("&cYou are not in a faction!"));
            return CommandResult.SUCCESS;
        }

        if (!faction.getLeaderUUID().equals(player.getUniqueId())) {
            player.sendMessage(ChatUtil.colorize("&cOnly the faction leader can do that!"));
            return CommandResult.SUCCESS;
        }

        if (!factionManager.remove(faction)) {
            player.sendMessage(ChatUtil.colorize("&cFailed to disband faction!"));
            return CommandResult.SUCCESS;
        }

        sender.sendMessage(ChatUtil.colorize("&aSuccessfully disbanded the faction!"));
        plugin.getServer()
                .broadcastMessage(ChatUtil.colorize("&eFaction &r" + faction.getName() + " &ehas been disbanded!"));
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Delete the faction";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.disband";
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
