package comparateur;

import agent.*;
import java.util.Comparator;
import java.util.Map;

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
