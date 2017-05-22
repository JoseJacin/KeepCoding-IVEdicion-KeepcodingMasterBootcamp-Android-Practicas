package josejacin.guedr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    // Para coger el nombre de la clase
    protected static String TAG = MainActivity.class.getCanonicalName();

    // Forma 2.1. Mediante el id del botón
    // Para permitir que el botón se pueda utilizar en todo el ámbito de la clase
    protected Button changeToStone;
    protected Button changeToDonkey;

    // Función que se llama justo antes de crearse una Actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Se crea una referencia al ImageView del offline image
        final ImageView offlineImage = (ImageView) findViewById(R.id.offline_weather_image);

        // Función que se ejecuta cuando se pulsa sobre un botón.
        // Forma 2. Mediante el id del botón
        // Se busca el botón
        changeToStone = (Button) findViewById(R.id.change_stone_system);
        // Se indica que setiene que hacer cuando se pulsa ese botón

        // Forma 3. Con una clase por separado
        //Esto es muy raro hacerlo, pero puede ayudar a entender qué son las clases anónimas en realidad
        changeToStone.setOnClickListener(new StoneButtonListener(offlineImage));
        changeToDonkey = (Button) findViewById(R.id.change_donkey_system);

        // Forma 4. Mediante una clase anónima
        // Es la forma más habitual. Se usa cuando en la llamada se hacen pocas cosas
        changeToDonkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "Me han pedido burro");
                offlineImage.setImageResource(R.drawable.offline_weather2);
            }
        });



        Log.v(TAG, "Hola Amundio, he pasado por onCreate");
    }

    // Función que se ejecuta cuando se pulsa sobre un botón.
    // Forma 1. Mediante la configuración de la propiedad onClick del botón.
    // Es la forma menos recomendable, ya que si se cambia el nombre de la función y no se actualiza en la propiedad onCLick del
    // botón, al pulsar sobre el botón se produce una excepción y la app se cierra
    /*
    public void changeSystem(View v) {
        Log.v(TAG, "Han llamado a ChangeSystem");
    }
    */

    // Función que se llama justo antes de destruirse una Actividad
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.v(TAG, "Nos han llamado a onSaveInstanceState");
        outState.putString("clave", "valor");
    }
}

// Forma 3. Con una clase por separado
// Es muy raro usar esta forma, solo se tendría que usar en el caso en que en la llamada se hagan muchas cosas
class StoneButtonListener implements View.OnClickListener {

    private final ImageView offlineImage;

    public StoneButtonListener(ImageView offlineImage) {
        this.offlineImage = offlineImage;
    }

    @Override
    public void onClick(View v) {
        Log.v("Lo que sea", "Me han pedido piedra");
        offlineImage.setImageResource(R.drawable.offline_weather);
    }
}
