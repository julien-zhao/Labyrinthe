package TP9;

import java.util.PriorityQueue;

public class PriorityQueueTest {
	public static void main(String[] args) {
		// Creation d'une file de priorité avec une capacité initiale de 5.
		PriorityQueue<Integer> fileDePriorite = new PriorityQueue<Integer>(5);
		// Ajout d'un nouvel élément une file de priorité.
		fileDePriorite.add(10);
		fileDePriorite.add(1);
		fileDePriorite.add(100);
		fileDePriorite.add(200);
		fileDePriorite.add(20);
		// Extraction du min
		System.out.println("min = " + fileDePriorite.poll());
		

		// Extraction du max
		Integer element = null, elementPrecedent = null;
		while ((element = fileDePriorite.poll()) != null)
			elementPrecedent = element;
		System.out.println("max = " + elementPrecedent);
	}
}
