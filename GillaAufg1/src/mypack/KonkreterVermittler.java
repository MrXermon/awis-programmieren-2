/**
 * Jan Gilla
 * 09.03.2017
 * V1.0
 * 
 * Klasse zur Abbildung des Vermittlers.
 */

package mypack;

import java.util.ArrayList;

public class KonkreterVermittler extends Vermittler {

	/**
	 * Liste die alle am Vermittler registrierten Kollegen enthält.
	 */
	private ArrayList<Kollege> kollegen;

	/**
	 * Konstruktor
	 */
	public KonkreterVermittler() {
		super();
		this.kollegen = new ArrayList<>();
	}

	/**
	 * Kollegen am Vermittler registrieren.
	 * 
	 * @param k
	 *            Kollege der am Vermittler registriert werden soll.
	 */
	public void add(Kollege k) {
		if (!this.find(k))
			this.kollegen.add(k);
	}

	/**
	 * Ueberpruefe ob ein KonkreterStudi bereits zugewiesen ist.
	 */
	public Boolean find(Kollege k) {
		if (this.kollegen.indexOf(k) > -1)
			return true;
		else
			return false;
	}

	/**
	 * Methode zum Abrufen aller am Vermittler registrierten Kollegen.
	 * 
	 * @return Liste aller am Vermittler registrierten Kollegen.
	 */
	public ArrayList<Kollege> getKollegen() {
		return kollegen;
	}

	/**
	 * Kopierte und Angepasste Methode zur Rueckgabe aller moeglichen
	 * Permutationen der Liste der Kollegen.
	 * 
	 * Quelle: http://www.programcreek.com/2013/02/leetcode-permutations-java/
	 * 
	 * @return Liste mit allen Permutationen der Zuordnungen,
	 */
	private ArrayList<ArrayList<Kollege>> kollegenPermutation() {
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
					// - remove num[i] add
					l.remove(j);
				}
			}

			result = new ArrayList<ArrayList<Kollege>>(current);
		}

		return result;
	}

	/**
	 * Ausgabe aller am KonkreterVermittler registrierten KonkreterStudis.
	 */
	public void print() {
		System.out.print("Ausgabe registrierter Studierende: [");
		for (int i = 0; i < this.kollegen.size(); i++) {
			System.out.print(this.kollegen.get(i));
			if (i != (this.kollegen.size() - 1))
				System.out.print(", ");
		}
		System.out.println("]");
	}

	/**
	 * Methode zum zuordnen aller am Vermittler registrierten Kollegen.
	 * 
	 * @param z
	 *            Bojekt in denen alle Zuordnungen abschließend gespeichert
	 *            werden.
	 */
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

		/**
		 * Bis zu 10 Kollegen "lohnt" sich der Aufwand alle Permutationen zu
		 * betrachten.
		 */
		if (this.kollegen.size() < 10) {
			/**
			 * Die Berechnungen der Permutationen koennen mitunter sehr lange
			 * dauern.
			 */
			ArrayList<ArrayList<Kollege>> permutationen = this.kollegenPermutation();
			int besteI = 0;
			int besteNutzen = 0;

			/**
			 * Permutationen als moegliche Zuordnungen in einer Liste ablegen.
			 */
			ArrayList<Zuord> moeglicheZuordnungen = new ArrayList<>();
			for (ArrayList<Kollege> i : permutationen) {
				Zuord tmp = new Zuord();
				tmp.add(i);
				moeglicheZuordnungen.add(tmp);
			}

			/**
			 * Suche die beste Kombination und speichere Sie in die uebergebene
			 * HashMap.
			 */
			for (int i = 0; i < moeglicheZuordnungen.size(); i++) {
				if (moeglicheZuordnungen.get(i).gesamtnutzen() > besteNutzen) {
					besteNutzen = moeglicheZuordnungen.get(i).gesamtnutzen();
					besteI = i;
				}
			}
			z.setZuordnungen(moeglicheZuordnungen.get(besteI).getZuordnungen());

			/**
			 * Zuordnung der Partnet-Objekte untereinander.
			 */
			for (int i = 0; i < this.kollegen.size(); i++) {
				((KonkreterStudi) this.kollegen.get(i)).setPartner(z.get(this.kollegen.get(i)), true);
			}
		} else {
			/**
			 * Tue was auch immer notwendig ist um eine Naeherungsloesing zu
			 * bekommen.
			 */
		}

	}

	/**
	 * Zuordnung eines Kollegen zu dem Kollegen mit dem besten Gesamtnutzen.
	 * 
	 * @param k
	 *            Kollege der Vermittelt werden soll.
	 * @param z
	 *            Zuordnungsobjekt, in dem die Zuordnungen abschließend
	 *            gespeichert werden.
	 */
	public void vermitteln(Zuord z, Kollege k) {
		/**
		 * Sofern der Kollege noch nicht zugeweisen ist, wird er vermittelt.
		 */
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

}
