package hm7.Part1

interface Media {
    val title: String
    fun play()
}

class Audio(
    override val title: String,
    val composer: String
) : Media {
    override fun play() {
        println("Playing audio: $title, composed by $composer")
    }
}

fun main() {
    val composition = Audio("Symphony No. 5", "Beethoven")
    composition.play()
}
