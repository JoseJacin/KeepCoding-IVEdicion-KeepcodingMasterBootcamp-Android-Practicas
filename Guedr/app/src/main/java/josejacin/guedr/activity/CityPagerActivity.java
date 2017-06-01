package josejacin.guedr.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import josejacin.guedr.R;
import josejacin.guedr.fragment.CityPagerFragment;
import josejacin.guedr.fragment.ForecastFragment;
import josejacin.guedr.model.Cities;
import josejacin.guedr.model.City;

public class CityPagerActivity extends AppCompatActivity {

    public static final String EXTRA_CITY_INDEX = "EXTRA_CITY_INDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pager);

        // Se indica a la actividd que se use la toolbar personalizada
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Se indica el logo que tienen que tener
        //toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Recibimos el índice de la ciudad que se quiere mostrar
        int cityIndex = getIntent().getIntExtra(EXTRA_CITY_INDEX, 0);

        // Añadimos, si hace falta, el CityPagerFragment a nuestra jerarquía
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentById(R.id.view_pager_fragment) == null) {
            // Hay hueco, y habiendo hueco metemos el fragment
            CityPagerFragment fragment = CityPagerFragment.newInstance(cityIndex);
            fm.beginTransaction()
                    .add(R.id.view_pager_fragment, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue =  super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            // Han pulsado la flecha de back de la ActionBar, por lo que se vuelve a la actividad anterior
            finish();
            return true;
        }

        return superValue;
    }
}