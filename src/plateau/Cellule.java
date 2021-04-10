package plateau;

import java.util.ArrayList;
import java.util.List;

public class Cellule {
	
	//boolean valable;
	int [] position;
	int etat;
	List<Cellule> cellulesAdjacentes;


	public Cellule(int i, int j, int _etat) {
		position = new int[2];
		position[0] = i;
		position[1] = j;
		cellulesAdjacentes = new ArrayList<>();
		for(int k = 0; k < 8; k++) {
			cellulesAdjacentes.add(null);
		}
		etat = _etat;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int i, int j) {
		this.position[0] = i;
		this.position[1] = j;
	}
	
//	public boolean isValable() {
//		return valable;
//	}
//
//	public void setValable(boolean valable) {
//		this.valable = valable;
//	}

	public List<Cellule> getCellulesAdjacentes() {
		return cellulesAdjacentes;
	}

	public void setCellulesAdjacentes(List<Cellule> cellulesAdjacentes) {
		this.cellulesAdjacentes = cellulesAdjacentes;
	}
}
