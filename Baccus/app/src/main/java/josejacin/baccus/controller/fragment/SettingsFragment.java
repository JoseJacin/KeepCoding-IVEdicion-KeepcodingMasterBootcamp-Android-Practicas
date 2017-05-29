package josejacin.baccus.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import josejacin.baccus.R;
import josejacin.baccus.controller.activity.SettingsActivity;

public class SettingsFragment extends Fragment implements View.OnClickListener {
    // Propiedades
    public static final String ARG_WINE_IMAGE_SCALE_TYPE = "josejacin.baccus.controller.fragment.SettingsActivity.ARG_WINE_IMAGE_SCALE_TYPE";

    // Vistas
    private RadioGroup mRadioGroup = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        mRadioGroup = (RadioGroup) root.findViewById(R.id.scale_type_radios);

        // Se activa el botón correspondiente con la opción seleccionada por el ususario
        if (getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ImageView.ScaleType.FIT_XY)) {
            mRadioGroup.check(R.id.fit_radio);
        } else if (getArguments().getSerializable(ARG_WINE_IMAGE_SCALE_TYPE).equals(ImageView.ScaleType.FIT_CENTER)) {
            mRadioGroup.check(R.id.center_radio);
        }

        Button cancelButton = (Button) root.findViewById(R.id.cancel_button);
        Button saveButton = (Button) root.findViewById(R.id.save_button);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_button:
                cancelSettings();
                break;
            case R.id.save_button:
                saveSettings();
                break;
        }
    }

    private void saveSettings() {
        Intent config = new Intent();

        if (mRadioGroup.getCheckedRadioButtonId() == R.id.fit_radio) {
            config.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, ImageView.ScaleType.FIT_XY);
        } else if (mRadioGroup.getCheckedRadioButtonId() == R.id.center_radio) {
            config.putExtra(SettingsActivity.EXTRA_WINE_IMAGE_SCALE_TYPE, ImageView.ScaleType.FIT_CENTER);
        }

        getActivity().setResult(Activity.RESULT_OK, config);
        getActivity().finish();
    }

    private void cancelSettings() {
        getActivity().setResult(Activity.RESULT_CANCELED);
        getActivity().finish();
    }
}
