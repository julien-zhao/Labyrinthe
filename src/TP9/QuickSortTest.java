package TP9;

import java.util.Arrays;

public class QuickSortTest {
	public static void main(String[] args) {
		// Creation d'un tableau.
		int[] tab = {100 , 1 , 10};
		// on fait un Tri rapidesur le tableau .
		Arrays.sort( tab );
		// Extraction du min
		System.out.println( "min = " + tab[0] );
		// Extraction du max
		System.out.println( "max = " + tab[tab.length - 1] );
		}
		
}
