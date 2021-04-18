package agent;

import plateau.*;

public class Effecteur {
	
	int priorite;
	Cellule celluleCiblee;
	
	public Effecteur(int _priorite, Cellule _celluleCiblee) {
		priorite = _priorite;
		celluleCiblee = _celluleCiblee;
	}
	
}
