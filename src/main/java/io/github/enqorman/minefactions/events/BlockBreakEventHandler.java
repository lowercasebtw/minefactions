package io.github.enqorman.minefactions.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.enqorman.minefactions.MineFactionsPlugin;

public class BlockBreakEventHandler implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        MineFactionsPlugin plugin = MineFactionsPlugin.getInstance();
        boolean silkSpawners = plugin.getConfig().getBoolean("silk-spawners");
        if (!silkSpawners)
            return;

        // Player player = event.getPlayer();
        // if (player == null)
        // return;

        // PlayerInventory inventory = player.getInventory();
        // ItemStack mainHandStack = inventory.getItemInMainHand();

        // Block block = event.getBlock();
        // World world = block.getWorld();

        // TODO
        // if (mainHandStack.getEnchantments().containsKey(Enchantment.SILK_TOUCH)
        // && block.getType() == Material.SPAWNER) {
        // CreatureSpawner cs = (CreatureSpawner) block.getState();
        // ItemStack spawnerStack = new ItemStack(block.getType(), 1, block.getData());
        // BlockStateMeta blockMeta = (BlockStateMeta) spawnerStack.getItemMeta();
        // blockMeta.setBlockState(cs);
        // spawnerStack.setItemMeta(blockMeta);
        // world.dropItemNaturally(block.getLocation(), spawnerStack);
        // }
    }
}
