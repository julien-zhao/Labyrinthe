package CodeMiniProjet;

import java.util.ArrayList;

import CodeMiniProjet.WeightedGraph.Graph;
import CodeMiniProjet.WeightedGraph.Vertex;

public class Labyrinthe {

	// le prisogner peut que se déplacé sur les cases '.' ou 'S'
	public static boolean can_i_move(int vertex_num, ArrayList<Vertex> vertexList, int numberV) {
		if (vertex_num < numberV) {
			return (vertexList.get(vertex_num).position == '.' || vertexList.get(vertex_num).position == 'S');
		} else {
			return false;
		}
	}

	static ArrayList<Character> BuildMove(Graph graph, int start, int end, int ncols, int numberV) {
		ArrayList<Vertex> chemin = Algo.AStar(graph, start, end, ncols, numberV);//plus cours chemin
		ArrayList<Character> directions = new ArrayList<Character>();

		for (int i = 0; i < (chemin.size() - 1); i++) {
			int mouvement = chemin.get(i + 1).num - chemin.get(i).num;
			if (mouvement == 1)
				directions.add('R'); // 'R' = right
			else if (mouvement == -1)
				directions.add('L'); // 'L' = left
			else if (mouvement == ncols)
				directions.add('B'); // 'B' = bottom
			else if (mouvement == (-1 * ncols))
				directions.add('T'); // 'T' = top
			else
				return directions;
		}
		return directions;
	}

	/*
	 * static boolean burn_around(int vertex_num, ArrayList<Vertex> vertexList, int
	 * nlignes, int ncols) { //'A' pour éviter que les flammes soient propagées le
	 * même tour où elles sont crées
	 * 
	 * if (vertexList.get(vertex_num).j != 0) { // Si nous ne sommes pas au début
	 * d'une ligne if (vertexList.get(vertex_num - 1).position == '.') //
	 * Signification : libre vertexList.get(vertex_num - 1).position = 'A'; //'A'
	 * pour éviter que les flammes soient propagées le même tour où elles sont crées
	 * else if (vertexList.get(vertex_num - 1).position == 'S' ||
	 * vertexList.get(vertex_num - 1).position == 'D') // D = début , S = sortie
	 * return true; // La fonction burn_around retourne true si gameover } // if
	 * (vertexList.get(vertex_num).j != 0)
	 * 
	 * if (vertexList.get(vertex_num).j != (ncols - 1) ) { // Si nous ne sommes pas
	 * au bout d'une ligne if (vertexList.get(vertex_num + 1).position == '.') //
	 * Signification : libre vertexList.get(vertex_num + 1).position = 'A'; //'A'
	 * pour éviter que les flammes soient propagées le même tour où elles sont crées
	 * else if (vertexList.get(vertex_num + 1).position == 'S' ||
	 * vertexList.get(vertex_num + 1).position == 'D') return true; // La fonction
	 * burn_around retourne true si gameover } // if (vertexList.get(vertex_num).j
	 * != (ncols - 1) )
	 * 
	 * if (vertexList.get(vertex_num).i != 0) { // Si nous ne sommes pas au début
	 * d'une colonne if (vertexList.get(vertex_num - ncols).position == '.') //
	 * Signification : libre vertexList.get(vertex_num - ncols).position = 'A';
	 * //'A' pour éviter que les flammes soient propagées le même tour où elles sont
	 * crées else if (vertexList.get(vertex_num - ncols).position == 'S' ||
	 * vertexList.get(vertex_num - ncols).position == 'D') // D = début , S = sortie
	 * return true; // La fonction burn_around retourne true si gameover } // if
	 * (vertexList.get(vertex_num).i != 0)
	 * 
	 * if ( vertexList.get(vertex_num).i != (nlignes-1)) { // Si nous ne sommes pas
	 * à la fin d'une colonne if (vertexList.get(vertex_num + ncols).position ==
	 * '.') // Signification : libre vertexList.get(vertex_num + ncols).position =
	 * 'A'; //'A' pour éviter que les flammes soient propagées le même tour où elles
	 * sont crées else if (vertexList.get(vertex_num + ncols).position == 'S' ||
	 * vertexList.get(vertex_num + ncols).position == 'D') // D = début , S = sortie
	 * return true; // La fonction burn_around retourne true si gameover } // if (
	 * vertexList.get(vertex_num).i != (nlignes-1))
	 * 
	 * return false; // La fonction burn_around retourne true si gameover ; false
	 * sinon }
	 * 
	 */

