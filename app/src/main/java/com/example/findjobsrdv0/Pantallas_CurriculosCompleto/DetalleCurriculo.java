package com.example.findjobsrdv0.Pantallas_CurriculosCompleto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.findjobsrdv0.Clases_EmpleoCompleto.AplicarCurriculo;

import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesArea;
import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesProvincia;
import com.example.findjobsrdv0.GeneralesApp.PantallaDetallesUniversidad;
import com.example.findjobsrdv0.GeneralesApp.PantallaNavegador;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.AreasCurriculos;
import com.example.findjobsrdv0.Modelos_CurriculoCompleto.Curriculos;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalleCurriculo extends AppCompatActivity {

    private TextView txtNombreCurr, txtApellidoCurr, txtCedulaCurr, txtEmailCurr, txtTelefonoCurr, txtCelularCurr, txtprovinciaCurr, txtEstadoCivil, txtDireccionCurr, txtOcupacion, txtIdioma,
            txtEstadoActualCur, txtGradoMayorCurr, txtHabilidades, txtFecha, TVSexo, TvYaAplicoCurriculo, TVAreaCurr, TVNivelPromarioCurr, TVNivelSecundarioCurr, TVCarrera,
     TVUniversidadCurr, TVAreaPrincipal, TVAreaSecundaria, TVAreaTerciaria;

    private FirebaseDatabase database,prueba;
    private DatabaseReference detalelcurriculo, DbLikesFavCurri;
    private DatabaseReference AplicarInteresCurriculoDataBase;

    private DatabaseReference DBAreas;

    private String detallecurrid = "";
    private Button btnIrFormacionAcademica, btnIrReferencia, btnIrExperienciaLab, btnOtrosEstudios, btnAplicarCurriculo;

    private FirebaseAuth mAuthEmpresa_Empleador,mFirebaseAuth;
    private FirebaseUser user,userActivo;

    private String sIdAplicarCurriculo, sIdCurriculoAplico, sIdEmpresaAplico, sFechadeAplicacion;

    private ToggleButton btnfavoritoCurriculo;

    private String sIdPersonaMarco;

    private ImageView FotoCurriculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_curriculo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        TvYaAplicoCurriculo = (TextView) findViewById(R.id.xmlTvYaaplicasteCurriculo);
        TvYaAplicoCurriculo.setVisibility(View.INVISIBLE);

        prueba = FirebaseDatabase.getInstance();
        DbLikesFavCurri = prueba.getReference();
        DbLikesFavCurri.keepSynced( true );

        mFirebaseAuth = FirebaseAuth.getInstance();
        userActivo = mFirebaseAuth.getCurrentUser();
        sIdPersonaMarco = userActivo.getUid();


        database = FirebaseDatabase.getInstance();
        detalelcurriculo = database.getReference("Curriculos");
        AplicarInteresCurriculoDataBase = database.getReference("CurriculosConSolicitudes");

        DBAreas = FirebaseDatabase.getInstance().getReference("AreasCurriculos");

        FotoCurriculo = (ImageView) findViewById(R.id.xmlImagenPerfilCurriculo);

        txtNombreCurr = (TextView) findViewById(R.id.xmlTvNombreDetalleCu);
        txtApellidoCurr = (TextView) findViewById(R.id.xmlTvApellidoDetalleCu);
        txtCedulaCurr = (TextView) findViewById(R.id.xmlTvCedulaDetalleCu);
        txtEmailCurr = (TextView) findViewById(R.id.xmlTvEmailDetalleCu);
        txtTelefonoCurr = (TextView) findViewById(R.id.xmlTvTelefonoDetalleCu);
        txtCelularCurr = (TextView) findViewById(R.id.xmlTvCelularDetalleCu);
        txtprovinciaCurr = (TextView) findViewById(R.id.xmlTvProvinciaDetalleCu);
        txtEstadoCivil = (TextView) findViewById(R.id.xmlTvEstadoCivilDetalleCu);
        txtDireccionCurr = (TextView) findViewById(R.id.xmlTvDireccionDetalleCu);
        txtOcupacion = (TextView) findViewById(R.id.xmlTvOcupacionDetalleCu);
        txtIdioma = (TextView) findViewById(R.id.xmlTvIdiomaDetalleCu);
        txtEstadoActualCur = (TextView) findViewById(R.id.xmlTvDisponibilidadDetalleCu);
        txtGradoMayorCurr = (TextView) findViewById(R.id.xmlTvMaestriaDetalleCu);
        txtHabilidades = (TextView) findViewById(R.id.xmlTvHabilidadesDetalleCu);
        txtFecha = (TextView) findViewById(R.id.xmlTvFechaNacimientoDetalleCu);
        TVSexo = (TextView) findViewById(R.id.xmlTvSexoDetalleCu);
//        TVAreaCurr = (TextView) findViewById( R.id.xmlTvAreaDetalleCu );

        TVNivelPromarioCurr = (TextView) findViewById( R.id.xmlTvNivelPrimarioDetalleCu );
        TVNivelSecundarioCurr = (TextView) findViewById( R.id.xmlTvNivelSecundarioDetalleCu );
        TVCarrera = (TextView) findViewById( R.id.xmlTvCarreraDetalleCu );
        TVUniversidadCurr = (TextView) findViewById( R.id.xmlTvUniversidadDetalleCu );

        TVAreaPrincipal = (TextView) findViewById( R.id.xmlTvAreaPrincipalDetalleCu );
        TVAreaSecundaria= (TextView) findViewById( R.id.xmlTvAreaSecunadriaDetalleCu );
        TVAreaTerciaria = (TextView) findViewById( R.id.xmlTvAreaTerciariaDetalleCu );



        if (getIntent() != null)
            detallecurrid = getIntent().getStringExtra("detallecurrID");

        Log.d( "verrr", ( detallecurrid ) );
        Log.d( "ver", String.valueOf( detallecurrid ) );
            goDetailCurriculo(detallecurrid);
        CargarAreas(detallecurrid);

            VerificarFavorito();


        VerificarAplicacionCurriculoExiste(sIdPersonaMarco);

        btnfavoritoCurriculo = (ToggleButton) findViewById(R.id.btn_CurriculosFavoritos);
        btnfavoritoCurriculo.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    marcarFavorito(detallecurrid);
                    Log.d( "estado activo", String.valueOf( b ) );
                }else {
                    final Query prueba = DbLikesFavCurri.child( "EmpleadoresConFavoritos" ).child( sIdPersonaMarco )
                            .child( "likes" ).orderByChild( "IdCurriculoLike" ).equalTo( detallecurrid );

                    prueba.addListenerForSingleValueEvent( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot pruebadataSnapshot : dataSnapshot.getChildren()) {
                                pruebadataSnapshot.getRef().removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                            Toast.makeText( DetalleCurriculo.this, "no funciono", Toast.LENGTH_SHORT ).show();
                        }
                    } );

                    Log.d( "estado inactivo", String.valueOf( b ) );
                }
            }
        } );

        mAuthEmpresa_Empleador = FirebaseAuth.getInstance();
        user = mAuthEmpresa_Empleador.getCurrentUser();
        sIdEmpresaAplico= user.getUid();


