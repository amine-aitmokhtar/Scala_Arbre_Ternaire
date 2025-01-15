
/*

  Nom :    AIT MOKHTAR
  Prenom : Mohamed Amine
  Groupe : 1

 */


/*
 **************
 Mots clé :
 **************

 1 - trait :est utilisé pour définir des interfaces et pour partager des champs entre les classes,
            trait est similaire aux interface de Java.

 2 - sealed :  permet d’indiquer au compilateur que tous les types en héritant sont
               définis dans le même fichier.

 3 - case classe : pour représenter des structures de données
                   Ces classes sont extrêmement pratiques car possèdent plusieurs intérêts


 4 - +A : A est un type abstrait (il peut être remplacé par n’importe quelle type concret),
         le signe + pour indiquer la covariance
*/


// Déclaration du trait Tree avec l'import de Tree
sealed trait Tree[+A] {
  import Tree._

  /*
   *******************
   Méthode insert(): son but est d'insérer une clé avec une valeur associée dans l'arbre ternaire.
   *******************

   ***********
   Paramètres :
   ***********

   1 - key: String : La clé à insérer dans l'arbre.
   2 - value: B : La valeur associée à la clé.

   ****************
   Type de retour : Elle renvoie un nouvel arbre avec la clé/valeur insérée.
   ****************

   Cette méthode redirige vers la fonction Tree.insert qui gère la logique d'insertion.
   Elle est définie dans le trait pour être utilisée directement sur une instance d'arbre.
   */
  def insert[B >: A](key: String, value: B): Tree[B] = Tree.insert(this, key, value, 0)


  /*
   *******************
   Méthode size(): son but est de calculer le nombre de clés/valeurs stockées dans l'arbre.
   *******************

   ***********
   Paramètres : Aucun.
   ***********

   ****************
   Type de retour : Elle renvoie un entier représentant le nombre de clés/valeurs.
   ****************

   Cette méthode utilise Tree.size pour effectuer un parcours de l'arbre et compter les clés/valeurs présentes.
   */


  def size: Int = Tree.size(this)

  /*
      // Implémentation alternative de size (moins optimisée)
      def size: Int = this match {
      case Leaf => 0
      case Node(value, _, left, next, right) =>
        val currentValue = if (value.isDefined) 1 else 0
        currentValue + left.size + next.size + right.size
    }
   */


  /*
   *******************
   Méthode toList(): son but est de retourner une liste de toutes les valeurs stockées dans l'arbre.
   *******************

   ***********
   Paramètres : Aucun.
   ***********

   ****************
   Type de retour : Elle renvoie une liste de type List[A].
   ****************

   Cette méthode redirige vers Tree.toList pour parcourir l'arbre et collecter les valeurs dans une liste.
   */
  def toList: List[A] = Tree.toList(this)


  /*
  // Implémentation alternative de toList (moins optimisée)
  def toList: List[A] = this match {
    case Leaf => Nil
    case Node(value, _, left, next, right) =>
      val current = value.toList
      current ++ left.toList ++ next.toList ++ right.toList
  }
  */

  /*
   *******************
   Méthode get(): son but est de récupérer la valeur associée à une clé donnée.
   *******************

   ***********
   Paramètres :
   ***********

   1 - key: String : La clé dont on veut récupérer la valeur.

   ****************
   Type de retour : Elle renvoie une Option[A], contenant la valeur si elle existe, sinon None.
   ****************

   Cette méthode utilise Tree.get pour effectuer un parcours de l'arbre en recherchant la clé donnée.
   */
  def get(key: String): Option[A] = Tree.get(this, key)

  /*
   *******************
   Méthode contains(): son but est de vérifier si une clé existe dans l'arbre.
   *******************

   ***********
   Paramètres :
   ***********

   1 - key: String : La clé à rechercher dans l'arbre.

   ****************
   Type de retour : Elle renvoie un booléen (true si la clé existe, false sinon).
   ****************

   Cette méthode utilise Tree.contains pour effectuer un parcours de l'arbre et déterminer si la clé est présente.
   */
  def contains(key: String): Boolean = Tree.contains(this, key)

