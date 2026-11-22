package hm2

import kotlin.math.PI
import kotlin.math.pow
import kotlin.random.Random

private fun runExercise1() {
    val emeraldList = listOf(1, 4, 23)
    val crimsonList = listOf(17, 2)
    val combinedSize = (emeraldList + crimsonList).count()
    println(combinedSize)
}

private fun runExercise2() {
    val allowedProtocols = setOf("HTTP", "HTTPS", "FTP")
    val targetProtocol = "smtp"
    val checkResult = targetProtocol.uppercase() in allowedProtocols
    println("Support for $targetProtocol: $checkResult")
}

private fun runExercise3() {
    val digitDictionary = mapOf(1 to "one", 2 to "two", 3 to "three")
    val targetDigit = 2
    val wordForm = digitDictionary[targetDigit]
    println("$targetDigit is spelled as '$wordForm'")
}

private fun runExercise4() {
    val dieOne = Random.nextInt(6)
    val dieTwo = Random.nextInt(6)
    when (dieOne == dieTwo) {
        true -> println("You win :)")
        false -> println("You lose :(")
    }
}

private fun runExercise5() {
    val pressedKey = "A"
    val responseText = when (pressedKey) {
        "A" -> "Yes"
        "B" -> "No"
        "X" -> "Menu"
        "Y" -> "Nothing"
        else -> "There is no such button"
    }
    println(responseText)
}

private fun runExercise6() {
    var sliceCounter = 1
    while (sliceCounter <= 7) {
        println("There's only $sliceCounter slice/s of pizza :(")
        sliceCounter++
    }
    println("There are $sliceCounter slices of pizza. Hooray! We have a whole pizza! :D")
}

private fun runExercise7() {
    (1..100).forEach { value ->
        val divBy3 = value % 3 == 0
        val divBy5 = value % 5 == 0
        println(
            when {
                divBy3 && divBy5 -> "fizzbuzz"
                divBy3 -> "fizz"
                divBy5 -> "buzz"
                else -> "$value"
            }
        )
    }
}

private fun runExercise8() {
    val vocabulary = listOf("dinosaur", "limousine", "magazine", "language")
    for (entry in vocabulary) {
        if (entry.firstOrNull() == 'l') {
            println(entry)
        }
    }
}

fun computeCircleAreaFull(r: Int): Double {
    val radiusAsDouble = r.toDouble()
    return PI * radiusAsDouble.pow(2.0)
}

private fun runExercise9() {
    println(computeCircleAreaFull(2))
}

fun computeCircleAreaCompact(r: Int): Double = PI * (r * r)

private fun runExercise10() {
    println(computeCircleAreaCompact(2))
}

fun toTotalSeconds(hours: Int = 0, minutes: Int = 0, seconds: Int = 0): Int {
    val minsTotal = hours * 60 + minutes
    return minsTotal * 60 + seconds
}

private fun runExercise11() {
    println(toTotalSeconds(1, 20, 15))
    println(toTotalSeconds(minutes = 1, seconds = 25))
    println(toTotalSeconds(hours = 2))
    println(toTotalSeconds(minutes = 10))
    println(toTotalSeconds(hours = 1, seconds = 1))
}

fun main() {
    runExercise1()
    runExercise2()
    runExercise3()
    runExercise4()
    runExercise5()
    runExercise6()
    runExercise7()
    runExercise8()
    runExercise9()
    runExercise10()
    runExercise11()
}
