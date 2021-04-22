package Main;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import agent.Agent;
import agent.Capteur;
import comparateur.ValueComparatorString;
import graphique.Accueil;
import graphique.TableauScores;
import plateau.Plateau;

public class Main {

	public static boolean tourJoueur = false;
	public static Plateau plateau;
	public static int couleurJoueur;
	public static boolean jeuEnCours;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean modeJoueur = false;
		couleurJoueur = -1;
		int nombreAgent = 0;
		int vitesse = 0;

		Accueil accueil = new Accueil();

		do {

			nombreAgent = accueil.getNombreAgent();

			System.out.print("");
		}
		while(nombreAgent < 2 || nombreAgent > 4);
		vitesse = accueil.getTemps();
		modeJoueur = accueil.getModeJoueur();
		if(nombreAgent == 4) {
			modeJoueur = false;
			System.out.println("Impossible de jouer, il y a deja 4 agents.");
		}
		jeuEnCours = true;
		//		int nombreAgent = 0;

		//		Scanner sc = new Scanner(System.in);
		//		do {
		//			System.out.print("Combien d'agents souhaitez vous faire jouer ? (2 à 4) : ");
		//			nombreAgent = sc.nextInt();
		//		}
		//		while(nombreAgent < 2 || nombreAgent > 4);

		plateau = new Plateau();
		plateau.afficherPlateau();

		List<Agent> agents = new ArrayList<>();
		List<Capteur> capteurs = new ArrayList<>();

		for(int i = 0; i < nombreAgent; i++) {
			agents.add(new Agent());
			capteurs.add(new Capteur(plateau));
		}

		if(modeJoueur) {
			couleurJoueur = agents.get(agents.size()-1).getCouleur()+1;
		}
		System.out.println("couleur du joueur : "+couleurJoueur);

		while(jeuEnCours) {

			if(modeJoueur) {
				tourJoueur = true;
				System.out.println("Tour du joueur");
				while(plateau.checkFinPartieJoueur()) {
					System.out.print("");
					if(!tourJoueur) {
						System.out.println("Plus au joueur");
						break;
					}
				}
			}

			for(int i = 0; i < nombreAgent; i++) {
				try {
					Thread.sleep(vitesse);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Tour de l'agent "+agents.get(i).getCouleur());

				capteurs.get(i).setPlateau(plateau);
				agents.get(i).setCapteur(capteurs.get(i));
				agents.get(i).jouer();
				jeuEnCours = agents.get(i).isEstVivant();
				if(!jeuEnCours) {
					plateau.calculerScore();
					break;
				}
				plateau.afficherPlateau();

			}
		}
		System.out.println("\nFin de la partie, voici le classement :\n");
		afficherScore(plateau.calculerScore());
	}

	public static void afficherScore(Map<String,Integer> scores) {
		ValueComparatorString comparateur = new ValueComparatorString(scores);
		TreeMap<String, Integer> classement = new TreeMap<String,Integer>(comparateur);
		classement.putAll(scores);
		scores = classement;
		for (Map.Entry m : classement.entrySet()) {
			System.out.println("Agent "+m.getKey()+" : "+m.getValue());
		}

		TableauScores tableauScores = new TableauScores(scores);
	}

	public static void jouerJoueur(int[] pos) {
		
		if(plateau.placementPossibleJoueur(couleurJoueur, pos)) {
			System.out.println("Placement de la bille du joueur");
			plateau.placerBille(couleurJoueur, pos);
			plateau.afficherPlateau();
			tourJoueur = false;
		}
		else {
			System.out.println("Placement incorrecte, reessayez");
		}


	}
	
	public static void checkFinDePartie() {
		jeuEnCours = plateau.checkFinPartieJoueur();
	}

}


