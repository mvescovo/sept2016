package observations;

import application.Main;
import data.Observation;
import data.Station;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.StandardXYItemLabelGenerator;
import org.jfree.chart.labels.XYItemLabelGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.HorizontalAlignment;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * Created by michael on 5/04/16.
 *
 * User interface for the display of weather observation data for the selected
 * Station.
 */
public class ObservationsView implements ObservationsContract.View, ActionListener {

	private ObservationsContract.UserActionsListener mActionsListener;
	private JProgressBar mJProgressBar;
	private JPanel mTablePanel;
	private JPanel mChartPanel;
	private JPanel mHeadPanel;
	private JScrollPane mTableScrollPane;
    private Station mStation;

	/**
	 * Instantiates the view and adds, a progress bar, header panel (for the
	 * title and latest weather data), and table panel and a chart of
	 * temperature data.
	 */
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

		// add header panel
		createHeaderPanel();

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
		cons.fill = GridBagConstraints.BOTH;
		cons.anchor = GridBagConstraints.CENTER;
		Main.MainWindow.getInstance().getObservationsPanel().add(mTablePanel, cons);

		mTableScrollPane = new JScrollPane();
		tableCons.gridy = 1;
		tableCons.weighty = 1;
		tableCons.weightx = 1;
		tableCons.fill = GridBagConstraints.BOTH;
		mTablePanel.add(mTableScrollPane, tableCons);

		// Add chart panel
		mChartPanel = new JPanel();
		mChartPanel.setLayout(new BorderLayout());
		mChartPanel.setBackground(Main.MainWindow.getInstance().getObservationsPanel().getBackground());

		cons.gridy = 2;
		cons.weightx = 1;
		cons.weighty = 0.5;
		Main.MainWindow.getInstance().getObservationsPanel().add(mChartPanel, cons);

