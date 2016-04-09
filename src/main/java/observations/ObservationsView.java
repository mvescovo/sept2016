package observations;

import application.Main;
import data.Observation;
import data.Station;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * UI for the observations.
 */
public class ObservationsView implements ObservationsContract.View, ActionListener {
    // TODO Kendall to UI parts and modify as necessary - delete comment when done.

    private ObservationsContract.UserActionsListener mActionsListener;
    private JProgressBar mJProgressBar;
    private JPanel mTablePanel;
    private JPanel mChartPanel;
    private JTextArea mObservationsTextArea;

    public ObservationsView() {
        // Add a progress bar
        mJProgressBar = new JProgressBar();
        mJProgressBar.setIndeterminate(true);
        mJProgressBar.setVisible(false);
        Main.MainWindow.getInstance().getObservationsPanel().add(mJProgressBar);
        Main.MainWindow.getInstance().getObservationsPanel().add(Box.createRigidArea(new Dimension(500, 0)));

        // Add table panel
        mTablePanel = new JPanel();
        mTablePanel.setPreferredSize(new Dimension(500,350));
        mTablePanel.setBorder(new LineBorder(Color.blue));
        JLabel tableLabel = new JLabel("Table Panel");
        mTablePanel.add(tableLabel);
        Main.MainWindow.getInstance().getObservationsPanel().add(mTablePanel);
        Main.MainWindow.getInstance().getObservationsPanel().add(Box.createRigidArea(new Dimension(500, 0)));

        // Add chart panel
        mChartPanel = new JPanel();
        mChartPanel.setPreferredSize(new Dimension(500,350));
        mChartPanel.setBorder(new LineBorder(Color.red));
        JLabel chartLabel = new JLabel("Chart Panel");
        mChartPanel.add(chartLabel);
        Main.MainWindow.getInstance().getObservationsPanel().add(mChartPanel);
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
        Main.MainWindow.getInstance().getObservationsPanel().repaint();
    }

    // When everything is initialised
    public void onReady(Station station) {
        mActionsListener.loadObservations(station, true);
    }

    public void showObservations(List<Observation> observations) {
        mObservationsTextArea = new JTextArea(10, 40);
        mObservationsTextArea.setLineWrap(true);
        mTablePanel.add(mObservationsTextArea);

        for (int i = 0; i < observations.size(); i++) {
            mObservationsTextArea.append(observations.get(i).getName());
        }
    }

    public void showChart() {

    }

    public void actionPerformed(ActionEvent e) {

    }
}
