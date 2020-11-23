package com.outlook.bselzer1.chess.sharedfunctions.implement

interface GetValue<T>
{
    /**
     * Before the value is retrieved.
     */
    fun before()

    /**
     * Get the value.
     */
    suspend fun getValue(): T

    /**
     * After the value is processed and resolved.
     */
    fun after(result: T)
}