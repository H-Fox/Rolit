package plateau;

import constantes.EtatsCase;
import constantes.PositionsAdjacentes;

public class Plateau {
	
	public static final int dimension = 6;
	Cellule [][] grille;
	
	public Plateau() {
		grille = new Cellule[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				grille[i][j] = new Cellule(i, j , EtatsCase.VIDE);
			}
		}
		initialiserPlateau();
	}
	
	private void initialiserPlateau() {
		//Placement des billes initialement presentes sur le plateau
		grille[dimension/2-1][dimension/2-1].setEtat(EtatsCase.ROUGE);
		grille[dimension/2][dimension/2-1].setEtat(EtatsCase.BLEU);
		grille[dimension/2-1][dimension/2].setEtat(EtatsCase.JAUNE);
		grille[dimension/2][dimension/2].setEtat(EtatsCase.VERT);
		
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				//Test case au dessus valable 
				if(i > 0) {
					grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.HAUT, grille[i-1][j]);
					if(j > 0) {
						grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.HAUT_GAUCHE, grille[i-1][j-1]);
					}
					if(j < dimension - 1) {
						grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.HAUT_DROITE, grille[i-1][j+1]);
					}
				}
				//Test case en dessous valable
				if(i < dimension - 1) {
					grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.BAS, grille[i+1][j]);
					if(j > 0) {
						grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.BAS_GAUCHE, grille[i+1][j-1]);
					}
					if(j < dimension - 1) {
						grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.BAS_DROITE, grille[i+1][j+1]);
					}
				}
				//Test case a gauche valable
				if(j > 0) {
					grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.GAUCHE, grille[i][j-1]);
				}
				//Test case a droite valable
				if(j < dimension - 1) {
					grille[i][j].getCellulesAdjacentes().set(PositionsAdjacentes.DROITE, grille[i][j+1]);
				}
				grille[i][j].getCellulesAdjacentes();
			}
		}
		
	}
	
	public void afficherPlateau() {
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				System.out.print(grille[i][j].getEtat()+" ");
			}
			System.out.println();
		}
		
//		for(int i = 0; i < dimension; i++) {
//			for(int j = 0; j < dimension; j++) {
//				for(int k = 0; k < grille[i][j].getCellulesAdjacentes().size(); k++) {
//					if(grille[i][j].getCellulesAdjacentes().get(k) != null) {
//						System.out.print(grille[i][j].getCellulesAdjacentes().get(k).getEtat()+", ");
//					}
//					else {
//						System.out.print("-1, ");
//					}
//					
//				}System.out.println();
//			}
//		}
	}

	public Cellule[][] getGrille() {
		return grille;
	}

	public void setGrille(Cellule[][] grille) {
		this.grille = grille;
	}
	
	
}