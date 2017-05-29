package josejacin.guedr.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import josejacin.guedr.R;

public class SettingsActivity extends AppCompatActivity {
    public static final String EXTRA_UNITS = "units";
    private RadioGroup mRadioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        findViewById(R.id.accept_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                acceptSettings();
            }
        });

        findViewById(R.id.cancel_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelSettings();
            }
        });

        mRadioGroup = (RadioGroup) findViewById(R.id.units_rg);

        // Se selecciona las unidades que se hayan recibido por el intent
        int radioSelected = getIntent().getIntExtra(SettingsActivity.EXTRA_UNITS, R.id.farenheit_rb);
        mRadioGroup.check(radioSelected);
    }

    private void cancelSettings() {
        // Se establece el resultado de regreso a la Actividad (pantalla) anterior a CANCELADO
        setResult(RESULT_CANCELED);
        // Se indica que la Actividad actual ha terminado y que se ha de ir a la actividad anterior
        finish();
    }

    private void acceptSettings() {
        // Se crea un Intent con los datos de salida (datos de retorno)
        Intent returnIntent = new Intent();
        // Se indican los datos de salida (datos de retorno)
        // Se pasa la clave de los datos de salida
        // Se pasan los datos de salida
        returnIntent.putExtra(EXTRA_UNITS, mRadioGroup.getCheckedRadioButtonId());

        // Se establece el resultado de regreso a la Actividad (pantalla) anterior a OK y se pasan los datos de retorno
        setResult(RESULT_OK, returnIntent);

        // Se indica que la Actividad actual ha terminado y que se ha de ir a la actividad anterior
        finish();
    }
}
