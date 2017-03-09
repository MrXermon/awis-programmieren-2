/**
 * Jan Gilla
 * 09.03.2017
 * V1.0
 * 
 * Klasse zur Abbildung der Zuordnungen zwischen verschiedenen Studenten.
 */

package mypack;

import java.util.ArrayList;
import java.util.HashMap;

public class Zuord {

	/**
	 * HashMap zur Speicherung der Zuordnungen.
	 */
	private HashMap<Kollege, Kollege> zuordnungen;

	/**
	 * Standardkonstruktor
	 */
	public Zuord() {
		this.zuordnungen = new HashMap<>();
	}

	/**
	 * Hinzufuegen der Zuordnungen anhand einer Liste von Kollegen. Eins und
	 * Zwei sind ein Paar, Drei und Vier sind ein Paar, ...
	 * 
	 * @param l
	 *            Liste die die Kollegen enthaelt.
	 */
	public void add(ArrayList<Kollege> l) {
		for (int i = 0; i < l.size(); i += 2) {
			this.add(l.get(i), l.get(i + 1));
			this.add(l.get(i + 1), l.get(i));
		}
	}

	/**
	 * Methode zum Hinzufuegen eines neuen Paares,
	 * 
	 * @param a
	 *            Erster Kollege
	 * @param b
	 *            Zweiter Kollege
	 */
	public void add(Kollege a, Kollege b) {
		this.zuordnungen.put(a, b);
	}

	/**
	 * Ueberpruefen ob ein Kollege bereits zugeordnet ist.
	 * 
	 * @param a
	 *            Kollege nach dem gesucht werden soll.
	 * @return Wahr, wenn der Kollege bereits zugeordnet wurde, sonst Unwahr.
	 */
	public Boolean find(Kollege a) {
		Boolean gefunden = false;

		if (this.zuordnungen.containsKey(a))
			gefunden = true;

		if (this.zuordnungen.containsValue(a))
			gefunden = true;

		return gefunden;
	}

	/**
	 * Methode zur Berechnung des Gesamtnutzens anhand der aktuellen
	 * Zuordnungen.
	 * 
	 * @return Gesamtnutzen
	 */
	public int gesamtnutzen() {
		int nutzen = 0;

		ArrayList<Kollege> keys = new ArrayList<>(this.zuordnungen.keySet());
		ArrayList<Kollege> values = new ArrayList<>(this.zuordnungen.values());
		for (int i = 0; i < keys.size(); i++) {
			nutzen += ((KonkreterStudi) keys.get(i)).nutzen(((KonkreterStudi) values.get(i)));
		}

		return nutzen;
	}

	/**
	 * Abrufen des Partners eines Kollegens.
	 * 
	 * @param a
	 *            Kollege, dessen Partner gesiucht ist.
	 * @return Partner
	 */
	public Kollege get(Kollege a) {
		if (this.find(a)) {
			return this.zuordnungen.get(a);
		} else {
			return null;
		}
	}

	/**
	 * Methode die alle Zuordnungen in Form einer HashMap zurueckliefert.
	 * 
	 * @return Zuordnung aller Kollegen in einer HashMap.
	 */
	public HashMap<Kollege, Kollege> getZuordnungen() {
		return zuordnungen;
	}

	/**
	 * Ueberschreiben der aktuellen Zuordnungen.
	 * 
	 * @param zuordnungen
	 *            HashMap die alle Zuordnungen enthaelt.
	 */
	public void setZuordnungen(HashMap<Kollege, Kollege> zuordnungen) {
		this.zuordnungen = zuordnungen;
	}

	/**
	 * Ausgabe aller Zuordnungen.
	 */
	public void print() {
		System.out.println(this.zuordnungen);
	}

	/**
	 * Ausgabe der Zuordnungen im Format "A zu B;".
	 * 
	 * @param vv
	 *            Vermittler, an dem die Kollegen registriert sind,
	 */
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

	/**
	 * Methode zum Zuruecksetzen der Zuordnungen.
	 */
	public void reset() {
		this.zuordnungen = new HashMap<>();
	}

}
