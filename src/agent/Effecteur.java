package agent;

import java.util.ArrayList;
import java.util.List;

import constantes.EtatsCase;
import plateau.*;

public class Effecteur {

	Cellule celluleCiblee;
	Plateau plateau;

	public Effecteur(Cellule _celluleCiblee, Plateau _plateau) {
		celluleCiblee = _celluleCiblee;
		plateau = _plateau;
	}

	public void placerBille(int couleur) {
		plateau.getGrille()[celluleCiblee.getPosition()[0]][celluleCiblee.getPosition()[1]].setEtat(couleur);
		capturer(couleur);

	}

	public void capturer(int couleur) {
		int compteur = 0;

		int[] position = celluleCiblee.getPosition();
		
		Cellule[][] grille = plateau.getGrille();

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
//		System.out.println("Nombre de bille(s) capturee(s) : "+compteur);

	}

}
