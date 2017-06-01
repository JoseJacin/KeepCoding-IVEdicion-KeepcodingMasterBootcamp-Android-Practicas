package josejacin.guedr.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import josejacin.guedr.R;
import josejacin.guedr.model.Forecast;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_FORECAST = "EXTRA_FORECAST";
    public static final String EXTRA_SHOW_CELSIUS = "EXTRA_SHOW_CELSIUS";

    private ImageView mForecastImage;
    private TextView mMaxTempText;
    private TextView mMinTempText;
    private TextView mHumidityText;
    private TextView mForecastDescriptionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Se acceden a los datos de entrada de la actividad anterior
        Forecast forecast = (Forecast) getIntent().getSerializableExtra(EXTRA_FORECAST);
        boolean showCelsius = getIntent().getBooleanExtra(EXTRA_SHOW_CELSIUS, true);

        // Se acceden las vistas de la interfaz para poder ir dándole valor
        mForecastImage = (ImageView) findViewById(R.id.forecast_image);
        mMaxTempText = (TextView) findViewById(R.id.max_temp);
        mMinTempText = (TextView) findViewById(R.id.min_temp);
        mHumidityText = (TextView) findViewById(R.id.humidity);
        mForecastDescriptionText = (TextView) findViewById(R.id.forecast_description);

        // Se caculan las temperaturas en función de las unidades
        float maxTemp = 0; // Por defecto está en celsius
        float minTemp = 0;
        String unitsToShow = null;
        if (showCelsius) {
            maxTemp = forecast.getMaxTemp(Forecast.CELSIUS);
            minTemp = forecast.getMinTemp(Forecast.CELSIUS);
            unitsToShow = "ºC";
        } else {
            maxTemp = forecast.getMaxTemp(Forecast.FARENHEIT);
            minTemp = forecast.getMinTemp(Forecast.FARENHEIT);
            unitsToShow = "F";
        }

        // Se accede al contexto
        Context context = mForecastImage.getContext();

        // Se actualiza la vista con el modelo
        mForecastImage.setImageResource(forecast.getIcon());
        mMaxTempText.setText(context.getString(R.string.max_temp_format, maxTemp, unitsToShow));
        mMinTempText.setText(context.getString(R.string.min_temp_format, minTemp, unitsToShow));
        mHumidityText.setText(context.getString(R.string.humidity_format, forecast.getHumidity()));
        mForecastDescriptionText.setText(forecast.getDescription());
    }
}