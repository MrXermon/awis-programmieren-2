/**
 * Jan Gilla
 * 11.04.2017
 * V1.0
 * 
 * Klasser zur Abbildung des Modells des Nonogramms.
 */

package mypack;

import java.util.Random;

public class Modell {

	private String fehlerText;
	private Status[][] feldStatus;
	private int MAX;
	private int[][] zeileVorgabe;
	private int[][] spalteVorgabe;

	/**
	 * Konstruktor zum Anlegen eines Nonogramm-Modells.
	 * 
	 * @param m
	 *            Groesse des Nonogramm-Modells.
	 */
	public Modell(int m) {
		this.MAX = m;
		this.feldStatus = new Status[this.MAX][this.MAX];

		/**
		 * Feld leer Initialisieren
		 */
		for (int x = 0; x < this.MAX; x++) {
			for (int y = 0; y < this.MAX; y++)
				this.feldStatus[x][y] = Status.LEER;
		}

		this.spalteVorgabe = new int[m][m];
		this.zeileVorgabe = new int[m][m];
		this.fehlerText = "";
	}

	/**
	 * Methode zum Setzen der Beispieldaten.
	 */
	public void feldBeispiel() {
		/**
		 * Zeilenvorgaben
		 */
		this.zeileVorgabe[0][0] = 1;
		this.zeileVorgabe[1][0] = 2;
		this.zeileVorgabe[2][0] = 2;
		this.zeileVorgabe[2][1] = 1;
		this.zeileVorgabe[3][0] = 1;

		/**
		 * Spaltenvorgaben
		 */
		this.spalteVorgabe[0][0] = 1;
		this.spalteVorgabe[0][1] = 1;
		this.spalteVorgabe[1][0] = 3;
		this.spalteVorgabe[2][0] = 1;
		this.spalteVorgabe[3][0] = 1;
	}

	/**
	 * Methode zum Erstellen einer Kopie des Feldes Status.
	 * 
	 * @return StatusfeldStatus
	 */
	private Status[][] feldKopie() {
		Status[][] tmpFeld = new Status[this.MAX][this.MAX];
		for (int x = 0; x < this.MAX; x++) {
			for (int y = 0; y < this.MAX; y++) {
				tmpFeld[x][y] = this.feldStatus[x][y];
			}
		}
		return tmpFeld;
	}

	public void feldZufall() {
		Random r = new Random(1);
		Status[][] tempFeld = new Status[this.MAX][this.MAX];

		/**
		 * Zufaellige Belegung der Felder erfinden und dann daraus die vorgaben
		 * generieren.
		 */
		for (int i = 0; i < this.MAX; i++) {
			for (int j = 0; j < this.MAX; j++) {
				int status = r.nextInt(2);
				if (status == 0)
					tempFeld[i][j] = Status.SCHWARZ;
				else
					tempFeld[i][j] = Status.LEER;
			}
		}

		// this.feldStatus = tempFeld;

		/**
		 * Fuer jede Zeile die Muster erkennen und in die Vorgaben-Liste
		 * uebernehmen.
		 */
		for (int i = 0; i < this.MAX; i++) {

			int j = 0;
			Status letzter = null;
			int start = 0;
			Boolean musterAktiv = false;
			int musterZaehler = 0;

			/**
			 * Ueber Zei
			 */
			while (j < this.MAX) {
				if ((letzter == null || letzter == Status.LEER) && tempFeld[j][i] == Status.SCHWARZ) {
					start = j;
					musterAktiv = true;
				}

				if (letzter == Status.SCHWARZ && tempFeld[j][i] == Status.LEER) {
					musterAktiv = false;
					this.zeileVorgabe[i][musterZaehler++] = (j - start);
				}

				letzter = tempFeld[j][i];
				j++;
			}

			/**
			 * Ende des letzten Musters wird nicht erkannt, deshalb getrennt
			 * betrachten.
			 */
			if (musterAktiv) {
				this.zeileVorgabe[i][musterZaehler++] = (j - start);
			}
		}

		/**
		 * Fuer jede Spalte die Muster erkennen und in die Vorgaben-Liste
		 * uebernehmen.
		 */
		for (int i = 0; i < this.MAX; i++) {

			int j = 0;
			Status letzter = null;
			int start = 0;
			Boolean musterAktiv = false;
			int musterZaehler = 0;

			/**
			 * Ueber Zei
			 */
			while (j < this.MAX) {
				if ((letzter == null || letzter == Status.LEER) && tempFeld[i][j] == Status.SCHWARZ) {
					start = j;
					musterAktiv = true;
				}

				if (letzter == Status.SCHWARZ && tempFeld[i][j] == Status.LEER) {
					musterAktiv = false;
					this.spalteVorgabe[i][musterZaehler++] = (j - start);
				}

				letzter = tempFeld[i][j];
				j++;
			}

			/**
			 * Ende des letzten Musters wird nicht erkannt, deshalb getrennt
			 * betrachten.
			 */
			if (musterAktiv) {
				this.spalteVorgabe[i][musterZaehler++] = (j - start);
			}
		}

	}

