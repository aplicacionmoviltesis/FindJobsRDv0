package com.example.findjobsrdv0.GeneralesApp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findjobsrdv0.Administradores.PantallaConfiguracionAdministrador;
import com.example.findjobsrdv0.PantallaBuscarEmpleos;
import com.example.findjobsrdv0.R;

public class PantallaConfiguracion extends AppCompatActivity {

    private Button btnCambiarPass, btnAcercaApp,btnContactoNosotros,btnReportarProblema,btnConfigAdmin;

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

        btnConfigAdmin = (Button) findViewById(R.id.xmlBtnConfigAdmin);
        btnConfigAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccesoAdmin();
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

    public void AccesoAdmin(){
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.login_acceso_admin, null);

        final TextView TvTiLoginAdmin = (TextView) dialogView.findViewById(R.id.xmlTiLoginAdmin);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvTiLoginAdmin.setTypeface(face);

        final EditText editEmailAdmin = (EditText) dialogView.findViewById(R.id.xmlEditEmailAdmin);
        final EditText editPassAdmin = (EditText) dialogView.findViewById(R.id.xmlEditPassAdmin);

        Button btnSalirAdmin = (Button) dialogView.findViewById(R.id.xmlBtnSalirAdmin);
        Button btnEntrarAdmin = (Button) dialogView.findViewById(R.id.xmlBtnEntrarAdmin);



        btnSalirAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
        btnEntrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // DO SOMETHINGS
                //dialogBuilder.dismiss();
                String emailadmin = editEmailAdmin.getText().toString().trim();
                String passAdmin = editPassAdmin.getText().toString().trim();
                if(emailadmin.equals("lamf") && passAdmin.equals("123")){
                    editEmailAdmin.setText("");
                    editPassAdmin.setText("");
                    Intent intent = new Intent(PantallaConfiguracion.this, PantallaConfiguracionAdministrador.class);
                    startActivityForResult(intent, 0);
                }
                else {
                    Toast.makeText(PantallaConfiguracion.this, "Acceso Denegado" , Toast.LENGTH_SHORT).show();
                }

            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }
}
