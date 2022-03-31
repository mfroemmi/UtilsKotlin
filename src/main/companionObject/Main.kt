package main.companionObject

/* In diesem Beispiel werden Autos erzeugt, die eine statische Variable "version" besitzen.
** Wird ein Update der Versionsnummer an einem Auto durchgeführt, ändert der Wert sich in jedem Objekt.
 */

fun main() {

    val carList: MutableList<Car> = mutableListOf()

    val car01 = Car("Opel", "gruen", 1)
    carList.add(car01)
    val car02 = Car("Audi", "blau", 1)
    carList.add(car02)
    val car03 = Car("Porsche", "rot", 1)
    carList.add(car03)

    for (car in carList) {
        print("Typ:${car.type}, Farbe:${car.color}, Version:${car.getNaviVersion()}")
        println()
    }
    car01.updateNaviVersion(2)

    println()
    for (car in carList) {
        print("Typ:${car.type}, Farbe:${car.color}, Version:${car.getNaviVersion()}")
        println()
    }

}

class Car(var type: String, var color: String, naviVersion: Int) {
    companion object {
        var version: Int = 0
    }

    init {
        version = naviVersion
    }

    fun getNaviVersion() : Int {
        return version
    }

    fun updateNaviVersion(newVersion: Int) {
        version = newVersion
    }
}