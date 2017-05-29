package josejacin.baccus.controller.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import josejacin.baccus.R;
import josejacin.baccus.controller.adapter.WineryPagerAdapter;
import josejacin.baccus.model.Wine;
import josejacin.baccus.model.Winery;

public class WineryFragment extends Fragment implements ViewPager.OnPageChangeListener {
    // Atributos
    private ViewPager mPager = null;
    private ActionBar mActionBar = null;
    private Winery mWinery = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_winery, container, false);

        // Se obiene una referencia al Pager
        mPager = (ViewPager) root.findViewById(R.id.pager);
        // Se indica quién es el adaptador del ViewPager
        mPager.setAdapter(new WineryPagerAdapter(getFragmentManager()));

        mWinery = Winery.getInstance();

        mActionBar = (ActionBar) ((AppCompatActivity)getActivity()).getSupportActionBar();

        mPager.setOnPageChangeListener(this);

        updateActionBar(0);

        return root;
    }

    private  void updateActionBar(int index) {
        mActionBar.setTitle(mWinery.getWine(index).getName());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        updateActionBar(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
