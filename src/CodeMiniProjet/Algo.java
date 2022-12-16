package CodeMiniProjet;
import java.util.ArrayList;
import java.util.HashSet;

import CodeMiniProjet.WeightedGraph.Graph;
import CodeMiniProjet.WeightedGraph.Vertex;


public class Algo {


	public static ArrayList<Vertex> AStar(Graph graph, int start, int end, int ncols, int numberV)
	{
		ArrayList<Vertex> path = new ArrayList<Vertex>();

		graph.vertexlist.get(start).timeFromSource = 0; 
		
		// Mettre tous les noeuds du graphe dans la liste des noeuds à visiter :
		HashSet<Integer> to_visit = new HashSet<Integer>();
		for(Vertex vertex : graph.vertexlist)
			to_visit.add( vertex.num );
		
		// Remplir l'attribut graph.vertexlist.get(v).heuristic pour tous les noeuds v du graphe:
		//  heuristic : une distance estimée au nœud d’arriver.
		int i = 0;
		for(Vertex vertex : graph.vertexlist) {
			// distance(int Xa, int Ya, int Xb, int Yb)
			vertex.heuristic = 	distance(i % ncols, i / ncols, end % ncols, end / ncols);
			i++;
		}
		
		while (to_visit.contains(end))
		{
			// Trouver le noeud min_v parmis tous les noeuds v ayant la distance temporaire
			//      (graph.vertexlist.get(v).timeFromSource + heuristic) minimale.
			int min_v = 0;
			double timeFromSourceHeuristicMinimale = Double.POSITIVE_INFINITY;
			for(Integer vertexNum : to_visit ) {
				if( ( graph.vertexlist.get(vertexNum).timeFromSource + graph.vertexlist.get(vertexNum).heuristic ) <= timeFromSourceHeuristicMinimale ) {
					min_v = vertexNum;
					timeFromSourceHeuristicMinimale = graph.vertexlist.get(vertexNum).timeFromSource + graph.vertexlist.get(vertexNum).heuristic;
				} // if
			} // for			
			
		    // Ajouter à la liste des nœuds de l'itinéraire.
			path.add( graph.vertexlist.get(min_v) );
			
			// On l'enlève des noeuds à visiter
			to_visit.remove(min_v); 
			
			// Pour tous ses voisins, on vérifie si on est plus rapide en passant par ce noeud.
			for (i = 0 ; i < graph.vertexlist.get(min_v).adjacencylist.size() ; i++) {
				if( to_visit.contains(graph.vertexlist.get(min_v).adjacencylist.get(i).destination)) {
					int to_try = graph.vertexlist.get(min_v).adjacencylist.get(i).destination;
					boolean can_move = Labyrinthe.can_i_move(to_try, graph.vertexlist, numberV); // true si le prisonnier peut se déplacer vers la position vertex_num.
																
					if(can_move) { // true si le prisonnier peut se déplacer vers la position vertex_num.
						if( (  ( graph.vertexlist.get(min_v).timeFromSource + graph.vertexlist.get(min_v).adjacencylist.get(i).weight )  < (graph.vertexlist.get(to_try).timeFromSource) ) ) { // si la distance en passant par le nœud courant (donc distance temporaire plus distance du nœud courant au voisin) est plus petite que la distance temporaire
							graph.vertexlist.get(to_try).timeFromSource = ( graph.vertexlist.get(min_v).timeFromSource + graph.vertexlist.get(min_v).adjacencylist.get(i).weight ); // on mets à jour la distance temporaire
							graph.vertexlist.get(to_try).prev =  graph.vertexlist.get(min_v); // on enregistre le nœud courant comme nœud parent du voisin
						} 
					}
				} 
			} 
		} 
		return path; // Path : Une liste de sommets correspondant au chemin.
	} 
	
	
	static double distance(int Xa, int Ya, int Xb, int Yb) {
		double resultX = Math.pow( (Xb-Xa) , 2);
		double resultY = Math.pow( (Yb-Ya) , 2);
		return ( Math.sqrt(resultX + resultY) );
   }

	
}