package net.bloxsims.bloxlib

import org.apache.maven.artifact.versioning.ComparableVersion
import org.bukkit.plugin.java.JavaPlugin

class BloxLib {
    companion object {
        val VERSION = ComparableVersion("1.0.0")


        private lateinit var plugin: JavaPlugin
        val TRUE_VALUES: List<String> = listOf("true", "yes", "on", "enable", "enabled")
        val FALSE_VALUES: List<String> = listOf("false", "no", "off", "disable", "disabled")

        fun setPlugin(plugin: JavaPlugin) {
            this.plugin = plugin
        }

        fun getPlugin(): JavaPlugin {
            return plugin
        }
    }
}