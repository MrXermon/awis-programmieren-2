package mypack;

import java.util.ArrayList;

public class KonkreterVermittler extends Vermittler {

	private ArrayList<Kollege> kollegen;

	public KonkreterVermittler() {
		super();
		this.kollegen = new ArrayList<>();
	}

	public void vermitteln(Zuord z) {

		ArrayList<ArrayList<Integer>> matrix = new ArrayList<>();

		/**
		 * Erstellung der Praeferenzmatrix
		 */
		for (Kollege i : this.kollegen) {
			ArrayList<Integer> zeile = new ArrayList<Integer>();
			for (Kollege j : this.kollegen) {

				zeile.add(((KonkreterStudi) i).nutzen(j));

			}
			matrix.add(zeile);
		}

		System.out.println("Praeferenzmatrix: " + matrix);

		/**
		 * Bildung der Paare
		 */
		ArrayList<Integer> verwendet = new ArrayList<>();
		for (int i = 0; i < matrix.size() / 2; i++) {
			int max = 0;
			int max_j = 0;
			for (int j = 0; j < matrix.get(i).size(); j++) {
				if (matrix.get(i).get(j) >= max && j != i && !verwendet.contains(j)) {
					max = matrix.get(i).get(j);
					max_j = j;
				}
			}
			verwendet.add(max_j);
			verwendet.add(i);

			z.add(this.kollegen.get(max_j), this.kollegen.get(i));
			z.add(this.kollegen.get(i), this.kollegen.get(max_j));

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
				 * Damit der Kollege nicbt mit sch selbst zusammengebracht wird.
				 */
				if (!k.equals(i)) {

					gesamtNutzen = ((KonkreterStudi) i).nutzen(k) + ((KonkreterStudi) k).nutzen(i);

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

		} else {
			System.err.println("Bereits zugeordnet!");
		}
	}

	public void add(Kollege k) {
		if (!this.find(k))
			this.kollegen.add(k);
	}

	public void rem(Kollege k) {
		if (this.find(k))
			this.kollegen.remove(k);
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
}
