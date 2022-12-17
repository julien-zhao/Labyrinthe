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

	//dans le labyrinthe.c, la fonction retourne un entier, ici la méthode retourne un arraylist qui contient les dirrections possibles
	static ArrayList<Character> can_move(Graph graph, int start, int end, int ncols, int numberV) {
		ArrayList<Vertex> listVertex = Algo.AStar(graph, start, end, ncols, numberV);// plus cours listVertex
		ArrayList<Character> listDirections = new ArrayList<Character>();

		for (int i = 0; i < (listVertex.size() - 1); i++) {
			int mouvement = listVertex.get(i + 1).num - listVertex.get(i).num;
			if (mouvement == 1)
				listDirections.add('R'); // 'R' = right
			else if (mouvement == -1)
				listDirections.add('L'); // 'L' = left
			else if (mouvement == ncols)
				listDirections.add('B'); // 'B' = bottom
			else if (mouvement == (-1 * ncols))
				listDirections.add('T'); // 'T' = top
			else
				return listDirections;
		}
		return listDirections;
	}
	//retourne true si gameover
	static boolean burn_around(int vertex_num, ArrayList<Vertex> vertexList, int nlignes, int ncols) {
		int tmpJ = vertexList.get(vertex_num).j;
		int tmpI = vertexList.get(vertex_num).i;
		//S = sortie, D = depart
		if (tmpJ != 0) {
			if (vertexList.get(vertex_num - 1).position == '.') 
				vertexList.get(vertex_num - 1).position = 'A'; 
			else if (vertexList.get(vertex_num - 1).position == 'S' || vertexList.get(vertex_num - 1).position == 'D') 
				return true; 
		}

		if (tmpJ != (ncols - 1)) {
			if (vertexList.get(vertex_num + 1).position == '.') 
				vertexList.get(vertex_num + 1).position = 'A';
			else if (vertexList.get(vertex_num + 1).position == 'S' || vertexList.get(vertex_num + 1).position == 'D')
				return true; 
		} 

		if (tmpI != 0) { 
			if (vertexList.get(vertex_num - ncols).position == '.')
				vertexList.get(vertex_num - ncols).position = 'A'; 
			else if (vertexList.get(vertex_num - ncols).position == 'S' || vertexList.get(vertex_num - ncols).position == 'D')
				return true; 
		} 

		if (tmpI != (nlignes - 1)) { 
			if (vertexList.get(vertex_num + ncols).position == '.') 
				vertexList.get(vertex_num + ncols).position = 'A'; 
			else if (vertexList.get(vertex_num + ncols).position == 'S' || vertexList.get(vertex_num + ncols).position == 'D') 
				return true; 
		} 
		return false; 
	} 
	//si le prochain move c'est 'S', retourne true
	static boolean win_move(int debut, ArrayList<Vertex> vertexList, int nlignes, int ncols) {
		//S : la sortie
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
		int debut = 0; 

		// localisation du prisonnier
		for (int i = 0; i < vertexList.size(); i++) {
			if (vertexList.get(i).position == 'D')
				debut = i;
		} 

		boolean win = win_move(debut, vertexList, nlignes, ncols);

		if (win)
			return true; //prisonier pardonné
		else {
			vertexList.get(debut).position = 'L'; 

			if (directionMouvementPossiblePourCeTour == 'B') 
				vertexList.get(debut + ncols).position = 'D'; 
			else if (directionMouvementPossiblePourCeTour == 'T')
				vertexList.get(debut - ncols).position = 'D';
			else if (directionMouvementPossiblePourCeTour == 'L')
				vertexList.get(debut - 1).position = 'D';
			else if (directionMouvementPossiblePourCeTour == 'R')
				vertexList.get(debut + 1).position = 'D';
		}
		return false;
	}

	//a chaque tour, dès que on on a true, le prosinier est pardorné
	static char run_instance(Graph graph, int start, int end, int nlignes, int ncols) {
		int turn = 0; // Le numéro du premier tour est 0.

		// list de dirrection du prisonnier
		ArrayList<Character> listDirections = can_move(graph, start, end, ncols, nlignes * ncols);
		while (turn < listDirections.size()) {
			for (int i = 0; i < graph.vertexlist.size(); i++) {
				if (graph.vertexlist.get(i).position == 'A') 
					graph.vertexlist.get(i).position = 'F'; // le feu se propage autour de lui
			} 
			for (int i = 0; i < graph.vertexlist.size(); i++) {
				if (graph.vertexlist.get(i).position == 'F') { 
					if (burn_around(i, graph.vertexlist, nlignes, ncols)) 
						return 'N'; //le feu bloque la sortie donc gameonver
				} 
			} 
			//si true, prisonnier win
			if (move_prisoner(listDirections.get(turn), graph.vertexlist, nlignes, ncols, end)) 
				return 'Y'; 
			turn++;// next tour
		}
		return 'N';
	}
}
