package josejacin.guedr.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

import josejacin.guedr.R;
import josejacin.guedr.model.Forecast;


public class ForecastRecyclerViewAdapter extends RecyclerView.Adapter<ForecastRecyclerViewAdapter.ForecastViewHolder> {
    private LinkedList<Forecast> mForecast;
    private boolean mShowCelsius;

    // Constructor
    public ForecastRecyclerViewAdapter(LinkedList<Forecast> forecast, boolean showCelsius) {
        super();
        mForecast = forecast;
        mShowCelsius = showCelsius;
    }

    // Método que se ejecuta cuando se va a crear la vista. Es el que crea la vista
    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se le indica el lugar en el que va a estar enganchda la fila
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_forecast, parent, false);
        return new ForecastViewHolder(view);
    }

    // Método que se ejecuta cuando se solicitan los datos que van a ir en la vista
    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        holder.bindForecast(mForecast.get(position), mShowCelsius);
    }

    // Método que se ejecuta cuando se solicita saber cuántos elementos (datos) hay
    @Override
    public int getItemCount() {
        return mForecast.size();
    }

    // Clase que tiene referencias a cada uno de los datos de cada una de las vistas de cada fila
    protected class ForecastViewHolder extends RecyclerView.ViewHolder {

        private View mRoot = null;
        private final ImageView mForecastImage;
        private final TextView mMaxTempText;
        private final TextView mMinTempText;
        private final TextView mHumidityText;
        private final TextView mForecastDescriptionText;

        // Constructor
        public ForecastViewHolder(View itemView) {
            super(itemView);

            // Se acceden las vistas de la interfaz para poder ir dándole valor
            mForecastImage = (ImageView) itemView.findViewById(R.id.forecast_image);
            mMaxTempText = (TextView) itemView.findViewById(R.id.max_temp);
            mMinTempText = (TextView) itemView.findViewById(R.id.min_temp);
            mHumidityText = (TextView) itemView.findViewById(R.id.humidity);
            mForecastDescriptionText = (TextView) itemView.findViewById(R.id.forecast_description);
        }

        public void bindForecast(Forecast forecast, boolean showCelsius) {
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
}
