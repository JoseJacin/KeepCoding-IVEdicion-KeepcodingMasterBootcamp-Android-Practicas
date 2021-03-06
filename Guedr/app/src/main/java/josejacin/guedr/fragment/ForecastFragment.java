package josejacin.guedr.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;

import josejacin.guedr.activity.DetailActivity;
import josejacin.guedr.adapter.ForecastRecyclerViewAdapter;
import josejacin.guedr.model.City;
import josejacin.guedr.model.Forecast;
import josejacin.guedr.R;
import josejacin.guedr.activity.SettingsActivity;

public class ForecastFragment extends Fragment {
    public static final String PREFERENCE_SHOW_CELSIUS = "showCelsius";

    // Para coger el nombre de la clase
    protected static String TAG = ForecastFragment.class.getCanonicalName();

    private static final String ARG_CITY = "city";
    private static final int REQUEST_UNITS = 1;
    private static final int LOADING_VIEW_INDEX = 0;
    private static final int FORECAST_VIEW_INDEX = 1;

    protected boolean mShowCelsius = true;

    private City mCity;
    private View mRoot;
    private RecyclerView mList;

    //
    public static ForecastFragment newInstance(City city) {
        ForecastFragment fragment = new ForecastFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CITY, city);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se indica que el fragment tiene opciones de menú
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            // Se recupera el modelo pasado como argumento
            mCity = (City) getArguments().getSerializable(ARG_CITY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Layout
        // De dónde va a colgar el layout
        // Se indica si se desea que lo enganche directamente al Container. Normalmente se indica false.
        // Se utiliza un Inflater para inflar la Actividad y poder sacar la vista deseada
        mRoot = inflater.inflate(R.layout.fragment_forecast, container, false);

        // Se recupera el valor que se ha guardado en disco para mShowCelsius
        mShowCelsius = PreferenceManager.getDefaultSharedPreferences(getActivity()).getBoolean(PREFERENCE_SHOW_CELSIUS, true);

        // Se accede al RecyclerView con findViewById
        mList = (RecyclerView) mRoot.findViewById(R.id.forecast_list);
        // Se indica cómo debe visualizarse el RecyclerView (su LayoutManager)
        // Forma básica de indicar que el RecyclerView debe mostrarse como una lista
        // mList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList.setLayoutManager(new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.recycler_columns)));

        // Se indica cómo debe animarse el RecyclerView
        mList.setItemAnimator(new DefaultItemAnimator());
        // Se indica el adapter que necesita el RecyclerView
        // Esto se hará en updateForecast

        updateForecast();

        return mRoot;
    }

