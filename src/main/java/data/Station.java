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
	private String mState;
	private String mLatitude;
	private String mLongitude;
	private HashMap<String, Observation> mObservations;

	/**
	 * Constructor for a station without a populated list of observation
	 * entities.
	 * 
	 * @param url
	 *            the URL of the stations's JSON weather observations
	 * @param city
	 *            the name of the weather station
	 * @param state
	 *            the name of the state the station is located in.
	 */
	public Station(String url, String city, String state) {
		setUrl(url);
		setCity(city);
		setState(state);
	}

    public Station(String url, String city, String state, String latitude, String longitude) {
        setUrl(url);
        setCity(city);
        setState(state);
        setLatitude(latitude);
        setLongitude(longitude);
    }

	/**
	 * Constructor for a station with a populated list of observation entities.
	 * 
	 * @param url
	 *            the URL of the stations's JSON weather observations
	 * @param city
	 *            the name of the weather station
	 * @param state
	 *            the name of the state the station is located in.
	 * @param observations
	 *            HashMap of observation objects
	 */
	public Station(String url, String city, String state, HashMap<String, Observation> observations) {
		setUrl(url);
		setCity(city);
        setState(state);
		setObservations(observations);
	}

	/*
	 * Outputs the name of the weather station (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
        return getCity();
	}

	/*
	 * When compared use the station name string (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Station s = (Station) obj;
		if (s.getCity().equals(mCity))
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
		return mCity;
	}

	public void setCity(String city) {
        mCity = city;
	}

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public HashMap<String, Observation> getObservations() {
		return mObservations;
	}

	public void setObservations(HashMap<String, Observation> observations) {
		mObservations = observations;
	}

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }
}
