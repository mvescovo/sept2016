package stations;

import application.Main;
import data.State;
import data.Station;
import data.WeatherRepositories;
import data.WeatherServiceApiImpl;
import observations.ObservationsPresenter;
import observations.ObservationsView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * UI for the stations list.
 */
public class StationsView implements StationsContract.View, ActionListener {

    private StationsContract.UserActionsListener mActionsListener;
    private JProgressBar mJProgressBar;
    private JComboBox mStatesComboList;
    private JComboBox mStationsComboList;
    private HashMap<String, Station> mStationHashMap = new HashMap<String, Station>();

    public StationsView() {
        // Add a progress bar to the main window
        mJProgressBar = new JProgressBar();
        mJProgressBar.setIndeterminate(true);
        mJProgressBar.setVisible(false);
        Main.MainWindow.getInstance().getContentPane().add(mJProgressBar);
    }

    // Set the presenter for this view
    public void setActionListener(StationsContract.UserActionsListener actionListener) {
        mActionsListener = actionListener;
    }

    public void setProgressBar(final boolean active) {
        if (active) {
            mJProgressBar.setVisible(true);
        } else {
            mJProgressBar.setVisible(false);
        }
    }

    // When everything is initialised
    public void onReady() {
        mActionsListener.loadStates(true);
    }

    // Show the states
    public void showStates(List<State> states) {
        ArrayList stateNames = new ArrayList();
        for (int i = 0; i < states.size(); i++) {
            stateNames.add(states.get(i).getName());
        }

        mStatesComboList = new JComboBox(stateNames.toArray());
        mStatesComboList.setSelectedIndex(0);
        mStatesComboList.addActionListener(this);
        Main.MainWindow.getInstance().getContentPane().add(mStatesComboList, BorderLayout.PAGE_START);
        Main.MainWindow.getInstance().getContentPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    // Show the stations
    public void showStations(List<Station> stations) {
        mStationHashMap.clear();
        for (int i = 0; i < stations.size(); i++) {
            mStationHashMap.put(stations.get(i).getCity(), stations.get(i));
        }

        ArrayList stationNames = new ArrayList();
        for (int i = 0; i < stations.size(); i++) {
            stationNames.add(stations.get(i).getCity());
        }

        mStationsComboList = new JComboBox(stationNames.toArray());
        mStationsComboList.setSelectedIndex(0);
        mStationsComboList.addActionListener(this);
        Main.MainWindow.getInstance().getContentPane().add(mStationsComboList, BorderLayout.PAGE_START);
        Main.MainWindow.getInstance().getContentPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    public void showStationAsFavourited(String stationId, boolean favourite) {

    }

    public void showObservationsUi(Station station) {
        ObservationsView observationsView = new ObservationsView();
        ObservationsPresenter observationsPresenter = new ObservationsPresenter(WeatherRepositories.getInMemoryRepoInstance(new WeatherServiceApiImpl()), observationsView);
        observationsView.setActionListener(observationsPresenter);
        observationsView.onReady(station);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mStatesComboList) {
            JComboBox cb = (JComboBox)e.getSource();
            String stateName = (String)cb.getSelectedItem();
            mActionsListener.loadStations(stateName, true);
        } else if (e.getSource() == mStationsComboList) {
            JComboBox cb = (JComboBox)e.getSource();
            String stationName = (String)cb.getSelectedItem();
            mActionsListener.openObservations(mStationHashMap.get(stationName));
        }
    }
}
