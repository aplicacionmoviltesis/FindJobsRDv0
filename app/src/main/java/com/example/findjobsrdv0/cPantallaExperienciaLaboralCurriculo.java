package com.example.findjobsrdv0;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class cPantallaExperienciaLaboralCurriculo extends AppCompatActivity {
    private TextView TituloExpLab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_pantalla_experiencia_laboral_curriculo);

        TituloExpLab = (TextView) findViewById(R.id.xmlTituloExperienciaLaboral);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/Chomsky.otf");
        TituloExpLab.setTypeface(face);
    }
}
