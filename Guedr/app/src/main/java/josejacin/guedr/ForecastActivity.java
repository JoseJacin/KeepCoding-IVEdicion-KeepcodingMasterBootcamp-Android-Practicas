package josejacin.guedr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class ForecastActivity extends AppCompatActivity {

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


    }
}
