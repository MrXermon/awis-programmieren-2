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

	public abstract void vermitteln(Zuord z);

}
