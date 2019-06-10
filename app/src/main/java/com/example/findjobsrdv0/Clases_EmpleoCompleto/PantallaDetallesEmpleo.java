package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesArea;
import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesProvincia;
import com.example.findjobsrdv0.GeneralesApp.PantallaNavegador;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaDetallesEmpleo extends AppCompatActivity {

    TextView Notificacion;
    ProgressDialog progressDialog;
    private Bundle bundle;
    private ImageView MostImagen;
    private DatabaseReference verificacionEmpleadores;
    private DatabaseReference AplicarEmpleoDataBase;
    private DatabaseReference CurriculosDataBase;

    //Query CurriculosDataBase;


    TextView TvNombreEmpleoDE,TvNombreEmpresaDE,TvProvinciaDE,TvDireccionDE,TvEmailDE,TvTelefonoDE,
            TvPaginaWebDE,TvJornadaDE,TvMostrarHorarioDE,TvTipoContratoDE,TvCantidadVacantesDE,
            TvSalarioDE,TvAreaDE,TvAnosExperienciaDE,TvFormacionAcademicaDE,TvIdiomasDE,TvSexoRequeridoDE,
            TvRangoEdadDE,TvNotaDE,TvEstadoEmpleoDE,TvFechaPublicacionDE;

    Button BtnAplicarEmpleoDE,BtnVerificacionEmpresaDE;

    private String sEmpleoIdE = "";
    private String sIdEmpleador = "";
    private String sIdBuscador = "";

    private Boolean sVerificarEmpleador;



    FirebaseDatabase database;
    DatabaseReference DBempleos;

    ////////////////////////////

    private String sIdAplicarEmpleo, sIdCurriculoAplico,sIdEmpleoAplico,sIdPersonaAplico,sFechadeAplicacion;
 //////////////////////

    private FirebaseAuth mAuthEmpleador;
    private FirebaseUser user;

    private TextView TvOtrosDatosDE, TvRequisitosDE,TvtiNotaDE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_empleo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetalle);
        setSupportActionBar(toolbar);

        TvOtrosDatosDE = (TextView) findViewById(R.id.xmlTiDatosEspecificosDE);
        Typeface face=Typeface.createFromAsset(getAssets(),"fonts/robotoslab.bold.ttf");
        TvOtrosDatosDE.setTypeface(face);

        TvRequisitosDE = (TextView) findViewById(R.id.xmlTiRequisitosDE);
        TvRequisitosDE.setTypeface(face);

        TvtiNotaDE = (TextView) findViewById(R.id.xmlTiNotaDE);
        TvtiNotaDE.setTypeface(face);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabDetalle);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
