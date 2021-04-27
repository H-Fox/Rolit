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

	/**
	 * Place la bille sur le plateau et capture les billes concernees.
	 *
	 * @result Plateau mis a jour avec la bille placee et les billes capturees.
	 */
	public void placerBille(int couleur) {
		plateau.getGrille()[celluleCiblee.getPosition()[0]][celluleCiblee.getPosition()[1]].setEtat(couleur);
		capturer(couleur);

	}

	/**
	 * Determine les billes capturees et change leur etat pour leur donner leur nouvelle couleur.
	 *
	 * @result Plateau mis a jour avec les billes capturees.
	 */
	public void capturer(int couleur) {
		int compteur = 0;

		int[] position = celluleCiblee.getPosition();
		
		Cellule[][] grille = plateau.getGrille();

		for(int i = 0; i < grille[position[0]][position[1]].getCellulesAdjacentes().size(); i++) {
			List<Cellule> listTemp = new ArrayList<>();
			Cellule temp = null;
			if(grille[position[0]][position[1]].getCellulesAdjacentes().get(i) != null
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != couleur 
					&& grille[position[0]][position[1]].getCellulesAdjacentes().get(i).getEtat() != EtatsCase.VIDE) {

				listTemp.add(grille[position[0]][position[1]].getCellulesAdjacentes().get(i));
				temp = grille[position[0]][position[1]].getCellulesAdjacentes().get(i);
				while(temp.getEtat() != couleur) {
					temp = temp.getCellulesAdjacentes().get(i);
					if(temp == null || temp.getEtat() == EtatsCase.VIDE) {
						listTemp.clear();
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

}