  /*
   *******************
   Méthode toKeyValueList(): son but est de retourner une liste de tuples contenant les clés et leurs valeurs associées.
   *******************

   ***********
   Paramètres : Aucun.
   ***********

   ****************
   Type de retour : Elle renvoie une liste de tuples (String, A).
   ****************

   Cette méthode redirige vers Tree.toKeyValueList pour collecter toutes les paires clé/valeur de l'arbre.
   */
  def toKeyValueList: List[(String, A)] = Tree.toKeyValueList(this)

  /*
   //Implémentation simple de la méthode toKeyValueList

   def toKeyValueList[A](root: Tree[A], key: String): List[(String, A)] = root match {
      case Leaf => Nil
      case node: Node[A] =>
        val current = if (node.value.isDefined) List((key + node.char, node.value.get)) else Nil
        current ++ toKeyValueList(node.left, key) ++ toKeyValueList(node.right, key) ++ toKeyValueList(node.next, key + node.char)
    }
  */

  /*
   *******************
   Méthode remove(): son but est de supprimer une clé et sa valeur associée de l'arbre.
   *******************

   ***********
   Paramètres :
   ***********

   1 - key: String : La clé à supprimer de l'arbre.

   ****************
   Type de retour :
   ****************

   1. Une Option[A] avec la valeur supprimée si elle existe, sinon None.
   2. Une nouvelle instance de l'arbre après suppression.

   Appelle la méthode `Tree.remove` pour gérer la logique de suppression.
   */
  def remove(key: String): (Option[A], Tree[A]) = Tree.remove(this, key, 0)
}

// Définition des objets `Leaf` et `Node`
case object Leaf extends Tree[Nothing]

case class Node[A](
                    value: Option[A], // Valeur stockée dans le nœud (Option pour gérer l'absence de valeur)
                    char: Char,       // Caractère associé à ce nœud
                    left: Tree[A],    // Sous-arbre gauche ( liste horizontale : ordre des lettres au meme niveau dans la clé )
                    next: Tree[A],    // Sous-arbre pour le prochain caractère ( liste verticale : ordre des lettres dans la clé )
                    right: Tree[A]    // Sous-arbre droit ( liste horizontale : ordre des lettres au meme niveau dans la clé )
                  ) extends Tree[A]





/* Object compagnon Tree contenant les méthodes statiques */

