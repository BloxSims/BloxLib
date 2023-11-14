package net.bloxsims.bloxlib

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import kotlin.math.roundToInt

abstract class Command (
    private val commands: List<String>,
    private val executableBy: Executor = Executor.BOTH,
) : CommandExecutor {

    fun register() {
        commands.forEach { command ->
            BloxLib.getPlugin().getCommand(command)?.setExecutor(this)
        }
    }

    override fun onCommand(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: Array<String>): Boolean {
        if (executableBy.executable.none { it.isInstance(sender) }) {
            sender.sendMessage(executableBy.notExecutableString)
            return true
        }
        return commandHandler(sender, command, label, args.clone().toMutableList())
    }

    abstract fun commandHandler(sender: CommandSender, command: org.bukkit.command.Command, label: String, args: MutableList<String>): Boolean

    /**
     * Parses the arguments as the specified types.
     *
     * If the next argument is not the specified type, it will be null.
     * Strings will be parsed until the next argument is the specified type.
     *
     * @param types The types to parse the arguments as
     * @param defaults The default values if the next argument is not the specified type
     * @return A list of the parsed arguments
     */
    fun MutableList<String>.parseAs(vararg types: ArgumentType, defaults: List<*>? = null): List<Any?> {
        val args: MutableList<Any?> = mutableListOf()
        for ((index, type) in types.withIndex()) {
            args.add(this.parseNextAs(type, defaults?.getOrNull(index), types.getOrNull(index + 1)))
        }
        return args
    }

    /**
     * Parses the next argument as the specified type.
     *
     * If the next argument is not the specified type, it will be null (or inputted default value).
     * Strings will be parsed until the end of the list (or specified type).
     *
     * @param type The type to parse the next argument as
     * @param default The default value if the next argument is not the specified type
     * @return The parsed argument
     */
    fun MutableList<String>.parseNextAs(type: ArgumentType, default: Any? = null, endStringAt: ArgumentType? = null): Any? {
        return when (type) {
            ArgumentType.PLAYER -> this.getPlayerOrNull()
            ArgumentType.OFFLINE_PLAYER -> this.getOfflinePlayerOrNull()
            ArgumentType.BOOLEAN -> this.getBooleanOrNull()
            ArgumentType.DOUBLE -> this.getDoubleOrNull()
            ArgumentType.STRING -> this.getStringOrNull(endStringAt ?: ArgumentType.NULL)
            ArgumentType.NULL -> null
        } ?: default
    }

    /**
     * Checks if the next arguments are the specified types.
     *
     * @param types The types to check for
     * @return Whether the next arguments are the specified type
     */
    fun MutableList<String>.nextAre(vararg types: ArgumentType): List<Boolean> {
        val args: MutableList<String> = this.toMutableList() // Clone the list
        val booleans: MutableList<Boolean> = mutableListOf()
        for ((index, type) in types.withIndex()) {
            booleans.add(args.parseNextAs(type, null, types.getOrNull(index + 1)) != null)
        }
        return booleans
    }

    /**
     * Checks if the next argument is the specified type.
     *
     * @param type The type to check for
     * @return Whether the next argument is the specified type
     */
    fun MutableList<String>.nextIs(type: ArgumentType): Boolean {
        return when (type) {
            ArgumentType.PLAYER -> this.nextIsPlayer()
            ArgumentType.OFFLINE_PLAYER -> this.nextIsOfflinePlayer()
            ArgumentType.BOOLEAN -> this.nextIsBoolean()
            ArgumentType.DOUBLE -> this.nextIsDouble()
            ArgumentType.STRING -> this.nextIsString()
            ArgumentType.NULL -> this.isEmpty()
        }
    }

    private fun MutableList<String>.nextIsString(): Boolean {
        return (!this.nextIs(ArgumentType.NULL))
    }

    private fun MutableList<String>.getStringOrNull(until: ArgumentType): String? {
        // Go until the next argument is the specified type
        var string = ""
        while (!this.nextIs(ArgumentType.NULL) && !this.nextIs(until)) {
            string += this.removeAt(0)
        }
        return if (string == "") null else string
    }

    private fun MutableList<String>.nextIsPlayer(): Boolean {
        return try {
            Bukkit.getPlayer(this[0])
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun MutableList<String>.getPlayerOrNull(): Player? {
        return try {
            Bukkit.getPlayer(this.removeAt(0))
        } catch (e: Exception) {
            null
        }
    }

    private fun MutableList<String>.nextIsOfflinePlayer(): Boolean {
        return try {
            Bukkit.getOfflinePlayer(this[0])
            true
        } catch (e: Exception) {
            false
        }
    }

    private fun MutableList<String>.getOfflinePlayerOrNull(): OfflinePlayer? {
        return try {
            Bukkit.getOfflinePlayer(this.removeAt(0))
        } catch (e: Exception) {
            null
        }
    }

    private fun MutableList<String>.nextIsBoolean(): Boolean {
        return try {
            val arg = this[0].lowercase()
            BloxLib.TRUE_VALUES.plus(BloxLib.FALSE_VALUES).contains(arg)
        } catch (e: Exception) {
            false
        }
    }

    private fun MutableList<String>.getBooleanOrNull(): Boolean? {
        try {
            if (nextIsBoolean()) {
                return BloxLib.TRUE_VALUES.contains(this.removeAt(0).lowercase())
            }
            return null
        } catch (e: Exception) {
            return null
        }
    }

    private fun MutableList<String>.nextIsDouble(): Boolean {
        return !this[0].asDouble().isNaN()
    }

    private fun MutableList<String>.getDoubleOrNull(): Double? {
        return try {
            this.removeAt(0).asDouble()
        } catch (e: Exception) {
            null
        }
    }

    enum class Executor(val notExecutableString: String, val executable: List<Class<out CommandSender>>) {
        PLAYER("This command can only be used by players!", listOf(Player::class.java)),
        CONSOLE("This command can only be used by the console!", listOf(ConsoleCommandSender::class.java)),
        BOTH("This command can only be used by players and the console!", listOf(Player::class.java, ConsoleCommandSender::class.java))
    }

    enum class ArgumentType() {
        PLAYER,
        OFFLINE_PLAYER,
        BOOLEAN,
        /**
         * @see asDouble
         */
        DOUBLE,
        STRING,
        NULL
    }
}