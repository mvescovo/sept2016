package application;

import data.WeatherRepositories;
import data.WeatherServiceApiImpl;
import stations.StationsPresenter;
import stations.StationsView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Created by michael on 20/03/16.
 *
 * Start up the app - contains the main method.
 */
public class Main {

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
        // TODO Kendall to UI parts and modify as necessary - delete comment when done.

        private volatile static MainWindow uniqueInstance;
        private static Container container;
        private static JPanel stationsPanel;
        private static JPanel observationsPanel;

        private MainWindow() {
            // Container frame for the main window
            JFrame jFrame;
            jFrame = new JFrame("SEPT Weather App");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            container = jFrame.getContentPane();

            // Stations panel - callable directly from stations view
            stationsPanel = new JPanel();
            stationsPanel.setBorder(new LineBorder(Color.black));
            stationsPanel.setPreferredSize(new Dimension(300, 0));
            JLabel stationsLabel = new JLabel("Stations Panel");
            stationsPanel.add(stationsLabel);
            stationsPanel.add(Box.createRigidArea(new Dimension(300, 0)));
            container.add(stationsPanel, BorderLayout.LINE_START);

            // Observations panel - callable directly from observations view
            createObservationsPanel();

            // Display the main window.
            jFrame.setSize(800,800);
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

        public void createObservationsPanel() {
            observationsPanel = new JPanel();
            observationsPanel.setBorder(new LineBorder(Color.black));
            JLabel observationsLabel = new JLabel("Observations Panel");
            observationsPanel.add(observationsLabel);
            observationsPanel.add(Box.createRigidArea(new Dimension(500, 0)));
            container.add(observationsPanel, BorderLayout.CENTER);
        }

        public void clearObservationsPanel() {
            if (observationsPanel != null) {
                container.remove(observationsPanel);
                createObservationsPanel();
            }
        }
    }
}
