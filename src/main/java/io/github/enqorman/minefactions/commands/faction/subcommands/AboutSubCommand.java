package io.github.enqorman.minefactions.commands.faction.subcommands;

import io.github.enqorman.minefactions.commands.CommandResult;
import io.github.enqorman.minefactions.commands.SubCommand;
import io.github.enqorman.minefactions.util.ChatUtil;
import org.bukkit.command.CommandSender;

public class AboutSubCommand implements SubCommand {
    @Override
    public CommandResult execute(CommandSender sender, String[] args) {
        sender.sendMessage(ChatUtil.colorize("&6&l------- &6MineFactions &6-------"));
        sender.sendMessage(ChatUtil.colorize("&b&lCreator:&r &aenqorman"));
        sender.sendMessage(ChatUtil.colorize("&b&lVersion:&r &a1.0"));
        sender.sendMessage(ChatUtil.colorize("&b&lGitHub: &7https://github.com/enqorman/minefactions"));
        sender.sendMessage(ChatUtil.colorize("&6&l----------------------------"));
        return CommandResult.SUCCESS;
    }

    @Override
    public String getDescription() {
        return "About the plugin";
    }

    @Override
    public String getPermission() {
        return "minefactions.command.faction.about";
    }

    @Override
    public boolean isConsoleAllowed() {
        return true;
    }
}
