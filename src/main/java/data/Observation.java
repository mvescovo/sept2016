package data;

import java.util.Vector;

/**
 * Created by michael on 5/04/16.
 *
 * Observation entity.
 */
public class Observation {

    private String mId;
    private String mName;
    private String mDateTime;
    private String mApparentTemp;
    private String mCloud;
    private String mAirtemp;
    private String mRain;
    private String mHumidity;

    public Observation(String airTemp, String name) {
        mAirtemp = airTemp;
        mName = name;
    }

    public Observation(String id, String name, String dateTime, String apparentTemp, String cloud, String airtemp, String rain, String humidity) {
        mId = id;
        mName = name;
        mDateTime = dateTime;
        mApparentTemp = apparentTemp;
        mCloud = cloud;
        mAirtemp = airtemp;
        mRain = rain;
        mHumidity = humidity;
    }

	public Vector<String> getObsVector() {
    	Vector<String> data = new Vector<String>();
    	data.addElement(getDateTime());
    	data.addElement(getApparentTemp());
    	data.addElement(getCloud());
    	data.addElement(getAirtemp());
    	data.addElement(getRain());
    	data.addElement(getHumidity());
    	
    	return data;
    }
    
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDateTime() {
        return mDateTime;
    }

    public void setDateTime(String dateTime) {
        mDateTime = dateTime;
    }

    public String getApparentTemp() {
        return mApparentTemp;
    }

    public void setApparentTemp(String apparentTemp) {
        mApparentTemp = apparentTemp;
    }

    public String getCloud() {
        return mCloud;
    }

    public void setCloud(String cloud) {
        mCloud = cloud;
    }

    public String getAirtemp() {
        return mAirtemp;
    }

    public void setAirtemp(String airtemp) {
        mAirtemp = airtemp;
    }

    public String getRain() {
        return mRain;
    }

    public void setRain(String rain) {
        mRain = rain;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }
}
