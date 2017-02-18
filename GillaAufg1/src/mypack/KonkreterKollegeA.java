package mypack;

public class KonkreterKollegeA extends Kollege {
	// Konstruktor
	public KonkreterKollegeA(Vermittler v) {
		super(v);
		System.out.println("KonkreterKollegeA: instanziiert");
	}

	// Wird aufgerufen, wenn sich ein anderer Kollege aendert
	public void methodeA() {
		System.out
				.println("KonkreterKollegeA wird in methodeA() geaendert " + "als Folge der Aenderung eines Kollegen");
	}

	// Neuen Status setzen
	public void aenderung() {
		System.out.println("KonkreterKollegeA wurde geaendert durch Aufruf"
				+ " der Methode aenderung(). KonkreterKollegeA" + " informiert den Vermittler ");
		super.aenderung(); // informiert Vermittler
	}
}