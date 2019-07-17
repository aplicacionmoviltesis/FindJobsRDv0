package com.example.findjobsrdv0;

import android.content.Intent;
import android.os.Bundle;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.Empleadores;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaListaEmpleosEmpresa;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class PantallaDetalleAplicacionVerificacionEmpleador extends AppCompatActivity {

    private EditText editRNCEmpleadorADM, editNombreEmpleadorADM, editPaginaWebEmpleadorADM,
            editTelefonoEmpleadorADM, editDescEmpleadorADM, editEmailEmpleadorADM, editProvEmpleadorADM,
            editDireccionEmpleadorADM;

    private String sIdEmpleadorAdmin = "";

    private ImageView fotoEmpleador;

    private Spinner spinVerificacionEmpleador;

    private DatabaseReference DBEmpleadores;
    private FirebaseDatabase databaseEmpleadores;

    private Boolean EstadoVerificacionEmp;

    private Button btnCambiarVerificacionADM, btnVerDocumentosADM, btnVerEmpleosAnadidosADM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalle_aplicacion_verificacion_empleador);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        databaseEmpleadores = FirebaseDatabase.getInstance();
        DBEmpleadores = databaseEmpleadores.getReference("Empleadores");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Datos Empleador");

        fotoEmpleador = (ImageView) findViewById(R.id.xmlImagenEmpresaADM);

        editRNCEmpleadorADM = (EditText) findViewById(R.id.xmleditRNCEmpleadorADM);
        editNombreEmpleadorADM = (EditText) findViewById(R.id.xmleditNombreEmpleadorADM);
        editPaginaWebEmpleadorADM = (EditText) findViewById(R.id.xmleditPagWebEmpleadorADM);
        editTelefonoEmpleadorADM = (EditText) findViewById(R.id.xmleditTelefonoEmpleadorADM);
        editDireccionEmpleadorADM = (EditText) findViewById(R.id.xmleditDireccionEmpleadorADM);
        editEmailEmpleadorADM = (EditText) findViewById(R.id.xmleditEmailEmpleadorADM);
        editProvEmpleadorADM = (EditText) findViewById(R.id.xmleditProvEmpleadorADM);
        editDescEmpleadorADM = (EditText) findViewById(R.id.xmleditDescEmpleadorADM);

        spinVerificacionEmpleador = (Spinner) findViewById(R.id.xmlspinVerificacionEmpleador);
        ArrayAdapter<CharSequence> adapterEstadoReport = ArrayAdapter.createFromResource(this,
                R.array.VerificacionEmpleadorAdmin, android.R.layout.simple_spinner_item);
        adapterEstadoReport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinVerificacionEmpleador.setAdapter(adapterEstadoReport);


//        if (getIntent() != null) {
//            sIdEmpleadorAdmin = getIntent().getStringExtra("sIdEmpleadorAdmin");
//            if (!sIdEmpleadorAdmin.isEmpty()) {
//                goDetalleEmpleador(sIdEmpleadorAdmin);
//            }
//        }
        sIdEmpleadorAdmin = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";
        goDetalleEmpleador(sIdEmpleadorAdmin);


        btnCambiarVerificacionADM = (Button) findViewById(R.id.xmlbtnCambiarEmpleadorADM);
        btnCambiarVerificacionADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnVerDocumentosADM = (Button) findViewById(R.id.xmlbtnVerDocumentosEmpleadorADM);
        btnVerDocumentosADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnVerEmpleosAnadidosADM = (Button) findViewById(R.id.xmlbtnVerEmpleosPublicadosADM);
        btnVerEmpleosAnadidosADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaDetalleAplicacionVerificacionEmpleador.this, PantallaListaEmpleosEmpresa.class);
                intent.putExtra("sEmpresaId",sIdEmpleadorAdmin);
                startActivity(intent);
            }
        });
    }

    private void goDetalleEmpleador(String sIdEmpleadorADM) {

        DBEmpleadores.child(sIdEmpleadorADM).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Empleadores empleadores = dataSnapshot.getValue(Empleadores.class);
                    Log.d("holap", String.valueOf(empleadores));

                    if (!empleadores.getsImagenEmpleador().equals(null)) {
                        Picasso.get().load(empleadores.getsImagenEmpleador()).into(fotoEmpleador);
                    }
                    editRNCEmpleadorADM.setText(empleadores.getsRncEmpleador());
                    editNombreEmpleadorADM.setText(empleadores.getsNombreEmpleador());
                    editPaginaWebEmpleadorADM.setText(empleadores.getsPaginaWebEmpleador());
                    editTelefonoEmpleadorADM.setText(empleadores.getsTelefonoEmpleador());
                    editDireccionEmpleadorADM.setText(empleadores.getsDireccionEmpleador());
                    editEmailEmpleadorADM.setText(empleadores.getsCorreoEmpleador());
                    editProvEmpleadorADM.setText(empleadores.getsProvinciaEmpleador());
                    editDescEmpleadorADM.setText(empleadores.getsDescripcionEmpleador());

                    EstadoVerificacionEmp = empleadores.getsVerificacionEmpleador();

                    if (!EstadoVerificacionEmp.equals(null)){
                        if(EstadoVerificacionEmp.equals(true)){
                            spinVerificacionEmpleador.setSelection(0);
                        }
                        else {
                            spinVerificacionEmpleador.setSelection(1);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
