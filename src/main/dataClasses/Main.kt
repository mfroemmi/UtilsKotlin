package main.dataClasses

fun main() {
    val myGames: MutableList<Games> = mutableListOf()

    val game1 = Games("Diablo", Genres.RPG, 7)
    val game2 = Games("Siedler", Genres.STRATEGY, 4)
    val game3 = Games("Battlefield", Genres.SHOOTER, 6)
    val game4 = Games("Guild Wars", Genres.RPG, 10)

    myGames.add(game1)
    myGames.add(game2)
    myGames.add(game3)
    myGames.add(game4)

    println(myGames)
}

data class Games(val title: String, val genre: Genres, val rate: Int)

enum class Genres() {
    RPG,
    STRATEGY,
    SHOOTER
}