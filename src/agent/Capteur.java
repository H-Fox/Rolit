package agent;

import constantes.EtatsCase;
import plateau.*;

public class Capteur {

	boolean partieEnCours = true;

	Plateau plateau;

	public Capteur(Plateau _plateau) {
		plateau = _plateau;
	}

	public void capter(Plateau _plateau) {
		plateau = _plateau;
	}

	public void checkFinPartie() {
		boolean temp = false;
		for(int i = 0; i < Plateau.dimension; i++) {
			for(int j = 0; j < Plateau.dimension; j++) {
				if(plateau.getGrille()[i][j].getEtat() == EtatsCase.VIDE) {
					temp = true;
					break;
				}
			}
		}
		if(!temp) {
			partieEnCours = false;
		}
	}
	
	public Plateau getPlateau() {
		return plateau;
	}

	public void setPlateau(Plateau plateau) {
		this.plateau = plateau;
	}

	public boolean isPartieEnCours() {
		return partieEnCours;
	}

	public void setPartieEnCours(boolean partieEnCours) {
		this.partieEnCours = partieEnCours;
	}

}
