package data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Weather station entity containing attributes for the stations name (City),
 * state name it is located in, and the url string of weather observations for
 * this station.
 *
 * @author michael
 * @author kendall
 * @author steve
 */
public class Station implements Serializable {

	private static final long serialVersionUID = -1970346110178787000L;
	private String mUrl;
	private String mCity;
	private String mStateName;
	private HashMap<String, Observation> mObservations;

	/**
	 * Constructor for a station without a populated list of observation
	 * entities.
	 * 
	 * @param url
	 *            the URL of the stations's JSON weather observations
	 * @param city
	 *            the name of the weather station
	 * @param stateName
	 *            the name of the state the station is located in.
	 */
	public Station(String url, String city, String stateName) {
		mUrl = url;
		setmCity(city);
		this.setmStateName(stateName);
	}

	/**
	 * Constructor for a station with a populated list of observation entities.
	 * 
	 * @param url
	 *            the URL of the stations's JSON weather observations
	 * @param city
	 *            the name of the weather station
	 * @param stateName
	 *            the name of the state the station is located in.
	 * @param observations
	 *            HashMap of observation objects
	 */
	public Station(String url, String city, HashMap<String, Observation> observations, String stateName) {
		mUrl = url;
		setmCity(city);
		mObservations = observations;
		this.setmStateName(stateName);
	}

	/*
	 * Outputs the name of the weather station (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append(getmCity());
		return result.toString();
	}

	/*
	 * When compared use the station name string (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Station s = (Station) obj;
		if (s.getmCity().equals(mCity))
			return true;
		return false;
	}

	/*
	 * Getters and setters for the Station object properties.
	 */
	public String getUrl() {
		return mUrl;
	}

	public void setUrl(String url) {
		mUrl = url;
	}

	public String getCity() {
		return getmCity();
	}

	public void setCity(String city) {
		setmCity(city);
	}

	public HashMap<String, Observation> getObservations() {
		return mObservations;
	}

	public void setObservations(HashMap<String, Observation> observations) {
		mObservations = observations;
	}

	public String getmCity() {
		return mCity;
	}

	public void setmCity(String mCity) {
		this.mCity = mCity;
	}

	public String getmStateName() {
		return mStateName;
	}

	public void setmStateName(String mStateName) {
		this.mStateName = mStateName;
	}
}
