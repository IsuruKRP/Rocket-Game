package com.example.rocket

interface GameTask {
    abstract var myrocktPosition: Int

    fun closeGame(mScore:Int)
}