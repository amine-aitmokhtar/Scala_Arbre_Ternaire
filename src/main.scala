
/*

  Nom :    AIT MOKHTAR
  Prenom : Mohamed Amine
  Groupe : 1

 */


/*
 **************
 les mots clé :
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
   *******************
   Méthode remove(): son but est de supprimer une clé et sa valeur associée de l'arbre.
   *******************

   ***********
   Paramètres :
   ***********

   1 - key: String : La clé à supprimer de l'arbre.

   ****************
   Type de retour : Elle renvoie un tuple contenant :
   ****************

   1. Une Option[A] avec la valeur supprimée si elle existe, sinon None.
   2. Une nouvelle instance de l'arbre après suppression.

   Cette méthode redirige vers Tree.remove pour gérer la suppression et assurer qu'aucune branche morte ne subsiste.
   */
  def remove(key: String): (Option[A], Tree[A]) = Tree.remove(this, key, 0)
}

// Définition des objets Leaf et Node
case object Leaf extends Tree[Nothing]

case class Node[A](
                    value: Option[A], // Valeur stockée dans le nœud (Option pour gérer l'absence de valeur)
                    char: Char,       // Caractère associé à ce nœud
                    left: Tree[A],    // Sous-arbre gauche
                    next: Tree[A],    // Sous-arbre pour le prochain caractère
                    right: Tree[A]    // Sous-arbre droit
                  ) extends Tree[A]

