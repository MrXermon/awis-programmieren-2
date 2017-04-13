/**
 * Jan Gilla
 * 11.04.2017
 * V1.0
 * 
 * Klasse zur Abbildung der grafischen Oberflaeche des Spiels.
 */

package mypack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Nonogramm extends JFrame {

	private static int groessenFaktor = 100;
	private static final long serialVersionUID = 1L;

	private JButton[][] buttonFeld;
	private Modell modell;

	/**
	 * Konstruktor zum Erstellen des vorgegebenen Beispiels.
	 */
	public Nonogramm() {
		this(-1);
	}

	/**
	 * Konstruktor zum Erstellen eines beliebigen Spiels.
	 * 
	 * @param groesse
	 *            Groesse des Spielfelds
	 */
	public Nonogramm(int groesse) {
		super("Nonogramm - Jan Gilla - awis TZ - SoSe 2017 - HS Mainz");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * Wenn als Groesse -1 gesetzt ist wird das Beispiel geladen, sonst ein
		 * zufaelliges Feld generiert.
		 */
		if (groesse == -1) {
			groesse = 4;
			this.modell = new Modell(groesse);
			this.buttonFeld = new JButton[groesse][groesse];
			this.modell.feldBeispiel();
		} else {
			this.modell = new Modell(groesse);
			this.buttonFeld = new JButton[groesse][groesse];
			this.modell.feldZufall();
		}

		this.setSize((groessenFaktor * (groesse + 1)), ((groessenFaktor * (groesse + 1)) + 50));

		Container fensterInhalt = this.getContentPane();

		/**
		 * Erezugung des Spielfelds
		 */
		JPanel spielPanel = new JPanel();
		spielPanel.setLayout(new GridLayout((groesse + 1), (groesse + 1), 0, 0));

		for (int x = 0; x < (groesse + 1); x++) {
			for (int y = 0; y < (groesse + 1); y++) {
				if (x == 0 || y == 0) {
					/**
					 * Ueberschriften
					 */
					JLabel label = new JLabel("Text", SwingConstants.CENTER);
					label.setBorder(LineBorder.createBlackLineBorder());
					label.setFont(new Font(null, Font.BOLD, 20));

					/**
					 * Unterscheidung in Eckfeld Links Oben,
					 * Spaltenueberschriften und Zeilenueberschriften
					 */
					if (x == 0 && y == 0) {
						label.setText("");
					} else if (x == 0) {
						/**
						 * Spaltenfeld generieren
						 */
						label.setText(this.modell.getTitelFeld(0, (y - 1)));
					} else if (y == 0) {
						/**
						 * Zeilenfeld generieren
						 */
						label.setText(this.modell.getTitelFeld(1, (x - 1)));
					}

					spielPanel.add(label);
				} else {
					/**
					 * Spielfeld
					 */
					buttonFeld[x - 1][y - 1] = new JButton();
					buttonFeld[x - 1][y - 1].setBackground(Color.WHITE);
					buttonFeld[x - 1][y - 1].setName((x - 1) + ":" + (y - 1));
					buttonFeld[x - 1][y - 1].addMouseListener(new spielfeldButtonListener());
					spielPanel.add(buttonFeld[x - 1][y - 1]);
				}
			}
		}
		fensterInhalt.add(spielPanel, "Center");

		/**
		 * Erezugung des "Menus"
		 */
		JPanel menuePanel = new JPanel();
		menuePanel.setLayout(new BorderLayout());

		/**
		 * Button zum Pruefen der durch den Anwender eingegebenen Loesung ueber
		 * die im Modell erstellte Methode. Ausgabe des Status mittels
		 * Textboxen.
		 */
		JButton pruefenButton = new JButton("Pruefe Loesung");
		pruefenButton.addActionListener((e) -> {
			if (this.modell.loesungChecken())
				JOptionPane.showMessageDialog(this, "Alles ok!", "Loesung", JOptionPane.INFORMATION_MESSAGE);
			else
				JOptionPane.showMessageDialog(this, this.modell.getFehlerText(), "Loesung", JOptionPane.ERROR_MESSAGE);
		});
		menuePanel.add(pruefenButton, "East");

		/**
		 * Button zum Erstellen einer neuen Runde. Erzeugen eines Input-Dialogs
		 * mit Umwandlung der Eingabe in eine Ganzzahl, wenn die Zahl
		 * umgewandelt werden kann.
		 */
		JButton neuButton = new JButton("Neues Spiel");
		neuButton.addActionListener(e -> {
			String rueckgabe = JOptionPane.showInputDialog(this, "Bitte Groesse des Quadrats eingeben:");
			if (rueckgabe != null) {
				try {
					int r = Integer.parseInt(rueckgabe);
					this.dispose();
					new Nonogramm(r);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(this, "Das war doch keine Zahl, oder?", "Fehler",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		menuePanel.add(neuButton, "West");

		fensterInhalt.add(menuePanel, "South");

		/**
		 * Groessenveraenderung des Felds durch den ComponentAdapter erkennen.
		 * Quellen:
		 * http://stackoverflow.com/questions/2303305/window-resize-event,
		 * http://docs.oracle.com/javase/6/docs/api/java/awt/event/ComponentAdapter.html
		 */
		this.addComponentListener(new spielfeldComponentAdapter());
		this.setVisible(true);
	}

	/**
	 * Main-Methode zum Starten des Programms.
	 * 
	 * @param args
	 *            Argumente, die ueber die Kommandozeile mitgegeben werden
	 *            koennen-
	 */
	public static void main(String[] args) {
		new Nonogramm();
	}

	/**
	 * Klasse zur Implementierung des ueberschriebenen MouseListeners.
	 * 
	 * @author gillaj
	 *
	 */
	private class spielfeldButtonListener extends MouseAdapter {

		/**
		 * Methode, die beim Klick auf einen Button aufgerufen wird.
		 * 
		 * @param e:
		 *            Uebergebene Eventinformationen
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			JButton button = (JButton) e.getComponent();
			Modell m = Nonogramm.this.modell;
			try {
				String[] koord = button.getName().split(":");

				int x = Integer.parseInt(koord[0]);
				int y = Integer.parseInt(koord[1]);

				switch (e.getButton()) {
				/**
				 * Linke Maustaste
				 */
				case 1:
					/**
					 * Schwarz und Weiﬂ umschalten. Kreuz wird ebenfalls zu
					 * Schwarz.
					 */
					if (m.getStatus(x, y) == Status.SCHWARZ) {
						m.setStatus(x, y, Status.LEER);
						button.setBackground(Color.WHITE);
						button.setText("");
					} else {
						m.setStatus(x, y, Status.SCHWARZ);
						button.setBackground(Color.BLACK);
						button.setText("");
					}
					break;
				/**
				 * Rechte Maustaste
				 */
				case 3:
					/**
					 * Kreuz und Leer umschalten.
					 */
					if (m.getStatus(x, y) == Status.KREUZ) {
						m.setStatus(x, y, Status.LEER);
						button.setBackground(Color.WHITE);
						button.setText("");
					} else {
						m.setStatus(x, y, Status.KREUZ);
						button.setBackground(Color.WHITE);
						button.setText("X");
					}
					break;
				}
			} catch (Exception e2) {
				System.err.println("Exeption beim Auslesen der x und y Koordinaten des gedrueckten Buttons.");
			}
		}

	}

	/**
	 * Klasse zur Implementierung des ueberschriebenen ComponentAdapter zur
	 * Anpassung der Schriftgroesse inerhalb des Spielfelds.
	 * 
	 * Quellen: http://stackoverflow.com/questions/2303305/window-resize-event,
	 * http://docs.oracle.com/javase/6/docs/api/java/awt/event/ComponentAdapter.html
	 * 
	 * @author gillaj
	 *
	 */
	private class spielfeldComponentAdapter extends ComponentAdapter {

		/**
		 * Methode am Listener, die bei einer Groessenaenderung des Fensters
		 * aufgerufen wird.
		 * 
		 * @param e:
		 *            Uebergebene Eventinformationen
		 */
		@Override
		public void componentResized(ComponentEvent e) {
			JButton[][] bf = Nonogramm.this.buttonFeld;
			/**
			 * Iteration und Anpassung der Schriftgroesse auf den einzelnen
			 * Buttons durch Iteration uber das Array mit Feldern.
			 */
			for (int x = 0; x < bf.length; x++) {
				for (int y = 0; y < bf[x].length; y++) {
					/**
					 * Vorgabe der neuen Groesse als Hoehe des Buttons / 2
					 */
					bf[x][y].setFont(new Font(null, Font.PLAIN, bf[x][y].getHeight() / 2));
				}
			}
		}

	}

}
