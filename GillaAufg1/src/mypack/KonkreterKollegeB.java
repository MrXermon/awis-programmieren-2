package mypack;

public class KonkreterKollegeB extends Kollege {
	// Konstruktor
	public KonkreterKollegeB(Vermittler v) {
		super(v);
		System.out.println("KonkreterKollegeB: instanziiert");
	}

	// Wird aufgerufen, wenn sich ein anderer Kollege aendert
	public void methodeB() {
		System.out
				.println("KonkreterKollegeB wird in methodeB() geaendert" + " als Folge der Aenderung eines Kollegen");
	}

	// Neuen Status setzen
	public void aenderung() {
		System.out.println("KonkreterKollegeB wurde geaendert durch Aufruf"
				+ " der Methode aenderung(). KonkreterKollegeB" + " informiert den Vermittler ");
		super.aenderung(); // informiert Vermittler
	}
}
