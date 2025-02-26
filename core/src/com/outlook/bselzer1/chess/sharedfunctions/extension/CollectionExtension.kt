package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.outlook.bselzer1.chess.sharedfunctions.implement.Copy

/**
 * Adds each object in [items] to the collection.
 */
fun <T> MutableCollection<T>.addVarargs(vararg items: T): MutableCollection<T>
{
    for (item in items)
    {
        this.add(item)
    }

    return this
}

/**
 * Adds an element to the collection if it is not null.
 *
 * @return whether or not an element was added
 */
fun <T> MutableCollection<T>.addNoNull(element: T?): Boolean
{
    element ?: return false
    return this.add(element)
}

/**
 * @return a copy of the collection
 */
fun <T : Copy<T>> Collection<T>.copy(): Collection<T>
{
    return mutableCopy().toList()
}

/**
 * @return a copy of the collection
 */
fun <T : Copy<T>> Collection<T>.mutableCopy(): MutableCollection<T>
{
    val collection = mutableListOf<T>()
    this.forEach { element -> collection.add(element.copy()) }
    return collection
}

fun <T> Iterable<T>.merge(it: Iterable<T>): Iterable<T>
{
    return this.zip(it) { item1, item2 -> listOf(item1, item2) }.flatten()
}