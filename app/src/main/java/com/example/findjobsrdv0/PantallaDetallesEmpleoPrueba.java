package com.example.findjobsrdv0;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.AplicarEmpleo;
import com.example.findjobsrdv0.Clases_EmpleoCompleto.Empleos;
import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesArea;
import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesProvincia;
import com.example.findjobsrdv0.GeneralesApp.PantallaNavegador;
import com.example.findjobsrdv0.Pantallas_CurriculosCompleto.cPantallaRegistrarCurriculo;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class PantallaDetallesEmpleoPrueba extends AppCompatActivity { //implements CompoundButton.OnCheckedChangeListener {

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
    DatabaseReference DbLikesFavorito;
    String sEmpleoIdFavoritos;

    CheckBox checkBoxFav;

    String userActivo;

    //----------- para los favoritos  -------------------------------------------------------------------------------------------------------------------------------------------------------

//    private DatabaseReference favoritos;
//    ImageButton mImageButton;
//    private boolean mProcessLike = false;

    //    DatabaseReference mDatabaseLike;
//    FirebaseAuth mFirebaseAuth;
    ToggleButton btnfavorito;

    //------------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
    private ImageView mcolorRed;
    private ImageView mcolorWhile;

    private GestureDetector mgesturedetector;

    private HeartFavorito mHeartFavorito;*/
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

    List<Category> categoryList;
    RecyclerView recyclerView;
    WallpapersAdapter adapter;

    DatabaseReference dbWallpapers;
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_pantalla_detalles_empleo );
        Toolbar toolbar = (Toolbar) findViewById( R.id.toolbarDetalle );
        setSupportActionBar( toolbar );

//        favoritos = FirebaseDatabase.getInstance().getReference().child( "EmpleosConFavoritos" );
//        favoritos.keepSynced( true );


//        mDatabaseLike = FirebaseDatabase.getInstance().getReference().child( "EmpleosConFavoritos" );
//        mFirebaseAuth = FirebaseAuth.getInstance();
//        userActivo = FirebaseAuth.getInstance().getCurrentUser().getUid();

//
//        mDatabaseLike.keepSynced( true );

        // mImageButton = (ImageButton) findViewById( R.id.ImageButtomfav );

//        mImageButton.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //marcarFavorito();
//            }
//        } );

        /*checkBoxFav.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(checkBoxFav.isChecked()){
                    Log.d("activo", String.valueOf( b ) );
                }
                else {
                    Log.d("inactivo", String.valueOf( b ) );

                }
            }
        } );*/

//        checkBoxFav.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // update your model (or other business logic) based on isChecked
//            }
//        } );


//----------- para los favoritos  -------------------------------------------------------------------------------------------------------------------------------------------------------


        prueba = FirebaseDatabase.getInstance();
        DbLikes = prueba.getReference();
        DbLikes.keepSynced( true );

        //  checkBoxFav = (CheckBox) findViewById( R.id.checkboxFavoritoEmpleoklk );
        //this.checkBoxFav.setSelected(true);
        //checkBoxFav.setSelected( true );

        btnfavorito = (ToggleButton) findViewById( R.id.empleosfavoritos );
        btnfavorito.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d( "estado", String.valueOf( b ) );
                //boolean x = b;
                //b= !b;
                //  Log.d("estado2", String.valueOf( b ) );

                if (b) {
//                    if (b != true) {
                    marcarFavorito();
//
//                    }
                    /*final Query q = DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
                            .child( sIdPersonaAplico )//referencia usuario
                            .child( "likes" )//referencia likes
                            .orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );
                    Log.d( "fav", String.valueOf( sEmpleoIdE ) );
                    Log.d( "fav", String.valueOf( sIdPersonaAplico ) );

                    q.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d( "fav", String.valueOf( dataSnapshot ) );
                            for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                                if (FavdataSnapshot != null) {
//                                    mImageButton.setImageResource( R.mipmap.likerojo );
//                                    checkBoxFav.setChecked( true );
//                                    btnfavorito.setChecked( true );
                                    marcarFavorito();


                                } else {
//                                    mImageButton.setImageResource( R.mipmap.likegris );
//                                    checkBoxFav.setChecked( false );
//                                    btnfavorito.setChecked( false );

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );*/

                    Log.d( "estado activo", String.valueOf( b ) );

                    //lanzaM( "ON" );
                    //cambiarColorTB( android.R.color.holo_red_dark, android.R.color.holo_red_dark );
                } else {

                    final Query prueba = DbLikes.child("BuscadoresEmpleos").child(userActivo)
                            .child("likes").orderByChild("IdEmpleoLike").equalTo(sEmpleoIdE);

                    prueba.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                                pruebadataSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText( PantallaDetallesEmpleoPrueba.this, "no funciono", Toast.LENGTH_SHORT ).show();
                        }
                    } );


                    Log.d( "estado inactivo", String.valueOf( b ) );

                    //lanzaM( "OFF" );
                    //cambiarColorTB( android.R.color.holo_red_dark, android.R.color.black );
                }

            }
        } );


