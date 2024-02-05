package io.github.enqorman.minefactions.timers;

import org.bukkit.scheduler.BukkitRunnable;

public class DTRTimer extends BukkitRunnable {
    private int currentTick = 0;

    @Override
    public void run() {
        // code

        // 72000 = 1 irl hour
        if (currentTick == 72000) {
            currentTick = 0;
            return;
        }

        currentTick++;
    }
}
