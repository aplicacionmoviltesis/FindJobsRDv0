package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PantallaNavegador extends AppCompatActivity {
    private WebView webView;
    String probandopage="https://www.google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_navegador);

        Bundle bundle = getIntent().getExtras();
        //probandopage = bundle.getString("sPaginaWebDE");
        webView =(WebView) findViewById(R.id.navegador);
        webView.setWebViewClient(new WebViewClient());

        if (bundle != null) {
            if (!probandopage.isEmpty()) {
                probandopage = getIntent().getStringExtra("sPaginaWebDE");
                webView.loadUrl(probandopage);
            }
        }


//despues tengo que validar la url
        webView.loadUrl(probandopage);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);


    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
