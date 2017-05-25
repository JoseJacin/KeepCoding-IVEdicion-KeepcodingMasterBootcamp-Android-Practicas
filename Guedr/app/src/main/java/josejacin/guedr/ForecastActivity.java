package josejacin.guedr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ForecastActivity extends AppCompatActivity {
    // Para coger el nombre de la clase
    protected static String TAG = ForecastActivity.class.getCanonicalName();
    private static final int REQUEST_UNITS = 1;
    protected boolean mShowCelsius = true;
    public static final String PREFERENCE_SHOW_CELSIUS = "showCelsius";

    private Forecast mForecast;
    // Función que se llama justo antes de crearse una Actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        // Se crea un modelo
        mForecast = new Forecast(25, 10, 35, "Soleado con alguna nube", R.drawable.ico_01);

        // Se recupera el valor que se ha guardado en disco para mShowCelsius
        mShowCelsius = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(PREFERENCE_SHOW_CELSIUS, true);

        updateForecast();
    }

    private void updateForecast() {
        // Se acceden las vistas de la interfaz para poder ir dándole valor
        ImageView forecastImage = (ImageView) findViewById(R.id.forecast_image);
        TextView maxTempText = (TextView) findViewById(R.id.max_temp);
        TextView minTempText = (TextView) findViewById(R.id.min_temp);
        TextView humidityText = (TextView) findViewById(R.id.humidity);
        TextView forecastDescriptionText = (TextView) findViewById(R.id.forecast_description);

        // Se caculan las temperaturas en función de las unidades
        float maxTemp = 0; // Por defecto está en celsius
        float minTemp = 0;
        String unitsToShow = null;
        if (mShowCelsius) {
            maxTemp = mForecast.getMaxTemp(Forecast.CELSIUS);
            minTemp = mForecast.getMinTemp(Forecast.CELSIUS);
            unitsToShow = "ºC";
        } else {
            maxTemp = mForecast.getMaxTemp(Forecast.FARENHEIT);
            minTemp = mForecast.getMinTemp(Forecast.FARENHEIT);
            unitsToShow = "F";
        }

        // Se actualiza la vista con el modelo
        forecastImage.setImageResource(mForecast.getIcon());
        maxTempText.setText(getString(R.string.max_temp_format, maxTemp, unitsToShow));
        minTempText.setText(getString(R.string.min_temp_format, minTemp, unitsToShow));
        humidityText.setText(getString(R.string.humidity_format, mForecast.getHumidity()));
        forecastDescriptionText.setText(mForecast.getDescription());
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

            // Intent para lanzar una segunda pantalla. Intent explícito (explícitamente se indica la clase que se quiere lanzar)
            // Se le indica el contexto desde el que se va a lanzar la Actividad. En este caso this
            // Se le indica la clase de la Actividad que se quiere lanzar
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            // Se pasan datos a la siguiente actividad: las unidades
            if (mShowCelsius) {
                settingsIntent.putExtra(SettingsActivity.EXTRA_UNITS, R.id.celsius_rb);
            } else {
                settingsIntent.putExtra(SettingsActivity.EXTRA_UNITS, R.id.farenheit_rb);
            }
            // Se lanza la actividad. Se usa para cuando la actividad destino retorna datos y se desean recibir
            // Intent de SettingsActivity
            // Se indica el tipo de pantalla de la que se va a volver
            startActivityForResult(settingsIntent, REQUEST_UNITS);
            // Se lanza la actividad. Se usa para cuando la actividad destino o no retorna datos o no se desean recibir
            //startActivity(settingsIntent);

            return true;
        }

        // Si ninguna de las opciones pulsadas es la que se quiere, se retorna exactamente lo que se ha recibido
        return superReturn;
    }

    // Método que se ejecuta cuando se vuelve de otra Actividad que se haya lanzado desde startActivityForResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UNITS) {
            // Se está volviendo de la pantalla de SettingsActivity
            // Se comprueba cómo ha ido el resultado
            if (resultCode == RESULT_OK) {
                // No ha habido errores y el usuario ha aceptado la selección de unidades
                // Se obtienen los datos del resultado
                // La opción por defecto aquí es absurda, pero se tienen que informar
                int optionSelected = data.getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.farenheit_rb);
                if (optionSelected == R.id.farenheit_rb) {
                    // Se muestra un mensaje flotante (Tostada) con la selección
                    Toast.makeText(this, "Se ha seleccionado Farenheit", Toast.LENGTH_LONG).show();
                    mShowCelsius = false;
                } else {
                    // Se muestra un mensaje flotante (Tostada) con la seleccióno
                    Toast.makeText(this, "Se ha seleccionado Celsius", Toast.LENGTH_LONG).show();
                    mShowCelsius = true;
                }

                // Se persisten las preferencias del usuario respecto a las unidades.
                // Forma larga
                //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                // Se crea un editor para poder poner las preferencias en modo "escritura"
                //SharedPreferences.Editor editor = prefs.edit();
                // Se establecen las propiedades
                // Se pasa la clave
                // Se pasa el valor
                //editor.putBoolean(PREFERENCE_SHOW_CELSIUS, mShowCelsius);
                // Se guardan los valores
                //editor.apply();

                // Se persisten las preferencias del usuario respecto a las unidades.
                // Forma corta
                PreferenceManager
                        .getDefaultSharedPreferences(this)
                        .edit()
                        .putBoolean(PREFERENCE_SHOW_CELSIUS, mShowCelsius)
                        .apply();


                // Se guarda el modelo recibido en una variable
                updateForecast();

            } else if (resultCode == RESULT_CANCELED) {
                // No se hace nada porque el usuario ha cancelado la selección de unidades
            }
        }
    }
}