//----------- para los favoritos  -------------------------------------------------------------------------------------------------------------------------------------------------------



/*
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

      //  categoryList = new ArrayList<>(  );
        recyclerView = findViewById( R.id.recyclerViewfavorito );
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( this ) );
        adapter = new WallpapersAdapter(this, categoryList );

        recyclerView.setAdapter( adapter );

        categoryList = new ArrayList<>(  );
        dbWallpapers = FirebaseDatabase.getInstance().getReference("imagenfav").child( "ImagenFavorito" );

        dbWallpapers.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    for (DataSnapshot wallpaperSnapshot: dataSnapshot.getChildren()){
//                       Category w = wallpaperSnapshot.getValue(Category.class);
//                        categoryList.add( w );

                        String imagen =  wallpaperSnapshot.child( "imagen" ).getValue(String.class);
                        String nombre = wallpaperSnapshot.child( "nombre" ).getValue(String.class);

                        Category c = new Category(imagen,nombre);
                        categoryList.add( c );

                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
*/
/*
//------------------------------------------------------------------------------------------------------------------------------------------------------------------

        checkBoxFav = (CheckBox)findViewById( R.id.checkboxFavoritoEmpleo );
        checkBoxFav.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        } );

//------------------------------------------------------------------------------------------------------------------------------------------------------------------

*/
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
/*
        mcolorRed = (ImageView)findViewById( R.id.imagen_roja );
        mcolorWhile = (ImageView)findViewById( R.id.imagen_blanca );

        mcolorRed.setVisibility( View.GONE );
        mcolorRed.setVisibility( View.VISIBLE );
        mHeartFavorito = new HeartFavorito( mcolorWhile, mcolorRed );
        mgesturedetector = new GestureDetector( this , new GestureListener() );

*/
//------------------------------------------------------------------------------------------------------------------------------------------------------------------


        TvOtrosDatosDE = (TextView) findViewById( R.id.xmlTiDatosEspecificosDE );
        Typeface face = Typeface.createFromAsset( getAssets(), "fonts/robotoslab.bold.ttf" );
        TvOtrosDatosDE.setTypeface( face );

        TvRequisitosDE = (TextView) findViewById( R.id.xmlTiRequisitosDE );
        TvRequisitosDE.setTypeface( face );

        TvtiNotaDE = (TextView) findViewById( R.id.xmlTiNotaDE );
        TvtiNotaDE.setTypeface( face );


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
        DBempleos = database.getReference( "empleos" );
        AplicarEmpleoDataBase = database.getReference( "EmpleosConCandidatos" );
        DbLikes = database.getReference();
        CurriculosDataBase = database.getReference();

        //Firebase
        //database = FirebaseDatabase.getInstance();
        //verificacionEmpleadores = database.getReference("Empleadores");
        verificacionEmpleadores = database.getReference( "Empleadores" );
        //verificacionEmpleadores.orderByChild("Nombre").equalTo("La Sirena");


        MostImagen = (ImageView) findViewById( R.id.xmlImagenEmpleoE );

        TvNombreEmpleoDE = (TextView) findViewById( R.id.xmlTvNombreEmpleoDE );
        TvNombreEmpresaDE = (TextView) findViewById( R.id.xmlTvNombreEmpresaDE );
        TvProvinciaDE = (TextView) findViewById( R.id.xmlTvProvinciaDE );
        TvDireccionDE = (TextView) findViewById( R.id.xmlTvDireccionDE );
        TvTelefonoDE = (TextView) findViewById( R.id.xmlTvTelefonoDE );
        TvEmailDE = (TextView) findViewById( R.id.xmlTvEmailDE );
        TvPaginaWebDE = (TextView) findViewById( R.id.xmlTvPaginaWebDE );
        TvJornadaDE = (TextView) findViewById( R.id.xmlTvJornadaDE );
        TvMostrarHorarioDE = (TextView) findViewById( R.id.xmlTvMostrarHorarioDE );
        TvTipoContratoDE = (TextView) findViewById( R.id.xmlTvTipoContratoDE );
        TvCantidadVacantesDE = (TextView) findViewById( R.id.xmlTvCantidadVacantesDE );
        TvSalarioDE = (TextView) findViewById( R.id.xmlTvSalarioDE );
        TvAreaDE = (TextView) findViewById( R.id.xmlTvAreaDE );
        TvAnosExperienciaDE = (TextView) findViewById( R.id.xmlTvAnosExperienciaDE );
        TvFormacionAcademicaDE = (TextView) findViewById( R.id.xmlTvFormacionAcademicaDE );
        TvIdiomasDE = (TextView) findViewById( R.id.xmlTvIdiomasDE );
        TvSexoRequeridoDE = (TextView) findViewById( R.id.xmlTvSexoRequeridoDE );
        TvRangoEdadDE = (TextView) findViewById( R.id.xmlTvRangoEdadDE );
        TvNotaDE = (TextView) findViewById( R.id.xmlTvNotaDE );
        TvEstadoEmpleoDE = (TextView) findViewById( R.id.xmlTvEstadoEmpleoDE );
        TvFechaPublicacionDE = (TextView) findViewById( R.id.xmlTvFechaPublicacionDE );

        BtnVerificacionEmpresaDE = (Button) findViewById( R.id.xmlBtnVerificacionEmpresaDE );
        BtnVerificacionEmpresaDE.setVisibility( View.INVISIBLE );

        mAuthEmpleador = FirebaseAuth.getInstance();
        user = mAuthEmpleador.getCurrentUser();
        sIdPersonaAplico = user.getUid();
        Log.d( "usuario", sIdPersonaAplico );

        if (getIntent() != null) {
            sEmpleoIdE = getIntent().getStringExtra( "sEmpleoIdBuscado" );
            if (!sEmpleoIdE.isEmpty()) {

                goDetalleEmpleo( sEmpleoIdE );
                VerificarFavorito();
            }
        }


        TvPaginaWebDE.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPaginaWeb();
            }
        } );

        TvAreaDE.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleArea();
            }
        } );

        TvProvinciaDE.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleProvincia();
            }
        } );

        Log.d( "hamaca", sIdEmpleador );


        BtnAplicarEmpleoDE = (Button) findViewById( R.id.xmlBtnAplicarEmpleoDE );
        BtnAplicarEmpleoDE.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AplicarEmpleo( sIdPersonaAplico, sEmpleoIdE );
            }
        } );

        //VerificacionEmpresa(sIdEmpleador);

        //Log.d("holap", perro);
        //Log.d("Verificacion", perro1);
        //SharedPreferences preferences= this.getSharedPreferences("UserPrefEmpleador", Context.MODE_PRIVATE);
        //String Nombre= preferences.getString("Nombre", "Nombre");

        CurriculosDataBase.child( "Curriculos" ).orderByChild( "sIdBuscadorEmpleo" ).equalTo( sIdPersonaAplico ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Is better to use a List, because you don't know the size
                // of the iterator returned by dataSnapshot.getChildren() to
                // initialize the array
                final List<String> areas = new ArrayList<String>();
                Log.d( "holap", String.valueOf( dataSnapshot ) );

                for (DataSnapshot areaSnapshot : dataSnapshot.getChildren()) {

                    String areaName = areaSnapshot.child( "sIdCurriculo" ).getValue( String.class );
                    //areas.add(areaName);
                    Log.d( "holap", areaName );
                    sIdCurriculoAplico = areaName;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText( PantallaDetallesEmpleoPrueba.this, "Usted No tiene empleo registrados", Toast.LENGTH_LONG ).show();


            }
        } );
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
        DBempleos.child( sEmpleoIdE ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Empleos empleos = dataSnapshot.getValue( Empleos.class );
                Log.d( "klk", String.valueOf( dataSnapshot ) );

                Picasso.get().load( empleos.getsImagenEmpleoE() ).into( MostImagen );

                TvNombreEmpleoDE.setText( empleos.getsNombreEmpleoE().toUpperCase() );
                TvNombreEmpresaDE.setText( empleos.getsNombreEmpresaE() );
                TvProvinciaDE.setText( empleos.getsProvinciaE() );
                TvDireccionDE.setText( empleos.getsDireccionE() );
                TvTelefonoDE.setText( empleos.getsTelefonoE() );
                TvPaginaWebDE.setText( empleos.getsPaginaWebE() );
                TvJornadaDE.setText( empleos.getsJornadaE() );
                TvMostrarHorarioDE.setText( empleos.getsHorarioE() );
                TvTipoContratoDE.setText( empleos.getsTipoContratoE() );
                TvCantidadVacantesDE.setText( empleos.getsCantidadVacantesE() );
                TvSalarioDE.setText( empleos.getsSalarioE() );
                TvAreaDE.setText( empleos.getsAreaE() );
                TvAnosExperienciaDE.setText( empleos.getsAnosExperienciaE() );
                TvFormacionAcademicaDE.setText( empleos.getsFormacionAcademicaE() );
                TvIdiomasDE.setText( empleos.getsMostrarIdiomaE() );
                TvSexoRequeridoDE.setText( empleos.getsSexoRequeridoE() );
                TvRangoEdadDE.setText( empleos.getsRangoE() );
                TvNotaDE.setText( empleos.getsOtrosDatosE() );
                TvEstadoEmpleoDE.setText( empleos.getsEstadoEmpleoE() );
                TvFechaPublicacionDE.setText( empleos.getsFechaPublicacionE() );

                //sIdEmpleador = empleos.getsIdEmpleador();
                sIdEmpleador = dataSnapshot.child( "sIdEmpleadorE" ).getValue( String.class );

///String klk= empleos.getsProvinciaE();
                Log.d( "pagina", String.valueOf( sIdEmpleador ) );
                VerificacionEmpresa( sIdEmpleador );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public void VerificacionEmpresa(String sIdEmpleador) {
        verificacionEmpleadores.child( sIdEmpleador ).addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d( "holapkkk", String.valueOf( dataSnapshot ) );
                //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
                sVerificarEmpleador = dataSnapshot.child( "sVerificacionEmpleador" ).getValue( Boolean.class );
                //Log.d("h", String.valueOf(dataSnapshot.child("Nombre").getValue(String.class)));
                Log.d( "holapkkk", String.valueOf( sVerificarEmpleador ) );

                if (sVerificarEmpleador == null) {
                    Toast.makeText( PantallaDetallesEmpleoPrueba.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG ).show();

                } else {

                    if (sVerificarEmpleador == true) {
                        BtnVerificacionEmpresaDE.setVisibility( View.VISIBLE );
                    }
                    if (sVerificarEmpleador == false) {
                        BtnVerificacionEmpresaDE.setVisibility( View.INVISIBLE );
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
                Toast.makeText( PantallaDetallesEmpleoPrueba.this, "No se realizo correctamente su aplicacion al empleo", Toast.LENGTH_LONG ).show();

            }
        } );

    }

    public void goPaginaWeb() {

        Intent intent = new Intent( PantallaDetallesEmpleoPrueba.this, PantallaNavegador.class );
        intent.putExtra( "sPaginaWebDE", TvPaginaWebDE.getText().toString().trim() );

        String hola = TvPaginaWebDE.getText().toString().trim();
        Log.d( "klkpaginaweb", hola );
        startActivity( intent );

    }

    public void goDetalleProvincia() {

        Intent intent = new Intent( PantallaDetallesEmpleoPrueba.this, PantallaDetallesProvincia.class );
        intent.putExtra( "sProvinciaDE", TvProvinciaDE.getText().toString().trim() );

        String hola = TvProvinciaDE.getText().toString().trim();
        Log.d( "klkprovincia", hola );
        startActivity( intent );

    }

    public void goDetalleArea() {

        Intent intent = new Intent( PantallaDetallesEmpleoPrueba.this, PantallaDetallesArea.class );
        intent.putExtra( "sAreaDE", TvAreaDE.getText().toString().trim() );

        String hola = TvAreaDE.getText().toString().trim();
        Log.d( "klkarea", hola );
        startActivity( intent );

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

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "EEE dd MMM yyyy" );
        sFechadeAplicacion = simpleDateFormat.format( new Date() );
        Log.d( "klkcurri", String.valueOf( sIdCurriculoAplico ) );

        if (sIdCurriculoAplico != null) {

            if (!sIdCurriculoAplico.isEmpty()) {

                String sIdAplicarEmpleo = AplicarEmpleoDataBase.push().getKey();

                AplicarEmpleo aplicarEmpleo = new AplicarEmpleo( sIdAplicarEmpleo, sIdCurriculoAplico, sIdEmpleoAplico, sIdPersonaAplico, sFechadeAplicacion );
                AplicarEmpleoDataBase.child( sIdAplicarEmpleo ).setValue( aplicarEmpleo );

                Toast.makeText( this, "Su Aplicacion se realizo exitosamente", Toast.LENGTH_LONG ).show();
            }

        } else {
            Toast.makeText( this, "Usted Aun No tiene Ningun Empleo Registrado", Toast.LENGTH_LONG ).show();
            BtnAplicarEmpleoDE.setEnabled( false );

            AlertDialog.Builder builder1 = new AlertDialog.Builder( PantallaDetallesEmpleoPrueba.this );
            builder1.setMessage( "Desea Registrar su Curriculo?" );
            builder1.setCancelable( true );

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent( PantallaDetallesEmpleoPrueba.this, cPantallaRegistrarCurriculo.class );
                            startActivityForResult( intent, 0 );
                        }
                    } );

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    } );

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    private void VerificarFavorito() {
        final Query q = DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
                .child( sIdPersonaAplico )//referencia usuario
                .child( "likes" )//referencia likes
                .orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );
        Log.d( "fav", String.valueOf( sEmpleoIdE ) );
        Log.d( "fav", String.valueOf( sIdPersonaAplico ) );

        q.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d( "fav", String.valueOf( dataSnapshot ) );
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                    if (FavdataSnapshot != null) {
                        // mImageButton.setImageResource( R.mipmap.likerojo );
                        // checkBoxFav.setChecked( true );
                        btnfavorito.setChecked( true );


                    } else {
                        // mImageButton.setImageResource( R.mipmap.likegris );
                        // checkBoxFav.setChecked( false );
                        btnfavorito.setChecked( false );

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }

    private void marcarFavorito() {
        String newLikeID = DbLikes.push().getKey();

        DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
                .child( userActivo )//referencia usuario
                .child( "likes" )//referencia likes
                .child( newLikeID )
                .child( "IdEmpleoLike" )
                .setValue( sEmpleoIdE );
        //  mImageButton.setImageResource( R.mipmap.likerojo );
        // checkBoxFav.setChecked( true );


    }

    public void onClick(View view) {
    }
}

