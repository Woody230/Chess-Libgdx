package com.outlook.bselzer1.chess.sharedfunctions.extension

import com.outlook.bselzer1.chess.sharedfunctions.Copy

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
    if (element == null)
    {
        return false
    }

    return this.add(element)
}

/**
 * @return whether or not this and [that] have the exact same elements
 */
fun <T> Collection<T>.contentEquals(that: Collection<T>?): Boolean
{
    return that != null && this.size == that.size && this.containsAll(that)
}

/**
 * @return a copy of the collection
 */
fun <T : Copy<T>> Collection<T>.copy(): Collection<T>
{
    val collection = mutableListOf<T>()
    this.forEach { collection.add(it.copy()) }
    return collection
}

/**
 * @return whether or not the collections contains all of the [items]
 */
fun <T> Collection<T>.containsAll(vararg items: T): Boolean
{
    return this.containsAll(items.toList())
}