    private void updateForecast() {
        // Se establece el nombre de la ciudad
        //cityName.setText(mCity.getName());

        // Se accede al modelo de Forecast
        final LinkedList<Forecast> forecast = mCity.getForecast();

        // Se accede al ViewSwitcher
        final ViewSwitcher viewSwitcher = (ViewSwitcher) mRoot.findViewById(R.id.view_switcher);
        // Se establece una animación cuando entra una vista
        // Se le pasa el contexto
        // Se le pasa la ubicación de las animaciones
        viewSwitcher.setInAnimation(getActivity(), android.R.anim.fade_in); // De transparente a opaco
        // Se establece una animación cuando sale una vista
        viewSwitcher.setOutAnimation(getActivity(), android.R.anim.fade_out); // De transparente a opaco


        if (forecast == null) {
            // Ejecutar código en segundo plano
            // Primer parámetro: Parámetro de entrada
            // Segundo parámetro: Progreso de la descarga
            // Tercer parámetro: Parámetro de salida
            AsyncTask<City, Integer, LinkedList<Forecast>> weatherDownloader = new AsyncTask<City, Integer, LinkedList<Forecast>>() {
                // Método que se ejecuta antes de comenzar la operación en segundo plano (método doInBackgroud)
                // Esto se ejecuta en el hilo principal
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();

                    // Se indica a ViewSwitcher cuál es la primera interfaz (ProgressBar)
                    viewSwitcher.setDisplayedChild(LOADING_VIEW_INDEX);
                }

                // Método que se ejecuta en segundo plano. En un hilo que no es el principal
                @Override
                protected LinkedList<Forecast> doInBackground(City... params) {
                    // Con esto se establece el grado de progreso
                    //publishProgress(50);
                    return downloadForecast(params[0]);
                }

                // Método que se cuando se va a actualizar el progreso de la descarga
                // Esto se ejecuta en el hilo principal
                @Override
                protected void onProgressUpdate(Integer... values) {
                    super.onProgressUpdate(values);
                }

                // Método que se ejecuta si se cancela la petición en segundo plano
                // Esto se ejecuta en el hilo principal
                @Override
                protected void onCancelled(LinkedList<Forecast> forecast) {
                    super.onCancelled(forecast);
                    // Se indica a ViewSwitcher cuál es la segunda interfaz (Forecast)
                    viewSwitcher.setDisplayedChild(FORECAST_VIEW_INDEX);
                }

                // Método que se ejecuta cuando la descarga en segundo plano ha finalizado (método doInBackgroud)
                // Esto se ejecuta en el hilo principal
                @Override
                protected void onPostExecute(LinkedList<Forecast> forecast) {
                    super.onPostExecute(forecast);

                    // Se comprueba si ha habido errores
                    if (forecast != null) {
                        // No ha habido errores al descargar la informacón del tiempo, por lo que se actualiza la interfaz
                        mCity.setForecast(forecast);

                        updateForecast();
                    } else {
                        // Ha ocurrido algún error al descargar la información del tiempo, por lo que se muestra un AlertDialog
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                        // Se establece el texto del AlertDialog
                        alertDialog.setTitle(R.string.error);
                        // Se establece el mensaje del AlertDialog
                        alertDialog.setMessage(R.string.couldnt_download_weather);
                        // Se establecen las acciones (botones)
                        // Botón de aceptar
                        alertDialog.setPositiveButton(R.string.retry, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Se vuelve a llamar a updateForecast para reintentar la descarga
                                updateForecast();
                            }
                        });

                        // Se muestra el AlertDialog
                        alertDialog.show();
                    }
                }
            };

            // Se ejecuta el proceso de lanzar un proceso en segundo plano
            // NOTA: MUY IMPORTANTE NO OLVIDARSE DE ESTO, SI NO SE PONE, NO SE EJECUTARÁ NADA DEL MÉTODO ASYNCTASK
            weatherDownloader.execute(mCity);

            // Cancela el proceso en segundo plano
            //weatherDownloader.cancel();

            return;
        }

        // Se indica a ViewSwitcher cuál es la segunda interfaz (Forecast)
        viewSwitcher.setDisplayedChild(FORECAST_VIEW_INDEX);

        // Se asigna un adapter al recyclerView
        ForecastRecyclerViewAdapter adapter = new ForecastRecyclerViewAdapter(forecast, mShowCelsius);

        // Se suscribe a las pulsaciones de las tarjetas del RecyclerView
        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Se obtiene la posicición de la tarjeta pulsada
                int position = mList.getChildAdapterPosition(v);
                Forecast forecastDetail = forecast.get(position);
                // Se lanza la actividad de detalle. Esto sería mejor lanzarlo desde la actividad
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(DetailActivity.EXTRA_FORECAST, forecastDetail);
                intent.putExtra(DetailActivity.EXTRA_SHOW_CELSIUS, mShowCelsius);
                // Se crea una animación al mostrar la actividad de detalle
                Bundle animationOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        // Contexto
                        getActivity(),
                        // Vista común
                        v,
                        // Nombre del transitionName que buscará en el destino
                        getString(R.string.transition_to_detail)
                ).toBundle();
                startActivity(intent, animationOptions);
            }
        });

        mList.setAdapter(adapter);
    }

    private LinkedList<Forecast> downloadForecast(City city) {
        URL url = null;
        InputStream input = null;

        try {
            // Nos descargamos la información del tiempo a machete
            url = new URL(String.format("http://api.openweathermap.org/data/2.5/forecast/daily?q=%s&lang=sp&units=metric&appid=62814cdb8729ba759fd95813d2961de1", city.getName()));
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.connect();
            byte data[] = new byte[1024];
            int downloadedBytes;
            input = con.getInputStream();
            StringBuilder sb = new StringBuilder();
            while ((downloadedBytes = input.read(data)) != -1) {
                sb.append(new String(data, 0, downloadedBytes));
            }

            // Analizamos los datos para convertirlos de JSON a algo que podamos manejar en código
            JSONObject jsonRoot = new JSONObject(sb.toString());
            JSONArray list = jsonRoot.getJSONArray("list");

            // Nos descargamos todos los días de la predicción
            LinkedList<Forecast> forecasts = new LinkedList<>();

            for (int i = 0; i < list.length(); i++) {
                JSONObject today = list.getJSONObject(i);
                float max = (float) today.getJSONObject("temp").getDouble("max");
                float min = (float) today.getJSONObject("temp").getDouble("min");
                float humidity = (float) today.getDouble("humidity");
                String description = today.getJSONArray("weather").getJSONObject(0).getString("description");
                String iconString = today.getJSONArray("weather").getJSONObject(0).getString("icon");

                // Se convierte el texto iconString a drawable
                iconString = iconString.substring(0, iconString.length() - 1);
                int iconInt = Integer.parseInt(iconString);
                int iconResource = R.drawable.ico_01;
                switch (iconInt) {
                    case 1:
                        iconResource = R.drawable.ico_01;
                        break;
                    case 2:
                        iconResource = R.drawable.ico_02;
                        break;
                    case 3:
                        iconResource = R.drawable.ico_03;
                        break;
                    case 4:
                        iconResource = R.drawable.ico_04;
                        break;
                    case 9:
                        iconResource = R.drawable.ico_09;
                        break;
                    case 10:
                        iconResource = R.drawable.ico_10;
                        break;
                    case 11:
                        iconResource = R.drawable.ico_11;
                        break;
                    case 13:
                        iconResource = R.drawable.ico_13;
                        break;
                    case 50:
                        iconResource = R.drawable.ico_50;
                        break;
                }

                Forecast forecast = new Forecast(max, min, humidity, description, iconResource);
                forecasts.add(forecast);
            }

            Thread.sleep(2000);

            return forecasts;

        } catch(Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Método que indica que se tiene que crear un menú
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        // Los inflater inflan ficheros xml de cualquier tipo, pero cada inflater "infla" un tipo de xml
        // Menu recogido de un xml
        // Lugar en el que se debe colgar el menú representado en el xml
        inflater.inflate(R.menu.menu_settings, menu);
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
            Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UNITS) {
            // Se está volviendo de la pantalla de SettingsActivity
            // Se comprueba cómo ha ido el resultado
            if (resultCode == Activity.RESULT_OK) {
                // Se guarda el valor anterior de los Settings por si el usuario deshace las preferencias
                final boolean oldShowCelsius = mShowCelsius;
                String snackBarText = null;

                // No ha habido errores y el usuario ha aceptado la selección de unidades
                // Se obtienen los datos del resultado
                // La opción por defecto aquí es absurda, pero se tienen que informar
                int optionSelected = data.getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.farenheit_rb);
                if (optionSelected == R.id.farenheit_rb) {
                    snackBarText = getString(R.string.farenheit_selected);
                    mShowCelsius = false;
                } else {
                    snackBarText = getString(R.string.celsius_selected);
                    mShowCelsius = true;
                }

                // Se muestra un mensaje flotante (Tostada) con la selección
                // Se indica la vista en la que va a colgar. En este caso, la vista actual
                // Se indica el texto a mostrar
                // Se indica la duración
                //Toast.makeText(this, mShowCelsius, Toast.LENGTH_LONG).show();

                // Se muestra un mensaje en una barra inferior (SnackBar) con la selección
                // Se indica la vista de la que va a colgar. En este caso se le indica que cuelgue de la raíz
                // Se indica el texto a mostrar
                // Se indica la duración
                //Snackbar.make(findViewById(android.R.id.content), snackBarText, Snackbar.LENGTH_LONG).show();

                if (getView() != null) {
                    // Se muestra un mensaje en una barra inferior (SnackBar) con la selección y además que muestre un botón de DESHACER
                    // Se indica la vista de la que va a colgar. En este caso se le indica que cuelgue de la raíz
                    // Se indica el texto a mostrar
                    // Se indica la duración
                    // Se indica una acción
                    Snackbar.make(getView(), snackBarText, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // Se deshacen los cambios
                                    mShowCelsius = oldShowCelsius;
                                    PreferenceManager
                                            .getDefaultSharedPreferences(getActivity())
                                            .edit()
                                            .putBoolean(PREFERENCE_SHOW_CELSIUS, mShowCelsius)
                                            .apply();
                                    updateForecast();
                                }
                            })
                            .show();
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
                        .getDefaultSharedPreferences(getActivity())
                        .edit()
                        .putBoolean(PREFERENCE_SHOW_CELSIUS, mShowCelsius)
                        .apply();

                // Se guarda el modelo recibido en una variable
                updateForecast();

            } else if (resultCode == Activity.RESULT_CANCELED) {
                // No se hace nada porque el usuario ha cancelado la selección de unidades
            }
        }
    }
}
