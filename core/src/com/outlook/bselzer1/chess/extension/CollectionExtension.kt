package com.outlook.bselzer1.chess.extension

/**
 * Adds each object in [items] to the collection.
 */
fun <E> MutableCollection<E>.addVarargs(vararg items: E)
{
    for (item in items)
    {
        this.add(item)
    }
}