	/**
	 * Methode zur Rueckgabe des Fehlertextes nach dem Ueberpruefen der Loesung.
	 * 
	 * @return Fehlertext
	 */
	public String getFehlerText() {
		return this.fehlerText;
	}

	public Status getStatus(int x, int y) {
		return this.feldStatus[x][y];
	}

	/**
	 * Generierung der Spalten- und Zeilentitel.
	 * 
	 * @param t
	 *            Umschalten Zwischen Spaltentitel (0) und Zeilentitel (1).
	 * @param xy
	 *            Nummer der Spalte / Zeile.
	 * @return HTML-Text zur Weiterverwendung.
	 */
	public String getTitelFeld(int t, int xy) {
		String r = "<html><body>";
		if (t == 0) {
			/**
			 * Erstellen des Spaltentitels.
			 * 
			 * Hier wird noch ein <br />
			 * am Ende zu viel ausgegeben.
			 */
			for (int i = 0; i < this.zeileVorgabe[xy].length; i++) {
				if (this.zeileVorgabe[xy][i] > 0)
					r += this.zeileVorgabe[xy][i] + "<br />";
			}
		} else {
			/**
			 * Erstellen des Zeilentitels.
			 */
			for (int i = 0; i < this.spalteVorgabe[xy].length; i++) {
				if (this.spalteVorgabe[xy][i] > 0)
					r += this.spalteVorgabe[xy][i] + "  ";
			}
			r = r.trim();
		}
		r += "</body></html>";
		return r;
	}

	/**
	 * Methode zur Ueberpruefung der vom Nutzer vorgeschlagenen Loesung. Wenn
	 * alle Vorgaben erfuellt sind, ist die Eingabe des Benutzers valide.
	 * 
	 * @return true wenn richtig, sonst false.
	 */
	public boolean loesungChecken() {
		this.fehlerText = "";
		Boolean status = true;

		/**
		 * StatusfeldStatus in ein neues Feld duplizieren.
		 */
		Status[][] tmpFeld = new Status[this.MAX][this.MAX];

		tmpFeld = this.feldKopie();

		/**
		 * Zeile einzeln kontrollieren.
		 */
		int i = 0;
		while (i < this.MAX && status) {
			int j = 0;

			/**
			 * Pruefen ob die Anzahl an vorgegeben Kaestchen in der Zeile
			 * angekreuzt ist.
			 */
			if (!this.zeileValide(i))
				status = false;

			/**
			 * Ueber alle Vorgaben fuer die einzelne Zeile laufen.
			 */
			while (j < this.MAX && this.spalteVorgabe[i][j] > 0 && status) {
				int musterSuchen = this.zeileSuchen(i, this.spalteVorgabe[i][j]);
				/**
				 * Pruefen ob Muster in der Zeile vorhanden ist.
				 */
				if (musterSuchen > -1) {
					for (int k = musterSuchen; k < this.MAX; k++) {
						tmpFeld[i][k] = null;
					}
				} else {
					status = false;
				}
				j++;
			}

			if (status == false)
				this.fehlerText += "Fehler in Zeile " + i + "\n";

			i++;
		}

		tmpFeld = this.feldKopie();

		/**
		 * Spalten einzeln kontrollieren.
		 */
		i = 0;
		while (i < this.MAX && status) {
			int j = 0;

			/**
			 * Pruefen ob die Anzahl an vorgegeben Kaestchen in der Spalte
			 * angekreuzt ist.
			 */
			if (!this.spalteValide(i))
				status = false;

			/**
			 * Ueber alle Vorgaben fuer die einzelne Spalte laufen.
			 */
			while (j < this.MAX && this.zeileVorgabe[i][j] > 0 && status) {
				int musterSuchen = this.spalteSuchen(i, this.zeileVorgabe[i][j]);
				/**
				 * Pruefen ob Muster in der Spalte vorhanden ist.
				 */
				if (musterSuchen > -1) {
					for (int k = musterSuchen; k < this.MAX; k++) {
						tmpFeld[k][i] = null;
					}
				} else {
					status = false;
				}
				j++;
			}

			if (status == false)
				this.fehlerText += "Fehler in Spalte " + i + "\n";

			i++;
		}

		return status;
	}

	public void setStatus(int x, int y, Status s) {
		this.feldStatus[x][y] = s;
	}

