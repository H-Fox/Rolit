package agent;

import java.util.ArrayList;
import java.util.List;

import constantes.EtatsCase;
import plateau.*;

public class Agent {

	boolean estVivant = true;

	Capteur capteur = null;
	Effecteur effecteur = null;

	Plateau plateau = null;

	List<List<Integer>> casesVeryStrong;
	List<List<Integer>> casesStrong;
	
	List<Cellule> cellulesJouablesCapture;
	List<Cellule> cellulesJouablesPasCapture;
	
	List<Effecteur> Prio1VeryStrongCapture;
	List<Effecteur> Prio2StrongCapture;
	List<Effecteur> Prio3Capture;
	List<Effecteur> Prio4PositionnementVeryStrong;
	List<Effecteur> Prio5PositionnementStrong;
	List<Effecteur> Prio6PositionnementRandom;

	int couleur;

	static int compteurCouleur = 1;

	public Agent() {
		couleur = compteurCouleur;
		compteurCouleur++;
		cellulesJouablesCapture = new ArrayList<>();
		cellulesJouablesPasCapture = new ArrayList<>();
		
		casesVeryStrong = new ArrayList<>();
		casesStrong = new ArrayList<>();
		initialiserCasesStrategiques();
		//Au debut, faire une Map<Cellule,Integer> avec cellule cible et boules capturees
		//Trier cette map et recouper avec VeryStrong, puis si necessaire Strong
		//Si prio 3 atteinte, map deja triee
		//Pour les 3 dernieres, juste a checker si cases vides et jouables (adjacence non vide)
		Prio1VeryStrongCapture = new ArrayList<>();
		Prio2StrongCapture = new ArrayList<>();
		Prio3Capture = new ArrayList<>();
		Prio4PositionnementVeryStrong = new ArrayList<>();
		Prio5PositionnementStrong = new ArrayList<>();
		Prio6PositionnementRandom = new ArrayList<>();
	}
	
	public void initialiserCasesStrategiques() {
		List<Integer> temp = new ArrayList<>();
		temp.add(0);
		temp.add(0);
		
		casesVeryStrong.add(temp);
		temp.clear();
		temp.add(0);
		temp.add(7);
		casesVeryStrong.add(temp);
		temp.clear();
		temp.add(7);
		temp.add(0);
		casesVeryStrong.add(temp);
		temp.clear();
		temp.add(7);
		temp.add(7);
		casesVeryStrong.add(temp);
		
		temp.clear();
		temp.add(0);
		temp.add(2);
		casesStrong.add(temp);
		
		temp.clear();
		temp.add(0);
		temp.add(5);
		casesStrong.add(temp);
		
		temp.clear();
		temp.add(2);
		temp.add(0);
		casesStrong.add(temp);
		
		temp.clear();
		temp.add(2);
		temp.add(5);
		casesStrong.add(temp);
		
		temp.clear();
		temp.add(7);
		temp.add(2);
		casesStrong.add(temp);
		
		temp.clear();
		temp.add(7);
		temp.add(5);
		casesStrong.add(temp);
		
		temp.clear();
		temp.add(2);
		temp.add(7);
		casesStrong.add(temp);
		
		temp.clear();
		temp.add(5);
		temp.add(7);
		casesStrong.add(temp);
		
		for(int i = 2; i < 6; i++) {
			for(int j = 2; j < 6; j++) {
				temp.clear();
				temp.add(i);
				temp.add(j);
				casesStrong.add(temp);
			}
		}
		
	}

