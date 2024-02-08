package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.util.ChatFilter;
import io.github.enqorman.minefactions.util.ChatUtil;
import org.bukkit.command.CommandSender;

public class HelpSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender player, String[] args) {
        player.sendMessage(ChatUtil.colorize(ChatFilter.filter("&cFUCK YOU you PENIS dick FUCKER")));
        player.sendMessage(ChatUtil.colorize(ChatFilter.filter("&c" + String.join(" ", args))));

        player.sendMessage("help cmd");
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "Info about a subcommand";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.help";
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}
