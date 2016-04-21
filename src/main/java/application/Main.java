package application;

import data.WeatherRepositories;
import data.WeatherServiceApiImpl;
import stations.StationsContract;
import stations.StationsPresenter;
import stations.StationsView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.StringTokenizer;
import java.util.prefs.Preferences;

/**
 * Start up the application - contains the main method and application
 * constants. Created by michael on 5/04/16. Modified by kendall on 18/04/16.
 * 
 * @author michael, kendall
 *
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

	private static final Color colorDark = new Color(64, 89, 114);
	private static final Color colorLight = new Color(241, 239, 226);
	private static final Color colorWhite = new Color(255, 255, 255);
	private static final Color colorBlack = new Color(0, 0, 0);
	private static final Color colorContrast1 = new Color(32, 107, 164);
	private static final Color colorContrast2 = new Color(119, 98, 31);

	// Symbols
	private static final String symbolDegree = "\u00b0";

	/**
	 * Creates and updates the Stations view on the event dispatch thread.
	 * 
	 * @param args
	 *            Standard command arguments for main()
	 */
	public static void main(String[] args) {

		// Start the app on the event dispatch thread
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Show the Weather stations
				StationsContract.View stationsView = new StationsView();
				StationsContract.UserActionsListener stationsPresenter = new StationsPresenter(
						WeatherRepositories.getInMemoryRepoInstance(new WeatherServiceApiImpl()), stationsView);
				stationsView.setActionListener(stationsPresenter);
				stationsView.onReady();
			}
		});
	}

	/**
	 * Singleton class to create and show the main window for the app.
	 *
	 * This window is then used by other parts of the app to add and update
	 * relevant GUI components.
	 * 
	 * @author michael, kendall
	 *
	 */
	public static class MainWindow {

		private volatile static MainWindow uniqueInstance;
		private static Container container;
		private static JPanel stationsPanel;
		private static JPanel observationsPanel;

		private static JScrollPane stationsScrollPane;
		private static JScrollPane favouritesScrollPane;

		private static JFrame jFrame;

		private static JMenuBar menubar;
		private JToolBar toolbar;
		private JLabel stationName;
		private JTextArea introText;

		private JButton btnFavourite;
		private JButton btnRefresh;
		private JButton btnRemove;

		/**
		 * Creates the application's user interface window. Restores previous
		 * settings and constructs the window's layout, containers and
		 * components.
		 */
		private MainWindow() {
			// Container frame for the main window

			jFrame = new JFrame("Australian weather");
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

			// add panel for menu and toolbar
			JPanel menuPanel = new JPanel();
			menuPanel.setLayout(new GridBagLayout());
			menuPanel.setBackground(colorDark);
			GridBagConstraints menuCons = new GridBagConstraints();
			menuCons.gridx = 0;
			menuCons.gridy = 0;
			menuCons.weightx = 1;
			menuCons.anchor = GridBagConstraints.WEST;
			menuCons.fill = GridBagConstraints.HORIZONTAL;
			// add menubar
			createMenuBar();

			// Add toolbar
			createToolBar();
			menuPanel.add(menubar, menuCons);
			menuCons.gridy = 1;

			menuPanel.add(toolbar, menuCons);
			container.add(menuPanel, BorderLayout.NORTH);

			// Stations panel - callable directly from stations view
			createStationsPanel();

			container.add(stationsPanel, BorderLayout.WEST);

			// Observations panel - callable directly from observations view
			createObservationsPanel();

			// Display the main window.

			jFrame.setVisible(true);

		}

		/**
		 * Creates a new Toolbar object with action buttons to update the
		 * observation and to add or remove the currently viewed station from
		 * the favourites list. Updates the class's toolbar reference.
		 */
		private void createToolBar() {
			toolbar = new JToolBar();
			toolbar.setFloatable(false);
			toolbar.setBackground(colorDark);
			toolbar.setBorder(null);
			toolbar.addSeparator(new Dimension(10, 50));
			
			JLabel title = new JLabel("Weather stations");
			title.setFont(fontNormalBold);
			title.setForeground(colorWhite);
			

			toolbar.add(title);
			toolbar.addSeparator(new Dimension(35, 10));
			
			btnRefresh = new JButton("Refresh");
			btnRefresh.setName("refresh");
			btnRefresh.setMargin(new Insets(10, 10, 10, 10));
			toolbar.add(btnRefresh);
			toolbar.addSeparator();
			btnFavourite = new JButton("Add to Favourites");
			btnFavourite.setMargin(new Insets(10, 10, 10, 10));
			btnFavourite.setName("add");
			toolbar.add(btnFavourite);
			btnRemove = new JButton("Remove from Favourites");
			btnRemove.setMargin(new Insets(10, 10, 10, 10));
			btnRemove.setName("remove");
			toolbar.add(btnRemove);
			

		}

		/**
		 * Instantiates a unique instance of the applications main window.
		 * 
		 * @return A MainWindow object instance.
		 */
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

		/**
		 * Creates a JPanel to display States and weather stations. It adds
		 * labels, a favourite station scrollpane, a combo box for States and
		 * another scrollpane for Stations (updated by selecting a State). Uses
		 * a GridBag layout with constraints. Updates the class's stationsPanel
		 * object. Called during UI build.
		 */
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

			stationsPanel.setBackground(colorDark);

			// favourites
			JLabel favlabel = new JLabel("Favourites");
			favlabel.setForeground(colorWhite);
			stationsPanel.add(favlabel, stationCons);

			stationCons.gridy = 1;
			stationCons.weighty = 0;
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

		/**
		 * Creates a JPanel for weather observation data. Uses a GridBag layout
		 * with constraints and adds the initial label to the panel. Updates the
		 * class's observationsPanel object. Called during UI build.
		 */
		public void createObservationsPanel() {
			observationsPanel = new JPanel();
			observationsPanel.setLayout(new GridBagLayout());
			observationsPanel.setBackground(colorLight);

			GridBagConstraints cons = new GridBagConstraints();
			cons.anchor = GridBagConstraints.NORTHWEST;
			cons.gridx = 0;
			cons.gridy = 0;
			cons.weighty = 1;
			cons.weightx = 1;
			cons.insets = new Insets(10, 10, 10, 10);
			cons.fill = GridBagConstraints.BOTH;

			introText = new JTextArea(
					"To view weather observations, select a State from the drop-down box and then a Stations from the displayed list.");
			introText.setLineWrap(true);
			introText.setMargin(new Insets(50,50,50,50));
			introText.setWrapStyleWord(true);
			introText.setFont(Main.getFonttitle());
			introText.setBackground(observationsPanel.getBackground());
			stationName = new JLabel("");
			stationName.setFont(Main.getFonttitle());
			observationsPanel.add(stationName, cons);
			
			cons.insets = new Insets(100, 10, 10, 10);
			observationsPanel.add(introText, cons);

			container.add(observationsPanel, BorderLayout.CENTER);
		}

		/**
		 * Resets the observation panel. Called when a user selects a different
		 * weather station for viewing.
		 * 
		 */
		public void clearObservationsPanel() {
			if (observationsPanel != null) {
				container.remove(observationsPanel);
				createObservationsPanel();
			}
		}

		/**
		 * Creates a JMenubar object and adds default menu items and actions.
		 * Updates the class's menubar object. Called during UI build.
		 */
		public void createMenuBar() {
			menubar = new JMenuBar();
			JMenu menu, menuFav;
			JMenuItem menuItem, menuItemFav1, menuItemFav2;

			// Build the file menu.
			menu = new JMenu("File");
			menu.setMnemonic(KeyEvent.VK_F);
			menuItem = new JMenuItem("Exit");
			menuItem.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					savePreferences();
					System.exit(0);
					
				}
				
			});
			menu.add(menuItem);
			menubar.add(menu);


		}

		/**
		 * Saves the session's window settings to a node using the Java
		 * Preferences API. Called on application end or any other time really.
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
				e.printStackTrace();
			}
		}

		/**
		 * Retrieve window settings from last session and apply them. Called at
		 * application start. If not previous settings exist or an error occurs
		 * the default settings will be used.
		 * 
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
		 * Helper method to tokenise a String.
		 * 
		 * @param str
		 *            is the String to be tokenised.
		 * @param separators
		 *            is the token delimiter as a String
		 * @return An array of String tokens.
		 */
		private static String[] explode(String str, String separators) {
			StringTokenizer tokenizer = new StringTokenizer(str, separators);
			int ct = tokenizer.countTokens();
			String[] tokens = new String[ct];
			for (int i = 0; i < ct; i++)
				tokens[i] = tokenizer.nextToken();
			return tokens;
		}

		/*
		 * Getters and setters for UI components.
		 */
		public Container getContainer() {
			return container;
		}

		public JPanel getStationsPanel() {
			return stationsPanel;
		}

		public JPanel getObservationsPanel() {
			return observationsPanel;
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

		public JScrollPane getFavouritesScrollPane() {
			return favouritesScrollPane;
		}

		public void setFavouritesScrollPane(JScrollPane favouritesScrollPane) {
			MainWindow.favouritesScrollPane = favouritesScrollPane;
		}

		public JToolBar getToolbar() {
			return toolbar;
		}

		public void setToolbar(JToolBar toolbar) {
			this.toolbar = toolbar;
		}

		public JButton getBtnFavourite() {
			return btnFavourite;
		}

		public void setBtnFavourite(JButton btnFavourite) {
			this.btnFavourite = btnFavourite;
		}

		public JButton getBtnRefresh() {
			return btnRefresh;
		}

		public void setBtnRefresh(JButton btnRefresh) {
			this.btnRefresh = btnRefresh;
		}

		public JButton getBtnRemove() {
			return btnRemove;
		}

		public void setBtnRemove(JButton btnRemove) {
			this.btnRemove = btnRemove;
		}

		public JTextArea getIntroText() {
			return introText;
		}

		public void setIntroText(JTextArea introText) {
			this.introText = introText;
		}

	}

	/*
	 * Getters for the application constants.
	 *
	 */
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

	public static Color getColordark() {
		return colorDark;
	}

	public static Color getColorlight() {
		return colorLight;
	}

	public static Color getColorwhite() {
		return colorWhite;
	}

	public static Color getColorcontrast1() {
		return colorContrast1;
	}

	public static Color getColorcontrast2() {
		return colorContrast2;
	}

	public static Color getColorblack() {
		return colorBlack;
	}

}
