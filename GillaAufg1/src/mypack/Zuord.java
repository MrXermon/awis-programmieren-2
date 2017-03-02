package mypack;

import java.util.HashMap;

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

}
