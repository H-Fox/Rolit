package plateau;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constantes.EtatsCase;
import constantes.PositionsAdjacentes;
import graphique.PlateauGraphique;

public class Plateau {

	public static int noSerie = 0;

	public int numero = -1;
	public static final int dimension = 8;
	Cellule [][] grille;

	PlateauGraphique affichage;

	public Plateau() {
		numero = noSerie++;
		grille = new Cellule[dimension][dimension];
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				grille[i][j] = new Cellule(i, j , EtatsCase.VIDE);
			}
		}
		affichage = new PlateauGraphique(this);
		initialiserPlateau();
	}

	private void initialiserPlateau() {
		//Placement des billes initialement presentes sur le plateau
		grille[dimension/2-1][dimension/2-1].setEtat(EtatsCase.JAUNE);
		grille[dimension/2][dimension/2-1].setEtat(EtatsCase.VERT);
		grille[dimension/2-1][dimension/2].setEtat(EtatsCase.BLEU);
		grille[dimension/2][dimension/2].setEtat(EtatsCase.ROUGE);

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

	public Map<String,Integer> calculerScore() {
		int rouge = 0, 
				bleu = 0, 
				vert = 0, 
				jaune = 0;
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				switch(grille[i][j].getEtat()){
				case EtatsCase.BLEU:
					bleu++;
					break;
				case EtatsCase.JAUNE:
					jaune++;
					break;
				case EtatsCase.VERT:
					vert++;
					break;
				case EtatsCase.ROUGE:
					rouge++;
					break;
				}
			}
		}
		Map<String,Integer> scores = new HashMap<>();

		if(bleu != 0) {
			scores.put("Bleu", bleu);
		}
		if(jaune != 0) {
			scores.put("Jaune", jaune);
		}
		if(vert != 0) {
			scores.put("Vert", vert);
		}
		if(rouge != 0) {
			scores.put("Rouge", rouge);
		}	

		return scores;
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
	}
	//Methode pour le joueur
	public Map<Boolean, List<Cellule>> determinerJouabilite(int couleur) {
		Map<Boolean, List<Cellule>> resultat = new HashMap<>();

		List<Cellule> cellulesJouablesCapture = new ArrayList<>();
		List<Cellule> cellulesJouablesPasCapture = new ArrayList<>();
		
		//Regle 1 : Trouver les cases permettant de capturer des billes adverses
		for(int i = 0; i < grille.length; i++) {
			for(int j = 0; j < grille.length; j++) {
				//Parcours des cases du plateau
				int[] pos = new int[2];
				pos[0] = i;
				pos[1] = j;
				if(placementPossible(couleur, pos) 
						&& grille[pos[0]][pos[1]].getEtat() == EtatsCase.VIDE) {
					//Application des regles de placement
					System.out.println("Placement possible : "+i+", "+j);
					cellulesJouablesCapture.add(grille[i][j]);
				}
			}
		}
		//Regle 2 : Si pas de capture possible, choisir cases sans capturer
		for(int i = 0; i < grille.length; i++) {
			for(int j = 0; j < grille.length; j++) {
				//Parcours des cases du plateau
				boolean adjancenceNonVide = false;
				int[] pos = new int[2];
				pos[0] = i;
				pos[1] = j;
				for(Cellule cellule : grille[i][j].getCellulesAdjacentes()) {
					if(cellule != null && cellule.getEtat() != EtatsCase.VIDE) {
						adjancenceNonVide = true;
						break;
					}
				}
				if(grille[pos[0]][pos[1]].getEtat() == EtatsCase.VIDE
						&& adjancenceNonVide) {
					//					System.out.println("Placement possible : "+i+", "+j);
					cellulesJouablesPasCapture.add(grille[i][j]);
				}
			}
		}
		
		for(Cellule cellule : cellulesJouablesCapture) {
			System.out.println("case : "+cellule.position[0]+", "+cellule.position[1]);
		}

		if(cellulesJouablesCapture.size() > 0) {
			resultat.put(true, cellulesJouablesCapture);
			for(Cellule cell : resultat.get(true)) {
				System.out.println("case bis : "+cell.position[0]+", "+cell.position[1]);
			}
			return resultat;
		}
		else {
			resultat.put(false, cellulesJouablesPasCapture);
			return resultat;
		}


	}

	public boolean placementPossible(int couleur, int[] position) {

			for(int i = 0; i < grille[position[0]][position[1]].getCellulesAdjacentes().size(); i++) {
				//			System.out.println("Debut du for");
				Cellule temp = null;

				if(grille[position[0]][position[1]].getCellulesAdjacentes().get(i) != null
						&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != couleur 
						&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != EtatsCase.VIDE) {
					//				System.out.println("Case potentielle : "+i);

					temp = grille[position[0]][position[1]].getCellulesAdjacentes().get(i);

					while(temp.getEtat() != couleur) {
						int x = temp.getPosition()[0] +1;
						int y = temp.getPosition()[1] +1;
						//					System.out.println("Case evaluee : "+x+", "+y);					
						temp = temp.getCellulesAdjacentes().get(i);
						if(temp == null || temp.getEtat() == EtatsCase.VIDE) {
							break;
						}
						if(temp.getEtat() == couleur) {
							return true;
						}
					}
					//				return true;
				}
			}
			return false;
		
		
		//		System.out.println("Test placement : "+position[0]+", "+position[1]+", couleur : "+couleur);	


	}
	
	public boolean placementPossibleJoueur(int couleur, int[] position) {
		Map<Boolean, List<Cellule>> resultat = determinerJouabilite(couleur);
		
		if(resultat.get(true) != null && resultat.get(true).size() > 0) {
			System.out.println("Cases jouables pour capturer");
			for(Cellule cell : determinerJouabilite(couleur).get(true)) {
				System.out.println(cell.position[0]+", "+cell.position[1]);
				System.out.println(position[0]+", "+position[1]);
				if(cell.getPosition()[0] == position[0] && cell.getPosition()[1] == position[1]) {
					System.out.println("Placement possible");
					return true;
				}
				
			}
		}
		else {
			System.out.println("Pas de cases jouables pour capturer");
			for(Cellule cell : determinerJouabilite(couleur).get(false)) {
				if(cell.getPosition()[0] == position[0] && cell.getPosition()[1] == position[1]) {
					System.out.println("Placement possible");
					return true;
				}
				
			}
		}
		return false;
	}

	public void placerBille(int couleur, int[] pos) {
		grille[pos[0]][pos[1]].setEtat(couleur);
		capturerJoueur(couleur, pos);

	}

	public void capturerJoueur(int couleur, int[] position) {
		int compteur = 0;

		//		System.out.println("Position ciblee : "+position[0]+", "+position[1]);
		for(int i = 0; i < grille[position[0]][position[1]].getCellulesAdjacentes().size(); i++) {
			List<Cellule> listTemp = new ArrayList<>();
			Cellule temp = null;
			//Test a surveiller
			if(grille[position[0]][position[1]].getCellulesAdjacentes().get(i) != null
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != couleur 
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != EtatsCase.VIDE) {

				listTemp.add(grille[position[0]][position[1]].getCellulesAdjacentes().get(i));
				temp = grille[position[0]][position[1]].getCellulesAdjacentes().get(i);
				while(temp.getEtat() != couleur) {
					temp = temp.getCellulesAdjacentes().get(i);
					if(temp == null || temp.getEtat() == EtatsCase.VIDE) {
						listTemp.clear();
						//						System.out.println("Liste vide");
						break;
					}
					else if(temp.getEtat() != couleur){
						listTemp.add(temp);						
					}
				}
				compteur += listTemp.size();

				for(Cellule cellule : listTemp) {
					cellule.setEtat(couleur);
				}
			}
		}

	}

	public boolean checkFinPartieJoueur() {
		boolean partieEnCours = true;
		boolean temp = false;
		for(int i = 0; i < Plateau.dimension; i++) {
			for(int j = 0; j < Plateau.dimension; j++) {
				if(grille[i][j].getEtat() == EtatsCase.VIDE) {
					temp = true;
					break;
				}
			}
		}
		if(!temp) {
			partieEnCours = false;
		}
		return partieEnCours;
	}


	public Cellule[][] getGrille() {
		return grille;
	}

	public void setGrille(Cellule[][] grille) {
		this.grille = grille;
	}



}
