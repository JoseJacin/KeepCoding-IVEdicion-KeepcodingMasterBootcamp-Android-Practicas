package josejacin.guedr.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import josejacin.guedr.R;
import josejacin.guedr.fragment.CityListFragment;
import josejacin.guedr.fragment.CityPagerFragment;
import josejacin.guedr.model.Cities;
import josejacin.guedr.model.City;

public class ForecastActivity extends AppCompatActivity implements CityListFragment.OnCitySelectedListener {

    // Función que se llama justo antes de crearse una Actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Chuleta para saber detalles del dispositivo real que se está ejecutando en esta aplicación
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        int dpWidth = (int) (width / metrics.density);
        int dpHeight = (int) (height / metrics.density);
        String model = Build.MODEL;
        int dpi = metrics.densityDpi;


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

        // Se averigua qué interfaz se ha cargado
        // Eso se averigua consultando si en la intezfaz hay un FrameLayout concreto
        if (findViewById(R.id.city_list_fragment) != null) {
            // Se ha cargado una interfaz que tiene el hueco para el fragment CityListFragment

            // Se comprueba que el Fragment ya no se encuentra añadido a la jerarquía
            if (fm.findFragmentById(R.id.city_list_fragment) == null) {
                // No está añadido, por lo que se añade con una transacción de fragments

                // Se crea una instancia del nuevo Fragment
                Cities cities = Cities.getmInstance();
                CityListFragment fragment = CityListFragment.newInstance(cities.getCities());

                // Se inicia la transacción
                fm.beginTransaction()
                        .add(R.id.city_list_fragment, fragment)
                        .commit();

                // En este punto se pueden añadir/eliminar todos los fragment que se necesiten, al finalizar se tiene que hacer commit();
                // Con .addToBackStack("Texto").commit(); el usuario puede volver al estado anterior a la transacción
            }
        }

        if (findViewById(R.id.view_pager_fragment) != null) {
            // Se ha cargado una interfaz que tiene el hueco para el fragment CityPagerFragment
            // Se comprueba primero que no está ya añadido el fragment
            if (fm.findFragmentById(R.id.view_pager_fragment) == null) {
                fm.beginTransaction()
                        .add(R.id.view_pager_fragment, CityPagerFragment.newInstance(0))
                        .commit();
            }
        }
    }

    // Método que se ejecuta cuando se selecciona una fila de CityListFragment
    @Override
    public void onCitySelected(City city, int position) {
        // Se comprueba qué fragments se tienen cargados en la interfaz
        FragmentManager fm = getFragmentManager();
        CityPagerFragment cityPagerFragment = (CityPagerFragment) fm.findFragmentById(R.id.view_pager_fragment);

        if (cityPagerFragment != null) {
            // Tenemos un pager, vamos a ver cómo podemos decirle que cambie de ciudad
            cityPagerFragment.moveToCity(position);
        } else {
            // No tenemos un pager, lanzamos la actividad CityPagerActivity
            Intent intent = new Intent(this, CityPagerActivity.class);
            intent.putExtra(CityPagerActivity.EXTRA_CITY_INDEX, position);
            startActivity(intent);
        }
    }
}