	/**
	 * Methode zum Suchen der Anzahl der schwarzen Felder in einer Spalte.
	 * 
	 * @param spalte
	 *            Spalte in der gesucht werden soll.
	 * @param suchwert
	 *            Hauefigkeit nach der gesucht werden soll.
	 * @return Stelle an der das erste Zeichen der Folge gefunden wurde (-1,
	 *         wenn nicht gefunden).
	 */
	private int spalteSuchen(int spalte, int suchwert) {
		int startwert = -1;
		int zaehler = 0;

		Boolean gefunden = false;

		int i = 0;
		/**
		 * Jeden Eintrag einer Spalte durchlaufen.
		 */
		while (i < this.MAX && !gefunden) {
			/**
			 * Wenn das Feld schwarz ist weiterzaehlen oder neu beginnen.
			 */
			if (this.feldStatus[i][spalte] == Status.SCHWARZ) {
				if (startwert > -1) {
					zaehler++;
				} else {
					startwert = i;
					zaehler++;
				}
			} else {
				startwert = -1;
				zaehler = 0;
			}

			/**
			 * Ausbruch aus den Schleifen sobald die gewuenschte Anzahl an
			 * Feldern gefunden ist.
			 */
			if (suchwert == zaehler)
				gefunden = true;

			i++;
		}

		if (gefunden)
			return startwert;
		else
			return -1;
	}

	/**
	 * Pruefen ob die Anzahl der vorgegebenen Felder mit der Anzahl der in der
	 * Spalte markierten uebereinstimmt.
	 * 
	 * @param spalte
	 *            Uebergabe der Spalte, die ueberprueft werden soll.
	 * @return Richtig oder Falsch.
	 */
	private Boolean spalteValide(int spalte) {
		int summe = 0;
		int zaehler = 0;

		int i = 0;
		while (i < this.MAX && this.zeileVorgabe[spalte][i] > 0) {
			summe += this.zeileVorgabe[spalte][i];
			i++;
		}

		for (i = 0; i < this.MAX; i++) {
			if (this.feldStatus[i][spalte] == Status.SCHWARZ)
				zaehler++;
		}

		if (zaehler == summe)
			return true;
		else
			return false;
	}

	/**
	 * Ueberschreibung der toString Methode zur Ausgabe der aktuellen Felddaten
	 * in der Konsole.
	 * 
	 * @return Felddaten in Textform.
	 */
	@Override
	public String toString() {
		String ret = "";
		for (int x = 0; x < this.MAX; x++) {
			for (int y = 0; y < this.MAX; y++) {
				ret += this.feldStatus[x][y];
				if ((y + 1) != this.MAX)
					ret += " ";
			}
			if ((x + 1) != this.MAX)
				ret += "\r\n";
		}
		return ret;
	}

	/**
	 * Methode zum Suchen der Anzahl der schwarzen Felder in einer Zeile.
	 * 
	 * @param zeile
	 *            Zeile in der gesucht werden soll.
	 * @param suchwert
	 *            Hauefigkeit nach der gesucht werden soll.
	 * @return Stelle an der das erste Zeichen der Folge gefunden wurde (-1,
	 *         wenn nicht gefunden).
	 */
	private int zeileSuchen(int zeile, int suchwert) {
		int startwert = -1;
		int zaehler = 0;

		Boolean gefunden = false;

		int i = 0;
		/**
		 * Jeden Eintrag einer Zeile durchlaufen.
		 */
		while (i < this.MAX && !gefunden) {
			/**
			 * Wenn das Feld schwarz ist weiterzaehlen oder neu beginnen.
			 */
			if (this.feldStatus[zeile][i] == Status.SCHWARZ) {
				if (startwert > -1) {
					zaehler++;
				} else {
					startwert = i;
					zaehler++;
				}
			} else {
				startwert = -1;
				zaehler = 0;
			}

			/**
			 * Ausbruch aus den Schleifen sobald die gewuenschte Anzahl an
			 * Feldern gefunden ist.
			 */
			if (suchwert == zaehler)
				gefunden = true;

			i++;
		}

		if (gefunden)
			return startwert;
		else
			return -1;
	}

	/**
	 * Pruefen ob die Anzahl der vorgegebenen Felder mit der Anzahl der in der
	 * Zeile markierten uebereinstimmt.
	 * 
	 * @param zeile
	 *            Uebergabe der Zeile, die ueberprueft werden soll.
	 * @return Richtig oder Falsch.
	 */
	private Boolean zeileValide(int zeile) {
		int summe = 0;
		int zaehler = 0;

		int i = 0;
		while (i < this.MAX && this.spalteVorgabe[zeile][i] > 0) {
			summe += this.spalteVorgabe[zeile][i];
			i++;
		}

		for (i = 0; i < this.MAX; i++) {
			if (this.feldStatus[zeile][i] == Status.SCHWARZ)
				zaehler++;
		}

		if (zaehler == summe)
			return true;
		else
			return false;
	}

}