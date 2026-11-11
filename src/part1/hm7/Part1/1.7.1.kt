package hm7.Part1

abstract class SmartDevice(val name: String) {
    abstract fun turnOn()
    abstract fun turnOff()
}

class SmartLight(name: String) : SmartDevice(name) {
    override fun turnOn() {
        println("$name is now ON.")
    }

    override fun turnOff() {
        println("$name is now OFF.")
    }

    fun adjustBrightness(level: Int) {
        println("Adjusting $name brightness to $level%.")
    }
}

class SmartThermostat(name: String) : SmartDevice(name) {
    override fun turnOn() {
        println("$name thermostat is now heating.")
    }

    override fun turnOff() {
        println("$name thermostat is now off.")
    }

    fun adjustTemperature(temperature: Int) {
        println("$name thermostat set to $temperature°C.")
    }
}

fun main() {
    val lamp = SmartLight("Living Room Light")
    val heater = SmartThermostat("Bedroom Thermostat")

    lamp.turnOn()
    lamp.adjustBrightness(10)
    lamp.turnOff()

    heater.turnOn()
    heater.adjustTemperature(5)
    heater.turnOff()
}
