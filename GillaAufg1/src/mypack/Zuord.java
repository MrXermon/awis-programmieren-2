package mypack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.ListIterator;

public class Zuord {

	private HashMap<Kollege, Kollege> zuordnungen;

	public Zuord() {
		this.zuordnungen = new HashMap<>();
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

	public void print() {
		System.out.println(this.zuordnungen);
	}
	
	public void reset(){
		this.zuordnungen = new HashMap<>();
	}

}
