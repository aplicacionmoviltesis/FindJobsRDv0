package com.example.findjobsrdv0.Clases_EmpleoCompleto;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesArea;
import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesProvincia;
import com.example.findjobsrdv0.GeneralesApp.PantallaNavegador;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.cPantallaRegistrarCurriculo;
import com.example.findjobsrdv0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PantallaDetallesEmpleo extends AppCompatActivity { //implements CompoundButton.OnCheckedChangeListener {

    TextView Notificacion;
    ProgressDialog progressDialog;
    private Bundle bundle;
    private ImageView MostImagen;
    private DatabaseReference verificacionEmpleadores;
    private DatabaseReference AplicarEmpleoDataBase, DbLikes;
    private DatabaseReference CurriculosDataBase;

    //Query CurriculosDataBase;


    TextView TvNombreEmpleoDE, TvNombreEmpresaDE, TvProvinciaDE, TvDireccionDE, TvEmailDE, TvTelefonoDE,
            TvPaginaWebDE, TvJornadaDE, TvMostrarHorarioDE, TvTipoContratoDE, TvCantidadVacantesDE,
            TvSalarioDE, TvAreaDE, TvAnosExperienciaDE, TvFormacionAcademicaDE, TvIdiomasDE, TvSexoRequeridoDE,
            TvRangoEdadDE, TvNotaDE, TvEstadoEmpleoDE, TvFechaPublicacionDE;

    Button BtnAplicarEmpleoDE, BtnVerificacionEmpresaDE;

    private String sEmpleoIdE = "";
    private String sIdEmpleador = "";
    private String sIdBuscador = "";

    private Boolean sVerificarEmpleador;


    FirebaseDatabase database;
    DatabaseReference DBempleos;

    ////////////////////////////

    private String sIdAplicarEmpleo, sIdCurriculoAplico, sIdEmpleoAplico, sIdPersonaAplico, sFechadeAplicacion;
    //////////////////////

    private FirebaseAuth mAuthEmpleador;
    private FirebaseUser user;

    private TextView TvOtrosDatosDE, TvRequisitosDE, TvtiNotaDE;

//---------  para los favoritos ---------------------------------------------------------------------------------------------------------------------------------------------------------

    FirebaseDatabase prueba;
    ToggleButton btnfavorito;

//----------- para los favoritos  -------------------------------------------------------------------------------------------------------------------------------------------------------


    private Button btndDislike, btnLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_detalles_empleo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetalle);
        setSupportActionBar(toolbar);

//----------- para los favoritos  -------------------------------------------------------------------------------------------------------------------------------------------------------

        prueba = FirebaseDatabase.getInstance();
        DbLikes = prueba.getReference();
        DbLikes.keepSynced(true);



//----------- para los favoritos  -------------------------------------------------------------------------------------------------------------------------------------------------------


        TvOtrosDatosDE = (TextView) findViewById(R.id.xmlTiDatosEspecificosDE);
        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/robotoslab.bold.ttf");
        TvOtrosDatosDE.setTypeface(face);

        TvRequisitosDE = (TextView) findViewById(R.id.xmlTiRequisitosDE);
        TvRequisitosDE.setTypeface(face);

        TvtiNotaDE = (TextView) findViewById(R.id.xmlTiNotaDE);
        TvtiNotaDE.setTypeface(face);

        // btndDislike = (Button) findViewById(R.id.btn_dislike);
        // btnLike = (Button) findViewById(R.id.btn_like);



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
        DbLikes = database.getReference();
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

        mAuthEmpleador = FirebaseAuth.getInstance();
        user = mAuthEmpleador.getCurrentUser();
        sIdPersonaAplico = user.getUid();
        Log.d("usuario", sIdPersonaAplico);

        if (getIntent() != null) {
            sEmpleoIdE = getIntent().getStringExtra("sEmpleoIdBuscado");
            if (!sEmpleoIdE.isEmpty()) {

                goDetalleEmpleo(sEmpleoIdE);
                VerificarFavorito();
            }
        }

        btnfavorito = (ToggleButton) findViewById(R.id.empleosfavoritos);
        btnfavorito.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d("estado", String.valueOf(b));

                if (b) {
                    marcarFavorito(sEmpleoIdE);
                    Log.d("estado activo", String.valueOf(b));
                } else {
                    final Query prueba = DbLikes.child("BuscadoresEmpleos").child(sIdPersonaAplico)
                            .child("likes").orderByChild("IdEmpleoLike").equalTo(sEmpleoIdE);

                    prueba.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                                pruebadataSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText(PantallaDetallesEmpleo.this, "no funciono", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Log.d("estado inactivo", String.valueOf(b));

                }
            }
        });


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
        sIdPersonaAplico = user.getUid();

        Log.d("usuario", sIdPersonaAplico);


        BtnAplicarEmpleoDE = (Button) findViewById(R.id.xmlBtnAplicarEmpleoDE);
        BtnAplicarEmpleoDE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AplicarEmpleo(sIdPersonaAplico, sEmpleoIdE);
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

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {

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
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
        //   sEmpleoIdE = "-Lg4YqLFDOwMHBe7RNbz";


        // mantenerRojo();


        //  mantenercolor();
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

    }


