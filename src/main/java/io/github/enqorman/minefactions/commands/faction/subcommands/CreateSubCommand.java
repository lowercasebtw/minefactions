package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.MineFactionsPlugin;
import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.manager.Faction;
import io.github.enqorman.minefactions.manager.FactionManager;
import io.github.enqorman.minefactions.util.ChatUtil;

import java.util.LinkedList;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();

        Faction faction = factionManager.getFactionByPlayer((Player) sender);
        if (faction != null) {
            sender.sendMessage(
                    ChatUtil.colorize("&cYou are already in a faction and cannot create another whilst in it!"));
            return CommandResult.SUCCESS;
        }

        if (args.length < 1) {
            sender.sendMessage(ChatUtil.colorize("&cYou must provide a faction name!"));
            return CommandResult.SUCCESS;
        }

        String factionName = String.join(" ", args);
        Faction otherExists = factionManager.findFactionByName(factionName, true);
        if (otherExists != null) {
            sender.sendMessage(ChatUtil.colorize("&cSorry but that name is already taken!"));
            sender.sendMessage(ChatUtil.colorize("&aUse \"/f list\" to list all existing factions!"));
            return CommandResult.SUCCESS;
        }

        faction = new Faction(factionName, "(empty)", ((Player) sender).getUniqueId(), new LinkedList<>(), 0, 0, null,
                null);
        factionManager.addFaction(faction);

        if (!factionManager.saveFactions()) {
            sender.sendMessage(ChatUtil.colorize("&cFailed to create faction! Reason: Internal Error"));
            return CommandResult.FAIL;
        }

        sender.sendMessage(ChatUtil.colorize("&aSuccessfully created a faction named '" + factionName + "&a'!"));
        plugin.getServer().broadcastMessage(ChatUtil.colorize("&eFaction &r" + factionName + " &ehas been created!"));
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Create a faction";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.create";
    }

    @Override
    public boolean isConsoleAllowed() {
        return false;
    }
}
