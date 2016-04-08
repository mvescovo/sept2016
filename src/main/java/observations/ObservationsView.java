package observations;

import application.Main;
import data.Observation;
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
 * UI for the observations.
 */
public class ObservationsView implements ObservationsContract.View, ActionListener {

    private ObservationsContract.UserActionsListener mActionsListener;
    private JProgressBar mJProgressBar;
    private JComboBox mObservationsComboList;

    public ObservationsView() {
        // Add a progress bar to the main window
        mJProgressBar = new JProgressBar();
        mJProgressBar.setIndeterminate(true);
        mJProgressBar.setVisible(false);
        Main.MainWindow.getInstance().getContentPane().add(mJProgressBar);
    }

    // Set the presenter for this view
    public void setActionListener(ObservationsContract.UserActionsListener actionListener) {
        mActionsListener = actionListener;
    }

    public void setProgressBar(boolean active) {
        if (active) {
            mJProgressBar.setVisible(true);
        } else {
            mJProgressBar.setVisible(false);
        }
    }

    // When everything is initialised
    public void onReady(Station station) {
        mActionsListener.loadObservations(station, true);
    }

    public void showObservations(List<Observation> observations) {
        ArrayList observationNames = new ArrayList();
        for (int i = 0; i < observations.size(); i++) {
            observationNames.add(observations.get(i).getName());
        }

        mObservationsComboList = new JComboBox(observationNames.toArray());
        mObservationsComboList.setSelectedIndex(0);
        mObservationsComboList.addActionListener(this);
        Main.MainWindow.getInstance().getContentPane().add(mObservationsComboList, BorderLayout.PAGE_END);
        Main.MainWindow.getInstance().getContentPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    }

    public void showChart() {

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == mObservationsComboList) {
            JComboBox cb = (JComboBox) e.getSource();
            String observationName = (String) cb.getSelectedItem();
            System.out.println("Observation name: " + observationName);
        }
    }
}
