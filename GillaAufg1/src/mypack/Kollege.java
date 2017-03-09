/**
 * Jan Gilla
 * 09.03.2017
 * V1.0
 * 
 * Abstrakte Klasse eines Kollegen.
 */

package mypack;

public abstract class Kollege {

	private Vermittler vermittler;

	public Kollege(Vermittler v) {
		this.vermittler = v;
	}

	public abstract void aktualisiert();

	public Vermittler getVermittler() {
		return vermittler;
	}

	public abstract void zuteilen(Zuord z);

}
