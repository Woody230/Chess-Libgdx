package com.outlook.bselzer1.chess.ui.actor.dialog

import kotlinx.coroutines.delay

/**
 * Dialog for waiting for the result of a user's input.
 */
open class AwaitResultDialog<Result>(title: String) : GdxGameDialog(title)
{
    /**
     * The result to wait for.
     */
    private var result: Result? = null

    override fun result(`object`: Any?)
    {
        @Suppress("UNCHECKED_CAST")
        result = `object` as Result
    }

    /**
     * Await the user's response to the dialog.
     */
    suspend fun awaitResult(): Result
    {
        while (result == null)
        {
            delay(100)
        }

        return result!!
    }
}