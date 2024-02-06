package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.util.ChatUtil;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class HelpSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender player, String[] args) {
        player.sendMessage(ChatUtil.filter(ChatUtil.colorize("&cFUCK YOU you PENIS dick FUCKER")));
        player.sendMessage(ChatUtil.filter(ChatUtil.colorize("&c" + String.join(" ", args))));

        player.sendMessage("help cmd");
        return CommandResult.SUCCESS;
    }
}