	public void determinerCasesJouables() {
		cellulesJouablesCapture = new ArrayList<>();
		//Regle 1 : Trouver les cases permettant de capturer des billes adverses
		for(int i = 0; i < capteur.getPlateau().getGrille().length; i++) {
			for(int j = 0; j < capteur.getPlateau().getGrille().length; j++) {
				//Parcours des cases du plateau
				int[] pos = new int[2];
				pos[0] = i;
				pos[1] = j;
				if(placementPossible(couleur, pos) 
						&& capteur.getPlateau().getGrille()[pos[0]][pos[1]].getEtat() == EtatsCase.VIDE) {
					//Application des regles de placement
					System.out.println("Placement possible : "+i+", "+j);
					cellulesJouablesCapture.add(capteur.getPlateau().getGrille()[i][j]);
				}
			}
		}
		//Regle 2 : Si pas de capture possible, poser une bille sans capturer a la position la plus strategique
		for(int i = 0; i < capteur.getPlateau().getGrille().length; i++) {
			for(int j = 0; j < capteur.getPlateau().getGrille().length; j++) {
				//Parcours des cases du plateau
				boolean adjancenceNonVide = false;
				int[] pos = new int[2];
				pos[0] = i;
				pos[1] = j;
				for(Cellule cellule : capteur.getPlateau().getGrille()[i][j].getCellulesAdjacentes()) {
					if(cellule != null && cellule.getEtat() != EtatsCase.VIDE) {
						adjancenceNonVide = true;
						break;
					}
				}
				if(capteur.getPlateau().getGrille()[pos[0]][pos[1]].getEtat() == EtatsCase.VIDE
						&& adjancenceNonVide) {
					System.out.println("Placement possible : "+i+", "+j);
					cellulesJouablesCapture.add(capteur.getPlateau().getGrille()[i][j]);
				}
			}
		}


	}

	public void choisirCaseSansConditionDePlacement() {
		for(int i = 0; i < capteur.getPlateau().getGrille().length; i++) {
			for(int j = 0; j < capteur.getPlateau().getGrille().length; j++) {
				int[] pos = new int[2];
				pos[0] = i;
				pos[1] = j;
				if(capteur.getPlateau().getGrille()[pos[0]][pos[1]].getEtat() == EtatsCase.VIDE) {
					System.out.println("Placement possible : "+i+", "+j);
					cellulesJouablesCapture.add(capteur.getPlateau().getGrille()[i][j]);
				}
			}
		}
	}

	public void choisirAction() {
		//Prio 1: Capturer
		determinerCasesJouables();
		//Prio 2 : Jouer sans capturer
		if(cellulesJouablesCapture.size() == 0) {
			choisirCaseSansConditionDePlacement();
		}

		effecteur = new Effecteur(1, cellulesJouablesCapture.get(0));
		System.out.println(cellulesJouablesCapture.get(0).getPosition()[0]+", "+cellulesJouablesCapture.get(0).getPosition()[1]);
	}

	public void agir() {
		capteur.checkFinPartie();
		this.estVivant = capteur.isPartieEnCours();
		if(this.estVivant) {
			choisirAction();
			capteur.getPlateau().placerBille(couleur, effecteur.celluleCiblee.getPosition());
		}
	}


	public boolean placementPossible(int couleur, int[] position) {
		//		System.out.println("Test placement : "+position[0]+", "+position[1]+", couleur : "+couleur);
		for(int i = 0; i < capteur.getPlateau().getGrille()[position[0]][position[1]].getCellulesAdjacentes().size(); i++) {
			//			System.out.println("Debut du for");
			Cellule temp = null;

			if(capteur.getPlateau().getGrille()[position[0]][position[1]].getCellulesAdjacentes().get(i) != null
					&& capteur.getPlateau().getGrille()[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != couleur 
					&& capteur.getPlateau().getGrille()[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != EtatsCase.VIDE) {
				//				System.out.println("Case potentielle : "+i);

				temp = capteur.getPlateau().getGrille()[position[0]][position[1]].getCellulesAdjacentes().get(i);

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
	}




	public Capteur getCapteur() {
		return capteur;
	}

	public void setCapteur(Capteur capteur) {
		this.capteur = capteur;
		plateau = capteur.getPlateau();
	}

	public Effecteur getEffecteur() {
		return effecteur;
	}

	public void setEffecteur(Effecteur effecteur) {
		this.effecteur = effecteur;
	}

	public List<Cellule> getCellulesJouablesCapture() {
		return cellulesJouablesCapture;
	}

	public void setCellulesJouablesCapture(List<Cellule> cellulesJouablesCapture) {
		this.cellulesJouablesCapture = cellulesJouablesCapture;
	}

	public int getCouleur() {
		return couleur;
	}

	public void setCouleur(int couleur) {
		this.couleur = couleur;
	}

	public boolean isEstVivant() {
		return estVivant;
	}

	public void setEstVivant(boolean estVivant) {
		this.estVivant = estVivant;
	}

	public List<Cellule> getCellulesJouablesPasCapture() {
		return cellulesJouablesPasCapture;
	}

	public void setCellulesJouablesPasCapture(List<Cellule> cellulesJouablesPasCapture) {
		this.cellulesJouablesPasCapture = cellulesJouablesPasCapture;
	}





}
