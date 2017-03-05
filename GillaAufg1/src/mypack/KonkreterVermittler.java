package mypack;

import java.util.ArrayList;
import java.util.ListIterator;

public class KonkreterVermittler extends Vermittler {

	private ArrayList<Kollege> kollegen;

	public KonkreterVermittler() {
		super();
		this.kollegen = new ArrayList<>();
	}

	public void add(Kollege k) {
		if (!this.find(k))
			this.kollegen.add(k);
	}

	public Boolean find(Kollege k) {
		if (this.kollegen.indexOf(k) > -1)
			return true;
		else
			return false;
	}

	public void print() {
		System.out.print("Ausgabe registrierter Studierende: [");
		for (int i = 0; i < this.kollegen.size(); i++) {
			System.out.print(this.kollegen.get(i));
			if (i != (this.kollegen.size() - 1))
				System.out.print(", ");
		}
		System.out.println("]");
	}

	public void rem(Kollege k) {
		if (this.find(k))
			this.kollegen.remove(k);
	}

	public void vermitteln(Zuord z) {
		z.reset();

		/**
		 * Erstellung der Praeferenzmatrix
		 */
		ArrayList<ArrayList<Integer>> praeferenzMatrix = new ArrayList<>();
		for (Kollege i : this.kollegen) {
			ArrayList<Integer> zeile = new ArrayList<>();
			for (Kollege j : this.kollegen) {
				zeile.add(((KonkreterStudi) i).nutzen((KonkreterStudi) j));
			}
			praeferenzMatrix.add(zeile);
		}

		System.out.println("Praeferenzmatrix: " + praeferenzMatrix);

		for (Kollege k : this.kollegen) {
			if (!z.find(k)) {
				int gesamtNutzen = 0;
				int gesamtNutzenMax = 0;
				Kollege gesamtNutzenMaxObj = null;

				/**
				 * Einmal alle Kollegen mit dem gegebenen vergleichen und mit
				 * dem besten Partner zusammenbringen.
				 */
				for (Kollege i : this.kollegen) {
					/**
					 * Damit der Kollege nicbt mit sich selbst zusammengebracht
					 * wird. Der Nutzen mit sich selbt waere 0, sodass die
					 * Ueberpruefung eigentlich nicht notwendig waere.
					 */
					if (!k.equals(i) && !z.find(i)) {
						gesamtNutzen = ((KonkreterStudi) i).nutzen(k) + ((KonkreterStudi) k).nutzen(i);
						/**
						 * Wenn der Gesamtnutzen besser ist, dann setze den
						 * aktuellen Wert als Besten.
						 */
						if (gesamtNutzen > gesamtNutzenMax) {
							gesamtNutzenMax = gesamtNutzen;
							gesamtNutzenMaxObj = i;
						}
					}
				}

				/**
				 * Wenn das Traumpaar gefunden wurde in die Tabelle übernehmen.
				 */
				if (!gesamtNutzenMaxObj.equals(null)) {
					z.add(k, gesamtNutzenMaxObj);
					z.add(gesamtNutzenMaxObj, k);

					((KonkreterStudi) k).setPartner(gesamtNutzenMaxObj);
					((KonkreterStudi) gesamtNutzenMaxObj).setPartner(k);
				}

			}
		}

	}

	public void vermitteln(Zuord z, Kollege k) {
		if (!z.find(k)) {
			int gesamtNutzen = 0;
			int gesamtNutzenMax = 0;
			Kollege gesamtNutzenMaxObj = null;

			System.out.println("KonkreterVermittler berechnet Zuteilung");

			/**
			 * Einmal alle Kollegen mit dem gegebenen vergleichen und mit dem
			 * besten Partner zusammenbringen.
			 */
			for (Kollege i : this.kollegen) {
				/**
				 * Damit der Kollege nicbt mit sich selbst zusammengebracht
				 * wird. Der Nutzen mit sich selbt waere 0, sodass die
				 * Ueberpruefung eigentlich nicht notwendig waere.
				 */
				if (!k.equals(i) && !z.find(i)) {
					gesamtNutzen = ((KonkreterStudi) i).nutzen(k) + ((KonkreterStudi) k).nutzen(i);
					/**
					 * Wenn der Gesamtnutzen besser ist, dann setze den
					 * aktuellen Wert als Besten.
					 */
					if (gesamtNutzen > gesamtNutzenMax) {
						gesamtNutzenMax = gesamtNutzen;
						gesamtNutzenMaxObj = i;
					}
				}
			}

			/**
			 * Wenn das Traumpaar gefunden wurde in die Tabelle übernehmen.
			 */
			if (!gesamtNutzenMaxObj.equals(null)) {
				z.add(k, gesamtNutzenMaxObj);
				z.add(gesamtNutzenMaxObj, k);

				((KonkreterStudi) k).setPartner(gesamtNutzenMaxObj);
				((KonkreterStudi) gesamtNutzenMaxObj).setPartner(k);
			} else {
				System.out.println("Keine Zuordnung mit dem Nutzen > 0 gefunden.");
			}

		} else {
			System.out.println("Bereits zugeordnet!");
		}
	}

	/**
	 * Kopierte und Angepasste Methode zur Rueckgabe aller moeglichen
	 * Permutationen der Liste der Kollegen.
	 * 
	 * Quelle: http://www.programcreek.com/2013/02/leetcode-permutations-java/
	 * 
	 * @param num
	 * @return
	 */
	public ArrayList<ArrayList<Kollege>> kollegenPermutation() {
		ArrayList<Kollege> num = this.kollegen;
		ArrayList<ArrayList<Kollege>> result = new ArrayList<ArrayList<Kollege>>();

		// start from an empty list
		result.add(new ArrayList<Kollege>());

		for (int i = 0; i < num.size(); i++) {
			// list of list in current iteration of the array num
			ArrayList<ArrayList<Kollege>> current = new ArrayList<ArrayList<Kollege>>();

			for (ArrayList<Kollege> l : result) {
				// # of locations to insert is largest index + 1
				for (int j = 0; j < l.size() + 1; j++) {
					// + add num[i] to different locations
					l.add(j, num.get(i));

					ArrayList<Kollege> temp = new ArrayList<Kollege>(l);
					current.add(temp);

					// System.out.println(temp);

					// - remove num[i] add
					l.remove(j);
				}
			}

			result = new ArrayList<ArrayList<Kollege>>(current);
		}

		return result;
	}
}