//    private void marcarFavorito() {
//
//        mProcessLike = true;
//        String newLikeID = DbLikes.push().getKey();
//
//        if (mProcessLike) {
//            DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
//                    .child( userActivo )//referencia usuario
//                    .child( "likes" )//referencia likes
//                     .child( newLikeID )
//                    .child( "IdEmpleoLike" )
//                    .setValue( sEmpleoIdE );
//        }else {
//
//            final Query prueba = DbLikes.child( "BuscadoresEmpleos" ).child( userActivo )
//                    .child( "likes" ).orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );
//
//            prueba.addListenerForSingleValueEvent( new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
//                        pruebadataSnapshot.getRef().removeValue();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    Toast.makeText( PantallaDetallesEmpleoPrueba.this, "no funciono", Toast.LENGTH_SHORT ).show();
//                }
//            } );
//        }

// String newLikeID = DbLikes.push().getKey();

//        mDatabaseLike.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (mProcessLike){
//                    if (dataSnapshot.child( userActivo  ).hasChild( "UsuarioQueMarcx" )){
//
//                        mDatabaseLike.child( userActivo ).child(  "UsuarioQueMarcx").removeValue();
//                        mImageButton.setImageResource( R.mipmap.likegris );
//
//                        mProcessLike = false;
//
//                  }else {
////
////                        Query prueba2 = mDatabaseLike.child( sEmpleoIdE ).orderByChild( "UsuarioQueMarcx" ).equalTo(  userActivo );
////
////                        prueba2.addListenerForSingleValueEvent( new ValueEventListener() {
////                            @Override
////                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                                for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
////
////                                    if (pruebadataSnapshot != null){
////                                        mImageButton.setImageResource( R.mipmap.likerojo );
////
////                                    }
////
////                                }
////
////                            }
////
////                            @Override
////                            public void onCancelled(@NonNull DatabaseError databaseError) {
////
////                            }
////                        } );
//
//
//                        favoritos.child( userActivo ).child( "UsuarioQueMarcx" ).setValue(sEmpleoIdE   );
//                        mImageButton.setImageResource( R.mipmap.likerojo );
//
//                        mProcessLike = false;
//
//                    }
//                }
//
//}

