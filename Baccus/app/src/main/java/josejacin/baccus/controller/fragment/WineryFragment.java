package josejacin.baccus.controller.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import josejacin.baccus.R;
import josejacin.baccus.controller.activity.WineActivity;
import josejacin.baccus.model.Wine;

public class WineryFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.activity_winery, container, false);

        // Se crea el modelo
        Wine bembibre = new Wine(
                "Bembibre",
                "Tinto",
                R.drawable.bembibre,
                "Dominio de Tares",
                "http://www.dominiodetares.com/portfolio/bembibre/",
                "Este vino muestra toda la complejidad y la elegancia de la variedad Mencía. En fase visual luce un color rojo picota muy cubierto con tonalidades violáceas en el menisco. En nariz aparecen recuerdos frutales muy intensos de frutas rojas (frambuesa, cereza) y una potente ciruela negra, así como tonos florales de la gama de las rosas y violetas, vegetales muy elegantes y complementarios, hojarasca verde, tabaco y maderas aromáticas (sándalo) que le brindan un toque ciertamente perfumado.",
                "El Bierzo",
                5);
        bembibre.addGrape("Mencía");

        Wine vegaval = new Wine(
                "Vegaval",
                "Tinto",
                R.drawable.vegaval,
                "Miguel de Calatayud",
                "http://www.vegaval.com/es",
                "Blah blah blah",
                "Valdepeñas",
                4);
        vegaval.addGrape("tempranillo");

        FragmentTabHost tabHost = (FragmentTabHost) root.findViewById(android.R.id.tabhost);
        tabHost.setup(getActivity(), getActivity().getSupportFragmentManager(), android.R.id.tabcontent);

        // Se añade la primera pestaña
        Bundle arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE, bembibre);
        tabHost.addTab(tabHost.newTabSpec(bembibre.getName()).setIndicator(bembibre.getName()), WineFragment.class, arguments);

        // Se añade la segunda pestaña
        arguments = new Bundle();
        arguments.putSerializable(WineFragment.ARG_WINE, vegaval);
        tabHost.addTab(tabHost.newTabSpec(vegaval.getName()).setIndicator(vegaval.getName()), WineFragment.class, arguments);

        return root;
    }
}
