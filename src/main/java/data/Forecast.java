package data;

/**
 * Forecast entity class.
 *
 * @author michael
 */
public class Forecast {

    private String mDate;
    private String mLatitude;
    private String mLongitude;
    private String mDescription;
    private String mMinTemp;
    private String mMaxTemp;
    private String mHumidity;
    private String mPressure;
    private String mWindSpeed;

    /**
     * @param date the date of the particular forecast.
     * @param latitude latitude of the forecast.
     * @param longitude longitude of the forecast.
     * @param maxTemp maximum temperature of the forecast.
     */
    public Forecast(String date, String latitude, String longitude, String maxTemp) {
        mDate = date;
        mLatitude = latitude;
        mLongitude = longitude;
        mMaxTemp = maxTemp;
    }

    /**
     * @param date the date of the particular forecast.
     * @param latitude latitude of the forecast.
     * @param longitude longitude of the forecast.
     * @param description text description of forecast. e.g. cloudy.
     * @param minTemp minimum temperature of the forecast.
     * @param maxTemp maximum temperature of the forecast.
     * @param humidity humidity of the forecast.
     * @param pressure pressure of the forecast.
     * @param windSpeed wind speed of the forecast.
     */
    public Forecast(String date, String latitude, String longitude, String description, String minTemp, String maxTemp, String humidity, String pressure, String windSpeed) {
        mDate = date;
        mLatitude = latitude;
        mLongitude = longitude;
        mDescription = description;
        mMinTemp = minTemp;
        mMaxTemp = maxTemp;
        mHumidity = humidity;
        mPressure = pressure;
        mWindSpeed = windSpeed;
    }

    /*
    * Getters and setters
    *
    * */
    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
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

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getMinTemp() {
        return mMinTemp;
    }

    public void setMinTemp(String minTemp) {
        mMinTemp = minTemp;
    }

    public String getMaxTemp() {
        return mMaxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        mMaxTemp = maxTemp;
    }

    public String getHumidity() {
        return mHumidity;
    }

    public void setHumidity(String humidity) {
        mHumidity = humidity;
    }

    public String getPressure() {
        return mPressure;
    }

    public void setPressure(String pressure) {
        mPressure = pressure;
    }

    public String getWindSpeed() {
        return mWindSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        mWindSpeed = windSpeed;
    }
}
