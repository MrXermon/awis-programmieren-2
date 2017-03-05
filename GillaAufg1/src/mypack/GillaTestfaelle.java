package mypack;

import static org.junit.Assert.assertEquals;

import java.util.Random;

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
	public void Gilla1() {
		Random rand = new Random(123);
		for (int i = 0; i < 4; i++)
			v.add(new KonkreterStudi(v, "" + (char) (i + 65), rand.nextInt(10), rand.nextInt(10)));
		
		v.print();
		
		

		v.vermitteln(z);

		z.print();
		//assertEquals(19, z.gesamtnutzen());
	}

	@Test
	public void Gilla2() {
		KonkreterStudi studi1 = new KonkreterStudi(v, "A", 0, 10);
		v.add(studi1);
		KonkreterStudi studi2 = new KonkreterStudi(v, "B", 3, 7);
		v.add(studi2);
		KonkreterStudi studi3 = new KonkreterStudi(v, "C", 6, 6);
		v.add(studi3);

		v.print();

		KonkreterStudi studi4 = new KonkreterStudi(v, "D", 10, 1);
		v.add(studi4);

		v.vermitteln(z);
		
		z.gesamtnutzen();
	}
	
	@Test
	public void Gilla3() {
		System.out.println("========================== Testfall 3 ==========================");
		Random rand = new Random(123);
		for (int i = 0; i < 8; i++)
			v.add(new KonkreterStudi(v, "" + (char) (i + 65), rand.nextInt(10), rand.nextInt(10)));

		v.print();

		v.vermitteln(z);
		System.out.println();

		System.out.println("Gesamtnutzen: " + z.gesamtnutzen());
		assertEquals(117, z.gesamtnutzen());
		System.out.println("================================================================");
	}
}
