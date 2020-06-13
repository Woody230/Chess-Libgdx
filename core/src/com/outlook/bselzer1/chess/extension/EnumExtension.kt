package com.outlook.bselzer1.chess.extension

import java.util.*

/**
 * @return a user friendly string of the enum name
 */
fun <E : Enum<E>> Enum<E>.toDisplayableString(): String
{
    val name: String = this.name.toLowerCase(Locale.getDefault()).replace("_", " ")
    val builder = StringBuilder(name.length)

    val split = name.split(" ".toRegex()).toTypedArray()
    for (string: String in split)
    {
        val firstChar: Char = Character.toUpperCase(string[0])
        val segment = firstChar.toString() + string.substring(1)
        builder.append(segment).append(" ")
    }

    return builder.toString()
}