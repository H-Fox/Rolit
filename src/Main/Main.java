package Main;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import agent.Agent;
import agent.Capteur;
import comparateur.ValueComparator;
import graphique.Accueil;
import graphique.TableauScores;
import plateau.Plateau;

public class Main {
	
//	public static int nombreAgent = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int nombreAgent = 0;
		int vitesse = 0;
		
		Accueil accueil = new Accueil();
		
		do {
			
			nombreAgent = accueil.getNombreAgent();
			
			System.out.print("");
		}
		while(nombreAgent < 2 || nombreAgent > 4);
		vitesse = accueil.getTemps();
		boolean jeuEnCours = true;
//		int nombreAgent = 0;
		
//		Scanner sc = new Scanner(System.in);
//		do {
//			System.out.print("Combien d'agents souhaitez vous faire jouer ? (2 � 4) : ");
//			nombreAgent = sc.nextInt();
//		}
//		while(nombreAgent < 2 || nombreAgent > 4);
	
		Plateau plateau = new Plateau();
		plateau.afficherPlateau();
		
		List<Agent> agents = new ArrayList<>();
		List<Capteur> capteurs = new ArrayList<>();
		
		for(int i = 0; i < nombreAgent; i++) {
			agents.add(new Agent());
			capteurs.add(new Capteur(plateau));
		}
		
		
		while(jeuEnCours) {
			
			for(int i = 0; i < nombreAgent; i++) {
				System.out.println("Tour de l'agent "+agents.get(i).getCouleur());
				
				capteurs.get(i).setPlateau(plateau);
				agents.get(i).setCapteur(capteurs.get(i));
				agents.get(i).agir();
				jeuEnCours = agents.get(i).isEstVivant();
				if(!jeuEnCours) {
					plateau.calculerScore();
					break;
				}
				plateau.afficherPlateau();
				try {
					Thread.sleep(vitesse);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("\nFin de la partie, voici le classement :\n");
		afficherScore(plateau.calculerScore());
	}
	
	public static void afficherScore(Map<String,Integer> scores) {
		ValueComparator comparateur = new ValueComparator(scores);
		TreeMap<String, Integer> classement = new TreeMap<String,Integer>(comparateur);
		classement.putAll(scores);
		for (Map.Entry m : classement.entrySet()) {
            System.out.println("Agent "+m.getKey()+" : "+m.getValue());
        }
		TableauScores tableauScores = new TableauScores(scores);
	}

}
