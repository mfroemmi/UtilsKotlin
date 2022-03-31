package main.messageAlert

class MessageAlertImpl(private var time: Int) : MessageAlert {

    override fun updateTimer(newTime: Int) {
        time = newTime
    }

    override fun getTimer(): Int {
        return time * 1000
    }

    override fun getMessage(): String {
        return "Alarm! Alarm!"
    }

}