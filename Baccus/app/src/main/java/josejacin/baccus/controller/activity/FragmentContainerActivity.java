package josejacin.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import josejacin.baccus.R;

public abstract class FragmentContainerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fragment_container);

        // Se obtiene una referencia a la ActionBar
        //ActionBar actionBar = (ActionBar) getSupportActionBar();
        // Se modifica el ttulo de la ActionBar
        //actionBar.setTitle("Un título diferente");

        // Se obtiene una referencia a la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Se provoca que la Toolbar se comporte como una Actionbar
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

    }

    protected abstract Fragment createFragment();
}
