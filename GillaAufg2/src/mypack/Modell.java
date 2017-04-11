/**
 * Jan Gilla
 * 11.04.2017
 * V1.0
 * 
 * Klasser zur Abbildung des Modells des Nonogramms.
 */

package mypack;

public class Modell {

	private int MAX;
	private Status[][] feld;
	private int[][] vorgabeZeile;
	private int[][] vorgabeSpalte;
	private String fehlerText;

	public Modell(int m) {
		this.MAX = m;
		this.feld = new Status[this.MAX][this.MAX];

		/**
		 * Feld leer Initialisieren
		 */
		for (int x = 0; x < this.MAX; x++) {
			for (int y = 0; y < this.MAX; y++)
				this.feld[x][y] = Status.LEER;
		}

		this.vorgabeZeile = new int[m][m];
		this.vorgabeSpalte = new int[m][m];
		this.fehlerText = "";
	}

	/**
	 * Ueberschreibung der toString Methode zur Ausgabe der aktuellen Felddaten
	 * in der Konsole.
	 * 
	 * @return Felddaten in Textform.
	 */
	public String toString() {
		String ret = "";
		for (int x = 0; x < this.MAX; x++) {
			for (int y = 0; y < this.MAX; y++) {
				ret += this.feld[x][y];
				if ((y + 1) != this.MAX)
					ret += " ";
			}
			if ((x + 1) != this.MAX)
				ret += "\r\n";
		}
		return ret;
	}

	public Status getStatus(int x, int y) {
		return this.feld[x][y];
	}

	public void setStatus(int x, int y, Status s) {
		this.feld[x][y] = s;
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
		 * Statusfeld in ein neues Feld duplizieren.
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
			while (j < this.MAX && this.vorgabeZeile[i][j] > 0 && status) {
				int musterSuchen = this.zeileSuchen(i, this.vorgabeZeile[i][j]);
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
			while (j < this.MAX && this.vorgabeSpalte[i][j] > 0 && status) {
				int musterSuchen = this.spalteSuchen(i, this.vorgabeSpalte[i][j]);
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

	/**
	 * Methode zum Erstellen einer Kopie des Feldes Status.
	 * 
	 * @return Statusfeld
	 */
	private Status[][] feldKopie() {
		Status[][] tmpFeld = new Status[this.MAX][this.MAX];
		for (int x = 0; x < this.MAX; x++) {
			for (int y = 0; y < this.MAX; y++) {
				tmpFeld[x][y] = this.feld[x][y];
			}
		}
		return tmpFeld;
	}

	private Boolean zeileValide(int zeile) {
		int summe = 0;
		int zaehler = 0;

		int i = 0;
		while (i < this.MAX && this.vorgabeZeile[zeile][i] > 0) {
			summe += this.vorgabeZeile[zeile][i];
			i++;
		}

		for (i = 0; i < this.MAX; i++) {
			if (this.feld[zeile][i] == Status.SCHWARZ)
				zaehler++;
		}

		if (zaehler == summe)
			return true;
		else
			return false;
	}

	private Boolean spalteValide(int spalte) {
		int summe = 0;
		int zaehler = 0;

		int i = 0;
		while (i < this.MAX && this.vorgabeSpalte[spalte][i] > 0) {
			summe += this.vorgabeSpalte[spalte][i];
			i++;
		}

		for (i = 0; i < this.MAX; i++) {
			if (this.feld[i][spalte] == Status.SCHWARZ)
				zaehler++;
		}

		if (zaehler == summe)
			return true;
		else
			return false;
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
			if (this.feld[zeile][i] == Status.SCHWARZ) {
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
			if (this.feld[i][spalte] == Status.SCHWARZ) {
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
	 * Methode zur Rueckgabe des Fehlertextes nach dem Ueberpruefen der Loesung.
	 * 
	 * @return Fehlertext
	 */
	public String getFehlerText() {
		return this.fehlerText;
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
			for (int i = 0; i < this.vorgabeSpalte[xy].length; i++) {
				if (this.vorgabeSpalte[xy][i] > 0)
					r += this.vorgabeSpalte[xy][i] + "<br />";
			}
		} else {
			/**
			 * Erstellen des Zeilentitels.
			 */
			for (int i = 0; i < this.vorgabeZeile[xy].length; i++) {
				if (this.vorgabeZeile[xy][i] > 0)
					r += this.vorgabeZeile[xy][i] + "  ";
			}
			r = r.trim();
		}
		r += "</body></html>";
		return r;
	}

	/**
	 * Methode zum Setzen der Beispieldaten.
	 */
	public void beispielLaden() {
		/**
		 * Zeilenvorgaben
		 */
		this.vorgabeSpalte[0][0] = 1;
		this.vorgabeSpalte[1][0] = 2;
		this.vorgabeSpalte[2][0] = 2;
		this.vorgabeSpalte[2][1] = 1;
		this.vorgabeSpalte[3][0] = 1;

		/**
		 * Spaltenvorgaben
		 */
		this.vorgabeZeile[0][0] = 1;
		this.vorgabeZeile[0][1] = 1;
		this.vorgabeZeile[1][0] = 3;
		this.vorgabeZeile[2][0] = 1;
		this.vorgabeZeile[3][0] = 1;
	}

}