		// Set refresh button listener
		Main.MainWindow.getInstance().getBtnRefresh().addActionListener(this);
	}

	/**
	 * Displays and hides the progress bar used while waiting for data
	 * 
	 * @param active
	 *            true displays the progress bar and false hides it.
	 */
    @Override
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

	/**
	 * When the view has finished initalising load the observations from the
	 * presenter.
	 * 
	 * @param station
	 *            the selected weather station
	 */
    @Override
	public void onReady(Station station) {
        mStation = station;
		mActionsListener.loadObservations(mStation, false);
	}

	/**
	 * Creates a header panel containing the selection station details and a
	 * summary of the latest weather observation for that station.
	 * 
	 * @param obs
	 *            a collection of observations for a weather station.
	 */
    @Override
	public void showLatestObservation(Observation obs) {
		// Update Station name
		String stationTitle = obs.getmName() + " - " + obs.getmStateName();

		mHeadPanel.setLayout(new GridBagLayout());
		mHeadPanel.setBackground(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
		GridBagConstraints headCons = new GridBagConstraints();
		headCons.gridx = 0;
		headCons.gridy = 0;
		headCons.weighty = 0;
		headCons.weightx = 1;
		headCons.gridwidth = 2;
		headCons.fill = GridBagConstraints.HORIZONTAL;
		headCons.anchor = GridBagConstraints.WEST;

		JLabel title = new JLabel();
		title.setText(stationTitle);
		title.setFont(Main.getFonttitle());
		mHeadPanel.add(title, headCons);

		JLabel lblLatest = new JLabel();
		lblLatest.setText("Latest weather observation:");
		lblLatest.setFont(Main.getFontnormalbold());
		headCons.gridy = 1;
		headCons.weightx = 1;
		headCons.gridwidth = 1;
		mHeadPanel.add(lblLatest, headCons);

		JLabel lblLatestDate = new JLabel();
		SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		Date thisDate = null;
		try {
			thisDate = standardDateFormat.parse(obs.getmDateTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		c.setTime(thisDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a EE dd/MM/yy");

		lblLatestDate.setText(dateFormat.format(c.getTime()));
		lblLatestDate.setFont(Main.getFontnormal());
		headCons.gridy = 2;
		headCons.weightx = 0;
		mHeadPanel.add(lblLatestDate, headCons);

		JLabel lblTemp = new JLabel();
		lblTemp.setText(obs.getmAirtemp() + Main.getSymboldegree() + " C");
		lblTemp.setFont(Main.getFonttitle());
		headCons.gridx = 1;
		headCons.gridy = 2;
		headCons.weightx = 0;
		headCons.weighty = 1;
		headCons.gridheight = 2;
		headCons.anchor = GridBagConstraints.EAST;
		mHeadPanel.add(lblTemp, headCons);

		JLabel lblCloud = new JLabel();
		lblCloud.setText(obs.getmCloud() + ", " + obs.getmHumidity() + "% humidity. Wind - " + obs.getmWindDir() + " "
				+ obs.getmWindSpdKmh() + "kph.");
		lblCloud.setFont(Main.getFontnormal());
		headCons.gridy = 3;
		headCons.weightx = 1;
		headCons.gridx = 0;
		headCons.gridheight = 1;
		headCons.anchor = GridBagConstraints.WEST;
		mHeadPanel.add(lblCloud, headCons);

	}

	/**
	 * Create a table to display all observations and attributes and add it to
	 * the table scroll pane.
	 * 
	 * @param observations
	 *            a collection of observations for a weather station.
	 */
    @Override
	public void showObservationTable(List<Observation> observations) {

		// Table settings
		String[] columnNames = { "Date Time", "Temp C" + Main.getSymboldegree(), "App Temp C" + Main.getSymboldegree(),
				"Dew point", "Humidity %", "Delta-T C" + Main.getSymboldegree(), "Wind Dir", "Wind spd kmh",
				"Wind gust kmh", "Wind spd kts", "Wind gust kts", "Press QNH hPa", "Press MSL hPa",
				"Rain since 9am, mm" };

		JTable table = new JTable();
		DefaultTableModel dataModel = new DefaultTableModel(columnNames, 0);

		for (Observation obs : observations) {
			dataModel.addRow(obs.getObsVector());
		}

		table.setModel(dataModel);
		table.setFont(Main.getFontsmall());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumn("Date Time").setPreferredWidth(160);
		mTableScrollPane.setViewportView(table);

		// set component visibilities
		// Main.MainWindow.getInstance().getToolbar().get.setVisible(true);
		Main.MainWindow.getInstance().getMenubar().getMenu(1).getItem(0).setEnabled(true);

	}

	/**
	 * Creates and displays a graph of temperatures for the selected weather
	 * station. Uses JFreeChart to build the image.
	 * 
	 * @param observations
	 *            a collection of observations for a weather station.
	 */
    @Override
	public void showChart(List<Observation> observations) {
		System.out.println(observations.get(0).getmDateTime());
		String chtTitle = observations.get(0).getmName() + " - Temperature observations";

		String chtXAxisLabel = "Date and time";
		String chtYAxisLabel = "Temperature " + Main.getSymboldegree() + "C";

		TimeSeries seriesTemp = new TimeSeries("Temp", Minute.class);
		TimeSeries seriesMin = new TimeSeries("Min", Minute.class);
		TimeSeries seriesMax = new TimeSeries("Max", Minute.class);
		TimeSeries series9am = new TimeSeries("9am", Hour.class);
		TimeSeries series3pm = new TimeSeries("3pm", Hour.class);
		
		double temp = Double.NaN;
		double minTemp = Double.NaN;
		double maxTemp = Double.NaN;
		List<String> dates = new ArrayList<String>();
		SimpleDateFormat standardDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
		
		String previousDay = null;
		Date minDate = null;
		Date maxDate = null;
		for (Observation obs : observations) {
			if(dates.contains(obs.getmDateTime())) {
				continue;
			}
			dates.add(obs.getmDateTime());
			// format the temperature
			try {
				temp = Double.parseDouble(obs.getmAirtemp());
			} catch (NumberFormatException e) {
				temp = 0.0;
			}

			// format the date
			Date myDate = null;
			Date myHour = null;
			String day = obs.getmDateTime().substring(0, 8);

			try {
				myDate = standardDateFormat.parse(obs.getmDateTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}

			// Check for 9am observations
			Calendar myCal = Calendar.getInstance();
			myCal.setTime(myDate);
			int hour = myCal.get(Calendar.HOUR_OF_DAY);
			if(hour == 9 ) {
				series9am.addOrUpdate(new Hour(myDate), temp);
			} else if (hour == 15) {
				series3pm.addOrUpdate(new Hour(myDate), temp);
			}
			try {
			//System.out.println(myDate);
			seriesTemp.addOrUpdate(new Minute(myDate), temp);
			} catch (SeriesException e) {
				e.printStackTrace();
			}
			if (previousDay == null) {
				previousDay = day;
			}

			if (Double.isNaN(minTemp)) {
				minTemp = temp;
				minDate = myDate;
			}
			if (Double.isNaN(maxTemp)) {
				maxTemp = temp;
				maxDate = myDate;
			}
			
			if (day.equals(previousDay)) {
				if (temp < minTemp) {
					minTemp = temp;
					minDate = myDate;
				}
				if (temp > maxTemp) {
					maxTemp = temp;
					maxDate = myDate;
				}

			} else {
				seriesMin.addOrUpdate(new Minute(minDate), minTemp);
				seriesMax.addOrUpdate(new Minute(maxDate), maxTemp);
				minDate = null;
				maxDate = null;
				minTemp = Double.NaN;
				maxTemp = Double.NaN;
			}
			previousDay = day;
			
		}


		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(seriesTemp);
		dataset.addSeries(seriesMin);
		dataset.addSeries(seriesMax);
		dataset.addSeries(series9am);
		dataset.addSeries(series3pm);

		JFreeChart chart = ChartFactory.createTimeSeriesChart(chtTitle, chtXAxisLabel, chtYAxisLabel, dataset, true,
				true, false);

		
		XYPlot plot = (XYPlot) chart.getPlot();
		
		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			for(int i = 1; i < 5; i++) {
				renderer.setSeriesShapesVisible(i, true);
				renderer.setSeriesShapesFilled(i, true);
				renderer.setSeriesLinesVisible(i, false);
			}

			NumberFormat format = NumberFormat.getNumberInstance();
			format.setMaximumFractionDigits(2);
			XYItemLabelGenerator generator =
			    new StandardXYItemLabelGenerator("{0} {2}", format, format);
			renderer.setBaseItemLabelGenerator(generator);
			renderer.setBaseItemLabelsVisible(true);
			renderer.setSeriesItemLabelsVisible(0, false);
			renderer.setSeriesItemLabelsVisible(1, false);
			renderer.setSeriesItemLabelsVisible(2, false);
			
			renderer.setSeriesPaint(3, Main.getColordark());
		}

		chart.getTitle().setHorizontalAlignment(HorizontalAlignment.LEFT);
		chart.getTitle().setFont(Main.getFontnormalbold());

		chart.setBackgroundPaint(Main.MainWindow.getInstance().getObservationsPanel().getBackground());
		chart.getXYPlot().getDomainAxis().setLabelFont(Main.getFontsmall());
		chart.getXYPlot().getRangeAxis().setLabelFont(Main.getFontsmall());

		 DateAxis axis = (DateAxis) plot.getDomainAxis();
		 axis.setDateFormatOverride(new SimpleDateFormat("hh:mm a dd-MM-yy"));
		
		ChartPanel chartPanel = new ChartPanel(chart);

		mChartPanel.add(chartPanel, BorderLayout.CENTER);
		Main.MainWindow.getInstance().getObservationsPanel().revalidate();
		Main.MainWindow.getInstance().getObservationsPanel().repaint();
	}

	/*
	 * Setter for this view's action listener.
	 */
    @Override
	public void setActionListener(ObservationsContract.UserActionsListener actionListener) {
		mActionsListener = actionListener;
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton btn = (JButton) e.getSource();
            if (btn.getName().equals("refresh")) {
                recreateHeadPanel();
                mActionsListener.loadObservations(mStation, true);
            }
        }
    }

	/*
	 * Getters and setters for the table scroll pane.
	 */
	private JScrollPane getTableScrollPane() {
		return mTableScrollPane;
	}

	private void setTableScrollPane(JScrollPane tableScrollPane) {
		this.mTableScrollPane = tableScrollPane;
	}

    // Refresh needs to recreate this. Otherwise it overwrites.
    private void recreateHeadPanel() {
        if (mHeadPanel != null) {
            Main.MainWindow.getInstance().getObservationsPanel().remove(mHeadPanel);
            createHeaderPanel();
        }
    }

    private void createHeaderPanel() {
        // add header panel
        mHeadPanel = new JPanel();
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.anchor = GridBagConstraints.NORTHWEST;
        cons.insets = new Insets(10, 10, 10, 10);
        Main.MainWindow.getInstance().getObservationsPanel().add(mHeadPanel, cons);
    }

}