//------------------------------------------------------------------------------------------------------------------------------------------------------------------

  /*  private void testToggle(){
        mcolorRed.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                Toast.makeText( PantallaDetallesEmpleoPrueba.this, "quitaste el like mamon", Toast.LENGTH_SHORT ).show();

                return mgesturedetector.onTouchEvent( event );
            }
        } );

        mcolorWhile.setOnTouchListener( new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                addNewLike();
                return mgesturedetector.onTouchEvent( event );

            }
        } );

    }

    private void addNewLike(){

        //Log.d(TAG, "addNewLike: adding new like");

        String newLikeID = DbLikes.push().getKey();
        //Like like = new Like();
        //like.setUser_id(FirebaseAuth.getInstance().getCurrentUser().getUid());

      */



       /* DbLikes.child("Empleadores")//referencia empleadores
                .child("HmAtSRSnxdfxb0Z1kM2qoW1OvNo1")//referencia usuario
                .child("likes")//referencia likes
                .child(newLikeID)
                .child("IdEmpleoLike")
                .setValue(sEmpleoIdE);
*/
        /*myRef.child(getString(R.string.dbname_user_photos))
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child(mPhoto.getPhoto_id())
                .child(getString(R.string.field_likes))
                .child(newLikeID)
                .setValue(like);*/

    //mHeart.toggleLike();
    //getLikesString();
/*    }


    public class GestureListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            mHeartFavorito.togglelike();
            Toast.makeText( PantallaDetallesEmpleoPrueba.this, "", Toast.LENGTH_SHORT ).show();
            return true;
        }
    }*/
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

