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
        mCities.add(new City("Madrid"));
        mCities.add(new City("Jaen"));
        mCities.add(new City("Quito"));
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
