package DEVOIR1

// ==========================
// Partie 1 : Gestion Bibliothèque
// ==========================

open class Personne(val nom: String, val prenom: String, val email: String) {
    open fun afficherInfos() {
        println("Personne -> Nom: $nom, Prénom: $prenom, Email: $email")
    }
}

class Utilisateur(
    nom: String,
    prenom: String,
    email: String,
    val idUtilisateur: Int
) : Personne(nom, prenom, email) {
    val emprunts: MutableList<Emprunt> = mutableListOf()

    fun emprunterLivre(livre: Livre, dateEmprunt: String) {
        if (livre.disponiblePourEmprunt()) {
            val emprunt = Emprunt(this, livre, dateEmprunt, null)
            emprunts.add(emprunt)
            livre.mettreAJourStock(livre.nombreExemplaires - 1)
            println("$prenom $nom a emprunté '${livre.titre}' le $dateEmprunt")
        } else {
            println("Le livre '${livre.titre}' n'est pas disponible pour emprunt.")
        }
    }

    fun afficherEmprunts() {
        println("Emprunts de $prenom $nom (ID: $idUtilisateur) :")
        if (emprunts.isEmpty()) {
            println("  Aucun emprunt.")
        } else {
            emprunts.forEach { it.afficherDetails() }
        }
    }

    override fun afficherInfos() {
        println("Utilisateur -> ID: $idUtilisateur, Nom: $nom, Prénom: $prenom, Email: $email")
    }
}

class Livre(
    val titre: String,
    val auteur: String,
    val isbn: String,
    var nombreExemplaires: Int
) {
    fun afficherDetails() {
        println("Livre -> Titre: $titre, Auteur: $auteur, ISBN: $isbn, Exemplaires disponibles: $nombreExemplaires")
    }

    fun disponiblePourEmprunt(): Boolean = nombreExemplaires > 0

    fun mettreAJourStock(nouveauStock: Int) {
        nombreExemplaires = if (nouveauStock < 0) 0 else nouveauStock
    }
}

class Emprunt(
    val utilisateur: Utilisateur,
    val livre: Livre,
    val dateEmprunt: String,
    var dateRetour: String?
) {
    fun afficherDetails() {
        println("  Emprunt -> Livre: '${livre.titre}', Utilisateur: ${utilisateur.prenom} ${utilisateur.nom}, Date emprunt: $dateEmprunt, Date retour: ${dateRetour ?: "non retourné"}")
    }

    fun retournerLivre(dateRetourStr: String) {
        if (dateRetour != null) {
            println("Ce livre a déjà été retourné le $dateRetour")
            return
        }
        dateRetour = dateRetourStr
        livre.mettreAJourStock(livre.nombreExemplaires + 1)
        println("Le livre '${livre.titre}' a été retourné par ${utilisateur.prenom} ${utilisateur.nom} le $dateRetourStr")
    }
}

abstract class GestionBibliotheque {
    val utilisateurs: MutableList<Utilisateur> = mutableListOf()
    val livres: MutableList<Livre> = mutableListOf()

    abstract fun ajouterUtilisateur(utilisateur: Utilisateur)
    abstract fun ajouterLivre(livre: Livre)
    abstract fun afficherTousLesLivres()
}

class Bibliotheque : GestionBibliotheque() {
    override fun ajouterUtilisateur(utilisateur: Utilisateur) {
        utilisateurs.add(utilisateur)
        println("Utilisateur ajouté: ${utilisateur.prenom} ${utilisateur.nom} (ID: ${utilisateur.idUtilisateur})")
    }

    override fun ajouterLivre(livre: Livre) {
        livres.add(livre)
        println("Livre ajouté: '${livre.titre}' de ${livre.auteur}")
    }

    override fun afficherTousLesLivres() {
        println("--- Tous les livres de la bibliothèque ---")
        if (livres.isEmpty()) println("Aucun livre.") else livres.forEach { it.afficherDetails() }
    }

