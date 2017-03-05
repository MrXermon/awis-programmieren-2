package mypack;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class GillaTestfaelle {

	private KonkreterVermittler v;
	private Zuord z;

	@Before
	public void setUp() {
		v = new KonkreterVermittler();
		z = new Zuord();
	}

	@Test
	public void KonkreterVermittler_Gesamtnutzen() {
		KonkreterStudi studi1 = new KonkreterStudi(v, "A", 0, 10);
		v.add(studi1);
		KonkreterStudi studi2 = new KonkreterStudi(v, "B", 3, 7);
		v.add(studi2);
		KonkreterStudi studi3 = new KonkreterStudi(v, "C", 6, 6);
		v.add(studi3);
		KonkreterStudi studi4 = new KonkreterStudi(v, "D", 10, 1);
		v.add(studi4);

		studi4.vermitteln(z);

		assertEquals(19, z.gesamtnutzen());
	}
}