/*
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            Toast.makeText( this, "klklklklklk", Toast.LENGTH_SHORT ).show();
            compoundButton.setChecked( false );
            return;
        }



        DatabaseReference databaseReferenceFav = FirebaseDatabase.getInstance().getReference("EmpleosConFavoritos");

        if (b){
            databaseReferenceFav.child( FirebaseAuth.getInstance().getCurrentUser().getUid() );

        }else {
            databaseReferenceFav.child( null );

        }

    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/

    private void goDetalleEmpleo(String sEmpleoIdE) {
        DBempleos.child(sEmpleoIdE).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos = dataSnapshot.getValue(Empleos.class);
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
                TvFormacionAcademicaDE.setText(empleos.getsFormacionAcademicaE());
                TvIdiomasDE.setText(empleos.getsMostrarIdiomaE());
                TvSexoRequeridoDE.setText(empleos.getsSexoRequeridoE());
                TvRangoEdadDE.setText(empleos.getsRangoE());
                TvNotaDE.setText(empleos.getsOtrosDatosE());
                TvEstadoEmpleoDE.setText(empleos.getsEstadoEmpleoE());
                TvFechaPublicacionDE.setText(empleos.getsFechaPublicacionE());

                //sIdEmpleador = empleos.getsIdEmpleador();
                sIdEmpleador = dataSnapshot.child("sIdEmpleadorE").getValue(String.class);

///String klk= empleos.getsProvinciaE();
                Log.d("pagina", String.valueOf(sIdEmpleador));
                VerificacionEmpresa(sIdEmpleador);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void VerificacionEmpresa(String sIdEmpleador) {
        verificacionEmpleadores.child(sIdEmpleador).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("holapkkk", String.valueOf(dataSnapshot));
                //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
                sVerificarEmpleador = dataSnapshot.child("sVerificacionEmpleador").getValue(Boolean.class);
                //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
                Log.d("holapkkk", String.valueOf(sVerificarEmpleador));

                if (sVerificarEmpleador == null) {
                    Toast.makeText(PantallaDetallesEmpleo.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG).show();

                } else {

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

    public void goPaginaWeb() {

        Intent intent = new Intent(PantallaDetallesEmpleo.this, PantallaNavegador.class);
        intent.putExtra("sPaginaWebDE", TvPaginaWebDE.getText().toString().trim());

        String hola = TvPaginaWebDE.getText().toString().trim();
        Log.d("klkpaginaweb", hola);
        startActivity(intent);

    }

    public void goDetalleProvincia() {

        Intent intent = new Intent(PantallaDetallesEmpleo.this, PantallaDetallesProvincia.class);
        intent.putExtra("sProvinciaDE", TvProvinciaDE.getText().toString().trim());

        String hola = TvProvinciaDE.getText().toString().trim();
        Log.d("klkprovincia", hola);
        startActivity(intent);

    }

    public void goDetalleArea() {

        Intent intent = new Intent(PantallaDetallesEmpleo.this, PantallaDetallesArea.class);
        intent.putExtra("sAreaDE", TvAreaDE.getText().toString().trim());

        String hola = TvAreaDE.getText().toString().trim();
        Log.d("klkarea", hola);
        startActivity(intent);

    }

    public void AplicarEmpleo(String sIdPersonaAplico, String sEmpleoIdE) {
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
        Log.d("klkcurri", String.valueOf(sIdCurriculoAplico));

        if (sIdCurriculoAplico != null) {

            if (!sIdCurriculoAplico.isEmpty()) {

                String sIdAplicarEmpleo = AplicarEmpleoDataBase.push().getKey();

                AplicarEmpleo aplicarEmpleo = new AplicarEmpleo(sIdAplicarEmpleo, sIdCurriculoAplico, sIdEmpleoAplico, sIdPersonaAplico, sFechadeAplicacion);
                AplicarEmpleoDataBase.child(sIdAplicarEmpleo).setValue(aplicarEmpleo);

                Toast.makeText(this, "Su Aplicacion se realizo exitosamente", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Usted Aun No tiene Ningun Empleo Registrado", Toast.LENGTH_LONG).show();
            BtnAplicarEmpleoDE.setEnabled(false);

            AlertDialog.Builder builder1 = new AlertDialog.Builder(PantallaDetallesEmpleo.this);
            builder1.setMessage("Desea Registrar su Curriculo?");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(PantallaDetallesEmpleo.this, cPantallaRegistrarCurriculo.class);
                            startActivityForResult(intent, 0);
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    private void VerificarFavorito() {
        final Query q = DbLikes.child("BuscadoresEmpleos")//referencia empleadores
                .child(sIdPersonaAplico)//referencia usuario
                .child("likes")//referencia likes
                .orderByChild("IdEmpleoLike").equalTo(sEmpleoIdE);
//        Log.d( "fav", String.valueOf( sEmpleoIdE ) );
//        Log.d( "fav", String.valueOf( sIdPersonaAplico ) );

        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("fav", String.valueOf(dataSnapshot));
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                    if (FavdataSnapshot != null) {
                        btnfavorito.setChecked(true);


                    } else {
                        btnfavorito.setChecked(false);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void marcarFavorito(String sEmpleoIdEFav) {

//        Query query  = DbLikes.child("BuscadoresEmpleos")
//                .child(sIdPersonaAplico)
//                .child("likes")
//                .orderByChild("IdEmpleoLike").equalTo("-Lg4alOTsAzGLKms6tmu");
//        query.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Log.d( "favValidacion", String.valueOf( dataSnapshot ) );
//                Log.d( "favValidacion", String.valueOf( dataSnapshot.getValue() ) );
//                Log.d( "favValidacionB", String.valueOf( dataSnapshot.getValue() ) );
//
//                String prueba = dataSnapshot.getValue().toString();
//                Log.d( "favValidacionpru", prueba);
//
//                if (prueba.equals("null")){
//                    //etCedula.setError( "error " );
//
//                    String newLikeID = DbLikes.push().getKey();
//
//                    DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
//                            .child( sIdPersonaAplico )//referencia usuario
//                            .child( "likes" )//referencia likes
//                            .child( newLikeID )
//                            .child( "IdEmpleoLike" )
//                            .setValue( sEmpleoIdE );
//
//                }
//                else {
//                    Toast.makeText( PantallaDetallesEmpleo.this, "Favorito repetido", Toast.LENGTH_LONG ).show();
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );

        Query q = DbLikes.child("BuscadoresEmpleos")
                .child(sIdPersonaAplico)
                .child("likes")
                .orderByChild("IdEmpleoLike").equalTo(sEmpleoIdEFav);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    //Toast.makeText(PantallaDetallesEmpleo.this, "Favorito si", Toast.LENGTH_LONG).show();
        String newLikeID = DbLikes.push().getKey();

        DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
                .child( sIdPersonaAplico )//referencia usuario
                .child( "likes" )//referencia likes
                .child( newLikeID )
                .child( "IdEmpleoLike" )
                .setValue( sEmpleoIdE );
                }
                //existe
                else {
                    //Toast.makeText(PantallaDetallesEmpleo.this, "Favorito repetido", Toast.LENGTH_LONG).show();

                }
            }
            //no existe


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


//        String newLikeID = DbLikes.push().getKey();
//
//        DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
//                .child( sIdPersonaAplico )//referencia usuario
//                .child( "likes" )//referencia likes
//                .child( newLikeID )
//                .child( "IdEmpleoLike" )
//                .setValue( sEmpleoIdE );
    }
}
