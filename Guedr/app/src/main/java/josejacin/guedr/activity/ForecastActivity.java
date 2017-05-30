package josejacin.guedr.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import josejacin.guedr.R;
import josejacin.guedr.fragment.CityListFragment;
import josejacin.guedr.model.Cities;
import josejacin.guedr.model.City;

public class ForecastActivity extends AppCompatActivity implements CityListFragment.OnCitySelectedListener {

    // Función que se llama justo antes de crearse una Actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // Formas de mostrar un Fragment
        // 1.Directamente en el layout (activity_forecast)
        // En el layout se tiene que hacer referencia al fragment
        // Con esta forma se pierde el menú y en el fragment, en el método onCreate se tiene que indicar que el fragment tiene opciones de menú
        /*
        <fragment
            android:id="@+id/forecast_fragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:name="josejacin.guedr.ForecastFragment" />
         */

        // Formas de mostrar un Fragment
        // 2.Mediante el FragmentManager
        // Se carga a mano el Fragment
        FragmentManager fm = getFragmentManager();
        // Se comprueba que el Fragment ya no se encuentra añadido a la jerarquía
        if (fm.findFragmentById(R.id.city_list_fragment) == null) {
            // No está añadido, por lo que se añade con una transacción de fragments

            // Se crea una instancia del nuevo Fragment
            Cities cities = new Cities();
            CityListFragment fragment = CityListFragment.newInstance(cities.getCities());

            // Se inicia la transacción
            fm.beginTransaction().add(R.id.city_list_fragment, fragment).commit();

            // En este punto se pueden añadir/eliminar todos los fragment que se necesiten, al finalizar se tiene que hacer commit();
            // Con .addToBackStack("Texto").commit(); el usuario puede volver al estado anterior a la transacción
        }

    }

    // Método que se ejecuta cuando se selecciona una fila de CityListFragment
    @Override
    public void onCitySelected(City city, int position) {
        // Con esto se lanza una actividad
        Intent intent = new Intent(this, CityPagerActivity.class);
        intent.putExtra(CityPagerActivity.EXTRA_CITY_INDEX, position);
        startActivity(intent);
    }
}
