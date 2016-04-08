package application;

import data.WeatherRepositories;
import data.WeatherServiceApiImpl;
import stations.StationsPresenter;
import stations.StationsView;

import javax.swing.*;

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
    * Singleton class to create and show an empty main window for the app.
    *
    * This window is then used by other parts of the app to add and update relevant GUI components.
    *
    * */
    public static class MainWindow {
        private volatile static MainWindow uniqueInstance;
        private static JComponent mContentPane;

        private MainWindow() {
            JFrame jFrame;
            jFrame = new JFrame("SEPT Weather App");
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create and set up the content pane.
            mContentPane = new JPanel();
            mContentPane.setOpaque(true); //content panes must be opaque
            jFrame.setContentPane(mContentPane);

            // Display the window.
            jFrame.setSize(600,600);
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

        public JComponent getContentPane() {
            return mContentPane;
        }
    }
}
