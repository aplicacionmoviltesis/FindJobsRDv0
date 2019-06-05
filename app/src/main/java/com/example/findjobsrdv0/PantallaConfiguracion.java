package com.example.findjobsrdv0;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.collection.LLRBNode;

public class PantallaConfiguracion extends AppCompatActivity {

    Button btnCambiarPass, btnAcercaApp,btnContactoNosotros,btnReportarProblema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_configuracion);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        btnCambiarPass = (Button) findViewById(R.id.xmlBtnCambiarContrasena);
        btnCambiarPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracion.this, PantallaCambiarContrasena.class);
                startActivityForResult(intent, 0);
            }
        });

        btnAcercaApp = (Button) findViewById(R.id.xmlBtnAcercaDe);
        btnAcercaApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracion.this, PantallaAcercadeApp.class);
                startActivityForResult(intent, 0);
            }
        });

        btnReportarProblema = (Button) findViewById(R.id.xmlBtnReportarProblema);
        btnReportarProblema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaConfiguracion.this, PantallaReportarProblemas.class);
                startActivityForResult(intent, 0);
            }
        });

        btnContactoNosotros = (Button) findViewById(R.id.xmlBtnContactoNosotros);
        btnContactoNosotros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactoNosotros();
            }
        });

    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void ContactoNosotros() {

        AlertDialog.Builder builder = new AlertDialog.Builder(PantallaConfiguracion.this);
        // Get the layout inflater
        LayoutInflater inflater = (PantallaConfiguracion.this).getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the
        // dialog layout
        builder.setTitle("Contacto Con: ");
        builder.setCancelable(false);
        builder.setIcon(R.drawable.ic_contactocorreo);
        builder.setView(inflater.inflate(R.layout.pantalla_contacto_nosotros, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }

                });
        builder.create();
        builder.show();

    }
}
