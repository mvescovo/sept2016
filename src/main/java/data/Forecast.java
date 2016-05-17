package data;

/**
 * Forecast entity class.
 *
 * @author michael
 */
public class Forecast {

    private String mTime;
    private String mDescription;
    private String mTemp;
    private String mMinTemp;
    private String mMaxTemp;
    private String mHumidity;
    private String mPressure;
    private String mWindSpeed;

    /**
     * @param time the time of the particular forecast.
     * @param temp temperature of the forecast.
     */
    public Forecast(String time, String temp) {
        mTime = time;
        mTemp = temp;
    }

    /**
     * @param time the time of the particular forecast.
     * @param description text description of forecast. e.g. cloudy.
     * @param minTemp minimum temperature of the forecast.
     * @param maxTemp maximum temperature of the forecast.
     * @param humidity humidity of the forecast.
     * @param pressure pressure of the forecast.
     * @param windSpeed wind speed of the forecast.
     */
    public Forecast(String time, String description, String minTemp, String maxTemp, String humidity, String pressure, String windSpeed) {
        mTime = time;
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
    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getTemp() {
        return mTemp;
    }

    public void setTemp(String temp) {
        mTemp = temp;
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
