package com.example.findjobsrdv0.GeneralesApp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.PantallaRegistroBuscador;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaRegistroEmpleador;
import com.example.findjobsrdv0.R;

public class PantallaModoUsuario extends AppCompatActivity {

    private TextView tvElegirmodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_modo_usuario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvElegirmodo = (TextView) findViewById(R.id.tvRegistrar);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/robotoslab.bold.ttf");
        tvElegirmodo.setTypeface(face);


        LinearLayout IrRegistroBuscador = (LinearLayout )findViewById(R.id.lyBuscadorEmpleo);
        IrRegistroBuscador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PantallaRegistroBuscador.class);
                startActivityForResult(intent, 0);
            }
        });

        LinearLayout IrRegistroEmpleador = (LinearLayout )findViewById(R.id.lyEmpleador);
        IrRegistroEmpleador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (v.getContext(), PantallaRegistroEmpleador.class);
                startActivityForResult(intent, 0);
            }
        });
    }
}