//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );
//
//    }
//
//    public void mantenercolor(){
//
//        favoritos.addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.child( sEmpleoIdE ).hasChild( userActivo )){
//
//                    mImageButton.setImageResource( R.mipmap.likerojo );
//                }else {
//                    mImageButton.setImageResource( R.mipmap.likegris );
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );
//
//    }
///
    /*
                String newLikeID = DbLikes.push().getKey();
        DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
                .child( userActivo )//referencia usuario
                .child( "likes" )//referencia likes
                .child( newLikeID )
                .child( "IdEmpleoLike" )
                .setValue( sEmpleoIdE );

        mProcessLike = false;



    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

        final Query prueba = DbLikes.child( "BuscadoresEmpleos" ).child( userActivo )
                .child( "likes" ).orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );

        prueba.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                    pruebadataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText( PantallaDetallesEmpleoPrueba.this, "no funciono", Toast.LENGTH_SHORT ).show();
            }
        } );

        mProcessLike = false;

    }
/*


/*
    public void onClick(View view) {

        // mantenerRojo ();


        String newLikeID = DbLikes.push().getKey();


        if (view.getId() == R.id.checkboxFavoritoEmpleo) {
            if (checkBoxFav.isChecked()) {

                DbLikes.child( "BuscadoresEmpleos" )//referencia empleadores
                        .child( userActivo )//referencia usuario
                        .child( "likes" )//referencia likes
                        .child( newLikeID )
                        .child( "IdEmpleoLike" )
                        .setValue( sEmpleoIdE );
            } else {
                final Query prueba = DbLikes.child( "BuscadoresEmpleos" ).child( userActivo )
                        .child( "likes" ).orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );

                prueba.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                            pruebadataSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                        Toast.makeText( PantallaDetallesEmpleoPrueba.this, "no funciono", Toast.LENGTH_SHORT ).show();
                    }
                } );

            }

        }
    }
*/
/*
    public void mantenerRojo() {
//        String newLikeID = DbLikes.push().getKey();
//        Query manterRojo = DbLikes.child( "BuscadoresEmpleos" ).child( userActivo ).child("likes").child( newLikeID ).child("IdEmpleoLike");

        Query prueba2 = DbLikes.child( "BuscadoresEmpleos" ).child( userActivo )
                .child( "likes" ).orderByChild( "IdEmpleoLike" ).equalTo( sEmpleoIdE );

        prueba2.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                    //pruebadataSnapshot.getRef().removeValue();
                    if (pruebadataSnapshot != null) {

                        Log.d( "favotito", String.valueOf( pruebadataSnapshot ) );
                        //checkBoxFav.isChecked(this);

                        Toast.makeText( PantallaDetallesEmpleoPrueba.this, "estaba dada el like", Toast.LENGTH_SHORT ).show();
                    }
                    if (String.valueOf( pruebadataSnapshot ) == "") {
                        Log.d( "favotito no dado", String.valueOf( pruebadataSnapshot ) );

                    }
                    if (pruebadataSnapshot.getValue() == "") {
                        Log.d( "favotito dont dado", String.valueOf( pruebadataSnapshot ) );

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                // Toast.makeText( PantallaDetallesEmpleoPrueba.this, "no funciono", Toast.LENGTH_SHORT ).show();
            }
        } );

    }*/


