package com.outlook.bselzer1.chess.sharedfunctions.extension

import kotlin.math.max
import kotlin.math.min

/**
 * A number is between two other numbers.
 */
fun Number.isBetweenInclusive(num1: Number, num2: Number): Boolean
{
    val double1 = num1.toDouble()
    val double2 = num2.toDouble()
    val lesser = min(double1, double2)
    val greater = max(double1, double2)
    return this.toDouble() in lesser..greater
}

/**
 * A number is between two other numbers, with the greater number excluded.
 */
fun Number.isBetweenUntil(num1: Number, num2: Number): Boolean
{
    val double1 = num1.toDouble()
    val double2 = num2.toDouble()
    val lesser = min(double1, double2)
    val greater = max(double1, double2)
    return this.toDouble() in lesser..(greater - 1)
}