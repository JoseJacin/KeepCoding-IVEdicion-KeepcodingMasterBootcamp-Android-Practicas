package josejacin.guedr.model;

import java.io.Serializable;

// Una clase es serializable cuando todos sus atributos de instancia son serializables
public class City implements Serializable {
    private String mName;
    private Forecast mForecast;

    public City(String name, Forecast forecast) {
        mName = name;
        mForecast = forecast;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Forecast getForecast() {
        return mForecast;
    }

    public void setForecast(Forecast forecast) {
        mForecast = forecast;
    }
}
