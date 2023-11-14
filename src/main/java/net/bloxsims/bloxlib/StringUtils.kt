package net.bloxsims.bloxlib

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import kotlin.math.pow

val mm = MiniMessage.miniMessage()
val smallFontMap = mapOf(
    'a' to 'ᴀ', 'b' to 'ʙ', 'c' to 'ᴄ', 'd' to 'ᴅ', 'e' to 'ᴇ', 'f' to 'ꜰ', 'g' to 'ɢ', 'h' to 'ʜ', 'i' to 'ɪ', 'j' to 'ᴊ', 'k' to 'ᴋ', 'l' to 'ʟ', 'm' to 'ᴍ', 'n' to 'ɴ', 'o' to 'ᴏ', 'p' to 'ᴘ', 'q' to 'ǫ', 'r' to 'ʀ', 's' to 's', 't' to 'ᴛ', 'u' to 'ᴜ', 'v' to 'ᴠ', 'w' to 'ᴡ', 'x' to 'x', 'y' to 'ʏ', 'z' to 'ᴢ'
)

fun String.color() : Component {
    return mm.deserialize(this).decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
}

fun String.oldColor() : Component {
    return LegacyComponentSerializer.legacyAmpersand().deserialize(this)
        .decorationIfAbsent(TextDecoration.ITALIC, TextDecoration.State.FALSE)
}

fun String.toSmall() : String {
    var string = ""
    for (char in this) {
        val lowercase = char.lowercaseChar()
        string += (smallFontMap[lowercase] ?: lowercase)
    }
    return string
}


/**
 * Returns the value of a string with a suffix as a Double
 * @return The value of the string as a Double, corresponding infinity if the input is "∞" or "-∞", or NaN if the input is NaN
 */
fun String.asDouble() : Double {

    if (this.contains("∞")) {
        return if (this.contains("-")) Double.NEGATIVE_INFINITY else Double.POSITIVE_INFINITY
    }

    // Change suffixes to numbers (ex: 1.5m = 1500000)
    val suffixLength =
        if (this.substring(length - 2, length - 1).toDoubleOrNull() == null
            && this.substring(length - 2, length - 1) != ".") // If the second to last character is NaN
        {
            2 // Double length suffix
        } else if (this.substring(length - 1).toDoubleOrNull() == null) { // If the last character is NaN
            1 // Single length suffix
        } else {
            0 // No suffix
        }

    val suffix = this.substring(length - suffixLength)

    var number = try {
        this.substring(0, (length - suffixLength).coerceAtLeast(0)).toDouble()
    } catch (e: NumberFormatException) {
        return Double.NaN
    }

    // If the suffix is a number (i.e. no suffix), set the number to the whole string.toDouble()
    if (numberSuffixes.contains(suffix)) {
        number *= 10.0.pow(((numberSuffixes.indexOf(suffix)) * 3))
    }

    return number
}