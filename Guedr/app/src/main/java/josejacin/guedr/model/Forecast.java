package josejacin.guedr.model;

import java.io.Serializable;

// Una clase es serializable cuando todos sus atributos de instancia son serializables
public class Forecast implements Serializable {
    // Properties
    public final static int CELSIUS = 0;
    public final static int FARENHEIT = 1;

    private float mMaxTemp;
    private float mMinTemp;
    private float mHumidity;
    private String mDescription;
    private int mIcon;

    // Constructor (Initialization)
    public Forecast(float maxTemp, float minTemp, float humidity, String description, int icon) {
        mMaxTemp = maxTemp;
        mMinTemp = minTemp;
        mHumidity = humidity;
        mDescription = description;
        mIcon = icon;
    }

    protected float toFarenheit(float celsius) {
        return (celsius * 1.8f) + 32;
    }

    // Getters & Setters
    public float getMaxTemp(int units) {
        if (units == CELSIUS) {
            return mMaxTemp;
        } else {
            return  toFarenheit(mMaxTemp);
        }
    }

    public void setMaxTemp(float maxTemp) {
        mMaxTemp = maxTemp;
    }

    public float getMinTemp(int units) {
        if (units == CELSIUS) {
            return mMinTemp;
        } else {
            return  toFarenheit(mMinTemp);
        }
    }

    public void setMinTemp(float minTemp) {
        mMinTemp = minTemp;
    }

    public float getHumidity() {
        return mHumidity;
    }

    public void setHumidity(float humidity) {
        mHumidity = humidity;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public int getIcon() {
        return mIcon;
    }

    public void setIcon(int icon) {
        mIcon = icon;
    }
}
