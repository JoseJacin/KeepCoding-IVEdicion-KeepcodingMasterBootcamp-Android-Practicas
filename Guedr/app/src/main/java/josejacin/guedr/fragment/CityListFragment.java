package josejacin.guedr.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.LinkedList;

import josejacin.guedr.R;
import josejacin.guedr.model.City;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityListFragment extends Fragment {
    private final static String ARG_CITIES = "cities";

    protected LinkedList<City> mCities;
    protected OnCitySelectedListener mOnCitySelectedListener;

    public static CityListFragment newInstance(LinkedList<City> cities) {
        CityListFragment fragment = new CityListFragment();

        Bundle arguments = new Bundle();
        arguments.putSerializable(ARG_CITIES, cities);
        fragment.setArguments(arguments);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Se obtiene la lista de ciudades
        if (getArguments() != null) {
            mCities = (LinkedList<City>) getArguments().getSerializable(ARG_CITIES);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_city_list, container, false);

        // Se obtiene la lista de ciudades
        // Se accede a la vista
        ListView list = (ListView) root.findViewById(R.id.city_list);

        // Se genera el adapter con la lista de ciudades
        ArrayAdapter<City> adapter = new ArrayAdapter<City>(getActivity(), android.R.layout.simple_list_item_1, mCities);

        // Se pasa el adapter el view para que rellene la lista
        list.setAdapter(adapter);

        // Se asigna un listener a la lista para saber cuándo se ha pulsado una fila
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Se avisa al listener, si no se encuentra, que el usuario ha seleccionado una fila
                if (mOnCitySelectedListener != null) {
                    City selectedCity = mCities.get(position);
                    // Se avisa al listener
                    mOnCitySelectedListener.onCitySelected(selectedCity, position);
                }
            }
        });

        // Hacemos algo con el floating action button
        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.add_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getView(), "Aquí iría el añadir una ciudad", Snackbar.LENGTH_LONG).show();
            }
        });

        return root;
    }

    // Método que se ejecuta cuando un fragment se añade a una actividad
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Se comprueba que la actividad "enganchada" es la deseada
        if (getActivity() instanceof OnCitySelectedListener) {
            mOnCitySelectedListener = (OnCitySelectedListener) getActivity();
        }
    }

    // Método que se ejecuta cuando un fragment se añade a una actividad
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Se comprueba que la actividad "enganchada" es la deseada
        if (getActivity() instanceof OnCitySelectedListener) {
            mOnCitySelectedListener = (OnCitySelectedListener) getActivity();
        }
    }

    // Método que se ejecuta cuando un fragment se elimina a una actividad
    @Override
    public void onDetach() {
        super.onDetach();

        // Se elimina la referencia a la actividad
        mOnCitySelectedListener = null;
    }

    // Interfaz que implementa cuando se va a seleccionar una fila
    public interface OnCitySelectedListener {
        void onCitySelected(City city, int position);
    }
}