case object Tree {
  /*
   *******************
   Méthode apply(): son but est de créer un arbre vide.
   *******************

   ***********
   Paramètres : Aucun.
   ***********

   ****************
   Type de retour : Elle renvoie une instance de Leaf, représentant un arbre vide.
   ****************
   */
  def apply[A](): Tree[A] = Leaf


  // Méthode générique d'insertion (réutilisable pour tout type de valeur)
  // Fonction récursive pour insérer une clé et sa valeur dans l'arbre ternaire
  def insert[A](root: Tree[A], key: String, value: A, n: Int): Tree[A] = root match {
    case Leaf =>
      insert(Node(None, key.charAt(n), Leaf, Leaf, Leaf), key, value, n)

    case node: Node[A] if node.char > key.charAt(n) =>
      node.copy(left = insert(node.left, key, value, n))

    case node: Node[A] if node.char < key.charAt(n) =>
      node.copy(right = insert(node.right, key, value, n))

    case node: Node[A] if n < key.length - 1 =>
      node.copy(next = insert(node.next, key, value, n + 1))

    case node: Node[A] =>
      node.copy(value = Some(value))
  }

  // Méthode insert qui associe automatiquement la valeur true pour une clé dans Tree[Boolean]
  def insert(root: Tree[Boolean], key: String): Tree[Boolean] = insert(root, key, true, 0)

  /*
   *******************
   Méthode size(): son but est de compter le nombre de clés/valeurs stockées dans un arbre ternaire.
   *******************

   ***********
   Paramètres :
   ***********

   1 - tree: Tree[A] : L'arbre à parcourir pour compter les clés/valeurs.

   ****************
   Type de retour : Elle renvoie un entier représentant le nombre de clés/valeurs dans l'arbre.
   ****************

   ****************
   Fonctionnement : Cette méthode utilise une boucle avec une récursion terminale pour parcourir tous les nœuds de l'arbre.
                    Elle incrémente un compteur chaque fois qu'une clé/valeur est trouvée.
   ****************

   */

  private def size[A](tree: Tree[A]): Int = {
    @annotation.tailrec
    def loop(nodes: List[Tree[A]], acc: Int): Int = nodes match {
      case Nil => acc
      case Leaf :: tail => loop(tail, acc)
      case Node(value, _, left, next, right) :: tail =>
        val newAcc = if (value.isDefined) acc + 1 else acc
        loop(left :: next :: right :: tail, newAcc)
    }
    loop(List(tree), 0)
  }

  /*
   *******************
   Méthode toList(): son but est de retourner une liste contenant toutes les valeurs stockées dans l'arbre.
   *******************

   ***********
   Paramètres :
   ***********

   1 - tree: Tree[A] : L'arbre à parcourir pour collecter les valeurs.

   ****************
   Type de retour : Elle renvoie une liste de type List[A] contenant toutes les valeurs.
   ****************

   ****************
   Fonctionnement : Cette méthode utilise une boucle avec une récursion terminale pour parcourir les nœuds de l'arbre.
                    Les valeurs trouvées sont ajoutées à une liste accumulée, qui est retournée à la fin.
   ****************

   */
  private def toList[A](tree: Tree[A]): List[A] = {
    @annotation.tailrec
    def loop(nodes: List[Tree[A]], acc: List[A]): List[A] = nodes match {
      case Nil => acc
      case Leaf :: tail => loop(tail, acc)
      case Node(value, _, left, next, right) :: tail =>
        val newAcc = if (value.isDefined) acc :+ value.get else acc
        loop(left :: next :: right :: tail, newAcc)
    }
    loop(List(tree), Nil)
  }

  /*
   *******************
   Méthode get(): son but est de rechercher une clé spécifique et de retourner sa valeur associée si elle existe.
   *******************

   ***********
   Paramètres :
   ***********

   1 - tree: Tree[A] : L'arbre dans lequel effectuer la recherche.
   2 - key: String : La clé à rechercher.

   ****************
   Type de retour : Elle renvoie une Option[A], contenant la valeur associée si elle existe, sinon None.
   ****************

   ****************
   Fonctionnement : Cette méthode utilise une récursion terminale pour parcourir l'arbre en fonction des caractères de la clé.
   ****************

   */
  private def get[A](tree: Tree[A], key: String): Option[A] = {
    @annotation.tailrec
    def loop(current: Tree[A], index: Int): Option[A] = current match {
      case Leaf => None
      case Node(value, char, left, next, right) =>
        if (index >= key.length) None
        else if (char > key.charAt(index)) loop(left, index)
        else if (char < key.charAt(index)) loop(right, index)
        else if (index == key.length - 1) value
        else loop(next, index + 1)
    }
    loop(tree, 0)
  }

  /*
   *******************
   Méthode contains(): son but est de vérifier si une clé donnée existe dans un arbre ternaire.
   *******************

   ***********
   Paramètres :
   ***********

   1 - tree: Tree[A] : L'arbre dans lequel effectuer la recherche.
   2 - key: String : La clé à vérifier.

   ****************
   Type de retour : Elle renvoie un booléen (true si la clé existe, false sinon).
   ****************

   ****************
   Fonctionnement : Cette méthode utilise la méthode get pour déterminer si une clé est présente dans l'arbre.
   ****************

   */
  private def contains[A](tree: Tree[A], key: String): Boolean = get(tree, key).isDefined

  /*
   *******************
   Méthode toKeyValueList(): son but est de collecter toutes les clés et leurs valeurs associées dans un arbre ternaire.
   *******************

   ***********
   Paramètres :
   ***********

   1 - tree: Tree[A] : L'arbre à parcourir pour collecter les clés et les valeurs.

   ****************
   Type de retour : Elle renvoie une liste de tuples (String, A).
   ****************

   ****************
   Fonctionnement : Cette méthode utilise une boucle avec une récursion terminale pour parcourir l'arbre et collecter toutes les paires clé/valeur.
   ****************

   */


  // Méthode toKeyValueList avec la récursivité terminale
  private def toKeyValueList[A](root: Tree[A]): List[(String, A)] = {
    @annotation.tailrec
    def loop(nodes: List[(Tree[A], String)], acc: List[(String, A)]): List[(String, A)] = nodes match {
      case Nil => acc.reverse
      case (Leaf, _) :: tail => loop(tail, acc)
      case (Node(value, char, left, next, right), prefix) :: tail =>
        val newAcc = if (value.isDefined) (prefix + char, value.get) :: acc else acc
        loop((left, prefix) :: (next, prefix + char) :: (right, prefix) :: tail, newAcc)
    }
    loop(List((root, "")), Nil)
  }



  /*
   *******************
   Méthode remove(): son but est de supprimer une clé et sa valeur associée d'un arbre ternaire.
   *******************

   ***********
   Paramètres :
   ***********

   1 - tree: Tree[A] : L'arbre dans lequel effectuer la suppression.
   2 - key: String : La clé à supprimer.
   3 - n: Int : L'indice actuel dans la clé (utilisé pour la récursion).

   ****************
   Type de retour : Elle renvoie un tuple contenant :
   ****************

   1. Une Option[A] représentant la valeur supprimée si elle existait, sinon None.
   2. Un nouvel arbre avec la clé et la valeur supprimées.

   ****************
   Fonctionnement : Cette méthode utilise un pattern matching pour parcourir l'arbre et supprimer la clé tout en maintenant la structure valide.
   ****************
 */

  private def remove[A](root: Tree[A], key: String, n: Int): (Option[A], Tree[A]) = root match {
    case Leaf =>
      // Si l'arbre est vide, aucune valeur n'existe, on retourne (None, Leaf).
      (None, Leaf)

    case node: Node[A] if node.char > key.charAt(n) =>
      // Parcours à gauche si le caractère est plus grand.
      val (removedValue, newLeft) = remove(node.left, key, n)
      // Si le sous-arbre gauche devient inutile, supprimer le nœud.
      val newTree = if (newLeft == Leaf && node.next == Leaf && node.right == Leaf && node.value.isEmpty) Leaf
      else node.copy(left = newLeft)
      (removedValue, newTree)

    case node: Node[A] if node.char < key.charAt(n) =>
      // Parcours à droite si le caractère est plus petit.
      val (removedValue, newRight) = remove(node.right, key, n)
      // Si le sous-arbre droit devient inutile, supprimer le nœud.
      val newTree = if (newRight == Leaf && node.next == Leaf && node.left == Leaf && node.value.isEmpty) Leaf
      else node.copy(right = newRight)
      (removedValue, newTree)

    case node: Node[A] if n < key.length - 1 =>
      // Parcours du sous-arbre "next" pour le prochain caractère de la clé.
      val (removedValue, newNext) = remove(node.next, key, n + 1)
      // Si le sous-arbre "next" devient inutile, supprimer le nœud.
      val newTree = if (newNext == Leaf && node.left == Leaf && node.right == Leaf && node.value.isEmpty) Leaf
      else node.copy(next = newNext)
      (removedValue, newTree)

    case node: Node[A] =>
      // Cas où on atteint le dernier caractère de la clé.
      val removedValue = node.value
      // Si ce nœud n'a pas d'autres enfants, supprimer complètement le nœud.
      val newTree = if (node.left == Leaf && node.next == Leaf && node.right == Leaf) Leaf
      else node.copy(value = None)
      (removedValue, newTree)
  }

}

