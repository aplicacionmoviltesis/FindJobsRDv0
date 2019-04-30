package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class cPantallaReferenciasCurriculos extends AppCompatActivity {

    private TextView TituloReferencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_pantalla_referencias_curriculos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        TituloReferencia = (TextView) findViewById(R.id.xmlTituloReferencia);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chomsky.otf");
        TituloReferencia.setTypeface(face);
    }
}
