import java.util.Scanner;

import plateau.Plateau;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Plateau plateau = new Plateau();
		plateau.afficherPlateau();
		
		while(true) {
			for(int i = 1; i<3; i++) {
				int col = -1;
				int lig = -1;
				int[] pos = new int[2];
				pos[0] = lig;
				pos[1] = col;
				System.out.println("Au tour du joueur "+i);				
				Scanner sc = new Scanner(System.in);
				boolean place = false;
				do {
					place = false;
					do{
						System.out.print("Colonne: ");
						col = sc.nextInt();
					}
					while(col < 1 || col > 8);				
					do{
						System.out.print("Ligne: ");
						lig = sc.nextInt();
					}
					while(lig < 1 || lig > 8);					
					pos[0] = lig-1;
					pos[1] = col-1;
					if(plateau.placementPossible(i, pos)) {
						System.out.println("Placement possible");
						plateau.placerBille(i, pos);
						plateau.afficherPlateau();
						place = true;
					}
					else {
						System.out.println("Placement impossible");
					}
				}
				while(!place);
								
			}
		}
	}

}
