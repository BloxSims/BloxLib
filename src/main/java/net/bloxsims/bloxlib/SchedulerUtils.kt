package net.bloxsims.bloxlib

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.scheduler.BukkitTask

val scheduler = BloxLib.getPlugin().server.scheduler
val instance = BloxLib.getPlugin()

fun Block.set(material: Material) {
    if (BloxLib.getPlugin().isEnabled) scheduler.callSyncMethod(instance) { this.setType(material, false) }
}

fun Runnable.runTask() : BukkitTask {
    return scheduler.runTask(instance, this)
}

fun Runnable.runAsync() : BukkitTask {
    return scheduler.runTaskAsynchronously(instance, this)
}

fun Runnable.runLater(ticks: Long) : BukkitTask {
    return scheduler.runTaskLater(instance, this, ticks)
}

fun Runnable.runAsyncLater(delay: Long) : BukkitTask {
    return scheduler.runTaskLaterAsynchronously(instance, this, delay)
}

fun Runnable.runTimer(delay: Long, period: Long) : BukkitTask {
    return scheduler.runTaskTimer(instance, this, delay, period)
}

fun Runnable.runAsyncTimer(delay: Long, period: Long) : BukkitTask {
    return scheduler.runTaskTimerAsynchronously(instance, this, delay, period)
}