package josejacin.baccus.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import josejacin.baccus.R;
import josejacin.baccus.model.Wine;

public class WineActivity extends AppCompatActivity {
    //Obtener el nombre de la clase
    private static final String TAG = WineActivity.class.getCanonicalName();
    private static final int SETTINGS_REQUEST = 1;
    private static final String STATE_IMAGE_SCALE_TYPE = "josejacin.baccus.controller.WineActivity.STATE_IMAGE_SCALE_TYPE";

    //Modelo
    private Wine mWine = null;

    //Vistas
    private ImageView mWineImage = null;
    private TextView mWineNameText = null;
    private TextView mWineTypeText = null;
    private TextView mWineOriginText = null;
    private RatingBar mWineRatingBar = null;
    private TextView mWineCompanyText = null;
    private TextView mWineNotesText = null;
    private ViewGroup mWineGrapesContainer = null;
    private ImageButton mGoToWebButton = null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            settingsIntent.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
            startActivityForResult(settingsIntent, SETTINGS_REQUEST);

            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SETTINGS_REQUEST && resultCode == RESULT_OK) {
            ImageView.ScaleType scaleType = (ImageView.ScaleType) data.getSerializableExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE);
            mWineImage.setScaleType(scaleType);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STATE_IMAGE_SCALE_TYPE, mWineImage.getScaleType());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine);

        // Se crea el modelo
        mWine = new Wine(
                "Bembibre",
                "Tinto",
                R.drawable.bembibre,
                "Dominio de Tares",
                "http://www.dominiodetares.com/portfolio/bembibre/",
                "Este vino muestra toda la complejidad y la elegancia de la variedad Mencía. En fase visual luce un color rojo picota muy cubierto con tonalidades violáceas en el menisco. En nariz aparecen recuerdos frutales muy intensos de frutas rojas (frambuesa, cereza) y una potente ciruela negra, así como tonos florales de la gama de las rosas y violetas, vegetales muy elegantes y complementarios, hojarasca verde, tabaco y maderas aromáticas (sándalo) que le brindan un toque ciertamente perfumado.",
                "El Bierzo",
                5);
        mWine.addGrape("Mencía");

        // Se accede a las vistas desde el controlador (asocian)
        mWineImage = (ImageView) findViewById(R.id.wine_image);
        mWineNameText = (TextView) findViewById(R.id.wine_name);
        mWineTypeText = (TextView) findViewById(R.id.wine_type);
        mWineOriginText = (TextView) findViewById(R.id.wine_origin);
        mWineRatingBar = (RatingBar)findViewById(R.id.wine_rating);
        mWineCompanyText = (TextView) findViewById(R.id.wine_company);
        mWineNotesText = (TextView) findViewById(R.id.wine_notes);
        mWineGrapesContainer = (ViewGroup) findViewById(R.id.grapes_container);
        mGoToWebButton = (ImageButton) findViewById(R.id.go_to_web_button);

        // Se configuran las vistas. Se da valor a las vistas con el modelo
        mWineImage.setImageResource(mWine.getPhoto());
        mWineNameText.setText(mWine.getName());
        mWineTypeText.setText(mWine.getType());
        mWineOriginText.setText(mWine.getOrigin());
        mWineRatingBar.setRating(mWine.getRating());
        mWineCompanyText.setText(mWine.getCompanyName());
        mWineNotesText.setText(mWine.getNotes());

        // Se actualiza la lista de uvas
        for (int i = 0; i < mWine.getGrapeCount(); i++) {
            TextView grapeText = new TextView(this);
            grapeText.setText(mWine.getGrape(i));
            grapeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mWineGrapesContainer.addView(grapeText);
        }

        // Se configuran los botones
        mGoToWebButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(WineActivity.this, WebActivity.class);

                webIntent.putExtra(WebActivity.EXTRA_WINE, mWine);

                // Se comienza la actividad
                startActivity(webIntent);
            }
        });

        // Se configura como se ve la imagen
        if (savedInstanceState != null && savedInstanceState.containsKey(STATE_IMAGE_SCALE_TYPE)) {
            mWineImage.setScaleType((ImageView.ScaleType) savedInstanceState.getSerializable(STATE_IMAGE_SCALE_TYPE));
        }
    }
}
