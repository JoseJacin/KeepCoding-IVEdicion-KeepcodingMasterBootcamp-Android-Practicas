package josejacin.guedr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
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

        // Función que se ejecuta cuando se pulsa sobre un botón.
        // Forma 2. Mediante el id del botón
        // Se busca el botón
        changeToStone = (Button) findViewById(R.id.change_stone_system);
        // Se indica que setiene que hacer cuando se pulsa ese botón
        changeToStone.setOnClickListener(this);

        changeToDonkey = (Button) findViewById(R.id.change_donkey_system);
        changeToDonkey.setOnClickListener(this);

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

    // Forma 2. Mediante el id del botón
    // Función que se ejecuta cuando se pulsa sobre el botón y que se encuentra en la clase View.OnClickListener
    @Override
    public void onClick(View v) {
        // Forma 2.1. Mediante el id del botón
        /*
        if (v == changeToStone){
            Log.v(TAG, "Me han pedido Pierda");
        } else if (v == changeToDonkey) {
            Log.v(TAG, "Me han pedido Burro");
        } else {
            Log.v(TAG, "No sé que me han pedido");
        }
        */
        /*
        if (v.getId() == R.id.change_stone_system){
            Log.v(TAG, "Me han pedido Pierda");
        } else if (v.getId() == R.id.change_donkey_system) {
            Log.v(TAG, "Me han pedido Burro");
        } else {
            Log.v(TAG, "No sé qué me han pedido");
        }
        */
        // Otra forma de hacerlo
        switch (v.getId()) {
            case R.id.change_stone_system:
                Log.v(TAG, "Me han pedido Pierda");
                break;
            case R.id.change_donkey_system:
                Log.v(TAG, "Me han pedido Burro");
                break;
            default:
                Log.v(TAG, "No sé qué me han pedido");
        }
    }
}
