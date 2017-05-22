package josejacin.baccus.controller;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import josejacin.baccus.R;
import josejacin.baccus.model.Wine;

public class WebActivity extends AppCompatActivity {

    // Propiedades
    public static final String EXTRA_WINE = "josejacin.baccus.controller.WebActivity.extra_wine";
    private static final String STATE_URL = "url";

    //Modelo
    private Wine mWine = null;

    //Vistas
    private WebView mBrowser = null;
    private ProgressBar mLoading = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_web);

        // Se accede al modelo recibido desde el Indent
        mWine = (Wine) getIntent().getSerializableExtra(EXTRA_WINE);

        mWine.addGrape("Mencía");

        // Se accede a las vistas desde el controlador (asocian)
        mBrowser = (WebView) findViewById(R.id.browser);
        mLoading = (ProgressBar) findViewById(R.id.loading);

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
        mBrowser.loadUrl(mWine.getCompanyWeb());
    }

    // Método que crea las opciones de menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Se encarga de coger un XML y de sacar las opciones de menú que se encuentran en él
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_web, menu);

        return true;
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
