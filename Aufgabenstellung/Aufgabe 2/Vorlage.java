/**
* Frank Mehler
* 04.01.2017
* Vorlage fuer Abgabeaufgabe 2, Sommersemester 2017, awis HS Mainz
* Offene Punkte: 
* 	Das Model muss noch eingebunden werden
* 	Die Beschreibung in der linken auesseren Spalte sollte rechtsbuendig sein
* 	Das Kreuz sollte skaliert werden (je nach Zellengroesse), siehe z.B.:
*   http://stackoverflow.com/questions/2303305/window-resize-event
*   Das werde ich dann machen, wenn sonst nichts Besseres mehr zu tun ist
*/
package ss17aufg2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

class Vorlage extends JFrame {
	private static final long serialVersionUID = 1;

	/**
	 * Hier der Konstruktor als Orientierung fuer eigene Implementierung
	 */
	public Vorlage() {
		super("Nonogramm");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		Container cont = getContentPane();

		JPanel p1 = new JPanel();
		// hgap, vgap = 0, 0, d.h. Abstand von 0 Pixel zwischen Zellen
		// Im 4x4-Gitter sind erste Spalte und erste Zeile zur Beschriftung
		p1.setLayout(new GridLayout(4, 4, 0, 0));

		JButton[][] myB = new JButton[3][3];

		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				// obere Zeile bzw. linke Spalte nur beschriften
				if (x == 0 || y == 0) {
					JLabel myL = new JLabel("Text", JLabel.CENTER);
					myL.setBorder(LineBorder.createBlackLineBorder());
					if (x == 0 && y == 2) {
						myL.setFont(new Font(null, Font.BOLD, 20));
						// Mit html kann man Zeilenumbruch br in Labels
						// ermoeglichen, siehe:
						// http://docs.oracle.com/javase/tutorial/uiswing/components/html.html
						myL.setText("<html><body>3<br/>7</body></html>");
					}
					p1.add(myL);
				} else {
					// Zaehler x und y um 1 verringern, damit Feld
					// von vorne (ab Index 0) befuellt wird
					String nummer = (x - 1) + ";" + (y - 1);
					myB[x - 1][y - 1] = new JButton(nummer);
					myB[x - 1][y - 1].setBackground(Color.WHITE);

					// Spezialfall als Beispiel zeigen:
					myB[0][0].setFont(new Font(null, Font.PLAIN, 80));
					myB[0][0].setText("X");

					myB[x - 1][y - 1].setName(nummer); // interner Bezeichner
					myB[x - 1][y - 1].addMouseListener(new MyButtonListener());
					p1.add(myB[x - 1][y - 1]);
				}
			}
		}
		cont.add(p1, BorderLayout.CENTER);
		setVisible(true);
	}

	/**
	 * Frank Mehler 04.01.2017 Innere Klasse zur Reaktion auf Maus-Ereignisse
	 * Offene Punkte: Auslesen der x/y-Koordinate des Buttons, Eintrag ins
	 * Model, Andere Folgereaktion auf Mausklick
	 */
	public class MyButtonListener extends MouseAdapter {
		/*
		 * Falls ein Mausklick erfolgt, hier die Reaktion darauf
		 * 
		 * @see
		 * java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			Component comp = e.getComponent();
			System.out.println("mousePressed " + comp.getName());
			if (e.getButton() == MouseEvent.BUTTON1) { // linke Maustaste
				if (comp instanceof JButton) {
					JButton b = (JButton) comp;
					b.setBackground(Color.BLUE);
				}
			}
			if (e.getButton() == MouseEvent.BUTTON3) { // rechte Maustaste
				if (comp instanceof JButton) {
					JButton b = (JButton) comp;
					b.setBackground(Color.RED);
				}
			}
		}
	}

	public static void main(String args[]) {
		new Vorlage();
	}
}
