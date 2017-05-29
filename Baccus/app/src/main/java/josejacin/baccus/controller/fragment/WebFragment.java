package josejacin.baccus.controller.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import josejacin.baccus.R;
import josejacin.baccus.model.Wine;

public class WebFragment extends Fragment {
    // Propiedades
    public static final String ARG_WINE = "josejacin.baccus.controller.fragment.WebFragment.ARG_WINE";
    private static final String STATE_URL = "url";

    //Modelo
    private Wine mWine = null;

    //Vistas
    private WebView mBrowser = null;
    private ProgressBar mLoading = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_web, container, false);

        // Se accede al modelo recibido desde el Indent
        mWine = (Wine) getArguments().getSerializable(ARG_WINE);

        // Se accede a las vistas desde el controlador (asocian)
        mBrowser = (WebView) root.findViewById(R.id.browser);
        mLoading = (ProgressBar) root.findViewById(R.id.loading);

        // Se configuran las vistas
        mBrowser.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mLoading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mLoading.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                mLoading.setVisibility(View.GONE);
            }
        });

        // Se indica que use Javascript si es necesario
        mBrowser.getSettings().setJavaScriptEnabled(true);
        // Se indica que muestre los controles de zoom
        mBrowser.getSettings().setBuiltInZoomControls(true);
        // Se carga la página web
        if (savedInstanceState == null || !savedInstanceState.containsKey(STATE_URL)) {
            mBrowser.loadUrl(mWine.getCompanyWeb());
        } else {
            mBrowser.loadUrl(savedInstanceState.getString(STATE_URL));
        }

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_URL, mBrowser.getUrl());
    }

    // Método que crea las opciones de menú


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_web, menu);

    }

    // Método que se ejecuta cuando se ha seleccionado una opción de menú
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        // Se comprueba si el item seleccionado es el indicado
        if (item.getItemId() == R.id.menu_reload) {
            mBrowser.reload();
            return true;
        }
        return false;
    }
}
