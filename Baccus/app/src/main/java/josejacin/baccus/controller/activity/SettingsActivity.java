package josejacin.baccus.controller.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import josejacin.baccus.controller.fragment.SettingsFragment;

public class SettingsActivity extends FragmentContainerActivity {
    // Propiedades
    public static final String EXTRA_WINE_IMAGE_SCALE_TYPE = "josejacin.baccus.controller.activity.SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE";


    @Override
    protected Fragment createFragment() {
        Bundle arguments = new Bundle();
        arguments.putSerializable(SettingsFragment.ARG_WINE_IMAGE_SCALE_TYPE, getIntent().getSerializableExtra(EXTRA_WINE_IMAGE_SCALE_TYPE));
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(arguments);

        return fragment;
    }
}
