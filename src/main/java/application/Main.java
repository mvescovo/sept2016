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
	private static final Color colorWhite = new Color(255, 255, 255 );
	private static final Color colorContrast1 = new Color(119, 60, 31);
	private static final Color colorContrast2 = new Color(119, 98, 31);
	
	//Symbols
	private static final String symbolDegree  = "\u00b0";
	
    public static void main(String[] args) {
        // Start the app on the event dispatch thread
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Show the Weather stations
                StationsView stationsView = new StationsView();
                StationsPresenter stationsPresenter = new StationsPresenter(WeatherRepositories.getInMemoryRepoInstance(new WeatherServiceApiImpl()), stationsView);
                stationsView.setActionListener(stationsPresenter);
                stationsView.onReady();
            }
        });
    }


	/*
    * Singleton class to create and show the main window for the app.
    *
    * This window is then used by other parts of the app to add and update relevant GUI components.
    *
    * */
    public static class MainWindow {
        // TODO Kendall to modify as necessary - delete comment when done.

        private volatile static MainWindow uniqueInstance;
        private static Container container;
        private static JPanel stationsPanel;
        private static JPanel observationsPanel;

        private static JScrollPane stationsScrollPane;
        private static JScrollPane favouritesScrollPane;
        
        private static JMenuBar menubar;
        private JLabel stationName;

        private JButton btnFavourite;

        private MainWindow() {
            // Container frame for the main window
            JFrame jFrame;
            jFrame = new JFrame("SEPT Weather App");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            // add window listener to persist data
            /*jFrame.addComponentListener(new ComponentListener() {

				@Override
				public void componentResized(ComponentEvent e) {
					System.out.println("Resized Height: " + e.getComponent().getHeight());
					System.out.println("Resized Width: " + e.getComponent().getWidth());
					
				}

				@Override
				public void componentMoved(ComponentEvent e) {
					System.out.println("Moved X: " + e.getComponent().getX());
					System.out.println("Moved Y: " + e.getComponent().getY());
				}

				@Override
				public void componentShown(ComponentEvent e) {
					System.out.println("Shown: " + e.getSource());
					
				}

				@Override
				public void componentHidden(ComponentEvent e) {
					System.out.println("Hidden: " + e.getSource());
					
				}
            	
            });*/
            
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
            jFrame.setSize(frameWidth,frameHeight);
            jFrame.setLocationRelativeTo(null);
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
            
          //Build the first menu.
            menu = new JMenu("File");
            menu.setMnemonic(KeyEvent.VK_F);
            menuItem = new JMenuItem("Exit");
            menu.add(menuItem);
            menubar.add(menu);
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
