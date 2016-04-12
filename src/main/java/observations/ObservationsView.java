package observations;

import application.Main;
import data.Observation;
import data.Station;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.HorizontalAlignment;

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
        mTablePanel.setLayout(new GridBagLayout());
        GridBagConstraints tableCons = new GridBagConstraints();
        tableCons.gridx = 0;
        tableCons.gridy = 0;
        tableCons.weighty = 0;
        tableCons.fill = GridBagConstraints.HORIZONTAL;
        tableCons.anchor = GridBagConstraints.NORTHWEST;
        JLabel tableLabel = new JLabel("Table Panel");
        mTablePanel.add(tableLabel, tableCons);
        cons.gridy = 1;
        cons.weightx = 1;
        cons.weighty = 0.5;
        Main.MainWindow.getInstance().getObservationsPanel().add(mTablePanel, cons);
       
        tableScrollPane = new JScrollPane();
        tableCons.gridy = 1;
        tableCons.weighty = 1;
        tableCons.weightx = 1;
        tableCons.fill = GridBagConstraints.BOTH;
        mTablePanel.add(tableScrollPane, tableCons);

        // Add chart panel
        mChartPanel = new JPanel();
        mChartPanel.setLayout(new BorderLayout());
        JLabel chartLabel = new JLabel("Chart Panel");
        mChartPanel.add(chartLabel, BorderLayout.NORTH);       
        
        cons.gridy = 2;
        cons.weightx = 1;
        cons.weighty = 0.5;
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
    	
    	// Table settings
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
    	}
    	
    	table.setModel(dataModel);
    	tableScrollPane.setViewportView(table);
    	
    	// Update Station name
        Main.MainWindow.getInstance().getStationName().setText(observations.get(0).getName());


    }

    public void showChart(List<Observation> observations) {

    	String chtTitle = observations.get(0).getName() + " - Temperature observations";

    	String chtXAxisLabel = "Date and time";
        String chtYAxisLabel = "Temperature";
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        double temp;
        for(Observation obs : observations) {
        	try {
        		temp = Double.parseDouble(obs.getAirtemp());
        	} catch (NumberFormatException e) {
        	   temp = 0.0;
        	}
        	dataset.setValue(temp, chtYAxisLabel, obs.getDateTime());
        }
        JFreeChart chart = ChartFactory.createBarChart(chtTitle, chtXAxisLabel, chtYAxisLabel, dataset);
        chart.getTitle().setHorizontalAlignment(HorizontalAlignment.LEFT);
        chart.getTitle().setFont(Main.getFontnormalbold());
        ChartPanel chartPanel = new ChartPanel(chart);

        mChartPanel.add(chartPanel, BorderLayout.CENTER);
        Main.MainWindow.getInstance().getObservationsPanel().revalidate();
        Main.MainWindow.getInstance().getObservationsPanel().repaint();
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
