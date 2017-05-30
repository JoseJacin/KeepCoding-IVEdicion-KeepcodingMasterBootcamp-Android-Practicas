package josejacin.guedr.model;

import java.util.LinkedList;
import java.util.List;

import josejacin.guedr.R;

public class Cities {
    private static Cities mInstance;

    private LinkedList<City> mCities;

    public static Cities getmInstance() {
        if (mInstance == null) {
            // No existe aún una instancia estática de la clase, la creo
            mInstance = new Cities();
        }

        return  mInstance;
    }

    public Cities() {
        mCities = new LinkedList<>();
        mCities.add(new City("Madrid", new Forecast(25, 10, 35, "Soleado con alguna nube", R.drawable.ico_02)));
        mCities.add(new City("Jaen", new Forecast(36, 25, 35, "Sol a tope", R.drawable.ico_01)));
        mCities.add(new City("Quito", new Forecast(30, 15, 40, "Arcoriris", R.drawable.ico_10)));
    }

    // Método que retorna la ciudad indicada por index
    public City getCity(int index) {
        return mCities.get(index);
    }

    public LinkedList<City> getCities() {
        return mCities;
    }

    // Método que retorna el número de ciudades que hay
    public int getCount() {
        return mCities.size();
    }
}
