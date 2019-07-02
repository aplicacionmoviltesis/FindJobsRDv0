package com.example.findjobsrdv0.Administradores;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.findjobsrdv0.R;

public class PantallaConfiguracionAdministrador extends AppCompatActivity {

    private Button btnRegistrarArea,btnRegistrarUniversidad, btnRegistrarProvincia,
            btnActualizarArea,btnActualizarUniversidad,btnActualizarProvincia,
            btnVerProblemasReportados,btnVerOpinionesSugerencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_configuracion_administrador);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Menu Administrador");

        btnRegistrarArea = (Button) findViewById(R.id.xmlBtnAnadirArea);
        btnActualizarArea = (Button) findViewById(R.id.xmlBtnActualizarArea);

        btnRegistrarUniversidad = (Button) findViewById(R.id.xmlBtnAnadirUniversidad);
        btnActualizarUniversidad = (Button) findViewById(R.id.xmlBtnActualizarUniversidad);

        btnRegistrarProvincia = (Button) findViewById(R.id.xmlBtnAnadirProvincia);
        btnActualizarProvincia = (Button) findViewById(R.id.xmlBtnActualizarProvincia);

        btnVerProblemasReportados = (Button) findViewById(R.id.xmlBtnVerProblemasReportados);
        btnVerOpinionesSugerencias = (Button) findViewById(R.id.xmlBtnVerOpiSug);

        btnRegistrarArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaAgregarAreaDeTrabajo.class);
                startActivityForResult(intent, 0);
            }
        });

        btnActualizarArea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaListaAreas.class);
                startActivityForResult(intent, 0);
            }
        });

        btnRegistrarUniversidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaAgregarUniversidades.class);
                startActivityForResult(intent, 0);
            }
        });

        btnActualizarUniversidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaListaUniversidades.class);
                startActivityForResult(intent, 0);
            }
        });

        btnRegistrarProvincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaAgregarProvincias.class);
                startActivityForResult(intent, 0);
            }
        });

        btnActualizarProvincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaListaProvincias.class);
                startActivityForResult(intent, 0);
            }
        });

        btnVerProblemasReportados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaListaReportesProblemas.class);
                startActivityForResult(intent, 0);
            }
        });

        btnVerOpinionesSugerencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracionAdministrador.this, PantallaListaOpinionesSugerencias.class);
                startActivityForResult(intent, 0);
            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
