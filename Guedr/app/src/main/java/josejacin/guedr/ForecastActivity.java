package josejacin.guedr;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class ForecastActivity extends AppCompatActivity {
    // Para coger el nombre de la clase
    protected static String TAG = ForecastActivity.class.getCanonicalName();
    private static final int ID_OPCION_1 = 1;
    private static final int ID_OPCION_2 = 2;

    // Función que se llama justo antes de crearse una Actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // Se crea un modelo
        Forecast forecast = new Forecast(25, 10, 35, "Soleado con alguna nube", R.drawable.ico_01);
        setForecast(forecast);
    }

    private void setForecast(Forecast forecast) {
        // Se acceden las vistas de la interfaz para poder ir dándole valor
        ImageView forecastImage = (ImageView) findViewById(R.id.forecast_image);
        TextView maxTemp = (TextView) findViewById(R.id.max_temp);
        TextView minTemp = (TextView) findViewById(R.id.min_temp);
        TextView humidity = (TextView) findViewById(R.id.humidity);
        TextView forecastDescription = (TextView) findViewById(R.id.forecast_description);

        // Se actualiza la vista con el modelo
        forecastImage.setImageResource(forecast.getIcon());
        maxTemp.setText(getString(R.string.max_temp_format, forecast.getMaxTemp()));
        minTemp.setText(getString(R.string.min_temp_format, forecast.getMinTemp()));
        humidity.setText(getString(R.string.humidity_format, forecast.getHumidity()));
        forecastDescription.setText(forecast.getDescription());
    }

    // Método que indica que se tiene que crear un menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Los inflater inflan ficheros xml de cualquier tipo, pero cada inflater "infla" un tipo de xml
        // Menu recogido de un xml
        // Lugar en el que se debe colgar el menú representado en el xml
        getMenuInflater().inflate(R.menu.menu_settings, menu);

        return true;
    }

    // Método que se ejecuta cuando se selecciona un item del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superReturn = super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.menu_show_settings) {
            Log.v(TAG, "Se ha pulsado la opción de ajustes");
            return true;
        }

        // Si ninguna de las opciones pulsadas es la que se quiere, se retorna exactamente lo que se ha recibido
        return superReturn;
    }
}