//        database.setPersistenceEnabled(true);

        //Firebase
        database = FirebaseDatabase.getInstance();
        DBempleos = database.getReference("empleos");
        AplicarEmpleoDataBase = database.getReference("EmpleosConCandidatos");
        CurriculosDataBase = database.getReference();

        //Firebase
        //database = FirebaseDatabase.getInstance();
        //verificacionEmpleadores = database.getReference("Empleadores");
        verificacionEmpleadores = database.getReference("Empleadores");
        //verificacionEmpleadores.orderByChild("Nombre").equalTo("La Sirena");



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

        BtnVerificacionEmpresaDE = (Button) findViewById(R.id.xmlBtnVerificacionEmpresaDE);
        BtnVerificacionEmpresaDE.setVisibility(View.INVISIBLE);




        if(getIntent() != null){
            sEmpleoIdE = getIntent().getStringExtra("sEmpleoIdBuscado");
            if(!sEmpleoIdE.isEmpty()){

                goDetalleEmpleo(sEmpleoIdE);
            }
        }


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

        Log.d("hamaca", sIdEmpleador);

        mAuthEmpleador = FirebaseAuth.getInstance();
        user = mAuthEmpleador.getCurrentUser();
        sIdPersonaAplico= user.getUid();
        Log.d("usuario",sIdPersonaAplico);

        BtnAplicarEmpleoDE= (Button) findViewById(R.id.xmlBtnAplicarEmpleoDE);
        BtnAplicarEmpleoDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AplicarEmpleo(sIdPersonaAplico,sEmpleoIdE);
            }
        });

        //VerificacionEmpresa(sIdEmpleador);

        //Log.d("holap", perro);
        //Log.d("Verificacion", perro1);
        //SharedPreferences preferences= this.getSharedPreferences("UserPrefEmpleador", Context.MODE_PRIVATE);
        //String Nombre= preferences.getString("Nombre", "Nombre");

        CurriculosDataBase.child("Curriculos").orderByChild("sIdBuscadorEmpleo").equalTo(sIdPersonaAplico).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();
                Log.d("holap", String.valueOf(dataSnapshot));

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child("sIdCurriculo").getValue(String.class);
                    //areas.add(areaName);
                    Log.d("holap", areaName);
                    sIdCurriculoAplico = areaName;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PantallaDetallesEmpleo.this, "Usted No tiene empleo registrados", Toast.LENGTH_LONG).show();


            }
        });
    }



    private void goDetalleEmpleo(String sEmpleoIdE) {
        DBempleos.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos= dataSnapshot.getValue(Empleos.class);
                Log.d("klk", String.valueOf(dataSnapshot));

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
                TvAnosExperienciaDE.setText(empleos.getsAnosExperienciaE());
                TvFormacionAcademicaDE.setText(empleos.getsFormacionAcademica());
                TvIdiomasDE.setText(empleos.getsMostrarIdioma());
                TvSexoRequeridoDE.setText(empleos.getsSexoRequeridoE());
                TvRangoEdadDE.setText(empleos.getsRangoE());
                TvNotaDE.setText(empleos.getsOtrosDatosE());
                TvEstadoEmpleoDE.setText(empleos.getsEstadoEmpleoE());
                TvFechaPublicacionDE.setText(empleos.getsFechaPublicacionE());

                //sIdEmpleador = empleos.getsIdEmpleador();
                sIdEmpleador = dataSnapshot.child("sIdEmpleadorE").getValue(String.class);

///String klk= empleos.getsProvinciaE();
                Log.d("pagina",String.valueOf(sIdEmpleador));
                VerificacionEmpresa(sIdEmpleador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void VerificacionEmpresa(String sIdEmpleador){
        verificacionEmpleadores.child(sIdEmpleador).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("holapkkk", String.valueOf(dataSnapshot));
                //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
                sVerificarEmpleador = dataSnapshot.child("sVerificacionEmpleador").getValue(Boolean.class);
                //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
                Log.d("holapkkk", String.valueOf(sVerificarEmpleador));

                if(sVerificarEmpleador==null) {
                    Toast.makeText(PantallaDetallesEmpleo.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG).show();

                }else {

                    if (sVerificarEmpleador == true) {
                        BtnVerificacionEmpresaDE.setVisibility(View.VISIBLE);
                    }
                    if (sVerificarEmpleador == false) {
                        BtnVerificacionEmpresaDE.setVisibility(View.INVISIBLE);
                    }
                }
                //String perro1 = dataSnapshot.child("Verificacion").getValue(String.class);
                 //Log.d("holap", String.valueOf(perro));
                //Log.d("Verificacion", perro1);
                //Picasso.get().load(empleos.getsImagenEmpleoE()).into(MostImagen);
                //TvNombreEmpleoDE.setText(dataSnapshot.child("Nombre").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PantallaDetallesEmpleo.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG).show();

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

    public void AplicarEmpleo(String sIdPersonaAplico,String sEmpleoIdE) {
        //final String Curriculo = "Y9NcTUy9ZwRDtQps7y2INqSzw1v1";
        //String Empleo= "Empleo";
        //String Fecha= "hoy";
        //String sIdBuscador = "";

        //String sIdPersonaAplicoE=/*
        /*Query query = CurriculosDataBase.orderByChild("cIdBuscador").equalTo(Curriculo);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("curriculo", String.valueOf(dataSnapshot));

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
        if(dataSnapshot.child("cCodigoId").equals(Curriculo)) {


    Log.d("curriculo", String.valueOf(snapshot));
    //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
    sIdBuscador = dataSnapshot.child("cCodigoId").getValue(String.class);
    //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
    Log.d("perro", String.valueOf(sIdBuscador));
}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/
///////////////////////////////////////////
        /*
        CurriculosDataBase.child("Curriculos").orderByChild("cIdBuscador").equalTo("39UKAKAN9NMxPYHWVguMNbnHnoq1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();
                Log.d("holap", String.valueOf(dataSnapshot));

                for (DataSnapshot areaSnapshot: dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child("cCodigoId").getValue(String.class);
                    //areas.add(areaName);
                    Log.d("holap", areaName);
                    sIdCurriculoAplico = areaName;
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        ///////////////////////////////////////////////////

        //sIdCurriculoAplico = sIdBuscador;
        sIdEmpleoAplico = sEmpleoIdE;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        sFechadeAplicacion = simpleDateFormat.format(new Date());

        String sIdAplicarEmpleo = AplicarEmpleoDataBase.push().getKey();

        AplicarEmpleo aplicarEmpleo = new AplicarEmpleo(sIdAplicarEmpleo,sIdCurriculoAplico,sIdEmpleoAplico,sIdPersonaAplico,sFechadeAplicacion);
        AplicarEmpleoDataBase.child(sIdAplicarEmpleo).setValue(aplicarEmpleo);

        Toast.makeText(this, "Su Aplicacion se realizo exitosamente", Toast.LENGTH_LONG).show();


    }
}
