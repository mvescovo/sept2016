package observations;

import application.Main;
import data.Observation;
import data.Station;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

/**
 * Created by michael on 5/04/16.
 *
 * UI for the observations.
 */
public class ObservationsView implements ObservationsContract.View, ActionListener {
    // TODO Kendall to modify as necessary - delete comment when done.

    private ObservationsContract.UserActionsListener mActionsListener;
    private JProgressBar mJProgressBar;
    private JPanel mTablePanel;
    private JPanel mChartPanel;
    private JScrollPane tableScrollPane;

    public ObservationsView() {
        // Add a progress bar
        mJProgressBar = new JProgressBar();
        mJProgressBar.setIndeterminate(true);
        mJProgressBar.setVisible(false);

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.CENTER;
        Main.MainWindow.getInstance().getObservationsPanel().add(mJProgressBar, cons);

        // Add table panel
        mTablePanel = new JPanel();
        mTablePanel.setPreferredSize(new Dimension(500,350));
        JLabel tableLabel = new JLabel("Table Panel");
        mTablePanel.add(tableLabel);
        cons.gridy = 1;
        cons.weightx = 1;
        cons.weighty = 1;
        Main.MainWindow.getInstance().getObservationsPanel().add(mTablePanel, cons);
       
        tableScrollPane = new JScrollPane();

        mTablePanel.add(tableScrollPane);

        // Add chart panel
        mChartPanel = new JPanel();
        mChartPanel.setPreferredSize(new Dimension(500,350));
        mChartPanel.setBorder(new LineBorder(Color.red));
        JLabel chartLabel = new JLabel("Chart Panel");
        mChartPanel.add(chartLabel);
        cons.gridy = 2;
        cons.weightx = 1;
        cons.weighty = 1;
        Main.MainWindow.getInstance().getObservationsPanel().add(mChartPanel, cons);
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
    	String[] columnNames = { "Date Time",
    			"Apparent temperature",
    			"Cloud",
    			"Air temperature",
    			"Rain",
    			"Humidity" };
    	JTable table = new JTable();
    	DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);


    	for(Observation obs : observations) {
    		dataModel.addRow(obs.getObsVector());
    		System.out.println(obs.getObsVector());
    	}
    	
    	table.setModel(dataModel);
    	tableScrollPane.setViewportView(table);
        Main.MainWindow.getInstance().getStationName().setText(observations.get(0).getName());


    }

    public void showChart() {

    }

    public void actionPerformed(ActionEvent e) {

    }

	public JScrollPane getTableScrollPane() {
		return tableScrollPane;
	}

	public void setTableScrollPane(JScrollPane tableScrollPane) {
		this.tableScrollPane = tableScrollPane;
	}
}
