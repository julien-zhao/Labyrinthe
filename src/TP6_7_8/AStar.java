package TP6_7_8;

import java.util.HashSet;
import java.util.LinkedList;

import TP6_7_8.WeightedGraph.Graph;
import TP6_7_8.WeightedGraph.Vertex;

public class AStar {

	/**
	 * La méthode A* (à pronnoncer A star ou A étoile) peut être vu comme une
	 * extension de l’algorithme de Dijkstra. La différence se fait dans le choix du
	 * nœud à explorer. Au lieu de choisir celui ayant la plus petite distance
	 * temporaire, on sélectionne en fonction de la somme entre la distance
	 * temporaire et une heuristique (ici, une distance estimée au nœud d’arriver) à
	 * définir. (Source : Consignes.pdf)
	 * 
	 * Source de certains des commentaires dans le code : Consignes.pdf.
	 * 
	 * @param graph   le graphe représentant la carte.
	 * @param start   un entier représentant la case de départ (entier unique
	 *                correspondant à la case obtenue dans le sens de la lecture).
	 * @param end     un entier représentant la case d'arrivée (entier unique
	 *                correspondant à la case obtenue dans le sens de la lecture).
	 * @param ncols   le nombre de colonnes dans la carte.
	 * @param numberV le nombre de cases dans la carte.
	 * @param board   l'affichage.
	 * @return une liste d'entiers correspondant au chemin.
	 */
	public static LinkedList<Integer> AlgoAStar(Graph graph, int start, int end, int ncols, int numberV, Board board) {
		graph.vertexlist.get(start).timeFromSource = 0; 
		int number_tries = 0; // Nombre de nœuds explorés.

		// TODO: mettre tous les noeuds du graphe dans la liste des noeuds à visiter:
		HashSet<Integer> to_visit = new HashSet<Integer>(); 
		for (Vertex vertex : graph.vertexlist) // pour chaque vertex du graphe, on l'ajoute dans l'hashmap
			to_visit.add(vertex.num);

		// TODO: Remplir l'attribut graph.vertexlist.get(v).heuristic pour tous les
		// noeuds v du graphe:
		// heuristic : une distance estimée au nœud d’arriver.
		int i = 0;
		for (Vertex vertex : graph.vertexlist) {
			vertex.heuristic = distance(i % ncols, i / ncols, end % ncols, end / ncols);
			i++;
		}

		while (to_visit.contains(end)) {
			// TODO: trouver le noeud min_v parmis tous les noeuds v ayant la distance
			// temporaire
			// (graph.vertexlist.get(v).timeFromSource + heuristic) minimale.
			int min_v = 0; // on cree un int pour pouvoir stocké le min
			double timeFromSourceHeuristicMinimale = Double.POSITIVE_INFINITY;
			for (Integer vertexNum : to_visit) { // pour chaque vertex
				if ((graph.vertexlist.get(vertexNum).timeFromSource //si timefromsource + heuristic inferieur ou égal a timeFromSourceHeuristicMinimal
						+ graph.vertexlist.get(vertexNum).heuristic) <= timeFromSourceHeuristicMinimale) {
				//si on trouve un min, on le stock et on modifie timeFromSourceHeuristicMinimal pour trouver le minimal du graphe
					min_v = vertexNum; // on remplace
					timeFromSourceHeuristicMinimale = graph.vertexlist.get(vertexNum).heuristic + graph.vertexlist.get(vertexNum).timeFromSource; 
						
				}
			}

			// On l'enlève des noeuds à visiter
			to_visit.remove(min_v);
			number_tries += 1; // Nombre de nœuds explorés.

			// TODO: pour tous ses voisins, on vérifie si on est plus rapide en passant par
			// ce noeud.
			for (i = 0; i < graph.vertexlist.get(min_v).adjacencylist.size(); i++) {
				if (to_visit.contains(graph.vertexlist.get(min_v).adjacencylist.get(i).destination)) {
					int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
					//on stock un tmp pour comparer, si on trouve plus rapide, on remplace
					double tmp = graph.vertexlist.get(min_v).adjacencylist.get(i).weight + graph.vertexlist.get(min_v).timeFromSource; 

					if (( tmp < (graph.vertexlist.get(to_try).timeFromSource))) {
						graph.vertexlist.get(to_try).timeFromSource = tmp;
						graph.vertexlist.get(to_try).prev = graph.vertexlist.get(min_v); 
					}

				}
			}

			// On met à jour l'affichage
			try {
				board.update(graph, min_v);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				System.out.println("stop");
			}

		}

		System.out.println("Done! Using A*:");
		System.out.println("	Number of nodes explored: " + number_tries);
		System.out.println("	Total time of the path: " + graph.vertexlist.get(end).timeFromSource);
		LinkedList<Integer> path = new LinkedList<Integer>();
		path.addFirst(end);

		// TODO: remplir la liste path avec le chemin
		Vertex noeudParent = graph.vertexlist.get(end).prev;
		while (noeudParent != null) { // si le noeudParent est pas null
			path.addFirst(noeudParent.num);
			noeudParent = graph.vertexlist.get(noeudParent.num).prev;
		}

		board.addPath(graph, path);
		return path;
	}

	private static double distance(int Xa, int Ya, int Xb, int Yb) {
		double resultX = Math.pow((Xb - Xa), 2);
		double resultY = Math.pow((Yb - Ya), 2);

		return (Math.sqrt(resultX + resultY));
	}
}