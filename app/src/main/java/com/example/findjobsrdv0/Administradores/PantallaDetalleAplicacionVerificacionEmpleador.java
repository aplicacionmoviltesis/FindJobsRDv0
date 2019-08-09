package com.example.findjobsrdv0.Administradores;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.findjobsrdv0.Adaptadores_Empleador.AplicarVerificacionEmpleador;
import com.example.findjobsrdv0.Adaptadores_Empleador.Empleadores;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.PantallaListaEmpleosEmpresa;
import com.example.findjobsrdv0.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class PantallaDetalleAplicacionVerificacionEmpleador extends AppCompatActivity {

    private EditText editRNCEmpleadorADM, editNombreEmpleadorADM, editPaginaWebEmpleadorADM,
            editTelefonoEmpleadorADM, editDescEmpleadorADM, editEmailEmpleadorADM, editProvEmpleadorADM,
            editDireccionEmpleadorADM;

    private String RNCEmpleadorADM, NombreEmpleadorADM, PaginaWebEmpleadorADM,
            TelefonoEmpleadorADM, DescEmpleadorADM, EmailEmpleadorADM, ProvEmpleadorADM,
            DireccionEmpleadorADM;

    private String sIdVerifEmp, sIdEmpleadorVerifEmp, sNombreDocumVerifEmp, sDocumentoVerifEmp, sEstado, sFechaVerifEmp;

    private String IdEmpleador;
    private String imagen;

    private String sIdEmpleadorAdmin = "";

    private ImageView fotoEmpleador;

    private Spinner spinVerificacionEmpleador;

    private DatabaseReference DBEmpleadores;
    private FirebaseDatabase databaseEmpleadores;

    private DatabaseReference DBVerificarEmp;
    private FirebaseDatabase databaseDBVerificarEmp;

    private Boolean EstadoVerificacionEmp;
    private String valorspinner;
    private Boolean Estado;

    private Button btnCambiarVerificacionADM, btnVerDocumentosADM, btnVerEmpleosAnadidosADM;

    String estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalle_aplicacion_verificacion_empleador);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Datos Empleador");


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Detalle de Solicitud", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        databaseEmpleadores = FirebaseDatabase.getInstance();
        DBEmpleadores = databaseEmpleadores.getReference("Empleadores");

        databaseDBVerificarEmp = FirebaseDatabase.getInstance();
        DBVerificarEmp = databaseDBVerificarEmp.getReference("SolicitudVerificacionEmpleador");




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

        if (getIntent() != null) {
            sIdEmpleadorAdmin = getIntent().getStringExtra("detalleverifID");
            if (!sIdEmpleadorAdmin.isEmpty()) {
                Log.d("klkId", String.valueOf(sIdEmpleadorAdmin));

                DBVerificarEmp.child(sIdEmpleadorAdmin).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        if (dataSnapshot.exists()) {
                            AplicarVerificacionEmpleador aplicarVerificacionEmpleador = dataSnapshot.getValue(AplicarVerificacionEmpleador.class);

                            IdEmpleador = aplicarVerificacionEmpleador.getsIdEmpleadorVerifEmp();

                            goDetalleEmpleador(IdEmpleador);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        }
//        sIdEmpleadorAdmin = "HmAtSRSnxdfxb0Z1kM2qoW1OvNo1";
//        goDetalleEmpleador( sIdEmpleadorAdmin );

        btnCambiarVerificacionADM = (Button) findViewById(R.id.xmlbtnCambiarEmpleadorADM);
        btnCambiarVerificacionADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActualizarPerfilEmpleador(IdEmpleador);

            }
        });

        btnVerDocumentosADM = (Button) findViewById(R.id.xmlbtnVerDocumentosEmpleadorADM);
        btnVerDocumentosADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verdocumento(IdEmpleador);

            }
        });

        btnVerEmpleosAnadidosADM = (Button) findViewById(R.id.xmlbtnVerEmpleosPublicadosADM);
        btnVerEmpleosAnadidosADM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PantallaDetalleAplicacionVerificacionEmpleador.this, PantallaListaEmpleosEmpresa.class);
                intent.putExtra("sEmpresaId", IdEmpleador);
                startActivity(intent);
            }
        });
    }

    private void verdocumento(String IdEmpleador) {
        Log.d("klk3", String.valueOf(IdEmpleador));
        Query query = DBVerificarEmp.orderByChild("sIdEmpleadorVerifEmp").equalTo(IdEmpleador);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("klk", String.valueOf(dataSnapshot));

                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {
                    Log.d("klk1", String.valueOf(dataSnapshot));
                    Log.d("klk2", String.valueOf(FavdataSnapshot));

                    if (FavdataSnapshot.exists()) {
                        AplicarVerificacionEmpleador Datosempleadores = FavdataSnapshot.getValue(AplicarVerificacionEmpleador.class);

                        Intent intent = new Intent();
                        intent.setData(Uri.parse(Datosempleadores.getsDocumentoVerifEmp()));
                        startActivity(intent);

                    } else {
                        Toast.makeText(PantallaDetalleAplicacionVerificacionEmpleador.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void goDetalleEmpleador(String IdEmpleador) {

        DBEmpleadores.child(IdEmpleador).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Empleadores empleadores = dataSnapshot.getValue(Empleadores.class);
                    Log.d("holap", String.valueOf(empleadores));

                    imagen = empleadores.getsImagenEmpleador();

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

                    if (!EstadoVerificacionEmp.equals(null)) {
                        if (EstadoVerificacionEmp.equals(true)) {
                            spinVerificacionEmpleador.setSelection(0);
                        } else {
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

    private void ActualizarPerfilEmpleador(String IdEmpleador) {

//            DBEmpleadores.child( IdEmpleador ).addListenerForSingleValueEvent( new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//
//                        Empleadores empleadores = dataSnapshot.getValue( Empleadores.class );

        RNCEmpleadorADM = editRNCEmpleadorADM.getText().toString().trim();
        NombreEmpleadorADM = editNombreEmpleadorADM.getText().toString().trim();
        PaginaWebEmpleadorADM = editPaginaWebEmpleadorADM.getText().toString().trim();
        TelefonoEmpleadorADM = editTelefonoEmpleadorADM.getText().toString().trim();
        DireccionEmpleadorADM = editDireccionEmpleadorADM.getText().toString().trim();
        EmailEmpleadorADM = editEmailEmpleadorADM.getText().toString().trim();
        ProvEmpleadorADM = editProvEmpleadorADM.getText().toString().trim();
        DescEmpleadorADM = editDescEmpleadorADM.getText().toString().trim();

        valorspinner = spinVerificacionEmpleador.getSelectedItem().toString();

        if (!valorspinner.equals(null)) {
            if (valorspinner.equals("Verificado")) {
                Estado = true;
            } else {
                Estado = false;
            }
        }


        Log.d("verificacion", String.valueOf(valorspinner));

        Empleadores aplicarVerificacionEmpleador = new Empleadores(
                NombreEmpleadorADM, RNCEmpleadorADM, PaginaWebEmpleadorADM,
                TelefonoEmpleadorADM, DireccionEmpleadorADM, EmailEmpleadorADM,
                imagen, Estado, IdEmpleador, DescEmpleadorADM,
                ProvEmpleadorADM);

        DBEmpleadores.child(IdEmpleador).setValue(aplicarVerificacionEmpleador);

        ActualizarSolicitud(IdEmpleador);

    }


    private void ActualizarSolicitud(String IdEmpleador) {
        Query query = DBVerificarEmp.orderByChild("sIdEmpleadorVerifEmp").equalTo(IdEmpleador);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("klk", String.valueOf(dataSnapshot));

                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {
                    Log.d("klk1", String.valueOf(dataSnapshot));

                    AplicarVerificacionEmpleador Datosempleadores = FavdataSnapshot.getValue(AplicarVerificacionEmpleador.class);

                    sIdVerifEmp = Datosempleadores.getsIdVerifEmp();
                    sIdEmpleadorVerifEmp = Datosempleadores.getsIdEmpleadorVerifEmp();
                    sNombreDocumVerifEmp = Datosempleadores.getsNombreDocumVerifEmp();
                    sDocumentoVerifEmp = Datosempleadores.getsDocumentoVerifEmp();
                    sEstado = Datosempleadores.getsEstado();
                    sFechaVerifEmp = Datosempleadores.getsFechaVerifEmp();

                    estado = "Verificado";
                    if (!sEstado.equals(null)) {
                        if (sEstado.equals("Pendiente")) {
                            sEstado = estado;
                        }
                    } else {
                        Toast.makeText(PantallaDetalleAplicacionVerificacionEmpleador.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    AplicarVerificacionEmpleador areasCurriculos = new AplicarVerificacionEmpleador(sIdVerifEmp, sIdEmpleadorVerifEmp,
                            sNombreDocumVerifEmp, sDocumentoVerifEmp, sEstado, sFechaVerifEmp);
                    DBVerificarEmp.child(sIdVerifEmp).setValue(areasCurriculos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}


