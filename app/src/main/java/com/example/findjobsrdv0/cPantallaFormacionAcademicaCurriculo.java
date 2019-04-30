package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class cPantallaFormacionAcademicaCurriculo extends AppCompatActivity {
    private TextView TituloFormacionAcademica;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_pantalla_formacion_academica_curriculo);

        TituloFormacionAcademica = (TextView) findViewById(R.id.xmlTituloFormacionAcademica);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chomsky.otf");
        TituloFormacionAcademica.setTypeface(face);
    }
}
