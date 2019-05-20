package com.example.findjobsrdv0;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PantallaDetallesEmpleo extends AppCompatActivity {

    TextView Notificacion;
    ProgressDialog progressDialog;
    private Bundle bundle;
    private ImageView MostImagen;

    TextView TvNombreEmpleoDE,TvNombreEmpresaDE,TvProvinciaDE,TvDireccionDE,TvEmailDE,TvTelefonoDE,
            TvPaginaWebDE,TvJornadaDE,TvMostrarHorarioDE,TvTipoContratoDE,TvCantidadVacantesDE,
            TvSalarioDE,TvAreaDE,TvAnosExperienciaDE,TvFormacionAcademicaDE,TvIdiomasDE,TvSexoRequeridoDE,
            TvRangoEdadDE,TvNotaDE,TvEstadoEmpleoDE,TvFechaPublicacionDE;

    Button BtnAplicarEmpleoDE,BtnVerificacionEmpresaDE;

    String sEmpleoIdE = "";

    FirebaseDatabase database;
    DatabaseReference DBempleos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_empleo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetalle);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDetalle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        //Firebase
        database = FirebaseDatabase.getInstance();
        DBempleos = database.getReference("empleos");

        MostImagen = (ImageView) findViewById(R.id.xmlImagenEmpleoE);

        TvNombreEmpleoDE = (TextView) findViewById(R.id.xmlTvNombreEmpleoDE);
        TvNombreEmpresaDE = (TextView) findViewById(R.id.xmlTvNombreEmpresaDE);
        TvProvinciaDE = (TextView) findViewById(R.id.xmlTvProvinciaDE);
        TvDireccionDE = (TextView) findViewById(R.id.xmlTvDireccionDE);
        TvTelefonoDE = (TextView) findViewById(R.id.xmlTvTelefonoDE);
        TvEmailDE = (TextView) findViewById(R.id.xmlTvEmailDE);
        TvPaginaWebDE = (TextView) findViewById(R.id.xmlTvPaginaWebDE);
        TvJornadaDE = (TextView) findViewById(R.id.xmlTvJornadaDE);
        TvMostrarHorarioDE = (TextView) findViewById(R.id.xmlTvMostrarHorarioDE);
        TvTipoContratoDE = (TextView) findViewById(R.id.xmlTvTipoContratoDE);
        TvCantidadVacantesDE = (TextView) findViewById(R.id.xmlTvCantidadVacantesDE);
        TvSalarioDE = (TextView) findViewById(R.id.xmlTvSalarioDE);
        TvAreaDE = (TextView) findViewById(R.id.xmlTvAreaDE);
        TvAnosExperienciaDE = (TextView) findViewById(R.id.xmlTvAnosExperienciaDE);
        TvFormacionAcademicaDE = (TextView) findViewById(R.id.xmlTvFormacionAcademicaDE);
        TvIdiomasDE = (TextView) findViewById(R.id.xmlTvIdiomasDE);
        TvSexoRequeridoDE = (TextView) findViewById(R.id.xmlTvSexoRequeridoDE);
        TvRangoEdadDE = (TextView) findViewById(R.id.xmlTvRangoEdadDE);
        TvNotaDE = (TextView) findViewById(R.id.xmlTvNotaDE);
        TvEstadoEmpleoDE = (TextView) findViewById(R.id.xmlTvEstadoEmpleoDE);
        TvFechaPublicacionDE = (TextView) findViewById(R.id.xmlTvFechaPublicacionDE);


        if(getIntent() != null){
            sEmpleoIdE = getIntent().getStringExtra("sEmpleoIdBuscado");
            if(!sEmpleoIdE.isEmpty()){

                goDetalleEmpleo(sEmpleoIdE);
            }
        }

        BtnAplicarEmpleoDE= (Button) findViewById(R.id.xmlBtnAplicarEmpleoDE);
        TvPaginaWebDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPaginaWeb();
            }
        });

        TvAreaDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleArea();
            }
        });

        TvProvinciaDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleProvincia();
            }
        });


    }

    private void goDetalleEmpleo(String sEmpleoIdE) {
        DBempleos.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos= dataSnapshot.getValue(Empleos.class);
                //Log.d("holap", String.valueOf(empleos));

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(MostImagen);

                TvNombreEmpleoDE.setText(empleos.getsNombreEmpleoE().toUpperCase());
                TvNombreEmpresaDE.setText(empleos.getsNombreEmpresaE());
                TvProvinciaDE.setText(empleos.getsProvinciaE());
                TvDireccionDE.setText(empleos.getsDireccionE());
                TvTelefonoDE.setText(empleos.getsTelefonoE());
                TvPaginaWebDE.setText(empleos.getsPaginaWebE());
                TvJornadaDE.setText(empleos.getsJornadaE());
                TvMostrarHorarioDE.setText(empleos.getsHorarioE());
                TvTipoContratoDE.setText(empleos.getsTipoContratoE());
                TvCantidadVacantesDE.setText(empleos.getsCantidadVacantesE());
                TvSalarioDE.setText(empleos.getsSalarioE());
                TvAreaDE.setText(empleos.getsAreaE());
                TvAnosExperienciaDE.setText(empleos.getsAnosExperienciaDE());
                TvFormacionAcademicaDE.setText(empleos.getsFormacionAcademica());
                TvIdiomasDE.setText(empleos.getsMostrarIdioma());
                TvSexoRequeridoDE.setText(empleos.getsSexoRequeridoE());
                TvRangoEdadDE.setText(empleos.getsRangoE());
                TvNotaDE.setText(empleos.getsOtrosDatosE());
                TvEstadoEmpleoDE.setText(empleos.getsEstadoEmpleoE());
                TvFechaPublicacionDE.setText(empleos.getsFechaPublicacionE());

//String klk= empleos.getsProvinciaE();
                //Log.d("pagina",empleos.getsPaginaWebE());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goPaginaWeb(){

        Intent intent = new Intent(PantallaDetallesEmpleo.this, PantallaNavegador.class);
        intent.putExtra("sPaginaWebDE",TvPaginaWebDE.getText().toString().trim());

        String hola=TvPaginaWebDE.getText().toString().trim();
        Log.d("klkpaginaweb",hola);
        startActivity(intent);

    }

    public void goDetalleProvincia(){

        Intent intent = new Intent(PantallaDetallesEmpleo.this, PantallaDetallesProvincia.class);
        intent.putExtra("sProvinciaDE",TvProvinciaDE.getText().toString().trim());

        String hola=TvProvinciaDE.getText().toString().trim();
        Log.d("klkprovincia",hola);
        startActivity(intent);

    }

    public void goDetalleArea(){

        Intent intent = new Intent(PantallaDetallesEmpleo.this, PantallaDetallesArea.class);
        intent.putExtra("sAreaDE",TvAreaDE.getText().toString().trim());

        String hola=TvAreaDE.getText().toString().trim();
        Log.d("klkarea",hola);
        startActivity(intent);

    }
}
