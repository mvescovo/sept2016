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
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.HorizontalAlignment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        cons.gridwidth = 2;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.CENTER;
        Main.MainWindow.getInstance().getObservationsPanel().add(mJProgressBar, cons);

        // Add table panel
        mTablePanel = new JPanel();
        mTablePanel.setLayout(new GridBagLayout());
        mTablePanel.setBackground(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
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
        cons.gridwidth = 2;
        cons.insets = new Insets(10, 10, 10, 10);
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
        mChartPanel.setBackground(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
        
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
        	Main.MainWindow.getInstance().getStationName().setVisible(false);
            mJProgressBar.setVisible(true);
        } else {
            mJProgressBar.setVisible(false);
            Main.MainWindow.getInstance().getStationName().setVisible(false);
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
    			"Temp C" + Main.getSymboldegree(),
    			"App Temp C" + Main.getSymboldegree(),
    			"Dew point",
    			"Humidity %",
    			"Delta-T C" + Main.getSymboldegree(),
    			"Wind Dir",
    			"Wind spd kmh",
    			"Wind gust kmh",
    			"Wind spd kts",
    			"Wind gust kts",
    			"Press QNH hPa",
    			"Press MSL hPa",
    			"Rain since 9am, mm"};
    	
    	JTable table = new JTable();
    	DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

    	for(Observation obs : observations) {
    		dataModel.addRow(obs.getObsVector());
    	}
    	
    	table.setModel(dataModel);
    	tableScrollPane.setViewportView(table);
    	
    	// Update Station name
    	String stationTitle = observations.get(0).getmName() + " - State name";
        Main.MainWindow.getInstance().getStationName().setText(stationTitle);

        
        // set component visibilities
        Main.MainWindow.getInstance().getStationName().setVisible(true);
        Main.MainWindow.getInstance().getBtnFavourite().setVisible(true);
        Main.MainWindow.getInstance().getMenubar().getMenu(1).getItem(0).setEnabled(true);

    }

    public void showChart(List<Observation> observations) {

    	String chtTitle = observations.get(0).getmName() + " - Temperature observations";

    	String chtXAxisLabel = "Date and time";
        String chtYAxisLabel = "Temperature " + Main.getSymboldegree() + "C";

        TimeSeries series = new TimeSeries("Temp");
        double temp = 0.0;
    	SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");

    	
        for(Observation obs : observations) {
        	try {
        		temp = Double.parseDouble(obs.getmAirtemp());
        	} catch (NumberFormatException e) {
        	   temp = 0.0;
        	}


        	Date myDate = null;
			try {
				myDate = standardDateFormat.parse(obs.getmDateTime());
	        	series.addOrUpdate(new Hour(myDate), temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}

        }
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        dataset.addSeries(series);
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(chtTitle, chtXAxisLabel, chtYAxisLabel, dataset, false, true, false);
        chart.getTitle().setHorizontalAlignment(HorizontalAlignment.LEFT);
        chart.getTitle().setFont(Main.getFontnormalbold());
        
        chart.setBackgroundPaint(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
        chart.getXYPlot().getDomainAxis().setLabelFont(Main.getFontsmall());
        chart.getXYPlot().getRangeAxis().setLabelFont(Main.getFontsmall());
        
        
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