    fun rechercherLivreParTitre(titre: String): Livre? {
        return livres.find { it.titre.equals(titre, ignoreCase = true) }
    }
}

// ==========================
// Partie 2 : Gestion Parc Automobile
// ==========================

abstract class Vehicule1(
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

class Voiture3(
    immatriculation: String,
    marque: String,
    modele: String,
    kilometrage: Int,
    disponible: Boolean = true,
    val nombrePortes: Int,
    val typeCarburant: String
) : Vehicule1(immatriculation, marque, modele, kilometrage, disponible) {
    override fun afficherDetails() {
        println("Voiture3 -> Immat: $immatriculation, $marque $modele, Portes: $nombrePortes, Carburant: $typeCarburant, Km: $kilometrage, Disponible: $disponible")
    }
}

class Moto3(
    immatriculation: String,
    marque: String,
    modele: String,
    kilometrage: Int,
    disponible: Boolean = true,
    val cylindree: Int
) : Vehicule1(immatriculation, marque, modele, kilometrage, disponible) {
    override fun afficherDetails() {
        println("Moto -> Immat: $immatriculation, $marque $modele, Cylindrée: ${cylindree}cm³, Km: $kilometrage, Disponible: $disponible")
    }
}

class Conducteur(val nom: String, val prenom: String, val numeroPermis: String) {
    fun afficherDetails() {
        println("Conducteur -> Nom: $nom, Prénom: $prenom, N° Permis: $numeroPermis")
    }
}

class Reservation(
    val vehicule: Vehicule1,
    val conducteur: Conducteur,
    val dateDebut: String,
    val dateFin: String,
    val kilometrageDebut: Int,
    var kilometrageFin: Int?
) {
    fun cloturerReservation(kilometrageRetour: Int) {
        if (kilometrageFin != null) {
            println("Réservation déjà clôturée (kilométrage retour: $kilometrageFin)")
            return
        }
        kilometrageFin = kilometrageRetour
        vehicule.mettreAJourKilometrage(kilometrageRetour)
        vehicule.marquerDisponible()
        println("Réservation de ${vehicule.immatriculation} clôturée, km retour: $kilometrageRetour")
    }

    fun afficherDetails() {
        println("Reservation -> Vehicule: ${vehicule.immatriculation}, Conducteur: ${conducteur.prenom} ${conducteur.nom}, Début: $dateDebut, Fin: $dateFin, Km début: $kilometrageDebut, Km fin: ${kilometrageFin ?: "non connu"}")
    }
}

class VehiculeIndisponibleException(message: String) : Exception(message)
class VehiculeNonTrouveException(message: String) : Exception(message)

class ParcAutomobile {
    val vehicules: MutableList<Vehicule1> = mutableListOf()
    val reservations: MutableList<Reservation> = mutableListOf()

    fun ajouterVehicule(vehicule: Vehicule1) {
        vehicules.add(vehicule)
        println("Véhicule ajouté: ${vehicule.immatriculation}")
    }

    fun supprimerVehicule(immatriculation: String) {
        val v = vehicules.find { it.immatriculation == immatriculation }
        if (v != null) {
            vehicules.remove(v)
            println("Véhicule supprimé: $immatriculation")
        } else {
            println("Aucun véhicule trouvé avec l'immatriculation $immatriculation")
        }
    }

    fun reserverVehicule(
        immatriculation: String,
        conducteur: Conducteur,
        dateDebut: String,
        dateFin: String
    ) {
        val v = vehicules.find { it.immatriculation == immatriculation }
            ?: throw VehiculeNonTrouveException("Véhicule avec immatriculation $immatriculation non trouvé.")
        if (!v.estDisponible()) throw VehiculeIndisponibleException("Le véhicule $immatriculation n'est pas disponible.")

        v.marquerIndisponible()
        val reservation = Reservation(v, conducteur, dateDebut, dateFin, v.kilometrage, null)
        reservations.add(reservation)
        println("Véhicule $immatriculation réservé pour ${conducteur.prenom} ${conducteur.nom} du $dateDebut au $dateFin")
    }

    fun afficherVehiculesDisponibles() {
        println("--- Véhicules disponibles ---")
        val disp = vehicules.filter { it.estDisponible() }
        if (disp.isEmpty()) println("Aucun véhicule disponible.") else disp.forEach { it.afficherDetails() }
    }

    fun afficherReservations() {
        println("--- Réservations en cours ---")
        if (reservations.isEmpty()) println("Aucune réservation.") else reservations.forEach { it.afficherDetails() }
    }
}

// ==========================
// Main : démonstration
// ==========================

fun main() {
    println("===== Démonstration : Bibliothèque =====")
    val bib = Bibliotheque()

    val livre2 = Livre("Kotlin en Action", "Dmitry Jemerov", "978-1617293290", 2)
    val livre3 = Livre("Clean Code", "Robert C. Martin", "978-0132350884", 1)

    val user1 = Utilisateur("Doe", "Alice", "alice@example.com", 1)
    val user2 = Utilisateur("Dupont", "Bob", "bob@example.com", 2)

    bib.ajouterLivre(livre2)
    bib.ajouterLivre(livre3)
    bib.ajouterUtilisateur(user1)
    bib.ajouterUtilisateur(user2)

    user1.emprunterLivre(livre2, "2025-09-01")
    user2.emprunterLivre(livre3, "2025-09-02")
    user2.emprunterLivre(livre3, "2025-09-03")

    bib.afficherTousLesLivres()
    println()
    user1.afficherInfos(); user1.afficherEmprunts()
    println()
    user2.afficherInfos(); user2.afficherEmprunts()

    println("\n--- Retour d'un livre ---")
    val empruntARetourner = user1.emprunts.firstOrNull()
    empruntARetourner?.retournerLivre("2025-09-10")

    println()
    bib.afficherTousLesLivres()
    user1.afficherEmprunts()

    println("\n===== Démonstration : Parc Automobile =====")
    val parc = ParcAutomobile()

    val v1 = Voiture3("AA-123-BB", "Peugeot", "308", 50000, true, 5, "essence")
    val v2 = Voiture3("CC-456-DD", "Tesla", "Model 3", 20000, true, 4, "électrique")
    val m1 = Moto3("EE-789-FF", "Yamaha", "MT-07", 15000, true, 689)

    parc.ajouterVehicule(v1)
    parc.ajouterVehicule(v2)
    parc.ajouterVehicule(m1)

    val c1 = Conducteur("Martin", "Paul", "PERM1234")
    val c2 = Conducteur("Leblanc", "Sophie", "PERM5678")

    try {
        parc.reserverVehicule("AA-123-BB", c1, "2025-10-01", "2025-10-05")
        parc.reserverVehicule("EE-789-FF", c2, "2025-10-02", "2025-10-03")
        parc.reserverVehicule("AA-123-BB", c2, "2025-10-06", "2025-10-07")
    } catch (e: VehiculeIndisponibleException) {
        println("Erreur de réservation: ${e.message}")
    } catch (e: VehiculeNonTrouveException) {
        println("Erreur: ${e.message}")
    }

    try {
        parc.reserverVehicule("ZZ-000-YY", c1, "2025-10-01", "2025-10-02")
    } catch (e: Exception) {
        println("Erreur: ${e.message}")
    }

    println()
    parc.afficherVehiculesDisponibles()
    println()
    parc.afficherReservations()

    println("\n--- Clôture d'une réservation ---")
    val resToClose = parc.reservations.firstOrNull()
    resToClose?.cloturerReservation(50300)

    println()
    parc.afficherVehiculesDisponibles()
    parc.afficherReservations()
}