	static boolean win_move(int debut, ArrayList<Vertex> vertexList, int nlignes, int ncols) {

		/* S = sortie */
		int tmpJ = vertexList.get(debut).j;
		int tmpI = vertexList.get(debut).i;
		boolean left = tmpJ != 0 && (vertexList.get(debut - 1).position == 'S');
		boolean right = tmpJ != (ncols - 1) && (vertexList.get(debut + 1).position == 'S');
		boolean top = tmpI != 0 && vertexList.get(debut - ncols).position == 'S';
		boolean bottom = tmpI != (nlignes - 1) && vertexList.get(debut + ncols).position == 'S';

		return top || left || right || bottom;
	}

	static boolean move_prisoner(char directionMouvementPossiblePourCeTour, ArrayList<Vertex> vertexList, int nlignes,
			int ncols, int end) {
		int debut = 0; // Initialisation de la variable.

		// Nous parcourons la carte et localisons l'emplacement du prisoner
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).position == 'D') // Signification : début
				debut = i;
		} // for

		/*
		 * La fonction win_move() vérifie si le prisonnier est à une position du point
		 * de sortie (à une position de la victoire)
		 */
		boolean win = win_move(debut, vertexList, nlignes, ncols);

		if (win)
			return true; // La fonction move_prisoner retourne true si victoire
		else {
			vertexList.get(debut).position = 'L'; // L signifie que le prisonnier est déjà passé par cette case

			if (directionMouvementPossiblePourCeTour == 'B') // 'B' = bottom
				vertexList.get(debut + ncols).position = 'D'; // On fait descendre le prisonnier.
			else if (directionMouvementPossiblePourCeTour == 'T') // 'T' = top
				vertexList.get(debut - ncols).position = 'D'; // Nous déplaçons le prisonnier vers le haut.
			else if (directionMouvementPossiblePourCeTour == 'L') // 'L' = left
				vertexList.get(debut - 1).position = 'D'; // Nous déplaçons le prisonnier vers la gauche.
			else if (directionMouvementPossiblePourCeTour == 'R') // 'R' = right.
				vertexList.get(debut + 1).position = 'D'; // Nous déplaçons le prisonnier vers la droite.
		}
		return false;
	} // move_prisoner()

	static char run_instance(Graph graph, int start, int end, int nlignes, int ncols) {
		int turn = 0; // Le numéro du premier tour est 0.

		// La liste des mouvements quele prisonnier doit effectuer à chaque tour.
		ArrayList<Character> directions = BuildMove(graph, start, end, ncols, nlignes * ncols);

		while (turn < directions.size()) {
			/*
			 * for(int i = 0 ; i < graph.vertexlist.size() ; i++) { if(
			 * graph.vertexlist.get(i).position == 'A' ) // La lettre A représente un carré
			 * qui était autour du feu lors du tour précédent. Autrement dit, maintenant le
			 * feu se déplace. graph.vertexlist.get(i).position = 'F'; // Signification :
			 * feu } // for
			 * 
			 * // Si l'une des positions autour du feu est le pointde début ou de sortie, le
			 * jeu est terminé. for(int i = 0 ; i < graph.vertexlist.size() ; i++) { if(
			 * graph.vertexlist.get(i).position == 'F' ) { // Signification : feu if (
			 * burn_around(i, graph.vertexlist, nlignes, ncols) ) // La fonction burn_around
			 * retourne true si gameover ; false sinon return 'N'; // le prisonnier a PAS
			 * une chance d'être pardonné. } // if } // for
			 */
			if (move_prisoner(directions.get(turn), graph.vertexlist, nlignes, ncols, end)) 
				return 'Y'; // le prisonnier a une chance d'être pardonné.

			turn++; // Le numéro du prochain tour
		} // while
		return 'N'; // le prisonnier a PAS une chance d'être pardonné.
	}

}
