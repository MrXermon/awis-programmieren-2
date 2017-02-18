package mypack;

public class KonkreterVermittler extends Vermittler {
	// Instanzvariablen
	private KonkreterKollegeA kollegeA;
	private KonkreterKollegeB kollegeB;

	// Konstruktor
	public KonkreterVermittler() {
		System.out.println("KonkreterVermittler: instanziiert");
	}

	// bei Aenderungen ruft der geaenderte Kollege diese
	// Vermittler-Methode auf
	public void aenderungAufgetreten(Kollege k) {
		if (k == (Kollege) kollegeA) {
			System.out.println("KonkreterVermittler: informiere KollegeB");
			kollegeB.methodeB();
		} else if (k == (Kollege) kollegeB) {
			System.out.println("KonkreterVermittler: informiere KollegeA");
			kollegeA.methodeA();
		}
	}

	// Set-Methoden fuer Kollegen
	public void setKollegeA(KonkreterKollegeA kka) {
		kollegeA = kka;
	}

	public void setKollegeB(KonkreterKollegeB kkb) {
		kollegeB = kkb;
	}
}
