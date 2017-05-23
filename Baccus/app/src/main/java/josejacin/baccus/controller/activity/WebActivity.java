package josejacin.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import josejacin.baccus.controller.fragment.WebFragment;

public class WebActivity extends FragmentContainerActivity {

    // Propiedades
    public static final String EXTRA_WINE = "josejacin.baccus.controller.activity.WebActivity.extra_wine";

    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(WebFragment.ARG_WINE, getIntent().getSerializableExtra(EXTRA_WINE));

        // Se crea el Fragment
        WebFragment fragment = new WebFragment();
        fragment.setArguments(arguments);

        return fragment;
    }
}
