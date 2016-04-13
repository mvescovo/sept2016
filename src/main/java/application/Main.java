package application;

import data.WeatherRepositories;
import data.WeatherServiceApiImpl;
import stations.StationsPresenter;
import stations.StationsView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

/**
 * Created by michael on 20/03/16.
 *
 * Start up the app - contains the main method.
 */
public class Main {

	// default window size
	private static final int frameWidth = 1024;
	private static final int frameHeight = 768;

	// fonts
	private static final String fontFamily = "SansSerif";
	private static final Font fontTitle = new Font(fontFamily, Font.BOLD, 24);
	private static final Font fontSmall = new Font(fontFamily, Font.PLAIN, 11);
	private static final Font fontNormal = new Font(fontFamily, Font.PLAIN, 14);
	private static final Font fontNormalBold = new Font(fontFamily, Font.BOLD, 14);

	// colors
	private static final Color colorDark = new Color(50, 50, 50);
	private static final Color colorLight = new Color(250, 250, 250);
	private static final Color colorWhite = new Color(255, 255, 255);
	private static final Color colorContrast1 = new Color(119, 60, 31);
	private static final Color colorContrast2 = new Color(119, 98, 31);

	// Symbols
	private static final String symbolDegree = "\u00b0";

	public static void main(String[] args) {
		// Start the app on the event dispatch thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Show the Weather stations
				StationsView stationsView = new StationsView();
				StationsPresenter stationsPresenter = new StationsPresenter(
						WeatherRepositories.getInMemoryRepoInstance(new WeatherServiceApiImpl()), stationsView);
				stationsView.setActionListener(stationsPresenter);
				stationsView.onReady();
			}
		});
	}

	/*
	 * Singleton class to create and show the main window for the app.
	 *
	 * This window is then used by other parts of the app to add and update
	 * relevant GUI components.
	 *
	 */
	public static class MainWindow {
		// TODO Kendall to modify as necessary - delete comment when done.

		private volatile static MainWindow uniqueInstance;
		private static Container container;
		private static JPanel stationsPanel;
		private static JPanel observationsPanel;

		private static JScrollPane stationsScrollPane;
		private static JScrollPane favouritesScrollPane;

		private static JFrame jFrame;

		private static JMenuBar menubar;
		private JLabel stationName;

		private JButton btnFavourite;

		private MainWindow() {
			// Container frame for the main window

			jFrame = new JFrame("SEPT Weather App");
			applyPreferences();

			jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			jFrame.addWindowListener(new WindowAdapter() {
				// When the window is disposed, this event handler will
				// be notified. It will save current settings to the
				// user's preferences and terminate the program.
				public void windowClosed(WindowEvent evt) {
					savePreferences();
					System.exit(0);
				}
			});

			container = jFrame.getContentPane();

			// add menubar
			createMenuBar();
			container.add(menubar, BorderLayout.NORTH);

			// Stations panel - callable directly from stations view
			createStationsPanel();

			container.add(stationsPanel, BorderLayout.WEST);

			// Observations panel - callable directly from observations view
			createObservationsPanel();

			// Display the main window.

			jFrame.setVisible(true);

		}

		public static MainWindow getInstance() {
			if (uniqueInstance == null) {
				synchronized (MainWindow.class) {
					if (uniqueInstance == null) {
						uniqueInstance = new MainWindow();
					}
				}
			}
			return uniqueInstance;
		}

		public Container getContainer() {
			return container;
		}

		public JPanel getStationsPanel() {
			return stationsPanel;
		}

		public JPanel getObservationsPanel() {
			return observationsPanel;
		}

		private void createStationsPanel() {
			stationsPanel = new JPanel();
			stationsPanel.setLayout(new GridBagLayout());
			GridBagConstraints stationCons = new GridBagConstraints();
			stationCons.gridx = 0;
			stationCons.gridy = 0;
			stationCons.weighty = 0;
			stationCons.insets = new Insets(10, 10, 0, 10);
			stationCons.anchor = GridBagConstraints.NORTH;
			stationCons.fill = GridBagConstraints.BOTH;

			stationsPanel.setBorder(new LineBorder(colorWhite));
			stationsPanel.setBackground(colorDark);

			// favourites
			JLabel favlabel = new JLabel("Favourites");
			favlabel.setForeground(colorWhite);
			stationsPanel.add(favlabel, stationCons);

			stationCons.gridy = 1;
			stationCons.weighty = 0.5;
			stationCons.insets = new Insets(0, 10, 10, 10);
			favouritesScrollPane = new JScrollPane();
			stationsPanel.add(favouritesScrollPane, stationCons);

			// states
			stationCons.gridy = 2;
			stationCons.weighty = 0;
			stationCons.insets = new Insets(0, 10, 10, 10);
			JLabel statesLabel = new JLabel("Select a State");
			statesLabel.setForeground(colorWhite);
			stationsPanel.add(statesLabel, stationCons);
			stationsPanel.setBackground(colorDark);

			stationCons.gridy = 4;
			stationCons.insets = new Insets(10, 10, 0, 10);
			JLabel stationsLabel = new JLabel("Select a Station");
			stationsLabel.setForeground(colorWhite);
			stationsPanel.add(stationsLabel, stationCons);

			stationsScrollPane = new JScrollPane();
			stationCons.gridy = 6;
			stationCons.weighty = 1;
			stationCons.insets = new Insets(0, 10, 10, 10);
			stationsPanel.add(stationsScrollPane, stationCons);

		}

		public void createObservationsPanel() {
			observationsPanel = new JPanel();
			observationsPanel.setLayout(new GridBagLayout());
			GridBagConstraints cons = new GridBagConstraints();
			cons.anchor = GridBagConstraints.NORTHWEST;
			cons.gridx = 0;
			cons.gridy = 0;
			cons.insets = new Insets(10, 10, 10, 10);
			cons.fill = GridBagConstraints.BOTH;
			observationsPanel.setBorder(new LineBorder(Color.black));

			stationName = new JLabel("Observations Panel");
			stationName.setFont(Main.getFonttitle());
			observationsPanel.add(stationName, cons);

			// add to favourites button
			btnFavourite = new JButton("Add to favourites");
			cons.gridx = 1;
			cons.weightx = 0;
			cons.fill = GridBagConstraints.NONE;
			cons.anchor = GridBagConstraints.EAST;
			btnFavourite.setVisible(false);
			observationsPanel.add(btnFavourite, cons);
			observationsPanel.setBackground(colorLight);

			container.add(observationsPanel, BorderLayout.CENTER);
		}

		public void clearObservationsPanel() {
			if (observationsPanel != null) {
				container.remove(observationsPanel);
				createObservationsPanel();
			}
		}

		public void createMenuBar() {
			menubar = new JMenuBar();
			JMenu menu, menuFav;
			JMenuItem menuItem, menuItemFav1, menuItemFav2;

			// Build the file menu.
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_F);
			menuItem = new JMenuItem("Exit");
			menu.add(menuItem);
			menubar.add(menu);

			// Build favourites menu
			menuFav = new JMenu("Favourites");
			menuFav.setMnemonic(KeyEvent.VK_V);
			menuItemFav1 = new JMenuItem("Add current station");
			menuItemFav1.setEnabled(false);
			menuFav.add(menuItemFav1);
			menuItemFav2 = new JMenuItem("Remove current station");
			menuItemFav2.setEnabled(false);
			menuFav.add(menuItemFav2);
			menubar.add(menuFav);
		}

		/**
		 * Called when the program ends to try to save window bounds and current
		 * directory to user's preferences, so they can be restored the next
		 * time the program is run. Since this is not a critical function,
		 * errors are ignored. If an error occurs, the data simply won't be
		 * saved.
		 */
		private static void savePreferences() {
			try {
				String pathName = "/sept2016";
				// The pathname uniquely identifies this program. (It is unique
				// because it comes from the package name, which should not be
				// used
				// for any other program under Java's package naming guidelines.
				Preferences root = Preferences.userRoot();
				Preferences node = root.node(pathName);
				// This "node" holds the preferences associated with the
				// pathName.
				// Preferences take the form of key/value pairs, with put() and
				// get() operations for changing/reading values.
				Rectangle bounds = jFrame.getBounds();
				String boundsString = bounds.x + "," + bounds.y + "," + bounds.width + "," + bounds.height;
				node.put("window.bounds", boundsString);

			} catch (Exception e) {
			}
		}

		/**
		 * This method is called when the program starts to read and apply the
		 * preferences that were saved the last time the program ran. Since this
		 * is not a critical function, errors are ignored. If an error occurs,
		 * preferences will simply not be restored.
		 */
		private static void applyPreferences() {
			try {
				String pathName = "/sept2016"; // Identifies prefs for this
												// program.
				Preferences root = Preferences.userRoot();
				if (!root.nodeExists(pathName))
					return; // There are no saved prefs for this program yet.
				Preferences node = root.node(pathName);
				String boundsString = node.get("window.bounds", null);

				if (boundsString != null) {
					// Try to restore window bounds, ignoring any error.
					String[] bounds = explode(boundsString, ",");
					try {
						int x = Integer.parseInt(bounds[0]);
						int y = Integer.parseInt(bounds[1]);
						int w = Integer.parseInt(bounds[2]);
						int h = Integer.parseInt(bounds[3]);
						if (w > 5000 || h > 5000)
							throw new NumberFormatException(); // unreasonable
																// values.
						jFrame.setBounds(x, y, w, h);

					} catch (NumberFormatException e) {
					}
				} else {
					jFrame.setSize(frameWidth, frameHeight);
					jFrame.setLocationRelativeTo(null);
				}
			} catch (Exception e) {
			}
		}

		/**
		 * A convenience method that breaks up a string into tokens, where the
		 * tokens are substrings separated by specified delimiters. For example,
		 * explode("ab,cde,f,ghij", ",") produces an array of the four
		 * substrings "ab" "cde" "f" "ghi".
		 */
		private static String[] explode(String str, String separators) {
			StringTokenizer tokenizer = new StringTokenizer(str, separators);
			int ct = tokenizer.countTokens();
			String[] tokens = new String[ct];
			for (int i = 0; i < ct; i++)
				tokens[i] = tokenizer.nextToken();
			return tokens;
		}

		public JScrollPane getStationsScrollPane() {
			return stationsScrollPane;
		}

		public JMenuBar getMenubar() {
			return menubar;
		}

		public static void setMenubar(JMenuBar menubar) {
			MainWindow.menubar = menubar;
		}

		public JLabel getStationName() {
			return stationName;
		}

		public void setStationName(JLabel stationName) {
			this.stationName = stationName;
		}

		public static JScrollPane getFavouritesScrollPane() {
			return favouritesScrollPane;
		}

		public static void setFavouritesScrollPane(JScrollPane favouritesScrollPane) {
			MainWindow.favouritesScrollPane = favouritesScrollPane;
		}

		public JButton getBtnFavourite() {
			return btnFavourite;
		}

		public void setBtnFavourite(JButton btnFavourite) {
			this.btnFavourite = btnFavourite;
		}

	}

	public static Font getFontnormalbold() {
		return fontNormalBold;
	}

	public static String getSymboldegree() {
		return symbolDegree;
	}

	public static Font getFontsmall() {
		return fontSmall;
	}

	public static Font getFonttitle() {
		return fontTitle;
	}

	public static Font getFontnormal() {
		return fontNormal;
	}

}
