/**
 * Jan Gilla
 * 09.03.2017
 * V1.0
 * 
 * Umsetzung der einzelnen Testfaelle.
 * 
 * Leider habe ich die Naeherungsloesung nicht umgesetzt bekommen.
 */

package mypack;

import static org.junit.Assert.assertEquals;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Testfaelle {

	private KonkreterVermittler v;
	private Zuord z;

	@Before
	public void setUp() {
		v = new KonkreterVermittler();
		z = new Zuord();
	}

	@After
	public void setDown() {
		System.out.println("================================================================");
		System.out.println();
	}

	@Test
	public void testFall1() {
		System.out.println("========================== Testfall 1 ==========================");
		KonkreterStudi studi1 = new KonkreterStudi(v, "A", 0, 10);
		v.registriereKollege(studi1);
		KonkreterStudi studi2 = new KonkreterStudi(v, "B", 3, 7);
		v.registriereKollege(studi2);
		KonkreterStudi studi3 = new KonkreterStudi(v, "C", 6, 6);
		v.registriereKollege(studi3);

		v.print();

		KonkreterStudi studi4 = new KonkreterStudi(v, "D", 10, 1);
		v.registriereKollege(studi4);

		studi4.zuteilen(z);

		assertEquals(studi1, z.get(studi4));
	}

	@Test
	public void testFall2() {
		System.out.println("========================== Testfall 2 ==========================");
		KonkreterStudi studi1 = new KonkreterStudi(v, "A", 0, 10);
		v.registriereKollege(studi1);
		KonkreterStudi studi2 = new KonkreterStudi(v, "B", 3, 7);
		v.registriereKollege(studi2);
		KonkreterStudi studi3 = new KonkreterStudi(v, "C", 6, 6);
		v.registriereKollege(studi3);
		KonkreterStudi studi4 = new KonkreterStudi(v, "D", 10, 1);
		v.registriereKollege(studi4);

		v.print();

		v.zuteilen(z);
		System.out.println();

		System.out.print("Zuordnung: ");
		z.print(v);

		System.out.println("Gesamtnutzen: " + z.gesamtnutzen());
		assertEquals(23, z.gesamtnutzen());
	}

	@Test
	public void testFall3() {

		System.out.println("========================== Testfall 3 ==========================");
		Random rand = new Random(123);
		for (int i = 0; i < 26; i++)
			v.registriereKollege(new KonkreterStudi(v, "" + (char) (i + 65), rand.nextInt(10), rand.nextInt(10)));

		v.print();

		v.zuteilen(z);
		System.out.println();

		System.out.print("Zuordnung: ");
		z.print(v);

		System.out.println("Gesamtnutzen: " + z.gesamtnutzen());
		assertEquals(77, z.gesamtnutzen());
	}

}
