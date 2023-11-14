package net.bloxsims.bloxlib

import java.math.BigInteger
import java.text.DecimalFormat
import kotlin.math.absoluteValue
import kotlin.math.log10
import kotlin.math.pow
import kotlin.math.roundToInt

// suffixes for numbers up to 10^120
val numberSuffixes = arrayOf("", "k", "m", "b", "t", "q", "Q", "s", "S", "o", "n", "d", "ud", "dd", "td", "qd", "Qd", "sd", "Sd", "od", "nd", "v", "uv", "dv", "tv", "qv", "Qv", "sv", "Sv", "ov", "nv", "T", "uT", "dT", "tT", "qT", "QT", "sT", "ST", "oT", "nT")
val formatter = DecimalFormat("#,##0.00").apply {
    minimumFractionDigits = 0
    maximumFractionDigits = 2
}

fun Double.formatWithSuffix(): String {
    if (this.isInfinite() || this.isNaN()) {
        return this.toString()
    }

    val negative: String = if (this < 0) "-" else ""
    val index = (log10(absoluteValue) / 3).toInt().coerceIn(0, numberSuffixes.size - 1)
    val formattedValue = formatter.format(absoluteValue / 10.0.pow(index * 3))

    return "${negative}${formattedValue}${numberSuffixes[index]}"
}

fun Double.formatWithCommas(): String {
    return this.toString().split(Regex("(?<=\\d(?=(\\d{3}) (?!\\d)))")).joinToString(",")
}
fun Int.formatWithSuffix() : String {
    return this.toDouble().formatWithSuffix()
}

fun Int.formatWithCommas() : String {
    return this.toDouble().formatWithCommas()
}

fun BigInteger.formatWithSuffix() : String {
    return this.toDouble().formatWithSuffix()
}

fun BigInteger.formatWithCommas() : String {
    return this.toDouble().formatWithCommas()
}

fun Long.formatWithSuffix() : String {
    return this.toDouble().formatWithSuffix()
}

fun Long.formatWithCommas() : String {
    return this.toDouble().formatWithCommas()
}

fun Long.pow(exp: Int) : Long {
    return toBigInteger().pow(exp).toLong()
}
