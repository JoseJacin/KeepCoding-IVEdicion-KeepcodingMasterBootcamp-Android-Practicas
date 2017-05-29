package josejacin.guedr.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import josejacin.guedr.R;
import josejacin.guedr.fragment.ForecastFragment;
import josejacin.guedr.model.Cities;
import josejacin.guedr.model.City;

public class CityPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_pager);

        // Se indica a la actividd que se use la toolbar personalizada
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);


        ViewPager pager = (ViewPager) findViewById(R.id.view_pager);
        // Se crea una instancia de Cities
        Cities cities = new Cities();
        // Se crea una instancia de un adapter
        CityPagerAdapter adapter = new CityPagerAdapter(getFragmentManager(), cities);

        // Se le da al viewPager su adapter para que muestre tantos fragments como diga el modelo
        pager.setAdapter(adapter);
    }

}

// Clase que adapta la vista con el Fragment
class CityPagerAdapter extends FragmentPagerAdapter {
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