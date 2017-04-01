package mypack;

public class Modell {

	private int MAX;
	private Status[][] feld;
	private int[][] vorgabeZeile;
	private int[][] vorgabeSpalte;

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
	}

	public Modell() {
		this(3);
	}

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
		this.vorgabeSpalte[3][1] = 1;

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