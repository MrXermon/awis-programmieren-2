/**
 * Jan Gilla
 * 09.03.2017
 * V1.0
 * 
 * Klasse zur Abbildung des Studenten.
 */

package mypack;

public class KonkreterStudi extends Kollege {

	/**
	 * Name des Studis
	 */
	private String name;
	/**
	 * Zugewiesener Partner
	 */
	private Kollege partner;
	/**
	 * Informatikskill als Zahlenwert (0-10).
	 */
	private int skillInfo;
	/**
	 * Matheskill als Zahlenwert (0-10).
	 */
	private int skillMathe;

	public KonkreterStudi(Vermittler v, String n, int i, int m) {
		super(v);
		this.name = n;
		this.skillInfo = i;
		this.skillMathe = m;
	}

	/**
	 * Methode die den Vermittler informiert, dass ein KonkreterStudi
	 * aktualisiert wurde.
	 */
	public void aktualisiert() {
	}

	/**
	 * Methode zum Abrufen des Namens,
	 * 
	 * @return Name des KonkretenStudis.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Methode zum Abrufen des zugewiesenen Partners.
	 * 
	 * @return Objekt des Partners.
	 */
	public Kollege getPartner() {
		return partner;
	}

	/**
	 * Methode zum Abrufen des Info-Skills.
	 * 
	 * @return Informatikskill im Bereich 0-10.
	 */
	public int getSkillInfo() {
		return skillInfo;
	}

	/**
	 * Methode zum Abrufen des Mathe-Skills.
	 * 
	 * @return Matheskill im Bereich 0-10.
	 */
	public int getSkillMathe() {
		return skillMathe;
	}

	/**
	 * Berechnung des Nutzens des Studis mit dem übergebenen Kollegen.
	 * 
	 * @param k
	 *            Vergleichskollege
	 * @return Gesamtnutzen (Mathe + Info)
	 */
	public int nutzen(Kollege k) {
		int nutzen = 0;
		int mathe = ((KonkreterStudi) k).getSkillMathe() - ((KonkreterStudi) this).getSkillMathe();
		int info = ((KonkreterStudi) k).getSkillInfo() - ((KonkreterStudi) this).getSkillInfo();

		if (info < 0)
			info = 0;

		if (mathe < 0)
			mathe = 0;

		nutzen = mathe + info;
		return nutzen;
	}

	/**
	 * Methode zum Aktualisieren des Partners.
	 * 
	 * @param partner
	 *            Uebergabe des Partners
	 * @param b
	 *            Moeglichkeit die Ausgabe zu unterdruecken
	 * 
	 */
	public void setPartner(Kollege partner, Boolean b) {
		this.partner = partner;
		if (!b)
			System.out.println("KonkreterStudi ist informiert, zugeteilter Partner von " + this.getName() + " ist: "
					+ ((KonkreterStudi) this.getPartner()).getName());
		this.aktualisiert();
	}

	/**
	 * Methode zum Setzen des Partners. Der alte Partner wird ungefragt
	 * ueberschrieben.
	 * 
	 * @param partner
	 *            Neuer Partner.
	 */
	public void setPartner(Kollege partner) {
		this.setPartner(partner, false);
	}

	/**
	 * Metode zur angepassten Ausgabe der Objekte.
	 */
	public String toString() {
		return "[name=" + this.name + ", fitnessInf=" + this.skillInfo + ", fitnessMathe=" + this.skillMathe + "]";
	}

	/**
	 * Methode zur Zuteilung des aktuellen Studenten ueber den Vermittler.
	 * 
	 * @param z
	 *            Objekt in dem die Zuordnung gespeichert werden soll.
	 */
	public void vermitteln(Zuord z) {
		System.out.println(
				"KonkreterStudi " + this.getName() + " moechte zugeteilt werden und informiert den Vermittler");
		this.getVermittler().vermitteln(z, this);
	}

}
