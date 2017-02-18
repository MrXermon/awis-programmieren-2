package mypack;

//Datei: Client.java
public class Client {
	public static void main(String[] args) {
		// Initialisierung
		System.out.println("Initialisierung:");
		KonkreterVermittler konkreterVermittler = new KonkreterVermittler();
		KonkreterKollegeA kollegeA = new KonkreterKollegeA(konkreterVermittler);
		konkreterVermittler.setKollegeA(kollegeA);
		KonkreterKollegeB kollegeB = new KonkreterKollegeB(konkreterVermittler);
		konkreterVermittler.setKollegeB(kollegeB);
		// KollegeA aendern
		System.out.println("\nKollegeA aendern:");
		kollegeA.aenderung();
		// KollegeB aendern
		System.out.println("\nKollegeB aendern:");
		kollegeB.aenderung();
	}
}