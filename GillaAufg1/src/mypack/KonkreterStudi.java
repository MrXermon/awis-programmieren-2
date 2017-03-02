package mypack;

public class KonkreterStudi extends Kollege {

	private String name;
	private int skillInfo;
	private int skillMathe;
	private Kollege partner;

	public KonkreterStudi(Vermittler v, String n, int i, int m) {
		super(v);
		this.name = n;
		this.skillInfo = i;
		this.skillMathe = m;
	}

	public int getSkillInfo() {
		return skillInfo;
	}

	public void setSkillInfo(int skillInfo) {
		this.skillInfo = skillInfo;
		this.aktualisiert();
	}

	public int getSkillMathe() {
		return skillMathe;
	}

	public void setSkillMathe(int skillMathe) {
		this.skillMathe = skillMathe;
		this.aktualisiert();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		this.aktualisiert();
	}

	public Kollege getPartner() {
		return partner;
	}

	public void setPartner(Kollege partner) {
		this.partner = partner;
		this.aktualisiert();
	}
	
	public int nutzen(Kollege k){
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

	public String toString() {
		return "[name=" + this.name + ", fitnessInf=" + this.skillInfo + ", fitnessMathe" + this.skillMathe + "]";
	}

	public void aktualisiert() {
		System.out.println("KonkreterStudi ist informiert, zugeteilter Partner von " + this.getName() + " ist: "
				+ ((KonkreterStudi) this.getPartner()).getName());
	}

	public void vermitteln(Zuord z) {
		System.out.println(
				"KonkreterStudi " + this.getName() + " moechte zugeteilt werden und informiert den Vermittler");
		this.getVermittler().vermitteln(z, this);
	}

}