//        btnIrFormacionAcademica = (Button) findViewById(R.id.xmlBtnFormacionAcademicaDetalleCu);
//        btnIrFormacionAcademica.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(DetalleCurriculo.this, DetalleFormacionAcademica.class);
//                intent.putExtra("DetalleFormacionAcademicaID", detallecurrid);
//                startActivity(intent);
//            }
//        });

        btnIrReferencia = (Button) findViewById(R.id.xmlBtnReferenciasDetalleCu);
        btnIrReferencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleCurriculo.this, DetalleReferencias.class);
                intent.putExtra("DetalleReferenciaID", detallecurrid);
                startActivity(intent);
            }
        });

        btnIrExperienciaLab = (Button) findViewById(R.id.xmlBtnExperienciaLaboralDetalleCu);
        btnIrExperienciaLab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleCurriculo.this, DetalleExperienciaLaboral.class);
                intent.putExtra("DetalleExperienciaLabID", detallecurrid);
                startActivity(intent);
            }
        });

        btnOtrosEstudios = (Button) findViewById(R.id.xmlBtnOtrosEstudiosDetalleCu);
        btnOtrosEstudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetalleCurriculo.this, DetalleOtrosEstudios.class);
                intent.putExtra("DetalleOtrosEstudiosID", detallecurrid);
                startActivity(intent);
            }
        });

        btnAplicarCurriculo = (Button) findViewById(R.id.xmlBtnAplicarEmpleoDetalleCu);
        btnAplicarCurriculo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AplicarInteresCurriculo(sIdEmpresaAplico,detallecurrid);

            }
        });


        TVUniversidadCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goUniversidad();
            }
        });

        TVAreaPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleArea();
            }
        });
        TVAreaSecundaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleArea2();
            }
        });
        TVAreaTerciaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleArea3();
            }
        });

        txtprovinciaCurr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDetalleProvincia();
            }
        });


    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    private void AplicarInteresCurriculo(String sIdEmpleadorAplico,String Interes) {

        sIdCurriculoAplico = Interes;

        sIdEmpresaAplico = sIdEmpleadorAplico;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMM yyyy");
        sFechadeAplicacion = simpleDateFormat.format(new Date());

        sIdAplicarCurriculo = AplicarInteresCurriculoDataBase.push().getKey();

        AplicarCurriculo aplicarCurriculo = new AplicarCurriculo(sIdAplicarCurriculo, sIdCurriculoAplico, sIdEmpresaAplico, sFechadeAplicacion);
        AplicarInteresCurriculoDataBase.child(sIdAplicarCurriculo).setValue(aplicarCurriculo);
    }


    private void goDetailCurriculo(String detallecurrid) {
        detalelcurriculo.child(detallecurrid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("hola", String.valueOf(dataSnapshot));
                Curriculos vistaCurriculomodel = dataSnapshot.getValue(Curriculos.class);

                Picasso.get().load(vistaCurriculomodel.getsImagenC()).into(FotoCurriculo);

                txtNombreCurr.setText(vistaCurriculomodel.getsNombreC());
                txtApellidoCurr.setText(vistaCurriculomodel.getsApellidoC());
                txtCedulaCurr.setText(vistaCurriculomodel.getsCedulaC());
                txtEmailCurr.setText(vistaCurriculomodel.getsEmailC());
                txtTelefonoCurr.setText(vistaCurriculomodel.getsTelefonoC());
                txtCelularCurr.setText(vistaCurriculomodel.getsCelularC());
                txtprovinciaCurr.setText(vistaCurriculomodel.getsProvinciaC());
                txtEstadoCivil.setText(vistaCurriculomodel.getsEstadoCivilC());
                txtDireccionCurr.setText(vistaCurriculomodel.getsDireccionC());
                txtOcupacion.setText(vistaCurriculomodel.getsOcupacionC());
                txtEstadoActualCur.setText(vistaCurriculomodel.getsEstadoActualC());
                txtGradoMayorCurr.setText(vistaCurriculomodel.getsGradoMayorC());
                txtHabilidades.setText(vistaCurriculomodel.getsHabilidadesC());
                txtIdioma.setText( vistaCurriculomodel.getsIdiomaC() );
                TVSexo.setText( vistaCurriculomodel.getsSexoC() );
                txtFecha.setText(vistaCurriculomodel.getsFechaC());
//                TVAreaCurr.setText( vistaCurriculomodel.getsAreaC() );
                TVNivelPromarioCurr.setText( vistaCurriculomodel.getsNivelPrimarioFormAcad() );
                TVNivelSecundarioCurr.setText( vistaCurriculomodel.getsNivelSecundarioFormAcad() );
                TVCarrera.setText( vistaCurriculomodel.getsCarreraFormAcad() );
                TVUniversidadCurr.setText( vistaCurriculomodel.getsUniversidadFormAcad() );


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CargarAreas(String detallecurrid){

        Query query = DBAreas.orderByChild( "sIdCurriculo" ).equalTo(detallecurrid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                    Log.d("holaarea", String.valueOf(FavdataSnapshot));

                    AreasCurriculos areasCurriculos = FavdataSnapshot.getValue(AreasCurriculos.class);

                    TVAreaPrincipal.setText( areasCurriculos.getsAreaPrincipalCurr() );
                    TVAreaSecundaria.setText( areasCurriculos.getsAreaSecundariaCurr() );
                    TVAreaTerciaria.setText( areasCurriculos.getsAreaTerciaria() );
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void VerificarFavorito() {
        final Query q = DbLikesFavCurri.child( "EmpleadoresConFavoritos" )//referencia empleadores
                .child( sIdPersonaMarco )//referencia usuario
                .child( "likes" )//referencia likes
                .orderByChild( "IdCurriculoLike" ).equalTo( detallecurrid );

        q.addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d( "fav", String.valueOf( dataSnapshot ) );
                for (DataSnapshot FavdataSnapshot : dataSnapshot.getChildren()) {

                    if (FavdataSnapshot != null) {
                        btnfavoritoCurriculo.setChecked( true );

                    } else {
                        btnfavoritoCurriculo.setChecked( false );
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    private void marcarFavorito(String sIdCurriculoFav) {

        Query q = DbLikesFavCurri.child("EmpleadoresConFavoritos")
                .child(sIdPersonaMarco)
                .child("likes")
                .orderByChild("IdCurriculoLike").equalTo(sIdCurriculoFav);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    Toast.makeText(DetalleCurriculo.this, "Favorito si", Toast.LENGTH_LONG).show();
                    String newLikeID = DbLikesFavCurri.push().getKey();

                    DbLikesFavCurri.child( "EmpleadoresConFavoritos" )//referencia empleadores
                            .child( sIdPersonaMarco )//referencia usuario
                            .child( "likes" )//referencia likes
                            .child( newLikeID )
                            .child( "IdCurriculoLike" )
                            .setValue( detallecurrid );
                }
                else {
                    Toast.makeText(DetalleCurriculo.this, "Favorito repetido", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void VerificarAplicacionCurriculoExiste(String sPersonaIdE) {

        Query q = AplicarInteresCurriculoDataBase.orderByChild("sIdEmpresaAplico").equalTo(sPersonaIdE);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot CurriculosSnapshot : dataSnapshot.getChildren()) {

                    String IdCurriculoAplico = CurriculosSnapshot.child("sIdCurriculoAplico").getValue(String.class);
                    if(IdCurriculoAplico.equals(detallecurrid)){
                        btnAplicarCurriculo.setEnabled(false);
                        TvYaAplicoCurriculo.setVisibility(View.VISIBLE);

                        //btnAplicarCurriculo.setTextColor(getResources().getColor(R.color.md_red_900));
                        //Toast.makeText(PantallaDetallesEmpleo.this, "Usted ha Aplicado anteriormente a este empleo", Toast.LENGTH_LONG).show();

                    }else{
                        Log.d("dataidEmpleosAplicar", IdCurriculoAplico);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void goDetalleProvincia() {

        Log.d( "klkprov", String.valueOf( txtprovinciaCurr ));

        Intent intent = new Intent(DetalleCurriculo.this, PantallaDetallesProvincia.class);
        intent.putExtra("sProvinciaDE", txtprovinciaCurr.getText().toString().trim());
        String hola = txtprovinciaCurr.getText().toString().trim();
        Log.d("klkprovincia", hola);
        startActivity(intent);

    }

    public void goUniversidad() {

        Intent intent = new Intent(DetalleCurriculo.this, PantallaDetallesUniversidad.class);
        intent.putExtra("sNombreUniDetallekey", TVUniversidadCurr.getText().toString().trim());
        String hola = TVUniversidadCurr.getText().toString().trim();
        Log.d("klkarea", hola);
        startActivity(intent);

    }

    public void goDetalleArea() {

        Intent intent = new Intent(DetalleCurriculo.this, PantallaDetallesArea.class);
        intent.putExtra("sAreaDE", TVAreaPrincipal.getText().toString().trim());
        String hola = TVAreaPrincipal.getText().toString().trim();
        Log.d("klkarea", hola);
        startActivity(intent);

    }

    public void goDetalleArea2() {

        Intent intent = new Intent(DetalleCurriculo.this, PantallaDetallesArea.class);
        intent.putExtra("sAreaDE", TVAreaSecundaria.getText().toString().trim());
        String hola = TVAreaSecundaria.getText().toString().trim();
        Log.d("klkarea", hola);
        startActivity(intent);

    }
    public void goDetalleArea3() {

        Intent intent = new Intent(DetalleCurriculo.this, PantallaDetallesArea.class);
        intent.putExtra("sAreaDE", TVAreaTerciaria.getText().toString().trim());
        String hola = TVAreaTerciaria.getText().toString().trim();
        Log.d("klkarea", hola);
        startActivity(intent);

    }

}
