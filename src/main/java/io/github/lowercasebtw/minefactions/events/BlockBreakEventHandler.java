package io.github.lowercasebtw.minefactions.events;

import io.github.lowercasebtw.minefactions.manager.Faction;
import io.github.lowercasebtw.minefactions.manager.FactionManager;
import io.github.lowercasebtw.minefactions.util.Util;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.lowercasebtw.minefactions.MineFactionsPlugin;
import org.bukkit.util.BoundingBox;

public class BlockBreakEventHandler implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        FactionManager factionManager = plugin.getFactionManager();
        Block block = event.getBlock();
        Player player = event.getPlayer();
        for (Faction faction : factionManager.getFactions()) {
            if (faction.getBoundingBox() == null)
                continue;
            BoundingBox boundingBox = faction.getBoundingBox();
            if (boundingBox.contains(block.getBoundingBox()) && !faction.isMember(player)) {
                event.setCancelled(true);
                Util.sendMessage(player, "&cYou cannot break blocks in someone else's claim!");
            }
        }
    }
}