// Import pour lire les entrées utilisateur depuis la console (readLine).
import scala.io.StdIn.readLine

object TestTree {
  def main(args: Array[String]): Unit = {


    /*
    /** ------------------------------------------------------ Vérification des fonctionnalités de l'arbre ternaire ----------------------------------------------------- * */

    // Initialisation d'un arbre vide pour effectuer les tests
    var arbre: Tree[Boolean] = Tree()

    println("\n\n---------------------------------------------------------       État initial de l'arbre (vide)        ----------------------------------------------------------\n")
    println(arbre)
    println("Nombre d'éléments dans l'arbre : " + arbre.size)
    println("Liste des valeurs présentes dans l'arbre : " + arbre.toList)

    // Insertion de plusieurs clés dans l'arbre
    arbre = Tree.insert(arbre, "chien")
    arbre = Tree.insert(arbre, "chat")
    arbre = Tree.insert(arbre, "coq")
    arbre = Tree.insert(arbre, "pie")

    println("\n\n--------------------------------------------------------       État de l'arbre après insertion        ----------------------------------------------------------\n")

    println(arbre)
    println("Nombre total d'éléments après insertion : " + arbre.size)
    println("Valeurs actuellement dans l'arbre : " + arbre.toList)
    println("Paires clé-valeur enregistrées : " + arbre.toKeyValueList)

    // Récupération de valeurs pour des clés spécifiques
    println("\n\n-----------------------------------------------------        Test de récupération avec 'get'        ------------------------------------------------------------\n")
    val cleExistante = "chat"
    val cleInexistante = "chaton"
    println(s"\nRécupération pour la clé existante '$cleExistante' : " + arbre.get(cleExistante))
    println(s"Récupération pour une clé absente '$cleInexistante' : " + arbre.get(cleInexistante))

    // Vérification de l'existence des clés dans l'arbre
    println("\n\n-------------------------------------------------        Vérification de l'existence avec 'contains'        ------------------------------------------------------\n")
    println(s"\nLa clé '$cleExistante' est-elle présente ? : " + arbre.contains(cleExistante))
    println(s"La clé '$cleInexistante' est-elle présente ? : " + arbre.contains(cleInexistante))

    // Suppression d'une clé existante
    println("\n\n-------------------------------------------------------       Suppression d'une clé existante        ------------------------------------------------------------\n")
    val cleASupprimer = "chat"
    val (valeurSupprimee, arbreMisAJour) = arbre.remove(cleASupprimer)
    println(s"\nClé supprimée : '$cleASupprimer', Valeur associée : " + valeurSupprimee)
    println("État de l'arbre après suppression :")
    println(arbreMisAJour)
    println("Nombre total d'éléments après suppression : " + arbreMisAJour.size)
    println("Paires clé-valeur restantes : " + arbreMisAJour.toKeyValueList)

    // Suppression d'une clé inexistante
    val cleASupprimerInexistante = "mouette"
    val (valeurSupprimee2, arbreMisAJour2) = arbre.remove(cleASupprimerInexistante)
    println(s"\nTentative de suppression d'une clé inexistante '$cleASupprimerInexistante', Valeur supprimée : " + valeurSupprimee2)
    println("État de l'arbre après cette tentative :")
    println(arbreMisAJour2.toKeyValueList)

    // Suppressions successives de plusieurs clés
    val suppressionsSuccessives = List("chien", "chaton", "coq", "pie")
    val listePairesInitiales = arbre.toKeyValueList
    println(s"\nSuppressions successives des clés suivantes : '$suppressionsSuccessives' à partir de l'arbre initial : '$listePairesInitiales' :")
    var arbreCourant = arbre
    suppressionsSuccessives.foreach { cle =>
      val (valeur, nouvelArbre) = arbreCourant.remove(cle)
      println(s"\nClé supprimée : '$cle', Valeur associée : " + valeur)
      println("État de l'arbre après cette suppression :")
      println(nouvelArbre.toKeyValueList)
      arbreCourant = nouvelArbre
    }

    println("\nÉtat final de l'arbre après toutes les suppressions :")
    println(arbreCourant)
    println("Nombre total d'éléments restants dans l'arbre : " + arbreCourant.size)
    println("Paires clé-valeur finales : " + arbreCourant.toKeyValueList)

    /** ------------------------------------------------------------- Fin des tests de l'arbre ternaire ---------------------------------------------------------------- */

*/






    /** -------------------------------------------------------------          AFFICHAGE AVEC MENU        -------------------------------------------------------------*/


    runMenu()


    // Fonction pour afficher l'en-tête principal
    def printHeader(): Unit = {
      println("\u001b[2J") // Code ANSI pour effacer l'écran
      println("╔═════════════════════════════════════════════════════════════════════════════╗")
      println("║                       UNIVERSITE LE HAVRE NORMANDIE                         ║")
      println("║                    Projet SCALA : Arbre Ternaire                            ║")
      println("║                 Encadré par Monsieur Antoine DUTOT                          ║")
      println("╠═════════════════════════════════════════════════════════════════════════════╣")
      println("║                      Module : PROGRAMMATION FONCTIONNEL                     ║")
      println("║                Réalisé par : AIT MOKHTAR MOHAMED AMINE                      ║")
      println("╚═════════════════════════════════════════════════════════════════════════════╝\n")
    }

    // Fonction pour afficher le menu principal

    def printMenu(): Unit = {
      println("╔════════════════════════════════════════════════════════════╗")
      println("║                      MENU PRINCIPAL                        ║")
      println("╠════════════════════════════════════════════════════════════╣")
      println("║ [1]  Initialiser un arbre                                  ║")
      println("║ [2]  Insérer des clés                                      ║")
      println("║ [3]  Afficher la taille de l'arbre                         ║")
      println("║ [4]  Afficher la liste des valeurs (toList)                ║")
      println("║ [5]  Test de récupération (get)                            ║")
      println("║ [6]  Vérifier l'existence d'une clé (contains)             ║")
      println("║ [7]  Afficher les paires clé-valeur (toKeyValueList)       ║")
      println("║ [8]  Supprimer une clé (remove)                            ║")
      println("║ [9]  Supprimer plusieurs clés                              ║")
      println("║ [10] Afficher l'arbre actuel                               ║")
      println("║ [11] Quitter                                               ║")
      println("╚════════════════════════════════════════════════════════════╝")
    }


    // Fonction principale pour exécuter le menu
    def runMenu(): Unit = {
      var running = true
      var tree: Tree[Boolean] = Tree() // Initialisation par défaut

      while (running) {
        printHeader()
        printMenu()
        print("Choisissez une option : ")
        val choice = readLine().toIntOption.getOrElse(0)

        choice match {
          case 1 => // Initialiser un arbre
            println("╔═════════════════════════════════════╗")
            println("║       INITIALISER UN ARBRE          ║")
            println("╠═════════════════════════════════════╣")
            println("║ [1] Utiliser l'arbre de l'énoncé    ║")
            println("║ [2] Créer un arbre vide             ║")
            println("╚═════════════════════════════════════╝")
            print("Faites votre choix : ")
            val initChoice = readLine().toIntOption.getOrElse(0)
            initChoice match {
              case 1 =>
                tree = Tree()
                val keys = List("chien", "chat", "coq", "pie")
                keys.foreach(key => tree = Tree.insert(tree, key))
                println("\nArbre initialisé avec les clés de l'énoncé.")
              case 2 =>
                tree = Tree()
                println("\nUn arbre vide a été initialisé.")
              case _ =>
                println("Option invalide, retour au menu principal.")
            }

          case 2 => // Insérer des clés
            print("\nEntrez les clés à insérer (séparées par des espaces) : ")
            val keys = readLine().split(" ").toList
            keys.foreach(key => tree = Tree.insert(tree, key))
            println("\nClés insérées dans l'arbre.")

          case 3 => // Afficher la taille de l'arbre
            println(s"\nTaille de l'arbre : ${tree.size}")

          case 4 => // Afficher la liste des valeurs (toList)
            val values = tree.toList
            println(s"\nListe des valeurs dans l'arbre : $values")

          case 5 => // Test de récupération (get)
            if (tree.size == 0) {
              println("\nL'arbre est vide. Impossible d'effectuer un test de récupération.")
            } else {
              print("\nEntrez une clé à récupérer : ")
              val key = readLine()
              val value = tree.get(key)
              println(s"\nValeur associée à la clé '$key' : " + value.getOrElse("Aucune valeur trouvée"))
            }

          case 6 => // Vérifier l'existence d'une clé (contains)
            if (tree.size == 0) {
              println("\nL'arbre est vide. Impossible de vérifier l'existence d'une clé.")
            } else {
              print("\nEntrez une clé à vérifier : ")
              val key = readLine()
              println(s"\nLa clé '$key' existe-t-elle ? : " + tree.contains(key))
            }

          case 7 => // Afficher les paires clé-valeur (toKeyValueList)
            val keyValuePairs = tree.toKeyValueList
            println(s"\nListe des paires clé-valeur : $keyValuePairs")

          case 8 => // Supprimer une clé (remove)
            if (tree.size == 0) {
              println("\nL'arbre est vide. Aucune clé à supprimer.")
            } else {
              print("\nEntrez une clé à supprimer : ")
              val key = readLine()
              val (removedValue, updatedTree) = tree.remove(key)
              if (removedValue.isDefined) {
                println(s"\nClé '$key' supprimée avec la valeur associée : ${removedValue.get}.")
              } else {
                println(s"\nLa clé '$key' n'existe pas dans l'arbre.")
              }
              tree = updatedTree
            }

          case 9 => // Supprimer plusieurs clés
            if (tree.size == 0) {
              println("\nL'arbre est vide. Aucune clé à supprimer.")
            } else {
              print("\nEntrez les clés à supprimer (séparées par des espaces) : ")
              val keys = readLine().split(" ").toList
              keys.foreach { key =>
                val (removedValue, updatedTree) = tree.remove(key)
                if (removedValue.isDefined) {
                  println(s"\nClé '$key' supprimée avec la valeur associée : ${removedValue.get}.")
                } else {
                  println(s"\nLa clé '$key' n'existe pas dans l'arbre.")
                }
                tree = updatedTree
              }
            }

          case 10 => // Afficher l'arbre actuel
            println("\nArbre actuel :")
            println(tree)
            println(s"Taille de l'arbre : ${tree.size}")
            println(s"Liste des paires clé-valeur : ${tree.toKeyValueList}")

          case 11 => // Quitter
            println("Merci d'avoir utilisé le programme. À bientôt :) !")
            running = false

          case _ => // Option invalide
            println("Option invalide, veuillez réessayer.")
        }

        if (running) {
          println("\nAppuyez sur Entrée pour revenir au menu principal.")
          readLine()
        }
      }
    }



  }

}





