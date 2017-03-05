package mypack;

import java.util.ArrayList;
import java.util.HashMap;

public class Zuord {

	private HashMap<Kollege, Kollege> zuordnungen;

	public Zuord() {
		this.zuordnungen = new HashMap<>();
	}

	public void add(ArrayList<Kollege> l) {
		for (int i = 0; i < l.size(); i += 2) {
			this.add(l.get(i), l.get(i + 1));
			this.add(l.get(i + 1), l.get(i));
		}
	}

	public void add(Kollege a, Kollege b) {
		this.zuordnungen.put(a, b);
	}

	public Boolean find(Kollege a) {
		Boolean gefunden = false;

		if (this.zuordnungen.containsKey(a))
			gefunden = true;

		if (this.zuordnungen.containsValue(a))
			gefunden = true;

		return gefunden;
	}

	public int gesamtnutzen() {
		int nutzen = 0;

		ArrayList<Kollege> keys = new ArrayList<>(this.zuordnungen.keySet());
		ArrayList<Kollege> values = new ArrayList<>(this.zuordnungen.values());
		for (int i = 0; i < keys.size(); i++) {
			nutzen += ((KonkreterStudi) keys.get(i)).nutzen(((KonkreterStudi) values.get(i)));
		}

		return nutzen;
	}

	public Kollege get(Kollege a) {
		if (this.find(a)) {
			return this.zuordnungen.get(a);
		} else {
			return null;
		}
	}

	public HashMap<Kollege, Kollege> getZuordnungen() {
		return zuordnungen;
	}

	public void setZuordnungen(HashMap<Kollege, Kollege> zuordnungen) {
		this.zuordnungen = zuordnungen;
	}

	public void print() {
		System.out.println(this.zuordnungen);
	}

	public void print(Vermittler vv) {
		ArrayList<Kollege> ausgegeben = new ArrayList<>();
		KonkreterVermittler v = (KonkreterVermittler) vv;
		for (Kollege k : v.getKollegen()) {
			if (!ausgegeben.contains(k)) {
				System.out.print(((KonkreterStudi) k).getName() + " zu "
						+ ((KonkreterStudi) this.zuordnungen.get(k)).getName() + "; ");
				ausgegeben.add(k);
				ausgegeben.add(this.zuordnungen.get(k));
			}
			ausgegeben.add(k);
		}
		System.out.println();
	}

	public void reset() {
		this.zuordnungen = new HashMap<>();
	}

}
