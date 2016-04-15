package data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by michael on 5/04/16.
 *
 * Observation station entity.
 */
public class Station implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1970346110178787000L;
	
	private String mUrl;
    private String mCity;
    private String mStateName;
    
    private HashMap<String, Observation> mObservations;

    
    public Station(String url, String city, String stateName) {
        mUrl = url;
        setmCity(city);
        this.setmStateName(stateName);
    }

    public Station(String url, String city, HashMap<String, Observation> observations, String stateName) {
        mUrl = url;
        setmCity(city);
        mObservations = observations;
        this.setmStateName(stateName);
    }
    
    @Override
    public String toString() {
	StringBuilder result = new StringBuilder();
	result.append(getmCity());
	return result.toString();
    }

    @Override
	public boolean equals(Object obj) {
		Station s = (Station) obj;
		if (s.getmCity().equals(mCity))
			return true;
		return false;
	}


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