/*******************************************************
 ****************** RÉPONSES AUX QUESTIONS **************
 *******************************************************/

/** =====================================================
 * === Questions liées à la méthode `insert` ===
 * =====================================================
 *
 * - **La valeur `false` peut-elle apparaître dans l'arbre ?**
 *   Non. La méthode `insert` ne stocke jamais la valeur `false`.
 *   L'absence d'une clé est interprétée comme sa non-présence
 *   dans l'ensemble des clés.
 *
 * - **Que se passe-t-il si une clé recherchée est absente ?**
 *   Si une clé n'est pas présente dans l'arbre, aucun nœud
 *   correspondant n'existe. Cela équivaut à considérer que
 *   la clé est inexistante.
 *
 */

/** =====================================================
 * === Questions liées à la méthode `get` ===
 * =====================================================
 *
 * - **Que retourne la méthode si la clé n'existe pas ?**
 *   La méthode `get` renvoie `None` lorsqu'une clé n'est pas
 *   trouvée dans l'arbre. Cela garantit une gestion sûre et
 *   évite les erreurs inutiles.
 *
 */

/** =====================================================
 * === Questions liées à la méthode `remove` ===
 * =====================================================
 *
 * - **La valeur supprimée est-elle la seule chose retournée ?**
 *   Non. En plus de la valeur supprimée, la méthode renvoie
 *   une nouvelle instance de l'arbre, mise à jour après la
 *   suppression.
 *
 * - **Comment la méthode gère-t-elle des suppressions successives ?**
 *   Chaque suppression est indépendante. Si des branches
 *   inutilisées apparaissent après plusieurs suppressions,
 *   elles sont automatiquement supprimées grâce à la logique
 *   d'implémentation.
 *
 * - **Que se passe-t-il si la clé à supprimer n'existe pas ?**
 *   La méthode renvoie `(None, tree)` pour indiquer qu'aucune
 *   modification n'a été effectuée sur l'arbre.
 *
 * - **Peut-on supprimer des nœuds internes ?**
 *   Oui, la méthode est capable de supprimer aussi bien des
 *   feuilles que des nœuds internes, à condition qu'ils
 *   correspondent à une clé existante.
 *
 * - **Des lettres inutiles peuvent-elles rester après suppression ?**
 *   Oui, des lettres peuvent subsister si elles pointent
 *   vers des sous-arbres encore utilisés. Par exemple, si
 *   "chien" et "chat" sont insérés, puis "chien" est supprimé,
 *   la lettre 'i' peut rester car elle est encore utilisée
 *   pour former "chat".
 *
 * - **Pourquoi la méthode `remove` est-elle complexe ?**
 *   1. Elle parcourt l'arbre en suivant chaque caractère de la clé.
 *   2. Elle supprime la valeur associée au dernier caractère.
 *   3. Chaque nœud est vérifié après suppression pour déterminer
 *      s'il doit être conservé ou remplacé par `Leaf`.
 *   4. Cela garantit que l'arbre reste cohérent et ne contient
 *      que des branches utiles.
 *
 */



/*
 * *************************************************************************************************************************************************
 * conclusion : À travers ce projet et ce module, j'ai pu enrichir mes compétences en découvrant un nouveau paradigme de programmation.
 * **********   J'ai également acquis la capacité de construire et de manipuler des structures complexes comme des arbres ternaires en Scala,
 * **********   tout en respectant les différentes règles de la programmation fonctionnelle (fonction pure, fonction totale, récursivité terminale).
 ***************************************************************************************************************************************************
 */
