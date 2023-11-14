package net.bloxsims.bloxlib

import net.bloxsims.bloxlib.displays.ChunkListener
import net.bloxsims.bloxlib.displays.Display
import org.apache.maven.artifact.versioning.ComparableVersion
import org.bukkit.Bukkit
import org.bukkit.Chunk
import org.bukkit.plugin.java.JavaPlugin

class BloxLib {
    companion object {
        val VERSION = ComparableVersion("1.0.0")


        private lateinit var plugin: JavaPlugin
        val TRUE_VALUES: List<String> = listOf("true", "yes", "on", "enable", "enabled")
        val FALSE_VALUES: List<String> = listOf("false", "no", "off", "disable", "disabled")
        val DISPLAY_ENTITIES: HashMap<Chunk, MutableList<Display>> = HashMap()

        fun setPlugin(plugin: JavaPlugin) {
            this.plugin = plugin

            Bukkit.getPluginManager().registerEvents(ChunkListener(), plugin)
        }

        fun getPlugin(): JavaPlugin {
            return plugin
        }
    }
}