package data;

/**
 * Created by michael on 5/04/16.
 *
 * Weather entity.
 */
public class Weather {

    private int mId;
    private String mMax;
    private String mMin;
    private String m9am;
    private String m3pm;
    private String date;

    public Weather(int id, String max, String min, String nineam, String threepm) {
        mId = id;
        mMax = max;
        mMin = min;
        m9am = nineam;
        m3pm = threepm;
    }

    public int getId() {
        return mId;
    }

    public void setId(String id) {
        mId = mId;
    }

    public String getmMax() {
        return mMax;
    }

    public void setmMax(String mMax) {
        this.mMax = mMax;
    }

    public String getmMin() {
        return mMin;
    }

    public void setmMin(String mMin) {
        this.mMin = mMin;
    }

    public String getM9am() {
        return m9am;
    }

    public void setM9am(String m9am) {
        this.m9am = m9am;
    }

    public String getM3pm() {
        return m3pm;
    }

    public void setM3pm(String m3pm) {
        this.m3pm = m3pm;
    }
}
