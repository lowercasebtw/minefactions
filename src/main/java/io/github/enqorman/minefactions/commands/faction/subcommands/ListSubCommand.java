package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.manager.Faction;
import io.github.enqorman.minefactions.manager.FactionManager;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ListSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        FactionManager factionManager = MineFactionsPlugin.getFactionManager();
        List<Faction> factions = factionManager.getFactions();
        if (factions.isEmpty()) {
            sender.sendMessage("No factions exist currently.");
            return CommandResult.SUCCESS;
        }

        sender.sendMessage("Factions (" + factions.size() + "):");
        for (Faction faction : factions) {
            sender.sendMessage(" - " + faction.getName() + " - " + faction.getDescription() + " (dtr=" + faction.getDTR() + ", kills=" + faction.getKillCount() + ")");
        }

        return CommandResult.SUCCESS;
    }
}
