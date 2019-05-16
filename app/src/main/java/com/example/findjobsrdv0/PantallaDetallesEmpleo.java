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


        //goProvincia();




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
                goProvincia();
            }
        });


        //bundle= getIntent().getExtras();

        //TvNombreEmpleoDE.setText(bundle.getString("sNombreEmpleoE"));


        //MostImagen.setImageBitmap(bundle.getString("fotoempleo"));
        //TvNombreEmpresaDE.setText(bundle.getString("NomEmpresa"));






    }

    private void goDetalleEmpleo(String sEmpleoIdE) {
        DBempleos.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos= dataSnapshot.getValue(Empleos.class);
                //Log.d("holap", String.valueOf(empleos));

                Picasso.get().load(empleos.getsImagenEmpleoE()).into(MostImagen);


                TvProvinciaDE.setText(empleos.getsProvinciaE());
                TvDireccionDE.setText(empleos.getsDireccionE());
                TvTelefonoDE.setText(empleos.getsTelefonoE());
                TvPaginaWebDE.setText(empleos.getsPaginaWebE());
                TvJornadaDE.setText(empleos.getsJornadaE());

                /*TvMostrarHorarioDE.setText(bundle.getString("PaginaWeb"));
                TvTipoContratoDE.setText(bundle.getString("Direccion"));
                TvCantidadVacantesDE.setText(bundle.getString("Experiencia"));
                TvSalarioDE.setText(bundle.getString("Descripcion"));
                TvAreaDE.setText(bundle.getString("FechaPublicaion"));
                TvAnosExperienciaDE.setText(bundle.getString("Estado"));
                TvFormacionAcademicaDE.setText(bundle.getString("Telefono"));
                TvIdiomasDE.setText(bundle.getString("Email"));
                TvSexoRequeridoDE.setText(bundle.getString("PaginaWeb"));
                TvRangoEdadDE.setText(bundle.getString("Direccion"));
                TvNotaDE.setText(bundle.getString("Experiencia"));
                TvEstadoEmpleoDE.setText(bundle.getString("Descripcion"));
                TvFechaPublicacionDE.setText(bundle.getString("FechaPublicaion"));*/
//String klk= empleos.getsProvinciaE();
                //Log.d("pagina",empleos.getsPaginaWebE());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void goProvincia(){

        Intent intent = new Intent(PantallaDetallesEmpleo.this, PantallaNavegador.class);
        intent.putExtra("sPaginaWebDE",TvPaginaWebDE.getText().toString().trim());

        String hola=TvPaginaWebDE.getText().toString().trim();
        Log.d("klk",hola);
        startActivity(intent);

    }
}
