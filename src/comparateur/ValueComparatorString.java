package comparateur;

import java.util.Comparator;
import java.util.Map;

/**
 * Methode de tri de Map recuperee sur Internet.
 * 
 * Source : 
 * https://www.journaldunet.fr/web-tech/developpement/1202393-comment-trier-une-map-par-valeurs-en-java/
 */

public class ValueComparatorString implements Comparator<String> {
	
	Map<String, Integer> base;
	
	public ValueComparatorString(Map<String, Integer> base) {
		this.base = base;
	}

	public int compare(String a, String b) {
		if (base.get(a) >= base.get(b)) {
			return -1;
		} else {
			return 1;
		}
	}
}