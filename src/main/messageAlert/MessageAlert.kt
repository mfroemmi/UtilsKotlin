package main.messageAlert

interface MessageAlert {
    fun updateTimer(newTime: Int)
    fun getTimer(): Int
    fun getMessage(): String
}