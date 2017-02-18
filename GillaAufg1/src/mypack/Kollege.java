package mypack;

//Datei: Kollege.java
public abstract class Kollege {
	// Instanzvariable
	private Vermittler vermittler; // Referenz auf den Vermittler
	// Konstruktor

	public Kollege(Vermittler v) {
		vermittler = v;
	}

	// Wird von den ableitenden Klassen ueberschrieben
	public void aenderung() {
		vermittler.aenderungAufgetreten(this); // Vermittler informiert
	}
}
