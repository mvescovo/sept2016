package data;

import java.util.HashMap;

/**
 * Created by michael on 5/04/16.
 *
 * Observation station entity.
 */
public class Station {

    private String mUrl;
    private String mCity;
    private HashMap<String, Observation> mObservations;

    public Station(String url, String city) {
        mUrl = url;
        setmCity(city);
    }

    public Station(String url, String city, HashMap<String, Observation> observations) {
        mUrl = url;
        setmCity(city);
        mObservations = observations;
    }
    
    @Override
    public String toString() {
	StringBuilder result = new StringBuilder();
	result.append(getmCity());
	return result.toString();
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
}
