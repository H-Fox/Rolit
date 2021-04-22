package agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import comparateur.ValueComparatorEffecteur;
import constantes.EtatsCase;
import plateau.*;

public class Agent {

	boolean estVivant = true;

	Capteur capteur = null;
	Effecteur effecteur = null;

	Plateau plateau = null;

	List<int[]> casesVeryStrong;
	List<int[]> casesStrong;

	List<Cellule> cellulesJouablesCapture;
	List<Cellule> cellulesJouablesPasCapture;

	Map<Effecteur, Integer> Prio1VeryStrongCapture;
	Map<Effecteur, Integer> Prio2StrongCapture;
	Map<Effecteur, Integer> Prio3Capture;
	Map<Effecteur, Integer> Prio4PositionnementVeryStrong;
	Map<Effecteur, Integer> Prio5PositionnementStrong;
	Map<Effecteur, Integer> Prio6PositionnementRandom;

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
		Prio1VeryStrongCapture = new HashMap<>();		
		Prio2StrongCapture = new HashMap<>();
		Prio3Capture = new HashMap<>();
		Prio4PositionnementVeryStrong = new HashMap<>();
		Prio5PositionnementStrong = new HashMap<>();
		Prio6PositionnementRandom = new HashMap<>();
	}

	public void initialiserCasesStrategiques() {
		
		int[] temp = new int[2];
		temp[0] = 0;
		temp[1] = 0;
		casesVeryStrong.add(temp);
		
		temp = new int[2];
		temp[0] = 0;
		temp[1] = 7;
		casesVeryStrong.add(temp);
		
		temp = new int[2];
		temp[0] = 7;
		temp[1] = 0;
		casesVeryStrong.add(temp);
		
		temp = new int[2];
		temp[0] = 7;
		temp[1] = 7;
		casesVeryStrong.add(temp);
		
		
		
		temp = new int[2];
		temp[0] = 0;
		temp[1] = 2;		
		casesStrong.add(temp);
		
		temp = new int[2];
		temp[0] = 0;
		temp[1] = 5;
		casesStrong.add(temp);
		
		temp = new int[2];
		temp[0] = 2;
		temp[1] = 0;
		casesStrong.add(temp);
		
		temp = new int[2];
		temp[0] = 2;
		temp[1] = 5;
		casesStrong.add(temp);
		
		temp = new int[2];
		temp[0] = 7;
		temp[1] = 2;
		casesStrong.add(temp);

		temp = new int[2];
		temp[0] = 7;
		temp[1] = 5;
		casesStrong.add(temp);

		temp = new int[2];
		temp[0] = 2;
		temp[1] = 7;
		casesStrong.add(temp);

		temp = new int[2];
		temp[0] = 5;
		temp[1] = 7;
		casesStrong.add(temp);

		for(int i = 2; i < 6; i++) {
			for(int j = 2; j < 6; j++) {
				temp = new int[2];
				temp[0] = i;
				temp[1] = j;
//				System.out.println("ajout carré strong : "+temp[0]+", "+temp[1]);
				casesStrong.add(temp);
			}
		}
		int i = 0;
		for(int[] pos : casesStrong) {
			i++;
//			System.out.println("casesStrong "+i+" = "+pos[0]+", "+pos[1]);
		}

	}

	public void determinerCasesJouables() {
		cellulesJouablesCapture = new ArrayList<>();
		cellulesJouablesPasCapture = new ArrayList<>();
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
//					System.out.println("Placement possible : "+i+", "+j);
					cellulesJouablesCapture.add(capteur.getPlateau().getGrille()[i][j]);
				}
			}
		}
		//Regle 2 : Si pas de capture possible, choisir cases sans capturer
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
//					System.out.println("Placement possible : "+i+", "+j);
					cellulesJouablesPasCapture.add(capteur.getPlateau().getGrille()[i][j]);
				}
			}
		}


	}

	public void determinerAction() {		
		Prio1VeryStrongCapture = new HashMap<>();
		Prio2StrongCapture = new HashMap<>();
		Prio3Capture = new HashMap<>();
		Prio4PositionnementVeryStrong = new HashMap<>();
		Prio5PositionnementStrong = new HashMap<>();
		Prio6PositionnementRandom = new HashMap<>();

		//Priorite 1:
		//Sélection des cases Very Strong jouables en capturant des billes adverses
		//triee par nombre de captures effectuables.

		for(int[] positionVeryStrong : casesVeryStrong) {
			for(Cellule celluleJouableCapture : cellulesJouablesCapture) {
				if(comparerPosition(positionVeryStrong, celluleJouableCapture.getPosition())) {
					Prio1VeryStrongCapture
					.put(new Effecteur(celluleJouableCapture, capteur.getPlateau())
							, compteurBillesCapturables(couleur, positionVeryStrong));
				}
			}
		}
		ValueComparatorEffecteur comparateur = new ValueComparatorEffecteur(Prio1VeryStrongCapture);
		TreeMap<Effecteur, Integer> classement = new TreeMap<Effecteur,Integer>(comparateur);
		classement.putAll(Prio1VeryStrongCapture);
		Prio1VeryStrongCapture = classement;

		//Priorite 2:
		//Sélection des cases Strong jouables en capturant des billes adverses
		//triee par nombre de captures effectuables.
		for(int[] positionStrong : casesStrong) {
			for(Cellule celluleJouableCapture : cellulesJouablesCapture) {
				if(comparerPosition(positionStrong, celluleJouableCapture.getPosition())) {
					Prio2StrongCapture
					.put(new Effecteur(celluleJouableCapture, capteur.getPlateau())
							, compteurBillesCapturables(couleur, positionStrong));
				}
			}
		}
		comparateur = new ValueComparatorEffecteur(Prio2StrongCapture);
		classement = new TreeMap<Effecteur,Integer>(comparateur);
		classement.putAll(Prio2StrongCapture);
		Prio2StrongCapture = classement;

		//Priorite 3:
		//Sélection des cases jouables permettant le plus de captures.
		for(Cellule celluleJouableCapture : cellulesJouablesCapture) {
			Prio3Capture
			.put(new Effecteur(celluleJouableCapture, capteur.getPlateau())
					, compteurBillesCapturables(couleur, celluleJouableCapture.getPosition()));

		}
		comparateur = new ValueComparatorEffecteur(Prio3Capture);
		classement = new TreeMap<Effecteur,Integer>(comparateur);
		classement.putAll(Prio3Capture);
		Prio3Capture = classement;

		//Priorite 4:
		//Si pas de case jouable pour capturer, on joue sans capturer en position Very Strong.
		for(int[] positionVeryStrong : casesVeryStrong) {
			for(Cellule celluleJouablePasCapture : cellulesJouablesPasCapture) {
				if(comparerPosition(positionVeryStrong, celluleJouablePasCapture.getPosition())) {
					Prio4PositionnementVeryStrong
					.put(new Effecteur(celluleJouablePasCapture, capteur.getPlateau())
							, 0);
				}
			}
		}

		//Priorite 5:
		//Si pas de case jouable pour capturer, on joue sans capturer en position Strong.
		for(int[] positionStrong : casesStrong) {
			for(Cellule celluleJouablePasCapture : cellulesJouablesPasCapture) {
//				System.out.println("Pos Strong : "+positionStrong[0]+", "+positionStrong[1]);
//				System.out.println("Pos Pas capture : "+celluleJouablePasCapture.getPosition()[0]+", "+celluleJouablePasCapture.getPosition()[1]);
				if(comparerPosition(positionStrong, celluleJouablePasCapture.getPosition())) {
//					System.out.println("Prio 5 : "+celluleJouablePasCapture.getPosition()[0]+", "+celluleJouablePasCapture.getPosition()[1]);
					Prio5PositionnementStrong
					.put(new Effecteur(celluleJouablePasCapture, capteur.getPlateau())
							, 0);
				}
			}
		}

		//Priorite 6:
		//Si pas de case jouable pour capturer, et pas de case jouable pour une position stratégique,
		//placement sur les cases jouables restantes.
		for(Cellule celluleJouablePasCapture : cellulesJouablesPasCapture) {
			Prio6PositionnementRandom
			.put(new Effecteur(celluleJouablePasCapture, capteur.getPlateau())
					, 0);

		}


	}
	
	public void choisirAction() {
		Effecteur eff = null;
		
		List<Effecteur> temp = new ArrayList<>();
		
		if(Prio1VeryStrongCapture.size() > 0) {
			int billesCapturees = -1;
			for(Map.Entry entrySet : Prio1VeryStrongCapture.entrySet()) {
				if(billesCapturees == -1) {
					billesCapturees = (int) entrySet.getValue();
				}				
				if((int) entrySet.getValue() != billesCapturees) {
					break;
				}
				temp.add((Effecteur) entrySet.getKey());
			}
			int rand = (int) (Math.random()*temp.size());
			eff = temp.get(rand);
		}
		
		else if(Prio2StrongCapture.size() > 0) {
			int billesCapturees = -1;
			for(Map.Entry entrySet : Prio2StrongCapture.entrySet()) {
				if(billesCapturees == -1) {
					billesCapturees = (int) entrySet.getValue();
				}				
				if((int) entrySet.getValue() != billesCapturees) {
					break;
				}
				temp.add((Effecteur) entrySet.getKey());
			}
			int rand = (int) (Math.random()*temp.size());
			eff = temp.get(rand);
		}
		
		else if(Prio3Capture.size() > 0) {
			int billesCapturees = -1;
			for(Map.Entry entrySet : Prio3Capture.entrySet()) {
				if(billesCapturees == -1) {
					billesCapturees = (int) entrySet.getValue();
				}				
				if((int) entrySet.getValue() != billesCapturees) {
					break;
				}
				temp.add((Effecteur) entrySet.getKey());
			}
			int rand = (int) (Math.random()*temp.size());
			eff = temp.get(rand);
		}
		else if(Prio4PositionnementVeryStrong.size() > 0) {
			for(Map.Entry entrySet : Prio4PositionnementVeryStrong.entrySet()) {
				temp.add((Effecteur) entrySet.getKey());
			}
			int rand = (int) (Math.random()*temp.size());
			eff = temp.get(rand);
		}
		else if(Prio5PositionnementStrong.size() > 0) {
			for(Map.Entry entrySet : Prio5PositionnementStrong.entrySet()) {
				temp.add((Effecteur) entrySet.getKey());
			}
			int rand = (int) (Math.random()*temp.size());
			eff = temp.get(rand);
		}
		else if(Prio6PositionnementRandom.size() > 0) {
			for(Map.Entry entrySet : Prio6PositionnementRandom.entrySet()) {
				temp.add((Effecteur) entrySet.getKey());
			}
			int rand = (int) (Math.random()*temp.size());
			eff = temp.get(rand);
		}
		
		effecteur = eff;
	}

	public void agir() {
		capteur.checkFinPartie();
		this.estVivant = capteur.isPartieEnCours();
		if(this.estVivant) {		
			effecteur.placerBille(couleur);
		}
	}

	public void jouer() {
		determinerCasesJouables();
		determinerAction();
//		System.out.println("Prio 1 : "+Prio1VeryStrongCapture.size());
//		System.out.println("Prio 2 : "+Prio2StrongCapture.size());
//		System.out.println("Prio 3 : "+Prio3Capture.size());
//		System.out.println("Prio 4 : "+Prio4PositionnementVeryStrong.size());
//		System.out.println("Prio 5 : "+Prio5PositionnementStrong.size());
//		System.out.println("Prio 6 : "+Prio6PositionnementRandom.size());
		choisirAction();
//		System.out.println("");
		agir();
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

	public int compteurBillesCapturables(int couleur, int[] position) {
		int compteur = 0;

		Cellule[][] grille = capteur.getPlateau().getGrille();

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
//				System.out.println("Ajout case adjacente : "+temp.getPosition()[0]+", "+temp.getPosition()[1]);
				while(temp.getEtat() != couleur) {
					temp = temp.getCellulesAdjacentes().get(i);
					if(temp == null || temp.getEtat() == EtatsCase.VIDE) {
						listTemp.clear();
//						System.out.println("Liste vide");
						break;
					}
					else if(temp.getEtat() != couleur){
//						System.out.println("Ajout case : "+temp.getPosition()[0]+", "+temp.getPosition()[1]);
						listTemp.add(temp);

					}
				}
				compteur += listTemp.size();
			}
		}
//		System.out.println("Nombre de bille(s) capturee(s) : "+compteur);
		return compteur;
	}

	public boolean comparerPosition(int[] position1, int[] position2) {
		if(position1[0] == position2[0] && position1[1] == position2[1]) {
			return true;
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
