package com.outlook.bselzer1.chess.sharedfunctions.implement

interface Copy<out T>
{
    /**
     * @return a copy of the object
     */
    fun copy(): T
}