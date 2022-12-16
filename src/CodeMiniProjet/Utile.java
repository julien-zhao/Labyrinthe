package CodeMiniProjet;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Utile {
	static Scanner sc = new Scanner(System.in);
	
	//On recupere un entier
	public static int lireEntier(Scanner sc, String message) {
		boolean lecture = false;
		int res = 0;
		while(!lecture) {
			try {
				System.out.println(message);
				res = sc.nextInt();
				lecture = true;
			}catch(InputMismatchException e) {
				System.out.println("Il faut taper un entier");
				sc.nextLine();
			}
		}
		return res;
	}
	
	public static String lireString(Scanner sc, String message) {
		boolean lecture = false;
		String res = null;
		while(!lecture) {
			try {
				System.out.println(message);
				res = sc.nextLine();
				lecture = true;
			}catch(InputMismatchException e) {
				System.out.println("Il faut taper un string");
				sc.nextLine();
			}
		}
		return res;
	}
	
}
