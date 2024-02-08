package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.manager.Faction;
import io.github.enqorman.minefactions.manager.FactionManager;
import io.github.enqorman.minefactions.util.ChatUtil;

import org.bukkit.command.CommandSender;

import java.util.List;

public class ListSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();
        List<Faction> factions = factionManager.getFactions();
        if (factions.isEmpty()) {
            sender.sendMessage(ChatUtil.colorize("&7&lNo factions exist currently."));
            return CommandResult.SUCCESS;
        }

        sender.sendMessage(ChatUtil.colorize("&e----==== Factions List ====----"));
        for (Faction faction : factions) {
            int count = 1 + faction.getMemberUUIDs().size();
            sender.sendMessage(
                    ChatUtil.colorize(
                            faction.getName() + " &e| &b" + count + " " + (count > 1 ? "members" : "member") + " &e| &r"
                                    + faction.getDTR() + "/5 DTR"));
        }

        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "List all existing factions";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.list";
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}
