package comparateur;

import agent.*;
import java.util.Comparator;
import java.util.Map;

/**
 * Methode de tri de Map recuperee sur Internet.
 * 
 * Source : 
 * https://www.journaldunet.fr/web-tech/developpement/1202393-comment-trier-une-map-par-valeurs-en-java/
 */

public class ValueComparatorEffecteur implements Comparator<Effecteur>{

	Map<Effecteur, Integer> base;

	public ValueComparatorEffecteur(Map<Effecteur, Integer> base) {
		this.base = base;
	}

	public int compare(Effecteur a, Effecteur b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}

}
