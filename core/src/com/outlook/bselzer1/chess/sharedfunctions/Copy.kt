package com.outlook.bselzer1.chess.sharedfunctions

interface Copy<out T>
{
    /**
     * @return a copy of the object
     */
    fun copy(): T
}