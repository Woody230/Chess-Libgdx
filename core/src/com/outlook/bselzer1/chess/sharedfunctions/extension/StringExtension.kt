package com.outlook.bselzer1.chess.sharedfunctions.extension

/**
 * String representation of the boolean value true.
 */
private const val UI_TRUE = "On"

/**
 * String representation of the boolean value false.
 */
private const val UI_FALSE = "Off"

/**
 * @return the converted ui string representation
 */
fun Boolean.toUiString(): String
{
    return if (this) UI_TRUE else UI_FALSE
}

/**
 * The ui string representation of a boolean.
 * @return the boolean
 */
fun String.uiToBoolean(): Boolean
{
    return this == UI_TRUE
}

/**
 * @return true and false as ui string representations
 */
fun booleanAsUIString(): Array<String>
{
    return arrayOf(UI_TRUE, UI_FALSE)
}