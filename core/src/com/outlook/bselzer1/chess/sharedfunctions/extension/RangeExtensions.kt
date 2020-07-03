package com.outlook.bselzer1.chess.sharedfunctions.extension

/**
 * @return a progression from this value to the specified [to] value, exclusive
 */
infix fun Int.toExclusive(to: Int): IntProgression
{
    val step = if (this > to) -1 else 1
    val start = if (this > to) this - 1 else this + 1
    val end = if (this > to) to + 1 else to - 1
    return IntProgression.fromClosedRange(start, end, step)
}