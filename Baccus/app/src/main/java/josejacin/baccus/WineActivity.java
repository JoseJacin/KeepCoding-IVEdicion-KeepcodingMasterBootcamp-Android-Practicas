package josejacin.baccus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class WineActivity extends AppCompatActivity {
    //Obtener el nombre de la clase
    private static final String TAG = WineActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);

        Log.v(TAG, "Hola Amundio, estamos en Baccus");
    }
}
