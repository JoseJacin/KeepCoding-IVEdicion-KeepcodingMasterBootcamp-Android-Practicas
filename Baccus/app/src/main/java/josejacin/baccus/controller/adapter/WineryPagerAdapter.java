package josejacin.baccus.controller.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import josejacin.baccus.controller.fragment.WineFragment;
import josejacin.baccus.model.Winery;

public class WineryPagerAdapter extends FragmentPagerAdapter {

    // Atributos
    private Winery mWinery = null;

    public WineryPagerAdapter(FragmentManager fm) {
        super(fm);
        mWinery = Winery.getInstance();
    }

    // Método que retorna cada uno de los fragments
    @Override
    public Fragment getItem(int position) {
        // Se instancia la pantalla que se va a mostrar en esa posición
        WineFragment fragment = new WineFragment();
        // Se crean los argumentos que necesita el WineFragment
        Bundle arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE, mWinery.getWine(position));
        // Se pasan los argumentos a WineFragment
        fragment.setArguments(arguments);

        return fragment;
    }

    // Método que retorna el número de fragments o pantallas se tienen que mostrar en el adapter
    @Override
    public int getCount() {
        return mWinery.getWineCount();
    }

    // Método que indica lo que se tiene que poner en el título de las pestañas
    @Override
    public CharSequence getPageTitle(int position) {
        super.getPageTitle(position);
        return mWinery.getWine(position).getName();
    }
}
