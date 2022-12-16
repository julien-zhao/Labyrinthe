package CodeMiniProjet;

import java.util.LinkedList;
import java.util.ArrayList;

public class WeightedGraph {

	static class Edge {
		int source;
		int destination;
		double weight;

		public Edge(int source, int destination, double weight) {
			this.source = source;
			this.destination = destination;
			this.weight = weight;
		}
	}

	static class Vertex {
		double indivTime;
		double timeFromSource;
		double heuristic;
		Vertex prev;
		LinkedList<Edge> adjacencylist;
		int num;
		char position;
		int i;
		int j;

		public Vertex(int num, char position, int i, int j) {
			this.indivTime = Double.POSITIVE_INFINITY;
			this.timeFromSource = Double.POSITIVE_INFINITY;
			this.heuristic = -1;
			this.prev = null;
			this.adjacencylist = new LinkedList<Edge>();
			this.num = num;
			this.position = position;
			this.i = i;
			this.j = j;
		}

		public void addNeighbor(Edge e) {
			this.adjacencylist.addFirst(e);
		}
	}

	static class Graph {
		ArrayList<Vertex> vertexlist;
		int num_v = 0;

		Graph() {
			vertexlist = new ArrayList<Vertex>();
		}

		public void addVertex(char position, double indivTime, int i, int j) {
			Vertex v = new Vertex(num_v, position, i, j);
			v.indivTime = indivTime;
			vertexlist.add(v);
			num_v = num_v + 1;
		}

		public void addEgde(int source, int destination, double weight) {
			Edge edge = new Edge(source, destination, weight);
			vertexlist.get(source).addNeighbor(edge);
		}
	}
}