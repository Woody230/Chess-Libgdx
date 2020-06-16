package com.outlook.bselzer1.chess.extension

import java.util.concurrent.atomic.AtomicInteger

/**
 * The next integer identifier.
 */
private val intId = AtomicInteger(Int.MIN_VALUE)

/**
 * @return the next integer identifier
 */
fun nextIntId(): Int
{
    return intId.getAndIncrement()
}

/**
 * @return whether or not the object is one of [values]
 */
fun <T> T.isOneOf(vararg values: T): Boolean
{
    return values.contains(this)
}