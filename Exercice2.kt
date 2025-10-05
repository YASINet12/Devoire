package DEVOIR1

abstract class Vehicule(
    val immatriculation: String,
    val marque: String,
    val modele: String,
    var kilometrage: Int,
    var disponible: Boolean = true
) {
    open fun afficherDetails() {
        println("Vehicule -> Immat: $immatriculation, Marque: $marque, Modèle: $modele, Km: $kilometrage, Disponible: $disponible")
    }


    fun estDisponible(): Boolean = disponible
    fun marquerIndisponible() { disponible = false }
    fun marquerDisponible() { disponible = true }
    fun mettreAJourKilometrage(km: Int) { if (km >= kilometrage) kilometrage = km }
}


class Voiture(
    immatriculation: String,
    marque: String,
    modele: String,
    kilometrage: Int,
    disponible: Boolean = true,
    val nombrePortes: Int,
    val typeCarburant: String
) : Vehicule(immatriculation, marque, modele, kilometrage, disponible) {
    override fun afficherDetails() {
        println("Voiture -> Immat: $immatriculation, $marque $modele, Portes: $nombrePortes, Carburant: $typeCarburant, Km: $kilometrage, Disponible: $disponible")
    }
}


class Moto(
    immatriculation: String,
    marque: String,
    modele: String,
    kilometrage: Int,
    disponible: Boolean = true,
    val cylindree: Int
) : Vehicule(immatriculation, marque, modele, kilometrage, disponible) {
    override fun afficherDetails() {
        println("Moto -> Immat: $immatriculation, $marque $modele, Cylindrée: ${cylindree}cm³, Km: $kilometrage, Disponible: $disponible")
    } }