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


/*
        mPager = (ViewPager) findViewById(R.id.view_pager);
        // Se crea una instancia de Cities
        mCities = new Cities();
        // Se crea una instancia de un adapter
        CityPagerAdapter adapter = new CityPagerAdapter(getFragmentManager(), mCities);

        // Se le da al viewPager su adapter para que muestre tantos fragments como diga el modelo
        mPager.setAdapter(adapter);

        // Se detecta cuándo se cambia de página en el Page
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateCityInfo(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateCityInfo(0);
    }

    private void updateCityInfo(int position) {
        String cityName = mCities.getCity(position).getName();
        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle(cityName);
    }

    // Método que indica que se tiene que crear un menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_pager, menu);

        return true;
    }

    // Método que se ejecuta cuando se selecciona un item del menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superReturn = super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.prevoius) {
            // Se mueve el pager hacia atrás
            mPager.setCurrentItem(mPager.getCurrentItem()-1);

            return true;
        } else if (item.getItemId() == R.id.next) {
            // Se mueve el pager hacia delante
            mPager.setCurrentItem(mPager.getCurrentItem()+1);

            return true;
        }

        return superReturn;
    }

    // Método que se ejecuta justo antes de dibujar las opciones de menú
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem menuPrev = menu.findItem(R.id.prevoius);
        MenuItem menuNext = menu.findItem(R.id.next);

        menuPrev.setEnabled(mPager.getCurrentItem() > 0);
        menuNext.setEnabled(mPager.getCurrentItem() < mCities.getCount() - 1);

        /*
        // Se comprueba si se encuentra en el primer o último elemento
        if (mPager.getCurrentItem() > 0) {
            // Se puede ir hacia atrás, por lo que se activa la opción de menú
            menuPrev.setEnabled(true);
        } else {
            // No se puede ir hacia atrás, por lo que se desactiva la opción de menú
            menuPrev.setEnabled(false);
        }

        // Se comprueba si se encuentra en el último elemento
        if (mPager.getCurrentItem() < mCities.getCount() -1) {
            // Se puede ir hacia delante, por lo que se activa la opción de menú
            menuNext.setEnabled(true);
        } else {
            // No se puede ir hacia delante, por lo que se desactiva la opción de menú
            menuNext.setEnabled(false);
        }
        */

        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean superValue =  super.onOptionsItemSelected(item);

        if (item.getItemId() == android.R.id.home) {
            // Han pulsado la flecha de back de la ActionBar, por lo que se vuelve
            finish();
            return true;
        }

        return superValue;
    }
}

// Clase que adapta la vista con el Fragment
/*class CityPagerAdapter extends FragmentPagerAdapter {
    private Cities mCities;

    // Método constructor
    public CityPagerAdapter(FragmentManager fm, Cities cities) {
        super(fm);
        mCities = cities;
    }

    // Método que retorna la ciudad indicada por index
    @Override
    public Fragment getItem(int position) {
        ForecastFragment fragment = ForecastFragment.newInstance(mCities.getCity(position));

        return fragment;
    }

    // Método que retorna el número de adapters que hay
    @Override
    public int getCount() {
        return mCities.getCount();
    }

    // Método que retorna el título que se le debe poner a la ActionBar
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        City city = mCities.getCity(position);
        String cityName = city.getName();

        return cityName;
    }

}
*/