package stations;

import application.Main;
import data.State;
import data.Station;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * UI for the stations list.
 */
public class StationsView implements StationsContract.View, ActionListener {

    private StationsContract.UserActionsListener mActionsListener;
    private JProgressBar mJProgressBar;

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

    public void setProgressIndicator(final boolean active) {
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
        ArrayList stateList = new ArrayList();
        for (int i = 0; i < states.size(); i++) {
            stateList.add(states.get(i).getName());
        }

        final JComboBox comboList = new JComboBox(stateList.toArray());
        comboList.setSelectedIndex(0);
        comboList.addActionListener(this);
        Main.MainWindow.getInstance().getContentPane().add(comboList, BorderLayout.PAGE_START);
        Main.MainWindow.getInstance().getContentPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    public void showStations(List<Station> stations) {

    }

    public void showStationAsFavourited(String stationId, boolean favourite) {

    }

    public void showObservationsUi(String stationId) {

    }

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String stateName = (String)cb.getSelectedItem();
        System.out.println("clicked on: " + stateName);
    }
}
