package plateau;

import java.util.ArrayList;
import java.util.List;

import constantes.EtatsCase;
import constantes.PositionsAdjacentes;
import graphique.PlateauGraphique;

public class Plateau {
	
	public static final int dimension = 8;
	Cellule [][] grille;
	
	PlateauGraphique affichage;
	
	public Plateau() {
		grille = new Cellule[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				grille[i][j] = new Cellule(i, j , EtatsCase.VIDE);
			}
		}
		affichage = new PlateauGraphique();
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
	
	public void placerBille(int couleur, int[] position) {
			grille[position[0]][position[1]].setEtat(couleur);
			capturer(couleur, position);
				
	}
	
	public boolean placementPossible(int couleur, int[] position) {
//		System.out.println("Test placement : "+position[0]+", "+position[1]+", couleur : "+couleur);
		for(int i = 0; i < grille[position[0]][position[1]].getCellulesAdjacentes().size(); i++) {
//			System.out.println("Debut du for");
			Cellule temp = null;
			//Test a surveiller
			if(grille[position[0]][position[1]].getCellulesAdjacentes().get(i) != null
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != couleur 
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != EtatsCase.VIDE) {
//				System.out.println("Case potentielle : "+i);
				
				temp = grille[position[0]][position[1]].getCellulesAdjacentes().get(i);
				
				while(temp.getEtat() != couleur) {
					int x = temp.getPosition()[0] +1;
					int y = temp.getPosition()[1] +1;
//					System.out.println("Case evaluee : "+x+", "+y);
					if(temp == null || temp.getEtat() == EtatsCase.VIDE) {
						break;
					}
					temp = temp.getCellulesAdjacentes().get(i);
					if(temp.getEtat() == couleur) {
						return true;
					}
				}
//				return true;
			}
		}
		return false;
	}
	
	public void capturer(int couleur, int[] position) {
		for(int i = 0; i < grille[position[0]][position[1]].getCellulesAdjacentes().size(); i++) {
			List<Cellule> listTemp = new ArrayList<>();
			Cellule temp = null;
			//Test a surveiller
			if(grille[position[0]][position[1]].getCellulesAdjacentes().get(i) != null
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != couleur 
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != EtatsCase.VIDE) {
				
				listTemp.add(grille[position[0]][position[1]].getCellulesAdjacentes().get(i));
				temp = grille[position[0]][position[1]].getCellulesAdjacentes().get(i);
				
				while(temp.getCellulesAdjacentes().get(i).getEtat() != couleur) {
					if(temp == null || temp.getEtat() == EtatsCase.VIDE) {
						listTemp.clear();
						break;
					}
					temp = temp.getCellulesAdjacentes().get(i);
					listTemp.add(temp);
				}
				for(Cellule cellule : listTemp) {
					cellule.setEtat(couleur);
				}
			}
		}
	}
	
	public void afficherPlateau() {
		affichage.afficherGraphiquement(this);
		int temp = -1;
		System.out.print("    ");
		for(int k = 0; k < dimension; k++) {
			temp = k+1;
			System.out.print(temp+" ");
		}
		System.out.println();
		System.out.print("   ");
		for(int k = 0; k < dimension; k++) {
			temp = k+1;
			System.out.print("--");
		}
		System.out.println();
		
		for(int i = 0; i < dimension; i++) {
			temp = i+1;
			System.out.print(temp+" | ");
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