// Object compagnon Tree contenant les méthodes statiques
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
      if (n < key.length) {
        // Crée un nouveau nœud avec le caractère actuel de la clé
        val newNode = Node(None, key.charAt(n), Leaf, Leaf, Leaf)
        insert(newNode, key, value, n)
      } else {
        Node(Some(value), key.charAt(n - 1), Leaf, Leaf, Leaf)
      }


    case node: Node[A] if node.char > key.charAt(n) =>
      // Cas où le caractère du nœud est supérieur au caractère de la clé, insertion à gauche
      node.copy(left = insert(node.left, key, value, n))


    case node: Node[A] if node.char < key.charAt(n) =>
      // Cas où le caractère du nœud est inférieur au caractère de la clé, insertion à droite
      node.copy(right = insert(node.right, key, value, n))


    case node: Node[A] if n < key.length - 1 =>
      // On avance au prochain caractère de la clé en utilisant le pointeur `next`
      node.copy(next = insert(node.next, key, value, n + 1))


    case node: Node[A] =>
      // Si on atteint la fin de la clé, on associe la valeur au nœud
      node.copy(value = Some(value))
  }


  // Méthode d'insertion pour simuler un Set (associe toujours `true` à une clé)
  def insert(root: Tree[Boolean], key: String): Tree[Boolean] =
    insert(root, key, true, 0)

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

   Cette méthode utilise une boucle avec une récursion terminale pour parcourir tous les nœuds de l'arbre.
   Elle incrémente un compteur chaque fois qu'une clé/valeur est trouvée.
   */
  def size[A](tree: Tree[A]): Int = {
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
   Méthode toList(): son but est de retourner une liste contenant toutes les valeurs stockées dans un arbre ternaire.
   *******************

   ***********
   Paramètres :
   ***********

   1 - tree: Tree[A] : L'arbre à parcourir pour collecter les valeurs.

   ****************
   Type de retour : Elle renvoie une liste de type List[A] contenant toutes les valeurs.
   ****************

   Cette méthode utilise une boucle avec une récursion terminale pour parcourir les nœuds de l'arbre.
   Les valeurs trouvées sont ajoutées à une liste accumulée, qui est retournée à la fin.
   */
  def toList[A](tree: Tree[A]): List[A] = {
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

   Cette méthode utilise une récursion terminale pour parcourir l'arbre en fonction des caractères de la clé.
   */
  def get[A](tree: Tree[A], key: String): Option[A] = {
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

   Cette méthode utilise la méthode get pour déterminer si une clé est présente dans l'arbre.
   */
  def contains[A](tree: Tree[A], key: String): Boolean = get(tree, key).isDefined

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

   Cette méthode utilise une boucle avec une récursion terminale pour parcourir l'arbre et collecter toutes les paires clé/valeur.
   */
  def toKeyValueList[A](tree: Tree[A]): List[(String, A)] = {
    @annotation.tailrec
    def loop(nodes: List[(Tree[A], String)], acc: List[(String, A)]): List[(String, A)] = nodes match {
      case Nil => acc.reverse
      case (Leaf, _) :: tail => loop(tail, acc)
      case (Node(value, char, left, next, right), prefix) :: tail =>
        val newAcc = if (value.isDefined) (prefix + char, value.get) :: acc else acc
        loop((left, prefix) :: (next, prefix + char) :: (right, prefix) :: tail, newAcc)
    }
    loop(List((tree, "")), Nil)
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

   Cette méthode utilise un pattern matching pour parcourir l'arbre et supprimer la clé tout en maintenant la structure valide.
   */
  /*
  def remove[A](tree: Tree[A], key: String, n: Int): (Option[A], Tree[A]) = tree match {
    case Leaf => (None, Leaf)
    case Node(value, char, left, next, right) if char > key.charAt(n) =>
      val (removedValue, newLeft) = remove(left, key, n)
      (removedValue, Node(value, char, newLeft, next, right))

    case Node(value, char, left, next, right) if char < key.charAt(n) =>
      val (removedValue, newRight) = remove(right, key, n)
      (removedValue, Node(value, char, left, next, newRight))

    case Node(value, char, left, next, right) if n < key.length - 1 =>
      val (removedValue, newNext) = remove(next, key, n + 1)
      (removedValue, Node(value, char, left, newNext, right))

    case Node(value, char, left, next, right) =>
      val removedValue = value
      val newTree = if (left == Leaf && next == Leaf && right == Leaf) Leaf else Node(None, char, left, next, right)
      (removedValue, newTree)
  }
}
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


import scala.io.StdIn.readLine


object TestTree {  // Point d'entrée principal
  def main(args: Array[String]): Unit = {


    /****************************************************** ----- Test  ----- ********************************************************************************************/
    
    /*
    // Initialisation de l'arbre vide
    var tree: Tree[Boolean] = Tree()

    println("\n\n***************************************************** -----  Arbre initialisé (vide)    ----- *************************************************************\n")
    println(tree)
    println("Taille de l'arbre : " + tree.size)
    println("Liste des valeurs : " + tree.toList)

    // Test insertion de plusieurs clés
    val keys = List("chien", "chat", "chien", "coq", "elephant", "pie", "ch")
    keys.foreach(key => tree = Tree.insert(tree, key))

    println("\n\n***************************************************** -----  Arbre après insertion de plusieurs clés    ----- *************************************************************\n")

    println(tree)
    println("Taille de l'arbre : " + tree.size)
    println("Liste des valeurs : " + tree.toList)
    println("Liste des paires clé-valeur : " + tree.toKeyValueList)

    // Test de récupération d'une clé existante et non existante
    println("\n\n***************************************************** -----  Test de la fonction get    ----- *************************************************************\n")
    val existingKey = "chat"
    val nonExistingKey = "loup"
    println(s"\nRécupération de la valeur pour la clé existante '$existingKey' : " + tree.get(existingKey))
    println(s"Récupération de la valeur pour la clé inexistante '$nonExistingKey' : " + tree.get(nonExistingKey))

    // Test de présence d'une clé
    println("\n\n***************************************************** -----  Test de la fonction contains    ----- *************************************************************\n")
    println(s"\nLa clé '$existingKey' existe-t-elle ? : " + tree.contains(existingKey))
    println(s"La clé '$nonExistingKey' existe-t-elle ? : " + tree.contains(nonExistingKey))

    // Test suppression d'une clé existante
    println("\n\n***************************************************** -----  Test de la fonction remove    ----- *************************************************************\n")
    val keyToRemove = "chat"
    val (removedValue, updatedTree) = tree.remove(keyToRemove)
    println(s"\nSuppression de la clé '$keyToRemove', valeur supprimée : " + removedValue)
    println("Arbre après suppression :")
    println(updatedTree)
    println("Taille de l'arbre après suppression : " + updatedTree.size)
    println("Liste des paires clé-valeur après suppression : " + updatedTree.toKeyValueList)

    // Test suppression d'une clé inexistante
    val keyToRemoveNonExisting = "loup"
    val (removedValue2, updatedTree2) = tree.remove(keyToRemoveNonExisting)
    println(s"\nSuppression de la clé inexistante '$keyToRemoveNonExisting', valeur supprimée : " + removedValue2)
    println("Arbre après tentative de suppression d'une clé inexistante :")
    println(updatedTree2.toKeyValueList)

    // Test de suppression successive
    val successiveRemovals = List("chien", "pie", "coq", "elephant", "ch")
    val listKeyValue = tree.toKeyValueList
    println(s"\nSuppressions successives des cles '$successiveRemovals' a partire de l'arbre initial '$listKeyValue' :")
    var currentTree = tree
    successiveRemovals.foreach { key =>
      val (value, newTree) = currentTree.remove(key)
      println(s"\nSuppression de la clé '$key', valeur supprimée : " + value)
      println("Arbre après suppression :")
      println(newTree.toKeyValueList)
      currentTree = newTree
    }

    println("\nArbre final après suppressions successives :")
    println(currentTree)
    println("Taille finale de l'arbre : " + currentTree.size)
    println("Liste des paires clé-valeur finales : " + currentTree.toKeyValueList)
  }*/

    /****************************************************** ----- Fin des tests  ----- ********************************************************************************************/

    
    
    /****************************************************** ----- AFFICHAGE AVEC MENU  ----- ********************************************************************************************/
    
    runMenu()


    // Fonction pour afficher l'en-tête principal
    def printHeader(): Unit = {
      println("\u001b[2J") // Code ANSI pour effacer l'écran
      println("╔═════════════════════════════════════════════════════════════════════════════╗")
      println("║                       UNIVERSITE LE HAVRE NORMANDIE                         ║")
      println("║                    Projet SCALA : Arbre Ternaire                            ║")
      println("║                 Encadré par Monsieur Antoine Dutot                          ║")
      println("╠═════════════════════════════════════════════════════════════════════════════╣")
      println("║                      Module : PROGRAMMATION FONCTIONNEL                     ║")
      println("║                Réalisé par : AIT MOKHTAR MOHAMED AMINE                      ║")
      println("╚═════════════════════════════════════════════════════════════════════════════╝\n")
    }

    // Fonction pour afficher le menu principal
    def printMenu(): Unit = {
      println("╔═════════════════════════════════════════╗")
      println("║          MENU PRINCIPAL                ║")
      println("╠═════════════════════════════════════════╣")
      println("║ [1] Initialiser un arbre               ║")
      println("║ [2] Insérer des clés                   ║")
      println("║ [3] Test de récupération (get)         ║")
      println("║ [4] Vérifier l'existence (contains)    ║")
      println("║ [5] Supprimer une clé                  ║")
      println("║ [6] Supprimer plusieurs clés           ║")
      println("║ [7] Afficher l'arbre actuel            ║")
      println("║ [8] Quitter                            ║")
      println("╚═════════════════════════════════════════╝")
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
          case 1 =>
            println("╔═════════════════════════════════════╗")
            println("║       INITIALISER UN ARBRE         ║")
            println("╠═════════════════════════════════════╣")
            println("║ [1] Utiliser l'arbre de l'énoncé   ║")
            println("║ [2] Créer un arbre vide            ║")
            println("╚═════════════════════════════════════╝")
            print("Faites votre choix : ")
            val initChoice = readLine().toIntOption.getOrElse(0)
            initChoice match {
              case 1 =>
                tree = Tree()
                val keys = List("chien", "chat", "chien", "coq", "elephant", "pie", "ch")
                keys.foreach(key => tree = Tree.insert(tree, key))
                println("\nArbre initialisé avec les clés de l'énoncé.")
              case 2 =>
                tree = Tree()
                println("\nUn arbre vide a été initialisé.")
              case _ =>
                println("Option invalide, retour au menu principal.")
            }

          case 2 =>
            print("\nEntrez les clés à insérer (séparées par des espaces) : ")
            val keys = readLine().split(" ").toList
            keys.foreach(key => tree = Tree.insert(tree, key))
            println("\nClés insérées dans l'arbre.")

          case 3 =>
            if (tree.size == 0) {
              println("\nL'arbre est vide. Impossible d'effectuer un test de récupération.")
            } else {
              print("\nEntrez une clé à récupérer : ")
              val key = readLine()
              val value = tree.get(key)
              println(s"\nValeur associée à la clé '$key' : " + value.getOrElse("Aucune valeur trouvée"))
            }

          case 4 =>
            if (tree.size == 0) {
              println("\nL'arbre est vide. Impossible de vérifier l'existence d'une clé.")
            } else {
              print("\nEntrez une clé à vérifier : ")
              val key = readLine()
              println(s"\nLa clé '$key' existe-t-elle ? : " + tree.contains(key))
            }

          case 5 =>
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

          case 6 =>
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

          case 7 =>
            println("\nArbre actuel :")
            println(tree)
            println("Taille de l'arbre : " + tree.size)
            println("Liste des paires clé-valeur : " + tree.toKeyValueList)

          case 8 =>
            println("Merci d'avoir utilisé le programme. À bientôt !")
            running = false

          case _ =>
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



// ### Réponse aux questions


//  - *Questions sur la fonction Insert :

//  - **Obtiendrons-nous un jour la valeur `false` ?** Non, car `false` n'est jamais stocké dans l'arbre. Si une clé n'est pas présente, cela signifie simplement qu'elle n'est pas dans l'ensemble.

//  - **Que se passe-t-il si on demande une clé non présente ?** Si une clé est absente, l'arbre n'aura aucun nœud pour cette clé, ce qui signifie que nous traiterons l'absence de valeur comme équivalente à `false` ou à "non présent dans l'ensemble".

// ***************

//  - *Questions sur la fonction get :

//  - **Que faire 'get' si la clé n’existe pas ? Retourne None si la clé n'existe pas dans l'arbre, évitant les erreurs et respectant le concept de programmation fonctionnelle.

// ***************


/*
 * Questions sur la fonction remove :
 *
 * - **L’élément supprimé est-il la seule valeur à retourner ?**
 *   Non, la fonction retourne également une référence vers la nouvelle structure de l'arbre après suppression.
 *
 * - **Que faire si on fait deux ou trois suppressions de suite ?**
 *   Chaque suppression fonctionne indépendamment. Si des suppressions successives laissent des branches inutiles,
 *   elles sont nettoyées automatiquement grâce à la logique des conditions dans le code.
 *
 * - **Que faire si la valeur n’existe pas ?**
 *   La fonction retourne `(None, root)` pour indiquer qu'aucune modification n'a été faite sur l'arbre.
 *
 * - **Peut‑on supprimer des éléments qui ne sont pas en bas de l’arbre ?**
 *   Oui, la fonction permet de supprimer n'importe quel élément dans l'arbre, qu'il soit une feuille ou un nœud interne,
 *   tant qu'il correspond à une clé existante.
 *
 * - **Peut‑il rester des lettres qui ne sont pas utilisées dans un mot après suppression ?**
 *   Oui, cela peut arriver si une lettre fait partie d'une clé partiellement supprimée,
 *   mais elle a encore des pointeurs vers des sous-arbres utiles (gauche ou droit).
 *   Par exemple, après avoir inséré "chien" et "chat", puis supprimé "chien",
 *   la lettre 'i' restera dans l'arbre car elle a un pointeur vers 'a', qui est encore utile pour "chat".
 *   on peut eviter ca avec la reorganisation de l'arbre, mais c'est pas demande !

 *
 * - **Cette méthode n’est pas triviale à mettre en place, expliquez en détail son fonctionnement :**
 *   - La fonction parcourt l'arbre en suivant les caractères de la clé.
 *   - Lorsqu'elle trouve un nœud correspondant au dernier caractère de la clé, elle supprime la valeur associée.
 *   - Après suppression, chaque nœud est vérifié pour déterminer s'il doit être conservé ou remplacé par `Leaf`.
 *   - Cela garantit que l'arbre ne contient que des branches utiles après toute suppression.
 */



/**
 *
 * *********
 * conclusion : À travers ce TP et ce module, j'ai pu enrichir et apprendre un nouveau paradigme de programmation.
 * *********  j'ai pu apprendre comment construire les arbres binaire en scala tout en respectant les différentes
 * règles de programmation fonctionnelle(fonction pure,fonction totale,récursive terminale,...)
 *
 *
 */
