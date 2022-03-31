package main.messageAlert

import java.util.*
import kotlin.concurrent.schedule

fun main() {

    val handler = Timer()
    val newAlert = MessageAlertImpl(5)

    println("messageAlert")
    println("oldTime: ${newAlert.getTimer()}")
    newAlert.updateTimer(3)
    println("newTime: ${newAlert.getTimer()}")

    handler.schedule(newAlert.getTimer().toLong()){
        println(newAlert.getMessage())
    }

}