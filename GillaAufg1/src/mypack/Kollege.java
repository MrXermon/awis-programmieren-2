package mypack;

public abstract class Kollege {

	private Vermittler vermittler;

	public Kollege(Vermittler v) {
		this.vermittler = v;
	}

	public abstract void vermitteln(Zuord z);

	public abstract void aktualisiert();

	public Vermittler getVermittler() {
		return vermittler;
	}

}
